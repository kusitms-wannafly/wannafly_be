package com.kusitms.wannafly.query.repository;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.query.dto.PagingParams;

import java.util.List;

public interface ApplicationFormDynamicRepository {

    List<ApplicationForm> findByParams(Long memberId, PagingParams params);
}
