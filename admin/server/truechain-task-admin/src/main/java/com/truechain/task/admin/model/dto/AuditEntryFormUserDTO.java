package com.truechain.task.admin.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AuditEntryFormUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String taskName;
    private BigDecimal rewardNum;
    private String personName;
    private String pushAddress;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public BigDecimal getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(BigDecimal rewardNum) {
        this.rewardNum = rewardNum;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPushAddress() {
        return pushAddress;
    }

    public void setPushAddress(String pushAddress) {
        this.pushAddress = pushAddress;
    }
}
