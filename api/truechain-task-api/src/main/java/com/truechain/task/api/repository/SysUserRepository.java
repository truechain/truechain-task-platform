package com.truechain.task.api.repository;

import com.truechain.task.model.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, Long>, QueryDslPredicateExecutor<SysUser> {

    SysUser findByOpenId(String openId);

    SysUser findByUserName(String userName);

    SysUser findByMobile(String mobile);

    long countByMobile(String mobile);
}
