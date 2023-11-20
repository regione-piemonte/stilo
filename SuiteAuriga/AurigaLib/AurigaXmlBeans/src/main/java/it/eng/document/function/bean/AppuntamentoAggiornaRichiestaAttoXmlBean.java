/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * Dati dell'appuntamento di accesso atti da aggiornare
 * 
 * @author Mattia Zanetti
 *
 */

@XmlRootElement
public class AppuntamentoAggiornaRichiestaAttoXmlBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9005558780760729715L;

	/**
	 * Indica se l'appuntamento già fissato è da annullare
	 */
	@XmlVariabile(nome = "DaAnnullare", tipo = TipoVariabile.SEMPLICE)
	private Boolean daAnnullare;

	/**
	 * Sede dell'appuntamento. Obbligatoria se per la data richiesta (anno e n.ro di prot.) ci sono appuntamenti in più sedi
	 */
	@XmlVariabile(nome = "SedeAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String sedeAppuntamento;

	/**
	 * ID univoco dell'appuntamento
	 */
	@XmlVariabile(nome = "UID", tipo = TipoVariabile.SEMPLICE)
	private String uid;

	/**
	 * Codice dell'appuntamento
	 */
	@XmlVariabile(nome = "AppCode", tipo = TipoVariabile.SEMPLICE)
	private String appCode;

	/**
	 * Data e ora dell'appuntamento nel formato xs:dateTime, obbligatoria se l'appuntamento non è da annullare
	 */
	@XmlVariabile(nome = "DataOra", tipo = TipoVariabile.SEMPLICE)
	private XMLGregorianCalendar dataOra;

	/**
	 * Dati richiedente, cioè la persone fisica intestataria della richiesta di accesso atti
	 */

	/**
	 * Nome del richiedente
	 */
	@XmlVariabile(nome = "Richiedente.Nome", tipo = TipoVariabile.SEMPLICE)
	private String nomeRichiedente;

	/**
	 * Cognome del richiedente
	 */
	@XmlVariabile(nome = "Richiedente.Cognome", tipo = TipoVariabile.SEMPLICE)
	private String cognomeRichiedente;

	/**
	 * UserID del richiedente sul sistema di prenotazione
	 */
	@XmlVariabile(nome = "Richiedente.UserID", tipo = TipoVariabile.SEMPLICE)
	private String userIDRichiedente;

	/**
	 * Codice fiscale del richiedente
	 */
	@XmlVariabile(nome = "Richiedente.CodFiscale", tipo = TipoVariabile.SEMPLICE)
	private String codiceFiscaleRichiedente;

	/**
	 * Email del richiedente
	 */
	@XmlVariabile(nome = "Richiedente.Email", tipo = TipoVariabile.SEMPLICE)
	private String emailRichiedente;

	/**
	 * Telefono del richiedente
	 */
	@XmlVariabile(nome = "Richiedente.Tel", tipo = TipoVariabile.SEMPLICE)
	private String telefonoRichiedente;

	/**
	 * Delegante: Dati di colui per cui viene preso l'appuntamento (se diverso dal richiedente)
	 */

	/**
	 * Cognome o denominazione in caso di persona giuridica
	 */
	@XmlVariabile(nome = "Delegante.Cognome_Denominazione", tipo = TipoVariabile.SEMPLICE)
	private String cognomeDenominazioneDelegante;

	/**
	 * Nome del delegante
	 */
	@XmlVariabile(nome = "Delegante.Nome", tipo = TipoVariabile.SEMPLICE)
	private String nomeDelegante;

	/**
	 * Codice fiscale del delegante
	 */
	@XmlVariabile(nome = "Delegante.CodFiscale", tipo = TipoVariabile.SEMPLICE)
	private String codiceFiscaleDelegante;

	/**
	 * Email del delegante
	 */
	@XmlVariabile(nome = "Delegante.Email", tipo = TipoVariabile.SEMPLICE)
	private String emailDelegante;

	/**
	 * Nominativo: chi si presenterà all'appuntamento (se diverso dal richiedente)
	 */

	/**
	 * Cognome di chi si presenterà all'appuntamento (se diverso dal richiedente)
	 */
	@XmlVariabile(nome = "NominativoPresenza.Cognome", tipo = TipoVariabile.SEMPLICE)
	private String cognomeNominativoPresenza;

	/**
	 * Nome di chi si presenterà all'appuntamento (se diverso dal richiedente)
	 */
	@XmlVariabile(nome = "NominativoPresenza.Nome", tipo = TipoVariabile.SEMPLICE)
	private String nomeNominativoPresenza;

	/**
	 * CodFiscale di chi si presenterà all'appuntamento (se diverso dal richiedente)
	 */
	@XmlVariabile(nome = "NominativoPresenza.CodFiscale", tipo = TipoVariabile.SEMPLICE)
	private String codiceFiscaleNominativoPresenza;

	/**
	 * Email di chi si presenterà all'appuntamento (se diverso dal richiedente)
	 */
	@XmlVariabile(nome = "NominativoPresenza.Email", tipo = TipoVariabile.SEMPLICE)
	private String emailNominativoPresenza;

	/**
	 * Telefono di chi si presenterà all'appuntamento (se diverso dal richiedente)
	 */
	@XmlVariabile(nome = "NominativoPresenza.Tel", tipo = TipoVariabile.SEMPLICE)
	private String telefonoNominativoPresenza;

	public Boolean getDaAnnullare() {
		return daAnnullare;
	}

	public void setDaAnnullare(Boolean daAnnullare) {
		this.daAnnullare = daAnnullare;
	}

	public String getSedeAppuntamento() {
		return sedeAppuntamento;
	}

	public void setSedeAppuntamento(String sedeAppuntamento) {
		this.sedeAppuntamento = sedeAppuntamento;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public XMLGregorianCalendar getDataOra() {
		return dataOra;
	}

	public void setDataOra(XMLGregorianCalendar dataOra) {
		this.dataOra = dataOra;
	}

	public String getNomeRichiedente() {
		return nomeRichiedente;
	}

	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}

	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}

	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}

	public String getUserIDRichiedente() {
		return userIDRichiedente;
	}

	public void setUserIDRichiedente(String userIDRichiedente) {
		this.userIDRichiedente = userIDRichiedente;
	}

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getEmailRichiedente() {
		return emailRichiedente;
	}

	public void setEmailRichiedente(String emailRichiedente) {
		this.emailRichiedente = emailRichiedente;
	}

	public String getTelefonoRichiedente() {
		return telefonoRichiedente;
	}

	public void setTelefonoRichiedente(String telefonoRichiedente) {
		this.telefonoRichiedente = telefonoRichiedente;
	}

	public String getCognomeDenominazioneDelegante() {
		return cognomeDenominazioneDelegante;
	}

	public void setCognomeDenominazioneDelegante(String cognomeDenominazioneDelegante) {
		this.cognomeDenominazioneDelegante = cognomeDenominazioneDelegante;
	}

	public String getNomeDelegante() {
		return nomeDelegante;
	}

	public void setNomeDelegante(String nomeDelegante) {
		this.nomeDelegante = nomeDelegante;
	}

	public String getCodiceFiscaleDelegante() {
		return codiceFiscaleDelegante;
	}

	public void setCodiceFiscaleDelegante(String codiceFiscaleDelegante) {
		this.codiceFiscaleDelegante = codiceFiscaleDelegante;
	}

	public String getEmailDelegante() {
		return emailDelegante;
	}

	public void setEmailDelegante(String emailDelegante) {
		this.emailDelegante = emailDelegante;
	}

	public String getCognomeNominativoPresenza() {
		return cognomeNominativoPresenza;
	}

	public void setCognomeNominativoPresenza(String cognomeNominativoPresenza) {
		this.cognomeNominativoPresenza = cognomeNominativoPresenza;
	}

	public String getNomeNominativoPresenza() {
		return nomeNominativoPresenza;
	}

	public void setNomeNominativoPresenza(String nomeNominativoPresenza) {
		this.nomeNominativoPresenza = nomeNominativoPresenza;
	}

	public String getCodiceFiscaleNominativoPresenza() {
		return codiceFiscaleNominativoPresenza;
	}

	public void setCodiceFiscaleNominativoPresenza(String codiceFiscaleNominativoPresenza) {
		this.codiceFiscaleNominativoPresenza = codiceFiscaleNominativoPresenza;
	}

	public String getEmailNominativoPresenza() {
		return emailNominativoPresenza;
	}

	public void setEmailNominativoPresenza(String emailNominativoPresenza) {
		this.emailNominativoPresenza = emailNominativoPresenza;
	}

	public String getTelefonoNominativoPresenza() {
		return telefonoNominativoPresenza;
	}

	public void setTelefonoNominativoPresenza(String telefonoNominativoPresenza) {
		this.telefonoNominativoPresenza = telefonoNominativoPresenza;
	}

}
