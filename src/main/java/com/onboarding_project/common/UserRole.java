package com.onboarding_project.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserRole {
    CUSTOMER, OWNER;

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
