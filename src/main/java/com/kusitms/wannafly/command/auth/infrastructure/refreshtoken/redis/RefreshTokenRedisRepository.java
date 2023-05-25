package com.kusitms.wannafly.command.auth.infrastructure.refreshtoken.redis;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RedisRefreshToken, String> {
}
