/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.gestioneatti.annulla_atti_in_iter_anno_prec;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class AnnullaAttiInIterAnnoPrecWindow extends ModalWindow {

	private CustomDetail detail;
	
	private ToolStrip detailToolStrip;
	private DetailToolStripButton annullaProposteButton;
	private DetailToolStripButton chiudiButton;

	public AnnullaAttiInIterAnnoPrecWindow() {
		
		super("annulla_atti_in_iter_anno_prec", false);
		
		setTitle(I18NUtil.getMessages().annullaAttiInIterAnnoPrec_window_title());

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		detail = new AnnullaAttiInIterAnnoPrecDetail("annulla_atti_in_iter_anno_prec");
		((AnnullaAttiInIterAnnoPrecDetail) detail).setWindow(this);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiInIterAnnoPrecDatasource", true, "idAtto", FieldType.TEXT);
		Record lRecordToLoad = new Record();
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];
					detail.editRecord(lRecord);
					detail.getValuesManager().clearErrors(true);
				}
			}
		});
		
		annullaProposteButton = new DetailToolStripButton("Annulla proposte", "ok.png");
		annullaProposteButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) { 
				onAnnullaProposteButtonClick();				
			}   
		}); 
		
		chiudiButton = new DetailToolStripButton("Chiudi","annulla.png"); 
		chiudiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Chiudo la finestra
				markForDestroy();
			}
		});
		
		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setAlign(Alignment.CENTER);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addButton(annullaProposteButton);
		detailToolStrip.addButton(chiudiButton);
		
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail , detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);			
		
		setWidth(660);
		setHeight(480);
		
		setIcon("menu/annulla_atti_in_iter_anno_prec.png");		
	}

	public void onAnnullaProposteButtonClick() {
		final Record record = detail.getRecordToSave();
		if(detail.validate()) {		
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiInIterAnnoPrecDatasource"); 			  
			lGwtRestDataSource.updateData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Layout.hideWaitPopup();
						Layout.addMessage(new MessageBean("Annullamento proposte effettuato con successo", "", MessageType.INFO));
						markForRedraw();
					}
					else{
						Layout.hideWaitPopup();
					}	
					try {	
						reload(response.getData()[0], new DSCallback() {		
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Layout.hideWaitPopup();
								markForRedraw();
							}
						});		
					} catch(Exception e) {
						Layout.hideWaitPopup();
					}	
				}
			});
		}
	}
	
	public void reload(Record record, final DSCallback callback) {
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiInIterAnnoPrecDatasource"); 
		lGwtRestDataSource.getData(record, new DSCallback() {	
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					detail.editRecord(record);	
					detail.getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		});	
	}
	
	public void disabilitaAnnullaProposteButton(boolean abil){
		annullaProposteButton.setDisabled(abil);
	}
}
