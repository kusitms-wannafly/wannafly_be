package com.kusitms.wannafly.query.controller;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.ApplicationFormResponse;
import com.kusitms.wannafly.query.dto.PagingParams;
import com.kusitms.wannafly.query.dto.SimpleFormResponse;
import com.kusitms.wannafly.query.service.ApplicationFormQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-forms")
@RequiredArgsConstructor
public class ApplicationFormQueryController {

    private final ApplicationFormQueryService applicationFormQueryService;


    @GetMapping("{applicationFormId}")
    public ResponseEntity<ApplicationFormResponse> findOne(@PathVariable Long applicationFormId,
                                                           LoginMember loginMember) {
        ApplicationFormResponse response = applicationFormQueryService.findOne(applicationFormId, loginMember);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SimpleFormResponse>> findAllByCondition(LoginMember loginMember,
                                                                       @ModelAttribute PagingParams params) {
        List<SimpleFormResponse> responses = applicationFormQueryService.findAllByCondition(loginMember, params);
        return ResponseEntity.ok(responses);
    }
}
