/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ConfigurazioneFlussiXmlBean {
	
	// 1: Codice identificativo dell'attività (TASK_DEF_KEY_ di Activiti)
	// 2: Display name dell'attività (NAME_ di Activiti)
	// 3: (1/0) Se 1 indica che l'attività è attualmente valida
	// 4: Codice identificativo della fase in cui ricade l'istanza di attività
	// 5: Nome della fase in cui ricade l'istanza di attività
	// 6: N.ro di ordine tra le attività della fase (valorizzato solo se lista di istanze attività)

	@NumeroColonna(numero = "1")
	private String idTask;
	@NumeroColonna(numero = "2")
	private String nomeTask;
	@NumeroColonna(numero = "3")
	private String flgValido;	
	@NumeroColonna(numero = "4")
	private String idFase;
	@NumeroColonna(numero = "5")
	private String nomeFase;
	@NumeroColonna(numero = "6")
	private String numeroOrdine;
	
	public String getIdTask() {
		return idTask;
	}
	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public String getNomeTask() {
		return nomeTask;
	}
	public void setNomeTask(String nomeTask) {
		this.nomeTask = nomeTask;
	}
	public String getFlgValido() {
		return flgValido;
	}
	public void setFlgValido(String flgValido) {
		this.flgValido = flgValido;
	}
	public String getIdFase() {
		return idFase;
	}
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}
	public String getNomeFase() {
		return nomeFase;
	}
	public void setNomeFase(String nomeFase) {
		this.nomeFase = nomeFase;
	}
	public String getNumeroOrdine() {
		return numeroOrdine;
	}
	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}

}
