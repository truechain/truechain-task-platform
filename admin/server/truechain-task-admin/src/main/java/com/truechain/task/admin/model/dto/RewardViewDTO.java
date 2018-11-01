package com.truechain.task.admin.model.dto;

import java.io.Serializable;

public class RewardViewDTO extends TimeRangeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private Integer rewardResource;
    private Integer rewardType;

    public RewardViewDTO() {
    }

    public Integer getRewardResource() {
        return rewardResource;
    }

    public void setRewardResource(Integer rewardResource) {
        this.rewardResource = rewardResource;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewordType) {
        this.rewardType = rewordType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
