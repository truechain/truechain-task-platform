package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 岗位
 */
@Table(name = "BsTaskDetail")
@Entity
@DynamicUpdate
public class BsTaskDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位名称
     */
    private String station;
    /**
     * 人数
     */
    private Integer peopleNum;
    /**
     * 奖励
     */
    /*@JsonProperty("reward")*/
    private BigDecimal rewardNum;
    /**
     * 任务
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskId")
    private BsTask task;
    /**
     * 报名人员
     */
    @JsonIgnore
    @OneToMany(mappedBy = "taskDetail")
    private Set<BsTaskUser> taskUserSet;
    /**
     * 已报人数
     */
    @Transient
    private int hasPeople;
    /**
     * 是否已满
     */
    @Transient
    private int isFull;
    /**
     * 是否已经抢到
     */
    @Transient
    private int isHold;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public BigDecimal getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(BigDecimal rewardNum) {
        this.rewardNum = rewardNum;
    }

    public BsTask getTask() {
        return task;
    }

    public void setTask(BsTask task) {
        this.task = task;
    }

    public int getHasPeople() {
        return hasPeople;
    }

    public void setHasPeople(int hasPeople) {
        this.hasPeople = hasPeople;
    }

    public Set<BsTaskUser> getTaskUserSet() {
        return taskUserSet;
    }

    public void setTaskUserSet(Set<BsTaskUser> taskUserSet) {
        this.taskUserSet = taskUserSet;
    }

    public int getIsFull() {
        return isFull;
    }

    public void setIsFull(int isFull) {
        this.isFull = isFull;
    }

    public int getIsHold() {
        return isHold;
    }

    public void setIsHold(int isHold) {
        this.isHold = isHold;
    }
}
