package com.kusitms.wannafly.applicationform.command.dto;

import com.kusitms.wannafly.applicationform.command.domain.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationFormMapper {

    public static ApplicationForm toDomain(ApplicationFormCreateRequest request, Long memberId) {
        ApplicationForm form = createEmptyForm(memberId, request.recruiter(), request.year(), request.semester());
        for (ApplicationItemCreateRequest item : request.applicationItems()) {
            form.addItem(
                    new ApplicationQuestion(item.applicationQuestion()),
                    new ApplicationAnswer(item.applicationAnswer())
            );
        }
        return form;
    }

    public static ApplicationForm toDomain(ApplicationFormUpdateRequest request, Long memberId) {
        ApplicationForm form = createEmptyForm(memberId, request.recruiter(), request.year(), request.semester());
        for (ApplicationItemUpdateRequest requestItem : request.applicationItems()) {
            form.addItem(
                    new ApplicationItem(
                    requestItem.applicationItemId(),
                    new ApplicationQuestion(requestItem.applicationQuestion()),
                    new ApplicationAnswer(requestItem.applicationAnswer())
            ));
        }
        return form;
    }

    private static ApplicationForm createEmptyForm(Long memberId, String recruiter, Integer year, String semester) {
        return ApplicationForm.createEmptyForm(
                memberId, recruiter, year, Semester.valueOf(semester.toUpperCase())
        );
    }
}
