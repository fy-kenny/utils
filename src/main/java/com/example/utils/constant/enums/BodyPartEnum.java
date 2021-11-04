package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum BodyPartEnum {

    FIVE_FINGERS("五指"),
    WRIST("腕部"),
    SHOULDER("肩"),
    ELBOW("肘"),
    KNEE("膝"),
    HIP("髋"),
    // 动捕
    MOTION_UPPER_LIMB_WRIST("动捕上肢腕部"),
    MOTION_UPPER_LIMB_SHOULDER("动捕上肢肩"),
    MOTION_UPPER_LIMB_ELBOW("动捕上肢肘"),
    MOTION_LOWER_LIMB_DIFFICULTY1("动捕下肢难度1"),
    MOTION_LOWER_LIMB_DIFFICULTY2("动捕下肢难度2"),
    MOTION_LOWER_LIMB_DIFFICULTY3("动捕下肢难度3"),
    MOTION_STEPS("动捕步态分析训练"),
    MOTION_MULTI("动捕多人"),
    ANKLE("踝"),
    ;

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }

}
