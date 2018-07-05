package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private int peopleNum;
    /**
     * 奖励
     */
    private String reward;
    /**
     * 任务
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskId")
    private BsTask task;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public BsTask getTask() {
        return task;
    }

    public void setTask(BsTask task) {
        this.task = task;
    }
}
