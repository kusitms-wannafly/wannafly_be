package com.kusitms.wannafly.applicationform.command.domain;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
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
        validateContent(content);
        this.content = content;
    }

    private void validateContent(String content) {
        if (content.isBlank()) {
            throw BusinessException.from(ErrorCode.EMPTY_QUESTION);
        }
    }
}
