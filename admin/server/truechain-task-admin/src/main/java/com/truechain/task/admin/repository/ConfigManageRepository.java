package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.ConfigManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ConfigManageRepository extends JpaRepository<ConfigManage,Long>, QueryDslPredicateExecutor<ConfigManage> {
}
