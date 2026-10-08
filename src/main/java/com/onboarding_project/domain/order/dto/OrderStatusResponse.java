package com.onboarding_project.domain.order.dto;

import com.onboarding_project.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderStatusResponse {
    private final Long orderId;
    private final OrderStatus status;
    private final String statusDescription;
}
