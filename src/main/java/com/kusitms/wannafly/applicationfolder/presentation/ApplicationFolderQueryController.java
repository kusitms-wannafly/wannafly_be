package com.kusitms.wannafly.applicationfolder.presentation;


import com.kusitms.wannafly.applicationfolder.ApplicationFolderQueryService;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderResponse;
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
public class ApplicationFolderQueryController {
    private final ApplicationFolderQueryService applicationFolderQueryService;

    @GetMapping
    public ResponseEntity<List<ApplicationFolderResponse>> checkFolders(LoginMember loginMember) {
        Long memberId = loginMember.id();
        List<ApplicationFolderResponse> responses = applicationFolderQueryService.extractYearsByMemberId(memberId);
        return ResponseEntity.ok(responses);
    }
}
