/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class FileDaPubblicareSUEBean {

	@NumeroColonna(numero = "1")
	private String uri;

	@NumeroColonna(numero = "2")
	private String diplayFileName;
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getDiplayFileName() {
		return diplayFileName;
	}
	
	public void setDiplayFileName(String diplayFileName) {
		this.diplayFileName = diplayFileName;
	}

}