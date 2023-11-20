/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class SaveCodRapidoOrganigrammaWindow extends Window {

	private DynamicForm mDynamicForm;
	private ExtendedTextItem codRapidoItem;

	public SaveCodRapidoOrganigrammaWindow() {

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setShowTitle(true);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle(I18NUtil.getMessages().configUtenteMenuCodRapidoOrganigramma_title());
		setHeaderIcon("menu/organigramma.png");

		setAutoCenter(true);
		setWidth(300);
		setHeight(120);

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

		ImgButtonItem lookupOrganigrammaButton = new ImgButtonItem("lookupOrganigrammaButton", "lookup/organigramma.png", I18NUtil.getMessages()
				.protocollazione_detail_lookupOrganigrammaButton_prompt());
		lookupOrganigrammaButton.setColSpan(1);
		lookupOrganigrammaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				OrganigrammaLookupOrganigramma lookupOrganigrammaPopup = new OrganigrammaLookupOrganigramma(null);
				lookupOrganigrammaPopup.show();
			}
		});

		codRapidoItem = new ExtendedTextItem("codRapido", "Cod. rapido");
		codRapidoItem.setWidth(120);
		codRapidoItem.setColSpan(1);

		mDynamicForm.setItems(lookupOrganigrammaButton, codRapidoItem);

		Button okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				mDynamicForm.clearErrors(true);
				final String value = codRapidoItem.getValueAsString();
				if (value != null && !"".equals(value)) {
					AurigaLayout.showWaitPopup("Validazione in corso...");
					SelectGWTRestDataSource organigrammaDS = new SelectGWTRestDataSource("SelectOrganigrammaDatasource", "id", FieldType.TEXT,
							new String[] { "descrizione" }, true);
					organigrammaDS.addParam("flgNodoType", "UO");
					organigrammaDS.addParam("codice", value);
					organigrammaDS.fetchData(null, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							AurigaLayout.hideWaitPopup();
							boolean trovato = false;
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								RecordList data = response.getDataAsRecordList();
								if (data.getLength() > 0) {
									for (int i = 0; i < data.getLength(); i++) {
										String codice = data.get(i).getAttribute("codice");
										if (value.equals(codice)) {
											trovato = true;
											break;
										}
									}
								}
							}
							if (!trovato) {
								mDynamicForm.setFieldErrors("codRapido", "Valore non valido");
							} else {
								manageOnOkButtonClick((String) mDynamicForm.getValue("codRapido"));
								hide();
							}
						}
					});
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

		addItem(portletLayout);
	}

	public void clearValues() {
		mDynamicForm.clearValues();
	}

	public void setValue(String value) {
		mDynamicForm.setValue("codRapido", value);
		mDynamicForm.clearErrors(true);
	}

	public void manageOnOkButtonClick(String value) {

	}

	public class OrganigrammaLookupOrganigramma extends LookupOrganigrammaPopup {

		public OrganigrammaLookupOrganigramma(Record record) {
			super(record, true, new Integer(0));
		}

		@Override
		public void manageLookupBack(Record record) {
			mDynamicForm.setValue("codRapido", record.getAttribute("codRapidoUo"));
			mDynamicForm.clearErrors(true);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

	}

}