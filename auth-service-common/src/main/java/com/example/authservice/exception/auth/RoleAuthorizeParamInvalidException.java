package com.example.authservice.exception.auth;

import com.example.authservice.exception.AuthErrorCode;

/**
 * 角色授权缺少必要参数时抛出。
 */
public class RoleAuthorizeParamInvalidException extends AuthBusinessException {

    public RoleAuthorizeParamInvalidException() {
        super(AuthErrorCode.ROLE_AUTHORIZE_PARAM_INVALID);
    }
}
