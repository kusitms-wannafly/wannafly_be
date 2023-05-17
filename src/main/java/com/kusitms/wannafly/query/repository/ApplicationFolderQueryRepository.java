package com.kusitms.wannafly.query.repository;

import com.kusitms.wannafly.command.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.query.dto.ApplicationFolderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationFolderQueryRepository extends JpaRepository<ApplicationFolder, Long> {

    @SuppressWarnings("JpaQlInspection")
    @Query("select new com.kusitms.wannafly.query.dto.ApplicationFolderResponse(afd.year, " +
            "(select count(af) " +
            "from ApplicationForm af " +
            "where af.writer.id = :memberId and af.year.value = afd.year))" +
            "from ApplicationFolder afd " +
            "where afd.memberId = :memberId " +
            "order by afd.year desc")
    List<ApplicationFolderResponse> findAllByMemberIdOrderByYearDesc(@Param("memberId") Long memberId);
}
