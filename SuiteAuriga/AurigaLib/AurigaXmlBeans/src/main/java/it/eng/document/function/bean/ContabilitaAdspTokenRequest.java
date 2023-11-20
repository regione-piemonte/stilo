/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspTokenRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String Token;
	
	public String getToken() {
		return Token;
	}
	
	public void setToken(String token) {
		Token = token;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspTokenRequest [Token=" + Token + "]";
	}
	
}
