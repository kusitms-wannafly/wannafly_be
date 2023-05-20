package com.kusitms.wannafly.command.applicationform.domain;

import com.kusitms.wannafly.command.applicationform.domain.value.ApplicationAnswer;
import com.kusitms.wannafly.command.applicationform.domain.value.ApplicationQuestion;
import com.kusitms.wannafly.command.category.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplicationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_form_id")
    private ApplicationForm applicationForm;

    @Embedded
    private ApplicationQuestion applicationQuestion;

    @Embedded
    private ApplicationAnswer applicationAnswer;

    private Long categoryId;

    public ApplicationItem(ApplicationForm form, ApplicationQuestion question, ApplicationAnswer answer) {
        this.applicationForm = form;
        this.applicationQuestion = question;
        this.applicationAnswer = answer;
    }

    public ApplicationItem(Long id, ApplicationQuestion question, ApplicationAnswer answer) {
        this.id = id;
        this.applicationQuestion = question;
        this.applicationAnswer = answer;
    }

    public void updateContents(ApplicationItem updated) {
        this.applicationQuestion = updated.getApplicationQuestion();
        this.applicationAnswer = updated.getApplicationAnswer();
    }

    public void registerCategory(Category category) {
        this.categoryId = category.getId();
    }
}
