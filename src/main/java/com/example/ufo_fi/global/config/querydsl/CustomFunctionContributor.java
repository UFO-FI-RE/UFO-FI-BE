package com.example.ufo_fi.global.config.querydsl;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;

import static org.hibernate.type.StandardBasicTypes.INTEGER;

/**
 * 통신사 bit 계산을 위한 커스텀 함수
 */
public class CustomFunctionContributor implements FunctionContributor {

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        SqmFunctionRegistry registry = functionContributions.getFunctionRegistry();

        registry.registerPattern(
                "bitand",
                "(?1 & ?2)",
                functionContributions.getTypeConfiguration().getBasicTypeRegistry().resolve(INTEGER)
        );
    }
}
