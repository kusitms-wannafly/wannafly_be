package com.kusitms.wannafly.auth.infrastructure.refreshtoken;

import com.kusitms.wannafly.auth.token.RefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class JpaRefreshToken {

    @Id
    @Column(name = "refresh_token_id")
    private String value;

    @Column(nullable = false)
    private LocalDateTime expiredTime;

    @Column(nullable = false)
    private Long memberId;

    public JpaRefreshToken(RefreshToken refreshToken) {
        this.value = refreshToken.getValue();
        this.expiredTime = refreshToken.getExpiredTime();
        this.memberId = refreshToken.getMemberId();
    }
}
