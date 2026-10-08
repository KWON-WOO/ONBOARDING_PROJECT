package com.onboarding_project.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    REQUESTED("주문 요청"),
    PAID("결제 완료"),
    ACCEPTED("주문 수락"),
    DELIVERED("배달 완료"),
    CANCELED("주문 취소"),
    FAILED("주문 실패");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    // 소비자 취소 가능 여부: 주문 요청 상태에서만 가능
    public boolean isCancelable() {
        return this == REQUESTED;
    }

    // 판매자 상태 변경 허용
    public boolean canOwnerChangeTo(OrderStatus next) {
        return switch (this) {
            case PAID -> next == ACCEPTED;
            case ACCEPTED -> next == DELIVERED;
            default -> false;
        };
    }

    // 결제 가능 여부: 주문 요청 상태에서만 가능
    public boolean isPayable() {
        return this == REQUESTED;
    }
}
