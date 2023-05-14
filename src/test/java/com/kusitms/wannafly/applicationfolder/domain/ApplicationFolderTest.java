package com.kusitms.wannafly.applicationfolder.domain;


import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ApplicationFolderTest {
    @DisplayName("지원서보관함을 생성할 때")
    @Nested
    class CreateFormTest {
        @ParameterizedTest
        @ValueSource(ints = {0, -1})
        void 지원년도는_자연수여야_한다(int year) {
            // given
            Long memberId = 1L;

            // when then
            assertThatThrownBy(() -> ApplicationFolder.createFolderByYear(memberId, year))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_YEAR);
        }
    }
}
