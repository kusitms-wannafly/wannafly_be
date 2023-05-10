package com.kusitms.wannafly.applicationform.command.dto;

import java.util.List;

public record ApplicationFormUpdateRequest(
        String recruiter,
        Integer year,
        String semester,
        List<ApplicationItemUpdateRequest> applicationItems
) {
}
