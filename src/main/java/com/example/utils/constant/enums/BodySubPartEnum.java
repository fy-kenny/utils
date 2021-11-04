package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum BodySubPartEnum {

    HAND_THUMB("大拇指"),
    HAND_INDEX("食指"),
    HAND_MIDDLE("中指"),
    HAND_RING("无名指"),
    HAND_LITTLE("小拇指"),
    WRIST("腕部"),
    SHOULDER("肩"),
    ELBOW("肘"),
    KNEE("膝"),
    HIP("髋"),
    ANKLE("踝"),
    ;

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
