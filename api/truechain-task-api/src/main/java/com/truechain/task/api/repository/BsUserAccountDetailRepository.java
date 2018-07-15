package com.truechain.task.api.repository;

import com.truechain.task.model.entity.BsUserAccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BsUserAccountDetailRepository extends JpaRepository<BsUserAccountDetail, Long>, QueryDslPredicateExecutor<BsUserAccountDetail> {
}
