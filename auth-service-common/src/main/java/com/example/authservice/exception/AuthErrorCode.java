package com.example.authservice.exception;

import com.roki.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
    USERNAME_ALREADY_EXISTS(44001, "用户名已存在"),
    USERNAME_REQUIRED(44002, "用户名不能为空"),
    PASSWORD_TOO_SHORT(44003, "密码长度不能少于6位"),
    EMAIL_INVALID(44004, "邮箱格式不正确"),
    OLD_PASSWORD_INCORRECT(44005, "旧密码错误"),
    ROLE_AUTHORIZE_PARAM_INVALID(44006, "角色授权参数不能为空"),
    LOGIN_FAILED(44101, "用户名或密码错误"),
    TOKEN_MISSING(44102, "缺少Token，请先登录"),
    TOKEN_EXPIRED(44103, "Token已过期，请重新登录"),
    TOKEN_INVALID(44104, "Token无效"),
    JSON_PROCESS_ERROR(44901, "JSON处理失败");

    private final int code;
    private final String message;

    AuthErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
