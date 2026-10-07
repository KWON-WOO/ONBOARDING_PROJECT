package com.onboarding_project.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInUserResponse {
    private final String accessToken;
}
