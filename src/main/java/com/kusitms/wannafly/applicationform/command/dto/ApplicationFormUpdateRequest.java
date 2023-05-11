package com.kusitms.wannafly.applicationform.command.dto;

import com.kusitms.wannafly.applicationform.command.domain.*;

import java.util.List;

public record ApplicationFormUpdateRequest(
        String recruiter,
        Integer year,
        String semester,
        List<ApplicationItemUpdateRequest> applicationItems
) {

    public ApplicationForm toDomain(Long memberId) {
        ApplicationForm form = ApplicationForm.createEmptyForm(
                memberId, recruiter, year, Semester.valueOf(semester.toUpperCase())
        );
        for (ApplicationItemUpdateRequest requestItem : applicationItems) {
            form.addItem(new ApplicationItem(
                    requestItem.applicationItemId(),
                    new ApplicationQuestion(requestItem.applicationQuestion()),
                    new ApplicationAnswer(requestItem.applicationAnswer())
            ));
        }
        return form;
    }
}
