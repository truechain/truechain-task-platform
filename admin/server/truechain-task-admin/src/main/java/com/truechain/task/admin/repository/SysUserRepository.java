package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
}
