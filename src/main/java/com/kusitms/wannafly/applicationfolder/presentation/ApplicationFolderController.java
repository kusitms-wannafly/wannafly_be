package com.kusitms.wannafly.applicationfolder.presentation;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateRequest;
import com.kusitms.wannafly.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.member.domain.MemberRepository;
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
                                             LoginMember loginMember){
        if (ApplicationFolderRepository.existsByYear(loginMember,request.year())){
            throw BusinessException.from(ErrorCode.MEMBER_DUPLICATE_YEAR);
        }
        Long folderId = applicationFolderService.createFolder(loginMember,request);
        return ResponseEntity.created(URI.create("/application-folder/"+folderId))
                .build();
    }
}
