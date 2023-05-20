package com.kusitms.wannafly.query.controller;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.CategoryResponse;
import com.kusitms.wannafly.query.service.CategoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryQueryController {
    private final CategoryQueryService categoryQueryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> checkCategory(LoginMember loginMember) {
        Long memberId = loginMember.id();
        List<CategoryResponse> responses = categoryQueryService.extractCategoryByMemberId(memberId);
        return ResponseEntity.ok(responses);
    }
}
