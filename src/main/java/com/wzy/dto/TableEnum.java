package com.wzy.dto;

public enum TableEnum {
    MEMBER("会员"),
    DEAL("成交表"),
    POSI("持仓表");

    private String value;

    TableEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
