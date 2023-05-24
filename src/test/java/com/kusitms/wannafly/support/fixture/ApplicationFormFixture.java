package com.kusitms.wannafly.support.fixture;

import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormCreateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationFormUpdateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationItemCreateRequest;
import com.kusitms.wannafly.command.applicationform.dto.ApplicationItemUpdateRequest;

import java.util.List;

public class ApplicationFormFixture {

    private static final String RECRUITER = "큐시즘";

    public static final ApplicationItemCreateRequest ITEM_CREATE_REQUEST = new ApplicationItemCreateRequest(
            ApplicationFormFixture.QUESTION1, ApplicationFormFixture.ANSWER1
    );
    public static final ApplicationItemCreateRequest ITEM_CREATE_REQUEST_KUSITMS = new ApplicationItemCreateRequest(
            ApplicationFormFixture.QUESTION_KUSITMS, ApplicationFormFixture.ANSWER_KUSITMS
    );
    public static final ApplicationItemCreateRequest ITEM_CREATE_REQUEST_SOPT = new ApplicationItemCreateRequest(
            ApplicationFormFixture.QUESTION_SOPT, ApplicationFormFixture.ANSWER_SOPT
    );
    public static final ApplicationFormCreateRequest FORM_CREATE_REQUEST = new ApplicationFormCreateRequest(
            RECRUITER,
            2023,
            "first_half",
            List.of(ITEM_CREATE_REQUEST, ITEM_CREATE_REQUEST, ITEM_CREATE_REQUEST)
    );
    public static final ApplicationFormCreateRequest FORM_CREATE_REQUEST_KEYWORD = new ApplicationFormCreateRequest(
            RECRUITER,
            2023,
            "first_half",
            List.of(ITEM_CREATE_REQUEST_KUSITMS, ITEM_CREATE_REQUEST_SOPT)
    );
    public static final ApplicationFormUpdateRequest FORM_UPDATE_REQUEST = new ApplicationFormUpdateRequest(
            RECRUITER + " 28기",
            2024,
            "second_half",
            List.of(
                    new ApplicationItemUpdateRequest(
                            1L, ApplicationFormFixture.QUESTION2, ApplicationFormFixture.ANSWER2
                    ),
                    new ApplicationItemUpdateRequest(
                            2L, ApplicationFormFixture.QUESTION1, ApplicationFormFixture.ANSWER1
                    ),
                    new ApplicationItemUpdateRequest(
                            3L, ApplicationFormFixture.QUESTION2, ApplicationFormFixture.ANSWER2
                    )
            )
    );

    public static final ApplicationFormUpdateRequest INVALID_FORM_UPDATE_REQUEST = new ApplicationFormUpdateRequest(
            RECRUITER + " 28기",
            2024,
            "second_half",
            List.of(
                    new ApplicationItemUpdateRequest(
                            1L, ApplicationFormFixture.QUESTION2, ApplicationFormFixture.ANSWER2
                    ),
                    new ApplicationItemUpdateRequest(
                            2L, ApplicationFormFixture.QUESTION2, ApplicationFormFixture.ANSWER2
                    ),
                    new ApplicationItemUpdateRequest(
                            null, ApplicationFormFixture.QUESTION1, ApplicationFormFixture.ANSWER1
                    ),
                    new ApplicationItemUpdateRequest(
                            null, ApplicationFormFixture.QUESTION2, ApplicationFormFixture.ANSWER2
                    )
            )
    );

    public static final ApplicationFormCreateRequest FORM_2019_1 = new ApplicationFormCreateRequest(
            RECRUITER,
            2019,
            "first_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2019_2 = new ApplicationFormCreateRequest(
            RECRUITER,
            2019,
            "second_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2020_1 = new ApplicationFormCreateRequest(
            RECRUITER,
            2020,
            "first_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2020_2 = new ApplicationFormCreateRequest(
            RECRUITER,
            2020,
            "first_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2021_1 = new ApplicationFormCreateRequest(
            RECRUITER,
            2021,
            "first_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2021_2 = new ApplicationFormCreateRequest(
            RECRUITER,
            2021,
            "second_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2022_1 = new ApplicationFormCreateRequest(
            RECRUITER,
            2022,
            "first_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2022_2 = new ApplicationFormCreateRequest(
            RECRUITER,
            2022,
            "second_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2023_1 = new ApplicationFormCreateRequest(
            RECRUITER,
            2023,
            "first_half",
            List.of()
    );

    public static final ApplicationFormCreateRequest FORM_2023_2 = new ApplicationFormCreateRequest(
            RECRUITER,
            2023,
            "second_half",
            List.of()
    );

    public final static String QUESTION1 = "본 직군으로 지원을 결정하시게 된 계기를 말하고, 그 동안의 노력도 말해주세요. (1000자 이내)";
    public final static String ANSWER1 = """
            저는 Back-end 분야를 희망합니다. 백엔드 개발을 진행하고 학습할 수록 여러가지 매력을 느꼈습니다.
            첫 번째로 서비스의 비즈니스 규칙을 구현할 수 있다는 점입니다. 서비스에는 여러 규칙과 제약이 존재하고 이들은 반드시 지켜지고 절차대로 이행되어야 합니다. 버그를 줄이고 효율적으로 규칙과 제약을 구현하기 위해 고민하고 동료들과 소통하는 과정이 가치있게 느껴졌고 재미 또한 느꼈습니다.
            두 번째로 규칙과 제약을 구현한 코드를 보호하고 유지보수하기 쉽게 하기 위해 여러 디자인 패턴이나 개발 방법론을 고민하고 적용하는 과정도 흥미로웠습니다. 코드의 길이부터 의존성 방향, 나아가 전체적인 소프트웨어 아키텍처 설계까지 정답이 없는 문제에 자신과 팀만의 답을 찾아나가는 과정을 계속 겪어보고 싶다는 생각을 하고 있습니다.
            백엔드 개발자를 희망한 이후부터 필요한 역량을 쌓기 위해 꾸준히 학습해왔습니다. 처음에는 인터넷 강의로 학습을 시작했습니다. 혼자보다 함께 학습하고 싶어서 스터디에 참여해 보기도 했지만 독학에 한계를 느꼈습니다. 양질의 학습과 경험을 하고 싶어서 ‘우아한테크코스’라는 교육 프로그램에 지원했고 한 달 간 코딩 테스트와 과제 전형을 거쳐 합격할 수 있었습니다. 교육 중에는 매주 과제를 수행하고 코드 리뷰를 받으며 TDD, 객체지향, 자바 및 스프링 등의 학습을 진행했습니다. 학습이 어느정도 진행된 후에는 5개월 간 팀 프로젝트를 진행했으며 지속적인 배포와 유지보수를 경험할 수 있었습니다.
            교육을 수료한 이후에는 졸업 프로젝트와 IT 연합 동아리에서 백엔드 개발자로 참여하여 협업을 계속 이어나가고 있습니다.
            """;

    public final static String QUESTION2 = "가장 열정을 가지고 임했던 프로젝트(목표/과제 등)를 소개해 주시고, " +
            "해당 프로젝트의 수행 과정 및 결과에 대해 기재해 주세요 (1000자 이내)";
    public final static String ANSWER2 = """
            3일 간의 짧은 주기의 도전을 반복하도록 유도하는 SNS 기반의  동기부여 서비스를 만드는 프로젝트에서 백엔드 개발자로 참여했었습니다. 당시 저는 개선점이나 문제점을 계속 생각하고 해결 방법을 찾으려고 했습니다.
            서비스에 알림 기능 도입을 담당했을 때 였습니다. 알림을 발송해야 하는 상황은 대표적으로 챌린지에 도전하는 경우가 있습니다. 챌린지 도전 로직과 알림의 저장 및 발송 로직의 결합도가 높아질 것을 우려하여  이벤트 방식으로 기능을 구현했습니다.
            알림 기능을 배포한 뒤, 평소에 하는 운동 챌린지를 인증하려고 했습니다. 그런데 인증이 안 되는 장애가 발생했습니다. 원인을 찾아보니 실수로 데이터베이스에 알림과 관련한 테이블 스키마에 문제가 있어서 발생하는 장애였습니다. 스키마를 수정하여 금방 해결할 수는 있었습니다.
            하지만 알림 때문에 챌린지 인증을 못 하게 되는 상황이 이상하다고 느껴졌습니다. 챌린지 인증은 서비스의 핵심 기능입니다. 알림을 보내지 못하는 상황이 되었다고 해서 핵심 기능까지 막을 수는 없다고 판단했습니다. 알림 기능은 이미 배포되었고 잘 동작하고 있었지만 이를 해결하고 싶었습니다.
            이벤트로 구현하여 코드 간 결합은 없었지만 챌린지 인증과 알림 저장 및 발송 로직은 동기적으로 한 트랜잭션에서 실행됩니다. 이를 분리하기 위해 챌린지 인증을 커밋 후 비동기로 알림을 처리하도록 했습니다. 알림 발송 중에 예외가 발생해도 챌린지 인증은 무사히 커밋 되는 테스트도 통과했고 서비스의 핵심 기능이 알림에 의해 영향받는 일은 이제 없을 것입니다.
            한 가지 아쉬운 점은 챌린지 도전을 했음에도 알림이 발송되지 않는 상황이 발생할 수도 있다는 것입니다. 스레드가 분리되어 있기 때문에 이벤트 처리에서 예외가 발생하면 알림이 손실됩니다. 여러 방법을 찾아본 결과 챌린지 도전 이벤트를 DB에 저장하여 최종적 일관성을 맞추는 방식이나 메시지 큐 등을 이용할 수 있다는 것을 알았지만 끝내 개선하지는 못해 아쉬웠습니다.
            """;

    public final static String QUESTION_KUSITMS = "큐시즘에 들어오기 위해 한 일을 쓰시오";
    public final static String ANSWER_KUSITMS = """
            저는 큐시즘에 들어오기 위해 정말 많은 노력을 하였습니다. 여러가지 프로젝트와 개인 공부를 하였습니다
            """;
    public final static String QUESTION_SOPT = "솝트에 들어오기 위해 한 일을 쓰시오";
    public final static String ANSWER_SOPT = """
            저는 솝트에 들어오기 위해 정말 많은 노력을 하였습니다. 여러가지 프로젝트와 개인 공부를 하였습니다
            """;
}
