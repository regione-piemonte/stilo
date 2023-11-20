/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SaveUoLavoroWindow extends ModalWindow {

	private DynamicForm mDynamicForm;

	private SelectItem uoLavoroSelectItem;
	private Button okButton;

	public SaveUoLavoroWindow(String nomeEntita,LinkedHashMap<String, String> uoCollegateUtenteValueMap) {
		
		super(nomeEntita, true);

		setTitle(I18NUtil.getMessages().configUtenteMenuUoLavoro_title());
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		setWidth(700);
		setHeight(120);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

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

		uoLavoroSelectItem = new SelectItem();
		uoLavoroSelectItem.setName("uoLavoro");
		uoLavoroSelectItem.setShowTitle(false);
		uoLavoroSelectItem.setColSpan(2);
		uoLavoroSelectItem.setWidth(680);
		uoLavoroSelectItem.setAlign(Alignment.CENTER);
		uoLavoroSelectItem.setDisplayField("descrizione");
		uoLavoroSelectItem.setValueField("idUo");
		uoLavoroSelectItem.setValueMap(uoCollegateUtenteValueMap);
		if (AurigaLayout.getParametroDBAsBoolean("SCELTA_UO_LAVORO_OBBLIGATORIA")) {
			uoLavoroSelectItem.setRequired(true);
		} else {
			uoLavoroSelectItem.setAllowEmptyValue(true);
		}

		mDynamicForm.setItems(uoLavoroSelectItem);

		okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (mDynamicForm.validate()) {
					manageOnOkButtonClick((String) mDynamicForm.getValue("uoLavoro"));
					hide();
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

	public void setValueMap(LinkedHashMap<String, String> uoCollegateUtenteValueMap) {
		uoLavoroSelectItem.setValueMap(uoCollegateUtenteValueMap);
	}

	public void setValue(String value) {
		mDynamicForm.setValue("uoLavoro", value);
		mDynamicForm.clearErrors(true);
	}

	public void manageOnOkButtonClick(String value) {

	}

}