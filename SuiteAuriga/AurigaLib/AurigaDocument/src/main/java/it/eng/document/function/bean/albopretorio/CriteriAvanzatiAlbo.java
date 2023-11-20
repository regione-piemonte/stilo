/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class CriteriAvanzatiAlbo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlVariabile(nome="FlgSoloUOAttive", tipo = TipoVariabile.SEMPLICE)
	private String flgSoloUOAttive;
	
	@XmlVariabile(nome="StatoPubblicazione", tipo=TipoVariabile.SEMPLICE)
	private final String statoPubblicazione = "IN_PUBBLICAZIONE";
	
	@XmlVariabile(nome="@Registrazioni", tipo=TipoVariabile.LISTA)
	private List<RegistrazioneAlbo> registrazioni;

	@XmlVariabile(nome="IdDocType", tipo=TipoVariabile.SEMPLICE)
	private String idDocType;

	@XmlVariabile(nome="OggettoUD", tipo=TipoVariabile.SEMPLICE)
	private String oggettoUD;	

	@XmlVariabile(nome="InizioPubblicazioneDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date inizioPubblicazioneDal;
	
	@XmlVariabile(nome="InizioPubblicazioneAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date inizioPubblicazioneAl;
	
	@XmlVariabile(nome="TerminePubblicazioneDal", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date terminePubblicazioneDal;
	
	@XmlVariabile(nome="TerminePubblicazioneAl", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date terminePubblicazioneAl;
	
	@XmlVariabile(nome="MittenteUD", tipo=TipoVariabile.SEMPLICE)
	private String mittenteUD;	
	
	@XmlVariabile(nome="IdMittenteUDInRubrica", tipo=TipoVariabile.SEMPLICE)
	private String idMittenteUDInRubrica;	
	
	@XmlVariabile(nome="StatoPubblicazioneDett", tipo=TipoVariabile.SEMPLICE)
	private String statoPubblicazioneDett;

	public String getFlgSoloUOAttive() {
		return flgSoloUOAttive;
	}

	public void setFlgSoloUOAttive(String flgSoloUOAttive) {
		this.flgSoloUOAttive = flgSoloUOAttive;
	}

	public List<RegistrazioneAlbo> getRegistrazioni() {
		return registrazioni;
	}

	public void setRegistrazioni(List<RegistrazioneAlbo> registrazioni) {
		this.registrazioni = registrazioni;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getOggettoUD() {
		return oggettoUD;
	}

	public void setOggettoUD(String oggettoUD) {
		this.oggettoUD = oggettoUD;
	}

	public Date getInizioPubblicazioneDal() {
		return inizioPubblicazioneDal;
	}

	public void setInizioPubblicazioneDal(Date inizioPubblicazioneDal) {
		this.inizioPubblicazioneDal = inizioPubblicazioneDal;
	}

	public Date getInizioPubblicazioneAl() {
		return inizioPubblicazioneAl;
	}

	public void setInizioPubblicazioneAl(Date inizioPubblicazioneAl) {
		this.inizioPubblicazioneAl = inizioPubblicazioneAl;
	}

	public Date getTerminePubblicazioneDal() {
		return terminePubblicazioneDal;
	}

	public void setTerminePubblicazioneDal(Date terminePubblicazioneDal) {
		this.terminePubblicazioneDal = terminePubblicazioneDal;
	}

	public Date getTerminePubblicazioneAl() {
		return terminePubblicazioneAl;
	}

	public void setTerminePubblicazioneAl(Date terminePubblicazioneAl) {
		this.terminePubblicazioneAl = terminePubblicazioneAl;
	}

	public String getMittenteUD() {
		return mittenteUD;
	}

	public void setMittenteUD(String mittenteUD) {
		this.mittenteUD = mittenteUD;
	}

	public String getIdMittenteUDInRubrica() {
		return idMittenteUDInRubrica;
	}

	public void setIdMittenteUDInRubrica(String idMittenteUDInRubrica) {
		this.idMittenteUDInRubrica = idMittenteUDInRubrica;
	}

	public String getStatoPubblicazioneDett() {
		return statoPubblicazioneDett;
	}

	public void setStatoPubblicazioneDett(String statoPubblicazioneDett) {
		this.statoPubblicazioneDett = statoPubblicazioneDett;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatoPubblicazione() {
		return statoPubblicazione;
	}	

}