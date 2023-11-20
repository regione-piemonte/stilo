/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FolderCustomBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;

public class RegoleProtocollazioneAutomaticaCaselleBean extends RegoleProtocollazioneAutomaticaCaselleXmlBean {

	private Date dtAttivazione;
	private Date dtCessazione;
	private Date dtInizioSospensione;
	private Date dtFineSospensione;
	private List<String> caselle;
	private String flgTipoEmailRicezione;
	private List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaIndirizziMittenti;
	private String flgTipoEmailEntrata;
	private List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaDatiSegnatura;
	private List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaParoleInOggettoMail;
	private List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaParoleInTestoMail;
	private List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaEmailDestinatari;
	private String flgTipoRegistrazione;
	private String repertorio;
	private List<UoRegProtAutoCaselleBean> uoRegistrazione;
	private String idUtente;
	private String desUtenteRegistrazione;
	private List<MittenteProtBean> mittenteRegistrazione;
	private List<UoRegProtAutoCaselleBean> listaUoDestinatarie;
	private String flgRiservatezza;
	private List<ClassificazioneFascicoloBean> classificazioneFascicolazione;
	private List<FolderCustomBean> listaFolderCustom;
	private String flgTimbratura;
	private String flgNotificaConferma;
	private String indirizzoDestinatarioRisposta;
	private String oggettoRisposta;
	private String testoRisposta;
	private String motivoCancellazione;
	
	public Date getDtAttivazione() {
		return dtAttivazione;
	}
	public void setDtAttivazione(Date dtAttivazione) {
		this.dtAttivazione = dtAttivazione;
	}
	public Date getDtCessazione() {
		return dtCessazione;
	}
	public void setDtCessazione(Date dtCessazione) {
		this.dtCessazione = dtCessazione;
	}
	public Date getDtInizioSospensione() {
		return dtInizioSospensione;
	}
	public void setDtInizioSospensione(Date dtInizioSospensione) {
		this.dtInizioSospensione = dtInizioSospensione;
	}
	public Date getDtFineSospensione() {
		return dtFineSospensione;
	}
	public void setDtFineSospensione(Date dtFineSospensione) {
		this.dtFineSospensione = dtFineSospensione;
	}
	public List<String> getCaselle() {
		return caselle;
	}
	public void setCaselle(List<String> caselle) {
		this.caselle = caselle;
	}
	public String getFlgTipoEmailRicezione() {
		return flgTipoEmailRicezione;
	}
	public void setFlgTipoEmailRicezione(String flgTipoEmailRicezione) {
		this.flgTipoEmailRicezione = flgTipoEmailRicezione;
	}
	public List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> getListaIndirizziMittenti() {
		return listaIndirizziMittenti;
	}
	public void setListaIndirizziMittenti(
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaIndirizziMittenti) {
		this.listaIndirizziMittenti = listaIndirizziMittenti;
	}
	public String getFlgTipoEmailEntrata() {
		return flgTipoEmailEntrata;
	}
	public void setFlgTipoEmailEntrata(String flgTipoEmailEntrata) {
		this.flgTipoEmailEntrata = flgTipoEmailEntrata;
	}
	public List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> getListaDatiSegnatura() {
		return listaDatiSegnatura;
	}
	public void setListaDatiSegnatura(List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaDatiSegnatura) {
		this.listaDatiSegnatura = listaDatiSegnatura;
	}
	public List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> getListaParoleInOggettoMail() {
		return listaParoleInOggettoMail;
	}
	public void setListaParoleInOggettoMail(
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaParoleInOggettoMail) {
		this.listaParoleInOggettoMail = listaParoleInOggettoMail;
	}
	public List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> getListaParoleInTestoMail() {
		return listaParoleInTestoMail;
	}
	public void setListaParoleInTestoMail(
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaParoleInTestoMail) {
		this.listaParoleInTestoMail = listaParoleInTestoMail;
	}
	public List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> getListaEmailDestinatari() {
		return listaEmailDestinatari;
	}
	public void setListaEmailDestinatari(
			List<CaratteristicheEmailProtocollazioneAutomaticaCaselleBean> listaEmailDestinatari) {
		this.listaEmailDestinatari = listaEmailDestinatari;
	}
	public String getFlgTipoRegistrazione() {
		return flgTipoRegistrazione;
	}
	public void setFlgTipoRegistrazione(String flgTipoRegistrazione) {
		this.flgTipoRegistrazione = flgTipoRegistrazione;
	}
	public String getRepertorio() {
		return repertorio;
	}
	public void setRepertorio(String repertorio) {
		this.repertorio = repertorio;
	}
	public List<UoRegProtAutoCaselleBean> getUoRegistrazione() {
		return uoRegistrazione;
	}
	public void setUoRegistrazione(List<UoRegProtAutoCaselleBean> uoRegistrazione) {
		this.uoRegistrazione = uoRegistrazione;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getDesUtenteRegistrazione() {
		return desUtenteRegistrazione;
	}
	public void setDesUtenteRegistrazione(String desUtenteRegistrazione) {
		this.desUtenteRegistrazione = desUtenteRegistrazione;
	}
	public List<MittenteProtBean> getMittenteRegistrazione() {
		return mittenteRegistrazione;
	}
	public void setMittenteRegistrazione(List<MittenteProtBean> mittenteRegistrazione) {
		this.mittenteRegistrazione = mittenteRegistrazione;
	}
	public List<UoRegProtAutoCaselleBean> getListaUoDestinatarie() {
		return listaUoDestinatarie;
	}
	public void setListaUoDestinatarie(List<UoRegProtAutoCaselleBean> listaUoDestinatarie) {
		this.listaUoDestinatarie = listaUoDestinatarie;
	}
	public String getFlgRiservatezza() {
		return flgRiservatezza;
	}
	public void setFlgRiservatezza(String flgRiservatezza) {
		this.flgRiservatezza = flgRiservatezza;
	}
	public List<ClassificazioneFascicoloBean> getClassificazioneFascicolazione() {
		return classificazioneFascicolazione;
	}
	public void setClassificazioneFascicolazione(List<ClassificazioneFascicoloBean> classificazioneFascicolazione) {
		this.classificazioneFascicolazione = classificazioneFascicolazione;
	}
	public List<FolderCustomBean> getListaFolderCustom() {
		return listaFolderCustom;
	}
	public void setListaFolderCustom(List<FolderCustomBean> listaFolderCustom) {
		this.listaFolderCustom = listaFolderCustom;
	}
	public String getFlgTimbratura() {
		return flgTimbratura;
	}
	public void setFlgTimbratura(String flgTimbratura) {
		this.flgTimbratura = flgTimbratura;
	}
	public String getFlgNotificaConferma() {
		return flgNotificaConferma;
	}
	public void setFlgNotificaConferma(String flgNotificaConferma) {
		this.flgNotificaConferma = flgNotificaConferma;
	}
	public String getIndirizzoDestinatarioRisposta() {
		return indirizzoDestinatarioRisposta;
	}
	public void setIndirizzoDestinatarioRisposta(String indirizzoDestinatarioRisposta) {
		this.indirizzoDestinatarioRisposta = indirizzoDestinatarioRisposta;
	}
	public String getOggettoRisposta() {
		return oggettoRisposta;
	}
	public void setOggettoRisposta(String oggettoRisposta) {
		this.oggettoRisposta = oggettoRisposta;
	}
	public String getTestoRisposta() {
		return testoRisposta;
	}
	public void setTestoRisposta(String testoRisposta) {
		this.testoRisposta = testoRisposta;
	}
	public String getMotivoCancellazione() {
		return motivoCancellazione;
	}
	public void setMotivoCancellazione(String motivoCancellazione) {
		this.motivoCancellazione = motivoCancellazione;
	}

}
