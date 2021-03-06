package com.truechain.task.api.model.dto;


import com.truechain.task.model.entity.AuthRole;

import java.io.Serializable;
import java.util.List;

/**
 * SessionPoJo
 */
public class SessionPOJO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Long userId;

    private String token;

    private String salt;

    private List<AuthRole> roleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<AuthRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthRole> roleList) {
        this.roleList = roleList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
