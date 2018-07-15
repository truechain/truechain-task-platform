package com.truechain.task.api.model.dto;

import java.io.Serializable;

public class RecommendTaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    private String personName;
    /**
     * 奖励
     */
    private String reward;
    /**
     * 时间
     */
    private String createTime;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
