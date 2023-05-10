package com.kusitms.wannafly.auth;

public record LoginMember(Long id) {
    public boolean equalsId(Long id) {
        return this.id.equals(id);
    }
}
