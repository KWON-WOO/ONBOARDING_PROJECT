package com.onboarding_project.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<Void>> handleException(CustomException e) {
        log.error("예외 발생. ", e);

        CommonResponse<Void> response = CommonResponse.exception(e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> exception(Exception e) {
        log.error("예외 발생. ", e);

        CommonResponse<Void> response = CommonResponse.exception("요청 본문 형식이 올바르지 않습니다");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
