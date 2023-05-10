package com.kusitms.wannafly.applicationform.query.dto;

public record ApplicationItemResponse(
        Long applicationItemId,
        String applicationQuestion,
        String applicationAnswer
) {
}
