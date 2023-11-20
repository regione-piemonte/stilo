/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author DANCRIST
 *
 */

public class TrovaPdVFiltriXmlBean {
	
	@XmlVariabile(nome = "IdPdV", tipo = TipoVariabile.SEMPLICE)
	private String idPdV;
	
	@XmlVariabile(nome = "LabelPdV", tipo = TipoVariabile.SEMPLICE)
	private String etichetta;

	@XmlVariabile(nome = "Stati", tipo = TipoVariabile.SEMPLICE)
	private String stato;

	@XmlVariabile(nome = "TsUltimoAggStatoDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsUltimoAggStatoDa;
	
	@XmlVariabile(nome = "TsUltimoAggStatoA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA)
	private Date tsUltimoAggStatoA;

	@XmlVariabile(nome = "DtGenerazioneDa", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataGenerazionePdVDa;
	
	@XmlVariabile(nome = "DtGenerazioneA", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataGenerazionePdVA;
	
	@XmlVariabile(nome = "CodProcessiBancaProd", tipo = TipoVariabile.SEMPLICE)
	private String codProcessoBancaProd;
	
	@XmlVariabile(nome = "NroDocPdVDa", tipo = TipoVariabile.SEMPLICE)
	private String nroDocPdVDa;

	@XmlVariabile(nome = "NroDocPdVA", tipo = TipoVariabile.SEMPLICE)
	private String nroDocPdVA;
	
	@XmlVariabile(nome = "VolDocPdVDa", tipo = TipoVariabile.SEMPLICE)
	private String volDocPdVDa;

	@XmlVariabile(nome = "VolDocPdVA", tipo = TipoVariabile.SEMPLICE)
	private String volDocPdVA;
	
	@XmlVariabile(nome = "CodiciErrWarnTrasm", tipo = TipoVariabile.SEMPLICE)
	private String codiciErrWarnTrasm;

	@XmlVariabile(nome = "MsgErrWarnTrasm", tipo = TipoVariabile.SEMPLICE)
	private String msgErrWarnTrasm;
	
	@XmlVariabile(nome = "CodiciErrWarnRecRdV", tipo = TipoVariabile.SEMPLICE)
	private String codiciErrWarnRecRdV;

	@XmlVariabile(nome = "MsgErrWarnRecRdV", tipo = TipoVariabile.SEMPLICE)
	private String msgErrWarnRecRdV;
	
	@XmlVariabile(nome = "TempoRicezRicTrasmDa", tipo = TipoVariabile.SEMPLICE)
	private String tempoRicezRicTrasmDa;

	@XmlVariabile(nome = "TempoRicezRicTrasmA", tipo = TipoVariabile.SEMPLICE)
	private String tempoRicezRicTrasmA;
	
	@XmlVariabile(nome = "TempoSenzaRicTrasmDa", tipo = TipoVariabile.SEMPLICE)
	private String tempoSenzaRicTrasmDa;
	
	@XmlVariabile(nome = "TempoSenzaRicTrasmA", tipo = TipoVariabile.SEMPLICE)
	private String tempoSenzaRicTrasmA;
	
	@XmlVariabile(nome = "TempoRicezioneRdVDa", tipo = TipoVariabile.SEMPLICE)
	private String tempoRicezioneRdVDa;
	
	@XmlVariabile(nome = "TempoRicezioneRdVA", tipo = TipoVariabile.SEMPLICE)
	private String tempoRicezioneRdVA;

	@XmlVariabile(nome = "TempoSenzaRdVDa", tipo = TipoVariabile.SEMPLICE)
	private String tempoSenzaRdVDa;
	
	@XmlVariabile(nome = "TempoSenzaRdVA", tipo = TipoVariabile.SEMPLICE)
	private String tempoSenzaRdVA;

	public String getIdPdV() {
		return idPdV;
	}
	public void setIdPdV(String idPdV) {
		this.idPdV = idPdV;
	}
	public String getEtichetta() {
		return etichetta;
	}
	public void setEtichetta(String etichetta) {
		this.etichetta = etichetta;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Date getTsUltimoAggStatoDa() {
		return tsUltimoAggStatoDa;
	}
	public void setTsUltimoAggStatoDa(Date tsUltimoAggStatoDa) {
		this.tsUltimoAggStatoDa = tsUltimoAggStatoDa;
	}
	public Date getTsUltimoAggStatoA() {
		return tsUltimoAggStatoA;
	}
	public void setTsUltimoAggStatoA(Date tsUltimoAggStatoA) {
		this.tsUltimoAggStatoA = tsUltimoAggStatoA;
	}
	public Date getDataGenerazionePdVDa() {
		return dataGenerazionePdVDa;
	}
	public void setDataGenerazionePdVDa(Date dataGenerazionePdVDa) {
		this.dataGenerazionePdVDa = dataGenerazionePdVDa;
	}
	public Date getDataGenerazionePdVA() {
		return dataGenerazionePdVA;
	}
	public void setDataGenerazionePdVA(Date dataGenerazionePdVA) {
		this.dataGenerazionePdVA = dataGenerazionePdVA;
	}
	public String getCodProcessoBancaProd() {
		return codProcessoBancaProd;
	}
	public void setCodProcessoBancaProd(String codProcessoBancaProd) {
		this.codProcessoBancaProd = codProcessoBancaProd;
	}
	public String getNroDocPdVDa() {
		return nroDocPdVDa;
	}
	public void setNroDocPdVDa(String nroDocPdVDa) {
		this.nroDocPdVDa = nroDocPdVDa;
	}
	public String getNroDocPdVA() {
		return nroDocPdVA;
	}
	public void setNroDocPdVA(String nroDocPdVA) {
		this.nroDocPdVA = nroDocPdVA;
	}
	public String getVolDocPdVDa() {
		return volDocPdVDa;
	}
	public void setVolDocPdVDa(String volDocPdVDa) {
		this.volDocPdVDa = volDocPdVDa;
	}
	public String getVolDocPdVA() {
		return volDocPdVA;
	}
	public void setVolDocPdVA(String volDocPdVA) {
		this.volDocPdVA = volDocPdVA;
	}
	public String getCodiciErrWarnTrasm() {
		return codiciErrWarnTrasm;
	}
	public void setCodiciErrWarnTrasm(String codiciErrWarnTrasm) {
		this.codiciErrWarnTrasm = codiciErrWarnTrasm;
	}
	public String getMsgErrWarnTrasm() {
		return msgErrWarnTrasm;
	}
	public void setMsgErrWarnTrasm(String msgErrWarnTrasm) {
		this.msgErrWarnTrasm = msgErrWarnTrasm;
	}
	public String getCodiciErrWarnRecRdV() {
		return codiciErrWarnRecRdV;
	}
	public void setCodiciErrWarnRecRdV(String codiciErrWarnRecRdV) {
		this.codiciErrWarnRecRdV = codiciErrWarnRecRdV;
	}
	public String getMsgErrWarnRecRdV() {
		return msgErrWarnRecRdV;
	}
	public void setMsgErrWarnRecRdV(String msgErrWarnRecRdV) {
		this.msgErrWarnRecRdV = msgErrWarnRecRdV;
	}
	public String getTempoRicezRicTrasmDa() {
		return tempoRicezRicTrasmDa;
	}
	public void setTempoRicezRicTrasmDa(String tempoRicezRicTrasmDa) {
		this.tempoRicezRicTrasmDa = tempoRicezRicTrasmDa;
	}
	public String getTempoRicezRicTrasmA() {
		return tempoRicezRicTrasmA;
	}
	public void setTempoRicezRicTrasmA(String tempoRicezRicTrasmA) {
		this.tempoRicezRicTrasmA = tempoRicezRicTrasmA;
	}
	public String getTempoSenzaRicTrasmDa() {
		return tempoSenzaRicTrasmDa;
	}
	public void setTempoSenzaRicTrasmDa(String tempoSenzaRicTrasmDa) {
		this.tempoSenzaRicTrasmDa = tempoSenzaRicTrasmDa;
	}
	public String getTempoSenzaRicTrasmA() {
		return tempoSenzaRicTrasmA;
	}
	public void setTempoSenzaRicTrasmA(String tempoSenzaRicTrasmA) {
		this.tempoSenzaRicTrasmA = tempoSenzaRicTrasmA;
	}
	public String getTempoRicezioneRdVDa() {
		return tempoRicezioneRdVDa;
	}
	public void setTempoRicezioneRdVDa(String tempoRicezioneRdVDa) {
		this.tempoRicezioneRdVDa = tempoRicezioneRdVDa;
	}
	public String getTempoRicezioneRdVA() {
		return tempoRicezioneRdVA;
	}
	public void setTempoRicezioneRdVA(String tempoRicezioneRdVA) {
		this.tempoRicezioneRdVA = tempoRicezioneRdVA;
	}
	public String getTempoSenzaRdVDa() {
		return tempoSenzaRdVDa;
	}
	public void setTempoSenzaRdVDa(String tempoSenzaRdVDa) {
		this.tempoSenzaRdVDa = tempoSenzaRdVDa;
	}
	public String getTempoSenzaRdVA() {
		return tempoSenzaRdVA;
	}
	public void setTempoSenzaRdVA(String tempoSenzaRdVA) {
		this.tempoSenzaRdVA = tempoSenzaRdVA;
	}
	
}
