/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author dbe4235
 *
 */

public class FirmaXadesBean {
	
	@XmlVariabile(nome = "alias", tipo = TipoVariabile.SEMPLICE)
	private String alias;
	
	@XmlVariabile(nome = "pin", tipo = TipoVariabile.SEMPLICE)
	private String pin;
	
	@XmlVariabile(nome = "otp", tipo = TipoVariabile.SEMPLICE)
	private String otp;
	
	@XmlVariabile(nome = "endpoint", tipo = TipoVariabile.SEMPLICE)
	private String endpoint;

	public String getAlias() {
		return alias;
	}

	public String getPin() {
		return pin;
	}

	public String getOtp() {
		return otp;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
}