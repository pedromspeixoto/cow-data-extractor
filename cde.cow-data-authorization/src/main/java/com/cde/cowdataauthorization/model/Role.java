package com.cde.cowdataauthorization.model;

import javax.persistence.*;

import com.cde.cowdataauthorization.enums.Roles;

@Entity
@Table(	name = "roles" )
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Roles name;

	public Role() {

	}

	public Role(Roles role) {
		this.name = role;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Roles getName() {
		return name;
	}

	public void setName(Roles name) {
		this.name = name;
	}
}