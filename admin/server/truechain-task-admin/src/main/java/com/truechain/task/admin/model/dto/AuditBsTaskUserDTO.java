package com.truechain.task.admin.model.dto;

public class AuditBsTaskUserDTO {

	private Long id;
	private int finishLevel;
	private float rewardNum;
	private float referralNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFinishLevel() {
		return finishLevel;
	}

	public void setFinishLevel(int finishLevel) {
		this.finishLevel = finishLevel;
	}

	public float getRewardNum() {
		return rewardNum;
	}

	public void setRewardNum(float rewardNum) {
		this.rewardNum = rewardNum;
	}

	public float getReferralNum() {
		return referralNum;
	}

	public void setReferralNum(float referralNum) {
		this.referralNum = referralNum;
	}

}
