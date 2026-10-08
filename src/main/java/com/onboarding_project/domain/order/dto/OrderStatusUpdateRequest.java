package com.onboarding_project.domain.order.dto;

import com.onboarding_project.domain.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequest {

    @NotNull(message = "변경할 주문 상태를 입력해주세요.")
    private OrderStatus status;
}
