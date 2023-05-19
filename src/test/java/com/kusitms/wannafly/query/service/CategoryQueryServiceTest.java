package com.kusitms.wannafly.query.service;

import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.command.category.service.CategoryService;
import com.kusitms.wannafly.query.dto.CategoryResponse;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kusitms.wannafly.support.fixture.CategoryFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryQueryServiceTest extends ServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryQueryService categoryQueryService;
    private final Long memberId = 1L;
    private final LoginMember loginMember = new LoginMember(memberId);


    @DisplayName("카테고리를 조회할 때")
    @Nested
    class FindAllTest {
        @Test
        void 카테고리를_생성했다면_조회한다() {
            //given
            categoryService.createCategory(loginMember, CATEGORY_CREATE_MOTIVE);

            //when
            List<CategoryResponse> actual = categoryQueryService.extractCategoryByMemberId(memberId);

            //then
            assertAll("Checking order of Categories",
                    () -> assertThat(actual).hasSize(1),
                    () -> assertThat(actual).extracting(CategoryResponse::categoryId)
                            .containsExactly(1L),
                    () -> assertThat(actual).extracting(CategoryResponse::name)
                            .containsExactly("지원동기")
            );
        }


        @Test
        void 카테고리가_여러개일때_모두_조회한다() {
            //given
            categoryService.createCategory(loginMember, CATEGORY_CREATE_MOTIVE);
            categoryService.createCategory(loginMember, CATEGORY_CREATE_PROS_CONS);
            categoryService.createCategory(loginMember, CATEGORY_CREATE_EXPERIENCE);

            //when
            List<CategoryResponse> actual = categoryQueryService.extractCategoryByMemberId(memberId);

            //then
            assertAll("Checking order of Categories",
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual).extracting(CategoryResponse::name)
                            .containsExactly("지원동기", "장단점", "경험")
            );
        }
    }
}
