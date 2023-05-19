package com.kusitms.wannafly.query.service;

import com.kusitms.wannafly.query.dto.CategoryResponse;
import com.kusitms.wannafly.query.repository.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryQueryService {
    private final CategoryQueryRepository categoryQueryRepository;

    public List<CategoryResponse> extractCategoryByMemberId(Long memberId) {
        return categoryQueryRepository.findAllByMemberId(memberId)
                .stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName()))
                .toList();
    }
}
