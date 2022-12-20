package com.example.common.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author wzklhk
 */
@Data
public class CommonResultInfo<T> {

    @ApiModelProperty(value = "结果状态码")
    private Integer code;
    public static final String CODE = "code";

    @ApiModelProperty(value = "提示信息")
    private String msg;
    public static final String MSG = "msg";

    @ApiModelProperty(value = "返回结果")
    private T data;
    public static final String DATA = "data";

    public CommonResultInfo() {
    }

    public CommonResultInfo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private CommonResultInfo(ErrorCodeEnum e, T data) {
        this.code = e.getErrorCode();
        this.msg = e.getErrorMsg();
        this.data = data;
    }

    private CommonResultInfo(HttpStatus e, T data) {
        this.code = e.value();
        this.msg = e.getReasonPhrase();
        this.data = data;
    }

    public static <T> CommonResultInfo<T> status(ErrorCodeEnum code, T data) {
        return new CommonResultInfo<>(code, data);
    }

    public static <T> CommonResultInfo<T> status(HttpStatus code, T data) {
        return new CommonResultInfo<>(code, data);
    }

    public static <T> CommonResultInfo<T> error() {
        return new CommonResultInfo<>(ErrorCodeEnum.ERROR, null);
    }

    public static <T> CommonResultInfo<T> error(T data) {
        return new CommonResultInfo<>(ErrorCodeEnum.ERROR, data);
    }

    public static <T> CommonResultInfo<T> ok() {
        return new CommonResultInfo<>(ErrorCodeEnum.OK, null);
    }

    public static <T> CommonResultInfo<T> ok(T data) {
        return new CommonResultInfo<>(ErrorCodeEnum.OK, data);
    }
}
