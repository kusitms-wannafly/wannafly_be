package com.kusitms.wannafly.applicationfolder.domain;

import com.kusitms.wannafly.auth.LoginMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFolderRepository extends JpaRepository<ApplicationFolder,Long> {

    static boolean existsByMemberIdAndYear(long memberId, int year) {
        return true;
    }

    static Boolean existsByYear(LoginMember loginMember, Integer year) {
        return existsByMemberIdAndYear(loginMember.id(), year);
    }
}
