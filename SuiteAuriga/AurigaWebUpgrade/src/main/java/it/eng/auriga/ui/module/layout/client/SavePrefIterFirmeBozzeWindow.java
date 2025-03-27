/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public class SavePrefIterFirmeBozzeWindow extends ModalWindow {
	
	private ValuesManager vm;
	private DynamicForm _form;
	
	
	private PrefIterFirmeBozzeItem prefIterFirmeBozzeItem;
	
	private Button okButton;
	
	public SavePrefIterFirmeBozzeWindow(String nomeEntita) {
		
		super(nomeEntita, true);

		setTitle(I18NUtil.getMessages().configUtenteMenuPreferenzaFirmeBozze_title());
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		setWidth(700);
		setHeight(390);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		this.vm = new ValuesManager();
		
		createIterFirmeBozze();

		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {
					vm.clearErrors(true);				
					Record record = new Record(vm.getValues());
					manageOnOkButtonClick(record);
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

		layout.addMember(_form);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);
	}	
	
	private void createIterFirmeBozze() {
		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWrapItemTitles(false);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(5);
		_form.setColWidths(10, 10, 10, 10, "*");
		_form.setCellPadding(7);
		_form.setCanSubmit(true);
		_form.setAlign(Alignment.LEFT);
		_form.setTop(50);
		_form.setValuesManager(vm);
		
		prefIterFirmeBozzeItem = new PrefIterFirmeBozzeItem();
		prefIterFirmeBozzeItem.setName("listaPrefIterBozza");
		prefIterFirmeBozzeItem.setShowTitle(false);
		prefIterFirmeBozzeItem.setStartRow(true);
		
		_form.setFields(prefIterFirmeBozzeItem);
	}
	
	public void clearValues() {
		if(_form != null) {
			_form.clearValues();
		}
	}
	
	public void setValues(Record values) {
		if (values != null) {	
			RecordList listaPrefIterBozza = values.getAttributeAsRecordList("listaPrefIterBozza");
			if(listaPrefIterBozza == null || listaPrefIterBozza.getLength() == 0) {
				listaPrefIterBozza = new RecordList();
				listaPrefIterBozza.add(new Record());
				values.setAttribute("listaPrefIterBozza", listaPrefIterBozza);
			}
			vm.editRecord(values);
		} else {
			RecordList listaPrefIterBozza = new RecordList();
			listaPrefIterBozza.add(new Record());
			Map<String, Object> initialValues = new HashMap<String, Object>();
			initialValues.put("listaPrefIterBozza", listaPrefIterBozza);
			vm.editNewRecord(initialValues);
		}
		vm.clearErrors(true);
	}
	
	public void manageOnOkButtonClick(Record values) {

	}

}
