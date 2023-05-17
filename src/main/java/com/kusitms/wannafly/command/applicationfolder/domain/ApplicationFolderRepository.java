package com.kusitms.wannafly.command.applicationfolder.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFolderRepository extends JpaRepository<ApplicationFolder, Long> {
    boolean existsByMemberIdAndYear(long memberId, int year);
}
