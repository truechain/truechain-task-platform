package com.truechain.task.api.repository;

import com.truechain.task.model.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    SysUser findByOpenId(String openId);

    SysUser findByUserName(String userName);
}
