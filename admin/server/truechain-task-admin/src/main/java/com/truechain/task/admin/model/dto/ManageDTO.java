package com.truechain.task.admin.model.dto;

import java.io.Serializable;

public class ManageDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String configtype;
    private String configdata;
    private String updatetime;
    private String createtime;
    private String createuser;
    /**
     * 页数
     */
    private int pageIndex;
    /**
     * 条数
     */
    private int pageSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigtype() {
        return configtype;
    }

    public void setConfigtype(String configtype) {
        this.configtype = configtype;
    }

    public String getConfigdata() {
        return configdata;
    }

    public void setConfigdata(String configdata) {
        this.configdata = configdata;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
