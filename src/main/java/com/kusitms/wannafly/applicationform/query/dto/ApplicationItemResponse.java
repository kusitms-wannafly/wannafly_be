package com.kusitms.wannafly.applicationform.query.dto;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationItem;

public record ApplicationItemResponse(
        Long applicationItemId,
        String applicationQuestion,
        String applicationAnswer
) {
    public static ApplicationItemResponse from(ApplicationItem applicationItem) {
        return new ApplicationItemResponse(
                applicationItem.getId(),
                applicationItem.getApplicationQuestion().getContent(),
                applicationItem.getApplicationAnswer().getContent()
        );
    }
}
