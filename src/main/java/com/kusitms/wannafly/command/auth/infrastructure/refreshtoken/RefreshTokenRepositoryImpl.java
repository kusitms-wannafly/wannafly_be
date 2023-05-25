package com.kusitms.wannafly.command.auth.infrastructure.refreshtoken;

import com.kusitms.wannafly.command.auth.infrastructure.refreshtoken.redis.RedisRefreshToken;
import com.kusitms.wannafly.command.auth.infrastructure.refreshtoken.redis.RefreshTokenRedisRepository;
import com.kusitms.wannafly.command.auth.token.RefreshToken;
import com.kusitms.wannafly.command.auth.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;


    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(new RedisRefreshToken(refreshToken));
    }

    @Override
    public Optional<RefreshToken> findByValue(String refreshTokenValue) {
        return refreshTokenRedisRepository.findById(refreshTokenValue)
                .map(RedisRefreshToken::toRefreshToken);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenRedisRepository.delete(new RedisRefreshToken(refreshToken));
    }
}
