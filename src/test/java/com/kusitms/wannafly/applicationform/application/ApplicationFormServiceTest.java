package com.kusitms.wannafly.applicationform.application;

import com.kusitms.wannafly.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.domain.ApplicationFormRepository;
import com.kusitms.wannafly.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.support.ServiceTest;
import com.kusitms.wannafly.support.fixture.ApplicationFormText;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFormServiceTest extends ServiceTest {

    @Autowired
    private ApplicationFormService applicationFormService;

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Test
    void 지원서를_등록한다() {
        // given
        ApplicationItemCreateRequest itemRequest = new ApplicationItemCreateRequest(
                ApplicationFormText.QUESTION,
                ApplicationFormText.ANSWER
        );
        ApplicationFormCreateRequest formRequest = new ApplicationFormCreateRequest(
                "큐시즘",
                2023,
                "first_half",
                List.of(itemRequest, itemRequest, itemRequest)
        );

        // when
        Long formId = applicationFormService.createForm(new LoginMember(1L), formRequest);

        // then
        Optional<ApplicationForm> savedForm = applicationFormRepository.findById(formId);
        assertAll(
                () -> assertThat(formId).isEqualTo(1L),
                () -> assertThat(savedForm).isPresent()
        );
    }
}
