package com.onboarding_project.domain.order;

import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import com.onboarding_project.domain.menu.Menu;
import com.onboarding_project.domain.menu.MenuRepository;
import com.onboarding_project.domain.order.dto.*;
import com.onboarding_project.domain.user.User;
import com.onboarding_project.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    // 주문 생성 (CUSTOMER)
    @Transactional
    public OrderCreateResponse createOrder(String username, OrderCreateRequest request) {

        User customer = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        Menu menu = menuRepository.findByIdAndIsDeletedFalse(request.getMenuId()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MENU)
        );

        Long totalPrice = (long) menu.getPrice() * request.getQuantity();
        OrderStatus status = OrderStatus.REQUESTED;

        Order order = new Order(customer, menu, request.getQuantity(),
                request.getAddress(), totalPrice, status);

        Order savedOrder = orderRepository.save(order);

        return new OrderCreateResponse(
                savedOrder.getId(),
                savedOrder.getMenu().getId(),
                savedOrder.getMenu().getName(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                savedOrder.getAddress(),
                savedOrder.getStatus(),
                savedOrder.getCreatedAt()
        );
    }

    // 주문 조회 (CUSTOMER & OWNER)
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrderList(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        List<Order> orderList = switch (user.getRole()) {
            case CUSTOMER -> orderRepository.findAllByUserAndIsDeletedFalseOrderByIdDesc(user);
            case OWNER -> orderRepository.findAllByMenuUserAndIsDeletedFalseOrderByIdDesc(user);
        };

        return orderList.stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getMenu().getId(),
                        order.getMenu().getName(),
                        order.getUser().getUsername(),
                        order.getQuantity(),
                        order.getTotalPrice(),
                        order.getAddress(),
                        order.getStatus(),
                        order.getCreatedAt()
                ))
                .toList();
    }

    // 주문 취소 (CUSTOMER)
    @Transactional
    public OrderStatusResponse cancelOrder(String username, Long orderId) {
        Order order = findOrder(orderId);

        if (!order.isOrderedBy(username)) {
            throw new CustomException(ErrorCode.NOT_ORDER_OWNER);
        }

        order.cancel();

        return new OrderStatusResponse(order.getId(), order.getStatus(), order.getStatus().getDescription());
    }

    // 주문 상태 변경 (OWNER)
    @Transactional
    public OrderStatusResponse changeOrderStatus(String username, Long orderId, OrderStatusUpdateRequest request) {
        Order order = findOrder(orderId);

        if (!order.isMenuOwnedBy(username)) {
            throw new CustomException(ErrorCode.NOT_MENU_ORDER_OWNER);
        }

        order.changeStatusByOwner(request.getStatus());

        return new OrderStatusResponse(order.getId(), order.getStatus(), order.getStatus().getDescription());
    }

    // 삭제되지 않은 주문 조회
    private Order findOrder(Long orderId) {
        return orderRepository.findByIdAndIsDeletedFalse(orderId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_ORDER)
        );
    }
}
