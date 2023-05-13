package com.kusitms.wannafly.applicationform.command.domain;

import com.kusitms.wannafly.applicationform.command.domain.value.*;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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


    @OneToMany(mappedBy = "applicationForm", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ApplicationItem> applicationItems = new ArrayList<>();

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
        this.recruiter = updatedForm.getRecruiter();
        this.year = updatedForm.getYear();
        this.semester = updatedForm.getSemester();

        List<ApplicationItem> updatedItems = updatedForm.getApplicationItems();
        updatedItems.forEach(this::updateItem);
    }

    private void updateItem(ApplicationItem updateItem) {
        this.applicationItems.stream()
                .filter(item -> item.getId().equals(updateItem.getId()))
                .findAny()
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_APPLICATION_ITEM))
                .updateContents(updateItem);
    }

    public ApplicationItem addItem(ApplicationQuestion question, ApplicationAnswer answer) {
        ApplicationItem item = new ApplicationItem(this, question, answer);
        applicationItems.add(item);
        return item;
    }

    public void addItem(ApplicationItem item) {
        applicationItems.add(item);
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
