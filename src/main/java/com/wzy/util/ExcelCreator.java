package com.wzy.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzy.dto.FileConfig;
import com.wzy.dto.TableFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class ExcelCreator {

    //private final LogEntry headEntry = new LogEntry("会员", 2024);

    private HashMap<String, HashMap<String, String>> map = new HashMap<>();

    public void clearMap() {
        map.clear();
    }

    /**
     * 按配置的文件名格式，依次读取多个交易日的结算文件日志
     * * @param tradeDate 日期
     */
    public void writeDailyData(FileConfig config, String tradeDate) {
        try {
            String logDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(tradeDate));
        } catch (ParseException e) {
            log.error("logDate parse error");
        }
        String fileName = config.getFileName();
        fileName = fileName.replaceFirst("\\d{8}", tradeDate);
        String logFilePath1 = config.getFilePath1() + fileName;
        String logFilePath2 = config.getFilePath2() + fileName;
        String logFilePath3 = config.getFilePath3() + fileName;
        String tmpPath = config.getResultFilePath() + "temp.xlsx";
        String resultPath = config.getResultFilePath() + "result.xlsx";
        //headEntry.setOverallTime(Integer.valueOf(tradeDate));
        parseLogFile(logFilePath1, map);
        parseLogFile(logFilePath2, map);
        parseLogFile(logFilePath3, map);

        ArrayList<HashMap<String, String>> list = new ArrayList<>(map.values());
        List<TableFormat> entryList = list.stream().map(x -> {
            ObjectMapper objectMapper = new ObjectMapper();
            TableFormat logEntry = null;
            try {
                String s = objectMapper.writeValueAsString(x);
                logEntry = objectMapper.readValue(s, TableFormat.class);
            } catch (JsonProcessingException e) {
                log.error("json parse error {}", x);

            }
            return logEntry;
        }).collect(Collectors.toList());
        map.clear();
        entryList.sort(new CustomComparator());

        // 使用EasyExcel生成Excel文件
        //ExcelWriter writer = EasyExcel.write(excelFilePath).build();
        if (entryList.size() > 1) {
            WriteSheet sheet1 = EasyExcel.writerSheet(tradeDate).head(TableFormat.class).build();

            writeAppend(tmpPath, resultPath, sheet1, entryList);
            log.info("数据写入成功：" + tradeDate);
        }
    }

    private void writeAppend(String tmpPath, String resultPath, WriteSheet sheet1, List<TableFormat> list) {
        File tmp = new File(tmpPath);
        File result = new File(resultPath);
        ExcelWriter writer;
        if (result.exists()) {
            // 第二次按照原有格式，不需要表头，追加写入
            writer = EasyExcel.write().
                    withTemplate(result).file(tmp).build();
            log.info("追加Excel文件：" + result);
        } else {
            // 第一次写入需要表头
            writer = EasyExcel.write(result).build();
            log.info("新建文件Excel文件：" + result);
        }
        writer.write(list, sheet1);

        writer.finish();
        if (tmp.exists()) {
            result.delete();
            tmp.renameTo(result);
        }
    }


    private void parseLogFile(String logFilePath, HashMap<String, HashMap<String, String>> list) {
        // 在这里读取a.log文件并解析信息
        // 读取日志文件并提取数据

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                LogParser.formatText(line, list);
            }
        } catch (FileNotFoundException e) {
            log.error("文件不存在：" + logFilePath);
        } catch (Exception e) {
            log.error(e);
        }
    }

    static class CustomComparator implements Comparator<TableFormat> {

        @Override
        public int compare(TableFormat p1, TableFormat p2) {

            // 如果一个元素的name是"会员"，则"会员"排在前面
            String memberId1 = p1.getMemberId();
            String memberId2 = p2.getMemberId();
            if ("会员".equals(memberId1)) {
                return -1;
            }
            // 如果一个元素的name是"总计"，则"总计"排在最后
            if ("总计".equals(memberId1)|| StringUtils.isEmpty(memberId1)) {
                return 1;
            }

            // 如果两个元素的name都是数字，则按照数字大小排序
            if (memberId1.matches("\\d+") && memberId2.matches("\\d+")) {
                return Integer.compare(Integer.parseInt(memberId1), Integer.parseInt(memberId2));
            }
            return memberId1.compareTo(memberId1);
        }
    }
}