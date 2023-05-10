package com.kusitms.wannafly.applicationfolder.dto;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;

public record ApplicationFolderCreateRequest(
        Integer year
) {
    public ApplicationFolder toDomain(Long memberId) {
    ApplicationFolder folder = ApplicationFolder.createEmptyFolder(
            memberId, year
        );
    return folder;
}
}
