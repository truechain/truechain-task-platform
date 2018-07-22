package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.SysDeclare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface SysDeclareRepository extends JpaRepository<SysDeclare, Long>, QueryDslPredicateExecutor<SysDeclare> {
}
