/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.ScadenzaBean;

/**
 * 
 * @author dbe4235
 *
 */

public class RichiesteAutotuteleCedPersonaliFilterBean {
	
	private String idProcessType;
	private String oggetto;
	private Boolean avviatiDaMe;
	private String inFase;
	private String eseguibileTask;
	private String svoltaFase;
	private String effettuatoTask;
	private String daIniziareFase;
	private String daCompletareFase;
	private String daEffettuareTask;
	private ScadenzaBean scadenza;
	private String note;
	
	public String getIdProcessType() {
		return idProcessType;
	}
	public String getOggetto() {
		return oggetto;
	}
	public Boolean getAvviatiDaMe() {
		return avviatiDaMe;
	}
	public String getInFase() {
		return inFase;
	}
	public String getEseguibileTask() {
		return eseguibileTask;
	}
	public String getSvoltaFase() {
		return svoltaFase;
	}
	public String getEffettuatoTask() {
		return effettuatoTask;
	}
	public String getDaIniziareFase() {
		return daIniziareFase;
	}
	public String getDaCompletareFase() {
		return daCompletareFase;
	}
	public String getDaEffettuareTask() {
		return daEffettuareTask;
	}
	public ScadenzaBean getScadenza() {
		return scadenza;
	}
	public String getNote() {
		return note;
	}
	public void setIdProcessType(String idProcessType) {
		this.idProcessType = idProcessType;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public void setAvviatiDaMe(Boolean avviatiDaMe) {
		this.avviatiDaMe = avviatiDaMe;
	}
	public void setInFase(String inFase) {
		this.inFase = inFase;
	}
	public void setEseguibileTask(String eseguibileTask) {
		this.eseguibileTask = eseguibileTask;
	}
	public void setSvoltaFase(String svoltaFase) {
		this.svoltaFase = svoltaFase;
	}
	public void setEffettuatoTask(String effettuatoTask) {
		this.effettuatoTask = effettuatoTask;
	}
	public void setDaIniziareFase(String daIniziareFase) {
		this.daIniziareFase = daIniziareFase;
	}
	public void setDaCompletareFase(String daCompletareFase) {
		this.daCompletareFase = daCompletareFase;
	}
	public void setDaEffettuareTask(String daEffettuareTask) {
		this.daEffettuareTask = daEffettuareTask;
	}
	public void setScadenza(ScadenzaBean scadenza) {
		this.scadenza = scadenza;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}