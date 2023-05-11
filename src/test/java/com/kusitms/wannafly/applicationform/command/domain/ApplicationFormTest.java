package com.kusitms.wannafly.applicationform.command.domain;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @DisplayName("지원서를 수정할 떼")
    @Nested
    class UpdateFormTest {

        private final Long writerId = 1L;

        private final ApplicationForm form = ApplicationForm.createEmptyForm(
                writerId, "큐시즘", 2023, Semester.FIRST_HALF
        );

        @Test
        void 로그인_회원이_지원서_작성자면_수정_가능하다() {
            // given
            ApplicationForm updated = ApplicationForm.createEmptyForm(
                    writerId, "큐시즘 28기", 2024, Semester.SECOND_HALF
            );

            // when
            form.update(updated);

            // then
            assertAll(
                    () -> assertThat(form.getRecruiter()).isEqualTo(updated.getRecruiter()),
                    () -> assertThat(form.getYear()).isEqualTo(updated.getYear()),
                    () -> assertThat(form.getSemester()).isEqualTo(updated.getSemester())
            );

        }

        @Test
        void 로그인_회원이_지원서_작성자가_아니면_예외가_발생한다() {
            // given
            ApplicationForm updated = ApplicationForm.createEmptyForm(
                    2L, "큐시즘 28기", 2024, Semester.SECOND_HALF
            );

            // when then
            assertThatThrownBy(() -> form.update(updated))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }

        @Test
        void 없는_지원_항목은_수정할_수_없다() {
            // given
            Long itemId1 = 1L;
            Long itemId2 = 2L;
            ApplicationItem item1 = new ApplicationItem(
                    itemId1,
                    new ApplicationQuestion(QUESTION1),
                    new ApplicationAnswer(ANSWER1)
            );
            ApplicationItem item2 = new ApplicationItem(
                    itemId2,
                    new ApplicationQuestion(QUESTION2),
                    new ApplicationAnswer(ANSWER2)
            );
            ApplicationForm updated = ApplicationForm.createEmptyForm(
                    writerId, "큐시즘 28기", 2024, Semester.SECOND_HALF
            );
            form.addItem(item1);
            updated.addItem(item2);

            // when then
            assertThatThrownBy(() -> form.update(updated))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.NOT_FOUND_APPLICATION_ITEM);
        }

        @Test
        void 지원_항목이_순서에_맞게_수정된다() {
            // given
            Long itemId1 = 1L;
            Long itemId2 = 2L;
            ApplicationItem item1 = new ApplicationItem(
                    itemId1,
                    new ApplicationQuestion(QUESTION1),
                    new ApplicationAnswer(ANSWER1)
            );
            ApplicationItem item2 = new ApplicationItem(
                    itemId2,
                    new ApplicationQuestion(QUESTION2),
                    new ApplicationAnswer(ANSWER2)
            );
            form.addItem(item1);
            form.addItem(item2);

            ApplicationForm updated = ApplicationForm.createEmptyForm(
                    writerId, "큐시즘 28기", 2024, Semester.SECOND_HALF
            );

            ApplicationItem updatedItem1 = new ApplicationItem(
                    itemId1,
                    new ApplicationQuestion(QUESTION2),
                    new ApplicationAnswer(ANSWER2)
            );
            ApplicationItem updatedItem2 = new ApplicationItem(
                    itemId2,
                    new ApplicationQuestion(QUESTION1),
                    new ApplicationAnswer(ANSWER1)
            );
            updated.addItem(updatedItem2);
            updated.addItem(updatedItem1);

            // when
            form.update(updated);

            // then
            List<ApplicationItem> actualItems = form.getApplicationItems();
            ListAssert<ApplicationItem> actualItem1 = assertThat(actualItems)
                    .filteredOn(item -> item.getId().equals(itemId1));
            ListAssert<ApplicationItem> actualItem2 = assertThat(actualItems)
                    .filteredOn(item -> item.getId().equals(itemId2));
            assertAll(
                    () -> actualItem1
                            .map(item -> item.getApplicationQuestion().getContent())
                            .containsExactly(QUESTION2),
                    () -> actualItem1
                            .map(item -> item.getApplicationAnswer().getContent())
                            .containsExactly(ANSWER2),
                    () -> actualItem2
                            .map(item -> item.getApplicationQuestion().getContent())
                            .containsExactly(QUESTION1),
                    () -> actualItem2
                            .map(item -> item.getApplicationAnswer().getContent())
                            .containsExactly(ANSWER1)
            );
        }

    }
}
