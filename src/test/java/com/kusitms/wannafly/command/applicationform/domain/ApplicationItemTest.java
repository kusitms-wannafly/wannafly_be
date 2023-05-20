package com.kusitms.wannafly.command.applicationform.domain;

import com.kusitms.wannafly.command.applicationform.domain.value.*;
import com.kusitms.wannafly.command.category.domain.Category;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationItemTest {

    @Test
    void 카테고리_등록자와_지원_항목_작성자가_같아야_한다() {
        // given
        long writerId = 1L;
        Writer writer = new Writer(writerId);
        long categoryRegister = 2L;

        ApplicationForm form = ApplicationForm.createEmptyForm(
                writer, new Recruiter("큐시즘"), new ApplicationYear(2023), Semester.FIRST_HALF
        );
        ApplicationItem item = new ApplicationItem(
                form, new ApplicationQuestion("질문"), new ApplicationAnswer("답변")
        );
        Category category = Category.createCategory(categoryRegister, "지원 동기");

        // when then
        Assertions.assertThatThrownBy(() -> item.registerCategory(category))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_MATCH_FORM_WRITER_CATEGORY_REGISTER);
    }
}