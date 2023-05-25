package com.kusitms.wannafly.command.auth.infrastructure.refreshtoken.redis;

import com.kusitms.wannafly.command.auth.token.RefreshToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token")
public class RedisRefreshToken {

    @Id
    private String id;
    private LocalDateTime expiredTime;
    private Long memberId;
    @TimeToLive
    private Long timeToLive;

    public RedisRefreshToken(RefreshToken refreshToken) {
        this.id = refreshToken.getValue();
        this.expiredTime = refreshToken.getExpiredTime();
        this.memberId = refreshToken.getMemberId();
        this.timeToLive = Duration.between(LocalDateTime.now(), expiredTime).getSeconds();
    }

    public RefreshToken toRefreshToken() {
        return new RefreshToken(id, expiredTime, memberId);
    }
}
