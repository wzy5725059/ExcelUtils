package com.wzy.util;

import com.wzy.dto.LogEntry;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class LogParser {

    final static Pattern member = Pattern.compile("会员\\[\\d+]"); // 匹配会员号   // 匹配会员号
    final static Pattern num = Pattern.compile("\\d+");
    final static Pattern time = Pattern.compile("整体时间：\\d+");
    final static Pattern totalTime = Pattern.compile("会服结算数据打包文件生成完成！整体运行时间：\\d+");

    public static void formatText(String line, List<LogEntry> list, LogEntry totalEntry) {
        log.info(line);
        Matcher matcher = member.matcher(line);
        Matcher matcher1 = time.matcher(line);
        Matcher matcher2 = totalTime.matcher(line);
        if (matcher.find() && matcher1.find()) {
            String txt1 = matcher.group();// 如果匹配成功，则提取数据并写入Excel表格
            String txt2 = matcher1.group();// 如果匹配成功，则提取数据并写入Excel表格
            Matcher matcher3 = num.matcher(txt1);
            Matcher matcher4 = num.matcher(txt2);
            if (matcher3.find() && matcher4.find()) {
                String memberId = matcher3.group();
                String times = matcher4.group();
                list.add(new LogEntry(memberId, Integer.parseInt(times) / 1000));
            }

        }
        if (matcher2.find()) {

            Matcher matcher5 = num.matcher(matcher2.group());
            if (matcher5.find()) {
                int time = Integer.parseInt(matcher5.group()) / 1000;
                Integer originTime = totalEntry.getOverallTime();
                if (null == originTime || time > originTime) {
                    totalEntry.setOverallTime(time);
                }
            }
        }
    }
}
