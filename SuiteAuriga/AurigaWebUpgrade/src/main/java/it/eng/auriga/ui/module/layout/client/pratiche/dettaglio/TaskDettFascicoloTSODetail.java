/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;

public class TaskDettFascicoloTSODetail extends TaskDettFascicoloGenCompletoDetail implements TaskFlussoInterface {
	
	protected TaskDettFascicoloTSODetail instance;
	
		
	public TaskDettFascicoloTSODetail(String nomeEntita, String idProcess, String nomeFlussoWF, String processNameWF, String idFolder, Record lRecordEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita, idProcess, nomeFlussoWF, processNameWF, idFolder, lRecordEvento, dettaglioPraticaLayout);
		
		instance = this;
		
	}
	
}