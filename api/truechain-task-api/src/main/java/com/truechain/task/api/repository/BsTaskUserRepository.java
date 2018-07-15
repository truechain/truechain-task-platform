package com.truechain.task.api.repository;

import com.truechain.task.model.entity.BsTaskDetail;
import com.truechain.task.model.entity.BsTaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BsTaskUserRepository extends JpaRepository<BsTaskUser, Long>, QueryDslPredicateExecutor<BsTaskUser> {

    int countByTaskDetail(BsTaskDetail taskDetail);
}
