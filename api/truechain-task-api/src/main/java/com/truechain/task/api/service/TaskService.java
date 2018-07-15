package com.truechain.task.api.service;

import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.model.dto.TaskTotalDTO;
import com.truechain.task.api.model.dto.UserTaskInfoDTO;
import com.truechain.task.model.entity.BsTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    /**
     * 获取任务分页数据
     *
     * @param task
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<BsTask> getTaskPage(BsTask task, int pageIndex, int pageSize);

    /**
     * 获取任务详情
     *
     * @param taskId
     * @return
     */
    TaskDTO getTaskInfo(Long taskId, Long userId);

    /**
     * 获取用户任务列表
     *
     * @param userId
     * @param taskStatus
     * @return
     */
    TaskTotalDTO getUserTaskList(Long userId, Integer taskStatus);

    /**
     * 获取用户任务状态
     *
     * @param userId
     * @param taskId
     * @return
     */
    UserTaskInfoDTO getUserTaskInfo(Long userId, Long taskId);

    /**
     * 抢任务
     *
     * @param taskDetailId
     * @param userId
     */
    void holdTask(Long taskDetailId, Long userId);

    /**
     * 提交用户任务
     *
     * @param userId
     * @param taskId
     * @param commitAddress
     * @param remark
     */
    void commitUserTask(Long userId, Long taskId, String commitAddress, String remark);
}
