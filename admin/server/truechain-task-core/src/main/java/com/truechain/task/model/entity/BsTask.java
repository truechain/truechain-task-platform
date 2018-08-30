package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 任务表
 */
@Table(name = "BsTask")
@Entity
@DynamicUpdate
public class BsTask extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务等级
     */
    private String level;
    /**
     * 类别
     */
    private Integer category;
    /**
     * 开始时间
     */
    private String startDateTime;
    /**
     * 结束时间
     */
    private String endDateTime;
    /**
     * 奖励类型
     */
    private Integer rewardType;
    /**
     * 奖励数量
     */
    private BigDecimal rewardNum;
    /**
     * 人数限制
     */
    private Integer peopleNum;
    /**
     * 图片地址
     */
    private String iconPath;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    /**
     * 审核任务状态
     */
    private Integer auditStatus;
    /**
     * 提交地址
     */
    private String pushAddress;
    /**
     * 任务描述
     */
    private String description;
    /**
     * 任务详情
     */
    @JsonIgnore
    @OneToMany(mappedBy = "task")
    private Set<BsTaskDetail> taskDetailSet;

    /**
     * 任务明细ID
     */
    @Transient
    private Long taskDetailId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(BigDecimal rewardNum) {
        this.rewardNum = rewardNum;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getPushAddress() {
        return pushAddress;
    }

    public void setPushAddress(String pushAddress) {
        this.pushAddress = pushAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BsTaskDetail> getTaskDetailSet() {
        return taskDetailSet;
    }

    public void setTaskDetailSet(Set<BsTaskDetail> taskDetailSet) {
        this.taskDetailSet = taskDetailSet;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getTaskDetailId() {
        return taskDetailId;
    }

    public void setTaskDetailId(Long taskDetailId) {
        this.taskDetailId = taskDetailId;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }
}
