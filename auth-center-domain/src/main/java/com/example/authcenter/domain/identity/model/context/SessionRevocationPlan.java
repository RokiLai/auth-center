package com.example.authcenter.domain.identity.model.context;

/**
 * 描述一次身份安全动作后需要撤销的会话范围。
 */
public record SessionRevocationPlan(
        String sessionIdToDelete,
        Long accountIdBindingToDelete
) {

    public static SessionRevocationPlan none() {
        return new SessionRevocationPlan(null, null);
    }
}
