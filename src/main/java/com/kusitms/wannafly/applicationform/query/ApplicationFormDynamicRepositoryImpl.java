package com.kusitms.wannafly.applicationform.query;

import com.kusitms.wannafly.applicationform.query.dto.PagingParams;
import com.kusitms.wannafly.applicationform.query.dto.SimpleFormResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ApplicationFormDynamicRepositoryImpl implements ApplicationFormDynamicRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SimpleFormResponse> querySimpleForm(Long memberId, PagingParams params) {

        return null;
    }
}
