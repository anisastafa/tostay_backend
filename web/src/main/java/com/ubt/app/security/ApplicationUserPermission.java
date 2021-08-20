package com.ubt.app.security;

public enum ApplicationUserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    HOST_READ("host:read"),
    HOST_WRITE("host:write"),
    APARTMENT_READ("apartment:read"),
    APARTMENT_WRITE("apartment:write");


    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
