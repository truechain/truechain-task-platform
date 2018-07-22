package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.BsTaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Collection;
import java.util.List;

public interface BsTaskUserRepository extends JpaRepository<BsTaskUser, Long>, QueryDslPredicateExecutor<BsTaskUser> {

    @Query("select u from BsTaskUser u where u.user.id in ?1")
    List<BsTaskUser> findByUserIds(Collection<Long> userIds);
}
