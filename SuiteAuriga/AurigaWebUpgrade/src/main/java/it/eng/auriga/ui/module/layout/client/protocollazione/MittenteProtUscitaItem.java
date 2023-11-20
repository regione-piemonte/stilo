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

public class MittenteProtUscitaItem extends MittenteProtItem{

	@Override
	public ReplicableCanvas getCanvasToReply() {
		MittenteProtUscitaCanvas lMittenteProtUscitaCanvas = new MittenteProtUscitaCanvas(this);
		return lMittenteProtUscitaCanvas;
	}
	
	@Override
	public Record getCanvasDefaultRecord() {
		if(getFlgSoloInOrganigramma()) {
			if(isDimOrganigrammaNonStd()) {
				Record lRecord = new Record();	
				lRecord.setAttribute("codRapidoMittente", AurigaLayout.getCodRapidoOrganigramma());
				return lRecord;				
			}
		}
		return null;
	}
	
	@Override
	public boolean getFlgAssegnaAlMittenteDefault() {
		return AurigaLayout.getParametroDBAsBoolean("ASSEGNAZIONE_MITT_DEFAULT_U");
	}
	
	@Override
	public void resetDefaultValueFlgAssegnaAlMittente() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			MittenteProtUscitaCanvas lMittenteProtUscitaCanvas = (MittenteProtUscitaCanvas) getReplicableCanvas(lHLayout);
			lMittenteProtUscitaCanvas.resetDefaultValueFlgAssegnaAlMittente();
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
					MittenteProtUscitaMultiLookupRubrica lookupRubricaPopup = new MittenteProtUscitaMultiLookupRubrica(null);				
					lookupRubricaPopup.show(); 	
				}   
			});
			addButtonsList.add(rubricamultiButton);
			
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
					MittenteProtUscitaMultiLookupOrganigramma lookupOrganigrammaPopup = new MittenteProtUscitaMultiLookupOrganigramma(null);				
					lookupOrganigrammaPopup.show(); 	
				}   
			});
			addButtonsList.add(organigrammamultiButton);
		}
		
		return addButtonsList.toArray(new ImgButton[addButtonsList.size()]);		
	}
	
	public String getFinalitaLookupRubrica(){
		return AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? "SEL_SOGG_INT" : "SEL_UOI";
	}
	
	public class MittenteProtUscitaMultiLookupRubrica extends LookupSoggettiPopup {

		private List<MittenteProtUscitaCanvas> multiLookupList = new ArrayList<MittenteProtUscitaCanvas>(); 
		
		public MittenteProtUscitaMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaLookupRubrica();
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {		
			MittenteProtUscitaCanvas lastCanvas = (MittenteProtUscitaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				MittenteProtUscitaCanvas canvas = (MittenteProtUscitaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}			
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(MittenteProtUscitaCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idMittente").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
		
		@Override
		public String[] getTipiAmmessi() {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? new String[] { "UOI", "UP" } : new String[] { "UOI" };
		}
	
	}	
	
	public String getFinalitaLookupOrganigramma(){
		if(isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
			return isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO";
		} 	
		return "MITT_DEST";
	}
	
	public class MittenteProtUscitaMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<MittenteProtUscitaCanvas> multiLookupList = new ArrayList<MittenteProtUscitaCanvas>(); 
		
		public MittenteProtUscitaMultiLookupOrganigramma(Record record) {
			super(record, false, (AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? null : new Integer(0)));			
		}
		
		@Override
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVMitt");
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			MittenteProtUscitaCanvas lastCanvas = (MittenteProtUscitaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(lastCanvas);
			} else {
				MittenteProtUscitaCanvas canvas = (MittenteProtUscitaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(canvas);
			}								
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(MittenteProtUscitaCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idMittente").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
		
		@Override
		public String getFinalita() {	
			return getFinalitaLookupOrganigramma();
		}
		
		@Override
		public String getIdUd() {
			return getIdUdProtocollo();
		}
	
	}
	
	public boolean isDimOrganigrammaNonStd() {
//		return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD");
		// con finalità MITTENTE_REG la combo si deve comportare come nel caso di dimensione dell'organigramma standard
		return false;
	}
	
	public String getUoProtocollante() {
		return null;
	}
	
	public void manageOnChangedUoProtocollante() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			MittenteProtUscitaCanvas lMittenteProtUscitaCanvas = (MittenteProtUscitaCanvas) getReplicableCanvas(lHLayout);
			lMittenteProtUscitaCanvas.manageOnChangedUoProtocollante();
		}		
	}
	
}
