/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class FolderCustomRegoleProtAutoBean implements Serializable {

	private static final long serialVersionUID = 6273589293516922469L;

	@NumeroColonna(numero = "1")
	private String flgEsistente;
	
	@NumeroColonna(numero = "2")
	private String idFolder;
	
	@NumeroColonna(numero = "3")
	private String pathFolder;

	public String getFlgEsistente() {
		return flgEsistente;
	}

	public void setFlgEsistente(String flgEsistente) {
		this.flgEsistente = flgEsistente;
	}

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getPathFolder() {
		return pathFolder;
	}

	public void setPathFolder(String pathFolder) {
		this.pathFolder = pathFolder;
	}

}
