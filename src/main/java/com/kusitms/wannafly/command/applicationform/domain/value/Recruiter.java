package com.kusitms.wannafly.command.applicationform.domain.value;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class Recruiter {

    @Column(nullable = false, name = "recruiter")
    private String value;

    public Recruiter(String value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(String value) {
        if (value.isBlank()) {
            throw BusinessException.from(ErrorCode.EMPTY_RECRUITER);
        }
    }
}
