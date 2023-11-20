/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.eng.core.annotation.Attachment;

@XmlRootElement
public class ProsaDocumentoProtocollato implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	@Attachment
	private byte[] contenuto;
	
	private Calendar dataDocumento;
	private Calendar dataProtocollo;
	private Long id;
	private boolean isContenuto;
	private ProsaMittenteDestinatario[] mittentiDestinatari;
	private String nomeFileContenuto;
	private String note;
	private String numeroProtocollo;
	private String numeroProtocolloEsterno;
	private String oggetto;
	private String registro;
	private boolean timbro;
	private String tipoProtocollo;
	private String ufficioCompetente;
	private String[] vociTitolario;

	public byte[] getContenuto() {
		return contenuto;
	}

	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}

	public Calendar getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Calendar dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public Calendar getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Calendar dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isContenuto() {
		return isContenuto;
	}

	public void setContenuto(boolean isContenuto) {
		this.isContenuto = isContenuto;
	}

	public ProsaMittenteDestinatario[] getMittentiDestinatari() {
		return mittentiDestinatari;
	}

	public void setMittentiDestinatari(ProsaMittenteDestinatario[] mittentiDestinatari) {
		this.mittentiDestinatari = mittentiDestinatari;
	}

	public String getNomeFileContenuto() {
		return nomeFileContenuto;
	}

	public void setNomeFileContenuto(String nomeFileContenuto) {
		this.nomeFileContenuto = nomeFileContenuto;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getNumeroProtocolloEsterno() {
		return numeroProtocolloEsterno;
	}

	public void setNumeroProtocolloEsterno(String numeroProtocolloEsterno) {
		this.numeroProtocolloEsterno = numeroProtocolloEsterno;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public boolean isTimbro() {
		return timbro;
	}

	public void setTimbro(boolean timbro) {
		this.timbro = timbro;
	}

	public String getTipoProtocollo() {
		return tipoProtocollo;
	}

	public void setTipoProtocollo(String tipoProtocollo) {
		this.tipoProtocollo = tipoProtocollo;
	}

	public String getUfficioCompetente() {
		return ufficioCompetente;
	}

	public void setUfficioCompetente(String ufficioCompetente) {
		this.ufficioCompetente = ufficioCompetente;
	}

	public String[] getVociTitolario() {
		return vociTitolario;
	}

	public void setVociTitolario(String[] vociTitolario) {
		this.vociTitolario = vociTitolario;
	}

}
