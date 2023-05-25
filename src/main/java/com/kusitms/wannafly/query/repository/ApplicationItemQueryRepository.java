package com.kusitms.wannafly.query.repository;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationItemQueryRepository extends JpaRepository<ApplicationItem, Long> {

    @EntityGraph(attributePaths = "applicationForm")
    List<ApplicationItem> findByCategoryId(Long categoryId);

    @Query("select ai from ApplicationItem ai join fetch ai.applicationForm af " +
            "where af.writer.id = :memberId and " +
            "(ai.applicationQuestion.content like %:keyword% or " +
            "ai.applicationAnswer.content like %:keyword%)")
    List<ApplicationItem> findItemsByKeyword(@Param("memberId") Long memberId, @Param("keyword") String keyword);
}
