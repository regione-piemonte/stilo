/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.protocollazione;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.LookupOrganigrammaPopup;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class DestinatarioProtUscitaItem extends DestinatarioProtItem{

	private boolean canEditMezzoTrasmissione = false;
	protected DynamicForm dynamicItemForm;
	protected CheckboxItem flgSegnaInvioMailExtraSistemaItem;
	protected CheckboxItem flgInviataMailExtraSistemaItem;
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		DestinatarioProtUscitaCanvas lDestinatarioProtUscitaCanvas = new DestinatarioProtUscitaCanvas(this);
		return lDestinatarioProtUscitaCanvas;
	}
	
	public boolean getShowDestinatariEstesi() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_DESTINATARI_ESTESI");
	}
	
	public boolean getSoloMezzoTrasmissionePEC() {
		return false;
	}
	
	public boolean showOpenIndirizzo() {
		return false;
	}
		
	public boolean isAttivoDestEsternoUnicoCartaceoProt() {		
		return isProtInModalitaWizard() && AurigaLayout.getParametroDBAsBoolean("DEST_EST_UNICO_CARTACEO") && isSupportoOriginaleCartaceoProt();
	}
	
	public boolean isEscludiDestinatariNonEsterni() {
		return AurigaLayout.getParametroDBAsBoolean("ESCLUDI_DEST_INT_IN_USCITA") || isAttivaSoloDestinatariEsterniCartaceoProt();		
	}
	
	public boolean isAttivaSoloDestinatariEsterniCartaceoProt() {				
		return isProtInModalitaWizard() && (isAttivoDestEsternoUnicoCartaceoProt() || AurigaLayout.getParametroDBAsBoolean("SOLO_DEST_ESTERNI_CARTACEO_U")) && isSupportoOriginaleCartaceoProt();
	}
	
	@Override
	public void resetDefaultValueFlgAssegnaAlDestinatario() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			DestinatarioProtUscitaCanvas lDestinatarioProtUscitaCanvas = (DestinatarioProtUscitaCanvas) getReplicableCanvas(lHLayout);
			lDestinatarioProtUscitaCanvas.resetDefaultValueFlgAssegnaAlDestinatario();
		}
	}
	
	@Override
	public HLayout createAddButtonsLayout() {
		HLayout addButtonsLayout = super.createAddButtonsLayout();
		
		flgSegnaInvioMailExtraSistemaItem = new CheckboxItem("flgSegnaInvioMailExtraSistema", "da inviare via PEC/e-mail extra sistema");
		flgSegnaInvioMailExtraSistemaItem.setRequired(false);
		flgSegnaInvioMailExtraSistemaItem.setColSpan(1);
		flgSegnaInvioMailExtraSistemaItem.setWidth(25);
		flgSegnaInvioMailExtraSistemaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA"));
			}
		});
		
		flgSegnaInvioMailExtraSistemaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				boolean checked = event.getValue() != null && (Boolean) event.getValue();	
				// Se NON è ceccato allora il check "inviata via PEC extra sistema" deve essere settato a NULL
				if (!checked) {							
					dynamicItemForm.setValue("flgInviataMailExtraSistema", false);
				} 
				dynamicItemForm.markForRedraw();
			}
		});
		
		flgInviataMailExtraSistemaItem = new CheckboxItem("flgInviataMailExtraSistema", "inviata via PEC/e-mail extra sistema");
		flgInviataMailExtraSistemaItem.setRequired(false);
		flgInviataMailExtraSistemaItem.setColSpan(1);
		flgInviataMailExtraSistemaItem.setWidth(25);
		flgInviataMailExtraSistemaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				boolean isBozza = isInBozza();
				boolean isProtocollata = isProtocollata();
				boolean isRepertoriata = isRepertoriata();
				boolean flgSegnaInvioMailExtraSistema = dynamicItemForm.getValue("flgSegnaInvioMailExtraSistema") != null && (Boolean) dynamicItemForm.getValue("flgSegnaInvioMailExtraSistema");
				
				// Si vede SOLO se : 
				// a) Non è una BOZZA
				// b) è stata protocollata o repertoriata
				// c) se il check "da inviare via PEC extra sistema" è spuntato
				return (!isBozza && (isProtocollata || isRepertoriata) && flgSegnaInvioMailExtraSistema && AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA"));
			}
		});
		
		dynamicItemForm = new DynamicForm();
		dynamicItemForm.setNumCols(10);
		dynamicItemForm.setColWidths(1,1,1,1,1,1,1,1,"*");
		dynamicItemForm.setMargin(0);	
		
		dynamicItemForm.setFields(flgSegnaInvioMailExtraSistemaItem, flgInviataMailExtraSistemaItem);

		addButtonsLayout.addMember(dynamicItemForm);

		return addButtonsLayout;
	}
	
	@Override
	public void setShowHideAddButtonsLayout(HLayout addButtonsLayout, boolean isToShow) {
		if(addButtonsLayout != null ) {
			Canvas[] members = addButtonsLayout.getMembers();
			for (Canvas canvas : members) {
				if(canvas instanceof DynamicForm) {
					DynamicForm dynamicForm = (DynamicForm) canvas;
					dynamicForm.show();
					dynamicForm.setCanEdit(editing);
				} else {
					if(isToShow) {
						canvas.show();
					} else {
						canvas.hide();
					}
				}
			}
		}
	}
	
//	@Override
//	public HLayout createOtherItemsLayout() {
//		
//		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA")) {
//			HLayout otherItemsLayout;
//			otherItemsLayout = new HLayout();
//			otherItemsLayout.setMembersMargin(3);
//			flgSegnaInvioMailExtraSistemaItem = new CheckboxItem("flgSegnaInvioMailExtraSistema",
//					"da inviare/inviata via PEC extra-sistema");
//			flgSegnaInvioMailExtraSistemaItem.setRequired(false);
//			flgSegnaInvioMailExtraSistemaItem.setColSpan(1);
//			flgSegnaInvioMailExtraSistemaItem.setWidth(25);
//			flgSegnaInvioMailExtraSistemaItem.setShowIfCondition(new FormItemIfFunction() {
//
//				@Override
//				public boolean execute(FormItem item, Object value, DynamicForm form) {
//					return (AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA"));
//				}
//			});
//			dynamicItemForm = new DynamicForm();
//			dynamicItemForm.setNumCols(10);
//			dynamicItemForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*");
//			dynamicItemForm.setMargin(0);
//			dynamicItemForm.setFields(flgSegnaInvioMailExtraSistemaItem);
//			otherItemsLayout.addMember(dynamicItemForm);
//			
//			return otherItemsLayout;
//		} else {
//			return null;
//		}
//	}
	
	@Override
	protected ImgButton[] createAddButtons() {

		ImgButton[] addButtons = !AurigaLayout.getParametroDBAsBoolean("ESCLUDI_DEST_INT_IN_USCITA") ? new ImgButton[3] : new ImgButton[2];
		
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
				DestinatarioProtUscitaMultiLookupRubrica lookupRubricaPopup = new DestinatarioProtUscitaMultiLookupRubrica(null);				
				lookupRubricaPopup.show(); 	
			}   
		});
		
		if(!AurigaLayout.getParametroDBAsBoolean("ESCLUDI_DEST_INT_IN_USCITA")) {
						
			addButtons[2] = new ImgButton();   
			addButtons[2].setSrc("lookup/organigrammamulti.png");   
			addButtons[2].setShowDown(false);   
			addButtons[2].setShowRollOver(false);      
			addButtons[2].setSize(16); 
			addButtons[2].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupOrganigrammaButton_prompt());
			addButtons[2].addClickHandler(new ClickHandler() {	
				@Override
				public void onClick(ClickEvent event) {
					cercaInRubricaAfterChanged = false;
					DestinatarioProtUscitaMultiLookupOrganigramma lookupOrganigrammaPopup = new DestinatarioProtUscitaMultiLookupOrganigramma(null);				
					lookupOrganigrammaPopup.show(); 	
				}   
			});
		}
					
		return addButtons;				
	}
	
	public Boolean isFlgSegnaInvioMailExtraSistema() {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA")) {
			Boolean flgSegnaInvioMailExtraSistema = dynamicItemForm != null && dynamicItemForm.getValue("flgSegnaInvioMailExtraSistema") != null && (Boolean) dynamicItemForm.getValue("flgSegnaInvioMailExtraSistema");
			return flgSegnaInvioMailExtraSistema != null && flgSegnaInvioMailExtraSistema;
		}
		return false;
	}
	
	public void setFlgSegnaInvioMailExtraSistema(Boolean flgSegnaInvioMailExtraSistema) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA") && dynamicItemForm != null) {
			dynamicItemForm.setValue("flgSegnaInvioMailExtraSistema", flgSegnaInvioMailExtraSistema);
		}
	}
	
	
	public Boolean isFlgInviataMailExtraSistemaItem() {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA")) {
			Boolean flgInviataMailExtraSistema = dynamicItemForm != null && dynamicItemForm.getValue("flgInviataMailExtraSistema") != null && (Boolean) dynamicItemForm.getValue("flgInviataMailExtraSistema");
			return flgInviataMailExtraSistema != null && flgInviataMailExtraSistema;
		}
		return false;
		
	}

	public void setFlgInviataMailExtraSistemaItem(Boolean flgInviataMailExtraSistema) {
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_SEGNA_INVIO_MAIL_EXTRA_SISTEMA") && dynamicItemForm != null) {
			dynamicItemForm.setValue("flgInviataMailExtraSistema", flgInviataMailExtraSistema);
		}
	}

	
	public String getFinalitaLookup(){
		if(isEscludiDestinatariNonEsterni() || AurigaLayout.getParametroDBAsBoolean("DEST_USCITA_CERCA_IN_RUBR_SOLO_ESTERNI")) {			
			return "SEL_SOGG_EST";
		}
		return null;
	}
	
	public class DestinatarioProtUscitaMultiLookupRubrica extends LookupSoggettiPopup {

		private List<DestinatarioProtUscitaCanvas> multiLookupList = new ArrayList<DestinatarioProtUscitaCanvas>(); 
		
		public DestinatarioProtUscitaMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}
		
		@Override
		public String getFinalita() {			
			return getFinalitaLookup();
		};

		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {	
			DestinatarioProtUscitaCanvas lastCanvas = (DestinatarioProtUscitaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				DestinatarioProtUscitaCanvas canvas = (DestinatarioProtUscitaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}								
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(DestinatarioProtUscitaCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if(values.getAttribute("idDestinatario").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
		
		@Override
		public String[] getTipiAmmessi() {
			if(isEscludiDestinatariNonEsterni() || AurigaLayout.getParametroDBAsBoolean("DEST_USCITA_CERCA_IN_RUBR_SOLO_ESTERNI")) {
				return AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? new String[] { "AOOI", /*"UOI", "UP",*/ "PA", "PF", "PG"} : new String[] {/*"UOI", "UP",*/ "PA", "PF", "PG"};
			}	
//			return AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? new String[] { "AOOI", "UOI", "UP", "PA", "PF", "PG"} : new String[] {"UOI", "UP", "PA", "PF", "PG"};
			return null;											
		}
	
	}
	
	public class DestinatarioProtUscitaMultiLookupOrganigramma extends LookupOrganigrammaPopup {

		private List<DestinatarioProtUscitaCanvas> multiLookupList = new ArrayList<DestinatarioProtUscitaCanvas>(); 
		
		public DestinatarioProtUscitaMultiLookupOrganigramma(Record record) {
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
			DestinatarioProtUscitaCanvas lastCanvas = (DestinatarioProtUscitaCanvas) getLastCanvas();
			if(lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(lastCanvas);
			} else {
				DestinatarioProtUscitaCanvas canvas = (DestinatarioProtUscitaCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordOrganigramma(record);
				multiLookupList.add(canvas);
			}							
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			for(DestinatarioProtUscitaCanvas canvas : multiLookupList) {
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
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setCanEditMezzoTrasmissione(false);
		super.setCanEdit(canEdit);
	}
	
	@Override
	public void setCanEditMezzoTrasmissioneMode(boolean mode) {		
		setCanEdit(mode);		
		setCanEditMezzoTrasmissione(true);
		if(getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas(); 		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);
				if(lVLayoutMember instanceof HLayout) {
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof ReplicableCanvas) {
							DestinatarioProtUscitaCanvas lDestinatarioProtUscitaCanvas = (DestinatarioProtUscitaCanvas)lHLayoutMember;								
							lDestinatarioProtUscitaCanvas.canEditMezzoTrasmissione();
						}
					}	
				}			
			}
		}
		
		/*
		if(getCanvas() != null) {
			final VLayout lVLayout = (VLayout) getCanvas(); 		
			for (int i=0;i<lVLayout.getMembers().length; i++){
				Canvas lVLayoutMember = lVLayout.getMember(i);									
				if(lVLayoutMember instanceof HLayout) {
					for(Canvas lHLayoutMember : ((HLayout)lVLayoutMember).getMembers()) {
						if(lHLayoutMember instanceof ImgButton) {
							if(i == (lVLayout.getMembers().length - 1)) {
								// se è un bottone di add lo mostro
								if(getShowNewButton()) {
									lHLayoutMember.show();
								} else {
									lHLayoutMember.hide();
								}
							} else if(lHLayoutMember instanceof RemoveButton) {
								// se è un bottone di remove lo disabilito
								((RemoveButton)lHLayoutMember).setAlwaysDisabled(true);																	
							}																			
						} else if(lHLayoutMember instanceof ReplicableCanvas) {
							DestinatarioProtUscitaCanvas lDestinatarioProtUscitaCanvas = (DestinatarioProtUscitaCanvas)lHLayoutMember;								
							lDestinatarioProtUscitaCanvas.canEditMezzoTrasmissione();
						}
					}	
				}			
			}
		}	
		*/
	}
	
	@Override
	public void updateMode() {
		if(isCanEditMezzoTrasmissione()) {
			setCanEditMezzoTrasmissioneMode(editing);
		} 		
	}
	
	public void redrawAddButtons() {
		if(isAttivoDestEsternoUnicoCartaceoProt()) {
			setMaxLength(1);
		} else {
			setMaxLength(null);
		}
		HLayout addButtonsLayout = getAddButtonsLayout();
		if (addButtonsLayout != null && addButtonsLayout.getMembers() != null && addButtonsLayout.getMembers().length > 1) {
			if(isAttivoDestEsternoUnicoCartaceoProt()) {
				addButtonsLayout.getMembers()[1].hide();
				addButtonsLayout.getMembers()[2].hide();				
			} else {
				addButtonsLayout.getMembers()[1].show();
				addButtonsLayout.getMembers()[2].show();				
			}
			if(addButtonsLayout.getMembers().length > 2) {
				if(isAttivaSoloDestinatariEsterniCartaceoProt()) {
					addButtonsLayout.getMembers()[2].hide();
				} else {
					addButtonsLayout.getMembers()[2].show();
				}	
			}			
		}
		redraw();
	}
	
	public boolean isSupportoOriginaleCartaceoProt() {
		String supportoOriginale = getSupportoOriginaleProt();		
		return supportoOriginale != null && "cartaceo".equals(supportoOriginale);
	}
	
	public void setCanEditMezzoTrasmissione(boolean canEditMezzoTrasmissione) {
		this.canEditMezzoTrasmissione = canEditMezzoTrasmissione;
	}

	public boolean isCanEditMezzoTrasmissione() {
		return canEditMezzoTrasmissione;
	}
	
	public void manageOnChangedUoProtocollante() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			DestinatarioProtUscitaCanvas lDestinatarioProtUscitaCanvas = (DestinatarioProtUscitaCanvas) getReplicableCanvas(lHLayout);
			lDestinatarioProtUscitaCanvas.manageOnChangedUoProtocollante();
		}		
	}
	
	public void reloadTipoValueMap() {
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			DestinatarioProtUscitaCanvas lDestinatarioProtUscitaCanvas = (DestinatarioProtUscitaCanvas) getReplicableCanvas(lHLayout);
			lDestinatarioProtUscitaCanvas.reloadTipoValueMap();
		}
	}

	public void setCanEditFlgSegnaInvioMailExtraSistema(boolean canEditFlgSegnaInvioMailExtraSistema) {
		flgSegnaInvioMailExtraSistemaItem.setCanEdit(canEditFlgSegnaInvioMailExtraSistema);
	}

	public void setCanEditFlgInviataMailExtraSistema(boolean canEditFlgInviataMailExtraSistema) {
		flgInviataMailExtraSistemaItem.setCanEdit(canEditFlgInviataMailExtraSistema);
	}
}
