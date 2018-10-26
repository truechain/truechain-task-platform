package com.truechain.task.api.model.dto;

import java.io.Serializable;

/**
 * 登录DTO
 */
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userUid;

    private String token;

    private String agent;
    
    private Boolean wxBinded;

    public Long getUserUid() {
        return userUid;
    }

    public void setUserUid(Long userUid) {
        this.userUid = userUid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

	public Boolean getWxBinded() {
		return wxBinded;
	}

	public void setWxBinded(Boolean wxBinded) {
		this.wxBinded = wxBinded;
	}
}
