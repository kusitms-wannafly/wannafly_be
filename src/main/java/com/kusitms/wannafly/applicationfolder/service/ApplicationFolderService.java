package com.kusitms.wannafly.applicationfolder.service;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderCreateRequest;
import com.kusitms.wannafly.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationFolderService {
    private final ApplicationFolderRepository applicationFolderRepository;

    public Long createFolder(LoginMember loginMember, ApplicationFolderCreateRequest request){
        ApplicationFolder applicationFolder = request.toDomain(loginMember.id());
        applicationFolderRepository.save(applicationFolder);
        return applicationFolder.getId();
    }
}
