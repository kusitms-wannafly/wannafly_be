package com.kusitms.wannafly.applicationfolder.presentation;


import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/application-folders")
@RequiredArgsConstructor
public class ApplicationFolderCheckController {
    private final ApplicationFolderService applicationFolderService;
    private final ApplicationFolderRepository applicationFolderRepository;
    @GetMapping
    public ResponseEntity<List> CheckFolder(LoginMember loginMember){
        Long memberId = loginMember.id();
        List<ApplicationFolder> folders = applicationFolderRepository.findAll();
        List<Integer> years = applicationFolderService.extractYearsByMemberId(folders, memberId);
        List<Map<String, Integer>> yearsList = applicationFolderService.convertToMap(years);
        return ResponseEntity.ok(yearsList);
    }
}
