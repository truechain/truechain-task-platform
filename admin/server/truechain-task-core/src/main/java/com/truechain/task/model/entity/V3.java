package com.truechain.task.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 任务表
 */
@Table(name = "V3")
@Entity
@DynamicUpdate
public class V3 {

	@Id
	private Long id;
	private Integer peopleNum;    
    private Integer enteredPeopleNum; 
    private Short isEnteredFull;
    private Integer completedPeopleNum;
    private Short isCompletedFull;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public Integer getEnteredPeopleNum() {
		return enteredPeopleNum;
	}
	public void setEnteredPeopleNum(Integer enteredPeopleNum) {
		this.enteredPeopleNum = enteredPeopleNum;
	}
	public Short getIsEnteredFull() {
		return isEnteredFull;
	}
	public void setIsEnteredFull(Short isEnteredFull) {
		this.isEnteredFull = isEnteredFull;
	}
	public Integer getCompletedPeopleNum() {
		return completedPeopleNum;
	}
	public void setCompletedPeopleNum(Integer completedPeopleNum) {
		this.completedPeopleNum = completedPeopleNum;
	}
	public Short getIsCompletedFull() {
		return isCompletedFull;
	}
	public void setIsCompletedFull(Short isCompletedFull) {
		this.isCompletedFull = isCompletedFull;
	}

    
    
}
