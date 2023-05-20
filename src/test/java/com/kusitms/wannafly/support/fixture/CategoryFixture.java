package com.kusitms.wannafly.support.fixture;

import com.kusitms.wannafly.command.category.dto.CategoryCreateRequest;

public class CategoryFixture {
    public static final CategoryCreateRequest CATEGORY_CREATE_MOTIVE = new CategoryCreateRequest(
            "지원동기"
    );
    public static final CategoryCreateRequest CATEGORY_CREATE_PROS_CONS = new CategoryCreateRequest(
            "장단점"
    );
    public static final CategoryCreateRequest CATEGORY_CREATE_EXPERIENCE = new CategoryCreateRequest(
            "경험"
    );
}
