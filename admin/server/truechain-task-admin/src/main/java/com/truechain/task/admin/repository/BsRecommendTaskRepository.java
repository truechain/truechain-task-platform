package com.truechain.task.admin.repository;

import com.truechain.task.model.entity.BsRecommendTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Collection;
import java.util.List;

public interface BsRecommendTaskRepository extends JpaRepository<BsRecommendTask, Long>, QueryDslPredicateExecutor<BsRecommendTask> {

    /**
     * 获取推荐制定用户的人
     * @return
     */
    @Query("select u from BsRecommendTask u where u.recommendUser.id in ?1")
    List<BsRecommendTask> getMyRecommendUser(Collection<Long> ids);

    /**
     * 获取指定用户推荐的人总数
     * @return
     */
    @Query("select u.user.id,count(u.id) from BsRecommendTask u where u.user.id in ?1 group by u.user.id")
    List<Object> getMyRecommendCount(Collection<Long> ids);
}
