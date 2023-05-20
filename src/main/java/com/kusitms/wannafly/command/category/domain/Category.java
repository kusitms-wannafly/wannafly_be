package com.kusitms.wannafly.command.category.domain;

import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false, name = "name")
    private String name;

    public static Category createCategory(Long memberId, String name) {
        return new Category(memberId, name);
    }

    private Category(Long memberId, String name) {
        validateName(name);
        this.memberId = memberId;
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw BusinessException.from(ErrorCode.INVALID_NAME);
        }
    }

    public boolean isNotMember(Long memberId) {
        return !this.memberId.equals(memberId);
    }
}
