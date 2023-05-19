package com.kusitms.wannafly.command.category.service;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.command.category.domain.Category;
import com.kusitms.wannafly.command.category.domain.CategoryRepository;
import com.kusitms.wannafly.command.category.dto.CategoryCreateRequest;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CheckedCategoryService checkedCategoryService;

    public Long createCategory(LoginMember loginMember, CategoryCreateRequest request) {
        Long memberId = loginMember.id();
        Category category = Category.createCategory(memberId, request.name());
        checkDuplicateName(memberId, request.name());
        categoryRepository.save(category);
        return category.getCategoryId();
    }

    private void checkDuplicateName(Long memberId, String name) {
        if (categoryRepository.existsByMemberIdAndName(memberId, name)) {
            throw BusinessException.from(ErrorCode.MEMBER_DEPULICATE_NAME);
        }
    }

    public void deleteCategory(Long categoryId, LoginMember loginMember) {
        Long memberId = loginMember.id();
        Category category = checkedCategoryService.checkCategoryIdAndGet(categoryId, memberId);
        categoryRepository.delete(category);
    }
}
