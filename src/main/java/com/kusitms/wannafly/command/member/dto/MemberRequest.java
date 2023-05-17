package com.kusitms.wannafly.command.member.dto;

import com.kusitms.wannafly.command.member.domain.Member;

public record MemberRequest(String registrationId, String name, String email, String pictureUrl) {
    public Member toEntity() {
        return new Member(name, email, pictureUrl, registrationId);
    }
}
