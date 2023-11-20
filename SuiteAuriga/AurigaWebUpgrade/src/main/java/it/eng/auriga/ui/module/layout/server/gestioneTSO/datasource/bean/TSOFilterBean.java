/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author dbe4235
 *
 */

public class TSOFilterBean {
	
	private String inFase;
	private String eseguibileTask;
	private String effettuatoTask;
	private String daEffettuareTask;
	
	public String getInFase() {
		return inFase;
	}
	public String getEseguibileTask() {
		return eseguibileTask;
	}
	public String getEffettuatoTask() {
		return effettuatoTask;
	}
	public String getDaEffettuareTask() {
		return daEffettuareTask;
	}
	public void setInFase(String inFase) {
		this.inFase = inFase;
	}
	public void setEseguibileTask(String eseguibileTask) {
		this.eseguibileTask = eseguibileTask;
	}
	public void setEffettuatoTask(String effettuatoTask) {
		this.effettuatoTask = effettuatoTask;
	}
	public void setDaEffettuareTask(String daEffettuareTask) {
		this.daEffettuareTask = daEffettuareTask;
	}

}