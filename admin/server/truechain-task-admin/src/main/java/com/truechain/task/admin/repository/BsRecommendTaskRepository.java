package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.BsRecommendTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BsRecommendTaskRepository extends JpaRepository<BsRecommendTask, Long>, QueryDslPredicateExecutor<BsRecommendTask> {
}
