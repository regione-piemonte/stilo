/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 *
 * @author DANCRIST
 *
 * Bean per definire la scelta del provider di firma e/o verifica firma
 *
 */

public class ProviderFirmaBean {
	
	private String tipoProvider;
	private String endpoint;
	private String servicens;
	private String servicename;

	public String getTipoProvider() {
		return tipoProvider;
	}

	public void setTipoProvider(String tipoProvider) {
		this.tipoProvider = tipoProvider;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getServicens() {
		return servicens;
	}

	public void setServicens(String servicens) {
		this.servicens = servicens;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

}