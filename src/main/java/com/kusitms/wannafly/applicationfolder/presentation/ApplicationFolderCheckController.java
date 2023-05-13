package com.kusitms.wannafly.applicationfolder.presentation;


import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateResponse;
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
    public ResponseEntity<List<ApplicationFolderCreateResponse>> CheckFolder(LoginMember loginMember){
        Long memberId = loginMember.id();
        List<ApplicationFolderCreateResponse> yearsList = applicationFolderService.extractYearsByMemberId(memberId);
        return ResponseEntity.ok(yearsList);
    }
}
