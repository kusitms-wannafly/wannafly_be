package com.kusitms.wannafly.command.applicationfolder.application;

import com.kusitms.wannafly.command.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.command.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.command.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.FOLDER_CREATE_2023;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ApplicationFolderServiceTest extends ServiceTest {
    @Autowired
    private ApplicationFolderService applicationFolderService;
    @Autowired
    private ApplicationFolderRepository applicationFolderRepository;

    @DisplayName("지원서 보관함을 생성할 때")
    @Nested
    class CreateTest {
        @Test
        void 지원서_보관함의_ID_값을_준다() {
            //when
            Long folderId = applicationFolderService.createFolder(new LoginMember(1L), FOLDER_CREATE_2023);
            //then
            Optional<ApplicationFolder> savedFolder = applicationFolderRepository.findById(folderId);
            assertAll(
                    () -> assertThat(folderId).isEqualTo(folderId),
                    () -> assertThat(savedFolder).isPresent()
            );
        }

        @Test
        void 이미_있는_보관함_년도면_예외가_발생한다() {
            //given
            LoginMember folderCreate = new LoginMember(1L);
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2023);
            //when then
            assertThatThrownBy(() -> applicationFolderService.createFolder(
                    folderCreate, FOLDER_CREATE_2023)
            )
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.MEMBER_DUPLICATE_YEAR);
        }
    }
}
