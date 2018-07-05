package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Integer> {
}
