/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class ProsaAllegato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	@Attachment
	private byte[] contenuto;
	
	private String collocazione;
	private String descrizione;
	private Long id;
	private Long idProfilo;
	private String nomeFile;

	public String getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(String collocazione) {
		this.collocazione = collocazione;
	}

	public byte[] getContenuto() {
		return contenuto;
	}

	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProfilo() {
		return idProfilo;
	}

	public void setIdProfilo(Long idProfilo) {
		this.idProfilo = idProfilo;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

}
