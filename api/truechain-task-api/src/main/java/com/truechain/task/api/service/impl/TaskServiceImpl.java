package com.truechain.task.api.service.impl;

import com.truechain.task.api.repository.BsTaskRepository;
import com.truechain.task.api.service.TaskService;
import com.truechain.task.model.entity.BsTask;
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
}
