package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.*;
import com.kusitms.wannafly.applicationform.query.ApplicationFormQueryService;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.applicationform.query.dto.ApplicationItemResponse;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.support.ServiceTest;
import com.kusitms.wannafly.support.fixture.ApplicationFormFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_CREATE_REQUEST;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_UPDATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFormServiceTest extends ServiceTest {

    @Autowired
    private ApplicationFormService applicationFormService;

    @Autowired
    private ApplicationFormQueryService applicationFormQueryService;

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Test
    void 지원서를_등록한다() {
        // when
        Long formId = applicationFormService.createForm(new LoginMember(1L), FORM_CREATE_REQUEST);

        // then
        Optional<ApplicationForm> savedForm = applicationFormRepository.findById(formId);
        assertAll(
                () -> assertThat(formId).isEqualTo(formId),
                () -> assertThat(savedForm).isPresent()
        );
    }

    @Test
    void 지원서를_수정한다() {
        // given
        LoginMember loginMember = new LoginMember(1L);
        Long formId = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST);

        // when
        applicationFormService.updateForm(
                formId, loginMember, FORM_UPDATE_REQUEST
        );

        // then
        ApplicationFormResponse actual = applicationFormQueryService.findOne(formId, loginMember);
        assertAll(
                () -> assertThat(actual.recruiter()).isEqualTo(FORM_UPDATE_REQUEST.recruiter()),
                () -> assertThat(actual.year()).isEqualTo(FORM_UPDATE_REQUEST.year()),
                () -> assertThat(actual.semester()).isEqualTo(FORM_UPDATE_REQUEST.semester()),
                () -> assertThat(actual.applicationItems())
                        .map(ApplicationItemResponse::applicationQuestion)
                        .containsExactly(
                                ApplicationFormFixture.QUESTION2,
                                ApplicationFormFixture.QUESTION1,
                                ApplicationFormFixture.QUESTION2
                        ),
                () -> assertThat(actual.applicationItems())
                        .map(ApplicationItemResponse::applicationAnswer)
                        .containsExactly(
                                ApplicationFormFixture.ANSWER2,
                                ApplicationFormFixture.ANSWER1,
                                ApplicationFormFixture.ANSWER2
                        )
        );
    }
}
