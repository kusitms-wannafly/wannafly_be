package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.query.dto.PagingParams;
import com.kusitms.wannafly.applicationform.query.dto.SimpleFormResponse;

import java.util.List;

public interface ApplicationFormDynamicRepository {

    List<SimpleFormResponse> querySimpleForm(Long memberId, PagingParams params);
}
