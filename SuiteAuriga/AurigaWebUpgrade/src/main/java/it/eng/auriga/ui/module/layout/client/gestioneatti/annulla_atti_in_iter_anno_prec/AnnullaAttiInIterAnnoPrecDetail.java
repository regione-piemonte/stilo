/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.gestioneatti.annulla_atti_in_iter_anno_prec;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class AnnullaAttiInIterAnnoPrecDetail extends CustomDetail {
	
	// window
	protected AnnullaAttiInIterAnnoPrecWindow window;
	
	// DynamicForm
	protected DynamicForm proposteDecretoForm;
	protected DynamicForm proposteRdAForm;
	
	// DetailSection
	protected DetailSection proposteDecretoSection;
	protected DetailSection proposteRdASection;

	// NumericItem
	protected NumericItem nroTotaleProposteDecretoItem;
	protected NumericItem nroTotaleProposteDecretoConMovimentiContabiliItem;
	protected NumericItem nroProposteDecretoInFaseIstruttoriaItem;
	protected NumericItem nroProposteDecretoInVerificaBilancioItem;
	protected NumericItem nroProposteDecretoInFasePerfezionamentoItem;
	protected NumericItem nroProposteRdaItem;
	
	// TitleItem
	protected TitleItem flgAnnullaConRilevContabFaseIstrutTitleItem;
	protected TitleItem flgAnnullaConRilevContabFasePerfezTitleItem;
	
	// CheckboxItem
	protected CheckboxItem flgAnnullaConRilevContabFaseIstrutNoValueItem;
	protected CheckboxItem flgAnnullaConRilevContabFaseIstrutSiValueItem;
	protected CheckboxItem flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem;
	protected CheckboxItem flgAnnullaConRilevContabFasePerfezNoValueItem;
	protected CheckboxItem flgAnnullaConRilevContabFasePerfezSiValueItem;
	protected CheckboxItem flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem;
	protected CheckboxItem flgProposteRdaDaAnnullareItem;
	
	// StaticTextItem 
	protected StaticTextItem nroProposteDecretoInVerificaBilancioLabel;
	protected StaticTextItem lineaSepItem;
	
	public AnnullaAttiInIterAnnoPrecDetail(String nomeEntita) {

		super(nomeEntita);
		
		sezioneProposteDecretoAnnoPassatoAncoraInIter();
		
		sezioneProposteRdAAnnoPassatoAncoraInIter();
		
		setMembers(proposteDecretoSection, proposteRdASection);
	}

	// Sezione Proposte di decreto anno passato ancora in iter
	private void sezioneProposteDecretoAnnoPassatoAncoraInIter(){
		
		proposteDecretoForm = new DynamicForm();
		proposteDecretoForm.setValuesManager(vm);
		proposteDecretoForm.setPadding(5);
		proposteDecretoForm.setNumCols(5);
		proposteDecretoForm.setColWidths(120,1,1,1,"*");
		proposteDecretoForm.setWrapItemTitles(false);
		
		// Riga 1 : N° totale proposte
		nroTotaleProposteDecretoItem = new NumericItem("nroTotaleProposteDecreto", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_nroTotaleProposteDecretoItem_title());
		nroTotaleProposteDecretoItem.setColSpan(4);	
		nroTotaleProposteDecretoItem.setWidth(100);
		nroTotaleProposteDecretoItem.setCanEdit(false);
		nroTotaleProposteDecretoItem.setStartRow(true);
		
		//  Riga 2 : N° totale proposte con movimenti contabili 
		nroTotaleProposteDecretoConMovimentiContabiliItem = new NumericItem("nroTotaleProposteDecretoConMovimentiContabili", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_nroTotaleProposteDecretoConMovimentiContabiliItem_title());
		nroTotaleProposteDecretoConMovimentiContabiliItem.setColSpan(4);	
		nroTotaleProposteDecretoConMovimentiContabiliItem.setWidth(100);
		nroTotaleProposteDecretoConMovimentiContabiliItem.setCanEdit(false);
		nroTotaleProposteDecretoConMovimentiContabiliItem.setStartRow(true);
		
		//  Riga 3 : N° proposte in FASE ISTRUTTORIA
		nroProposteDecretoInFaseIstruttoriaItem = new NumericItem("nroProposteDecretoInFaseIstruttoria", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_nroProposteDecretoInFaseIstruttoriaItem_title());
		nroProposteDecretoInFaseIstruttoriaItem.setColSpan(4);	
		nroProposteDecretoInFaseIstruttoriaItem.setWidth(100);
		nroProposteDecretoInFaseIstruttoriaItem.setCanEdit(false);
		nroProposteDecretoInFaseIstruttoriaItem.setStartRow(true);
		
		//  Riga 4 : Annulla quelle con rilevanza contabile (fase istruttoria)
		flgAnnullaConRilevContabFaseIstrutTitleItem = new TitleItem(I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabTitleItem_title(), true);
		flgAnnullaConRilevContabFaseIstrutTitleItem.setColSpan(1);	
		flgAnnullaConRilevContabFaseIstrutTitleItem.setWidth(10);
		flgAnnullaConRilevContabFaseIstrutTitleItem.setStartRow(true);
		
		// check NO
		flgAnnullaConRilevContabFaseIstrutNoValueItem = new CheckboxItem("flgAnnullaConRilevContabFaseIstrutNoValue", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabNoValueItem_title());
		flgAnnullaConRilevContabFaseIstrutNoValueItem.setColSpan(1);
		flgAnnullaConRilevContabFaseIstrutNoValueItem.setWidth(10);
		flgAnnullaConRilevContabFaseIstrutNoValueItem.setStartRow(false);
		flgAnnullaConRilevContabFaseIstrutNoValueItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				checkBoxOnChanged();
			}
		});
				
		// check SI
		flgAnnullaConRilevContabFaseIstrutSiValueItem = new CheckboxItem("flgAnnullaConRilevContabFaseIstrutSiValue", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabSiValueItem_title());
		flgAnnullaConRilevContabFaseIstrutSiValueItem.setColSpan(1);
		flgAnnullaConRilevContabFaseIstrutSiValueItem.setWidth(10);
		flgAnnullaConRilevContabFaseIstrutSiValueItem.setStartRow(false);
		flgAnnullaConRilevContabFaseIstrutSiValueItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				checkBoxOnChanged();
			}
		});

		// check SI, ma senza movimenti contabili
		flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem = new CheckboxItem("flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabSiSenzaMovContabItem_title());
		flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.setColSpan(1);
		flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.setWidth(10);
		flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.setStartRow(false);
		flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				checkBoxOnChanged();
			}
		});

		// Riga 5 : N° proposte in VERIFICA BILANCIO
		nroProposteDecretoInVerificaBilancioItem = new NumericItem("nroProposteDecretoInVerificaBilancio", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_nroProposteDecretoInVerificaBilancioItem_title());
		nroProposteDecretoInVerificaBilancioItem.setColSpan(4);	
		nroProposteDecretoInVerificaBilancioItem.setWidth(100);
		nroProposteDecretoInVerificaBilancioItem.setCanEdit(false);
		nroProposteDecretoInVerificaBilancioItem.setStartRow(true);
		
		// Riga 6 : scritta rossa "Non annullabili: serve re-invio alla FASE ISTRUTTORIA"
		nroProposteDecretoInVerificaBilancioLabel = new StaticTextItem();
		nroProposteDecretoInVerificaBilancioLabel.setDefaultValue("<span style=\"color:red\">" + I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_nroProposteDecretoInVerificaBilancioLabel_title() + "</b></span>");		
		nroProposteDecretoInVerificaBilancioLabel.setCellStyle(it.eng.utility.Styles.formTitle);		
		nroProposteDecretoInVerificaBilancioLabel.setShowTitle(false);
		nroProposteDecretoInVerificaBilancioLabel.setAlign(Alignment.LEFT);
		nroProposteDecretoInVerificaBilancioLabel.setWidth("*");
		nroProposteDecretoInVerificaBilancioLabel.setWrap(false);
		nroProposteDecretoInVerificaBilancioLabel.setColSpan(5);
		nroProposteDecretoInVerificaBilancioLabel.setStartRow(true);
		nroProposteDecretoInVerificaBilancioLabel.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Integer nroProposteDecretoInVerificaBilancioValue  = 0;
				if (nroProposteDecretoInVerificaBilancioItem.getValue()!=null  && nroProposteDecretoInVerificaBilancioItem.getValueAsString()!=null && !"".equals(nroProposteDecretoInVerificaBilancioItem.getValueAsString()) )
					nroProposteDecretoInVerificaBilancioValue = Integer.parseInt(nroProposteDecretoInVerificaBilancioItem.getValueAsString());
				
				return (nroProposteDecretoInVerificaBilancioValue > 0) ? true : false;
			}
		});	

		// Riga 7 : N° proposte in FASE PERFEZIONAMENTO 
		nroProposteDecretoInFasePerfezionamentoItem = new NumericItem("nroProposteDecretoInFasePerfezionamento", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_nroProposteDecretoInFasePerfezionamentoItem_title());
		nroProposteDecretoInFasePerfezionamentoItem.setColSpan(4);	
		nroProposteDecretoInFasePerfezionamentoItem.setWidth(100);
		nroProposteDecretoInFasePerfezionamentoItem.setCanEdit(false);
		nroProposteDecretoInFasePerfezionamentoItem.setStartRow(true);
				
		// Riga 8 : Annulla quelle con rilevanza contabile (fase perfezionamento)
		flgAnnullaConRilevContabFasePerfezTitleItem = new TitleItem(I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabTitleItem_title(), true);
		flgAnnullaConRilevContabFasePerfezTitleItem.setColSpan(1);	
		flgAnnullaConRilevContabFasePerfezTitleItem.setWidth(10);
		flgAnnullaConRilevContabFasePerfezTitleItem.setStartRow(true);
		
		// check NO
		flgAnnullaConRilevContabFasePerfezNoValueItem = new CheckboxItem("flgAnnullaConRilevContabFasePerfezNoValue", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabNoValueItem_title());
		flgAnnullaConRilevContabFasePerfezNoValueItem.setColSpan(1);
		flgAnnullaConRilevContabFasePerfezNoValueItem.setWidth(10);
		flgAnnullaConRilevContabFasePerfezNoValueItem.setStartRow(false);
		flgAnnullaConRilevContabFasePerfezNoValueItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				checkBoxOnChanged();
			}
		});
				
		// check SI
		flgAnnullaConRilevContabFasePerfezSiValueItem = new CheckboxItem("flgAnnullaConRilevContabFasePerfezSiValue", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabSiValueItem_title());
		flgAnnullaConRilevContabFasePerfezSiValueItem.setColSpan(1);
		flgAnnullaConRilevContabFasePerfezSiValueItem.setWidth(10);
		flgAnnullaConRilevContabFasePerfezSiValueItem.setStartRow(false);
		flgAnnullaConRilevContabFasePerfezSiValueItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				checkBoxOnChanged();
			}
		});
		
		// check SI, ma senza movimenti contabili
		flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem = new CheckboxItem("flgAnnullaConRilevContabFasePerfezSiSenzaMovContab", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgAnnullaConRilevContabSiSenzaMovContabItem_title());
		flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.setColSpan(1);
		flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.setWidth(10);
		flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.setStartRow(false);
		flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				checkBoxOnChanged();
			}
		});

		lineaSepItem = new StaticTextItem();
		lineaSepItem.setDefaultValue("<hr align=\"center\" size=\"1\" noshade>");
		lineaSepItem.setHeight(20);
		
		lineaSepItem.setCellStyle(it.eng.utility.Styles.formTitle);		
		lineaSepItem.setShowTitle(false);
		lineaSepItem.setAlign(Alignment.CENTER);
		lineaSepItem.setWidth("*");
		lineaSepItem.setWrap(false);
		lineaSepItem.setColSpan(5);
		lineaSepItem.setStartRow(true);
		
		proposteDecretoForm.setItems(nroTotaleProposteDecretoItem,
                                     nroTotaleProposteDecretoConMovimentiContabiliItem,
                                     lineaSepItem,
                                     nroProposteDecretoInFaseIstruttoriaItem,
                                     flgAnnullaConRilevContabFaseIstrutTitleItem,
                                     flgAnnullaConRilevContabFaseIstrutNoValueItem,
                                     flgAnnullaConRilevContabFaseIstrutSiValueItem,
                                     flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem,
                                     lineaSepItem,
                                     nroProposteDecretoInVerificaBilancioItem,
                                     nroProposteDecretoInVerificaBilancioLabel,
                                     lineaSepItem,
                                     nroProposteDecretoInFasePerfezionamentoItem,
                                     flgAnnullaConRilevContabFasePerfezTitleItem,
                                     flgAnnullaConRilevContabFasePerfezNoValueItem,
                                     flgAnnullaConRilevContabFasePerfezSiValueItem,
                                     flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem
                                     );		

		proposteDecretoSection = new DetailSection(I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_proposteDecretoAnnoPassatoAncoraInIter_section_title(), 3, false, true, false, proposteDecretoForm);
	}
	
	// Sezione Proposte di RdA anno passato ancora in iter
	private void sezioneProposteRdAAnnoPassatoAncoraInIter(){
				
		proposteRdAForm = new DynamicForm();
		proposteRdAForm.setValuesManager(vm);
		proposteRdAForm.setWidth("100%");
		proposteRdAForm.setPadding(5);
		proposteRdAForm.setNumCols(5);
		proposteRdAForm.setColWidths(120,1,1,1,"*");
		proposteRdAForm.setWrapItemTitles(false);
		
		// Riga 1 : N° proposte
		nroProposteRdaItem = new NumericItem("nroProposteRda", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_nroProposteRdaItem_title());
		nroProposteRdaItem.setColSpan(1);	
		nroProposteRdaItem.setWidth(100);
		nroProposteRdaItem.setCanEdit(false);
		nroProposteRdaItem.setStartRow(true);
				
		// Riga 2 :Da annullare
		flgProposteRdaDaAnnullareItem = new CheckboxItem("flgProposteRdaDaAnnullare", I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_flgProposteRdaDaAnnullareItem_title());
		flgProposteRdaDaAnnullareItem.setColSpan(1);
		flgProposteRdaDaAnnullareItem.setWidth(10);
		flgProposteRdaDaAnnullareItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				checkBoxOnChanged();
			}
		});
		
		proposteRdAForm.setItems(nroProposteRdaItem,flgProposteRdaDaAnnullareItem);
					
		proposteRdASection = new DetailSection(I18NUtil.getMessages().annullaAttiInIterAnnoPrecDetail_proposteRdaAnnoPassatoAncoraInIter_section_title(), 3, false, true, false, proposteRdAForm);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void setWindow(AnnullaAttiInIterAnnoPrecWindow window) {
		this.window = window;
	}
	
	public AnnullaAttiInIterAnnoPrecWindow getWindow() {
		return window;
	}
	
	private void initCheckBox() {
		Integer nroProposteRda = (Integer) ((nroProposteRdaItem.getValue() != null) ? (nroProposteRdaItem.getValue()) : 0);
		Integer nroProposteDecretoInFaseIstruttoria = (Integer) ((nroProposteDecretoInFaseIstruttoriaItem.getValue() != null) ? (nroProposteDecretoInFaseIstruttoriaItem.getValue()) : 0);
		Integer nroProposteDecretoInFasePerfezionamento = (Integer) ((nroProposteDecretoInFasePerfezionamentoItem.getValue() != null) ? (nroProposteDecretoInFasePerfezionamentoItem.getValue()) : 0);
		
		// Se il campo "N° proposte RDA" > 0 e Se si ha privilegio ATT/ANM/RDA
		// allora il check "Da annullare" è abilitato e spuntato
		// altrimenti il check è de-spuntato e disabilitato.
		if (nroProposteRda > 0 && Layout.isPrivilegioAttivo("ATT/ANM/RDA")){
			flgProposteRdaDaAnnullareItem.setValue(true);
			vm.setValue("flgProposteRdaDaAnnullare", true);
			flgProposteRdaDaAnnullareItem.setCanEdit(true);
		}
		else{
			flgProposteRdaDaAnnullareItem.setValue(false);
			vm.setValue("flgProposteRdaDaAnnullare", false);
			flgProposteRdaDaAnnullareItem.setCanEdit(false);
		}
		
		// Se il campo "N° proposte in FASE ISTRUTTORIA" > 0 e Se si ha il privilegio "ATT/ANM/DCR"
		// allora i check "SI" e "SI, ma senza movimenti contabili" sono abilitati e spuntati
		// altrimenti i 2 check sono de-spuntati e disabilitati.
		if (nroProposteDecretoInFaseIstruttoria > 0 && Layout.isPrivilegioAttivo("ATT/ANM/DCR")){
			flgAnnullaConRilevContabFaseIstrutSiValueItem.setValue(true);
			flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.setValue(true);
			
			vm.setValue("flgAnnullaConRilevContabFaseIstrutSiValue", true);
			vm.setValue("flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab", true);
			
			flgAnnullaConRilevContabFaseIstrutSiValueItem.setCanEdit(true);
			flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.setCanEdit(true);
		}
		else{
			flgAnnullaConRilevContabFaseIstrutSiValueItem.setValue(false);
			flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.setValue(false);
				
			vm.setValue("flgAnnullaConRilevContabFaseIstrutSiValue", false);
			vm.setValue("flgAnnullaConRilevContabFaseIstrutSiSenzaMovContab", false);
				
			flgAnnullaConRilevContabFaseIstrutSiValueItem.setCanEdit(false);
			flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.setCanEdit(false);
		}	
			
		// Se il campo "N° proposte in FASE ISTRUTTORIA" > 0 e	Se si ha privilegio ATT/ANM/DNR 
        // allora il check "NO" è abilitato e spuntato in automatico, 
		// altrimenti il check è de-spuntato e disabilitato.
		
		if (nroProposteDecretoInFaseIstruttoria > 0 && Layout.isPrivilegioAttivo("ATT/ANM/DNR")) {
			flgAnnullaConRilevContabFaseIstrutNoValueItem.setValue(true);
			vm.setValue("flgAnnullaConRilevContabFaseIstrutNoValue", true);
			flgAnnullaConRilevContabFaseIstrutNoValueItem.setCanEdit(true);
		}
		else{
			flgAnnullaConRilevContabFaseIstrutNoValueItem.setValue(false);
			vm.setValue("flgAnnullaConRilevContabFaseIstrutNoValue", false);
			flgAnnullaConRilevContabFaseIstrutNoValueItem.setCanEdit(false);
		}
		
		// Se si il campo "N° proposte in FASE PERFEZIONAMENTO" > 0 e Se si ha il privilegio "ATT/ANM/DCR"
		// allora i check "SI" e "SI, ma senza movimenti contabili" sono abilitati e spuntati
		// altrimenti i 2 check sono de-spuntati e disabilitati.
		if (nroProposteDecretoInFasePerfezionamento > 0 && Layout.isPrivilegioAttivo("ATT/ANM/DCR")){
			flgAnnullaConRilevContabFasePerfezSiValueItem.setValue(true);
			flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.setValue(true);
			
			vm.setValue("flgAnnullaConRilevContabFasePerfezSiValue", true);
			vm.setValue("flgAnnullaConRilevContabFasePerfezSiSenzaMovContab", true);
			
			flgAnnullaConRilevContabFasePerfezSiValueItem.setCanEdit(true);
			flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.setCanEdit(true);
		}	
		else{
			flgAnnullaConRilevContabFasePerfezSiValueItem.setValue(false);
			flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.setValue(false);
				
			vm.setValue("flgAnnullaConRilevContabFasePerfezSiValue", false);
			vm.setValue("flgAnnullaConRilevContabFasePerfezSiSenzaMovContab", false);
				
			flgAnnullaConRilevContabFasePerfezSiValueItem.setCanEdit(false);
			flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.setCanEdit(false);
		}
			
		// Se si il campo "N° proposte in FASE PERFEZIONAMENTO" > 0 e Se si ha privilegio ATT/ANM/DNR 
		// allora il check "NO" è abilitato e spuntato in automatico, 
		// altrimenti il check è de-spuntato e disabilitato.
		if (nroProposteDecretoInFasePerfezionamento > 0 && Layout.isPrivilegioAttivo("ATT/ANM/DNR")) {
			flgAnnullaConRilevContabFasePerfezNoValueItem.setValue(true);
			vm.setValue("flgAnnullaConRilevContabFasePerfezNoValue", true);
			flgAnnullaConRilevContabFasePerfezNoValueItem.setCanEdit(true);
		}
		else{
			flgAnnullaConRilevContabFasePerfezNoValueItem.setValue(false);
			vm.setValue("flgAnnullaConRilevContabFasePerfezNoValue", false);
			flgAnnullaConRilevContabFasePerfezNoValueItem.setCanEdit(false);
		}		
	}
	
	private void disabilitaAnnullaProposteButton() {
		
		boolean disabilitaAnnullaProposteButton = true;
		
		Integer nroProposteRda = (Integer) ((nroProposteRdaItem.getValue() != null) ? (nroProposteRdaItem.getValue()) : 0);
		Integer nroProposteDecretoInFaseIstruttoria = (Integer) ((nroProposteDecretoInFaseIstruttoriaItem.getValue() != null) ? (nroProposteDecretoInFaseIstruttoriaItem.getValue()) : 0);
		Integer nroProposteDecretoInFasePerfezionamento = (Integer) ((nroProposteDecretoInFasePerfezionamentoItem.getValue() != null) ? (nroProposteDecretoInFasePerfezionamentoItem.getValue()) : 0);
		Integer nroProposteDecretoInVerificaBilancio = (Integer) ((nroProposteDecretoInVerificaBilancioItem.getValue() != null) ? (nroProposteDecretoInVerificaBilancioItem.getValue()) : 0);
		
		// Il tasto “Annulla proposte” si abilita SOLO se
		//	almeno uno dei 4 campi "N° proposte…" è > 0 e almeno uno dei check relativi ad un campo “N° proposte…” è spuntato
		if (nroProposteRda > 0 || nroProposteDecretoInFaseIstruttoria > 0 || nroProposteDecretoInFasePerfezionamento > 0 || nroProposteDecretoInVerificaBilancio > 0) {			
			if ( (flgAnnullaConRilevContabFaseIstrutNoValueItem.getValue() != null          && (Boolean) flgAnnullaConRilevContabFaseIstrutNoValueItem.getValue())          ||
				 (flgAnnullaConRilevContabFaseIstrutSiValueItem.getValue() != null          && (Boolean) flgAnnullaConRilevContabFaseIstrutSiValueItem.getValue())          ||
				 (flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.getValue() != null && (Boolean) flgAnnullaConRilevContabFaseIstrutSiSenzaMovContabItem.getValue()) ||
				 (flgAnnullaConRilevContabFasePerfezNoValueItem.getValue() != null          && (Boolean) flgAnnullaConRilevContabFasePerfezNoValueItem.getValue())          ||
				 (flgAnnullaConRilevContabFasePerfezSiValueItem.getValue() != null          && (Boolean) flgAnnullaConRilevContabFasePerfezSiValueItem.getValue())          ||
				 (flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.getValue() != null && (Boolean) flgAnnullaConRilevContabFasePerfezSiSenzaMovContabItem.getValue()) ||
				 (flgProposteRdaDaAnnullareItem.getValue() != null                          && (Boolean) flgProposteRdaDaAnnullareItem.getValue())
				)
			{
				disabilitaAnnullaProposteButton = false;
			}
		}
		window.disabilitaAnnullaProposteButton(disabilitaAnnullaProposteButton);
	}
		
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		initCheckBox();
		disabilitaAnnullaProposteButton();
	}
	
	private void checkBoxOnChanged(){
		disabilitaAnnullaProposteButton();
		markForRedraw();
	}
}
