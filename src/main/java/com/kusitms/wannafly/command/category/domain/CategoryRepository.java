package com.kusitms.wannafly.command.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByMemberIdAndName(long memberId, String name);
}
