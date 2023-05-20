package com.kusitms.wannafly.query.dto;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;

public record CategoryItemResponse(
        ApplicationItemResponse applicationItems,
        Long applicationFormId,
        String recruiter,
        Integer year,
        String semester
) {

    public static CategoryItemResponse from(ApplicationItem item) {
        ApplicationForm form = item.getApplicationForm();
        return new CategoryItemResponse(
                ApplicationItemResponse.from(item),
                item.getId(),
                form.getRecruiter().getValue(),
                form.getYear().getValue(),
                form.getSemester().name().toLowerCase()
        );
    }
}
