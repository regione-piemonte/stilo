/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.util.Map;

public class EseguiBeanIn extends AbstractBean{
	
	private static final long serialVersionUID = -2102616915562007794L;

	private String idIstanzaProcesso;

	private String idTask;

	private Map<String, Object> variabili;

	public String getIdIstanzaProcesso() {
		return idIstanzaProcesso;
	}

	public void setIdIstanzaProcesso(String idIstanzaProcesso) {
		this.idIstanzaProcesso = idIstanzaProcesso;
	}

	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}

	public Map<String, Object> getVariabili() {
		return variabili;
	}

	public void setVariabili(Map<String, Object> variabili) {
		this.variabili = variabili;
	}

}
