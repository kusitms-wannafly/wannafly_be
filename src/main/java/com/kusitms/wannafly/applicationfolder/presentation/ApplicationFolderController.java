package com.kusitms.wannafly.applicationfolder.presentation;

import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateRequest;
import com.kusitms.wannafly.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/application-folders")
@RequiredArgsConstructor
public class ApplicationFolderController {
    private final ApplicationFolderService applicationFolderService;

    @PostMapping
    public ResponseEntity<Void> createFolder(@RequestBody ApplicationFolderCreateRequest request,
                                             LoginMember loginMember) {
        Long folderId = applicationFolderService.createFolder(loginMember, request);
        return ResponseEntity.created(URI.create("/application-folders/" + folderId))
                .build();
    }
}
