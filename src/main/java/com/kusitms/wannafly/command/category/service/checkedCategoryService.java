package com.kusitms.wannafly.command.category.service;

import com.kusitms.wannafly.command.category.domain.Category;
import com.kusitms.wannafly.command.category.domain.CategoryRepository;
import com.kusitms.wannafly.command.member.domain.Member;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class checkedCategoryService {
    private final CategoryRepository categoryRepository;

    public Category checkCategoryIdAndGet(Long categoryId, Long memberId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> BusinessException.from(ErrorCode.NOT_FOUND_CATEGORY_ID));
        validateMemberId(memberId, category);
        return category;
    }

    private static void validateMemberId(Long memberId, Category category) {
        if (category.isNotMember(memberId)) {
            throw BusinessException.from(ErrorCode.INVALID_MEMBER_OF_CATEGORY);
        }
    }

}
