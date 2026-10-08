package com.onboarding_project.domain.payment;

import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import com.onboarding_project.domain.order.Order;
import com.onboarding_project.domain.order.OrderRepository;
import com.onboarding_project.domain.payment.dto.PaymentCreateRequest;
import com.onboarding_project.domain.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public PaymentResponse pay(String username, Long orderId, PaymentCreateRequest request) {
        // 1. 주문 조회 (없거나 삭제된 주문 → 404)
        Order order = orderRepository.findByIdAndIsDeletedFalse(orderId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_ORDER)
        );

        // 2. 본인 주문 확인 → 403
        if (!order.isOrderedBy(username)) {
            throw new CustomException(ErrorCode.NOT_PAYMENT_ORDER_OWNER);
        }

        // 3. 결제 수단 확인 (카드만 허용) → 400
        PaymentType paymentType = request.getPaymentType();
        if (!paymentType.isSupported()) {
            throw new CustomException(ErrorCode.UNSUPPORTED_PAYMENT_TYPE);
        }

        // 4. 주문 상태 확인 및 결제 완료 처리 (주문 요청 상태가 아니면 → 409)
        order.pay();

        // 5. 결제 내역 저장 (금액은 주문 총액)
        Payment payment = paymentRepository.save(Payment.success(order, paymentType));

        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPaymentType(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getOrder().getStatus(),
                payment.getCreatedAt()
        );
    }
}
