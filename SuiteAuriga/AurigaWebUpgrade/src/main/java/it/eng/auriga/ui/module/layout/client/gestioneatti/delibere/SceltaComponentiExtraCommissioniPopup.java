/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SceltaComponentiExtraCommissioniPopup extends ModalWindow {

	private ValuesManager vm;
	protected DynamicForm mDynamicForm;
	private SelectItem componentiExtraItem;
	private Button okButton;

	public SceltaComponentiExtraCommissioniPopup(String idSeduta) {
		super("scelta_componenti_extra_commissioni");

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona componente/i da aggiungere");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		this.vm = new ValuesManager();
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(2);
		mDynamicForm.setColWidths(200, 200);
		mDynamicForm.setCellPadding(5);
		mDynamicForm.setCanSubmit(true);
		mDynamicForm.setAlign(Alignment.CENTER);
		mDynamicForm.setTop(50);
		mDynamicForm.setValuesManager(vm);

		GWTRestDataSource componentiExtraDS = new GWTRestDataSource("LoadComboComponentiExtraCommissioniDataSource", "key", FieldType.TEXT);
		componentiExtraDS.extraparam.put("idSeduta", idSeduta);
		
		componentiExtraItem = new SelectItem("listaComponentiExtra", "Componenti Extra");
		componentiExtraItem.setOptionDataSource(componentiExtraDS);
		componentiExtraItem.setValueField("idUser");
		componentiExtraItem.setDisplayField("denominazione");
		componentiExtraItem.setShowTitle(false);
		componentiExtraItem.setWidth(300);
		componentiExtraItem.setColSpan(2);
		componentiExtraItem.setAutoFetchData(false);
		componentiExtraItem.setAlign(Alignment.CENTER);
		componentiExtraItem.setRequired(false);
		componentiExtraItem.setMultiple(true);
		
		mDynamicForm.setItems(componentiExtraItem);

		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {
					vm.clearErrors(true);	
					ListGridRecord[] selectedRecords = componentiExtraItem.getSelectedRecords();
					manageOnOkButtonClick(selectedRecords);
					markForDestroy();
				}
			}
		});

		setShowTitle(true);
		setHeaderIcon("menu/commissione.png");
		
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
	
	public void manageOnOkButtonClick(ListGridRecord[] selectedRecords) {

	}
	
}