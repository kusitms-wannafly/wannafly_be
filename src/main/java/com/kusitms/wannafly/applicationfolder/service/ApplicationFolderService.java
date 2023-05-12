package com.kusitms.wannafly.applicationfolder.service;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateRequest;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationFolderService {
    private final ApplicationFolderRepository applicationFolderRepository;

    public Long createFolder(LoginMember loginMember, ApplicationFolderCreateRequest request) {
        ApplicationFolder applicationFolder = request.toDomain(loginMember.id());
        applicationFolderRepository.save(applicationFolder);
        return applicationFolder.getId();
    }

    public List<Integer> extractYearsByMemberId(List<ApplicationFolder> folders, Long memberId) {
        return folders.stream()
                .filter(folder -> folder.getMemberId().equals(memberId))
                .map(ApplicationFolder::getYear)
                .sorted(Comparator.reverseOrder()) // sort by latest year
                .collect(Collectors.toList());
    }

    public List<Map<String, Integer>> convertToMap(List<Integer> years) {
        List<Map<String, Integer>> result = new ArrayList<>();
        for (Integer year : years) {
            Map<String, Integer> map = new HashMap<>();
            map.put("year", year);
            result.add(map);
        }
        return result;
    }
}
