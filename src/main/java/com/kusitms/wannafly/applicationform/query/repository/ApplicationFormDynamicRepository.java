package com.kusitms.wannafly.applicationform.query.repository;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.query.dto.PagingParams;

import java.util.List;

public interface ApplicationFormDynamicRepository {

    List<ApplicationForm> findByParams(Long memberId, PagingParams params);
}
