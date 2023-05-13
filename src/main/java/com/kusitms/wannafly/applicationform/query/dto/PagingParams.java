package com.kusitms.wannafly.applicationform.query.dto;

public record PagingParams(
        Long cursor,
        Integer size,
        Integer year
) {
}
