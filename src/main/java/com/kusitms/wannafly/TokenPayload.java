package com.kusitms.wannafly;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TokenPayload {

    private Long id;

    public Map<String, Object> toMap() {
        return Map.of("id", id);
    }
}
