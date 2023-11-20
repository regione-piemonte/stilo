/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class PttEmailBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7334458054968198924L;

	private BigDecimal idEmail;
	private String pttDocumentiId;
	private String pttEmailId;
	private String mittente;
	private String oggetto;
	private Date dataInvio;
	private String tipoRicevuta;
	private String flgVuoleConferma;
	private String entrataUscita;
	private String codEnte;
	private BigDecimal stato;
	private BigDecimal idDocDm;
	private BigDecimal uoAssegnataria;
	private String listaIdUoAss;
	private Boolean flgProvPec;
	private String email;
	private String messageId;
	private String destinatari;
	private String destinatariCopia;
	private String corpo;
	private String statoAttivita;
	private Date dtAssegnazione;
	private Integer uoSmista;
	private Integer utenteSmista;
	private String listaSmistamenti;
	private Integer uoInvio;
	private Integer utenteInvio;
	private String pecRif;
	private Integer idUoPecRif;
	private Integer settoreAss;
	private Integer servizioAss;
	private Integer uocAss;
	private Integer uosAss;
	private Integer postazioneAss;
	private BigDecimal idDocDmEmail;
	private Date dtIns;
	private String errmsg;
	private String destinatariCcn;
	private Boolean flgSpam;
	private Date lockEmailDt;
	private BigDecimal lockEmailUte;
	private String idDocRegSuccessive;

	public BigDecimal getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(BigDecimal idEmail) {
		this.idEmail = idEmail;
	}

	public String getPttDocumentiId() {
		return pttDocumentiId;
	}

	public void setPttDocumentiId(String pttDocumentiId) {
		this.pttDocumentiId = pttDocumentiId;
	}

	public String getPttEmailId() {
		return pttEmailId;
	}

	public void setPttEmailId(String pttEmailId) {
		this.pttEmailId = pttEmailId;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}

	public String getTipoRicevuta() {
		return tipoRicevuta;
	}

	public void setTipoRicevuta(String tipoRicevuta) {
		this.tipoRicevuta = tipoRicevuta;
	}

	public String getFlgVuoleConferma() {
		return flgVuoleConferma;
	}

	public void setFlgVuoleConferma(String flgVuoleConferma) {
		this.flgVuoleConferma = flgVuoleConferma;
	}

	public String getEntrataUscita() {
		return entrataUscita;
	}

	public void setEntrataUscita(String entrataUscita) {
		this.entrataUscita = entrataUscita;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public BigDecimal getStato() {
		return stato;
	}

	public void setStato(BigDecimal stato) {
		this.stato = stato;
	}

	public BigDecimal getIdDocDm() {
		return idDocDm;
	}

	public void setIdDocDm(BigDecimal idDocDm) {
		this.idDocDm = idDocDm;
	}

	public BigDecimal getUoAssegnataria() {
		return uoAssegnataria;
	}

	public void setUoAssegnataria(BigDecimal uoAssegnataria) {
		this.uoAssegnataria = uoAssegnataria;
	}

	public String getListaIdUoAss() {
		return listaIdUoAss;
	}

	public void setListaIdUoAss(String listaIdUoAss) {
		this.listaIdUoAss = listaIdUoAss;
	}

	public Boolean getFlgProvPec() {
		return flgProvPec;
	}

	public void setFlgProvPec(Boolean flgProvPec) {
		this.flgProvPec = flgProvPec;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getDestinatari() {
		return destinatari;
	}

	public void setDestinatari(String destinatari) {
		this.destinatari = destinatari;
	}

	public String getDestinatariCopia() {
		return destinatariCopia;
	}

	public void setDestinatariCopia(String destinatariCopia) {
		this.destinatariCopia = destinatariCopia;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getStatoAttivita() {
		return statoAttivita;
	}

	public void setStatoAttivita(String statoAttivita) {
		this.statoAttivita = statoAttivita;
	}

	public Date getDtAssegnazione() {
		return dtAssegnazione;
	}

	public void setDtAssegnazione(Date dtAssegnazione) {
		this.dtAssegnazione = dtAssegnazione;
	}

	public Integer getUoSmista() {
		return uoSmista;
	}

	public void setUoSmista(Integer uoSmista) {
		this.uoSmista = uoSmista;
	}

	public Integer getUtenteSmista() {
		return utenteSmista;
	}

	public void setUtenteSmista(Integer utenteSmista) {
		this.utenteSmista = utenteSmista;
	}

	public String getListaSmistamenti() {
		return listaSmistamenti;
	}

	public void setListaSmistamenti(String listaSmistamenti) {
		this.listaSmistamenti = listaSmistamenti;
	}

	public Integer getUoInvio() {
		return uoInvio;
	}

	public void setUoInvio(Integer uoInvio) {
		this.uoInvio = uoInvio;
	}

	public Integer getUtenteInvio() {
		return utenteInvio;
	}

	public void setUtenteInvio(Integer utenteInvio) {
		this.utenteInvio = utenteInvio;
	}

	public String getPecRif() {
		return pecRif;
	}

	public void setPecRif(String pecRif) {
		this.pecRif = pecRif;
	}

	public Integer getIdUoPecRif() {
		return idUoPecRif;
	}

	public void setIdUoPecRif(Integer idUoPecRif) {
		this.idUoPecRif = idUoPecRif;
	}

	public Integer getSettoreAss() {
		return settoreAss;
	}

	public void setSettoreAss(Integer settoreAss) {
		this.settoreAss = settoreAss;
	}

	public Integer getServizioAss() {
		return servizioAss;
	}

	public void setServizioAss(Integer servizioAss) {
		this.servizioAss = servizioAss;
	}

	public Integer getUocAss() {
		return uocAss;
	}

	public void setUocAss(Integer uocAss) {
		this.uocAss = uocAss;
	}

	public Integer getUosAss() {
		return uosAss;
	}

	public void setUosAss(Integer uosAss) {
		this.uosAss = uosAss;
	}

	public Integer getPostazioneAss() {
		return postazioneAss;
	}

	public void setPostazioneAss(Integer postazioneAss) {
		this.postazioneAss = postazioneAss;
	}

	public BigDecimal getIdDocDmEmail() {
		return idDocDmEmail;
	}

	public void setIdDocDmEmail(BigDecimal idDocDmEmail) {
		this.idDocDmEmail = idDocDmEmail;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getDestinatariCcn() {
		return destinatariCcn;
	}

	public void setDestinatariCcn(String destinatariCcn) {
		this.destinatariCcn = destinatariCcn;
	}

	public Boolean getFlgSpam() {
		return flgSpam;
	}

	public void setFlgSpam(Boolean flgSpam) {
		this.flgSpam = flgSpam;
	}

	public Date getLockEmailDt() {
		return lockEmailDt;
	}

	public void setLockEmailDt(Date lockEmailDt) {
		this.lockEmailDt = lockEmailDt;
	}

	public BigDecimal getLockEmailUte() {
		return lockEmailUte;
	}

	public void setLockEmailUte(BigDecimal lockEmailUte) {
		this.lockEmailUte = lockEmailUte;
	}

	public String getIdDocRegSuccessive() {
		return idDocRegSuccessive;
	}

	public void setIdDocRegSuccessive(String idDocRegSuccessive) {
		this.idDocRegSuccessive = idDocRegSuccessive;
	}

}