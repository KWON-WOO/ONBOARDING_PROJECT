package com.onboarding_project.domain.order;

import com.onboarding_project.common.BaseSoftDeleteEntity;
import com.onboarding_project.domain.menu.Menu;
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
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private String quantity;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private OrderStatus status;

}
