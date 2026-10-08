package com.onboarding_project.domain.payment;

import com.onboarding_project.common.BaseEntity;
import com.onboarding_project.domain.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private Payment(Order order, PaymentType paymentType, Long amount, PaymentStatus paymentStatus) {
        this.order = order;
        this.paymentType = paymentType;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    // 결제 성공 내역 생성 (금액은 주문 총액에서 가져옴)
    public static Payment success(Order order, PaymentType paymentType) {
        return new Payment(order, paymentType, order.getTotalPrice(), PaymentStatus.SUCCESS);
    }
}
