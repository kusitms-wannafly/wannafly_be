package com.kusitms.wannafly.query.repository;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationItemQueryRepository extends JpaRepository<ApplicationItem, Long> {

    @EntityGraph(attributePaths = "applicationForm")
    List<ApplicationItem> findByCategoryId(Long categoryId);
}
