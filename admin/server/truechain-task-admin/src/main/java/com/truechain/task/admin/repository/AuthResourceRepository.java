package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.AuthResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AuthResourceRepository extends JpaRepository<AuthResource, Long>, QueryDslPredicateExecutor<AuthResource> {
}
