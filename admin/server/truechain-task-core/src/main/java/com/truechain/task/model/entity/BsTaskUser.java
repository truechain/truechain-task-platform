package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "BsTaskUser")
@Entity
@DynamicUpdate
public class BsTaskUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 状态(0-任务中,1-已经完成)
     */
    private int taskStatus;
    /**
     * 审核状态
     */
    private int auditStatus;
    /**
     * 审核结果
     */
    private String auditResult;
    /**
     * 提交地址
     */
    private String pushAddress;
    /**
     * 备注
     */
    private String remark;
    /**
     * 个人奖励
     */
    private Double rewardNum;

    /**
     * 任务
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskDetailId")
    private BsTaskDetail taskDetail;
    /**
     * 人员
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser user;

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public Double getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(Double rewardNum) {
        this.rewardNum = rewardNum;
    }

    public BsTaskDetail getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(BsTaskDetail taskDetail) {
        this.taskDetail = taskDetail;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getPushAddress() {
        return pushAddress;
    }

    public void setPushAddress(String pushAddress) {
        this.pushAddress = pushAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
