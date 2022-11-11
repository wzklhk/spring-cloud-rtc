package com.example.service.common.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResultInfo<T> {

    @ApiModelProperty(value = "结果状态码")
    private Integer code;
    @ApiModelProperty(value = "提示信息")
    private String msg;
    @ApiModelProperty(value = "返回结果")
    private T data;

    public ResultInfo() {
    }

    public ResultInfo(ErrorCodeEnum e, T data) {
        this.code = e.getErrorCode();
        this.msg = e.getErrorMsg();
        this.data = data;
    }

    public ResultInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public static <T> ResultInfo<T> status(ErrorCodeEnum code, T data) {
        return new ResultInfo<>(code, data);
    }

    public static <T> ResultInfo<T> error() {
        return new ResultInfo<>(ErrorCodeEnum.FAILURE, null);
    }

    public static <T> ResultInfo<T> error(T data) {
        return new ResultInfo<>(ErrorCodeEnum.FAILURE, data);
    }

    public static <T> ResultInfo<T> ok() {
        return new ResultInfo<>(ErrorCodeEnum.SUCCESS, null);
    }

    public static <T> ResultInfo<T> ok(T data) {
        return new ResultInfo<>(ErrorCodeEnum.SUCCESS, data);
    }
}
