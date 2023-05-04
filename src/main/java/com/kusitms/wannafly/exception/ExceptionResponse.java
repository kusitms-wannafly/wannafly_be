package com.kusitms.wannafly.exception;

public record ExceptionResponse(Integer errorCode, String message) {

    public static ExceptionResponse from(ErrorCode errorCode) {
        return new ExceptionResponse(errorCode.getValue(), errorCode.getMessage());
    }
}
