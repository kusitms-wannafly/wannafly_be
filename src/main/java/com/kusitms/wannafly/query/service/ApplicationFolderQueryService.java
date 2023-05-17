package com.kusitms.wannafly.query.service;

import com.kusitms.wannafly.query.dto.ApplicationFolderResponse;
import com.kusitms.wannafly.query.repository.ApplicationFolderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplicationFolderQueryService {

    private final ApplicationFolderQueryRepository applicationFolderQueryRepository;

    public List<ApplicationFolderResponse> extractYearsByMemberId(Long memberId) {
        return applicationFolderQueryRepository.findAllByMemberIdOrderByYearDesc(memberId);
    }
}
