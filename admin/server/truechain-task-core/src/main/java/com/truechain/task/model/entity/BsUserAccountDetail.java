package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BsUserAccountDetail")
@DynamicUpdate
public class BsUserAccountDetail extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "accountId")
    private BsUserAccount userAccount;

    /**
     * 任务(包括发布任务，推荐目前也作为一种任务考虑)
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskId")
    private BsTask task;
    /**
     * 奖励类型
     */
    private int rewardType;
    /**
     * 奖励
     */
    private String reward;

    public BsUserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(BsUserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public BsTask getTask() {
        return task;
    }

    public void setTask(BsTask task) {
        this.task = task;
    }

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
