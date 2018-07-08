package com.truechain.task.api.model.dto;

import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskUser;

import java.io.Serializable;

/**
 * 用户任务状态
 */
public class UserTaskInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务详情
     */
    private BsTask task;
    /**
     * 任务完成状态
     */
    private BsTaskUser taskCompleteInfo;

    public BsTask getTask() {
        return task;
    }

    public void setTask(BsTask task) {
        this.task = task;
    }

    public BsTaskUser getTaskCompleteInfo() {
        return taskCompleteInfo;
    }

    public void setTaskCompleteInfo(BsTaskUser taskCompleteInfo) {
        this.taskCompleteInfo = taskCompleteInfo;
    }
}
