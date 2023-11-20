/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.dm.engine.manage.EngineManager;
import it.eng.dm.engine.manage.bean.EseguiBeanIn;

/**
 * classe per la gestione del flusso dei vari procedimenti deployati
 * 
 * @author jravagnan
 * 
 */
public class FlowManager {

	private EngineManager engineMan = null;

	public FlowManager() {
		engineMan = new EngineManager();
	}
	
	/**
	 * esegue un task settando il corretto valore 
	 * delle variabili che ne decidono il risultato del flusso
	 * @param in
	 * @return
	 */
	public boolean eseguiTask(EseguiBeanIn in) {
		String idIstanza = in.getIdIstanzaProcesso();
		String idTask = in.getIdTask();
		Map<String, Object> mappa = in.getVariabili();
		String executionId = engineMan.getExecutionId(idIstanza);
		for (String chiave : mappa.keySet()) {
			engineMan.setVariable(executionId, chiave, (String) mappa.get(chiave));
		}
		return engineMan.completeTask(idTask);
	}

}
