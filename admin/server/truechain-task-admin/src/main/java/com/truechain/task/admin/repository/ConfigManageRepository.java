package com.truechain.task.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.truechain.task.model.entity.ConfigManage;

public interface ConfigManageRepository extends JpaRepository<ConfigManage,Long>, QueryDslPredicateExecutor<ConfigManage> {
	
	ConfigManage findByTypeName(String typeName);
}
