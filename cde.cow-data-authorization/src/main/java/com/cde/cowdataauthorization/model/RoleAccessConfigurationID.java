package com.cde.cowdataauthorization.model;

import java.io.Serializable;

public class RoleAccessConfigurationID implements Serializable {

	private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String method;
    private String path;

    public RoleAccessConfigurationID() {
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