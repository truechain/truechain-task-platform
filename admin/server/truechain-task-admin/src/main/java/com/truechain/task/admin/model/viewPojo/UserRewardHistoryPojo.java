package com.truechain.task.admin.model.viewPojo;

/**
 * 统计分析-用户任务列表
 */
public class UserRewardHistoryPojo {
    private Long id;
    private String eventName;
    private double rewardNum;
    private String rewardType;
    private String gotTime;



    public UserRewardHistoryPojo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(double rewardNum) {
        this.rewardNum = rewardNum;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getGotTime() {
        return gotTime;
    }

    public void setGotTime(String gotTime) {
        this.gotTime = gotTime;
    }
}
