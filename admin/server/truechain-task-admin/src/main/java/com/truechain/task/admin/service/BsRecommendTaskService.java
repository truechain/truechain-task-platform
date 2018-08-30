package com.truechain.task.admin.service;

import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.model.entity.BsRecommendTask;
import com.truechain.task.model.entity.BsUserAccountDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     * 获取指定推荐人的被推荐人清单
     * @param userDTO
     * @param pageable
     * @return
     */
    Page<BsRecommendTask> getRecommendTask(UserDTO userDTO, Pageable pageable);
}
