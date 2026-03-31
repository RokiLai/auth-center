package com.example.authcenter.domain.identity.model.context;

import com.example.authcenter.domain.identity.model.entity.IdentityAccount;

/**
 * 表达改密后的领域决策结果。
 */
public record PasswordChangeDecision(
        IdentityAccount account,
        SessionRevocationPlan sessionRevocationPlan
) {
}
