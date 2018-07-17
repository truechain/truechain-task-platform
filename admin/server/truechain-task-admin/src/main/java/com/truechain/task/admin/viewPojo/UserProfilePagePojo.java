package com.truechain.task.admin.viewPojo;

import com.truechain.task.model.entity.SysUser;

/**
 * 统计分析-用户任务列表
 */
public class UserProfilePagePojo {
    private Long id;
    private SysUser sysUser;

    private long taskCount;
    private long taskDoneCount;
    private long taskDoingCount;

    private double trueValue;
    private double ttrValue;
    private double rmbValue;

    private long recommendCount;

    public UserProfilePagePojo() {
    }

    public UserProfilePagePojo(SysUser sysUser) {
        this.id = sysUser.getId();
        this.sysUser = sysUser;
//        this.taskCount = 0L;
//        this.taskDoneCount = 0L;
//        this.taskDoingCount = 0L;
//        this.trueCount = 0L;
//        this.ttrCount = 0L;
//        this.rmbCount = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser user) {
        this.sysUser = user;
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

    public long getRecommendCount() {
        return recommendCount;
    }

    public void setRecommendCount(long recommendCount) {
        this.recommendCount = recommendCount;
    }
}
