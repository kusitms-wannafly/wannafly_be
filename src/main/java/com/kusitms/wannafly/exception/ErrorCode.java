package com.kusitms.wannafly.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    MEMBER_DUPLICATE_EMAIL(1001, 400, "이미 가입된 사용자의 이메일입니다."),
    NOT_FOUND_REFRESH_TOKEN_IN_COOKIE(2001, 401, "엑세스 토큰 재발급을 위한 리프레시 토큰이 존재하지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN_IN_REPOSITORY(2002, 401, "리프레시 토큰 저장소에 존재하지 않는 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(2003, 401, "리프레시 토큰이 만료되었습니다"),
    AUTHORIZATION_FAIL(2004, 401, "엑세스 토큰이 존재하지 않거나 만료되었습니다."),

    EMPTY_RECRUITER(3001, 400, "동아리명은 공백일 수 없습니다."),
    INVALID_YEAR(3002, 400, "올바르지 않은 지원 년도입니다."),
    EMPTY_QUESTION(3003, 400, "지원 문항은 공백일 수 없습니다."),
    NOT_FOUND_APPLICATION_FORM(3004, 404, "존재하지 않는 지원서입니다."),
    INVALID_WRITER_OF_FORM(3005, 403, "지원서를 작성한 회원만 열람할 수 있습니다."),
    NOT_FOUND_APPLICATION_ITEM(3006, 404, "존재하지 않는 지원 항목입니다."),

    MEMBER_DUPLICATE_YEAR(4001, 400, "이미 해당 년도가 존재합니다");

    private final int value;
    private final int httpStatusCode;
    private final String message;

    ErrorCode(int value, int httpStatusCode, String message) {
        this.value = value;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
