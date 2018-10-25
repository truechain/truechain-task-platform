package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.SysUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface SysUserRepository extends JpaRepository<SysUser, Long>, QueryDslPredicateExecutor<SysUser> {

	long countByMobile(String mobile);
	
    SysUser findByMobile(String mobile);
    
}
