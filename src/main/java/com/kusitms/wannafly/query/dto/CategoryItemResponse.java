package com.kusitms.wannafly.query.dto;

public record CategoryItemResponse(
        ApplicationItemResponse applicationItems,
        Long applicationFormId,
        String recruiter,
        Integer year,
        String semester
) {
}
