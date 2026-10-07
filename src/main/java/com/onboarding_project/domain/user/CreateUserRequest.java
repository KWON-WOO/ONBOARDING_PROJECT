package com.onboarding_project.domain.user;

import com.onboarding_project.common.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문 대소문자와 숫자만 사용 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max = 255, message = "비밀번호를 255자 이내로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
            message = "비밀번호는 8자 이상, 영문, 숫자, 특수문자를 최소 1개씩 포함해야합니다.")
    private String password;
    @NotNull(message = "가입권한을 기입해주세요.")
    private UserRole role;
}
