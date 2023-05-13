package com.kusitms.wannafly.applicationform.command.domain;

import com.kusitms.wannafly.applicationform.command.domain.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplicationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_form_id")
    private Long id;

    @Embedded
    private Writer writer;

    @Embedded
    private Recruiter recruiter;

    @Embedded
    private ApplicationYear year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WritingState writingState;

    @Embedded
    private ApplicationItems applicationItems = new ApplicationItems();

    public static ApplicationForm createEmptyForm(Writer writer,
                                                  Recruiter recruiter,
                                                  ApplicationYear year,
                                                  Semester semester) {
        return new ApplicationForm(writer, recruiter, year, semester, WritingState.ON_GOING);
    }

    private ApplicationForm(Writer writer,
                            Recruiter recruiter,
                            ApplicationYear year,
                            Semester semester,
                            WritingState writingState) {
        this.writer = writer;
        this.recruiter = recruiter;
        this.year = year;
        this.semester = semester;
        this.writingState = writingState;
    }

    public void update(ApplicationForm updatedForm) {
        recruiter = updatedForm.getRecruiter();
        year = updatedForm.getYear();
        semester = updatedForm.getSemester();
        applicationItems.updateItems(updatedForm.getApplicationItems());
    }

    public ApplicationItem addItem(ApplicationQuestion question, ApplicationAnswer answer) {
        ApplicationItem item = new ApplicationItem(this, question, answer);
        applicationItems.addItem(item);
        return item;
    }

    public void addItem(ApplicationItem item) {
        applicationItems.addItem(item);
    }

    public boolean isNotWriter(Writer writer) {
        return !this.writer.equals(writer);
    }

    public void changeWritingState() {
        writingState = writingState.change();
    }

    public Boolean isCompleted() {
        return writingState.isCompleted;
    }
}
