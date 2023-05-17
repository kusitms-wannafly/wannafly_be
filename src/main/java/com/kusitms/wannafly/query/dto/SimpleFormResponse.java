package com.kusitms.wannafly.query.dto;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;

import java.time.LocalDateTime;

public record SimpleFormResponse(
        Long applicationFormId,
        String recruiter,
        Integer year,
        String semester,
        Boolean isCompleted,
        LocalDateTime lastModifiedTime
) {
    public static SimpleFormResponse from(ApplicationForm form) {
        return new SimpleFormResponse(
                form.getId(),
                form.getRecruiter().getValue(),
                form.getYear().getValue(),
                form.getSemester().name().toLowerCase(),
                form.isCompleted(),
                form.getLastModifiedTime()
        );
    }
}
