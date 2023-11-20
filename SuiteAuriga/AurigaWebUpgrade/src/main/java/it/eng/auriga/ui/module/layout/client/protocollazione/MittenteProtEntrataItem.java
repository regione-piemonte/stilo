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
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;

public class MittenteProtEntrataItem extends MittenteProtItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		MittenteProtEntrataCanvas lMittenteProtEntrataCanvas = new MittenteProtEntrataCanvas(this);		
		return lMittenteProtEntrataCanvas;
	}
	
	public String getMezzoTrasmissione() {
		return null;
	}
	
	@Override
	public boolean getFlgAssegnaAlMittenteDefault() {
		return false; // non posso assegnare perchè sono solo utenti esterni
	}
	
	@Override
	public void resetDefaultValueFlgAssegnaAlMittente() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			MittenteProtEntrataCanvas lMittenteProtEntrataCanvas = (MittenteProtEntrataCanvas) getReplicableCanvas(lHLayout);
			lMittenteProtEntrataCanvas.resetDefaultValueFlgAssegnaAlMittente();
		}
	}
	
	@Override
	public boolean getFlgSoloInOrganigramma() {
		return false; //non ha senso che ci sia la restrizione per selezionare mittenti in organigramma: sono SOLO mittenti esterni
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
		addButtons[1].setSrc("lookup/rubricamulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupRubricaButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				cercaInRubricaAfterChanged = false;
				MittenteProtEntrataMultiLookupRubrica lookupRubricaPopup = new MittenteProtEntrataMultiLookupRubrica(null);				
				lookupRubricaPopup.show(); 	
			}   
		});
		
		return addButtons;		
	}
	
	public boolean getShowEmail() {
		return false;
	}
	
	public boolean getShowTelefono() {
		return false;
	}
	
	public boolean showOpenIndirizzo() {
		return false;
	}
	
	public boolean isRequiredDenominazione(boolean hasValue){
		return true;
	}
	
	public String getFinalitaLookup(){
		return "SEL_SOGG_EST";
	}
	
	public class MittenteProtEntrataMultiLookupRubrica extends LookupSoggettiPopup {

		private List<MittenteProtEntrataCanvas> multiLookupList = new ArrayList<MittenteProtEntrataCanvas>(); 
		
		public MittenteProtEntrataMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}

		@Override
		public String getFinalita() {
			return getFinalitaLookup();
		}
		
		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			MittenteProtEntrataCanvas lastCanvas = (MittenteProtEntrataCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				MittenteProtEntrataCanvas canvas = (MittenteProtEntrataCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(MittenteProtEntrataCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idMittente").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
		
		@Override
		public String[] getTipiAmmessi() {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? new String[] { "AOOI", "PA", "PF", "PG" } : new String[] { "PA", "PF", "PG" };
		}
	
	}

}
