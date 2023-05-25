package com.kusitms.wannafly.query.dto;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;

public record KeywordItemResponse(
        ApplicationItemResponse applicationItem,
        Long applicationFormId,
        String recruiter,
        Integer year,
        String semester
) {

    public static KeywordItemResponse from(ApplicationItem item) {
        ApplicationForm form = item.getApplicationForm();
        return new KeywordItemResponse(
                ApplicationItemResponse.from(item),
                form.getId(),
                form.getRecruiter().getValue(),
                form.getYear().getValue(),
                form.getSemester().name().toLowerCase()
        );
    }
}
