package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.*;
import com.kusitms.wannafly.applicationform.command.domain.value.ApplicationAnswer;
import com.kusitms.wannafly.applicationform.command.domain.value.ApplicationQuestion;
import com.kusitms.wannafly.applicationform.command.domain.value.Writer;
import com.kusitms.wannafly.applicationform.command.dto.*;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationFormService {

    private final ApplicationFormRepository applicationFormRepository;
    private final WriterCheckedFormService writerCheckedFormService;

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
        ApplicationForm originalForm = writerCheckedFormService.findById(applicationFormId, writer);
        ApplicationForm updatedForm = ApplicationFormMapper.toDomain(request, writer);
        originalForm.update(updatedForm);
    }

    public Long addItem(Long applicationFormId,
                        LoginMember loginMember,
                        ApplicationItemCreateRequest request) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm form = writerCheckedFormService.findById(applicationFormId, writer);
        ApplicationItem item = form.addItem(
                new ApplicationQuestion(request.applicationQuestion()),
                new ApplicationAnswer(request.applicationAnswer())
        );
        applicationFormRepository.flush();
        return item.getId();
    }

    public void deleteForm(Long applicationFormId, LoginMember loginMember) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm form = writerCheckedFormService.findById(applicationFormId, writer);
        applicationFormRepository.delete(form);
    }

    public FormStateResponse changeState(Long applicationFormId, LoginMember loginMember) {
        return null;
    }
}
