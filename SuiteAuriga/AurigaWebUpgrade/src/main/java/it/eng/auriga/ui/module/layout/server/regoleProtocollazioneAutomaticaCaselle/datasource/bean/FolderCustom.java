/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class FolderCustom {

	@NumeroColonna(numero = "1")
	private String flagTipoFolder;
	@NumeroColonna(numero = "2")
	private String idFolder;
	@NumeroColonna(numero = "3")
	private String pathCartella;
	
	public String getFlagTipoFolder() {
		return flagTipoFolder;
	}
	public void setFlagTipoFolder(String flagTipoFolder) {
		this.flagTipoFolder = flagTipoFolder;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public String getPathCartella() {
		return pathCartella;
	}
	public void setPathCartella(String pathCartella) {
		this.pathCartella = pathCartella;
	}

}
