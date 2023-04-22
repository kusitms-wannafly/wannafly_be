package com.kusitms.wannafly.auth.dto;

import com.kusitms.wannafly.member.domain.Member;

public record LoginRequest(String name, String email, String pictureUrl) {

    public Member toMember() {
        return new Member(name, email, pictureUrl);
    }
}
