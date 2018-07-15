package com.truechain.task.api.model.dto;

import com.truechain.task.model.entity.BsTask;
import com.truechain.task.model.entity.BsTaskDetail;

import java.io.Serializable;
import java.util.Set;

/**
 * 任务DTO
 */
public class TaskDTO implements Serializable {
    /**
     * 任务
     */
    private BsTask task;
    /**
     * 任务详情
     */
    private Set<BsTaskDetail> taskDetailList;
    /**
     * 是否已满
     */
    private int isFull;
    /**
     * 是否已经抢到
     */
    private int isHold;
    /**
     * 用户等级
     */
    private String userLevel;
    /**
     * 等级是否足够
     */
    private int isLevelEnough;

    public BsTask getTask() {
        return task;
    }

    public void setTask(BsTask task) {
        this.task = task;
    }

    public Set<BsTaskDetail> getTaskDetailList() {
        return taskDetailList;
    }

    public void setTaskDetailList(Set<BsTaskDetail> taskDetailList) {
        this.taskDetailList = taskDetailList;
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

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public int getIsLevelEnough() {
        return isLevelEnough;
    }

    public void setIsLevelEnough(int isLevelEnough) {
        this.isLevelEnough = isLevelEnough;
    }
}
