package com.wzy.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableFormat {

    @ExcelProperty("会员")
    @JsonProperty("会员")
    private String memberId;

    @ExcelProperty("持仓表")
    @JsonProperty("持仓表")
    private String S02;

    @ExcelProperty("平仓盈亏表")
    @JsonProperty("平仓盈亏表")
    private String S03;

    @ExcelProperty("非优惠持仓明细表")
    @JsonProperty("非优惠持仓明细表")
    private String S04;

    @ExcelProperty("资金结算表")
    @JsonProperty("资金结算表")
    private String S05;

    @ExcelProperty("每日资金变动表")
    @JsonProperty("每日资金变动表")
    private String S06;

    @ExcelProperty("成交表")
    @JsonProperty("成交表")
    private String S07;

    @ExcelProperty("期权执行表")
    @JsonProperty("期权执行表")
    private String S08;

    @ExcelProperty("期权执行持仓变动明细表")
    @JsonProperty("期权执行持仓变动明细表")
    private String S09;


    @ExcelProperty("优惠组合持仓明细表")
    @JsonProperty("优惠组合持仓明细表")
    private String S10;

    @ExcelProperty("期权申请表")
    @JsonProperty("期权申请表")
    private String S11;

    @ExcelProperty("权利金收支表")
    @JsonProperty("权利金收支表")
    private String S12;

    @ExcelProperty("期权履约后自动对冲持仓表")
    @JsonProperty("期权履约后自动对冲持仓表")
    private String S13;

    @ExcelProperty("保证金参数表")
    @JsonProperty("保证金参数表")
    private String S15;

    @ExcelProperty("期货持仓明细表")
    @JsonProperty("期货持仓明细表")
    private String S17;

    @ExcelProperty("期权持仓明细表")
    @JsonProperty("期权持仓明细表")
    private String S18;

    @ExcelProperty("交易组合持仓保留资格表")
    @JsonProperty("交易组合持仓保留资格表")
    private String S19;

    @ExcelProperty("交易组合持仓明细表")
    @JsonProperty("交易组合持仓明细表")
    private String S20;

    @ExcelProperty("保证金优惠参数表")
    @JsonProperty("保证金优惠参数表")
    private String S21;

    @ExcelProperty("手续费参数表")
    @JsonProperty("手续费参数表")
    private String S23;

    @ExcelProperty("外汇汇率参数表")
    @JsonProperty("外汇汇率参数表")
    private String S25;

    @ExcelProperty("交割信息表")
    @JsonProperty("交割信息表")
    private String S26;

    @ExcelProperty("持仓汇总表")
    @JsonProperty("持仓汇总表")
    private String S27;

    @ExcelProperty("客户移仓明细表")
    @JsonProperty("客户移仓明细表")
    private String S35;

    @ExcelProperty("当月手续费汇总表")
    @JsonProperty("当月手续费汇总表")
    private String S41;

    @ExcelProperty("出入金明细表")
    @JsonProperty("出入金明细表")
    private String S42;

    @ExcelProperty("每日充抵资金变动明细表")
    @JsonProperty("每日充抵资金变动明细表")
    private String S43;

    @ExcelProperty("_每日充抵资金查询")
    @JsonProperty("_每日充抵资金查询")
    private String S44;

    @ExcelProperty("交易编码保证金表")
    @JsonProperty("交易编码保证金表")
    private String S45;

    @JsonProperty("整体时间")
    @ExcelProperty("整体时间")
    private Integer overallTime;


}