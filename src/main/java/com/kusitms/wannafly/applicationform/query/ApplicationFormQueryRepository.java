package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormQueryRepository extends JpaRepository<ApplicationForm, Long> {
}
