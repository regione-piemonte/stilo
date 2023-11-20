/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

public class ChiusuraMassivaEmailLayout extends VLayout{

	private ChiusuraMassivaEmailForm form;

	public ChiusuraMassivaEmailLayout(final ChiusuraMassivaEmailWindow windowIn){
		
		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		form = new ChiusuraMassivaEmailForm(windowIn.getNomeEntita());
		form.setMargin(10);
		
		//form.setCellBorder(1);
		 
		Button inviaRichiestaButton = new Button("Invia richiesta");
		inviaRichiestaButton.setIcon("ok.png");
		inviaRichiestaButton.setIconSize(16); 
		inviaRichiestaButton.setAutoFit(false);
		inviaRichiestaButton.setAlign(Alignment.CENTER);
		inviaRichiestaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				inviaRichiesta(windowIn.getNomeEntita());
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(inviaRichiestaButton);		
		
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

	protected void inviaRichiesta(String nomeEntita) {
		if(form.validate()) {
			Record recordForm = new Record(form.getValues());
			
			Record record = new Record();
			record.setAttribute("nroGiorniSenzaOperazioni", recordForm.getAttribute("nroGiorniSenzaOperazioni"));
			record.setAttribute("nroGiorniApertura", recordForm.getAttribute("nroGiorniApertura"));
			record.setAttribute("dataInvioDa", recordForm.getAttributeAsDate("dataInvioDa"));
			record.setAttribute("dataInvioA", recordForm.getAttributeAsDate("dataInvioA"));
			record.setAttribute("uoAssegnazione", recordForm.getAttribute("uoAssegnazione"));
			record.setAttribute("caselle", recordForm.getAttribute("caselle"));
			
			Layout.showWaitPopup("Richiesta in corso: potrebbe richiedere qualche secondo. Attendere…");
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ChiusuraMassivaEmailDatasource");		

			lGwtRestDataSource.addData(record, new DSCallback() {
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record data = response.getData()[0];	
						
						String idJob = data.getAttributeAsString("idJob");
						
						if(idJob != null) 
							Layout.addMessage(new MessageBean("Richiesta acquisita. Assegnato alla richiesta il N° " + idJob, "", MessageType.INFO));
						else
							Layout.addMessage(new MessageBean("Richiesta fallita.", "", MessageType.INFO));
					}
					
				}
			});	
		}
	}

	public ChiusuraMassivaEmailForm getForm(){
		return form;
	}
	
}
