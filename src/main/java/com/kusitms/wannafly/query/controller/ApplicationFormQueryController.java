package com.kusitms.wannafly.query.controller;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.query.dto.CategoryItemResponse;
import com.kusitms.wannafly.query.dto.PagingParams;
import com.kusitms.wannafly.query.dto.SimpleFormResponse;
import com.kusitms.wannafly.query.service.ApplicationFormQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationFormQueryController {

    private final ApplicationFormQueryService applicationFormQueryService;


    @GetMapping("/application-forms/{applicationFormId}")
    public ResponseEntity<ApplicationFormResponse> findOne(@PathVariable Long applicationFormId,
                                                           LoginMember loginMember) {
        ApplicationFormResponse response = applicationFormQueryService.findOne(applicationFormId, loginMember);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/application-forms")
    public ResponseEntity<List<SimpleFormResponse>> findAllByCondition(LoginMember loginMember,
                                                                       @ModelAttribute PagingParams params) {
        List<SimpleFormResponse> responses = applicationFormQueryService.findAllByCondition(loginMember, params);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/categories/{categoryId}/application-items")
    public ResponseEntity<List<CategoryItemResponse>> findItemByCategory(LoginMember loginMember,
                                                                         @PathVariable Long categoryId) {
        List<CategoryItemResponse> responses = applicationFormQueryService.findByCategory(categoryId, loginMember);
        return ResponseEntity.ok(responses);
    }
}
