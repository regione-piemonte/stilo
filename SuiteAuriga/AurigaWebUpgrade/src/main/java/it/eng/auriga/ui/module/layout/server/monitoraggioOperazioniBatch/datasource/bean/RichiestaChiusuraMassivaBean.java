/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

public class RichiestaChiusuraMassivaBean {
	
	
	private String tipoOggettiDaChiudere;
	private String caselle;
	private String struttureSTD;
	private  List<AssegnatarioOpBatchXmlBean>  struttureNONSTD;
	private Date dtOperazione;
	private Date dataInvioDa;
	private Date dataInvioA;	
	private String periodoApertura;
	private String tipoPeriodoApertura;
	private String periodoSenzaOperazioni;	
	private String tipoPeriodoSenzaOperazioni;
	private String motivazioneRichiesta;
	private String noteRichiesta;
	private String idJob;
	private String uri;
	private String storeErrorMessage;
	private boolean storeInError;

	public String getTipoOggettiDaChiudere() {
		return tipoOggettiDaChiudere;
	}
	public void setTipoOggettiDaChiudere(String tipoOggettiDaChiudere) {
		this.tipoOggettiDaChiudere = tipoOggettiDaChiudere;
	}
	public String getCaselle() {
		return caselle;
	}
	public void setCaselle(String caselle) {
		this.caselle = caselle;
	}
	
	public Date getDtOperazione() {
		return dtOperazione;
	}
	public void setDtOperazione(Date dtOperazione) {
		this.dtOperazione = dtOperazione;
	}
	public Date getDataInvioDa() {
		return dataInvioDa;
	}
	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}
	public Date getDataInvioA() {
		return dataInvioA;
	}
	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}
	public String getPeriodoApertura() {
		return periodoApertura;
	}
	public void setPeriodoApertura(String periodoApertura) {
		this.periodoApertura = periodoApertura;
	}
	public String getTipoPeriodoApertura() {
		return tipoPeriodoApertura;
	}
	public void setTipoPeriodoApertura(String tipoPeriodoApertura) {
		this.tipoPeriodoApertura = tipoPeriodoApertura;
	}
	public String getPeriodoSenzaOperazioni() {
		return periodoSenzaOperazioni;
	}
	public void setPeriodoSenzaOperazioni(String periodoSenzaOperazioni) {
		this.periodoSenzaOperazioni = periodoSenzaOperazioni;
	}
	public String getTipoPeriodoSenzaOperazioni() {
		return tipoPeriodoSenzaOperazioni;
	}
	public void setTipoPeriodoSenzaOperazioni(String tipoPeriodoSenzaOperazioni) {
		this.tipoPeriodoSenzaOperazioni = tipoPeriodoSenzaOperazioni;
	}
	public String getMotivazioneRichiesta() {
		return motivazioneRichiesta;
	}
	public void setMotivazioneRichiesta(String motivazioneRichiesta) {
		this.motivazioneRichiesta = motivazioneRichiesta;
	}
	public String getNoteRichiesta() {
		return noteRichiesta;
	}
	public void setNoteRichiesta(String noteRichiesta) {
		this.noteRichiesta = noteRichiesta;
	}
	public String getIdJob() {
		return idJob;
	}
	public void setIdJob(String idJob) {
		this.idJob = idJob;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getStoreErrorMessage() {
		return storeErrorMessage;
	}
	public void setStoreErrorMessage(String storeErrorMessage) {
		this.storeErrorMessage = storeErrorMessage;
	}
	public boolean isStoreInError() {
		return storeInError;
	}
	public void setStoreInError(boolean storeInError) {
		this.storeInError = storeInError;
	}
	public List<AssegnatarioOpBatchXmlBean> getStruttureNONSTD() {
		return struttureNONSTD;
	}
	public void setStruttureNONSTD(List<AssegnatarioOpBatchXmlBean> struttureNONSTD) {
		this.struttureNONSTD = struttureNONSTD;
	}
	public String getStruttureSTD() {
		return struttureSTD;
	}
	public void setStruttureSTD(String struttureSTD) {
		this.struttureSTD = struttureSTD;
	}
	
	
	
			
}
