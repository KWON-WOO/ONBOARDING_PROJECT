package com.onboarding_project.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum UserRole {
    CUSTOMER(Authority.CUSTOMER), OWNER(Authority.OWNER);

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String OWNER = "ROLE_OWNER";
    }

    // 처리할 수 없는 UserRole이 들어올 시 예외처리 발생.
    @JsonCreator
    public static UserRole from(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new CustomException(ErrorCode.MISMATCH_USER_ROLE);
    }
}
