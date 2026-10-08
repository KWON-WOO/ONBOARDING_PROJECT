package com.onboarding_project.common;

import lombok.Getter;

@Getter
public enum SuccessMessage {
    SIGN_UP_SUCCESS("회원가입 성공"),
    SIGN_UP_OWNER_SUCCESS("판매자 회원가입 성공"),
    SIGN_IN_SUCCESS("로그인 성공"),
    MENU_CREATE_SUCCESS("메뉴 생성 성공"),
    MENU_GET_SUCCESS("메뉴 조회 성공"),
    MENU_LIST_GET_SUCCESS("메뉴 목록 조회 성공"),
    ;

    private final String message;

    SuccessMessage(String message) {
        this.message = message;
    }
}
