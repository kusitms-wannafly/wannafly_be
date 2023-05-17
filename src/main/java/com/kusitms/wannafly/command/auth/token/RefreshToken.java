package com.kusitms.wannafly.command.auth.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RefreshToken {

    private String value;
    private LocalDateTime expiredTime;
    private Long memberId;

    public boolean isValid(LocalDateTime now) {
        return now.isBefore(expiredTime);
    }
}
