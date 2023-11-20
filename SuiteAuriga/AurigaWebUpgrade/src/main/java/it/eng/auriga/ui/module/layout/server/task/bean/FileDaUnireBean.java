/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class FileDaUnireBean {

	@NumeroColonna(numero = "1")
	private String nomeFile;
	@NumeroColonna(numero = "2")
	private String uriFile;
	@NumeroColonna(numero = "3")
	private String mimetype;
	@NumeroColonna(numero = "4")
	private String uriModello;
	@NumeroColonna(numero = "5")
	private String tipoModello;
	@NumeroColonna(numero = "6")
	private String nomeModello;
	
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getUriFile() {
		return uriFile;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getUriModello() {
		return uriModello;
	}
	public void setUriModello(String uriModello) {
		this.uriModello = uriModello;
	}
	public String getTipoModello() {
		return tipoModello;
	}
	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}
	public String getNomeModello() {
		return nomeModello;
	}
	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}
	
}
