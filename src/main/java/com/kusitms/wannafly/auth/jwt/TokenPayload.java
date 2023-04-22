package com.kusitms.wannafly.auth.jwt;

import java.util.Map;

public record TokenPayload(Long id) {

    public Map<String, Object> toMap() {
        return Map.of("id", id);
    }
}