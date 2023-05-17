package com.kusitms.wannafly.query.dto;

import com.kusitms.wannafly.command.applicationform.domain.ApplicationItem;

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
