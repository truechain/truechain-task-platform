package com.truechain.task.api.service;

import com.truechain.task.api.model.dto.TaskDTO;
import com.truechain.task.api.model.dto.UserTaskInfoDTO;
import com.truechain.task.model.entity.BsTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

public interface TaskService {

    /**
     * 获取任务分页数据
     *
     * @param task
     * @param pageable
     * @return
     */
    Page<BsTask> getTaskPage(BsTask task, Pageable pageable);

    /**
     * 获取用户任务列表
     *
     * @param userId
     * @return
     */
    TaskDTO getUserTaskList(Long userId);

    /**
     * 获取用户任务状态
     *
     * @param userId
     * @param taskId
     * @return
     */
    UserTaskInfoDTO getUserTaskInfo(Long userId, Long taskId);

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
