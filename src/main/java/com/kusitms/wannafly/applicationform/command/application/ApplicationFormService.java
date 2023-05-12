package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.*;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormMapper;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormUpdateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationItemCreateRequest;
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
        Writer writer = new Writer(loginMember.id());
        ApplicationForm applicationForm = ApplicationFormMapper.toDomain(request, writer);
        applicationFormRepository.save(applicationForm);
        return applicationForm.getId();
    }

    public void updateForm(Long applicationFormId,
                           LoginMember loginMember,
                           ApplicationFormUpdateRequest request) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm originalForm = getApplicationForm(applicationFormId, writer);
        ApplicationForm updatedForm = ApplicationFormMapper.toDomain(request, writer);
        originalForm.update(updatedForm);
    }

    public Long addItem(Long applicationFormId,
                        LoginMember loginMember,
                        ApplicationItemCreateRequest request) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm form = getApplicationForm(applicationFormId, writer);
        ApplicationItem item = form.addItem(
                new ApplicationQuestion(request.applicationQuestion()),
                new ApplicationAnswer(request.applicationAnswer())
        );
        applicationFormRepository.flush();
        return item.getId();
    }

    private ApplicationForm getApplicationForm(Long applicationFormId, Writer requester) {
        ApplicationForm form = applicationFormRepository.findById(applicationFormId)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_APPLICATION_FORM));
        if (!form.isWriter(requester)) {
            throw BusinessException.from(ErrorCode.INVALID_WRITER_OF_FORM);
        }
        return form;
    }
}
