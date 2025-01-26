package com.lucky.newyear.config;

public class UserContext {
    private static final ThreadLocal<String> userUUIDThreadLocal = new ThreadLocal<>();

    public static void setUserUUID(String userUUID) {
        userUUIDThreadLocal.set(userUUID);
    }

    public static String getUserUUID() {
        return userUUIDThreadLocal.get();
    }

    public static void clear() {
        userUUIDThreadLocal.remove();
    }
}