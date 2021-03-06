package com.truechain.task.admin.service;

import com.truechain.task.admin.model.dto.*;
import com.truechain.task.model.entity.BsRecommendTask;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * 任务Service
 */
public interface TaskService {

    /**
     * 获取任务数据
     *
     * @param task
     * @param pageable
     */
    Page<BsTask> getTaskPage(TaskDTO task, Pageable pageable);

    /**
     * 获取任务详情
     *
     * @param taskId
     */
    TaskInfoDTO getTaskInfo(Long taskId);

    /**
     * 获取报名表信息
     *
     * @param taskId
     * @return
     */
    TaskEntryFromDTO getEntryFormInfo(Long taskId);

    /**
     * 新增任务
     *
     * @param task
     */
    BsTask addTask(TaskInfoDTO task);


    /**
     * 更新任务
     *
     * @param task
     */
    BsTask updateTask(TaskInfoDTO task);


    /**
     * 删除任务
     *
     * @param taskId
     */
    void deleteTask(Long taskId);


    /**
     * 启用任务
     *
     * @param taskId
     */
    void enableTask(Long taskId);


    /**
     * 禁用任务
     *
     * @param taskId
     */
    void disableTask(Long taskId);

    /**
     * 审核个人任务
     *
     * @param taskUserId
     */
    AuditEntryFormUserDTO auditEntryFormUser(Long taskUserId);

    /**
     * 发放奖励
     *  @param taskUserId
     * @param userReward
     * @param recommendUserReward
     */
    BsTaskUser rewardEntryFromUser(Long taskUserId, Double userReward, Double recommendUserReward);

    /**
     * 总任务数
     *
     * @return
     */
    long countTotalTask();


    /**
     * 完成任务数
     *
     * @return
     */
    long countComplateTask();

    /**
     * 推荐列表
     * @param user
     * @param pageable
     * @return
     */
    Page<BsRecommendTask> getBsRecommendTaskList(UserDTO user, Pageable pageable);
    
    
    /**
     * 初始化数据
     */
    public void initTaskData();
}

