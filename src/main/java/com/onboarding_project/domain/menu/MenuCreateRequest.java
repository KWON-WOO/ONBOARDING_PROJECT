package com.onboarding_project.domain.menu;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuCreateRequest {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    @Size(min = 1, max = 100, message = "1자 이상 100자 이내로 입력해주세요.")
    private String name;

    @NotNull(message = "메뉴 가격을 입력해주세요.")
    @Min(value = 1, message = "가격은 1원 이상이어야 합니다.")
    @Max(value = 100000000, message = "가격은 1억 이하여야 합니다.")
    private Integer price;

    @NotBlank(message = "가격 설명을 입력해주세요.")
    @Size(max = 1000, message = "메뉴 설명은 1000자 이하로 작성해주세요.")
    private String description;
}
