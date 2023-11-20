/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AccessTokenBean {
	
	private String scope;
	private String clientSecret;
	private String clientId;
	private String authority;
	private String tenantId;
	
	
	
	public AccessTokenBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccessTokenBean(String scope, String clientSecret, String clientId, String authority) {
		super();
		this.scope = scope;
		this.clientSecret = clientSecret;
		this.clientId = clientId;
		this.authority = authority;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
	

}
