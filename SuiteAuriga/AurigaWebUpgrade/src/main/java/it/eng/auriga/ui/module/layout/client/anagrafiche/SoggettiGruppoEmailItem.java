/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;


public class SoggettiGruppoEmailItem extends ReplicableItem {
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		SoggettiGruppoEmailCanvas lSoggettiGruppoEmailCanvas = new SoggettiGruppoEmailCanvas();		
		return lSoggettiGruppoEmailCanvas;
	}
	
	@Override
	protected ImgButton[] createAddButtons() {
		
		ImgButton[] addButtons = new ImgButton[1];
		
		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("lookup/rubricaemailmulti.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().invioudmail_detail_multilookupRubricaEmailItem_title());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				SoggettiGruppoEmailMultiLookupRubrica lookupRubricaPopup = new SoggettiGruppoEmailMultiLookupRubrica();				
				lookupRubricaPopup.show(); 	
			}   
		});
		
		return addButtons;		
	}
	
	// Lookup per chiamare la RUBRICA EMAIL
	public class SoggettiGruppoEmailMultiLookupRubrica extends LookupRubricaEmailPopup {

		private List<SoggettiGruppoEmailCanvas> multiLookupList = new ArrayList<SoggettiGruppoEmailCanvas>(); 
		
		public SoggettiGruppoEmailMultiLookupRubrica() {
			super(false);			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			SoggettiGruppoEmailCanvas lastCanvas = (SoggettiGruppoEmailCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordRubricaEmail(record);
				multiLookupList.add(lastCanvas);
			} else {
				SoggettiGruppoEmailCanvas canvas = (SoggettiGruppoEmailCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubricaEmail(record);
				multiLookupList.add(canvas);
			}
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(SoggettiGruppoEmailCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idVoceRubrica").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
	
	}
	
		

}
