package it.eng.core.service.client.config;

import com.sun.jersey.spi.inject.Errors.ErrorMessage;

public class RestBusinessConfig {

	private Integer connectionTimeout;
	private Integer readTimeout;
	private String errorMessage;

	public Integer getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}
	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
