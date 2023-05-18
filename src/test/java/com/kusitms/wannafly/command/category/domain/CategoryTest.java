package com.kusitms.wannafly.command.category.domain;

import com.kusitms.wannafly.command.applicationform.domain.value.Recruiter;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CategoryTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 카테고리는_공백일_수_없다(String name) {
        // given
        Long memberId = 1L;

        // when then
        assertThatThrownBy(() -> Category.createCategoryByName(memberId, name))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_NAME);
    }

}
