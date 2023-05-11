package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.command.domain.ApplicationFormRepository;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormUpdateRequest;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationFormService {

    private final ApplicationFormRepository applicationFormRepository;

    public Long createForm(LoginMember loginMember, ApplicationFormCreateRequest request) {
        ApplicationForm applicationForm = request.toDomain(loginMember.id());
        applicationFormRepository.save(applicationForm);
        return applicationForm.getId();
    }

    public void updateForm(Long applicationFormId,
                           LoginMember loginMember,
                           ApplicationFormUpdateRequest request) {
        ApplicationForm originalForm = getApplicationForm(applicationFormId);
        ApplicationForm updatedForm = request.toDomain(loginMember.id());
        originalForm.update(updatedForm);
    }

    private ApplicationForm getApplicationForm(Long applicationFormId) {
        return applicationFormRepository.findById(applicationFormId)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_APPLICATION_FORM));
    }
}
