package com.truechain.task.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.truechain.task.model.entity.BsTask;

public interface BsTaskRepository extends JpaRepository<BsTask, Long>, QueryDslPredicateExecutor<BsTask> {

}
