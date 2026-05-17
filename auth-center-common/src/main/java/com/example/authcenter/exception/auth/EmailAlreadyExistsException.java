package com.example.authcenter.exception.auth;

import com.example.authcenter.exception.AuthErrorCode;

/**
 * 注册时邮箱已存在。
 */
public class EmailAlreadyExistsException extends AuthBusinessException {

    public EmailAlreadyExistsException() {
        super(AuthErrorCode.EMAIL_ALREADY_EXISTS);
    }
}
