package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFormQueryService {

    private final ApplicationFormQueryRepository applicationFormQueryRepository;

    public ApplicationFormResponse findOne(Long applicationFormId, Long memberId) {
        ApplicationForm applicationForm = applicationFormQueryRepository.findById(applicationFormId)
                .orElseThrow();
        if (!applicationForm.getMemberId().equals(memberId)) {
            throw new RuntimeException();
        }
        return ApplicationFormResponse.from(applicationForm);
    }
}
