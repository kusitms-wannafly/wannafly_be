package com.kusitms.wannafly.command.applicationform.dto;

public record ApplicationItemUpdateRequest(
        Long applicationItemId,
        String applicationQuestion,
        String applicationAnswer
) {
}
