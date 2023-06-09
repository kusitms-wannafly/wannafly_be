package com.kusitms.wannafly.command.applicationform.application;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationFormRepository;
import com.kusitms.wannafly.command.applicationform.domain.value.Writer;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormMapper;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormUpdateRequest;
import com.kusitms.wannafly.command.applicationform.dto.FormStateRequest;
import com.kusitms.wannafly.command.auth.LoginMember;
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
        ApplicationForm originalForm = writerCheckedFormService.checkWriterAndGet(applicationFormId, writer);
        ApplicationForm updatedForm = ApplicationFormMapper.toDomain(request, writer);
        originalForm.update(updatedForm);
    }

    public void deleteForm(Long applicationFormId, LoginMember loginMember) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm form = writerCheckedFormService.checkWriterAndGet(applicationFormId, writer);
        applicationFormRepository.delete(form);
    }

    public void changeState(Long applicationFormId,
                                         LoginMember loginMember,
                                         FormStateRequest request) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm form = writerCheckedFormService.checkWriterAndGet(applicationFormId, writer);
        form.changeWritingState(request.isCompleted());
    }
}
