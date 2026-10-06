package com.onboarding_project.common;

public enum UserRole {
    CUSTOMER(Authority.CUSTOMER),
    OWNER(Authority.OWNER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String OWNER = "ROLE_OWNER";
    }
}
