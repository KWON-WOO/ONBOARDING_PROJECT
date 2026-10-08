package com.onboarding_project.domain.order;

import com.onboarding_project.common.CommonResponse;
import com.onboarding_project.common.SuccessMessage;
import com.onboarding_project.domain.order.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<CommonResponse<OrderCreateResponse>> createOrder(@AuthenticationPrincipal String username,
                                                                           @Valid @RequestBody OrderCreateRequest request) {
        OrderCreateResponse response = orderService.createOrder(username, request);

        CommonResponse<OrderCreateResponse> result = CommonResponse.success(SuccessMessage.ORDER_CREATE_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/order")
    public ResponseEntity<CommonResponse<List<OrderResponse>>> getOrderList(@AuthenticationPrincipal String username) {
        List<OrderResponse> response = orderService.getOrderList(username);

        CommonResponse<List<OrderResponse>> result = CommonResponse.success(SuccessMessage.ORDER_LIST_GET_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/order/{orderId}/cancel")
    public ResponseEntity<CommonResponse<OrderStatusResponse>> cancelOrder(@AuthenticationPrincipal String username,
                                                                           @PathVariable Long orderId) {
        OrderStatusResponse response = orderService.cancelOrder(username, orderId);

        CommonResponse<OrderStatusResponse> result = CommonResponse.success(SuccessMessage.ORDER_CANCEL_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/order/{orderId}/status")
    public ResponseEntity<CommonResponse<OrderStatusResponse>> changeOrderStatus(@AuthenticationPrincipal String username,
                                                                                 @PathVariable Long orderId,
                                                                                 @Valid @RequestBody OrderStatusUpdateRequest request) {
        OrderStatusResponse response = orderService.changeOrderStatus(username, orderId, request);

        CommonResponse<OrderStatusResponse> result = CommonResponse.success(SuccessMessage.ORDER_STATUS_UPDATE_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
