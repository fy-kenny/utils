package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum MediaTypeEnum {

    AUDIO("音频"),
    VIDEO("视频"),
    ACTION("动作");

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
