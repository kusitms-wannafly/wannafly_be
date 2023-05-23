package com.kusitms.wannafly.command.auth.infrastructure.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<JpaRefreshToken, String> {

}
