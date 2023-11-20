/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class StampaRegProtLayout extends VLayout{

	private StampaRegProtForm form;

	public StampaRegProtLayout(final StampaRegProtWindow stampaRegistriProtocolloWindow){
		
		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		form = new StampaRegProtForm(stampaRegistriProtocolloWindow.getNomeEntita());
		form.setMargin(10);
		
		//form.setCellBorder(1);
		 
		Button creaRegButton = new Button("Crea registro");
		creaRegButton.setIcon("ok.png");
		creaRegButton.setIconSize(16); 
		creaRegButton.setAutoFit(false);
		creaRegButton.setAlign(Alignment.CENTER);
		creaRegButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				creaRegistro(stampaRegistriProtocolloWindow.getNomeEntita());
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(creaRegButton);		
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(form);
		
		addMember(layout);		
		addMember(_buttons);	
	}

	protected void creaRegistro(String nomeEntita) {
		if(form.validate()) {
			Record record = new Record(form.getValues());
			Layout.showWaitPopup("Stampa registro in corso: potrebbe richiedere qualche secondo. Attendere…");
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("StampaRegProtDatasource");		

			// E' la stampa del registro dei PROTOCOLLI
			if(nomeEntita.equalsIgnoreCase("stampa_reg_prot")){
				
				// protocolli generali
				if(record.getAttributeAsString("siglaRegistro").equals("PG")) {
					record.setAttribute("codCategoriaRegistro", "PG");		
					record.setAttribute("siglaRegistro", "");
				// protocolli interni
				} else if(record.getAttributeAsString("siglaRegistro").equals("PI")) {
					record.setAttribute("codCategoriaRegistro", "I");		
					record.setAttribute("siglaRegistro", "P.I.");		
				}						
			}
			// E' la stampa del registro dei REPERTORI
			else if(nomeEntita.equalsIgnoreCase("stampa_reg_repertorio")){
				record.setAttribute("codCategoriaRegistro", "R");
			}
			// E' la stampa del registro delle PUBBLICAZIONI
			else if(nomeEntita.equalsIgnoreCase("stampa_reg_pubblicazioni")){
				record.setAttribute("codCategoriaRegistro", "PUBBL");
				record.setAttribute("siglaRegistro", "");
			}
			// E' la stampa del registro delle PROPOSTE ATTI
			else{
				record.setAttribute("codCategoriaRegistro", "I");
			}
						
			lGwtRestDataSource.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record data = response.getData()[0];	
						
						String idJob = data.getAttributeAsString("idJob");
						String uri = data.getAttributeAsString("uri");

						String nomeFileDaStampare = "";
						
						if(data.getAttribute("codCategoriaRegistro").equals("R")){
							nomeFileDaStampare = "RegistroRepertorio.pdf";
						}
						else if(data.getAttribute("codCategoriaRegistro").equals("I")){
							nomeFileDaStampare = "RegistroProposteAtto.pdf";
						}
						else if(data.getAttribute("codCategoriaRegistro").equals("PUBBL")){
							nomeFileDaStampare = "RegistroPubblicazioni.pdf";
						}
						else
							nomeFileDaStampare = "RegistroProtocollo.pdf";
						
						if(idJob != null) 
							Layout.addMessage(new MessageBean("Richiesta di stampa registro acquisita. Assegnato alla richiesta il N° " + idJob, "", MessageType.INFO));
						
						else if (uri == null || uri.equals("")) {
							if(data.getAttribute("codCategoriaRegistro").equals("PUBBL")) {
								Layout.addMessage(new MessageBean("Non esistono pubblicazioni nell'intervallo selezionato","", MessageType.INFO));
							} else {
								Layout.addMessage(new MessageBean("Non esistono registrazioni nell'intervallo selezionato","", MessageType.INFO));
							}
						}
						else
							Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + nomeFileDaStampare + "&url=" + URL.encode(uri));

					}
					
				}
			});	
		}
	}

	public StampaRegProtForm getForm(){
		return form;
	}
	
}
