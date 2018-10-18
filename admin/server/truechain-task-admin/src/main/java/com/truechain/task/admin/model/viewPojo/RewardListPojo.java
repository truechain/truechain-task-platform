package com.truechain.task.admin.model.viewPojo;
/**
 * 统计分析-奖励清单列表
 */
public class RewardListPojo {
    private Long id;
    private String personName;
    private String eventName;
    private String rewardType;
    private String gotTime;
    private double rewardNum;

    public RewardListPojo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public double getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(double rewardNum) {
        this.rewardNum = rewardNum;
    }
}
