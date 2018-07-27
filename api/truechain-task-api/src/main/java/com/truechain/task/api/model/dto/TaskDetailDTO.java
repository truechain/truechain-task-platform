package com.truechain.task.api.model.dto;

import java.io.Serializable;

public class TaskDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long taskDetailId;

    private String commitAddress;

    private String remark;

    public Long getTaskDetailId() {
        return taskDetailId;
    }

    public void setTaskDetailId(Long taskDetailId) {
        this.taskDetailId = taskDetailId;
    }

    public String getCommitAddress() {
        return commitAddress;
    }

    public void setCommitAddress(String commitAddress) {
        this.commitAddress = commitAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
