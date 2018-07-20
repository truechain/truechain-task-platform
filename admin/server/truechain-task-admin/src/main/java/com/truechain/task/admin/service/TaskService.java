package com.truechain.task.admin.service;

import com.truechain.task.admin.model.dto.TaskDTO;
import com.truechain.task.admin.model.dto.TaskInfoDTO;
import com.truechain.task.model.entity.BsTask;
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
     * 新增任务
     *
     * @param task
     */
    BsTask addTask(BsTask task);


    /**
     * 更新任务
     *
     * @param task
     */
    BsTask updateTask(BsTask task);


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


}

