package com.truechain.task.admin.service;

import com.truechain.task.model.entity.BsRecommendTask;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 推荐任务
 */
public interface BsRecommendTaskService {
    /**
     * 获取推荐制定用户的人
     * @return
     */
    Map<Long,BsRecommendTask> getMyRecommendUser(Collection<Long> ids);

    /**
     * 获取指定用户推荐的人总数
     * @return
     */
    Map<Long,Integer> getMyRecommendCount(Collection<Long> ids);
}
