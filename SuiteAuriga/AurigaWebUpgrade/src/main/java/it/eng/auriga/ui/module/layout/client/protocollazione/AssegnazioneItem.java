/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
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

public class AssegnazioneItem extends ReplicableItem {
	
	private boolean assegnatarioUnico = false;	
	private String flgUdFolder;
	private String tipoAssegnatari;
	
	private boolean flgSenzaLD = false;
	private boolean flgEscludiGruppiMisti = false;

	@Override
	public ReplicableCanvas getCanvasToReply() {		
		AssegnazioneCanvas lAssegnazioneCanvas = new AssegnazioneCanvas(this);		
		return lAssegnazioneCanvas;
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
				AssegnazioneMultiLookupOrganigramma lookupOrganigrammaPopup = new AssegnazioneMultiLookupOrganigramma(null);				
				lookupOrganigrammaPopup.show(); 	
			}   
		});
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			addButtons[0].setTabIndex(null);
			addButtons[0].setCanFocus(true);
			addButtons[1].setTabIndex(null);
			addButtons[1].setCanFocus(true);				
		}		
		return addButtons;		
	}
	
	public boolean isDimOrganigrammaNonStd() {
		return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST/ES");		
	}
	
	public boolean showOpzioniInvioAssegnazioneButton() {
		return true;
	}
	
	public boolean showPreferiti() {
		return flgUdFolder != null && !"".equals(flgUdFolder);
	}
	
	public RecordList getListaAssegnatariMitt() {
		Record lDetailRecord = getForm().getValuesManager() != null ? new Record(getForm().getValuesManager().getValues()) : null;
		return lDetailRecord != null ? lDetailRecord.getAttributeAsRecordList("listaAssPreselMitt") : null;
	}
	
	public boolean showAssegnatariMitt() {
		final RecordList listaAssPreselMitt = getListaAssegnatariMitt();
		return flgUdFolder != null && "U".equals(flgUdFolder) && (listaAssPreselMitt != null && !listaAssPreselMitt.isEmpty());
	}
	
	public boolean getFlgSenzaLD() {
		return flgSenzaLD;
	}

	public void setFlgSenzaLD(boolean flgSenzaLD) {
		this.flgSenzaLD = flgSenzaLD;
	}
	
	public boolean getFlgEscludiGruppiMisti() {
		return flgEscludiGruppiMisti;
	}

	public void setFlgEscludiGruppiMisti(boolean flgEscludiGruppiMisti) {
		this.flgEscludiGruppiMisti = flgEscludiGruppiMisti;
	}
	
	public void setAssegnatarioUnico(boolean assegnatarioUnico) {
		this.assegnatarioUnico = assegnatarioUnico;
		if(assegnatarioUnico) {
			setMaxLength(1);
		} else {
			setMaxLength(null);
		}
		HLayout addButtonsLayout = getAddButtonsLayout();
		if (addButtonsLayout != null && addButtonsLayout.getMembers() != null && addButtonsLayout.getMembers().length > 1) {
			if(assegnatarioUnico) {
				addButtonsLayout.getMembers()[1].hide();
			} else {
				addButtonsLayout.getMembers()[1].show();
			}
		}
		redraw();
	}
	
	public AssegnazioneCanvas getAssegnatarioUnicoCanvas() {
		if(getTotalMembers() == 1 && assegnatarioUnico) {
			return (AssegnazioneCanvas) getFirstCanvas();
		}
		return null;
	}
	
	public boolean getAssegnatarioUnico() {
		return assegnatarioUnico;
	}
	
	public String getFlgUdFolder() {
		return flgUdFolder;
	}

	public void setFlgUdFolder(String flgUdFolder) {
		this.flgUdFolder = flgUdFolder;
	}
	
	@Override
	public void setUpClickRemoveHandler(VLayout lVLayout, HLayout lHLayout) {
		super.setUpClickRemoveHandler(lVLayout, lHLayout);
		manageOnChanged();
	}
	
	public void manageOnChanged() {
		
	}
	
	public String getTipoAssegnatari() {
		String supportoOriginale = getSupportoOriginaleProt();
		if(supportoOriginale != null) {
			if((AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_CARTACEO") && "cartaceo".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_DIGITALE") && "digitale".equals(supportoOriginale)) ||
			   (AurigaLayout.getParametroDBAsBoolean("INIBITA_ASS_UP_MISTO") && "misto".equals(supportoOriginale))) {
				return "UO";
			}
		}
		return tipoAssegnatari;
	}

	public void setTipoAssegnatari(String tipoAssegnatari) {
		this.tipoAssegnatari = tipoAssegnatari;
	}
	
	public String getFlgSoloValide() {
		return null;
	}
	
	public String getSupportoOriginaleProt() {
		return null;
	}
	
	public boolean isSupportoOriginaleCartaceo() {
		String supportoOriginale = getSupportoOriginaleProt();
		return supportoOriginale != null && "cartaceo".equals(supportoOriginale);
	}

	public Integer getFlgIncludiUtenti() {
		Integer flgIncludiUtenti = new Integer(1);
		String tipoAssegnatari = getTipoAssegnatari();
		if(tipoAssegnatari != null) {
			if("UO".equals(tipoAssegnatari)) {
				flgIncludiUtenti = new Integer(0);
			} else if("SV".equals(tipoAssegnatari)) {
				flgIncludiUtenti = new Integer(2);				 
			} 
		}
		return flgIncludiUtenti;
	}

	public class AssegnazioneMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<AssegnazioneCanvas> multiLookupList = new ArrayList<AssegnazioneCanvas>(); 
		
		public AssegnazioneMultiLookupOrganigramma(Record record) {
			super(record, false, getFlgIncludiUtenti());			
		}

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			AssegnazioneCanvas lastCanvas = (AssegnazioneCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecord(record);
				multiLookupList.add(lastCanvas);
			} else {
				AssegnazioneCanvas canvas = (AssegnazioneCanvas) onClickNewButton();
				canvas.setFormValuesFromRecord(record);
				multiLookupList.add(canvas);
			}					
		}		
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(AssegnazioneCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("organigramma").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}	
		
		@Override
		public boolean getFlgMostraSVDefaultValue() {
			return !AurigaLayout.getImpostazioniSceltaOrganigrammaAsBoolean("flgNascondiSVAssegna");
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaOrganigrammaLookup();
		}
		
		@Override
		public String getIdUd() {
			return getIdUdProtocollo();
		}
	}
	
	public String getFinalitaOrganigrammaLookup() {
		if(isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {
			return isAttivaRestrAssCartaceoProt() ? "ASS_CARTACEO" : "ASS_NO_CARTACEO";
		} else return "ASSEGNAZIONE";
	}
	
	public String getFinalitaLoadComboOrganigramma() {
		if(isProtInModalitaWizard() && AurigaLayout.isAttivaFinalitaForRestrAssCartaceo()) {			
			return isAttivaRestrAssCartaceoProt() ? "ASS_CARTACEO" : "ASS_NO_CARTACEO";
		} else {
			return "ASS";
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
	
	public boolean isProtInModalitaWizard() {
		return false;
	}
	
	public boolean isAttivoAssegnatarioUnicoProt() {
		return false;
	}	
	
	public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
		return false;
	}	
	
	public boolean isAttivaRestrAssCartaceoProt() {
		return false;
	}
	
	public String getIdUdProtocollo() {
		try {
			Record lDetailRecord = new Record(getForm().getValuesManager().getValues());
			return lDetailRecord.getAttribute("idUd");
		} catch(Exception e) {}
		return null;
	}
	
	public String getCodRapidoTitle(){
		return I18NUtil.getMessages().protocollazione_detail_codRapidoItem_title();
	}
	
	public int getFilteredSelectItemWidth(){
		return 450;
	}
	
	public String getIdEmail() {
		return null;
	}
	
	public String getIdEmailArrivoProtocollo() {
		try {
			Record lDetailRecord = new Record(getForm().getValuesManager().getValues());
			return lDetailRecord.getAttribute("idEmailArrivo");
		} catch(Exception e) {}
		return null;
	}	
	
	public void reloadTipoValueMap() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			AssegnazioneCanvas lAssegnazioneCanvas = (AssegnazioneCanvas) getReplicableCanvas(lHLayout);
			lAssegnazioneCanvas.reloadTipoValueMap();
		}
	}

}
