package com.kusitms.wannafly.command.category.presentation;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.command.category.dto.CategoryCreateRequest;
import com.kusitms.wannafly.command.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryCreateRequest request,
                                               LoginMember loginMember) {
        Long CategoryId = categoryService.createCategory(loginMember, request);
        return ResponseEntity.created(URI.create("/categories/" + CategoryId))
                .build();
    }
}
