package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum SideEnum {

    LEFT("左"),
    RIGHT("右");

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
