package com.truechain.task.admin.model.viewPojo;
/**
 * 统计分析-奖励清单列表
 */
public class RewardListPojo {
    private Long id;
    private String personName;
    private String wxName;
    private String passTime;
    private String tcAddress;
    private String taskName;
    private String eventName;
    private String rewardType;
    private double rewardNum;
    private String lssuingstate;

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

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getTcAddress() {
        return tcAddress;
    }

    public void setTcAddress(String tcAddress) {
        this.tcAddress = tcAddress;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public double getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(double rewardNum) {
        this.rewardNum = rewardNum;
    }

    public String getLssuingstate() {
        return lssuingstate;
    }

    public void setLssuingstate(String lssuingstate) {
        this.lssuingstate = lssuingstate;
    }
}
