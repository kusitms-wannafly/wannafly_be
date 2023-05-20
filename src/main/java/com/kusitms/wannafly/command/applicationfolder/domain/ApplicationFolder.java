package com.kusitms.wannafly.command.applicationfolder.domain;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApplicationFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_folder_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, name = "years")
    private Integer year;

    public static ApplicationFolder createFolder(Long memberId, Integer year) {
        return new ApplicationFolder(memberId, year);
    }

    private ApplicationFolder(Long memberId, Integer year) {
        validateYear(year);
        this.memberId = memberId;
        this.year = year;
    }

    private void validateYear(Integer year) {
        if (year <= 0) {
            throw BusinessException.from(ErrorCode.INVALID_YEAR);
        }
    }
}
