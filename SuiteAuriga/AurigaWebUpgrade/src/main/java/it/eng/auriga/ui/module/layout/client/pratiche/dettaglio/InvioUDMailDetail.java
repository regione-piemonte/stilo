/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupRubricaEmailPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioUD.AttachmentDownloadAction;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDAttachmentItem;
import it.eng.auriga.ui.module.layout.client.invioUD.InvioUDDestinatariItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.events.HiddenValidationErrorsEvent;
import com.smartgwt.client.widgets.form.events.HiddenValidationErrorsHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

public class InvioUDMailDetail extends CustomDetail {

	private String tipoMail;

	private DynamicForm form;

	private SelectItem lSelectItemMittente;
	private ExtendedTextItem lTextItemDestinatari;
	private ImgButtonItem lookupRubricaEmailButtonDestinatari;
	private ExtendedTextItem lTextItemDestinatariCC;
	private ImgButtonItem lookupRubricaEmailButtonDestinatariCC;
	// private ExtendedTextItem lTextItemDestinatariCCN;
	// private ImgButtonItem lookupRubricaEmailButtonDestinatariCCN;
	private TextItem lTextItemOggetto;
	private CheckboxItem lCheckboxItemSalvaInviati;
	private CheckboxItem lCheckboxRichiestaConferma;
	private HiddenItem lHiddenItemConfermaLetturaShow;
	private CheckboxItem lCheckboxConfermaLettura;
	private TextAreaItem lTextAreaItemBody;
	private TextAreaItem lTextAreaItemAvvertimenti;
	private InvioUDAttachmentItem lAttachmentItem;
	private HiddenItem lHiddenItemSegnaturaPresente;
	private HiddenItem lHiddenItemIdUD;
	private HiddenItem lHiddenItemFlgTipoProv;
	private HiddenItem lHiddenItemTipoMail;
	private HiddenItem lHiddenItemIdMailPartenza;
	private InvioUDDestinatariItem lInvioUDDestinatariItem;

	public InvioUDMailDetail(String nomeEntita, String pTipoMail) {

		super(nomeEntita);

		this.tipoMail = pTipoMail;

		lHiddenItemSegnaturaPresente = new HiddenItem("segnaturaPresente");

		lHiddenItemIdUD = new HiddenItem("idUD");
		lHiddenItemFlgTipoProv = new HiddenItem("flgTipoProv");

		lHiddenItemTipoMail = new HiddenItem("tipoMail");

		lHiddenItemIdMailPartenza = new HiddenItem("idMailPartenza");

		// mittente
		lSelectItemMittente = new SelectItem("mittente", "Mittente");
		lSelectItemMittente.setStartRow(true);
		lSelectItemMittente.setEndRow(true);
		lSelectItemMittente.setTitleColSpan(2);
		lSelectItemMittente.setColSpan(3);
		lSelectItemMittente.setDisplayField("value");
		lSelectItemMittente.setValueField("key");
		lSelectItemMittente.setRequired(true);
		lSelectItemMittente.setAllowEmptyValue(false);
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		accounts.addParam("finalita", "INVIO");
		accounts.addParam("tipoMail", tipoMail);
		lSelectItemMittente.setOptionDataSource(accounts);
		lSelectItemMittente.setDefaultToFirstOption(true);
		// lSelectItemMittente.addDataArrivedHandler(new DataArrivedHandler() {
		// @Override
		// public void onDataArrived(DataArrivedEvent event) {
		// if (event.getData().getLength()==1){
		// String idMittente = event.getData().get(0).getAttribute("key");
		// vm.setValue("mittente", idMittente);
		// // if(tipoMail!=null && tipoMail.equals("PEC")) {
		// // retrieveInfoForConferma(idMittente);
		// // }
		// }
		// }
		// });
		// lSelectItemMittente.addChangedHandler(new ChangedHandler() {
		// @Override
		// public void onChanged(ChangedEvent event) {
		// if(tipoMail!=null && tipoMail.equals("PEC")) {
		// retrieveInfoForConferma((String)event.getValue());
		// }
		// }
		// });

		// Bottone lookup per aprire la RUBRICA EMAIL
		lookupRubricaEmailButtonDestinatari = new ImgButtonItem("lookupRubricaEmailButtonDestinatari", "lookup/rubricaemail.png", I18NUtil.getMessages()
				.invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailButtonDestinatari.setEndRow(false);
		lookupRubricaEmailButtonDestinatari.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioUDMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioUDMailMultiLookupRubricaEmailPopup("destinatari");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailButtonDestinatari.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEO");
			}
		});
		
		// destinatario
		lTextItemDestinatari = new ExtendedTextItem("destinatari", "A");
		lTextItemDestinatari.setColSpan(1);
		lTextItemDestinatari.setEndRow(true);
		lTextItemDestinatari.setWidth(480);
		lTextItemDestinatari.setRequired(true);
		lTextItemDestinatari.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEO");
			}
		});
		lTextItemDestinatari.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEO");
			}
		}), getValidatorEmail());
		// lTextItemDestinatari.setValidateOnChange(true);
		lTextItemDestinatari.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				lTextItemDestinatari.validate();
			}
		});

		// Bottone lookup per aprire la RUBRICA EMAIL
		lookupRubricaEmailButtonDestinatariCC = new ImgButtonItem("lookupRubricaEmailButtonDestinatariCC", "lookup/rubricaemail.png", I18NUtil.getMessages()
				.invioudmail_detail_lookupRubricaEmailItem_title());
		lookupRubricaEmailButtonDestinatariCC.setEndRow(false);
		lookupRubricaEmailButtonDestinatariCC.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				InvioUDMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioUDMailMultiLookupRubricaEmailPopup("destinatariCC");
				lookupRubricaEmailPopup.show();
			}
		});
		lookupRubricaEmailButtonDestinatariCC.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEO");
			}
		});

		// destinatario CC
		lTextItemDestinatariCC = new ExtendedTextItem("destinatariCC", "CC");
		lTextItemDestinatariCC.setColSpan(1);
		lTextItemDestinatariCC.setEndRow(true);
		lTextItemDestinatariCC.setWidth(480);
		lTextItemDestinatariCC.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEO");
			}
		});
		lTextItemDestinatariCC.setValidators(getValidatorEmail());
		// lTextItemDestinatariCC.setValidateOnChange(true);
		lTextItemDestinatariCC.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				lTextItemDestinatariCC.validate();
			}
		});

		// Bottone lookup per aprire la RUBRICA EMAIL - Deve essere tolto nel form dell'invio di una mail
		// lookupRubricaEmailButtonDestinatariCCN = new ImgButtonItem("lookupRubricaEmailButtonDestinatariCCN", "lookup/rubricaemail.png",
		// I18NUtil.getMessages().invioudmail_detail_lookupRubricaEmailItem_title());
		// lookupRubricaEmailButtonDestinatariCCN.setEndRow(false);
		// lookupRubricaEmailButtonDestinatariCCN.addIconClickHandler(new IconClickHandler() {
		// @Override
		// public void onIconClick(IconClickEvent event) {
		// InvioUDMailMultiLookupRubricaEmailPopup lookupRubricaEmailPopup = new InvioUDMailMultiLookupRubricaEmailPopup("destinatariCCN");
		// lookupRubricaEmailPopup.show();
		// }
		// });
		// lookupRubricaEmailButtonDestinatariCCN.setShowIfCondition(new FormItemIfFunction() {
		// @Override
		// public boolean execute(FormItem item, Object value, DynamicForm form) {
		// 
		// return form.getValueAsString("tipoMail")!=null && form.getValueAsString("tipoMail").equals("PEO");
		// }
		// });

		// destinatario CCN - Deve essere tolto nel form dell'invio di una mail
		// lTextItemDestinatariCCN = new ExtendedTextItem("destinatariCCN", "CCN");
		// lTextItemDestinatariCCN.setColSpan(1);
		// lTextItemDestinatariCCN.setEndRow(true);
		// lTextItemDestinatariCCN.setWidth(480);
		// lTextItemDestinatariCCN.setShowIfCondition(new FormItemIfFunction() {
		// @Override
		// public boolean execute(FormItem item, Object value, DynamicForm form) {
		// 
		// return form.getValueAsString("tipoMail")!=null && form.getValueAsString("tipoMail").equals("PEO");
		// }
		// });
		// lTextItemDestinatariCCN.setValidators(lCustomValidator);
		// // lTextItemDestinatariCCN.setValidateOnChange(true);
		// lTextItemDestinatariCCN.addChangedBlurHandler(new ChangedHandler() {
		// @Override
		// public void onChanged(ChangedEvent event) {
		// lTextItemDestinatariCCN.validate();
		// }
		// });

		// oggetto
		lTextItemOggetto = new TextItem("oggetto", "Oggetto");
		lTextItemOggetto.setTitleColSpan(2);
		lTextItemOggetto.setStartRow(true);
		lTextItemOggetto.setEndRow(true);
		lTextItemOggetto.setWidth(480);

		// file attachment
		lAttachmentItem = new InvioUDAttachmentItem(tipoMail, new AttachmentDownloadAction() {

			@Override
			public void manageIconClick(String display, String uri, boolean remoteUri) {
				if (uri == null && display.equalsIgnoreCase("segnatura.xml")) {
					// Sono in segnatura
					downloadSegnatura(display);
				} else
					_manageIconClick(display, uri, remoteUri);
			}
		});
		lAttachmentItem.setName("attach");
		lAttachmentItem.setTitle("Allegati");
		lAttachmentItem.setTitleColSpan(2);
		lAttachmentItem.setWidth(1);
		lAttachmentItem.setEndRow(true);

		SpacerItem lSpacerBodyItem = new SpacerItem();
		lSpacerBodyItem.setColSpan(2);
		lSpacerBodyItem.setStartRow(true);

		// body
		lTextAreaItemBody = new TextAreaItem("body");
		lTextAreaItemBody.setShowTitle(false);
		lTextAreaItemBody.setEndRow(true);
		lTextAreaItemBody.setColSpan(1);
		lTextAreaItemBody.setHeight(60);
		lTextAreaItemBody.setWidth(650);
		lTextAreaItemBody.setVisible(true);

		// avvertimenti
		lTextAreaItemAvvertimenti = new TextAreaItem("avvertimenti",
				"&nbsp;&nbsp;&nbsp;<img src=\"images/warning.png\" style=\"vertical-align:bottom\"/>&nbsp;<font color=\"red\"><b>Avvertimenti<b/></font>");
		lTextAreaItemAvvertimenti.setStartRow(true);
		lTextAreaItemAvvertimenti.setEndRow(true);
		lTextAreaItemAvvertimenti.setTitleColSpan(2);
		lTextAreaItemAvvertimenti.setColSpan(3);
		lTextAreaItemAvvertimenti.setHeight(60);
		lTextAreaItemAvvertimenti.setWidth(650);
		lTextAreaItemAvvertimenti.setCanEdit(false);
		lTextAreaItemAvvertimenti.setVisible(true);
		lTextAreaItemAvvertimenti.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return value != null && !((String) value).trim().equals("");
			}
		});

		// LinkedHashMap<String, String> styleMap = new LinkedHashMap<String, String>();
		// styleMap.put("text", "Testo semplice");
		// styleMap.put("html", "Testo in HTML");

		SpacerItem lSpacerSalvaInviatiItem = new SpacerItem();
		lSpacerSalvaInviatiItem.setColSpan(2);
		lSpacerSalvaInviatiItem.setStartRow(true);
		lSpacerSalvaInviatiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return false;
			}
		});

		lCheckboxItemSalvaInviati = new CheckboxItem("salvaInviati", "salva mail negli inviati");
		lCheckboxItemSalvaInviati.setWrapTitle(false);
		// lCheckboxItemSalvaInviati.setLabelAsTitle(true);
		// lCheckboxItemSalvaInviati.setShowTitle(true);
		lCheckboxItemSalvaInviati.setColSpan(1);
		lCheckboxItemSalvaInviati.setWidth("*");
		lCheckboxItemSalvaInviati.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return false;
			}
		});

		SpacerItem lSpacerRichiestaConfermaItem = new SpacerItem();
		lSpacerRichiestaConfermaItem.setColSpan(2);
		lSpacerRichiestaConfermaItem.setStartRow(true);
		lSpacerRichiestaConfermaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				boolean segnaturaPresente = lHiddenItemSegnaturaPresente.getValue() != null ? (Boolean) lHiddenItemSegnaturaPresente.getValue() : false;
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEC") && segnaturaPresente;
			}
		});

		lCheckboxRichiestaConferma = new CheckboxItem("richiestaConferma", "richiesta conferma protocollazione");
		lCheckboxRichiestaConferma.setWrapTitle(false);
		// lCheckboxRichiestaConferma.setLabelAsTitle(true);
		// lCheckboxRichiestaConferma.setShowTitle(true);
		lCheckboxRichiestaConferma.setColSpan(1);
		lCheckboxRichiestaConferma.setWidth("*");
		lCheckboxRichiestaConferma.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				boolean segnaturaPresente = lHiddenItemSegnaturaPresente.getValue() != null ? (Boolean) lHiddenItemSegnaturaPresente.getValue() : false;
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEC") && segnaturaPresente;
			}
		});

		SpacerItem lSpacerConfermaLetturaItem = new SpacerItem();
		lSpacerConfermaLetturaItem.setColSpan(2);
		lSpacerConfermaLetturaItem.setStartRow(true);
		lSpacerConfermaLetturaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				boolean showConfermaLettura = form.getValue("confermaLetturaShow") != null ? (Boolean) form.getValue("confermaLetturaShow") : false;
				// return form.getValueAsString("tipoMail")!=null && form.getValueAsString("tipoMail").equals("PEO") && showConfermaLettura;
				return showConfermaLettura;
			}
		});

		lHiddenItemConfermaLetturaShow = new HiddenItem("confermaLetturaShow");

		lCheckboxConfermaLettura = new CheckboxItem("confermaLettura", "conferma di lettura");
		lCheckboxConfermaLettura.setWrapTitle(false);
		// lCheckboxConfermaLettura.setLabelAsTitle(true);
		// lCheckboxConfermaLettura.setShowTitle(true);
		lCheckboxConfermaLettura.setColSpan(1);
		lCheckboxConfermaLettura.setWidth("*");
		lCheckboxConfermaLettura.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				boolean showConfermaLettura = form.getValue("confermaLetturaShow") != null ? (Boolean) form.getValue("confermaLetturaShow") : false;
				// return form.getValueAsString("tipoMail")!=null && form.getValueAsString("tipoMail").equals("PEO") && showConfermaLettura;
				return showConfermaLettura;
			}
		});

		lInvioUDDestinatariItem = new InvioUDDestinatariItem();
		lInvioUDDestinatariItem.setName("destinatariPec");
		lInvioUDDestinatariItem.setTitle("Destinatari PEC");
		lInvioUDDestinatariItem.setWrapTitle(false);
		lInvioUDDestinatariItem.setCanEdit(true);
		lInvioUDDestinatariItem.setWidth(350);
		lInvioUDDestinatariItem.setTitleColSpan(2);
		lInvioUDDestinatariItem.setColSpan(4);
		lInvioUDDestinatariItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEC");
			}
		});

		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWidth("100%");
		form.setHeight("5");
		form.setPadding(5);
		form.setWrapItemTitles(false);
		form.setNumCols(8);
		form.setColWidths("5", "5", "5", "5", "5", "5", "5", "*");

		form.setFields(lSelectItemMittente, lInvioUDDestinatariItem,
				lookupRubricaEmailButtonDestinatari,
				lTextItemDestinatari,
				lookupRubricaEmailButtonDestinatariCC,
				lTextItemDestinatariCC,
				// lookupRubricaEmailButtonDestinatariCCN,
				// lTextItemDestinatariCCN,
				lTextItemOggetto,
				lAttachmentItem,
				lSpacerBodyItem,
				lTextAreaItemBody,
				// lSpacerSalvaInviatiItem,
				// lCheckboxItemSalvaInviati,
				lSpacerRichiestaConfermaItem, lCheckboxRichiestaConferma, lSpacerConfermaLetturaItem, lCheckboxConfermaLettura, lTextAreaItemAvvertimenti,
				lHiddenItemIdUD, lHiddenItemFlgTipoProv, lHiddenItemTipoMail, lHiddenItemIdMailPartenza, lHiddenItemSegnaturaPresente,
				lHiddenItemConfermaLetturaShow);

		addMember(form);

	}
	
	/**
	 * Validator per il campo email
	 */
	private CustomValidator getValidatorEmail() {
		CustomValidator lEmailRegExpValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(form.getValueAsString("tipoMail") != null && form.getValueAsString("tipoMail").equals("PEO")) {
					if (value == null || "".equals(value))
						return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					String lString = (String) value;
					String[] values = lString.split(";");
					boolean res = true;
					for (String val : values) {
						if (val == null || val.equals(""))
							res = res && true;
						else
							res = res && regExp.test(val.trim());
					}
					return res;
				}
				return true;
			}
		};
		lEmailRegExpValidator.setErrorMessage("Valore non valido. Gli indirizzi email devono essere separati da ';'");
		return lEmailRegExpValidator;
	}


	protected void downloadSegnatura(final String display) {
		Record lRecordMail = getRecord();
		if (lRecordMail != null) {
			GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaInvioUDMailDatasource");
			lGWTRestDataSource.executecustom("downloadSegnatura", lRecordMail, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record lRecord = response.getData()[0];
						String uri = lRecord.getAttribute("value");
						_manageIconClick(display, uri, false);
					}
				}
			});
		}
	}

	protected void _manageIconClick(String display, String uri, boolean remoteUri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri + "");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	// private void retrieveInfoForConferma(String idAccount){
	// GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
	// Record lRecord = new Record();
	// lRecord.setAttribute("value", idAccount);
	// accounts.addParam("tipoMail", tipoMail);
	// accounts.addParam("segnaturaPresente", lHiddenItemSegnaturaPresente.getValue()+"");
	// accounts.executecustom("retrieveInfoForConferma", lRecord, new DSCallback() {
	// @Override
	// public void execute(DSResponse response, Object rawData, DSRequest request) {
	// if (response.getStatus() == RPCResponse.STATUS_SUCCESS){
	// Record lRecord = response.getData()[0];
	// if (lRecord.getAttributeAsBoolean("value")){
	// lCheckboxRichiestaConferma.show();
	// } else lCheckboxRichiestaConferma.hide();
	//
	// } else {
	// lCheckboxRichiestaConferma.hide();
	// }
	// }
	// });
	// }

	public void caricaMail(Record lRecord) {
		vm.editRecord(lRecord);
		lInvioUDDestinatariItem.setAggiuntaMode();
		markForRedraw();
	}

	public Record getRecord() {
		if (validate()) {
			Record lRecord = new Record(vm.rememberValues());
			return lRecord;
		} else
			return null;
	}

	/*
	boolean valid = false;
	Map<String, String> lMapErroriNascosti = null;

	public Boolean validate() {
		vm.clearErrors(true);
		vm.addHiddenValidationErrorsHandler(new HiddenValidationErrorsHandler() {

			@Override
			public void onHiddenValidationErrors(HiddenValidationErrorsEvent event) {
				lMapErroriNascosti = event.getErrors();
			}
		});
		valid = true;
		vm.validate();
		Map<String, String> lMapErrori = vm.getErrors();
		if (lMapErrori != null) {
			for (String lString : lMapErrori.keySet()) {
				if (lMapErroriNascosti == null || !lMapErroriNascosti.containsKey(lString)) {
					valid = false;
				}
			}
		}
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item instanceof ReplicableItem) {
					ReplicableItem lReplicableItem = (ReplicableItem) item;
					if (lReplicableItem.isVisible())
						valid = lReplicableItem.validate() && valid;
				}
			}
		}
		return valid;
	}
	*/

	public void setlHiddenItemIdMailPartenza(HiddenItem lHiddenItemIdMailPartenza) {
		this.lHiddenItemIdMailPartenza = lHiddenItemIdMailPartenza;
	}

	public HiddenItem getlHiddenItemIdMailPartenza() {
		return lHiddenItemIdMailPartenza;
	}

	// Appende a str la stringa strToAppend, preceduta dal carattere ; se questo non è presente alla fine di str
	public String appendIndirizzoEmail(String str, String strToAppend) {
		String res = "";
		if (str == null || str.equals("")) {
			res = strToAppend;
		} else if (strToAppend != null && !strToAppend.equals("") && !str.toLowerCase().contains(strToAppend.toLowerCase())) {
			String lastChar = str.substring(str.length() - 1);
			if (lastChar.equalsIgnoreCase(";")) {
				res = str + strToAppend;
			} else {
				res = str + ";" + strToAppend;
			}
		} else {
			res = str;
		}
		return res;
	}

	public String removeIndirizzoEmail(String str, String strToRemove) {
		String res = "";
		if (str != null && !str.equals("") && strToRemove != null && !strToRemove.equals("") && str.toLowerCase().contains(strToRemove.toLowerCase())) {
			StringSplitterClient st = new StringSplitterClient(str, ";");
			for (int i = 0; i < st.getTokens().length; i++) {
				if (!st.getTokens()[i].equalsIgnoreCase(strToRemove)) {
					res += st.getTokens()[i];
					if (i < (st.getTokens().length - 1)) {
						res += ";";
					}
				}
			}
		} else {
			res = str;
		}
		return res;
	}

	public class InvioUDMailMultiLookupRubricaEmailPopup extends LookupRubricaEmailPopup {

		private String fieldName;
		private HashMap<String, Integer> indirizzoRefCount = new HashMap<String, Integer>();

		public InvioUDMailMultiLookupRubricaEmailPopup(String pFieldName) {
			super(false);
			this.fieldName = pFieldName;
			String value = vm.getValueAsString(fieldName);
			if (value != null && !value.equals("")) {
				StringSplitterClient st = new StringSplitterClient(value, ";");
				for (int i = 0; i < st.getTokens().length; i++) {
					String indirizzo = st.getTokens()[i];
					incrementaIndirizzoRefCount(indirizzo);
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
			
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									incrementaIndirizzoRefCount(indirizzo);
									vm.setValue(fieldName, appendIndirizzoEmail(vm.getValueAsString(fieldName), indirizzo));
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				incrementaIndirizzoRefCount(indirizzo);
				vm.setValue(fieldName, appendIndirizzoEmail(vm.getValueAsString(fieldName), indirizzo));
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if ("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
				GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT);
				datasource.performCustomOperation("trovaMembriGruppo", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							RecordList listaMembri = record.getAttributeAsRecordList("listaMembri");
							if (listaMembri != null && listaMembri.getLength() > 0) {
								for (int i = 0; i < listaMembri.getLength(); i++) {
									String indirizzo = listaMembri.get(i).getAttribute("indirizzoEmail");
									decrementaIndirizzoRefCount(indirizzo);
									if (!indirizzoRefCount.containsKey(indirizzo)) {
										vm.setValue(fieldName, removeIndirizzoEmail(vm.getValueAsString(fieldName), indirizzo));
									}
								}
							}
						}
					}
				}, new DSRequest());
			} else {
				String indirizzo = record.getAttribute("indirizzoEmail");
				decrementaIndirizzoRefCount(indirizzo);
				if (!indirizzoRefCount.containsKey(indirizzo)) {
					vm.setValue(fieldName, removeIndirizzoEmail(vm.getValueAsString(fieldName), indirizzo));
				}
			}
		}

		private void incrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo)) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) + 1);
			} else {
				indirizzoRefCount.put(indirizzo, new Integer(1));
			}
		}

		private void decrementaIndirizzoRefCount(String indirizzo) {
			if (indirizzoRefCount.containsKey(indirizzo) && indirizzoRefCount.get(indirizzo).intValue() > 1) {
				indirizzoRefCount.put(indirizzo, indirizzoRefCount.get(indirizzo) - 1);
			} else {
				indirizzoRefCount.remove(indirizzo);
			}
		}

	}

}
