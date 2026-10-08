package com.onboarding_project.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    MISMATCH_USER_ROLE(HttpStatus.BAD_REQUEST, "입력하신 권한은 유효하지 않은 값 입니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "등록된 사용자가 없습니다."),

    MISMATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    ONLY_OWNER_CAN_CREATE_MENU(HttpStatus.FORBIDDEN, "메뉴 등록은 판매자만 가능합니다."),

    INVALID_TOKEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴입니다."),

    NOT_MENU_OWNER(HttpStatus.FORBIDDEN, "본인의 메뉴만 수정 및 삭제할 수 있습니다."),

    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),

    NOT_ORDER_OWNER(HttpStatus.FORBIDDEN, "본인의 주문만 취소할 수 있습니다."),

    NOT_MENU_ORDER_OWNER(HttpStatus.FORBIDDEN, "본인 메뉴에 들어온 주문만 변경할 수 있습니다."),

    ORDER_NOT_CANCELABLE(HttpStatus.CONFLICT, "주문 요청 상태에서만 취소할 수 있습니다."),

    INVALID_ORDER_STATUS_CHANGE(HttpStatus.CONFLICT, "허용되지 않는 주문 상태 변경입니다."),

    NOT_PAYMENT_ORDER_OWNER(HttpStatus.FORBIDDEN, "본인의 주문만 결제할 수 있습니다."),

    INVALID_PAYMENT_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 수단입니다."),

    UNSUPPORTED_PAYMENT_TYPE(HttpStatus.BAD_REQUEST, "카드 결제만 가능합니다."),

    ORDER_NOT_PAYABLE(HttpStatus.CONFLICT, "주문 요청 상태에서만 결제할 수 있습니다."),

    ORDER_CONFLICT(HttpStatus.CONFLICT, "다른 요청에 의해 주문이 변경되었습니다. 다시 시도해주세요."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
