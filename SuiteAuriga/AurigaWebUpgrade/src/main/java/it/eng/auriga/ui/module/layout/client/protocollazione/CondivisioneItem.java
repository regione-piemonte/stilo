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

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class CondivisioneItem extends ReplicableItem {
	
	private boolean readOnly = false;
	private boolean attivaEliminazioneUOCoinvolte = false;
	private String flgUdFolder;
	private boolean flgSoloUO = false;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		CondivisioneCanvas lCondivisioneCanvas = new CondivisioneCanvas(this);		
		return lCondivisioneCanvas;
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
		addButtons[1].setSrc("lookup/organigrammamulti.png");   
		addButtons[1].setShowDown(false);   
		addButtons[1].setShowRollOver(false);      
		addButtons[1].setSize(16); 
		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupOrganigrammaButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				CondivisioneMultiLookupOrganigramma lookupOrganigrammaPopup = new CondivisioneMultiLookupOrganigramma(null);				
				lookupOrganigrammaPopup.show(); 	
			}   
		});
		
		return addButtons;		
	}
	
	public boolean isDimOrganigrammaNonStd() {
		return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST/ES");		
	}
	
	public boolean showOpzioniInvioCondivisioneButton() {
		return true;
	}
	
	public boolean showPreferiti() {
		return flgUdFolder != null && !"".equals(flgUdFolder);
	}
	
	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}
	
	public boolean getFlgSoloUO() {
		return flgSoloUO;
	}

	public void setFlgSoloUO(boolean flgSoloUO) {
		this.flgSoloUO = flgSoloUO;
	}
	
	public class CondivisioneMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<CondivisioneCanvas> multiLookupList = new ArrayList<CondivisioneCanvas>(); 
		
		public CondivisioneMultiLookupOrganigramma(Record record) {
			super(record, false, getFlgSoloUO() ? new Integer(0) : new Integer(1));			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVInviiCC");
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			CondivisioneCanvas lastCanvas = (CondivisioneCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecord(record);
				multiLookupList.add(lastCanvas);
			} else {
				CondivisioneCanvas canvas = (CondivisioneCanvas) onClickNewButton();
				canvas.setFormValuesFromRecord(record);
				multiLookupList.add(canvas);
			}					
		}		
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(CondivisioneCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("organigramma").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
		
		@Override
		public String getFinalita() {
			return "INVIO_CC_NOTIFICA";
		}
		
		@Override
		public String getIdUd() {
			return getIdUdProtocollo();
		}
	
	}
	
	@Override
	public Record getCanvasDefaultRecord() {
		if(AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			Record lRecord = new Record();	
			lRecord.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			return lRecord;				
		}
		return null;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setReadOnly(false);
		setAttivaEliminazioneUOCoinvolte(false);
		super.setCanEdit(canEdit);
	}
	
	public void readOnlyMode(boolean attivaEliminazioneUOCoinvolte) {
		setCanEdit(false);
		setReadOnly(true);
		setAttivaEliminazioneUOCoinvolte(attivaEliminazioneUOCoinvolte);
		if (getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas();
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if (lVLayoutMember instanceof HLayout) {
					for (Canvas lHLayoutMember : ((HLayout) lVLayoutMember).getMembers()) {
						if (lHLayoutMember instanceof ReplicableCanvas) {
							CondivisioneCanvas lCondivisioneCanvas = (CondivisioneCanvas) lHLayoutMember;
							lCondivisioneCanvas.readOnlyMode(attivaEliminazioneUOCoinvolte);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void updateMode() {
		if(isReadOnly()) {
			readOnlyMode(isAttivaEliminazioneUOCoinvolte());
		} 		
	}
	
	public int getFilteredSelectItemWidth(){
		return 450;
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}
	
	public void setAttivaEliminazioneUOCoinvolte(boolean attivaEliminazioneUOCoinvolte) {
		this.attivaEliminazioneUOCoinvolte = attivaEliminazioneUOCoinvolte;
	}

	public boolean isAttivaEliminazioneUOCoinvolte() {
		return attivaEliminazioneUOCoinvolte;
	}
	
	public String getIdUdProtocollo() {
		try {
			Record lDetailRecord = new Record(getForm().getValuesManager().getValues());
			return lDetailRecord.getAttribute("idUd");
		} catch(Exception e) {}
		return null;
	}
	
	public String getIdEmailArrivoProtocollo() {
		try {
			Record lDetailRecord = new Record(getForm().getValuesManager().getValues());
			return lDetailRecord.getAttribute("idEmailArrivo");
		} catch(Exception e) {}
		return null;
	}
	
	public String getFinalitaOrganigrammaLookup() {
		return "INVIO_CC_NOTIFICA";
	}
	
	public String getFinalitaLoadComboOrganigramma() {
		return "NOT";
	}

}