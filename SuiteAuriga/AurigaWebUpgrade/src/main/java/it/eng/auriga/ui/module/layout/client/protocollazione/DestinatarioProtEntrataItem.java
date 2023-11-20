/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class DestinatarioProtEntrataItem extends DestinatarioProtItem{

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestinatarioProtEntrataCanvas lDestinatarioProtEntrataCanvas = new DestinatarioProtEntrataCanvas(this);
		return lDestinatarioProtEntrataCanvas;
	}
	
	public boolean isDimOrganigrammaNonStd() {
		return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST_E/ES");		
	}
	
	@Override
	public void resetDefaultValueFlgAssegnaAlDestinatario() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			DestinatarioProtEntrataCanvas lDestinatarioProtEntrataCanvas = (DestinatarioProtEntrataCanvas) getReplicableCanvas(lHLayout);
			lDestinatarioProtEntrataCanvas.resetDefaultValueFlgAssegnaAlDestinatario();
		}
	}
	
	@Override
	protected ImgButton[] createAddButtons() {
		
		List<ImgButton> addButtonsList = new ArrayList<ImgButton>();
		
		ImgButton addButton = new ImgButton();   
		addButton.setSrc("[SKIN]actions/add.png");   
		addButton.setShowDown(false);   
		addButton.setShowRollOver(false);      
		addButton.setSize(16); 
		addButton.setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();   	
			}   
		});
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addButton.setCanFocus(true);		
		}
		addButtonsList.add(addButton);

		if(!getFlgSoloInOrganigramma()) {
			ImgButton rubricamultiButton = new ImgButton();   
			rubricamultiButton.setSrc("lookup/rubricamulti.png");   
			rubricamultiButton.setShowDown(false);   
			rubricamultiButton.setShowRollOver(false);      
			rubricamultiButton.setSize(16); 
			rubricamultiButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupRubricaButton_prompt());
			rubricamultiButton.addClickHandler(new ClickHandler() {	
				@Override
				public void onClick(ClickEvent event) {
					cercaInRubricaAfterChanged = false;
					DestinatarioProtEntrataMultiLookupRubrica lookupRubricaPopup = new DestinatarioProtEntrataMultiLookupRubrica(null);				
					lookupRubricaPopup.show(); 	
				}   
			});
			addButtonsList.add(rubricamultiButton);			
		}
		
		ImgButton organigrammamultiButton = new ImgButton();   
		organigrammamultiButton.setSrc("lookup/organigrammamulti.png");   
		organigrammamultiButton.setShowDown(false);   
		organigrammamultiButton.setShowRollOver(false);      
		organigrammamultiButton.setSize(16); 
		organigrammamultiButton.setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupOrganigrammaButton_prompt());
		organigrammamultiButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				cercaInRubricaAfterChanged = false;
				DestinatarioProtEntrataMultiLookupOrganigramma lookupOrganigrammaPopup = new DestinatarioProtEntrataMultiLookupOrganigramma(null);				
				lookupOrganigrammaPopup.show(); 	
			}   
		});
		addButtonsList.add(organigrammamultiButton);
		
		return addButtonsList.toArray(new ImgButton[addButtonsList.size()]);		
	}
	
//	public void manageAssegnaAlDestinatario(Record record) {
//		
//	}
	
	public class DestinatarioProtEntrataMultiLookupRubrica extends LookupSoggettiPopup {

		private List<DestinatarioProtEntrataCanvas> multiLookupList = new ArrayList<DestinatarioProtEntrataCanvas>(); 
		
		public DestinatarioProtEntrataMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}
		
		@Override
		public String getFinalita() {	
			return "SEL_SOGG_INT";
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {	
			DestinatarioProtEntrataCanvas lastCanvas = (DestinatarioProtEntrataCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				DestinatarioProtEntrataCanvas canvas = (DestinatarioProtEntrataCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}					
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(DestinatarioProtEntrataCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idDestinatario").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
		
		@Override
		public String[] getTipiAmmessi() {
			return new String[] {"UOI", "UP"};	
		}
	
	}
	
	public class DestinatarioProtEntrataMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<DestinatarioProtEntrataCanvas> multiLookupList = new ArrayList<DestinatarioProtEntrataCanvas>(); 
		
		public DestinatarioProtEntrataMultiLookupOrganigramma(Record record) {
			super(record, false);			
		}
		
		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVDest");
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {	
			DestinatarioProtEntrataCanvas lastCanvas = (DestinatarioProtEntrataCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(lastCanvas);
			} else {
				DestinatarioProtEntrataCanvas canvas = (DestinatarioProtEntrataCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(canvas);
			}				
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(DestinatarioProtEntrataCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idDestinatario").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
		
		@Override
		public String getFinalita() {		
			if(isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
				return isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO";
			} 	
			return "MITT_DEST";			
		}

		@Override
		public String getIdUd() {
			return getIdUdProtocollo();
		}
		
	}
	
}
