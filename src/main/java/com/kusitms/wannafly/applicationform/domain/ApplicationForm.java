package com.kusitms.wannafly.applicationform.domain;

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

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String recruiter;

    @Column(nullable = false)
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semester semester;

    @OneToMany(mappedBy = "applicationForm", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ApplicationItem> applicationItems = new ArrayList<>();

    public static ApplicationForm createEmptyForm(Long memberId, String recruiter, Integer year, Semester semester) {
        return new ApplicationForm(memberId, recruiter, year, semester);
    }

    private ApplicationForm(Long memberId, String recruiter, Integer year, Semester semester) {
        this.memberId = memberId;
        this.recruiter = recruiter;
        this.year = year;
        this.semester = semester;
    }

    public void addItem(ApplicationQuestion question, ApplicationAnswer answer) {
        ApplicationItem item = new ApplicationItem(this, question, answer);
        applicationItems.add(item);
    }
}
