package com.example.service.common.api;

public enum ErrorCodeEnum {
    SUCCESS(0, "操作成功"),
    FAILURE(1, "操作失败");

    private int errorCode;
    private String errorMsg;

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
