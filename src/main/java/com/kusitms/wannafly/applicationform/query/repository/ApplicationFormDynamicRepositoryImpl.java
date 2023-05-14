package com.kusitms.wannafly.applicationform.query.repository;

import com.kusitms.wannafly.applicationform.command.domain.ApplicationForm;
import com.kusitms.wannafly.applicationform.query.dto.PagingParams;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.kusitms.wannafly.applicationform.command.domain.QApplicationForm.applicationForm;

@RequiredArgsConstructor
public class ApplicationFormDynamicRepositoryImpl implements ApplicationFormDynamicRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ApplicationForm> findByParams(Long memberId, PagingParams params) {
        DynamicBooleanBuilder booleanBuilder = DynamicBooleanBuilder.builder();
        return queryFactory
                .selectFrom(applicationForm)
                .where(booleanBuilder
                        .and(()-> applicationForm.writer.id.eq(memberId))
                        .and(() -> applicationForm.lastModifiedTime.loe(findCursorTime(params.getCursor())))
                        .and(() -> applicationForm.id.ne(params.getCursor()))
                        .and(() -> applicationForm.year.value.eq(params.getYear()))
                        .build()
                )
                .orderBy(applicationForm.lastModifiedTime.desc())
                .limit(params.getSize())
                .fetch();
    }

    private LocalDateTime findCursorTime(Long cursor) {
        return queryFactory
                .select(applicationForm.lastModifiedTime)
                .from(applicationForm)
                .where(applicationForm.id.eq(cursor))
                .fetchOne();
    }

}
