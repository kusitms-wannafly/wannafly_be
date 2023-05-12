package com.kusitms.wannafly.applicationform.command.dto;

public record ApplicationItemUpdateRequest(
        Long applicationItemId,
        String applicationQuestion,
        String applicationAnswer
) {
}
