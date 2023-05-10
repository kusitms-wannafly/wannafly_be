package com.kusitms.wannafly.applicationfolder.domain;

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
    @Column(nullable = false,name ="years")
    private Integer year;

    private ApplicationFolder(Long memberId, Integer year){
        this.memberId = memberId;
        this.year = year;
    }
}
