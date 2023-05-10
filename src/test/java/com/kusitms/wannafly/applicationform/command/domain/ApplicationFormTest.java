package com.kusitms.wannafly.applicationform.command.domain;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.command.domain.Semester;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationFormTest {

    @DisplayName("지원서를 생성할 떼")
    @Nested
    class CreateFormTest {

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        void 모집자는_공백일_수_없다(String recruiter) {
            // given
            Long memberId = 1L;
            Integer year = 2023;

            // when then
            assertThatThrownBy(() -> ApplicationForm.createEmptyForm(memberId, recruiter, year, Semester.FIRST_HALF))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.EMPTY_RECRUITER);
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1})
        void 지원년도는_자연수여야_한다(int year) {
            // given
            Long memberId = 1L;
            String recruiter = "큐시즘";

            // when then
            assertThatThrownBy(() -> ApplicationForm.createEmptyForm(memberId, recruiter, year, Semester.FIRST_HALF))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_YEAR);
        }
    }
}
