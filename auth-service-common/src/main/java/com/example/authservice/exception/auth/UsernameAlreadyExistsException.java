package com.example.authservice.exception.auth;

import com.example.authservice.exception.AuthErrorCode;

/**
 * 注册时用户名已存在。
 */
public class UsernameAlreadyExistsException extends AuthBusinessException {

    public UsernameAlreadyExistsException() {
        super(AuthErrorCode.USERNAME_ALREADY_EXISTS);
    }
}
