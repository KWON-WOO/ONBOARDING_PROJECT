package com.onboarding_project.domain.order;

import com.onboarding_project.common.BaseSoftDeleteEntity;
import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import com.onboarding_project.domain.menu.Menu;
import com.onboarding_project.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseSoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    // 동시 결제/취소 방지용 낙관적 락
    @Version
    private Long version;

    public Order(User user, Menu menu, Integer quantity, String address, Long totalPrice, OrderStatus status) {
        this.user = user;
        this.menu = menu;
        this.quantity = quantity;
        this.address = address;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // 본인 주문 여부 (CUSTOMER)
    public boolean isOrderedBy(String username) {
        return this.user.getUsername().equals(username);
    }

    // 본인 메뉴에 들어온 주문 여부 (OWNER)
    public boolean isMenuOwnedBy(String username) {
        return this.menu.getUser().getUsername().equals(username);
    }

    // 주문 취소 (주문 요청 상태에서만 가능)
    public void cancel() {
        if (!this.status.isCancelable()) {
            throw new CustomException(ErrorCode.ORDER_NOT_CANCELABLE);
        }
        this.status = OrderStatus.CANCELED;
    }

    // 사장님 주문 상태 변경 (허용된 전이만 가능)
    public void changeStatusByOwner(OrderStatus next) {
        if (!this.status.canOwnerChangeTo(next)) {
            throw new CustomException(ErrorCode.INVALID_ORDER_STATUS_CHANGE);
        }
        this.status = next;
    }

    // 결제 완료 처리 (주문 요청 상태에서만 가능)
    public void pay() {
        if (!this.status.isPayable()) {
            throw new CustomException(ErrorCode.ORDER_NOT_PAYABLE);
        }
        this.status = OrderStatus.PAID;
    }
}
