package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal trueReward;
    /**
     * ttr奖励
     */
    private BigDecimal ttrReward;
    /**
     * 红包奖励
     */
    private BigDecimal gitReward;
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

    public BigDecimal getTrueReward() {
        return trueReward;
    }

    public void setTrueReward(BigDecimal trueReward) {
        this.trueReward = trueReward;
    }

    public BigDecimal getTtrReward() {
        return ttrReward;
    }

    public void setTtrReward(BigDecimal ttrReward) {
        this.ttrReward = ttrReward;
    }

    public BigDecimal getGitReward() {
        return gitReward;
    }

    public void setGitReward(BigDecimal gitReward) {
        this.gitReward = gitReward;
    }

    public Set<BsUserAccountDetail> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Set<BsUserAccountDetail> accountDetails) {
        this.accountDetails = accountDetails;
    }
}
