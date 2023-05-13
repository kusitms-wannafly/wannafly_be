package com.kusitms.wannafly.applicationfolder.service;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateRequest;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateResponse;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationFolderService {
    private final ApplicationFolderRepository applicationFolderRepository;

    public Long createFolder(LoginMember loginMember, ApplicationFolderCreateRequest request) {
        ApplicationFolder applicationFolder = request.toDomain(loginMember.id());
        Long memberId = loginMember.id();
        if (applicationFolderRepository.existsByMemberIdAndYear(memberId,request.year())){
            throw BusinessException.from(ErrorCode.MEMBER_DUPLICATE_YEAR);
        }
        applicationFolderRepository.save(applicationFolder);
        return applicationFolder.getId();
    }

    public List<ApplicationFolderCreateResponse> extractYearsByMemberId(Long memberId) {
        return applicationFolderRepository.findAllByMemberIdOrderByYearDesc(memberId)
                .stream()
                .map(folder -> new ApplicationFolderCreateResponse(folder.getYear()))
                .toList();
    }
}
