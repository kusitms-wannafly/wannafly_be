package com.kusitms.wannafly.command.auth.infrastructure.refreshtoken;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RedisRefreshToken, String> {
}
