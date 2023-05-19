package com.kusitms.wannafly.query.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PagingParams {

    private static final int DEFAULT_FORMS_SIZE = 9;

    private Long cursor;
    private Integer size;
    private Integer year;

    public Long getCursor() {
        return cursor;
    }

    public Integer getSize() {
        if (size == null) {
            return DEFAULT_FORMS_SIZE;
        }
        return size;
    }

    public Integer getYear() {
        return year;
    }
}
