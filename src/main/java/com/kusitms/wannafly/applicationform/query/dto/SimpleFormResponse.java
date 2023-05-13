package com.kusitms.wannafly.applicationform.query.dto;

import java.time.LocalDateTime;

public record SimpleFormResponse(
        Long applicationFormId,
        String recruiter,
        Integer year,
        String semester,
        Boolean isCompleted,
        LocalDateTime lastModifiedTime
) {
}
