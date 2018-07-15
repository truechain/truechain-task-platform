package com.truechain.task.api.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BasicService {

    @Autowired
    protected JPAQueryFactory jpaQueryFactory;
}
