package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.command.domain.ApplicationFormRepository;
import com.kusitms.wannafly.applicationform.command.domain.value.Writer;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WriterCheckedFormService {

    private final ApplicationFormRepository applicationFormRepository;

    public ApplicationForm checkWriterAndGet(Long applicationFormId, Writer writer) {
        ApplicationForm form = applicationFormRepository.findById(applicationFormId)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_APPLICATION_FORM));
        validateWriter(writer, form);
        return form;
    }

    private static void validateWriter(Writer writer, ApplicationForm form) {
        if (form.isNotWriter(writer)) {
            throw BusinessException.from(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}
