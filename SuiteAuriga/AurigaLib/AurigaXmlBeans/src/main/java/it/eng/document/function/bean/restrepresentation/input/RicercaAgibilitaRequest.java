/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ricercaAgibilitaRequest")
public class RicercaAgibilitaRequest implements Serializable {
	

	private static final long serialVersionUID = 2889372497024071623L;
	@XmlElement(required = true)
	private String codiceFiscaleRichiedente;
	@XmlElement(required = true)
	private String cognomeRichiedente;
	@XmlElement(required = true)
	private String nomeRichiedente;
	@XmlElement(required = true)
	private String emailRichiedente;
	private String numeroTelefonoRichiedente;
	private String codiceFiscaleDelegante;
	private String cognomeDelegante;
	private String nomeDelegante;
	@XmlElement(required = true)
	private String codiceToponomasticoViaImmobile;
	@XmlElement(required = true)
	private String viaImmobile;
	@XmlElement(required = true)
	private Integer numeroCivico;
	private String appendiceCivico;
	@XmlElement(required = true)
	private String classificazioneRichiedente;
	@XmlElement(required = true)
	private String motivazioniRichiesta;
	private String note;
	
	//Parametri LogIn esterna
	private String codApplicazione;
	private String istanzaApplicazione;
	private String userNam;
	private String password;
	
	public String getCodApplicazione() {
		return codApplicazione;
	}

	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}

	public String getIstanzaApplicazione() {
		return istanzaApplicazione;
	}

	public void setIstanzaApplicazione(String istanzaApplicazione) {
		this.istanzaApplicazione = istanzaApplicazione;
	}

	public String getUserNam() {
		return userNam;
	}

	public void setUserNam(String userNam) {
		this.userNam = userNam;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}

	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}

	public String getNomeRichiedente() {
		return nomeRichiedente;
	}

	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}

	public String getEmailRichiedente() {
		return emailRichiedente;
	}

	public void setEmailRichiedente(String emailRichiedente) {
		this.emailRichiedente = emailRichiedente;
	}

	public String getNumeroTelefonoRichiedente() {
		return numeroTelefonoRichiedente;
	}

	public void setNumeroTelefonoRichiedente(String numeroTelefonoRichiedente) {
		this.numeroTelefonoRichiedente = numeroTelefonoRichiedente;
	}

	public String getCodiceFiscaleDelegante() {
		return codiceFiscaleDelegante;
	}

	public void setCodiceFiscaleDelegante(String codiceFiscaleDelegante) {
		this.codiceFiscaleDelegante = codiceFiscaleDelegante;
	}

	public String getCognomeDelegante() {
		return cognomeDelegante;
	}

	public void setCognomeDelegante(String cognomeDelegante) {
		this.cognomeDelegante = cognomeDelegante;
	}

	public String getNomeDelegante() {
		return nomeDelegante;
	}

	public void setNomeDelegante(String nomeDelegante) {
		this.nomeDelegante = nomeDelegante;
	}

	public String getCodiceToponomasticoViaImmobile() {
		return codiceToponomasticoViaImmobile;
	}

	public void setCodiceToponomasticoViaImmobile(String codiceToponomasticoViaImmobile) {
		this.codiceToponomasticoViaImmobile = codiceToponomasticoViaImmobile;
	}

	public String getViaImmobile() {
		return viaImmobile;
	}

	public void setViaImmobile(String viaImmobile) {
		this.viaImmobile = viaImmobile;
	}

	public Integer getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(Integer numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getAppendiceCivico() {
		return appendiceCivico;
	}

	public void setAppendiceCivico(String appendiceCivico) {
		this.appendiceCivico = appendiceCivico;
	}

	public String getClassificazioneRichiedente() {
		return classificazioneRichiedente;
	}

	public void setClassificazioneRichiedente(String classificazioneRichiedente) {
		this.classificazioneRichiedente = classificazioneRichiedente;
	}

	public String getMotivazioniRichiesta() {
		return motivazioniRichiesta;
	}

	public void setMotivazioniRichiesta(String motivazioniRichiesta) {
		this.motivazioniRichiesta = motivazioniRichiesta;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
