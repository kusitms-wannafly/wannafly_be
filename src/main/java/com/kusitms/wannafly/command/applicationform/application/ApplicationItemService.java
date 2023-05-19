package com.kusitms.wannafly.command.applicationform.application;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItemRepository;
import com.kusitms.wannafly.command.applicationform.domain.value.ApplicationAnswer;
import com.kusitms.wannafly.command.applicationform.domain.value.ApplicationQuestion;
import com.kusitms.wannafly.command.applicationform.domain.value.Writer;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.command.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationItemService {

    private final ApplicationItemRepository applicationItemRepository;
    private final WriterCheckedFormService writerCheckedFormService;


    public Long addItem(Long applicationFormId,
                        LoginMember loginMember,
                        ApplicationItemCreateRequest request) {
        Writer writer = new Writer(loginMember.id());
        ApplicationForm form = writerCheckedFormService.checkWriterAndGet(applicationFormId, writer);
        ApplicationItem item = new ApplicationItem(
                form,
                new ApplicationQuestion(request.applicationQuestion()),
                new ApplicationAnswer(request.applicationAnswer())
        );
        applicationItemRepository.save(item);
        return item.getId();
    }
}
