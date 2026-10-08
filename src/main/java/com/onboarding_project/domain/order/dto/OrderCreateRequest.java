package com.onboarding_project.domain.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {

    @NotNull(message = "주문할 메뉴를 선택해주세요.")
    private Long menuId;
    @NotNull(message = "수량을 입력해주세요.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer quantity;

    @NotBlank(message = "배송 주소를 입력해주세요.")
    @Size(max = 255, message = "배송 주소는 255자 이하로 입력해주세요.")
    private String address;
}
