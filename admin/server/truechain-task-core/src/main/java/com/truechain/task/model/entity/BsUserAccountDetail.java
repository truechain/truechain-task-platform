package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "BsUserAccountDetail")
@DynamicUpdate
public class BsUserAccountDetail extends BaseEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "accountId")
    private BsUserAccount userAccount;

    /**
     * 任务
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskId")
    private BsTask task;
    /**
     * 推荐任务
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "recommendTaskId")
    private BsRecommendTask recommendTask;
    /**
     * 奖励类型
     */
    private Integer rewardType;
    /**
     * 奖励来源
     */
    private Integer rewardResource;
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

    public BsRecommendTask getRecommendTask() {
        return recommendTask;
    }

    public void setRecommendTask(BsRecommendTask recommendTask) {
        this.recommendTask = recommendTask;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public int getRewardType() {
        return rewardType;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public Integer getRewardResource() {
        return rewardResource;
    }

    public void setRewardResource(Integer rewardResource) {
        this.rewardResource = rewardResource;
    }
}
