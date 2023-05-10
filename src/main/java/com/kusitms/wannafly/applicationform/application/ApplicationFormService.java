package com.kusitms.wannafly.applicationform.application;

import com.kusitms.wannafly.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.domain.ApplicationFormRepository;
import com.kusitms.wannafly.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.auth.LoginMember;
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
}
