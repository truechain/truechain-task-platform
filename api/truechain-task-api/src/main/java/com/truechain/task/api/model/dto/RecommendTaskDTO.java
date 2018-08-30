package com.truechain.task.api.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RecommendTaskDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    private String personName;
    /**
     * 奖励
     */
    private BigDecimal rewardNum;
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

    public BigDecimal getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(BigDecimal rewardNum) {
        this.rewardNum = rewardNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
