package com.example.authservice.exception.auth;

import com.example.authservice.exception.AuthErrorCode;

/**
 * 受保护接口缺少 Authorization 头时抛出。
 */
public class TokenMissingException extends AuthBusinessException {

    public TokenMissingException() {
        super(AuthErrorCode.TOKEN_MISSING);
    }
}
