package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.AuthResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthResourceRepository extends JpaRepository<AuthResource, Integer> {
}
