package com.truechain.task.admin.model.dto;

import java.io.Serializable;

/**
 * 任务报名表
 */
public class TaskEntryFromInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long taskUserId;
    /**
     * 职位
     */
    private String station;
    /**
     * 微信昵称
     */
    private String wxNickName;
    /**
     * 奖励状态
     */
    private Integer auditStatus;
    /**
     * 奖励
     */
    private Double rewardNum;
    /**
     * 参与人名称
     */
    private String personName;
    /**
     * 推荐人
     */
    private String recommendUser;
    /**
     * 提交地址
     */
    private String pushAddress;
    /**
     * 备注
     */
    private String remark;

    public Long getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(Long taskUserId) {
        this.taskUserId = taskUserId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Double getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(Double rewardNum) {
        this.rewardNum = rewardNum;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(String recommendUser) {
        this.recommendUser = recommendUser;
    }

    public String getPushAddress() {
        return pushAddress;
    }

    public void setPushAddress(String pushAddress) {
        this.pushAddress = pushAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
