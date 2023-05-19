package com.kusitms.wannafly.command.applicationform.domain.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WritingStateTest {

    @DisplayName("작성 상태를 변경할 때")
    @Nested
    class ChangeTest {

        @Test
        void 완료_상태를_생성한다() {
            // when
            WritingState actual = WritingState.from(true);

            // then
            assertThat(actual).isEqualTo(WritingState.COMPLETE);
        }

        @Test
        void 작성중_상태를_생성한다() {
            // when
            WritingState actual = WritingState.from(false);

            // then
            assertThat(actual).isEqualTo(WritingState.ON_GOING);
        }
    }
}
