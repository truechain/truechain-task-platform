package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {

    AuthUser findByUsername(String userName);

    int countByUsername(String userName);
}
