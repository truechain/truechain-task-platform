package com.truechain.task.admin.model.dto;

import java.io.Serializable;

public class RewardViewDTO extends TimeRangeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private String channel;
    private String rewardType;

    public RewardViewDTO() {
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewordType) {
        this.rewardType = rewordType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
