package com.truechain.task.plat.form.service;

import com.truechain.task.plat.form.model.entity.BsTask;
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
    Page<BsTask> getTaskPage(BsTask task, Pageable pageable);


    /**
     * 获取任务详情
     *
     * @param taskId
     */
    BsTask getTaskInfo(Long taskId);

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

