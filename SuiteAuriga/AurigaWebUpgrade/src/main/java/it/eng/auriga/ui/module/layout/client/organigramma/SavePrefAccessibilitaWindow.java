/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class SavePrefAccessibilitaWindow extends ModalWindow {

	private ValuesManager vm;
	private DynamicForm mDynamicForm;
	private CheckboxItem abilitaAccessibilita;
	private HiddenItem mostraModal;
	private HiddenItem nascondiPreferitiOnToolBar;
	private CheckboxItem abilitaAperturaRicercaPuntuale;
	private Button okButton;

	public SavePrefAccessibilitaWindow(String nomeEntita) {
		
		super(nomeEntita, true);

		setTitle(I18NUtil.getMessages().configUtenteMenuPreferenzeAccessibilita_title());
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		setWidth(700);
		setHeight(120);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		this.vm = new ValuesManager();
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setHeight(50);;
		spacerItem.setStartRow(true);
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths(10, 10, 10, 10, "*");
		mDynamicForm.setCellPadding(7);
		mDynamicForm.setCanSubmit(true);
		mDynamicForm.setAlign(Alignment.LEFT);
		mDynamicForm.setTop(50);
		mDynamicForm.setValuesManager(vm);
		mostraModal = new HiddenItem("mostraModal");
		nascondiPreferitiOnToolBar = new HiddenItem("nascondiPreferitiOnToolBar");
		
		
		abilitaAccessibilita = new CheckboxItem("abilitaAccessibilita", "Navigazione ottimizzata per screen-reader");
		abilitaAccessibilita.setStartRow(true);
		abilitaAccessibilita.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		abilitaAperturaRicercaPuntuale = new CheckboxItem("apriDettaglioRicercaPuntuale", "Apertura automatica dettaglio documento in caso di lista con un record");
		abilitaAperturaRicercaPuntuale.setStartRow(true);
		abilitaAperturaRicercaPuntuale.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});
		
		
		mDynamicForm.setItems(nascondiPreferitiOnToolBar,mostraModal,abilitaAccessibilita,abilitaAperturaRicercaPuntuale);

		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (mDynamicForm.validate()) {
					Record impostazioniSceltaAccessibilita = new Record ();
					Object isAbilitaAccessibilita = vm.getValue("abilitaAccessibilita");
					// IMPOSTO AL VALORE DELLA SCELTA DELL'ACCESSIBILITA ANCHE AGLI ALTRI DUE PARAM NASCOSTI PERCHE' SERVONO PER LA CORRETTA VISUALIZZAZIONE
//					impostazioniSceltaAccessibilita.setAttribute("nascondiPreferitiOnToolBar", vm.getValue("nascondiPreferitiOnToolBar"));
//					impostazioniSceltaAccessibilita.setAttribute("mostraModal", vm.getValue("mostraModal"));
					impostazioniSceltaAccessibilita.setAttribute("nascondiPreferitiOnToolBar", isAbilitaAccessibilita);
					impostazioniSceltaAccessibilita.setAttribute("mostraModal", isAbilitaAccessibilita);
					impostazioniSceltaAccessibilita.setAttribute("abilitaAccessibilita", isAbilitaAccessibilita);
					Record impostazioniAperturaDettaglioPerRicercaPuntuale = new Record ();
					impostazioniAperturaDettaglioPerRicercaPuntuale.setAttribute("apriDettaglioRicercaPuntuale", vm.getValue("apriDettaglioRicercaPuntuale"));
					manageOnOkButtonClick(impostazioniSceltaAccessibilita,impostazioniAperturaDettaglioPerRicercaPuntuale);
					markForDestroy();
				}
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(mDynamicForm);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);
	}

	public void clearValues() {
		mDynamicForm.clearValues();
	}
	
	public void setValues(Record values) {
		if (values != null) {
			mDynamicForm.editRecord(values);
		} else {
			mDynamicForm.editNewRecord();
		}
		mDynamicForm.clearErrors(true);
	}
	
	public void manageOnOkButtonClick(Record impostazioniSceltaAccessibilitaRecord,Record impostazioniAperturaDettaglioPerRicercaPuntualeRecord) {

	}

}