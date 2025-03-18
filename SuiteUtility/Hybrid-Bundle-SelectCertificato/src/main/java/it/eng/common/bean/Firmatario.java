/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class Firmatario implements Serializable{

	private String firmatario;
	private String alias;
	
	public String getFirmatario() {
		return firmatario;
	}
	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
		
	
	
	
}
