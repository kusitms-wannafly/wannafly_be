package com.kusitms.wannafly.auth.dto;

import com.kusitms.wannafly.member.dto.MemberRequest;

public record LoginRequest(String registrationId, String name, String email, String pictureUrl) {

    public MemberRequest toMemberRequest() {
        return new MemberRequest(registrationId, name, email, pictureUrl);
    }
}
