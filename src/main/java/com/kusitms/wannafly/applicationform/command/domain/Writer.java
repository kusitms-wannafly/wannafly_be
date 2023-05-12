package com.kusitms.wannafly.applicationform.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Writer {

    @Column(nullable = false, name = "member_id")
    private Long id;
}
