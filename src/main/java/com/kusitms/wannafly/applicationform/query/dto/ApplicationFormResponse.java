package com.kusitms.wannafly.applicationform.query.dto;

import java.util.List;

public record ApplicationFormResponse(
        String recruiter,
        Integer year,
        String semester,
        List<ApplicationItemResponse> applicationItems
) {
}
