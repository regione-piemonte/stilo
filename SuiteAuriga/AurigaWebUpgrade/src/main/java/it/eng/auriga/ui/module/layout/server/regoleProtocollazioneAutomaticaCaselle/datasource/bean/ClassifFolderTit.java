/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ClassifFolderTit {
	
	@NumeroColonna(numero = "1")
	private String flagOperazione;
	@NumeroColonna(numero = "2")
	private String idFolder;
	@NumeroColonna(numero = "3")
	private String idClassifica;
	@NumeroColonna(numero = "4")
	private String nomeFolder;
	@NumeroColonna(numero = "5")
	private String annoApertura;
	
	public String getFlagOperazione() {
		return flagOperazione;
	}
	public void setFlagOperazione(String flagOperazione) {
		this.flagOperazione = flagOperazione;
	}
	public String getIdFolder() {
		return idFolder;
	}
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public String getIdClassifica() {
		return idClassifica;
	}
	public void setIdClassifica(String idClassifica) {
		this.idClassifica = idClassifica;
	}
	public String getNomeFolder() {
		return nomeFolder;
	}
	public void setNomeFolder(String nomeFolder) {
		this.nomeFolder = nomeFolder;
	}
	public String getAnnoApertura() {
		return annoApertura;
	}
	public void setAnnoApertura(String annoApertura) {
		this.annoApertura = annoApertura;
	}

}
