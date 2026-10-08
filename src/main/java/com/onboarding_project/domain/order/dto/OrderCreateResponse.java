package com.onboarding_project.domain.order.dto;

import com.onboarding_project.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderCreateResponse {
    private final Long orderId;
    private final Long menuId;
    private final String menuName;
    private final Integer quantity;
    private final Long totalPrice;
    private final String address;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
}
