package com.cde.cowdataauthorization.dto;

import java.util.List;
 
public class AuthResponseDto {

	private String userId;
	private List<String> roles;;

	public AuthResponseDto() {
	}

	public AuthResponseDto(String userId, List<String> roles) {
		this.userId = userId;
		this.roles = roles;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRoles() {
		return this.roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}