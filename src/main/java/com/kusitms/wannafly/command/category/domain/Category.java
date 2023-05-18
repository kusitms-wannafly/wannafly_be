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

    @Column(nullable = false, name = "names")
    private String name;

    public static Category createCategoryByName(Long memberId, String name) {
        return new Category(memberId, name);
    }
    private Category(Long memberId, String name){
        validateName(name);
        this.memberId=memberId;
        this.name = name;
    }
    private void validateName(String name){
        if(name == ""){
            throw BusinessException.from(ErrorCode.INVALID_NAME);
        }
    }
}
