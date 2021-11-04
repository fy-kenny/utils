package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum ActionEnum {

    BEND("屈曲"),
    STRETCH("伸展"),
    RULER("尺屈"),
    FLEX("挠曲"),
    BEND_FORWARD("前屈"),
    STRETCH_BACKWARD("后伸"),
    OUTREACH("外展"),
    ADDUCTION("内收"),
    EXTERNAL_ROTATION("外旋"),
    PRONATION("内旋"),
    HORIZONTAL_BEND("水平屈曲"),
    HORIZONTAL_STRETCH("水平伸展"),
    BEND_KNEE("前屈（屈膝）"),
    SQUATANDSTAND("蹲立"),
    SITANDSTAND("坐立"),
    PRONATION2("旋前"),
    SUPINATION("旋后"),
    INVERSION("内翻"),
    EVERSION("外翻"),
    ;

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
