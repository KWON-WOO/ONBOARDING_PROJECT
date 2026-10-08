package com.onboarding_project.domain.payment.dto;

import com.onboarding_project.domain.order.OrderStatus;
import com.onboarding_project.domain.payment.PaymentStatus;
import com.onboarding_project.domain.payment.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentResponse {
    private final Long paymentId;
    private final Long orderId;
    private final PaymentType paymentType;
    private final Long amount;
    private final PaymentStatus paymentStatus;
    private final OrderStatus orderStatus;
    private final LocalDateTime paidAt;
}
