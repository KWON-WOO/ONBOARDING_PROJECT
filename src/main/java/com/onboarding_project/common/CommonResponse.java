package com.onboarding_project.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

    private final boolean success;

    private final String message;

    private final T data;

    private final LocalDateTime timestamp;

    // 성공 시 반환.
    public static <T> CommonResponse<T> success(SuccessMessage successMessage, T data) {
        return new CommonResponse<>(true, successMessage.getMessage(), data, LocalDateTime.now());
    }

    // 응답 데이터가 없는 건에 대한 성공 시 반환.
    public static <T> CommonResponse<Void> successNoData(SuccessMessage successMessage) {
        return new CommonResponse<>(true, successMessage.getMessage(), null, LocalDateTime.now());
    }

    // 예외처리 시
    public static CommonResponse<Void> exception(String errorMessage) {
        return new CommonResponse<>(false, errorMessage, null, LocalDateTime.now());
    }
}
