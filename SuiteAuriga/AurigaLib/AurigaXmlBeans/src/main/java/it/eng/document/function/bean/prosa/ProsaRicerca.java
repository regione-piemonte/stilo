/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProsaRicerca implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codiceTitolario;
	private Calendar dataDocumentoA;
	private Calendar dataDocumentoDa;
	private Calendar dataProtocolloA;
	private Calendar dataProtocolloDa;
	private String mittDest;
	private String modalita;
	private String numeroA;
	private String numeroDa;
	private String oggetto;
	private String registro;
	private String ufficioCompetente;

	public String getCodiceTitolario() {
		return codiceTitolario;
	}

	public void setCodiceTitolario(String codiceTitolario) {
		this.codiceTitolario = codiceTitolario;
	}

	public Calendar getDataDocumentoA() {
		return dataDocumentoA;
	}

	public void setDataDocumentoA(Calendar dataDocumentoA) {
		this.dataDocumentoA = dataDocumentoA;
	}

	public Calendar getDataDocumentoDa() {
		return dataDocumentoDa;
	}

	public void setDataDocumentoDa(Calendar dataDocumentoDa) {
		this.dataDocumentoDa = dataDocumentoDa;
	}

	public Calendar getDataProtocolloA() {
		return dataProtocolloA;
	}

	public void setDataProtocolloA(Calendar dataProtocolloA) {
		this.dataProtocolloA = dataProtocolloA;
	}

	public Calendar getDataProtocolloDa() {
		return dataProtocolloDa;
	}

	public void setDataProtocolloDa(Calendar dataProtocolloDa) {
		this.dataProtocolloDa = dataProtocolloDa;
	}

	public String getMittDest() {
		return mittDest;
	}

	public void setMittDest(String mittDest) {
		this.mittDest = mittDest;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public String getNumeroA() {
		return numeroA;
	}

	public void setNumeroA(String numeroA) {
		this.numeroA = numeroA;
	}

	public String getNumeroDa() {
		return numeroDa;
	}

	public void setNumeroDa(String numeroDa) {
		this.numeroDa = numeroDa;
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

	public String getUfficioCompetente() {
		return ufficioCompetente;
	}

	public void setUfficioCompetente(String ufficioCompetente) {
		this.ufficioCompetente = ufficioCompetente;
	}

}
