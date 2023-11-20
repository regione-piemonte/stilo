/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public abstract class SottomenuTaskBackDetail extends SottomenuTaskDetail implements BackDetailInterface {
	
	public SottomenuTaskBackDetail(String nomeEntita, String idProcess, String idTipoProc, String idTipoEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		super(nomeEntita, idProcess, idTipoProc, idTipoEvento, dettaglioPraticaLayout);
	}
	
	@Override
	public void buildSottomenu(final VLayout lVLayout) {
		
		//Chiamata con solo le sottovoci di quella sezione
		
		String idTipoEventoApp = idTipoEvento; // tipoAttivita AC
//		dettaglioPraticaLayout.getListaTaskProc(idProcess, idTipoProc, null, idTipoEventoApp, new GetListaTaskProcCallback() {			
//			@Override
//			public void execute(RecordList data) {
//				
//				buildListaTaskPratica(data, lVLayout);
//			}
//		});	
		
		RecordList listaTaskSottomenu = new RecordList();
		for(String key : dettaglioPraticaLayout.getMappaMenu().keySet()) {
			Record recordVoce = dettaglioPraticaLayout.getMappaMenu().get(key);
			if(recordVoce.getAttribute("tipoAttivita") != null && "AC".equals(recordVoce.getAttribute("tipoAttivita"))) {
				if(recordVoce.getAttribute("idTipoEvento") != null && idTipoEventoApp.equals(recordVoce.getAttribute("idTipoEvento"))) {
					listaTaskSottomenu = dettaglioPraticaLayout.getMappaSottomenuAttributiComplessi().get(key);
					break;
				}	
			}
		}	
		
		buildListaTaskPratica(listaTaskSottomenu, lVLayout);				
	}
	
}
