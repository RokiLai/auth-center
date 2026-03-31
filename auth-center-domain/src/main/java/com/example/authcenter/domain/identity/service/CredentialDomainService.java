package com.example.authcenter.domain.identity.service;

import com.example.authcenter.domain.identity.model.context.PasswordChangeDecision;

public interface CredentialDomainService {

    PasswordChangeDecision changePassword(Long accountId,
                                          String username,
                                          String currentSessionId,
                                          String oldPassword,
                                          String newPassword);
}
