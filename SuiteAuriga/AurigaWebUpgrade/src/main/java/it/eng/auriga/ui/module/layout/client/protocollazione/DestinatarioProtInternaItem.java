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

public class DestinatarioProtInternaItem extends DestinatarioProtItem{	

	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestinatarioProtInternaCanvas lDestinatarioProtInternaCanvas = new DestinatarioProtInternaCanvas(this);
		return lDestinatarioProtInternaCanvas;
	}
	
	public boolean isAbilitatiDestEsterniInRegInt() {
		return false;
	}
	
	@Override
	public void resetDefaultValueFlgAssegnaAlDestinatario() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			DestinatarioProtInternaCanvas lDestinatarioProtInternaCanvas = (DestinatarioProtInternaCanvas) getReplicableCanvas(lHLayout);
			lDestinatarioProtInternaCanvas.resetDefaultValueFlgAssegnaAlDestinatario();
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		reloadTipoValueMap();
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
					DestinatarioProtInternaMultiLookupRubrica lookupRubricaPopup = new DestinatarioProtInternaMultiLookupRubrica(null);				
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
				DestinatarioProtInternaMultiLookupOrganigramma lookupOrganigrammaPopup = new DestinatarioProtInternaMultiLookupOrganigramma(null);				
				lookupOrganigrammaPopup.show(); 	
			}   
		});
		addButtonsList.add(organigrammamultiButton);
		
		return addButtonsList.toArray(new ImgButton[addButtonsList.size()]);		
	}
	
	public class DestinatarioProtInternaMultiLookupRubrica extends LookupSoggettiPopup {

		private List<DestinatarioProtInternaCanvas> multiLookupList = new ArrayList<DestinatarioProtInternaCanvas>(); 
		
		public DestinatarioProtInternaMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}

		@Override
		public String getFinalita() {		
			if (isAbilitatiDestEsterniInRegInt()) {
				return null;
			} 
			return "SEL_SOGG_INT";
		}
		
		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {	
			DestinatarioProtInternaCanvas lastCanvas = (DestinatarioProtInternaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				DestinatarioProtInternaCanvas canvas = (DestinatarioProtInternaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}			
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(DestinatarioProtInternaCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idDestinatario").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
		
		@Override
		public String[] getTipiAmmessi() {
			if (isAbilitatiDestEsterniInRegInt()) {
				return null;
			} else {
				return new String[] { "UOI", "UP" };
			}
		}
	
	}
	
	public class DestinatarioProtInternaMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<DestinatarioProtInternaCanvas> multiLookupList = new ArrayList<DestinatarioProtInternaCanvas>(); 
		
		public DestinatarioProtInternaMultiLookupOrganigramma(Record record) {
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
			DestinatarioProtInternaCanvas lastCanvas = (DestinatarioProtInternaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(lastCanvas);
			} else {
				DestinatarioProtInternaCanvas canvas = (DestinatarioProtInternaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(canvas);
			}							
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(DestinatarioProtInternaCanvas canvas : multiLookupList) {
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
	
	public void reloadTipoValueMap() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			DestinatarioProtInternaCanvas lDestinatarioProtInternaCanvas = (DestinatarioProtInternaCanvas) getReplicableCanvas(lHLayout);
			lDestinatarioProtInternaCanvas.reloadTipoValueMap();
		}
	}

}
