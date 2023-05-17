package com.kusitms.wannafly.command.auth.dto;

import com.kusitms.wannafly.command.member.dto.MemberRequest;

public record LoginRequest(String registrationId, String name, String email, String pictureUrl) {

    public MemberRequest toMemberRequest() {
        return new MemberRequest(registrationId, name, email, pictureUrl);
    }
}
