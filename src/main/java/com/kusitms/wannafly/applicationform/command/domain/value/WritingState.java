package com.kusitms.wannafly.applicationform.command.domain.value;

public enum WritingState {

    COMPLETE(true) {
        @Override
        public WritingState change() {
            return ON_GOING;
        }
    },
    ON_GOING(false) {
        @Override
        public WritingState change() {
            return COMPLETE;
        }
    }

    ;

    public final boolean isCompleted;

    WritingState(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public abstract WritingState change();
}
