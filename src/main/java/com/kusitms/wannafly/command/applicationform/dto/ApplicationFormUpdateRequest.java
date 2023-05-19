package com.kusitms.wannafly.command.applicationform.dto;

import java.util.List;

public record ApplicationFormUpdateRequest(
        String recruiter,
        Integer year,
        String semester,
        List<ApplicationItemUpdateRequest> applicationItems
) {
}
