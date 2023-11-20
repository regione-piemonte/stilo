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

public class MittenteProtInternaItem extends MittenteProtItem{

	@Override
	public ReplicableCanvas getCanvasToReply() {
		MittenteProtInternaCanvas lMittenteProtInternaCanvas = new MittenteProtInternaCanvas(this);
		return lMittenteProtInternaCanvas;
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
		return AurigaLayout.getParametroDBAsBoolean("ASSEGNAZIONE_MITT_DEFAULT_I");
	}
	
	@Override
	public void resetDefaultValueFlgAssegnaAlMittente() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			MittenteProtInternaCanvas lMittenteProtInternaCanvas = (MittenteProtInternaCanvas) getReplicableCanvas(lHLayout);
			lMittenteProtInternaCanvas.resetDefaultValueFlgAssegnaAlMittente();
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

		if((isInBozza() && getFlgSoloInOrganigramma()) || !getFlgSoloInOrganigramma()) {
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
					MittenteProtInternaMultiLookupRubrica lookupRubricaPopup = new MittenteProtInternaMultiLookupRubrica(null);				
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
					MittenteProtInternaMultiLookupOrganigramma lookupOrganigrammaPopup = new MittenteProtInternaMultiLookupOrganigramma(null);				
					lookupOrganigrammaPopup.show(); 	
				}   
			});
			addButtonsList.add(organigrammamultiButton);
		}
		
		return addButtonsList.toArray(new ImgButton[addButtonsList.size()]);		
	}
	
	public String getFinalitaLookupRubrica(){
		if(getAllowOnlyUnitaDiPersonale()) {
			return "SEL_UP";
		}
		return "SEL_SOGG_INT";
	}
	
	public class MittenteProtInternaMultiLookupRubrica extends LookupSoggettiPopup {

		private List<MittenteProtInternaCanvas> multiLookupList = new ArrayList<MittenteProtInternaCanvas>(); 
		
		public MittenteProtInternaMultiLookupRubrica(Record record) {
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
			MittenteProtInternaCanvas lastCanvas = (MittenteProtInternaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				MittenteProtInternaCanvas canvas = (MittenteProtInternaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(MittenteProtInternaCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idMittente").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
		
		@Override
		public String[] getTipiAmmessi() {
			if (isInBozza() && getFlgSoloInOrganigramma()) {
				if (AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG")) {
					return new String[] { "UO", "SV" };
				} else {
					return new String[] { "UO" };
				}
			}
			return new String[] {"UOI", "UP"};
		}
	
	}
	
	public String getFinalitaLookupOrganigramma(){
		if (AurigaLayout.getParametroDBAsBoolean("ESTENDI_SCELTA_MITT_BOZZA") && isInBozza() && getFlgSoloInOrganigramma()) {
			return "ASSEGNAZIONE";
		}else if(getAllowOnlyUnitaDiPersonale()) {
			return "SEL_UP";
		} else if(isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {				
			return isAttivaRestrAssCartaceoProt() ? "MITT_DEST_CARTACEO" : "MITT_DEST_NO_CARTACEO";
		} 	
		return "MITT_DEST";
	}	
	
	public class MittenteProtInternaMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<MittenteProtInternaCanvas> multiLookupList = new ArrayList<MittenteProtInternaCanvas>(); 
		
		public MittenteProtInternaMultiLookupOrganigramma(Record record) {
			super(record, false, (AurigaLayout.getParametroDBAsBoolean("SHOW_UP_IN_MITT_REG") ? null : new Integer(0)));			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVMitt");
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			MittenteProtInternaCanvas lastCanvas = (MittenteProtInternaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(lastCanvas);
			} else {
				MittenteProtInternaCanvas canvas = (MittenteProtInternaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(canvas);
			}								
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(MittenteProtInternaCanvas canvas : multiLookupList) {
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

	public String getFinalitaOrganigramma() {
		return null;
	}
	
	public String getUoProtocollante() {
		return null;
	}
	
	public void manageOnChangedUoProtocollante() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			MittenteProtInternaCanvas lMittenteProtInternaCanvas = (MittenteProtInternaCanvas) getReplicableCanvas(lHLayout);
			lMittenteProtInternaCanvas.manageOnChangedUoProtocollante();
		}		
	}

}
