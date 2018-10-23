package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

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
     * 奖励发放状态（0-未发放，1-发放)
     */
    private Integer lssuingState;
    /**
     * 奖励数量
     */
    /*@JsonProperty("reward")*/
    private BigDecimal rewardNum;

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

    public BigDecimal getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(BigDecimal rewardNum) {
        this.rewardNum = rewardNum;
    }

    public Integer getRewardResource() {
        return rewardResource;
    }

    public void setRewardResource(Integer rewardResource) {
        this.rewardResource = rewardResource;
    }

    public Integer getLssuingState() {
        return lssuingState;
    }

    public void setLssuingState(Integer lssuingState) {
        this.lssuingState = lssuingState;
    }
}
