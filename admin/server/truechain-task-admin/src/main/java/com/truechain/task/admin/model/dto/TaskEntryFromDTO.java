package com.truechain.task.admin.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 任务报名表
 */
public class TaskEntryFromDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 奖励类型
     */
    private Integer rewardType;
    /**
     * 总审核状态
     */
    private Integer totalAuditStatus;
    /**
     * 报名信息
     */
    private List<TaskEntryFromInfoDTO> taskEntryFromInfoList;

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTotalAuditStatus() {
        return totalAuditStatus;
    }

    public void setTotalAuditStatus(Integer totalAuditStatus) {
        this.totalAuditStatus = totalAuditStatus;
    }

    public List<TaskEntryFromInfoDTO> getTaskEntryFromInfoList() {
        return taskEntryFromInfoList;
    }

    public void setTaskEntryFromInfoList(List<TaskEntryFromInfoDTO> taskEntryFromInfoList) {
        this.taskEntryFromInfoList = taskEntryFromInfoList;
    }

}
