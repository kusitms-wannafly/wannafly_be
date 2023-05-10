package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFormQueryService {


    public ApplicationFormResponse findOne(Long applicationFormId, Long memberId) {
        return null;
    }
}
