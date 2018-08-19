package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, QueryDslPredicateExecutor<AuthUser> {

    AuthUser findByUsername(String userName);

    int countByUsername(String userName);

    int countByUsernameAndIdIsNot(String userName, Long id);
}
