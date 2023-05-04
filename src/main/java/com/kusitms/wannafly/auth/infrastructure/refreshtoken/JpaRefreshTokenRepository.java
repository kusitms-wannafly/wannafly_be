package com.kusitms.wannafly.auth.infrastructure.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<JpaRefreshToken, String> {
}
