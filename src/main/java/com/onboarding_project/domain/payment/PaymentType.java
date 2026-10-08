package com.onboarding_project.domain.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum PaymentType {
    CARD(true),
    CASH(false);   // 추후 지원 예정

    private final boolean supported;

    PaymentType(boolean supported) {
        this.supported = supported;
    }

    public boolean isSupported() {
        return supported;
    }

    // 존재하지 않는 결제 수단 → 400 (대소문자 무시)
    @JsonCreator
    public static PaymentType from(String value) {
        for (PaymentType type : PaymentType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new CustomException(ErrorCode.INVALID_PAYMENT_TYPE);
    }
}
