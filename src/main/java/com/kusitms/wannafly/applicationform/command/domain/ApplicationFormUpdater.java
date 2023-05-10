package com.kusitms.wannafly.applicationform.command.domain;

import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;

import java.util.List;

public class ApplicationFormUpdater {

    private final ApplicationForm form;
    private String recruiter;
    private Integer year;
    private Semester semester;
    private List<ApplicationItem> items;

    private ApplicationFormUpdater(ApplicationForm form) {
        this.form = form;
    }

    public static ApplicationFormUpdater from(ApplicationForm form, LoginMember loginMember) {
        if (!loginMember.equalsId(form.getMemberId())) {
            throw BusinessException.from(ErrorCode.INVALID_WRITER_OF_FORM);
        }
        return new ApplicationFormUpdater(form);
    }

    public ApplicationFormUpdater updateRecruiter(String recruiter) {
        this.recruiter = recruiter;
        return this;
    }

    public ApplicationFormUpdater updateYear(Integer year) {
        this.year = year;
        return this;
    }

    public ApplicationFormUpdater updateSemester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public ApplicationFormUpdater updateItems(List<ApplicationItem> items) {
        this.items = items;
        return this;
    }

    public void executeUpdate() {
        form.updateInfo(recruiter, year, semester);
        for (ApplicationItem item : items) {
            form.updateItem(item);
        }
    }
}
