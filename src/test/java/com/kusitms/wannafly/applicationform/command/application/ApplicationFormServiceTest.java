package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.command.domain.ApplicationFormRepository;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.applicationform.command.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.support.ServiceTest;
import com.kusitms.wannafly.support.fixture.ApplicationFormFixture;
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
                ApplicationFormFixture.QUESTION,
                ApplicationFormFixture.ANSWER
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
                () -> assertThat(formId).isEqualTo(formId),
                () -> assertThat(savedForm).isPresent()
        );
    }
}
