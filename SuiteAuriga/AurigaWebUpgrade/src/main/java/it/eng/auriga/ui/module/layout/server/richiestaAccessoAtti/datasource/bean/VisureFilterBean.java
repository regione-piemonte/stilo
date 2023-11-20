/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.utility.ui.module.core.shared.bean.ScadenzaBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class VisureFilterBean {

	private String svoltaFase;
	private String effettuatoTask;
	private String daIniziareFase;
	private String daCompletareFase;
	private String daEffettuareTask;
	private Date dataDiAvvioStart;
	private Date dataDiAvvioEnd;
	private String stato;
	private String inFase;
	private String eseguibileTask;
	private ScadenzaBean scadenza;
	private String ricercatoreIncaricato;
	private String richiedenteFilter;
	private String indirizzoFilter;
	private String udcFilter;
	
	public Date getDataDiAvvioStart() {
		return dataDiAvvioStart;
	}
	public void setDataDiAvvioStart(Date dataDiAvvioStart) {
		this.dataDiAvvioStart = dataDiAvvioStart;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Date getDataDiAvvioEnd() {
		return dataDiAvvioEnd;
	}
	public void setDataDiAvvioEnd(Date dataDiAvvioEnd) {
		this.dataDiAvvioEnd = dataDiAvvioEnd;
	}
	public String getSvoltaFase() {
		return svoltaFase;
	}
	public void setSvoltaFase(String svoltaFase) {
		this.svoltaFase = svoltaFase;
	}
	public String getEffettuatoTask() {
		return effettuatoTask;
	}
	public void setEffettuatoTask(String effettuatoTask) {
		this.effettuatoTask = effettuatoTask;
	}
	public String getDaIniziareFase() {
		return daIniziareFase;
	}
	public void setDaIniziareFase(String daIniziareFase) {
		this.daIniziareFase = daIniziareFase;
	}
	public String getDaCompletareFase() {
		return daCompletareFase;
	}
	public void setDaCompletareFase(String daCompletareFase) {
		this.daCompletareFase = daCompletareFase;
	}
	public String getDaEffettuareTask() {
		return daEffettuareTask;
	}
	public void setDaEffettuareTask(String daEffettuareTask) {
		this.daEffettuareTask = daEffettuareTask;
	}
	public String getInFase() {
		return inFase;
	}
	public void setInFase(String inFase) {
		this.inFase = inFase;
	}
	public String getEseguibileTask() {
		return eseguibileTask;
	}
	public void setEseguibileTask(String eseguibileTask) {
		this.eseguibileTask = eseguibileTask;
	}
	public ScadenzaBean getScadenza() {
		return scadenza;
	}
	public void setScadenza(ScadenzaBean scadenza) {
		this.scadenza = scadenza;
	}
	public String getRicercatoreIncaricato() {
		return ricercatoreIncaricato;
	}
	public void setRicercatoreIncaricato(String ricercatoreIncaricato) {
		this.ricercatoreIncaricato = ricercatoreIncaricato;
	}
	public String getRichiedenteFilter() {
		return richiedenteFilter;
	}
	public void setRichiedenteFilter(String richiedenteFilter) {
		this.richiedenteFilter = richiedenteFilter;
	}
	public String getIndirizzoFilter() {
		return indirizzoFilter;
	}
	public void setIndirizzoFilter(String indirizzoFilter) {
		this.indirizzoFilter = indirizzoFilter;
	}
	public String getUdcFilter() {
		return udcFilter;
	}
	public void setUdcFilter(String udcFilter) {
		this.udcFilter = udcFilter;
	}
}