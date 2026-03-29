package com.example.authservice.exception.auth;

import com.example.authservice.exception.AuthErrorCode;

/**
 * 注册或改密时密码长度不满足最小要求。
 */
public class PasswordTooShortException extends AuthBusinessException {

    public PasswordTooShortException() {
        super(AuthErrorCode.PASSWORD_TOO_SHORT);
    }
}
