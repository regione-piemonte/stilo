/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class FileMarcatoBean {

	private boolean isMarcato = false;
	private String tsMarca;
	
	public boolean isMarcato() {
		return isMarcato;
	}
	public void setMarcato(boolean isMarcato) {
		this.isMarcato = isMarcato;
	}
	public String getTsMarca() {
		return tsMarca;
	}
	public void setTsMarca(String tsMarca) {
		this.tsMarca = tsMarca;
	}
		
}
