package com.truechain.task.admin.service.impl;

import com.google.common.base.Preconditions;
import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.enums.TaskStatusEnum;
import com.truechain.task.admin.repository.BsTaskRepository;
import com.truechain.task.admin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private BsTaskRepository taskRepository;

    @Override
    public Page<BsTask> getTaskPage(BsTask task, Pageable pageable) {
        Page<BsTask> taskPage = taskRepository.findAll(pageable);
        return taskPage;
    }

    @Override
    public BsTask getTaskInfo(Long taskId) {
        BsTask task = taskRepository.findOne(taskId);
        return task;
    }

    @Override
    public BsTask addTask(BsTask task) {
        task = taskRepository.save(task);
        return task;
    }

    @Override
    public BsTask updateTask(BsTask task) {
        BsTask bsTask = taskRepository.findOne(task.getId());
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        bsTask = taskRepository.save(bsTask);
        return bsTask;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.delete(taskId);
    }

    @Override
    public void enableTask(Long taskId) {
        BsTask bsTask = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        bsTask.setAuditStatus(TaskStatusEnum.ENABLE.getCode());
        taskRepository.save(bsTask);
    }

    @Override
    public void disableTask(Long taskId) {
        BsTask bsTask = taskRepository.findOne(taskId);
        Preconditions.checkArgument(null != bsTask, "该任务不存在");
        bsTask.setAuditStatus(TaskStatusEnum.DISABLE.getCode());
        taskRepository.save(bsTask);
    }
}
