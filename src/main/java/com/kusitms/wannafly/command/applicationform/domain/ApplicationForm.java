package com.kusitms.wannafly.command.applicationform.domain;

import com.kusitms.wannafly.command.applicationform.domain.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(name = "idx_writer", columnList = "member_id"))
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

    @Column(nullable = false)
    private LocalDateTime lastModifiedTime;

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
        updateModifiedDate();
    }

    public ApplicationItem addItem(ApplicationQuestion question, ApplicationAnswer answer) {
        ApplicationItem item = new ApplicationItem(this, question, answer);
        applicationItems.addItem(item);
        updateModifiedDate();
        return item;
    }

    public void update(ApplicationForm updatedForm) {
        recruiter = updatedForm.getRecruiter();
        year = updatedForm.getYear();
        semester = updatedForm.getSemester();
        applicationItems.updateItems(updatedForm.getApplicationItems());
        updateModifiedDate();
    }

    public void addItem(ApplicationItem item) {
        applicationItems.addItem(item);
        updateModifiedDate();
    }

    public boolean isNotWriter(Writer writer) {
        return !this.writer.equals(writer);
    }

    public void changeWritingState(Boolean isCompleted) {
        writingState = WritingState.from(isCompleted);
    }

    public Boolean isCompleted() {
        return writingState.isCompleted;
    }

    private void updateModifiedDate() {
        lastModifiedTime = LocalDateTime.now();
    }
}
