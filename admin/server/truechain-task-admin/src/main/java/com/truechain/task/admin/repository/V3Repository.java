package com.truechain.task.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.truechain.task.model.entity.V3;

public interface V3Repository extends JpaRepository<V3, Long>, QueryDslPredicateExecutor<V3> {

	
}
