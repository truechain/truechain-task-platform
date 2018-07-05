package com.truechain.task.api.service;

import com.truechain.task.model.entity.BsTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    /**
     * 获取任务分页数据
     *
     * @param task
     * @param pageable
     * @return
     */
    Page<BsTask> getTaskPage(BsTask task, Pageable pageable);
}
