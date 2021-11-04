package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum StatusEnum {

    DISABLED("停用"),
    ENABLED("启用");

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }

}
