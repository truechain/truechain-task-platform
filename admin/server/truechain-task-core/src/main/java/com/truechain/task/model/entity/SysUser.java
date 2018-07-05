package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "SysUser")
@DynamicUpdate
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String personName;
    /**
     * 微信昵称
     */
    private String wxNickName;
    /**
     * 微信号码
     */
    private String wxNum;
    /**
     * openId
     */
    private String openId;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 钱包地址
     */
    private String trueChainAddress;
    /**
     * 简历地址
     */
    private String resumeFilePath;
    /**
     * 状态
     */
    private int status;
    /**
     * 审核状态
     */
    private int auditStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<BsTaskUser> taskUserSet;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTrueChainAddress() {
        return trueChainAddress;
    }

    public void setTrueChainAddress(String trueChainAddress) {
        this.trueChainAddress = trueChainAddress;
    }

    public String getResumeFilePath() {
        return resumeFilePath;
    }

    public void setResumeFilePath(String resumeFilePath) {
        this.resumeFilePath = resumeFilePath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Set<BsTaskUser> getTaskUserSet() {
        return taskUserSet;
    }

    public void setTaskUserSet(Set<BsTaskUser> taskUserSet) {
        this.taskUserSet = taskUserSet;
    }
}
