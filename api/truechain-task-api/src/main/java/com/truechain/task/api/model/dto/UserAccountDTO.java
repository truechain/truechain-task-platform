package com.truechain.task.api.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.truechain.task.model.entity.BsUserAccountDetail;

import java.io.Serializable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Set<BsUserAccountDetail> accountDetails;

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
