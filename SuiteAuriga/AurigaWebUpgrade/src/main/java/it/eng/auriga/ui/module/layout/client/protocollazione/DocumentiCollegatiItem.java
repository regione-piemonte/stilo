/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class DocumentiCollegatiItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DocumentoCollegatoCanvas lDocumentoCollegatoCanvas = new DocumentoCollegatoCanvas(this);
		return lDocumentoCollegatoCanvas;
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
		addButtons[1].setSrc("lookup/archiviomulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupArchivioButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				DocumentoCollegatoMultiLookupArchivio lookupArchivioPopup = new DocumentoCollegatoMultiLookupArchivio(null);				
				lookupArchivioPopup.show();
			}   
		});
		
		return addButtons;		
	}
	
	public boolean isProtInModalitaWizard() {
		return false;
	}
	
	public class DocumentoCollegatoMultiLookupArchivio extends LookupArchivioPopup {

			private List<DocumentoCollegatoCanvas> multiLookupList = new ArrayList<DocumentoCollegatoCanvas>(); 
			
			public DocumentoCollegatoMultiLookupArchivio(Record record) {
				super(record, null, false);			
			}
			
			@Override
			public String getWindowTitle() {
				if(isIstanzeInElencoPopup()) {
					return "Seleziona elenco istanze";
				} else {
					return "Seleziona documenti da collegare";
				}
			}
			
			@Override
			public String getFinalita() {	
				if(isIstanzeInElencoPopup()) {
					return "IMPORT_ISTANZE_CONC_IN_ELENCO_PUBBL";
				} else {
					return "COLLEGA_UD";
				}
			}

			@Override
			public void manageLookupBack(Record record) {
									
			}
			
			@Override
			public void manageMultiLookupBack(Record record) {
				DocumentoCollegatoCanvas lastCanvas = (DocumentoCollegatoCanvas) getLastCanvas();
				if(lastCanvas != null && !lastCanvas.isChanged()) {
					lastCanvas.setFormValuesFromRecordArchivio(record);
					multiLookupList.add(lastCanvas);
				} else {
					DocumentoCollegatoCanvas canvas = (DocumentoCollegatoCanvas) onClickNewButton();
					canvas.setFormValuesFromRecordArchivio(record);
					multiLookupList.add(canvas);
				}	
			}	
			
			@Override
			public void manageMultiLookupUndo(Record record) {	
				for(DocumentoCollegatoCanvas canvas : multiLookupList) {
					Record values = canvas.getFormValuesAsRecord();
					if(values.getAttribute("idUdCollegata").equals(record.getAttribute("id"))) {
						setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
					}
				}
			}	
			
		}	
	
		@Override
		public void setCanEdit(Boolean canEdit) {

			setEditing(canEdit);
			if(getCanvas() != null) {
				final VLayout lVLayout = (VLayout) getCanvas(); 		
				for (int i=0;i<lVLayout.getMembers().length; i++){
					Canvas lVLayoutMember = lVLayout.getMember(i);
					if(lVLayoutMember instanceof HLayout) {				
						RemoveButton lRemoveButton = null;
						for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
							if(lHLayoutMember instanceof ImgButton) {
								if(i == (lVLayout.getMembers().length - 1)) {
									if(canEdit) {
										lHLayoutMember.show();
									} else {
										lHLayoutMember.hide();
									}									
								} else {
									lRemoveButton = ((RemoveButton)lHLayoutMember);	
									if(canEdit) {
										lRemoveButton.show();
									} else {
										lRemoveButton.hide();
									}	
								}																			
							} else if(lHLayoutMember instanceof ReplicableCanvas) {
								DocumentoCollegatoCanvas lDocumentoCollegatoCanvas = (DocumentoCollegatoCanvas)lHLayoutMember;
								if(lDocumentoCollegatoCanvas.isLocked()) {
									lDocumentoCollegatoCanvas.setCanEdit(false);
									lRemoveButton.setAlwaysDisabled(true);									
								} else {
									lDocumentoCollegatoCanvas.setCanEdit(canEdit);
								}								
							}							
						}	
					}			
				}
			}
		}
		
		public boolean isIstanzeInElencoPopup() {
			return false;
		}

}