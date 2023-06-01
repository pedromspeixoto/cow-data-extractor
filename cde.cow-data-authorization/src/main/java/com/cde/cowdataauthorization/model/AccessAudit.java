package com.cde.cowdataauthorization.model;

import javax.persistence.*;

@Entity
@Table(	name = "access_audit" )
public class AccessAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
    private String sourceIp;
    private String protocol;
	private String method;
	private String uri;

	public AccessAudit() {
	}

	public AccessAudit(String username, String sourceIp, String protocol, String method, String uri) {
		this.username = username;
		this.sourceIp = sourceIp;
		this.protocol = protocol;
		this.method = method;
		this.uri = uri;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSourceIp() {
		return this.sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}

