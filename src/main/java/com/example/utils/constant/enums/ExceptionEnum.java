package com.example.utils.constant.enums;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
@AllArgsConstructor
public enum ExceptionEnum {

    UNKNOWN(1, "未知错误"),
    ARGUMENT_INVALID(4000, "参数无效"),
    TOKEN_INVALID(4010, "TOKEN验证失败"),
    LOGIN(4030, "账号或密码错误"),
    ACCOUNT_NOT_EXISTED(4040, "账号不存在"),
    DATA_NOT_EXISTED(4041, "数据不存在"),
    ACCOUNT_EXISTED(4090, "账号已经存在"),
    ;

    private static final Map<Integer, ExceptionEnum> EXCEPTION_CODE_MAP = Maps.newHashMap();

    static {
        for (ExceptionEnum exceptionEnum : values()) {
            EXCEPTION_CODE_MAP.put(exceptionEnum.code(), exceptionEnum);
        }
    }

    private Integer code;
    private String msg;

    public Integer code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

    public ExceptionEnum codeOf(Integer code) {
        return EXCEPTION_CODE_MAP.get(code);
    }
}
