package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.BsTaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BsTaskDetailRepository extends JpaRepository<BsTaskDetail, Long>, QueryDslPredicateExecutor<BsTaskDetail> {
}
