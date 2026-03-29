package com.example.authservice.exception.auth;

import com.example.authservice.exception.AuthErrorCode;

/**
 * 修改密码时旧密码校验失败。
 */
public class OldPasswordIncorrectException extends AuthBusinessException {

    public OldPasswordIncorrectException() {
        super(AuthErrorCode.OLD_PASSWORD_INCORRECT);
    }
}
