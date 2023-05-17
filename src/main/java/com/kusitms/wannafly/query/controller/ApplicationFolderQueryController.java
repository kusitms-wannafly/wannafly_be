package com.kusitms.wannafly.query.controller;


import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.ApplicationFolderResponse;
import com.kusitms.wannafly.query.service.ApplicationFolderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/application-folders")
@RequiredArgsConstructor
public class ApplicationFolderQueryController {
    private final ApplicationFolderQueryService applicationFolderQueryService;

    @GetMapping
    public ResponseEntity<List<ApplicationFolderResponse>> checkFolders(LoginMember loginMember) {
        Long memberId = loginMember.id();
        List<ApplicationFolderResponse> responses = applicationFolderQueryService.extractYearsByMemberId(memberId);
        return ResponseEntity.ok(responses);
    }
}
