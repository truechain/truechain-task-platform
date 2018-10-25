package com.truechain.task.admin.model.viewPojo;

import java.util.Map;

public class ManagePojo {
    private Long id;
    private String name;
    private String type;
    private Map configdataMap;
    //    private String configdata;
    private String updatetime;
    private String createtime;
    private String createuser;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Map getConfigdataMap() {
        return configdataMap;
    }

    public void setConfigdataMap(Map configdataMap) {
        this.configdataMap = configdataMap;
    }
}
