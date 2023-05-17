package com.kusitms.wannafly.command.applicationform.domain.value;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationQuestionTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 지원_문항은_공백일_수_없다(String content) {

        // when then
        assertThatThrownBy(() -> new ApplicationQuestion(content))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.EMPTY_QUESTION);
    }
}
