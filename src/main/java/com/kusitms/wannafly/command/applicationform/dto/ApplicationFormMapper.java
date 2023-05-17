package com.kusitms.wannafly.command.applicationform.dto;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;
import com.kusitms.wannafly.command.applicationform.domain.value.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationFormMapper {

    public static ApplicationForm toDomain(ApplicationFormCreateRequest request, Writer writer) {
        ApplicationForm form = createEmptyForm(writer, request.recruiter(), request.year(), request.semester());
        for (ApplicationItemCreateRequest item : request.applicationItems()) {
            form.addItem(
                    new ApplicationQuestion(item.applicationQuestion()),
                    new ApplicationAnswer(item.applicationAnswer())
            );
        }
        return form;
    }

    public static ApplicationForm toDomain(ApplicationFormUpdateRequest request, Writer writer) {
        ApplicationForm form = createEmptyForm(writer, request.recruiter(), request.year(), request.semester());
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

    private static ApplicationForm createEmptyForm(Writer writer, String recruiter, Integer year, String semester) {
        return ApplicationForm.createEmptyForm(
                writer, new Recruiter(recruiter), new ApplicationYear(year), Semester.from(semester)
        );
    }
}
