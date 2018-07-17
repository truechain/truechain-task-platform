package com.truechain.task.admin.viewPojo;


/**
 * 统计分析-用户推荐列表
 */
public class UserRecommendPagePojo {
    private Long id;
    private String name;
    private String wxName;
    private String wxNum;
    private String level;
    private long doneTaskCount;
    private double rewardValue;
    private String recommendTime;

    public UserRecommendPagePojo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getDoneTaskCount() {
        return doneTaskCount;
    }

    public void setDoneTaskCount(long doneTaskCount) {
        this.doneTaskCount = doneTaskCount;
    }

    public double getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(double rewardValue) {
        this.rewardValue = rewardValue;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }
}
