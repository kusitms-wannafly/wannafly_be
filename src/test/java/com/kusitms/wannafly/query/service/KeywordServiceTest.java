package com.kusitms.wannafly.query.service;

import com.kusitms.wannafly.command.applicationform.application.ApplicationFormService;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.CategoryItemResponse;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class KeywordServiceTest extends ServiceTest {
    @Autowired
    private KeywordService keywordService;
    @Autowired
    private ApplicationFormService applicationFormService;


    @DisplayName("키워드로_조회할때")
    @Nested
    class FindAllTest {

        private final Long memberId = 1L;
        private final LoginMember loginMember = new LoginMember(memberId);
        private final Long memberId2 = 2L;
        private final LoginMember loginMember2 = new LoginMember(memberId2);

        private Long formId1;
        private Long formId2;

        @BeforeEach
        void initForm() {
            LoginMember loginMember = new LoginMember(1L);
            formId1 = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST_KEYWORD); // 항목 2개 저장 큐시즘,솝트
            formId2 = applicationFormService.createForm(loginMember, FORM_CREATE_REQUEST_KEYWORD2); // 항목 2개 저장 LG,SK

        }

        @Test
        void 키워드에_해당되는_지원항목이_조회된다() {
            // when
            List<CategoryItemResponse> actual = keywordService.findByKeyword("노력", loginMember);

            // then
            assertAll(
                    () -> assertThat(actual)
                            .map(item -> item.applicationItem().applicationItemId())
                            .containsOnly(1L, 2L, 3L),
                    () -> assertThat(actual)
                            .map(CategoryItemResponse::applicationFormId)
                            .containsOnly(formId1, formId2)
            );
        }


        @Test
        void 키워드가_질문에_있어도_지원항목이_조회된다() {
            // when
            List<CategoryItemResponse> actual = keywordService.findByKeyword("큐시즘", loginMember);

            // then
            assertAll(
                    () -> assertThat(actual)
                            .map(item -> item.applicationItem().applicationItemId())
                            .containsOnly(1L),
                    () -> assertThat(actual)
                            .map(CategoryItemResponse::applicationFormId)
                            .containsOnly(formId1)
            );
        }


        @Test
        void 키워드가_답변에_있어도_지원항목이_조회된다() {
            // when
            List<CategoryItemResponse> actual = keywordService.findByKeyword("공부", loginMember);

            // then
            assertAll(
                    () -> assertThat(actual)
                            .map(item -> item.applicationItem().applicationItemId())
                            .containsOnly(4L),
                    () -> assertThat(actual)
                            .map(CategoryItemResponse::applicationFormId)
                            .containsOnly(formId2)
            );
        }


        @Test
        void 키워드가_공백이면_빈문자열이_반환된다() {
            //when
            List<CategoryItemResponse> actual = keywordService.findByKeyword("", loginMember);
            //then
            assertThat(actual).isEmpty();
        }

        @Test
        void 맞는_키워드가_없으면_빈문자열이_반환된다() {
            //when
            List<CategoryItemResponse> actual = keywordService.findByKeyword("없다", loginMember);
            //then
            assertThat(actual).isEmpty();
        }

        @Test
        void 지원항목을_안써도_빈문자열이_반환된다() {
            //when
            List<CategoryItemResponse> actual = keywordService.findByKeyword("큐시즘", loginMember2);
            //then
            assertThat(actual).isEmpty();
        }
    }
}
