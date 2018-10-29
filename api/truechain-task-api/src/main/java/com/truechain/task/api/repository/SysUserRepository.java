package com.truechain.task.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.truechain.task.model.entity.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long>, QueryDslPredicateExecutor<SysUser> {

    SysUser findByOpenId(String openId);

    SysUser findByUserName(String userName);

    SysUser findByMobile(String mobile);

    List<SysUser> findByRecommendUserId(long recommendUserId);
    
    @Query("select count(id) from SysUser where audit_status = 1 and recommend_user_id = :recommendUserId")
    long countByRecommendUserIdAndAuditStatus(@Param("recommendUserId") long recommendUserId);

    long countByMobile(String mobile);
    
    long countByOpenId(String openId);
}
