package com.kusitms.wannafly.command.applicationform.dto;

import java.util.List;

public record ApplicationFormCreateRequest(
        String recruiter,
        Integer year,
        String semester,
        List<ApplicationItemCreateRequest> applicationItems
) {
}
