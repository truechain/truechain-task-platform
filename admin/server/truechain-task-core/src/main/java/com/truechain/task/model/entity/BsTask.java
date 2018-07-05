package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

/**
 * 任务表
 */
@Table(name = "BsTask")
@Entity
@DynamicUpdate
public class BsTask extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务等级
     */
    private int level;
    /**
     * 类别
     */
    private int category;
    /**
     * 开始时间
     */
    private String startDateTime;
    /**
     * 结束时间
     */
    private String endDateTime;
    /**
     * 奖励类型
     */
    private int rewardType;
    /**
     * 奖励数量
     */
    private String rewardNum;
    /**
     * 任务状态
     */
    private int status;
    /**
     * 提交地址
     */
    private String pushAddress;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务详情
     */
    @JsonIgnore
    @OneToMany(mappedBy = "task")
    private Set<BsTaskDetail> taskDetailSet;
    /**
     * 任务人员情况
     */
    @JsonIgnore
    @OneToOne(mappedBy = "task")
    private BsTaskUser bsTaskUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(String rewardNum) {
        this.rewardNum = rewardNum;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BsTaskDetail> getTaskDetailSet() {
        return taskDetailSet;
    }

    public void setTaskDetailSet(Set<BsTaskDetail> taskDetailSet) {
        this.taskDetailSet = taskDetailSet;
    }

    public BsTaskUser getBsTaskUser() {
        return bsTaskUser;
    }

    public void setBsTaskUser(BsTaskUser bsTaskUser) {
        this.bsTaskUser = bsTaskUser;
    }
}
