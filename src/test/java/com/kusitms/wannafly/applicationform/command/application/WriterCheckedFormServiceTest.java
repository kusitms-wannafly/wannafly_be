package com.kusitms.wannafly.applicationform.command.application;

import com.kusitms.wannafly.applicationform.command.domain.*;
import com.kusitms.wannafly.applicationform.command.domain.value.Recruiter;
import com.kusitms.wannafly.applicationform.command.domain.value.Semester;
import com.kusitms.wannafly.applicationform.command.domain.value.Writer;
import com.kusitms.wannafly.applicationform.command.domain.value.ApplicationYear;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.support.ServiceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WriterCheckedFormServiceTest extends ServiceTest {

    @Autowired
    private WriterCheckedFormService writerCheckedFormService;

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @DisplayName("지원서를 조회할 떼")
    @Nested
    class FindTest {

        private final Long writerId = 1L;
        private final Writer writer = new Writer(writerId);
        private final Recruiter recruiter = new Recruiter("큐시즘");
        private final ApplicationYear year = new ApplicationYear(2023);

        @Test
        void 지원서_작성자는_조회_가능하다() {
            // given
            ApplicationForm form = ApplicationForm.createEmptyForm(
                    writer, recruiter, year, Semester.FIRST_HALF
            );
            ApplicationForm saved = applicationFormRepository.save(form);

            // when
            ApplicationForm actual = writerCheckedFormService.checkWriterAndGet(saved.getId(), writer);

            // then
            Assertions.assertThat(actual.getId()).isEqualTo(saved.getId());
        }

        @Test
        void 지원서_작성자가_아니면_예외가_발생한다() {
            // given
            ApplicationForm form = ApplicationForm.createEmptyForm(
                    writer, recruiter, year, Semester.FIRST_HALF
            );
            ApplicationForm saved = applicationFormRepository.save(form);

            // when
            Writer requester = new Writer(2L);
            assertThatThrownBy(() -> writerCheckedFormService.checkWriterAndGet(saved.getId(), requester))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_WRITER_OF_FORM);
        }
    }
}