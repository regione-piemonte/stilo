/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.anagrafiche.datasource.bean.IndirizzoSoggettoBean;

import java.util.Date;
import java.util.List;

public class PraticaBean {
	
	//CAMPI LISTA
	private String idPratica; 
	private String tipoProcedimento;
	private String descrTipoProcedimento;
	private Date tsIns; 
	private String nroProt; 
	private Date tsProt; 
	private String nroPratica;
	private String nroSerie;
	private String nroRegistro;
	private String stato;
	private String richiedente;
	private String respProc;
	private String integrazioni;
	private Date dtScadPresIntegr;
	private Date dtDecorrenzaTerm;
	private Date dtScadTermProc;
	private String provvedimentoFinale;
	private String intestazione;
	private String idUd;
	private String idDocAttestatoAvvioProc;
	
	//CAMPI INSERIMENTO	
	private String idSoggGiuridico;
	private String idSoggEsterno;
	private String tipoSoggetto;
	private String denominazione;
	private String cognome;
	private String nome;
	private String codFiscale;
	private String partitaIva;
	private List<IndirizzoSoggettoBean> indirizzoSedeLegale;
	private List<IndirizzoSoggettoBean> indirizzoRecapito;	
	private String email;
	private String telefono;
	
	private String nomeProgetto;
//	private String tipoProgetto;	
	private String tipoAllegato;
	private String flgNuovoModifica;
	private String tipoOstA1;
	private String tipoOstB1;
	private String descrizioneSintetica;
	private String messaggioUltimaAttivita;
	
	private List<ModelloAttivitaBean> listaModelliAttivita;	
	
	private String warningConcorrenza;
	private String isAtto;
	
	public String getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}
	public String getTipoProcedimento() {
		return tipoProcedimento;
	}
	public void setTipoProcedimento(String tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}
	public Date getTsIns() {
		return tsIns;
	}
	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}
	public String getNroProt() {
		return nroProt;
	}
	public void setNroProt(String nroProt) {
		this.nroProt = nroProt;
	}
	public Date getTsProt() {
		return tsProt;
	}
	public void setTsProt(Date tsProt) {
		this.tsProt = tsProt;
	}
	public String getNroPratica() {
		return nroPratica;
	}
	public void setNroPratica(String nroPratica) {
		this.nroPratica = nroPratica;
	}
	public String getNroSerie() {
		return nroSerie;
	}
	public void setNroSerie(String nroSerie) {
		this.nroSerie = nroSerie;
	}
	public String getNroRegistro() {
		return nroRegistro;
	}
	public void setNroRegistro(String nroRegistro) {
		this.nroRegistro = nroRegistro;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(String richiedente) {
		this.richiedente = richiedente;
	}
	public String getRespProc() {
		return respProc;
	}
	public void setRespProc(String respProc) {
		this.respProc = respProc;
	}
	public String getIntegrazioni() {
		return integrazioni;
	}
	public void setIntegrazioni(String integrazioni) {
		this.integrazioni = integrazioni;
	}
	public Date getDtScadPresIntegr() {
		return dtScadPresIntegr;
	}
	public void setDtScadPresIntegr(Date dtScadPresIntegr) {
		this.dtScadPresIntegr = dtScadPresIntegr;
	}
	public Date getDtDecorrenzaTerm() {
		return dtDecorrenzaTerm;
	}
	public void setDtDecorrenzaTerm(Date dtDecorrenzaTerm) {
		this.dtDecorrenzaTerm = dtDecorrenzaTerm;
	}
	public Date getDtScadTermProc() {
		return dtScadTermProc;
	}
	public void setDtScadTermProc(Date dtScadTermProc) {
		this.dtScadTermProc = dtScadTermProc;
	}
	public String getProvvedimentoFinale() {
		return provvedimentoFinale;
	}
	public void setProvvedimentoFinale(String provvedimentoFinale) {
		this.provvedimentoFinale = provvedimentoFinale;
	}
	public String getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}	
	public String getIdSoggEsterno() {
		return idSoggEsterno;
	}
	public void setIdSoggEsterno(String idSoggEsterno) {
		this.idSoggEsterno = idSoggEsterno;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public List<IndirizzoSoggettoBean> getIndirizzoSedeLegale() {
		return indirizzoSedeLegale;
	}
	public void setIndirizzoSedeLegale(List<IndirizzoSoggettoBean> indirizzoSedeLegale) {
		this.indirizzoSedeLegale = indirizzoSedeLegale;
	}
	public List<IndirizzoSoggettoBean> getIndirizzoRecapito() {
		return indirizzoRecapito;
	}
	public void setIndirizzoRecapito(List<IndirizzoSoggettoBean> indirizzoRecapito) {
		this.indirizzoRecapito = indirizzoRecapito;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}	
	public String getNomeProgetto() {
		return nomeProgetto;
	}
	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}
//	public String getTipoProgetto() {
//		return tipoProgetto;
//	}
//	public void setTipoProgetto(String tipoProgetto) {
//		this.tipoProgetto = tipoProgetto;
//	}
	public String getDescrizioneSintetica() {
		return descrizioneSintetica;
	}
	public void setDescrizioneSintetica(String descrizioneSintetica) {
		this.descrizioneSintetica = descrizioneSintetica;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getIdSoggGiuridico() {
		return idSoggGiuridico;
	}
	public void setIdSoggGiuridico(String idSoggGiuridico) {
		this.idSoggGiuridico = idSoggGiuridico;
	}
	public String getDescrTipoProcedimento() {
		return descrTipoProcedimento;
	}
	public void setDescrTipoProcedimento(String descrTipoProcedimento) {
		this.descrTipoProcedimento = descrTipoProcedimento;
	}	
	public String getTipoAllegato() {
		return tipoAllegato;
	}
	public void setTipoAllegato(String tipoAllegato) {
		this.tipoAllegato = tipoAllegato;
	}
	public String getFlgNuovoModifica() {
		return flgNuovoModifica;
	}
	public void setFlgNuovoModifica(String flgNuovoModifica) {
		this.flgNuovoModifica = flgNuovoModifica;
	}
	public String getTipoOstA1() {
		return tipoOstA1;
	}
	public void setTipoOstA1(String tipoOstA1) {
		this.tipoOstA1 = tipoOstA1;
	}
	public String getTipoOstB1() {
		return tipoOstB1;
	}
	public void setTipoOstB1(String tipoOstB1) {
		this.tipoOstB1 = tipoOstB1;
	}
	public String getIdDocAttestatoAvvioProc() {
		return idDocAttestatoAvvioProc;
	}
	public void setIdDocAttestatoAvvioProc(String idDocAttestatoAvvioProc) {
		this.idDocAttestatoAvvioProc = idDocAttestatoAvvioProc;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getMessaggioUltimaAttivita() {
		return messaggioUltimaAttivita;
	}
	public void setMessaggioUltimaAttivita(String messaggioUltimaAttivita) {
		this.messaggioUltimaAttivita = messaggioUltimaAttivita;
	}
	public List<ModelloAttivitaBean> getListaModelliAttivita() {
		return listaModelliAttivita;
	}
	public void setListaModelliAttivita(List<ModelloAttivitaBean> listaModelliAttivita) {
		this.listaModelliAttivita = listaModelliAttivita;
	}
	public String getWarningConcorrenza() {
		return warningConcorrenza;
	}
	public void setWarningConcorrenza(String warningConcorrenza) {
		this.warningConcorrenza = warningConcorrenza;
	}
	public String getIsAtto() {
		return isAtto;
	}
	public void setIsAtto(String isAtto) {
		this.isAtto = isAtto;
	}
	
}