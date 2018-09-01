package com.truechain.task.model.entity;


import com.truechain.task.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String createId;

    private String createUser;

    private String createTime = DateUtil.getDate();

    private String updateId;

    private String updateUser;

    private String updateTime = DateUtil.getDate();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        if (StringUtils.isBlank(createTime)) {
            this.createTime = DateUtil.getDate();
        } else {
            this.createTime = createTime;
        }
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    @PreUpdate
    public void setUpdatetime() {
        this.updateTime = DateUtil.getDate();
    }

    public void setUpdatetime(String updatetime) {
        if (StringUtils.isBlank(updatetime)) {
            this.updateTime = updatetime;
        } else {
            this.updateTime = DateUtil.getDate();
        }
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
