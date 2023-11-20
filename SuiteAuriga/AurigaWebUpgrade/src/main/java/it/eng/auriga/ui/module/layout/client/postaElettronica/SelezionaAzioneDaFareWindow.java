/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SelezionaAzioneDaFareWindow extends ModalWindow {

	protected SelezionaAzioneDaFareWindow _window;
	
	private final int ALT_POPUP_ERR_MASS = 300;
	private final int LARG_POPUP_ERR_MASS = 600;

	private DynamicForm selezionaAzioneDaFareForm;

	private boolean isMassive;
	private CustomLayout postaElettonicaLayout;
	private DettaglioPostaElettronica dettaglioPostaElettronica;
	private CheckboxItem lCheckboxRilascia;

	public SelezionaAzioneDaFareWindow(boolean isMassive, RecordList listRecord, CustomLayout layout, CustomDetail detail) {
		super("seleziona_azione_da_fare", isMassive, true);

		_window = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setKeepInParentRect(true);
		setTitle("Seleziona azione da fare");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);

		setMassive(isMassive);

		if (detail != null) {
			setDettaglioPostaElettronica((DettaglioPostaElettronica) detail);

		} else if (layout != null) {
			setPostaElettonicaLayout(layout);
		}

		selezionaAzioneDaFareForm = createForm(listRecord);
		selezionaAzioneDaFareForm.setAlign(Alignment.CENTER);

		addItem(selezionaAzioneDaFareForm);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setShowTitle(true);
		setHeaderIcon("menu/elenchiAlbi.png");

	}

	private DynamicForm createForm(final RecordList listaRecords) {

		final DynamicForm form = new DynamicForm();
		form.setWidth(500);
		form.setNumCols(4);

		final SelectItem selezionaAzioneItem = new SelectItem();
		selezionaAzioneItem.setTitle("Seleziona azione");
		selezionaAzioneItem.setShowTitle(true);
		selezionaAzioneItem.setWidth(650);
		selezionaAzioneItem.setValueField("key");
		selezionaAzioneItem.setDisplayField("value");
		GWTRestDataSource azioneDaFareSelectDataSource = new GWTRestDataSource("AzioneDaFareSelectDataSource");
		azioneDaFareSelectDataSource.addParam("skipAction", "true");
//		azioneDaFareSelectDataSource.addParam("flgsolovldin", "true");

		selezionaAzioneItem.setOptionDataSource(azioneDaFareSelectDataSource);

		if (listaRecords.get(0).getAttributeAsString("codAzioneDaFare") != null && !listaRecords.get(0).getAttributeAsString("codAzioneDaFare").equals("")
				&& !isMassive) {
			selezionaAzioneItem.setDefaultValue(listaRecords.get(0).getAttributeAsString("codAzioneDaFare"));

		}

		selezionaAzioneItem.setAllowEmptyValue(true);

		selezionaAzioneItem.setEndRow(true);

		final TextAreaItem dettaglioAreaItem = new TextAreaItem("dettaglioItem");
		dettaglioAreaItem.setTitle("Dettaglio");
		dettaglioAreaItem.setShowTitle(true);
		if (listaRecords.get(0).getAttributeAsString("dettaglioAzioneDaFare") != null
				&& !listaRecords.get(0).getAttributeAsString("dettaglioAzioneDaFare").equals("") && !isMassive) {
			dettaglioAreaItem.setDefaultValue(listaRecords.get(0).getAttributeAsString("dettaglioAzioneDaFare"));
			dettaglioAreaItem.setVisible(true);
		} else {
			dettaglioAreaItem.setDefaultValue("");
			dettaglioAreaItem.setVisible(false);
		}
		dettaglioAreaItem.setHeight(160);
		dettaglioAreaItem.setWidth(650);
		dettaglioAreaItem.setEndRow(true);

		selezionaAzioneItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (event.getValue() == null) {
					dettaglioAreaItem.hide();
					dettaglioAreaItem.setValue("");
					selezionaAzioneItem.setDefaultValue("");
				} else {
					dettaglioAreaItem.show();
				}

			}
		});

		ButtonItem saveAzioneButtonItem = new ButtonItem();
		saveAzioneButtonItem.setTitle(I18NUtil.getMessages().savePreferenceButton_title());
		saveAzioneButtonItem.setIcon("ok.png");
		saveAzioneButtonItem.setIconHeight(16);
		saveAzioneButtonItem.setIconWidth(16);
		saveAzioneButtonItem.setColSpan(4);
		saveAzioneButtonItem.setWidth(100);
		saveAzioneButtonItem.setTop(20);
		saveAzioneButtonItem.setAlign(Alignment.CENTER);

		saveAzioneButtonItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (selezionaAzioneDaFareForm.validate()) {

					final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AzioneDaFareDataSource");
					final Record record = new Record();
					record.setAttribute("listaRecord", listaRecords);
					record.setAttribute("codAzioneDaFare", selezionaAzioneItem.getValue());
					record.setAttribute("dettaglioAzioneDaFare", dettaglioAreaItem.getValue());

					if (getPostaElettonicaLayout() != null) {
						if (isMassive()) {
							record.setAttribute("flgRilascia", "1");

							try {
								lGwtRestDataSource.executecustom("aggiornaAzioneDaFare", record, new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										massiveAzioneDaFareCallback(response, listaRecords, "idEmail", "id", 
												I18NUtil.getMessages().posta_elettronica_setta_azione_da_fare_successo(),
												I18NUtil.getMessages().posta_elettronica_setta_azione_da_fare_errore_totale(),
												I18NUtil.getMessages().posta_elettronica_setta_azione_da_fare_errore_parziale(),
												null);
										_window.closePortlet();

									}
								});
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						} else {
							Boolean flg = (Boolean) lCheckboxRilascia.getValue();

							if (flg != null && flg.booleanValue())
								record.setAttribute("flgRilascia", "2");
							else
								record.setAttribute("flgRilascia", "0");

							lGwtRestDataSource.executecustom("aggiornaAzioneDaFare", record, new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
										if (errorMessages.size() > 0) {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_setta_azione_da_fare_errore(), "", MessageType.ERROR));
										} else {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_setta_azione_da_fare_successo(), "", MessageType.INFO));
											// Chiudo prima di ricaricare la lista
											_window.closePortlet();
											getPostaElettonicaLayout().reloadListAndSetCurrentRecord(record);
										}
									}
								}
							});
						}
					} else {
						Boolean flg = (Boolean) lCheckboxRilascia.getValue();

						if (flg != null && flg.booleanValue())
							record.setAttribute("flgRilascia", "2");
						else
							record.setAttribute("flgRilascia", "0");

						lGwtRestDataSource.executecustom("aggiornaAzioneDaFare", record, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
									if (errorMessages.size() > 0) {
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_setta_azione_da_fare_errore(), "", MessageType.ERROR));
									} else {
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().posta_elettronica_setta_azione_da_fare_successo(), "", MessageType.INFO));
										// Chiudo prima ricaricare la lista
										_window.closePortlet();
										getDettaglioPostaElettronica().reloadDetail();
									}
								}
							}
						});
					}
				}
			}
		});

		dettaglioAreaItem.setEndRow(true);
		SpacerItem lSpacerItem = new SpacerItem();
		lSpacerItem.setColSpan(1);
		lSpacerItem.setStartRow(true);
		lCheckboxRilascia = new CheckboxItem("flgRilascia", "Rilascia al termine dell’operazione");
		lCheckboxRilascia.setWrapTitle(false);
		lCheckboxRilascia.setColSpan(1);
		lCheckboxRilascia.setWidth("*");
		lCheckboxRilascia.setEndRow(true);
		lCheckboxRilascia.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (isMassive)
					return false;

				return true;
			}
		});

		form.setWidth100();
		form.setHeight100();
		form.setNumCols(2);
		form.setColWidths(120, 120);
		form.setCellPadding(5);
		form.setCanSubmit(true);
		form.setItems(selezionaAzioneItem, dettaglioAreaItem, lSpacerItem, lCheckboxRilascia, saveAzioneButtonItem);
		form.setAlign(Alignment.CENTER);
		return form;
	}

	private void massiveAzioneDaFareCallback(DSResponse response, RecordList listaRecord, String pkField, String nameField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {

		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			int[] recordsToSelect = null;

			if (errorMessages != null && errorMessages.size() > 0) {
				RecordList listaErrori = new RecordList();
				recordsToSelect = new int[errorMessages.size()];
				if (listaRecord.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				int rec = 0;
				
				if ((errorMessages != null) && (!errorMessages.isEmpty())) {
					for (int i = 0; i < listaRecord.getLength(); i++) {
						Record record = listaRecord.get(i);
						if (errorMessages.get(record.getAttribute(pkField)) != null) {
							recordsToSelect[rec++] = postaElettonicaLayout.getList().getRecordIndex(record);
							Record recordErrore = new Record();
							recordErrore.setAttribute("idError", record.getAttribute(nameField));
							recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
							listaErrori.add(recordErrore);
						}
					}
				}
				if (listaErrori.getLength() > 0) {
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "N* email", listaErrori, listaRecord.getLength(),
							LARG_POPUP_ERR_MASS, ALT_POPUP_ERR_MASS);
					errorePopup.show();
				}
			}

			getPostaElettonicaLayout().doSearchAndSelectRecords(recordsToSelect);
			Layout.hideWaitPopup();
			if (errorMsg != null) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
				Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
		}
	}

	public boolean isMassive() {
		return isMassive;
	}

	public void setMassive(boolean isMassive) {
		this.isMassive = isMassive;
	}

	public CustomLayout getPostaElettonicaLayout() {
		return postaElettonicaLayout;
	}

	public void setPostaElettonicaLayout(CustomLayout postaElettonicaLayout) {
		this.postaElettonicaLayout = postaElettonicaLayout;
	}

	public DettaglioPostaElettronica getDettaglioPostaElettronica() {
		return dettaglioPostaElettronica;
	}

	public void setDettaglioPostaElettronica(DettaglioPostaElettronica dettaglioPostaElettronica) {
		this.dettaglioPostaElettronica = dettaglioPostaElettronica;
	}

}