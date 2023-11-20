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

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class PresenzaArgomentoWindow extends ModalWindow {
	
	protected PresenzaArgomentoWindow window;
	protected PresenzaArgomentoDetail detail;	
	protected ToolStrip detailToolStrip;

	public PresenzaArgomentoWindow(String nomeEntita, String title, final Record record, String tipologiaSessione) {
		super(nomeEntita, false);
		
		setTitle(title);
		
		window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(500);
		setWidth(900);
		setOverflow(Overflow.AUTO);    			
		
		detail = new PresenzaArgomentoDetail(nomeEntita, tipologiaSessione, record.getAttributeAsString("idSeduta"));	
		detail.editRecord(record);
		detail.setHeight100();
		detail.setWidth100();
		
		final DetailToolStripButton saveButton = new DetailToolStripButton("Salva", "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				saveButton.focusAfterGroup();
				if(detail.validate()) {
					saveData(detail.getRecordToSave());		
					window.manageOnCloseClick();
				}
			}
		});
		
		DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		annullaButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {				
				window.manageOnCloseClick();
			}
		});
		
		ToolStrip detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right		
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(annullaButton);
		
		VLayout detailLayout = new VLayout();  
		detailLayout.setOverflow(Overflow.HIDDEN);		
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		detailLayout.setMembers(detail, detailToolStrip);	
		
		setBody(detailLayout);
		
		detail.setCanEdit(true);
		
		setIcon("delibere/presenze.png");	
	}
	
	public void saveData(Record record) {
		
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		record.setAttribute("flgPresenzeVotiIn", "P");
		lGWTRestDataSource.executecustom("savePresenzeVotiSuContOdG", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
					Record recordArgomenti = dsResponse.getData()[0];
					caricaDettaglio(recordArgomenti);
				}
			}
		});
	}
	
	public void caricaDettaglio(Record record) {
		if(detail != null) {
			detail.editRecord(record);
		}
	}

}