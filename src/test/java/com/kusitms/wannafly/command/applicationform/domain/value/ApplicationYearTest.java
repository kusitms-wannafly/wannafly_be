package com.kusitms.wannafly.command.applicationform.domain.value;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationYearTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void 지원년도는_자연수여야_한다(int value) {
        // when then
        assertThatThrownBy(() -> new ApplicationYear(value))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INVALID_YEAR);
    }
}
