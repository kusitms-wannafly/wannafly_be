package com.kusitms.wannafly.applicationfolder.presentation;


import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderResponse;
import com.kusitms.wannafly.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/application-folders")
@RequiredArgsConstructor
public class ApplicationFolderCheckController {
    private final ApplicationFolderService applicationFolderService;

    @GetMapping
    public ResponseEntity<List<ApplicationFolderResponse>> checkFolders(LoginMember loginMember) {
        Long memberId = loginMember.id();
        List<ApplicationFolderResponse> responses = applicationFolderService.extractYearsByMemberId(memberId);
        return ResponseEntity.ok(responses);
    }
}
