package com.kusitms.wannafly.auth.dto;

public record AuthorizationResponse(boolean isAuthorized, Long memberId) {

    public static AuthorizationResponse authorized(Long memberId) {
        return new AuthorizationResponse(true, memberId);
    }

    public static AuthorizationResponse unauthorized() {
        return new AuthorizationResponse(false, null);
    }
}
