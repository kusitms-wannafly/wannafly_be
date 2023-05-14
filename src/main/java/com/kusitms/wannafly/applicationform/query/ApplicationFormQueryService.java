package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.command.domain.value.Writer;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.PagingParams;
import com.kusitms.wannafly.applicationform.query.dto.SimpleFormResponse;
import com.kusitms.wannafly.applicationform.query.repository.ApplicationFormQueryRepository;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFormQueryService {

    private final ApplicationFormQueryRepository applicationFormQueryRepository;

    public ApplicationFormResponse findOne(Long applicationFormId, LoginMember loginMember) {
        Writer requester = new Writer(loginMember.id());
        ApplicationForm applicationForm = getApplicationForm(applicationFormId);
        validateWriter(requester, applicationForm);
        return ApplicationFormResponse.from(applicationForm);
    }

    public List<SimpleFormResponse> findAllByCondition(LoginMember loginMember, PagingParams params) {
        return applicationFormQueryRepository.findByParams(loginMember.id(), params)
                .stream()
                .map(SimpleFormResponse::from)
                .toList();
    }

    private ApplicationForm getApplicationForm(Long applicationFormId) {
        return applicationFormQueryRepository.findById(applicationFormId)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_APPLICATION_FORM));
    }

    private void validateWriter(Writer requester, ApplicationForm applicationForm) {
        if (applicationForm.isNotWriter(requester)) {
            throw BusinessException.from(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}
