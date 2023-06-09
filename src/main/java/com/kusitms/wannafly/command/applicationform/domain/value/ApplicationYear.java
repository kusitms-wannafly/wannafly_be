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
public class ApplicationYear {

    @Column(nullable = false, name = "years")
    private Integer value;

    public ApplicationYear(Integer value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(Integer value) {
        if (value <= 0) {
            throw BusinessException.from(ErrorCode.INVALID_YEAR);
        }
    }
}
