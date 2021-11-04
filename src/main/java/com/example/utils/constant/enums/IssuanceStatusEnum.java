package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum IssuanceStatusEnum {

    NOT_ISSUED("未签发"),
    ISSUED("已签发");

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
