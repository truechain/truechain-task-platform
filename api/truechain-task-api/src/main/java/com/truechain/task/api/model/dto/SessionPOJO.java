package com.truechain.task.api.model.dto;

import com.truechain.task.admin.model.entity.AuthRole;

import java.io.Serializable;
import java.util.List;

/**
 * SessionPoJo
 */
public class SessionPOJO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private List<AuthRole> roleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AuthRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthRole> roleList) {
        this.roleList = roleList;
    }
}
