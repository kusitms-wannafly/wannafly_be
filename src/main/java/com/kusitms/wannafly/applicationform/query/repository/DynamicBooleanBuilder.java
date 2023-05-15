package com.kusitms.wannafly.applicationform.query.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DynamicBooleanBuilder {

    private final BooleanBuilder booleanBuilder = new BooleanBuilder();

    public static DynamicBooleanBuilder builder() {
        return new DynamicBooleanBuilder();
    }

    public DynamicBooleanBuilder and(Supplier<BooleanExpression> expressionSupplier) {
        try {
            booleanBuilder.and(expressionSupplier.get());
        } catch (IllegalArgumentException | NullPointerException ignored) {
            /*
              위의 예외가 터지면 null 이 들어왔다는 뜻이다.
              null 이면 where 쿼리에 and 조건을 넣지 않기 위해 예외를 무시했다.
             */
        }
        return this;
    }

    public BooleanBuilder build() {
        return booleanBuilder;
    }
}
