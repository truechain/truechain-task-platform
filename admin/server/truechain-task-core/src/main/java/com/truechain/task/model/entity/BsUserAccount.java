package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BsUserAccount")
@DynamicUpdate
public class BsUserAccount extends BaseEntity {

    /**
     * 用户
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "userId")
    private SysUser user;
    /**
     * true奖励
     */
    private String trueReward;
    /**
     * ttr奖励
     */
    private String ttrReward;
    /**
     * 红包奖励
     */
    private String gitReward;
    /**
     * 账户明细
     */
    @JsonIgnore
    @OneToMany(mappedBy = "userAccount")
    private Set<BsUserAccountDetail> accountDetails;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getTrueReward() {
        return trueReward;
    }

    public void setTrueReward(String trueReward) {
        this.trueReward = trueReward;
    }

    public String getTtrReward() {
        return ttrReward;
    }

    public void setTtrReward(String ttrReward) {
        this.ttrReward = ttrReward;
    }

    public String getGitReward() {
        return gitReward;
    }

    public void setGitReward(String gitReward) {
        this.gitReward = gitReward;
    }

    public Set<BsUserAccountDetail> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Set<BsUserAccountDetail> accountDetails) {
        this.accountDetails = accountDetails;
    }
}
