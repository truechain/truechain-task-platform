package com.truechain.task.model.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 操作日志
 */
@Table(name = "AuthOperationLog")
@Entity
@DynamicUpdate
public class AuthOperationLog extends BaseEntity {

    private String logName;

    private String api;

    private String method;

    private Short succeed;

    private String message;

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Short getSucceed() {
        return succeed;
    }

    public void setSucceed(Short succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
