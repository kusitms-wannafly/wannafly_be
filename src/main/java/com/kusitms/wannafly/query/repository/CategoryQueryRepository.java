package com.kusitms.wannafly.query.repository;

import com.kusitms.wannafly.command.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryQueryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByMemberId(Long memberId);
}
