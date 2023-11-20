/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum TaskDaCompletare {
	COMPILAISTANZA("compila_istanza"), 
	ALLEGADOCUMENTAZIONE("allega_documentazione"),
	PRESENTAISTANZA("presenta_istanza");
	
	private String nomeTask;
	
	private TaskDaCompletare(String nomeTask){
		this.nomeTask = nomeTask;
	}

	public String getNomeTask() {
		return nomeTask;
	}

	public void setNomeTask(String nomeTask) {
		this.nomeTask = nomeTask;
	}
	
	
}
