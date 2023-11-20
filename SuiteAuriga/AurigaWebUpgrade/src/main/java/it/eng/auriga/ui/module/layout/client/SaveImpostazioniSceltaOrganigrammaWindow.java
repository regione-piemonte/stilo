/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author FEBUONO
 *
 */

public class SaveImpostazioniSceltaOrganigrammaWindow extends ModalWindow {
	
	// SEZIONE NASCONDI UTENTI IN ORGANIGRAMMA QUANDO RICHIAMATO PER SELEZIONE
	private DynamicForm nascondiUtentiForm;
	private DetailSection flgNascondiSVDetailSection;
	private CheckboxItem flgNascondiSVDestItem;
	private CheckboxItem flgNascondiSVMittItem;
	private CheckboxItem flgNascondiSVAssegnaItem;
	private CheckboxItem flgNascondiSVInviiCCItem;
	
	private ValuesManager vm;

	public SaveImpostazioniSceltaOrganigrammaWindow() {
		super("config_utente_sceltaDaOrganigramma", true);
		
		vm = new ValuesManager();
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setTitle(I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_title());
		setIcon("menu/organigramma.png");

		setWidth(600);
		setHeight(400);		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
	
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		buildFormNascondiUtenti();
		flgNascondiSVDetailSection = new DetailSection(I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_nascondiUtentiSection(), true, true, false, nascondiUtentiForm);
		layout.addMember(flgNascondiSVDetailSection);
		layout.addMember(spacerLayout);
		
		portletLayout.addMember(layout);
		
		Button okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				vm.clearErrors(true);				
				Record record = new Record(vm.getValues());
				manageOnOkButtonClick(record);
				markForDestroy();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
	
		portletLayout.addMember(_buttons);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});

		addItem(portletLayout);
		
	}
	
	private void buildFormNascondiUtenti() {
		nascondiUtentiForm = new DynamicForm();
		nascondiUtentiForm.setTitle(I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_nascondiUtentiSection());
		nascondiUtentiForm.setKeepInParentRect(true);
		nascondiUtentiForm.setWrapItemTitles(false);
		nascondiUtentiForm.setNumCols(5);
		nascondiUtentiForm.setColWidths(10, 10, 10, 10, "*");
		nascondiUtentiForm.setPadding(5);
		nascondiUtentiForm.setAlign(Alignment.LEFT);
		nascondiUtentiForm.setTop(50);
		nascondiUtentiForm.setValuesManager(vm);
		
		flgNascondiSVDestItem = new CheckboxItem("flgNascondiSVDest", I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_destinatariInterniRegistrazioniCheckBox());
		flgNascondiSVDestItem.setStartRow(true);
		
		flgNascondiSVMittItem = new CheckboxItem("flgNascondiSVMitt", I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_mittentiInterniRegistrazioniCheckBox());
		flgNascondiSVMittItem.setStartRow(true);
		
		flgNascondiSVAssegnaItem = new CheckboxItem("flgNascondiSVAssegna", I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_assegnatariCheckBox());
		flgNascondiSVAssegnaItem.setStartRow(true);
		
		flgNascondiSVInviiCCItem = new CheckboxItem("flgNascondiSVInviiCC", I18NUtil.getMessages().configUtenteMenuSceltaOrganigramma_destinatariInviiPerConoscenzaCheckBox());
		flgNascondiSVInviiCCItem.setStartRow(true);
		
		nascondiUtentiForm.setFields(flgNascondiSVDestItem,
										flgNascondiSVMittItem,
										flgNascondiSVAssegnaItem,
										flgNascondiSVInviiCCItem);
	}

	public void clearValues() {
		if (nascondiUtentiForm != null) {
			nascondiUtentiForm.clearValues();
		}
	}

	public void setValues(Record values) {
		if (values != null) {
			vm.editRecord(values);
		} else {
			vm.editNewRecord();
		}
		vm.clearErrors(true);
	}

	public void manageOnOkButtonClick(Record values) {
		
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
}