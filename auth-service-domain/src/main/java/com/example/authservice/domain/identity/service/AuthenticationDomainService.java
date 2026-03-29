package com.example.authservice.domain.identity.service;

import com.example.authservice.domain.identity.model.result.AuthenticationResult;

public interface AuthenticationDomainService {

    /**
     * 完成登录认证的核心领域规则，并产出新的登录会话。
     */
    AuthenticationResult authenticate(String username, String password);
}
