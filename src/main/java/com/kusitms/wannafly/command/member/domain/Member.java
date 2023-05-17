package com.kusitms.wannafly.command.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_column_in_member", columnNames = "email")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String pictureUrl;

    private String registrationId;

    public Member(String name, String email, String pictureUrl, String registrationId) {
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.registrationId = registrationId;
    }
}
