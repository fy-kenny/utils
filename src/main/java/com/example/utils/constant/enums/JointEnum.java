package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum JointEnum {

    METACARPOPHALANGEAL("掌指"),
    INTERPHALANGEAL("指间"),
    WRIST("腕部"),
    RULER("尺"),
    FLEX("挠"),
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
