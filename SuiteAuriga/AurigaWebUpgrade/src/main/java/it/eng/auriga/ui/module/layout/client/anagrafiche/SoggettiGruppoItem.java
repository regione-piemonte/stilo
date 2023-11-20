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


public class SoggettiGruppoItem extends ReplicableItem {
	
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		SoggettiGruppoCanvas lSoggettiGruppoCanvas = new SoggettiGruppoCanvas();		
		return lSoggettiGruppoCanvas;
	}
	
	@Override
	protected ImgButton[] createAddButtons() {
	
		ImgButton[] addButtons = new ImgButton[2];
		
		addButtons[0] = new ImgButton();   
		addButtons[0].setSrc("lookup/rubricamulti.png");   
		addButtons[0].setShowDown(false);   
		addButtons[0].setShowRollOver(false);      
		addButtons[0].setSize(16); 
		addButtons[0].setPrompt(I18NUtil.getMessages().gruppisoggetti_detail_multilookupRubricaButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				SoggettiGruppoMultiLookupRubrica lookupRubricaPopup = new SoggettiGruppoMultiLookupRubrica(null);				
				lookupRubricaPopup.show(); 	
			}   
		});
		
		
		addButtons[1] = new ImgButton();   
		addButtons[1].setSrc("lookup/gruppimulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt(I18NUtil.getMessages().gruppisoggetti_detail_multilookupMembriButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				SoggettiGruppoMultiLookupGruppi lookupGruppiPopup = new SoggettiGruppoMultiLookupGruppi(null);				
				lookupGruppiPopup.show(); 	
			}   
		});
		
		
		return addButtons;		
	}
	
	
	// Lookup per chiamare i GRUUPI
	public class SoggettiGruppoMultiLookupGruppi extends LookupGruppiPopup {
		
		private List<SoggettiGruppoCanvas> multiLookupListGruppi = new ArrayList<SoggettiGruppoCanvas>(); 
		
		public SoggettiGruppoMultiLookupGruppi(Record record) {
			super(record, "SEL_GRUPPI_EST", false);			
		}
		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			SoggettiGruppoCanvas lastCanvas = (SoggettiGruppoCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordGruppi(record);
				multiLookupListGruppi.add(lastCanvas);
			} else {
				SoggettiGruppoCanvas canvas = (SoggettiGruppoCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordGruppi(record);
				multiLookupListGruppi.add(canvas);
			}
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(SoggettiGruppoCanvas canvas : multiLookupListGruppi) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idGruppo").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
	}
	
	
	
	// Lookup per chiamare la RUBRICA
	public class SoggettiGruppoMultiLookupRubrica extends LookupSoggettiPopup {

		private List<SoggettiGruppoCanvas> multiLookupListRubrica = new ArrayList<SoggettiGruppoCanvas>(); 
		
		public SoggettiGruppoMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			SoggettiGruppoCanvas lastCanvas = (SoggettiGruppoCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupListRubrica.add(lastCanvas);
			} else {
				SoggettiGruppoCanvas canvas = (SoggettiGruppoCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupListRubrica.add(canvas);
			}
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(SoggettiGruppoCanvas canvas : multiLookupListRubrica) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idSoggetto").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
	
	}
	
}