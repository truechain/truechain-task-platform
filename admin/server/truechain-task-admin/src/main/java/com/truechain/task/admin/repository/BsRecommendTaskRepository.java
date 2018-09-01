package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.BsRecommendTask;
import com.truechain.task.model.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Collection;
import java.util.List;

public interface BsRecommendTaskRepository extends JpaRepository<BsRecommendTask, Long>, QueryDslPredicateExecutor<BsRecommendTask> {

    /**
     * 被推荐人 找 推荐人
     *
     * @return
     */
    @Query("select u from BsRecommendTask u where u.user.id in ?1")
    List<BsRecommendTask> getMyRecommendUser(Collection<Long> ids);

    /**
     * 推荐人 找 自己的被推荐人总数
     *
     * @return
     */
    @Query("select u.recommendUser.id,count(u.recommendUser) from BsRecommendTask u where u.recommendUser.id in ?1 group by u.recommendUser.id")
    List<Object> getMyRecommendCount(Collection<Long> ids);

    BsRecommendTask getByUser(SysUser user);
}
