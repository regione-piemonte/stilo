/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.pratiche.DettaglioPraticaLayout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

/**
 * 
 * @author Antonio Magnocavallo
 *
 */

public class SottomenuTaskDetail extends CustomDetail {
	
	protected String idProcess;
	protected String idTipoProc;
	
	protected String idTipoEvento;
	
	protected DettaglioPraticaLayout dettaglioPraticaLayout;
	
	public SottomenuTaskDetail(String nomeEntita, String idProcess, String idTipoProc, String idTipoEvento, DettaglioPraticaLayout dettaglioPraticaLayout) {
		
		super(nomeEntita);
	
		this.idProcess = idProcess;
		this.idTipoProc = idTipoProc;
		this.idTipoEvento = idTipoEvento;
		this.dettaglioPraticaLayout = dettaglioPraticaLayout;
				
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();
		
		VLayout lVLayoutTask = new VLayout();
		lVLayoutTask.setMembersMargin(10);
		
		buildSottomenu(lVLayoutTask);
		
		lVLayout.addMember(lVLayoutTask);
				
		VLayout spacer = new VLayout();
		spacer.setHeight100();
		spacer.setWidth100();						
		lVLayout.addMember(spacer);		
		
		addMember(lVLayout);			
	}
	
	protected void buildListaTaskPratica(RecordList listaTask, VLayout lVLayout) {
		
		if(listaTask != null) {
			for(int i = 0; i < listaTask.getLength(); i++) {
				final Record recordTask = listaTask.get(i);
					
				final String nome = recordTask.getAttribute("nome");
				String displayName = dettaglioPraticaLayout.getDisplayNameEvento(nome);
				
				dettaglioPraticaLayout.getMappaMenu().put(nome, recordTask);			
				
				final String dettagli = recordTask.getAttribute("dettagli");
				final String flgFatta = recordTask.getAttribute("flgFatta");
				final String flgEsito = recordTask.getAttribute("flgEsito");
				final String flgDatiVisibili = recordTask.getAttribute("flgDatiVisibili");			
				final String flgToDo = recordTask.getAttribute("flgToDo");
				final String icona = recordTask.getAttribute("icona");
				
				boolean isEseguibile = true;
				if(recordTask != null && recordTask.getAttribute("flgEseguibile") != null) {
					isEseguibile = "1".equals(recordTask.getAttribute("flgEseguibile"));	
				}	
	      
				Label taskLabel = new TaskLabel(displayName, dettagli, (isEseguibile ? null : recordTask.getAttribute("motiviNonEseg")), icona, flgFatta, flgEsito, flgDatiVisibili, flgToDo, dettaglioPraticaLayout);
					
				lVLayout.addMember(taskLabel);
								
				if(flgDatiVisibili != null && "1".equals(flgDatiVisibili)) {
					taskLabel.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							dettaglioPraticaLayout.onClickVoceMenu(recordTask);
						}
					});			
				}
			}
		}
	}
	
	public void buildSottomenu(final VLayout lVLayout) {
		
		//Chiamata con solo le sottovoci di quella sezione
		String codGruppoAtt = idTipoEvento; // tipoAttivita G
//		dettaglioPraticaLayout.getListaTaskProc(idProcess, idTipoProc, codGruppoAtt, null, new GetListaTaskProcCallback() {			
//			@Override
//			public void execute(RecordList data) {
//				
//				buildListaTaskPratica(data, lVLayout);
//			}
//		});	
		
		RecordList listaTaskSottomenu = new RecordList();
		for(String key : dettaglioPraticaLayout.getMappaMenu().keySet()) {
			Record recordVoce = dettaglioPraticaLayout.getMappaMenu().get(key);
			if(recordVoce.getAttribute("tipoAttivita") != null && "G".equals(recordVoce.getAttribute("tipoAttivita"))) {
				if(recordVoce.getAttribute("idTipoEvento") != null && codGruppoAtt.equals(recordVoce.getAttribute("idTipoEvento"))) {
					listaTaskSottomenu = dettaglioPraticaLayout.getMappaSottomenuGruppi().get(key);
					break;
				}	
			}
		}	
		
		buildListaTaskPratica(listaTaskSottomenu, lVLayout);						
	}

}
