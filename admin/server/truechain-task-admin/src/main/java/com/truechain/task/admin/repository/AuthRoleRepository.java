package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long>, QueryDslPredicateExecutor<AuthRole> {
}
