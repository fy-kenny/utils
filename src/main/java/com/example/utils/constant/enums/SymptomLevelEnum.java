package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum SymptomLevelEnum {

    NORMAL("正常"),
    I("I型"),
    II("II型"),
    III("III型");

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
