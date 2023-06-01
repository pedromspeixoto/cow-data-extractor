package com.cde.cowdataauthorization.dto;

import java.util.List;
 
public class SignInResponseDto {

	private String jwt;
	private String userId;
	private List<String> roles;

	public SignInResponseDto() {
	}

	public SignInResponseDto(String jwt, String userId, List<String> roles) {
		this.jwt = jwt;
		this.userId = userId;
		this.roles = roles;
	}

	public String getJwt() {
		return this.jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
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