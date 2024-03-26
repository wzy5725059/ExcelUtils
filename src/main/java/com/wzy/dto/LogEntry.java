package com.wzy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {
    private String memberId;

    private String deal;


    private Integer overallTime;


    public void setValue(String name,String value){

    }
}
