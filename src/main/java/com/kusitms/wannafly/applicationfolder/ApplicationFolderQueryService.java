package com.kusitms.wannafly.applicationfolder;

import com.kusitms.wannafly.applicationfolder.dto.ApplicationFolderResponse;
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
        return applicationFolderQueryRepository.findAllByMemberIdOrderByYearDesc(memberId)
                .stream()
                .map(folder -> new ApplicationFolderResponse(folder.getYear(), 0))
                .toList();
    }
}
