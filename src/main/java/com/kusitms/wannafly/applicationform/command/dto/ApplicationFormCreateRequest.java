package com.kusitms.wannafly.applicationform.command.dto;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationAnswer;
import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.command.domain.ApplicationQuestion;
import com.kusitms.wannafly.applicationform.command.domain.Semester;

import java.util.List;

public record ApplicationFormCreateRequest(
        String recruiter,
        Integer year,
        String semester,
        List<ApplicationItemCreateRequest> applicationItems
) {

    public ApplicationForm toDomain(Long memberId) {
        ApplicationForm form = ApplicationForm.createEmptyForm(
                memberId, recruiter, year, Semester.valueOf(semester.toUpperCase())
        );
        for (ApplicationItemCreateRequest item : applicationItems) {
            ApplicationQuestion question = new ApplicationQuestion(item.applicationQuestion());
            ApplicationAnswer answer = new ApplicationAnswer(item.applicationAnswer());
            form.addItem(question, answer);
        }
        return form;
    }
}
