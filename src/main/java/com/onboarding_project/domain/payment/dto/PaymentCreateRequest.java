package com.onboarding_project.domain.payment.dto;

import com.onboarding_project.domain.payment.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCreateRequest {

    @NotNull(message = "결제 수단을 입력해주세요.")
    private PaymentType paymentType;
}
