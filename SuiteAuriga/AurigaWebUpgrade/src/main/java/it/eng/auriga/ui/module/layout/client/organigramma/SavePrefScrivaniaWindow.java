/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class SavePrefScrivaniaWindow extends ModalWindow {

	private ValuesManager vm;
	private DynamicForm mDynamicForm;
	private RadioGroupItem soloAUOLavoroItem;
	private Button okButton;

	public SavePrefScrivaniaWindow(String nomeEntita) {
		
		super(nomeEntita, true);

		setTitle(I18NUtil.getMessages().configUtenteMenuPreferenzaScrivania_title());
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		setWidth(800);
		setHeight(125);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		this.vm = new ValuesManager();
		
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
		
		soloAUOLavoroItem = new RadioGroupItem("soloAUOLavoro");
		soloAUOLavoroItem.setTitle(I18NUtil.getMessages().configUtenteMenuPreferenzaScrivania_mostradoc_title());
		soloAUOLavoroItem.setVertical(false);
		soloAUOLavoroItem.setStartRow(true);
		soloAUOLavoroItem.setWrap(false);
		Map<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("true", "solo assegnata/inviata alla U.O. di lavoro selezionata");
		valueMap.put("false", "assegnata/inviata a qualsiasi struttura cui sono collegato");
		soloAUOLavoroItem.setValueMap(valueMap);
		soloAUOLavoroItem.setDefaultValue(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RESTR_SEZ_SCRIV_A_UO_LAVORO"));
		
		mDynamicForm.setItems(soloAUOLavoroItem);

		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (mDynamicForm.validate()) {
					manageOnOkButtonClick(new Record(vm.getValues()));
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
	
	public void manageOnOkButtonClick(Record values) {

	}

}