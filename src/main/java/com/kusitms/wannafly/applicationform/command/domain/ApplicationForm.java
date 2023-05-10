package com.kusitms.wannafly.applicationform.command.domain;

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

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String recruiter;

    @Column(nullable = false, name = "years")
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
        validateRecruiter(recruiter);
        validateYear(year);
        this.memberId = memberId;
        this.recruiter = recruiter;
        this.year = year;
        this.semester = semester;
    }

    private void validateRecruiter(String recruiter) {
        if (recruiter.isBlank()) {
            throw BusinessException.from(ErrorCode.EMPTY_RECRUITER);
        }
    }

    private void validateYear(Integer year) {
        if (year <= 0) {
            throw BusinessException.from(ErrorCode.INVALID_YEAR);
        }
    }

    public void addItem(ApplicationQuestion question, ApplicationAnswer answer) {
        ApplicationItem item = new ApplicationItem(this, question, answer);
        applicationItems.add(item);
    }
}
