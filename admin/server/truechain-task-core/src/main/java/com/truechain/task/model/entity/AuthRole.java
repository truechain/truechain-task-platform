package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Table(name = "AuthRole")
@Entity
@DynamicUpdate
public class AuthRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    private Short status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 用户ID
     */
    @Transient
    private Integer userId;
    /**
     * 用户
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<AuthUser> users;
    /**
     * 资源
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "auth_role_resource")
    private List<AuthResource> resources;
    /**
     * 资源ID集合
     */
    @Transient
    private List<Long> resourceIdList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public List<AuthUser> getUsers() {
        return users;
    }

    public void setUsers(List<AuthUser> users) {
        this.users = users;
    }

    public List<AuthResource> getResources() {
        return resources;
    }

    public void setResources(List<AuthResource> resources) {
        this.resources = resources;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Long> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<Long> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
