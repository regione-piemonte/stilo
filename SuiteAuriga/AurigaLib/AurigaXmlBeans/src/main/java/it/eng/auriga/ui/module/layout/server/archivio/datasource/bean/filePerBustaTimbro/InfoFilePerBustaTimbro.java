/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.document.NumeroColonna;

public class InfoFilePerBustaTimbro {

	@NumeroColonna(numero = "1")
	private String versione;
	@NumeroColonna(numero = "2")
	private String uriFile;
	@NumeroColonna(numero = "3")
	private String nomeFile;
	@NumeroColonna(numero = "4")
	private String descrizione;
	
	private File file;
	
	public String getVersione() {
		return versione;
	}
	public String getUriFile() {
		return uriFile;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	public void setUriFile(String uriFile) {
		this.uriFile = uriFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
}
