package com.wzy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wzy.dto.FileConfig;
import com.wzy.util.ExcelCreator;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {

    public static void main(String[] args) {
        ExcelCreator excelCreator = new ExcelCreator();
        FileConfig config = getConfig();
        Integer startDate = config.getStartDate();
        Integer endDate = config.getEndDate();
        if (startDate != null && endDate != null) {
            for (int i = startDate; i < endDate; i++) {
                excelCreator.writeDailyData(config, String.valueOf(i));
            }
        } else {
            String tradeDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
            excelCreator.writeDailyData(config, tradeDate);
        }
    }

    public static FileConfig getConfig() {
        ObjectMapper objectMapper = new YAMLMapper();
        InputStream resourceAsStream = App.class.getClassLoader().getResourceAsStream("path.yml");
        try {
            return objectMapper.readValue(resourceAsStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件失败");
        }
    }
}
