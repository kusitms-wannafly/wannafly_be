package com.kusitms.wannafly.applicationform.command.domain.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WritingStateTest {

    @DisplayName("작성 상태를 변경할 때")
    @Nested
    class ChangeTest {

        @Test
        void 작성_중이면_완료가_된다() {
            // given
            WritingState state = WritingState.ON_GOING;

            // when
            WritingState actual = state.change();

            // then
            assertThat(actual).isEqualTo(WritingState.COMPLETE);
        }

        @Test
        void 완료면_작성_중이_된다() {
            // given
            WritingState state = WritingState.COMPLETE;

            // when
            WritingState actual = state.change();

            // then
            assertThat(actual).isEqualTo(WritingState.ON_GOING);
        }
    }
}
