package com.truechain.task.admin.model.dto;

public class RewardViewDTO extends TimeRangeDTO{
    private Long userId;
    private String channel;
    private String rewordType;

    public RewardViewDTO() {
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRewordType() {
        return rewordType;
    }

    public void setRewordType(String rewordType) {
        this.rewordType = rewordType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
