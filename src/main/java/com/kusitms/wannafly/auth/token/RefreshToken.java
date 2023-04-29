package com.kusitms.wannafly.auth.token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RefreshToken {

    private String value;
    private LocalDateTime expiredTime;
    private Long memberId;
}
