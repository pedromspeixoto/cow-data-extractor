package com.cde.cowdataauthorization.model;

import javax.persistence.*;

@Entity
@IdClass(RoleAccessConfigurationID.class)
@Table(	name = "role_access_configuration" )
public class RoleAccessConfiguration {

	@Id
	private Integer roleId;

	@Id
	private String method;

	@Id
	private String path;

	public RoleAccessConfiguration() {
	}

	public RoleAccessConfiguration(Integer roleId, String method, String path) {
		this.roleId = roleId;
		this.method = method;
		this.path = path;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}