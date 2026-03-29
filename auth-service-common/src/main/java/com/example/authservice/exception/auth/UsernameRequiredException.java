package com.example.authservice.exception.auth;

import com.example.authservice.exception.AuthErrorCode;

/**
 * 创建账号时用户名为空。
 */
public class UsernameRequiredException extends AuthBusinessException {

    public UsernameRequiredException() {
        super(AuthErrorCode.USERNAME_REQUIRED);
    }
}
