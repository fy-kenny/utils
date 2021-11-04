package com.example.utils.constant.enums;

import lombok.AllArgsConstructor;

/**
 *
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum WorkingStatusEnum {

    OFFLINE("离线"),
    ONLINE("在线"),
    IDLE("空闲"),
    RUNNING("运行中"),
    ERROR("异常"),
    ;

    private String nameCN;

    public String nameCN() {
        return this.nameCN;
    }
}
