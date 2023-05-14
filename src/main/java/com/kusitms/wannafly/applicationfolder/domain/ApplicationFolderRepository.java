package com.kusitms.wannafly.applicationfolder.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationFolderRepository extends JpaRepository<ApplicationFolder, Long> {
    boolean existsByMemberIdAndYear(long memberId, int year);

    List<ApplicationFolder> findAllByMemberIdOrderByYearDesc(Long memberId);
}
