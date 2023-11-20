/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspTokenResponse extends ContabilitaAdspResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String token;
	private boolean valid;

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspTokenResponse [token=" + token + ", valid=" + valid + "]";
	}
	
}
