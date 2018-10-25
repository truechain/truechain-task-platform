package com.truechain.task.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "BsTaskUser")
@Entity
@DynamicUpdate
public class BsTaskUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 状态(0-未提交,1-待审核,-1 = 取消,2 审核通过   3 未通过审核   4 已发放)
     */
    private int taskStatus;
    /**
     * 提交地址
     */
    private String pushAddress;
    /**
     * 备注
     */
    private String remark;
    /**
     * 个人奖励
     */
    /*@JsonProperty("reward")*/
    private BigDecimal rewardNum;
    
    /**
     * 推荐人奖励
     */
    private BigDecimal referralNum;
    
    /**
     * 名次 1 2 3 4 优秀 5通过
     */
    private Integer finishLevel;

    /**
     * 任务
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskDetailId")
    private BsTaskDetail taskDetail;
    /**
     * 人员
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private SysUser user;



    public BigDecimal getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(BigDecimal rewardNum) {
        this.rewardNum = rewardNum;
    }

    public BsTaskDetail getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(BsTaskDetail taskDetail) {
        this.taskDetail = taskDetail;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getPushAddress() {
        return pushAddress;
    }

    public void setPushAddress(String pushAddress) {
        this.pushAddress = pushAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public BigDecimal getReferralNum() {
		return referralNum;
	}

	public void setReferralNum(BigDecimal referralNum) {
		this.referralNum = referralNum;
	}

	public Integer getFinishLevel() {
		return finishLevel;
	}

	public void setFinishLevel(Integer finishLevel) {
		this.finishLevel = finishLevel;
	}



	
}
