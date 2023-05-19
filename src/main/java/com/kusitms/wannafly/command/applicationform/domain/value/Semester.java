package com.kusitms.wannafly.command.applicationform.domain.value;

public enum Semester {

    FIRST_HALF,
    SECOND_HALF

    ;

    public static Semester from(String name) {
        return valueOf(name.toUpperCase());
    }
}
