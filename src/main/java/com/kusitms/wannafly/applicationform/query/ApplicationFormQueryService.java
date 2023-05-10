package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFormQueryService {

    private final ApplicationFormQueryRepository applicationFormQueryRepository;

    public ApplicationFormResponse findOne(Long applicationFormId, LoginMember loginMember) {
        ApplicationForm applicationForm = getApplicationForm(applicationFormId);
        validateWriter(loginMember, applicationForm);
        return ApplicationFormResponse.from(applicationForm);
    }

    private ApplicationForm getApplicationForm(Long applicationFormId) {
        return applicationFormQueryRepository.findById(applicationFormId)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_APPLICATION_FORM));
    }

    private void validateWriter(LoginMember loginMember, ApplicationForm applicationForm) {
        if (!loginMember.equalsId(applicationForm.getMemberId())) {
            throw BusinessException.from(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}
