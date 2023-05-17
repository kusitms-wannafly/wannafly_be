package com.kusitms.wannafly.query.service;

import com.kusitms.wannafly.command.applicationfolder.service.ApplicationFolderService;
import com.kusitms.wannafly.command.auth.LoginMember;
import com.kusitms.wannafly.query.dto.ApplicationFolderResponse;
import com.kusitms.wannafly.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kusitms.wannafly.support.fixture.ApplicationFolderFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationFolderQueryServiceTest extends ServiceTest {

    @Autowired
    private ApplicationFolderQueryService applicationFolderQueryService;

    @Autowired
    private ApplicationFolderService applicationFolderService;

    private final Long memberId = 1L;
    private final LoginMember folderCreate = new LoginMember(memberId);


    @DisplayName("지원서 보관함을 조회할 때")
    @Nested
    class FindAllTest {
        @Test
        void 로그인_회원이_지원서_보관함을_생성했다면_조회한다() {
            //given
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2023);

            //when
            List<ApplicationFolderResponse> actual = applicationFolderQueryService.extractYearsByMemberId(memberId);

            //then
            assertAll("Checking order of yearList",
                    () -> assertThat(actual).hasSize(1),
                    () -> assertThat(actual).extracting(ApplicationFolderResponse::year)
                            .containsExactly(2023),
                    () -> assertThat(actual).extracting(ApplicationFolderResponse::count)
                            .containsExactly(0)
            );
        }

        @Test
        void 지원서_보관함이_여러개일때_모두_조회한다() {
            //given
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2023);
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2022);
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2021);

            //when
            List<ApplicationFolderResponse> actual = applicationFolderQueryService.extractYearsByMemberId(memberId);

            //then
            assertAll("Checking order of yearList",
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual).extracting(ApplicationFolderResponse::year)
                            .containsExactly(2023, 2022, 2021)
            );
        }

        @Test
        void 지원서_보관함이_최근_년도순으로_정렬되는지_조회한다() {
            //given
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2022);
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2021);
            applicationFolderService.createFolder(folderCreate, FOLDER_CREATE_2023);


            //when
            List<ApplicationFolderResponse> actual = applicationFolderQueryService.extractYearsByMemberId(memberId);

            //then
            assertAll("Checking order of yearList",
                    () -> assertThat(actual).hasSize(3),
                    () -> assertThat(actual).extracting(ApplicationFolderResponse::year)
                            .containsExactly(2023, 2022, 2021)
            );
        }
    }

}
