package com.kusitms.wannafly.applicationform.command.domain.value;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecruiterTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 모집자는_공백일_수_없다(String value) {
        // when then
        assertThatThrownBy(() -> new Recruiter(value))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.EMPTY_RECRUITER);
    }
}
