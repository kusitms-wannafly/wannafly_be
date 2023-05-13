package com.kusitms.wannafly.applicationform.query.dto;

public record QueryConditionRequest(
        Long cursor,
        Integer size,
        Integer year
) {
}
