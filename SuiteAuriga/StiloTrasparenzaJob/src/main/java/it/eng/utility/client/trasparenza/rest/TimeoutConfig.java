/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.client.trasparenza.rest;

public class TimeoutConfig {
	
	private Integer connectionTimeout;
	private Integer readTimeout;
	
	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}
	
	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	public Integer getReadTimeout() {
		return readTimeout;
	}
	
	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}
	
}
