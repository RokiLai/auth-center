package com.example.authcenter.domain.identity.service;

import com.example.authcenter.domain.identity.model.context.SessionRevocationPlan;

public interface SessionDomainService {

    SessionRevocationPlan logout(Long accountId, String currentSessionId);
}
