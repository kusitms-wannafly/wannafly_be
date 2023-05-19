package com.kusitms.wannafly.command.applicationform.domain.value;

import java.util.Arrays;

public enum WritingState {

    COMPLETE(true),
    ON_GOING(false)

    ;

    public final boolean isCompleted;

    WritingState(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public static WritingState from(Boolean isCompleted) {
        return Arrays.stream(values())
                .filter(state -> state.isCompleted == isCompleted)
                .findFirst()
                .orElseThrow();
    }
}
