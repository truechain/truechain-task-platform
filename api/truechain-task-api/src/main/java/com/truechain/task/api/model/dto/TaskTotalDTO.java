package com.truechain.task.api.model.dto;

import com.truechain.task.model.entity.BsTask;

import java.io.Serializable;
import java.util.List;

/**
 * 我的任务
 */
public class TaskTotalDTO implements Serializable {

    /**
     * 任务总计数
     */
    private long taskTotal;
    /**
     * 进行中的任务
     */
    private long taskingTotal;
    /**
     * 已完成的任务
     */
    private long taskComplateTolal;
    /**
     * 任务记录
     */
    private List<BsTask> taskList;

    public long getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(long taskTotal) {
        this.taskTotal = taskTotal;
    }

    public long getTaskingTotal() {
        return taskingTotal;
    }

    public void setTaskingTotal(long taskingTotal) {
        this.taskingTotal = taskingTotal;
    }

    public long getTaskComplateTolal() {
        return taskComplateTolal;
    }

    public void setTaskComplateTolal(long taskComplateTolal) {
        this.taskComplateTolal = taskComplateTolal;
    }

    public List<BsTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<BsTask> taskList) {
        this.taskList = taskList;
    }
}
