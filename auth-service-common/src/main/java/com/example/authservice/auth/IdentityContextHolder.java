package com.example.authservice.auth;

public final class IdentityContextHolder {

    private static final ThreadLocal<IdentityContext> CONTEXT = new ThreadLocal<>();

    private IdentityContextHolder() {
    }

    public static void set(IdentityContext identityContext) {
        CONTEXT.set(identityContext);
    }

    public static IdentityContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
