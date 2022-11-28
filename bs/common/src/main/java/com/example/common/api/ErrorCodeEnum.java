package com.example.common.api;

/**
 * 返回码枚举类
 *
 * @author wzklhk
 */

public enum ErrorCodeEnum {
    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),

    /**
     * 操作失败
     */
    FAILURE(1, "操作失败");

    private final int errorCode;
    private final String errorMsg;

    ErrorCodeEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
