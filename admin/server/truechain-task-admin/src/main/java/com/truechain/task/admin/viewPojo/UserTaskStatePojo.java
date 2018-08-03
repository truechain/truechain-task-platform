package com.truechain.task.admin.viewPojo;

import java.io.Serializable;

/**
 * 统计分析-用户任务列表
 */
public class UserTaskStatePojo implements Serializable {
    private Long id;
    private String taskName;
    private String taskLevel;
    private long taskState;
    private int taskCategory;
    private String taskStartTime;

    public UserTaskStatePojo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
    }

    public long getTaskState() {
        return taskState;
    }

    public void setTaskState(long taskState) {
        this.taskState = taskState;
    }

    public int getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(int taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }
}
