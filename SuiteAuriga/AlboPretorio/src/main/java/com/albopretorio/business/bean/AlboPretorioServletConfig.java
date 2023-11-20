/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

public class AlboPretorioServletConfig {
	
	String pathWSAlboPretorio;
	String token;
	String tokenStorico;
	String tokenStoricoAut;
	String schema;
	Integer readTimeout;
	String errorMessageTimeout;
	Map<String, String> tokenMultiEnte;
	
	public Integer getReadTimeout() {
		return readTimeout;
	}

	public String getErrorMessageTimeout() {
		return errorMessageTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public void setErrorMessageTimeout(String errorMessageTimeout) {
		this.errorMessageTimeout = errorMessageTimeout;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTokenStorico() {
		return tokenStorico;
	}

	public void setTokenStorico(String tokenStorico) {
		this.tokenStorico = tokenStorico;
	}

	public String getPathWSAlboPretorio() {
		return pathWSAlboPretorio;
	}

	public void setPathWSAlboPretorio(String pathWSAlboPretorio) {
		this.pathWSAlboPretorio = pathWSAlboPretorio;
	}

	public String getTokenStoricoAut() {
		return tokenStoricoAut;
	}

	public void setTokenStoricoAut(String tokenStoricoAut) {
		this.tokenStoricoAut = tokenStoricoAut;
	}

	public Map<String, String> getTokenMultiEnte() {
		return tokenMultiEnte;
	}

	public void setTokenMultiEnte(Map<String, String> tokenMultiEnte) {
		this.tokenMultiEnte = tokenMultiEnte;
	}
	
}
