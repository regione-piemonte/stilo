/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.document.function.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PubblicazioneElencoDocumentiRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<PubblicazioneFolderAppartenenzaRequest> folderAppartenenza;
	private String idDocPrimario;
	private String idUD;
	private String mittente;
	private String numeroRegistrazione;
	private String oggetto;
	private String tipoDocumento;
	
	public List<PubblicazioneFolderAppartenenzaRequest> getFolderAppartenenza() {
		return folderAppartenenza;
	}
	
	public void setFolderAppartenenza(List<PubblicazioneFolderAppartenenzaRequest> folderAppartenenza) {
		this.folderAppartenenza = folderAppartenenza;
	}
	
	public String getIdDocPrimario() {
		return idDocPrimario;
	}
	
	public void setIdDocPrimario(String idDocPrimario) {
		this.idDocPrimario = idDocPrimario;
	}
	
	public String getIdUD() {
		return idUD;
	}
	
	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}
	
	public String getMittente() {
		return mittente;
	}
	
	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	
	public String getNumeroRegistrazione() {
		return numeroRegistrazione;
	}
	
	public void setNumeroRegistrazione(String numeroRegistrazione) {
		this.numeroRegistrazione = numeroRegistrazione;
	}
	
	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	@Override
	public String toString() {
		return "PubblicazioneElencoDocumentiRequest [folderAppartenenza=" + folderAppartenenza + ", idDocPrimario="
				+ idDocPrimario + ", idUD=" + idUD + ", mittente=" + mittente + ", numeroRegistrazione="
				+ numeroRegistrazione + ", oggetto=" + oggetto + ", tipoDocumento=" + tipoDocumento + "]";
	}
	
}
