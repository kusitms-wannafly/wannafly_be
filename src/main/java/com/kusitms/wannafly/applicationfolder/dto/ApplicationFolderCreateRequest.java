package com.kusitms.wannafly.applicationfolder.dto;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;

public record ApplicationFolderCreateRequest(
        Integer year
) {
    public ApplicationFolder toDomain(Long memberId) {
        return ApplicationFolder.createEmptyFolder(
                memberId, year
        );
    }
}
