package com.example.authcenter.domain.identity.service.impl;

import com.example.authcenter.domain.identity.model.context.SessionRevocationPlan;
import com.example.authcenter.domain.identity.repository.IdentitySessionRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionDomainServiceImplTest {

    private final IdentitySessionRepository identitySessionRepository = mock(IdentitySessionRepository.class);
    private final SessionDomainServiceImpl sessionDomainService = new SessionDomainServiceImpl(identitySessionRepository);

    @Test
    void logoutShouldOnlyDeleteBindingWhenItStillPointsToCurrentSession() {
        when(identitySessionRepository.findSessionIdByAccountId(1L)).thenReturn("session-1");

        SessionRevocationPlan plan = sessionDomainService.logout(1L, "session-1");

        assertThat(plan.sessionIdToDelete()).isEqualTo("session-1");
        assertThat(plan.accountIdBindingToDelete()).isEqualTo(1L);
    }

    @Test
    void logoutShouldKeepBindingWhenItAlreadyPointsToAnotherSession() {
        when(identitySessionRepository.findSessionIdByAccountId(1L)).thenReturn("session-2");

        SessionRevocationPlan plan = sessionDomainService.logout(1L, "session-1");

        assertThat(plan.sessionIdToDelete()).isEqualTo("session-1");
        assertThat(plan.accountIdBindingToDelete()).isNull();
    }
}
