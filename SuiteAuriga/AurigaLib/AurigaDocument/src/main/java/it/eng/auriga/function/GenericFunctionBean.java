/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Connection;

import it.eng.auriga.repository2.generic.VersionHandler;

public class GenericFunctionBean {
	
	
	public GenericFunctionBean() {
		super();
	}
	private Connection mConnection;
	private VersionHandler mVersionHandler;
	private String token;
	private String userIdLavoro;
	
	public Connection getmConnection() {
		return mConnection;
	}
	public void setmConnection(Connection mConnection) {
		this.mConnection = mConnection;
	}
	public VersionHandler getmVersionHandler() {
		return mVersionHandler;
	}
	public void setmVersionHandler(VersionHandler mVersionHandler) {
		this.mVersionHandler = mVersionHandler;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserIdLavoro() {
		return userIdLavoro;
	}
	public void setUserIdLavoro(String userIdLavoro) {
		this.userIdLavoro = userIdLavoro;
	}

	
	
}
