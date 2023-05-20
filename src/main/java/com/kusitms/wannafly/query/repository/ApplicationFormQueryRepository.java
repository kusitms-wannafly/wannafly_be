package com.kusitms.wannafly.query.repository;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.value.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormQueryRepository extends
        JpaRepository<ApplicationForm, Long>, ApplicationFormDynamicRepository {

    Long countAllByWriter(Writer writer);
}
