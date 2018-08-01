package com.truechain.task.admin.viewPojo;

import java.io.Serializable;

public class ReportIndexPojo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long Id;
    private Long userCount;
    private long taskCount;
    private long taskDoneCount;
    private long taskDoingCount;
    private double trueValue;
    private double ttrValue;
    private double rmbValue;

    public ReportIndexPojo() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(long taskCount) {
        this.taskCount = taskCount;
    }

    public long getTaskDoneCount() {
        return taskDoneCount;
    }

    public void setTaskDoneCount(long taskDoneCount) {
        this.taskDoneCount = taskDoneCount;
    }

    public long getTaskDoingCount() {
        return taskDoingCount;
    }

    public void setTaskDoingCount(long taskDoingCount) {
        this.taskDoingCount = taskDoingCount;
    }

    public double getTrueValue() {
        return trueValue;
    }

    public void setTrueValue(double trueValue) {
        this.trueValue = trueValue;
    }

    public double getTtrValue() {
        return ttrValue;
    }

    public void setTtrValue(double ttrValue) {
        this.ttrValue = ttrValue;
    }

    public double getRmbValue() {
        return rmbValue;
    }

    public void setRmbValue(double rmbValue) {
        this.rmbValue = rmbValue;
    }
}
