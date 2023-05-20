package com.kusitms.wannafly.command.applicationform.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class ApplicationQuestion {

    @Column(name = "application_question", nullable = false)
    @Lob
    private String content;

    public ApplicationQuestion(String content) {
        this.content = content;
    }
}
