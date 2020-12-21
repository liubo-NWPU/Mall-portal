package com.ai.website.common.api;

/**
 * 枚举了一些常用API操作码
 * 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(1000, "操作成功"),
    FAILED(1001, "操作失败"),
    VALIDATE_FAILED(1002, "参数检验失败"),
    UNAUTHORIZED(1003, "请登录"),
    FORBIDDEN(1004, "没有相关权限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
