package com.wzy.util;

import com.wzy.dto.TableFormat;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class LogParser {

    final static Pattern member = Pattern.compile("会员\\[\\d+]"); // 匹配会员号   // 匹配会员号

    final static Pattern table = Pattern.compile("业务\\[.*?]"); // 匹配表名   // 匹配会员号

    final static Pattern num = Pattern.compile("\\d+");

    final static Pattern name = Pattern.compile("\\[(.*?)]");

    final static Pattern time = Pattern.compile("整体时间：\\d+");

    final static Pattern time2 = Pattern.compile("处理时间：\\d+");

    final static Pattern totalTime = Pattern.compile("会服结算数据打包文件生成完成！整体运行时间：\\d+");

    public static void formatText(String line, HashMap<String, HashMap<String, String>> listMap, TableFormat totalEntry) {
        log.info(line);
        Matcher matcher = member.matcher(line);
        Matcher matcher1 = time2.matcher(line);
        Matcher matcher2 = time.matcher(line);
        Matcher matcher3 = totalTime.matcher(line);
        if (matcher.find()) {
            // txt1提取值为会员[011]
            String txt1 = matcher.group();
            Matcher matcher4 = num.matcher(txt1);
            String memberId = "";
            if (matcher4.find()) {
                memberId = matcher4.group();
            }
            String txt2;
            String txt4 = null;
            HashMap<String, String> memMap = listMap.get(memberId);
            if (memMap == null) {
                memMap = new HashMap<>();
                memMap.put("会员", memberId);
                listMap.put(memberId, memMap);
            }

            if (matcher1.find()) {
                // txt2提取值为处理时间：7470
                txt2 = matcher1.group();
                Matcher matcher5 = table.matcher(line);
                if (matcher5.find()) {
                    // txt3 提取值为业务[成交表]
                    String txt3 = matcher5.group();
                    Matcher matcher6 = name.matcher(txt3);
                    if (matcher6.find())
                        // txt3 提取值为成交表
                        txt4 = matcher6.group();
                    txt4 = txt4.substring(1, txt4.length() - 1);
                }
            } else if (matcher2.find()) {
                // txt2提取值为整体时间：80747
                txt2 = matcher2.group();
                txt4 = "整体时间";

            } else {
                return;
            }
            Matcher matcher5 = num.matcher(txt2);
            if (matcher5.find()) {
                String times = matcher5.group();
                memMap.put(txt4, (Integer.parseInt(times) / 1000) + "");
            }

        }
        if (matcher3.find()) {

            Matcher matcher5 = num.matcher(matcher3.group());
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
