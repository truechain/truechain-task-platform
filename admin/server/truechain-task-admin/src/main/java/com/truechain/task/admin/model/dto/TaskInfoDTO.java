package com.truechain.task.admin.model.dto;

import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskDetail;

import java.util.Set;

public class TaskInfoDTO {

    private BsTask task;

    private Set<BsTaskDetail> taskDetailList;

    public BsTask getTask() {
        return task;
    }

    public void setTask(BsTask task) {
        this.task = task;
    }

    public Set<BsTaskDetail> getTaskDetailList() {
        return taskDetailList;
    }

    public void setTaskDetailList(Set<BsTaskDetail> taskDetailList) {
        this.taskDetailList = taskDetailList;
    }
}
