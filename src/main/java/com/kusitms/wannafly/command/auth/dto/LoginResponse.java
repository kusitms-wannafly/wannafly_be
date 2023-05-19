package com.kusitms.wannafly.command.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record LoginResponse(Long memberId, String accessToken, @JsonIgnore String refreshToken) {
}
