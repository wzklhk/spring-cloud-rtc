package com.example.common.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author wzklhk
 */
@Data
public class ResultInfo<T> {

    @ApiModelProperty(value = "结果状态码")
    private Integer code;
    public static final String CODE = "code";

    @ApiModelProperty(value = "提示信息")
    private String msg;
    public static final String MSG = "msg";

    @ApiModelProperty(value = "返回结果")
    private T data;
    public static final String DATA = "data";

    public ResultInfo() {
    }

    public ResultInfo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResultInfo(ErrorCodeEnum e, T data) {
        this.code = e.getErrorCode();
        this.msg = e.getErrorMsg();
        this.data = data;
    }

    private ResultInfo(HttpStatus e, T data) {
        this.code = e.value();
        this.msg = e.getReasonPhrase();
        this.data = data;
    }

    public static <T> ResultInfo<T> status(ErrorCodeEnum code, T data) {
        return new ResultInfo<>(code, data);
    }

    public static <T> ResultInfo<T> status(HttpStatus code, T data) {
        return new ResultInfo<>(code, data);
    }

    public static <T> ResultInfo<T> error() {
        return new ResultInfo<>(ErrorCodeEnum.ERROR, null);
    }

    public static <T> ResultInfo<T> error(T data) {
        return new ResultInfo<>(ErrorCodeEnum.ERROR, data);
    }

    public static <T> ResultInfo<T> ok() {
        return new ResultInfo<>(ErrorCodeEnum.OK, null);
    }

    public static <T> ResultInfo<T> ok(T data) {
        return new ResultInfo<>(ErrorCodeEnum.OK, data);
    }
}
