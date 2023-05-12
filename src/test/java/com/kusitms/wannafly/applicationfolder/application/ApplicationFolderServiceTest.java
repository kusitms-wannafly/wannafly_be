package com.kusitms.wannafly.applicationfolder.application;

import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolder;
import com.kusitms.wannafly.applicationfolder.domain.ApplicationFolderRepository;
import com.kusitms.wannafly.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.auth.LoginMember;
import com.kusitms.wannafly.exception.BusinessException;
import com.kusitms.wannafly.exception.ErrorCode;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
public class ApplicationFolderServiceTest extends ServiceTest {
    @Autowired
    private ApplicationFolderService applicationFolderService;
    @Autowired
    private ApplicationFolderRepository applicationFolderRepository;

    @DisplayName("지원서 보관함을 생성할 때")
    @Nested
    class CreateTest{
        @Test
        void 지원서_보관함의_ID_값을_준다(){
            //when
            Long folderId = applicationFolderService.createFolder(new LoginMember(1L),FOLDER_CREATE_2023);
            //then
            Optional<ApplicationFolder> savedFolder = applicationFolderRepository.findById(folderId);
            assertAll(
                    () -> assertThat(folderId).isEqualTo(folderId),
                    () -> assertThat(savedFolder).isPresent()
            );
        }

        @Test
        void 이미_있는_보관함_년도면_예외가_발생한다(){
            //given
            LoginMember folderCreater = new LoginMember(1L);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2023);
            //when then
            assertThatThrownBy(()->applicationFolderService.createFolder(
                    folderCreater,FOLDER_CREATE_2023)
            )
                    .isInstanceOf(BusinessException.class)
                    .extracting("errorCode")
                    .isEqualTo(ErrorCode.MEMBER_DUPLICATE_YEAR);
        }
    }
    @DisplayName("지원서 보관함을 조회할 때")
    @Nested
    class FindAllTest{
        @Test
        void 로그인_회원이_지원서_보관함을_생성했다면_조회한다(){
            //given
            LoginMember folderCreater = new LoginMember(1L);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2023);

            //when
            List<ApplicationFolder> folders = applicationFolderRepository.findAll();
            List<Map<String, Integer>> yearList = applicationFolderService.extractYearsByMemberId(folders,folderCreater.id());

            //then
            assertAll(
                    () -> assertThat(yearList)
                    .filteredOn(map -> map.get("year").equals(2023))
                    .hasSize(1)
            );
        }
        @Test
        void 지원서_보관함이_여러개일때_모두_조회한다(){
            //given
            LoginMember folderCreater = new LoginMember(1L);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2023);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2022);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2021);

            //when
            List<ApplicationFolder> folders = applicationFolderRepository.findAll();
            List<Map<String, Integer>> yearList = applicationFolderService.extractYearsByMemberId(folders,folderCreater.id());

            //then
            assertAll(
                    () -> assertThat(yearList).hasSize(3),
                    () -> assertThat(yearList)
                    .filteredOn(map -> map.get("year").equals(2023))
                    .hasSize(1),
                    () -> assertThat(yearList)
                    .filteredOn(map -> map.get("year").equals(2022))
                    .hasSize(1),
                    () -> assertThat(yearList)
                    .filteredOn(map -> map.get("year").equals(2021))
                    .hasSize(1)
            );
        }
        @Test
        void 지원서_보관함이_최근_년도순으로_정렬되는지_조회한다(){
            //given
            LoginMember folderCreater = new LoginMember(1L);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2022);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2021);
            applicationFolderService.createFolder(folderCreater,FOLDER_CREATE_2023);


            //when
            List<ApplicationFolder> folders = applicationFolderRepository.findAll();
            List<Map<String, Integer>> yearList = applicationFolderService.extractYearsByMemberId(folders,folderCreater.id());

            //then
            assertAll(
                    ()-> assertThat(yearList).hasSize(3),
                    () -> assertThat(yearList.get(0).get("year")).isEqualTo(2023),
                    () -> assertThat(yearList.get(1).get("year")).isEqualTo(2022),
                    () -> assertThat(yearList.get(2).get("year")).isEqualTo(2021)
            );
        }
    }
}
