package com.onboarding_project.domain.payment;

import com.onboarding_project.common.CommonResponse;
import com.onboarding_project.common.SuccessMessage;
import com.onboarding_project.domain.payment.dto.PaymentCreateRequest;
import com.onboarding_project.domain.payment.dto.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/order/{orderId}/payment")
    public ResponseEntity<CommonResponse<PaymentResponse>> pay(@AuthenticationPrincipal String username,
                                                               @PathVariable Long orderId,
                                                               @Valid @RequestBody PaymentCreateRequest request) {
        PaymentResponse response = paymentService.pay(username, orderId, request);

        CommonResponse<PaymentResponse> result = CommonResponse.success(SuccessMessage.PAYMENT_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
