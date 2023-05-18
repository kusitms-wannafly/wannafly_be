package com.kusitms.wannafly.command.category.application;

import com.kusitms.wannafly.command.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.command.applicationform.domain.ApplicationForm;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.command.category.domain.Category;
import com.kusitms.wannafly.command.category.domain.CategoryRepository;
import com.kusitms.wannafly.command.category.service.CategoryService;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.FOLDER_CREATE_2023;
import static com.kusitms.wannafly.support.fixture.ApplicationFormFixture.FORM_CREATE_REQUEST;
import static com.kusitms.wannafly.support.fixture.CategoryFixture.CATEGORY_CREATE_EXPERIENCE;
import static com.kusitms.wannafly.support.fixture.CategoryFixture.CATEGORY_CREATE_MOTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryServiceTest extends ServiceTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    private final LoginMember loginMember = new LoginMember(1L);

    @DisplayName("카테고리를 생성할때")
    @Nested
    class CreateTest {
        @Test
        void 카테고리_ID_값을_준다() {
            //when
            Long categoryId = categoryService.createCategory(new LoginMember(1L), CATEGORY_CREATE_MOTIVE);
            //then
            Optional<Category> savedCategory = categoryRepository.findById(categoryId);
            assertAll(
                    () -> assertThat(categoryId).isEqualTo(categoryId),
                    () -> assertThat(savedCategory).isPresent()
            );
        }

        @Test
        void 이미_있는_카테고리면_예외가_발생한다() {
            //given
            LoginMember loginMember = new LoginMember(1L);
            categoryService.createCategory(loginMember, CATEGORY_CREATE_MOTIVE);
            //when then
            assertThatThrownBy(() -> categoryService.createCategory(
                    loginMember, CATEGORY_CREATE_MOTIVE)
            )
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.MEMBER_DEPULICATE_NAME);
        }
    }

    @DisplayName("카테고리 삭제할 때")
    @Nested
    class DeleteTest {

        @Test
        void 로그인_회원이_카테고리_작성자면_삭제_가능하다() {
            // given
            Long CategoryId = categoryService.createCategory(loginMember, CATEGORY_CREATE_MOTIVE);

            // when
            categoryService.deleteCategory(CategoryId, loginMember);

            // then
            Optional<Category> actual = categoryRepository.findById(CategoryId);
            assertThat(actual).isEmpty();
        }

        @Test
        void 로그인_회원이_카테고리_작성자가_아니면_예외가_발생한다() {
            // given
            Long categoryId = categoryService.createCategory(loginMember, CATEGORY_CREATE_MOTIVE);

            // when then
            LoginMember requester = new LoginMember(2L);
            assertThatThrownBy(() -> categoryService.deleteCategory(categoryId, requester))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.INVALID_MEMBER_OF_CATEGORY);
        }

        @Test
        void 없는_카테고리는_삭제할수_없다() {
            // given
            LoginMember loginMember = new LoginMember(1L);

            // when then
            assertThatThrownBy(() -> categoryService.deleteCategory(1L, loginMember))
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.NOT_FOUND_CATEGORY_ID);
        }
    }
}
