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
     * 状态
     */
    private int status;
    /**
     * 审核状态
     */
    private int auditStatus;
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
    private String reward;

    /**
     * 任务
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskId")
    private BsTask task;
    /**
     * 人员
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser user;

    public BsTask getTask() {
        return task;
    }

    public void setTask(BsTask task) {
        this.task = task;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
