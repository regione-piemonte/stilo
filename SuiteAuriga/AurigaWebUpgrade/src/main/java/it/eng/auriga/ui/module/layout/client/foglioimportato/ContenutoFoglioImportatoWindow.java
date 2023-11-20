/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ContenutoFoglioImportatoWindow extends ModalWindow {

	private ContenutoFoglioImportatoWindow _window;

	/**
	 * Layout & Detail della maschera da cui provengo
	 */
	private CustomLayout _layoutContenutoFoglioImportato;
	private CustomDetail _detailContenutoFoglioImportato;

	/**
	 * Detail della maschera corrente
	 */
	private CustomDetail detail;
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton annullaButton;

	public ContenutoFoglioImportatoWindow(Record detailRecord) {

		super("dettaglioContenutoFoglioImportato", true);

		setTitle("Riga csv relativa al PG capofila");

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		detail = new ContenutoFoglioImportatoDetail("contenuto_foglio_importato", detailRecord);
		((ContenutoFoglioImportatoDetail) detail).setWindow(this);
		detail.editRecord(detailRecord);
		
		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick();				
			}   
		}); 
		
		annullaButton = new DetailToolStripButton("Annulla","annulla.png"); 
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Chiudo la finestra
				markForDestroy();
			}
		});
		
		
		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(annullaButton);
		
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		setOverflow(Overflow.AUTO);    			
		
		detailLayout.setMembers(detail , detailToolStrip);		
		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		setBody(detailLayout);			

		setIcon("menu/contenuto_foglio_importato.png");
	}
	
	
	public CustomLayout get_layoutContenutoFoglioImportato() {
		return _layoutContenutoFoglioImportato;
	}

	public void set_layoutContenutoFoglioImportato(CustomLayout _layoutContenutoFoglioImportato) {
		this._layoutContenutoFoglioImportato = _layoutContenutoFoglioImportato;
	}
	
	public CustomDetail get_detailContenutoFoglioImportato() {
		return _detailContenutoFoglioImportato;
	}

	public void set_detailContenutoFoglioImportato(CustomDetail _detailContenutoFoglioImportato) {
		this._detailContenutoFoglioImportato = _detailContenutoFoglioImportato;
	}
	
	public void onSaveButtonClick() {
		final Record record = detail.getRecordToSave();
		if(detail.validate()) {		
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ContenutoFoglioImportatoDataSource"); 			  
			lGwtRestDataSource.updateData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						
						try {	
							reload(response.getData()[0], new DSCallback() {		
								
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Layout.hideWaitPopup();
									Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
									markForRedraw();
								}
							});		
						} catch(Exception e) {
							Layout.hideWaitPopup();
						}	
					} else {
						Layout.hideWaitPopup();
					}
				}
			});
		}
	}
	
	public void reload(Record record, final DSCallback callback) {

		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ContenutoFoglioImportatoDataSource"); 
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

}