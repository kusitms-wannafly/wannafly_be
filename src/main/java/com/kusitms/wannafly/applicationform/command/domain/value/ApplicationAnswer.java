package com.kusitms.wannafly.applicationform.command.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ApplicationAnswer {

    @Column(name = "application_answer", nullable = false)
    @Lob
    private String content;
}
