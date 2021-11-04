package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum GenderEnum {

    UNKNOWN("未知"),
    MALE("男"),
    FEMALE("女");

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
