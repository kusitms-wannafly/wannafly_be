package com.kusitms.wannafly.applicationfolder.service;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateRequest;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderResponse;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationFolderService {
    private final ApplicationFolderRepository applicationFolderRepository;

    public Long createFolder(LoginMember loginMember, ApplicationFolderCreateRequest request) {
        Long memberId = loginMember.id();
        ApplicationFolder applicationFolder = ApplicationFolder.createFolderByYear(memberId, request.year());
        checkDuplicateYear(memberId, request.year());
        applicationFolderRepository.save(applicationFolder);
        return applicationFolder.getId();
    }

    public List<ApplicationFolderResponse> extractYearsByMemberId(Long memberId) {
        return applicationFolderRepository.findAllByMemberIdOrderByYearDesc(memberId)
                .stream()
                .map(folder -> new ApplicationFolderResponse(folder.getYear(), folder.getFormCount()))
                .toList();
    }

    private void checkDuplicateYear(Long memberId, int year) {
        if (applicationFolderRepository.existsByMemberIdAndYear(memberId, year)) {
            throw BusinessException.from(ErrorCode.MEMBER_DUPLICATE_YEAR);
        }
    }
}
