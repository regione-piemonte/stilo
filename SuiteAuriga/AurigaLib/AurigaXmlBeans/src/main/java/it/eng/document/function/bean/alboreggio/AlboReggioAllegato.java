/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class AlboReggioAllegato implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String base64File;

	private String id;

	private String idAtto;

	private String nomeFile;

	private String principale;

	private String descrizione;

	private String estensione;

	private String tipo;

	public String getId() {
		return id;
	}

	public String getIdAtto() {
		return idAtto;
	}

	public String getPrincipale() {
		return principale;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getEstensione() {
		return estensione;
	}

	public String getTipo() {
		return tipo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdAtto(String idAtto) {
		this.idAtto = idAtto;
	}

	public void setPrincipale(String principale) {
		this.principale = principale;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setEstensione(String estensione) {
		this.estensione = estensione;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getBase64File() {
		return base64File;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setBase64File(String base64File) {
		this.base64File = base64File;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}
