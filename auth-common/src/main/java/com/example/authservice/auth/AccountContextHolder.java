package com.example.authservice.auth;

public class AccountContextHolder {
    private static final ThreadLocal<AccountInfo> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(AccountInfo accountInfo) {
        USER_THREAD_LOCAL.set(accountInfo);
    }

    public static AccountInfo get() {
        return USER_THREAD_LOCAL.get();
    }

    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}
