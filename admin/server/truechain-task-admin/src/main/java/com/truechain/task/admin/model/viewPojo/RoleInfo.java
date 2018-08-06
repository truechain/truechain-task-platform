package com.truechain.task.admin.model.viewPojo;

import com.truechain.task.model.entity.AuthResource;
import com.truechain.task.model.entity.AuthRole;

import java.io.Serializable;
import java.util.List;

public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private AuthRole role;

    private List<AuthResource> resources;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }

    public List<AuthResource> getResources() {
        return resources;
    }

    public void setResources(List<AuthResource> resources) {
        this.resources = resources;
    }
}
