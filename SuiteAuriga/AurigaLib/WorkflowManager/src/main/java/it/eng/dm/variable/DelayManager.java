/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.dm.engine.manage.EngineManager;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.variables.VariableEngine;

public class DelayManager {

	private Logger log = Logger.getLogger(DelayManager.class);

	public List<String> executeDelayedTasks(String data) throws SiraBusinessException {
		List<String> tasks = new ArrayList<String>();
		try {
			VariableEngine VE = new VariableEngine();
			List<String> idTasks = VE.getTasksDelayed(data);
			EngineManager manager = new EngineManager();
			for (String id : idTasks) {
				boolean ok = manager.completeTask(id);
				if (ok) {
					tasks.add(id);
				} else {
					log.warn("Impossibile completare il task con id: " + id);
				}
			}
			if (tasks.size() != idTasks.size()) {
				log.warn("non tutti i task sono stati completati correttamente");
			}
		} catch (Exception e) {
			log.error("Impossibile completare l'operazione: ", e);
			throw new SiraBusinessException("Impossibile completare l'operazione: ", e);
		}
		return tasks;
	}

}
