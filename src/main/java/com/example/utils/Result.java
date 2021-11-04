package com.example.utils;

import com.example.utils.constant.enums.ExceptionEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author Kenny Fang
 * @since 1.1.0
 */
@ApiModel("通用返回结果")
@AllArgsConstructor
@Builder
@Value
public class Result<T> {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final String MSG_SUCCESS = "success";
    public static final String MSG_FAIL = "fail";

    @ApiModelProperty(value = "代码", required = true)
    private Integer code;
    @ApiModelProperty(value = "消息", required = true)
    private String msg;
    @ApiModelProperty(value = "数据", required = true)
    private T data;
//    private Function function;

//    public <I, O> Result (T data, Function<I, O> function) {
//        this.code = SUCCESS;
//        this.msg = MSG_SUCCESS;
//        this.data = data;
//        this.function = function;
//    }

    public Result(T data, Page page) {
        this.code = SUCCESS;
        this.msg = MSG_SUCCESS;
//        this.data = data;
//        this.function = null;

        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList((List) data);
        this.data = (T) pageInfo;
    }

//    public Result toPageInfoResult() {
//        List dataList = (List) this.getData();
//        List list = new ArrayList(dataList.size());
//        for (Object input : dataList) {
//            list.add(function.apply(input));
//        }
//
//        Page page = (Page)this.getData();
//        PageInfo pageInfo = page.toPageInfo();
//        pageInfo.setList(list);
//
//       return Result.out(this.getCode(), this.getMsg(), pageInfo);
//    }

//    public Result toPageInfoResultByPage() {
//
//        PageInfo pageInfo = page.toPageInfo();
//        pageInfo.setList((List) this.getData());
//
//        return Result.out(this.getCode(), this.getMsg(), pageInfo);
//    }

    public Result() {
        this.code = SUCCESS;
        this.msg = MSG_SUCCESS;
        this.data = null;
//        this.function = null;
    }

    public Result(T data) {
        this.code = SUCCESS;
        this.msg = MSG_SUCCESS;
        this.data = data;
//        this.function = null;
    }

    public Result(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.code();
        this.msg = exceptionEnum.msg();
        this.data = null;
//        this.function = null;
    }

    public Result(Throwable e) {
        this.code = FAIL;
        this.msg = getExceptionMsg(e);
        this.data = null;
//        this.function = null;
    }


    public static Result ok() {
        return success();
    }


    public static <T> Result<T> out(int code, String msg, T data) {

        return Result.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static Result success() {

        return Result.builder()
                .code(SUCCESS)
                .msg(MSG_SUCCESS)
                .build();
    }

    /**
     * return a success {@link Result}, if data is {@link Page} instance will cast to {@link PageInfo}.
     * @param data which will be write to
     * @param <T> the data real type
     * @return success result
     */
    public static <T> Result<T> success(T data) {

        if(data instanceof Page) {
            data = (T) ((Page)data).toPageInfo();
        }

        return Result.<T>builder()
                .code(SUCCESS)
                .msg(MSG_SUCCESS)
                .data(data)
                .build();
    }

//    public static <T> Result<T> success(T data, Function function) {
//
//        return Result.<T>builder()
//                .code(SUCCESS)
//                .msg(MSG_SUCCESS)
//                .data(data)
//                .function(function)
//                .build();
//    }

    public static <T> Result<T> success(T data, Page page) {

        return new Result<>(data, page);
    }

    public static Result success(String msg) {
        return success(SUCCESS, msg);
    }
//    public static Result success(Page page) {
//        return success(page.toPageInfo());
//    }

    public static Result success(int code, String msg) {
        return Result.out(code, msg, null);
    }

    public static <T> Result<T> success(int code, String msg, T data) {
        return Result.out(code, msg, data);
    }

    public static <T> Result<T> success(T data, String msg) {
        return Result.out(SUCCESS, msg, data);
    }

    public static Result fail() {

        return Result.builder()
                .code(FAIL)
                .msg(MSG_FAIL)
                .build();
    }

    public static <T> Result<T> fail(int code) {

        return Result.<T>builder()
                .code(code)
                .msg(MSG_FAIL)
                .build();
    }

    public static <T> Result<T> fail(T data) {

        return Result.<T>builder()
                .code(FAIL)
                .msg(MSG_FAIL)
                .data(data)
                .build();
    }

    public static Result fail(Throwable e) {

        return Result.builder()
                .code(FAIL)
                .msg(getExceptionMsg(e))
                .build();
    }

    public static Result fail(String msg) {
        return fail(FAIL, msg);
    }

    public static Result fail(ExceptionEnum exceptionEnum) {
        return new Result(exceptionEnum);
    }

    public static Result fail(int code, String msg) {
        return Result.out(code, msg, null);
    }

    public static <T> Result<T> fail(int code, String msg, T data) {
        return Result.out(code, msg, data);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return Result.out(FAIL, msg, data);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return 0 == this.getCode();
    }

    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

    private static String getExceptionMsg(Throwable e) {
        String exceptionMsg;

        exceptionMsg = e.getLocalizedMessage();
        if (null == exceptionMsg || exceptionMsg.isEmpty()) {
            exceptionMsg = e.getMessage();
        }
        if (null == exceptionMsg || exceptionMsg.isEmpty()) {
            exceptionMsg = e.toString();
        }

        return exceptionMsg;
    }

}
