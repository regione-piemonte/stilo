/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class InvioUDDestinatariItem extends ReplicableItem{

	private boolean aggiunta = false;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		return new InvioUDDestinatariCanvas();
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		setAggiunta(false);
		super.setCanEdit(canEdit);
	}
	
	public void setAggiuntaMode() {
		setAggiunta(true);
		if(getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas(); 		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);									
				if(lVLayoutMember instanceof HLayout) {
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof ImgButton) {
							if(getShowNewButton() && (i == (lVLayout.getMembers().length - 1))) {
								// se è un bottone di add lo mostro
								lHLayoutMember.show();																																
							} else if(lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton)lHLayoutMember).setAlwaysDisabled(true);																	
							} 
						} else if(lHLayoutMember instanceof InvioUDDestinatariCanvas) {
							((InvioUDDestinatariCanvas)lHLayoutMember).intestazioneItem.setCanEdit(false);
						}
					}	
				}			
			}		
		}
	}
	
	@Override
	public void updateMode() {
		if(isAggiunta()) {
			setAggiuntaMode();
		} 		
	}

	@Override
	protected ImgButton[] createAddButtons() {
		ImgButton[] addButtons = new ImgButton[2];
		
		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("[SKIN]actions/add.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();   	
			}   
		});
		
		addButtons[1] = new ImgButton();   
		addButtons[1].setSrc("lookup/rubricaemailmulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt(I18NUtil.getMessages().invioudmail_detail_multilookupRubricaEmailItem_title());
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				InvioUDDestinatariMultiLookupRubricaEmailPopup multilookupRubricaEmailPopup = new InvioUDDestinatariMultiLookupRubricaEmailPopup();				
				multilookupRubricaEmailPopup.show(); 	
			}   
		});
		
		
		
		return addButtons;		
	}
	
	public void setAggiunta(boolean aggiunta) {
		this.aggiunta = aggiunta;
	}

	public boolean isAggiunta() {
		return aggiunta;
	}
	
	public class InvioUDDestinatariMultiLookupRubricaEmailPopup extends LookupRubricaEmailPopup {

		private List<InvioUDDestinatariCanvas> multiLookupList = new ArrayList<InvioUDDestinatariCanvas>(); 
		
		public InvioUDDestinatariMultiLookupRubricaEmailPopup() {
			super(false);			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			final String idMailingList = record.getAttribute("idVoceRubrica");
			if("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if(listaMembri != null && listaMembri.getLength() > 0) {
								for(int i = 0; i < listaMembri.getLength(); i++) {
									manageMultiLookupBackSingolo(idMailingList, listaMembri.get(i));	
								}
							}
						} 				
					}
				}, new DSRequest());					
			} else manageMultiLookupBackSingolo(record);
		}
		
		private void manageMultiLookupBackSingolo(Record record) {
			manageMultiLookupBackSingolo(null, record);
		}
		
		private void manageMultiLookupBackSingolo(String idMailingList, Record record) {
			InvioUDDestinatariCanvas lastCanvas = (InvioUDDestinatariCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);		
				lastCanvas.setIdMailingList(idMailingList);
				multiLookupList.add(lastCanvas);
			} else {
				InvioUDDestinatariCanvas canvas = (InvioUDDestinatariCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				canvas.setIdMailingList(idMailingList);
				multiLookupList.add(canvas);
			}		
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(InvioUDDestinatariCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if((canvas.getIdMailingList() != null && canvas.getIdMailingList().equals(record.getAttribute("id"))) ||
					values.getAttribute("idVoceRubrica").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
	
	}
}
