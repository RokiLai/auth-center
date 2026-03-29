package com.example.authservice.exception.auth;

import com.example.authservice.exception.AuthErrorCode;

/**
 * 用户名不存在或密码不匹配时抛出。
 */
public class AuthenticationFailedException extends AuthBusinessException {

    public AuthenticationFailedException() {
        super(AuthErrorCode.LOGIN_FAILED);
    }
}
