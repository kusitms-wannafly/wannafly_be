package com.kusitms.wannafly.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    MEMBER_DUPLICATE_EMAIL(1001, 400, "이미 가입된 사용자의 이메일입니다.");

    private final int value;
    private final int httpStatusCode;
    private final String message;

    ErrorCode(int value, int httpStatusCode, String message) {
        this.value = value;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
