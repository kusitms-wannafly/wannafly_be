package com.kusitms.wannafly.query.repository;

import com.kusitms.wannafly.command.applicationfolder.domain.ApplicationFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationFolderQueryRepository extends JpaRepository<ApplicationFolder, Long> {

    List<ApplicationFolder> findAllByMemberIdOrderByYearDesc(Long memberId);
}
