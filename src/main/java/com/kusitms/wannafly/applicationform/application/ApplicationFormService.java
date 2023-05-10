package com.kusitms.wannafly.applicationform.application;

import com.kusitms.wannafly.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.auth.LoginMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationFormService {

    public Long createForm(LoginMember loginMember, ApplicationFormCreateRequest request) {
        return null;
    }
}
