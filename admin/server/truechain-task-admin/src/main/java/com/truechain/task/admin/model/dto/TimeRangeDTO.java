package com.truechain.task.admin.model.dto;

import java.io.Serializable;

public class TimeRangeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String startDate;

    private String endDate;

    public TimeRangeDTO() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
