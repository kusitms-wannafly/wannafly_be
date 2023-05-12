package com.kusitms.wannafly.applicationfolder.domain;

import com.kusitms.wannafly.auth.LoginMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;

public interface ApplicationFolderRepository extends JpaRepository<ApplicationFolder,Long> {
    boolean existsByMemberIdAndYear(long memberId, int year);

}
