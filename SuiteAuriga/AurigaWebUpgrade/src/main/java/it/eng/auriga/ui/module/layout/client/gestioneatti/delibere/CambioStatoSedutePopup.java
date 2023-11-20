/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public class CambioStatoSedutePopup extends ModalWindow {
	
	private ValuesManager vm;
	protected DynamicForm mDynamicForm;
	private SelectItem statoSeduteItem;
	
	private Button okButton;

	public CambioStatoSedutePopup(Map<String,String> mapStati) {
		super("cambio_stato_sedute");
		
		setTitle("Selezione nuovo stato");
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		setWidth(500);
		setHeight(150);
		
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
		
		statoSeduteItem = new SelectItem();
		statoSeduteItem.setName("stato");
		statoSeduteItem.setTitle("Stato");
		statoSeduteItem.setShowTitle(true);
		statoSeduteItem.setColSpan(1);
		statoSeduteItem.setWidth(300);
		statoSeduteItem.setAlign(Alignment.CENTER);
		statoSeduteItem.setValueMap(mapStati);
		statoSeduteItem.setAllowEmptyValue(false);
		statoSeduteItem.setRequired(true);
		statoSeduteItem.setDefaultToFirstOption(mapStati != null && mapStati.size() == 1);
		
		mDynamicForm.setItems(statoSeduteItem);
		
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

		layout.addMember(mDynamicForm);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		setBody(portletLayout);
		
	}
	
	public void manageOnOkButtonClick(Record values) {

	}

}