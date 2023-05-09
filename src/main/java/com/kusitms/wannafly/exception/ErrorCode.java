package com.kusitms.wannafly.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    MEMBER_DUPLICATE_EMAIL(1001, 400, "이미 가입된 사용자의 이메일입니다."),
    NOT_FOUND_REFRESH_TOKEN_IN_COOKIE(2001, 401, "엑세스 토큰 재발급을 위한 리프레시 토큰이 존재하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN_IN_REPOSITORY(2002, 401, "리프레시 토큰 저장소에 존재하지 않는 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(2003, 401, "리프레시 토큰이 만료되었습니다"),
    AUTHORIZATION_FAIL(2004, 401, "엑세스 토큰이 존재하지 않거나 만료되었습니다.")

    ;

    private final int value;
    private final int httpStatusCode;
    private final String message;

    ErrorCode(int value, int httpStatusCode, String message) {
        this.value = value;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
