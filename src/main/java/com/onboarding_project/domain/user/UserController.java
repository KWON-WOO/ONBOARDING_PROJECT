package com.onboarding_project.domain.user;

import com.onboarding_project.common.CommonResponse;
import com.onboarding_project.domain.user.dto.CreateUserRequest;
import com.onboarding_project.domain.user.dto.CreateUserResponse;
import com.onboarding_project.domain.user.dto.SignInUserRequest;
import com.onboarding_project.domain.user.dto.SignInUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.onboarding_project.common.SuccessMessage.*;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<CreateUserResponse>> signUp(@Valid @RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.signUp(request);

        CommonResponse<CreateUserResponse> result = CommonResponse.success(SIGN_UP_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<SignInUserResponse>> signIn(@Valid @RequestBody SignInUserRequest request) {
        SignInUserResponse response = userService.signIn(request);
        CommonResponse<SignInUserResponse> result = CommonResponse.success(SIGN_IN_SUCCESS, response);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
