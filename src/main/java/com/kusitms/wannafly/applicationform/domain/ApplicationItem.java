package com.kusitms.wannafly.applicationform.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplicationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_form_id", nullable = false)
    private ApplicationForm applicationForm;

    @Embedded
    private ApplicationQuestion applicationQuestion;

    @Embedded
    private ApplicationAnswer applicationAnswer;

    public ApplicationItem(ApplicationForm form, ApplicationQuestion question, ApplicationAnswer answer) {
        this.applicationForm = form;
        this.applicationQuestion = question;
        this.applicationAnswer = answer;
    }
}
