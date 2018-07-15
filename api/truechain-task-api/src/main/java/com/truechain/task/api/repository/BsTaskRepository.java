package com.truechain.task.api.repository;

import com.truechain.task.model.entity.BsTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BsTaskRepository extends JpaRepository<BsTask, Long>, QueryDslPredicateExecutor<BsTask> {
}
