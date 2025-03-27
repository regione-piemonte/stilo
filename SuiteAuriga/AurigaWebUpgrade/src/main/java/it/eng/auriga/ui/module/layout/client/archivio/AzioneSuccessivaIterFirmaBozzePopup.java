/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.archivio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class AzioneSuccessivaIterFirmaBozzePopup extends Window {

	private AzioneSuccessivaIterFirmaBozzePopup window;

	private DynamicForm form;

	private CheckboxItem flgInviaPassoSuccessivoItem;

	private CheckboxItem flgInviaAlVistoDiItem;
	protected FilteredSelectItemWithDisplay vistatoreItem;
	private HiddenItem desUtenteVistatoreItem;
	
	private CheckboxItem flgFirmaItem;
	protected FilteredSelectItemWithDisplay firmatarioItem;
	private HiddenItem desUtenteFirmatarioItem;
	
	private CheckboxItem flgInvioAItem;
	protected AssegnazioneItem invioAItem;
	
	private CheckboxItem flgRestituzioneRedattoriItem;
	
	private CheckboxItem flgTrasmissioneMailAiDestinatariItem;
	
	private Button confermaButton;
	private ServiceCallback<Record> callback;
	private boolean flgApposizione;
	private boolean azioneMassiva;
	private TipologiaApposizione tipologiaApposizione;

	private RecordList listaRecord;

	public AzioneSuccessivaIterFirmaBozzePopup(Record record, boolean flgApposizione, TipologiaApposizione tipologiaApposizione, ServiceCallback<Record> callback) {

		this(buildRecordListFromRecord(record), flgApposizione, tipologiaApposizione, callback);
	}

	public AzioneSuccessivaIterFirmaBozzePopup(final RecordList listaRecord, boolean flgApposizione, TipologiaApposizione tipologiaApposizione, final ServiceCallback<Record> callback) {

		window = this;
		this.callback = callback;

		this.flgApposizione = flgApposizione;
		this.tipologiaApposizione = tipologiaApposizione;
		this.listaRecord = listaRecord;
		
		azioneMassiva = !(listaRecord != null && listaRecord.getLength() == 1);

		setIsModal(true);
		setModalMaskOpacity(50);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setOverflow(Overflow.VISIBLE);
		setAutoSize(true);
		setAutoDraw(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		setTitle(I18NUtil.getMessages().azioneSuccessivaPopup_title());
		setHeaderIcon("blank.png");

		setAutoCenter(true);
		setHeight(150);
		setWidth(600);

		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(8);
		form.setColWidths(10, "*");
		form.setAlign(Alignment.CENTER);
		form.setOverflow(Overflow.VISIBLE);
		form.setTop(50);
		form.setBorder("1px solid grey");
		form.setMargin(5);
		
		Record record = azioneMassiva ? null : listaRecord.get(0);

		createCheckboxItem();

		createAssegnazioneItems();

		List<FormItem> listItems = new ArrayList<FormItem>();
		
		if(!azioneMassiva) {
			if(tipologiaApposizione == TipologiaApposizione.VISTO) {				
				listItems.add(flgInviaAlVistoDiItem);
				listItems.add(vistatoreItem);
				listItems.add(desUtenteVistatoreItem);
				listItems.add(flgFirmaItem);
				listItems.add(firmatarioItem);
				listItems.add(desUtenteFirmatarioItem);
				listItems.add(flgInvioAItem);
				listItems.add(invioAItem);
				listItems.add(flgRestituzioneRedattoriItem);
				listItems.add(flgTrasmissioneMailAiDestinatariItem);
			}else if(tipologiaApposizione == TipologiaApposizione.FIRMA || tipologiaApposizione == TipologiaApposizione.FIRMA_PROTOCOLLA) {
				listItems.add(flgFirmaItem);
				listItems.add(firmatarioItem);
				listItems.add(desUtenteFirmatarioItem);
				listItems.add(flgInvioAItem);
				listItems.add(invioAItem);
				listItems.add(flgRestituzioneRedattoriItem);
				listItems.add(flgTrasmissioneMailAiDestinatariItem);
			}
		}else {
			if(tipologiaApposizione == TipologiaApposizione.VISTO) {
				listItems.add(flgInviaPassoSuccessivoItem);
				listItems.add(flgInviaAlVistoDiItem);
				listItems.add(vistatoreItem);
				listItems.add(desUtenteVistatoreItem);
				listItems.add(flgFirmaItem);
				listItems.add(firmatarioItem);
				listItems.add(desUtenteFirmatarioItem);
				listItems.add(flgRestituzioneRedattoriItem);
			}else if(tipologiaApposizione == TipologiaApposizione.FIRMA || tipologiaApposizione == TipologiaApposizione.FIRMA_PROTOCOLLA) {
				listItems.add(flgInviaPassoSuccessivoItem);
				listItems.add(flgFirmaItem);
				listItems.add(firmatarioItem);
				listItems.add(desUtenteFirmatarioItem);
				listItems.add(flgRestituzioneRedattoriItem);
			}
		}		
		
		form.setFields(listItems.toArray(new FormItem[listItems.size()]));

		confermaButton = new Button(I18NUtil.getMessages().saveButton_prompt());
		confermaButton.setIcon("buttons/save.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				manageClickOnSave();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setWidth100();
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.RIGHT);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);

		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		
		// Aggiungo al layout il form e i button
		lVLayout.setMembers(form, _buttons);

		addItem(lVLayout);
		setShowTitle(true);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				if (callback != null) {
					callback.execute(new Record());
				}
				markForDestroy();
			}
		});

		// Entra nel ramo then di questo if solamente nel caso in cui si abbia un solo file
		if (!azioneMassiva) {
			if ((record.getAttribute("idUd") != null && !"".equals(record.getAttribute("idUd"))) || (record.getAttribute("idUdFolder") != null && !"".equals(record.getAttribute("idUdFolder")))) {				
				// Sia che arrivi da lista e sia che arrivi da dettaglio documento devo comunque aggiornare i dati del dettaglio per prendere il firmatario successivo corretto
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUd", (record.getAttribute("idUd") != null && !"".equals(record.getAttribute("idUd"))) ? record.getAttribute("idUd") : record.getAttribute("idUdFolder"));
				final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
				lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record detailRecord = response.getData()[0];
							setInitialValues(detailRecord);
						}
					}
				});
			} else {
				// Qui non dovrei mai entrare
				setInitialValues(null);
			}
		} else {
			// Nel caso sia una firma massiva di almeno due elementi
			setInitialValues(null);
		}
		draw();
	}

	protected void setInitialValues(Record detailRecord) {

		Record lRecord = new Record();	
		
		// Eseguo un settaggio di default dell codice rapido
		RecordList lRecordListAssegnazione = new RecordList();
		if (AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD")) {
			Record lRecordAssegnazione = new Record();
			lRecordAssegnazione.setAttribute("codRapido", AurigaLayout.getCodRapidoOrganigramma());
			lRecordListAssegnazione.add(lRecordAssegnazione);
		} else {
			lRecordListAssegnazione.add(new Record());
		}

		if (detailRecord != null) {
			String prossimaAzione = detailRecord.getAttributeAsString("prossimaAzione");
			if (prossimaAzione != null) {
				if (prossimaAzione.equals("invio_per_visto")) {
					lRecord.setAttribute("flgInviaAlVistoDi", true);
					lRecord.setAttribute("idVistatore", detailRecord.getAttributeAsString("prossimaAzioneIdFirmatario"));
					lRecord.setAttribute("desUtenteVistatore", detailRecord.getAttributeAsString("prossimaAzioneNomeFirmatario"));
				} else if (prossimaAzione.equals("invio_in_firma")) {
					lRecord.setAttribute("flgFirma", true);
					lRecord.setAttribute("idFirmatario", detailRecord.getAttributeAsString("prossimaAzioneIdFirmatario"));
					lRecord.setAttribute("desUtenteFirmatario", detailRecord.getAttributeAsString("prossimaAzioneNomeFirmatario"));
				} else if (prossimaAzione.equals("invio")) {
					lRecord.setAttribute("flgInvioA", true);
					RecordList linvioARecordList = new RecordList();
					Record linvioARecord = new Record();
					
					if(detailRecord.getAttributeAsString("prossimaAzioneIdUOAss")!=null && !"".equalsIgnoreCase(detailRecord.getAttributeAsString("prossimaAzioneIdUOAss"))) {
						String typeNodo = detailRecord.getAttributeAsString("prossimaAzioneIdUOAss").substring(0, 2);
						String idUOSV = detailRecord.getAttributeAsString("prossimaAzioneIdUOAss").substring(2);
						
						linvioARecord.setAttribute("codRapido", detailRecord.getAttributeAsString("prossimaAzioneCodRapioUOAss"));
						linvioARecord.setAttribute("idUo", idUOSV);
						linvioARecord.setAttribute("descrizione", detailRecord.getAttributeAsString("prossimaAzioneDesUOAss"));
						linvioARecord.setAttribute("organigramma", detailRecord.getAttributeAsString("prossimaAzioneIdUOAss"));
						linvioARecord.setAttribute("typeNodo", typeNodo);
					}
					linvioARecordList.add(linvioARecord);			
					lRecord.setAttribute("invioA", linvioARecordList);
				} else if (prossimaAzione.equals("restituzione_ai_redattori")) {
					lRecord.setAttribute("flgRestituzioneRedattori", true);
				} else if (prossimaAzione.equals("mail_automatica_a_destinatari")) {
					lRecord.setAttribute("flgTrasmissioneMailAiDestinatari", true);
				} 
			}
		} else {
			if(azioneMassiva) {
				lRecord.setAttribute("flgInviaPassoSuccessivo", true);
			}
		}

		editRecord(lRecord);
	}
	
	public void editRecord(Record record) {
		
		String idFirmatario = record.getAttribute("idFirmatario");
		String desUtenteFirmatario = record.getAttribute("desUtenteFirmatario");
		if (idFirmatario != null && !"".equals(idFirmatario) && desUtenteFirmatario != null && !"".equals(desUtenteFirmatario) ) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(idFirmatario, desUtenteFirmatario);
			firmatarioItem.setValueMap(valueMap);
		}
		
		String idVistatore = record.getAttribute("idVistatore");
		String desUtenteVistatore = record.getAttribute("desUtenteVistatore");
		if (idVistatore != null && !"".equals(idVistatore) && desUtenteVistatore != null && !"".equals(desUtenteVistatore) ) {
			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
			valueMap.put(idVistatore, desUtenteVistatore);
			vistatoreItem.setValueMap(valueMap);
		}
		form.editRecord(record);
	}


	private void createAssegnazioneItems() {

		// Assegnazione item per l-invio alla protocollazione/registrazione
		invioAItem = new AssegnazioneItem() {
			
			@Override
			public boolean showPreferiti() {
				return false;
			}

			@Override
			public String getTipoAssegnatari() {
				/*
				 * Impostando i flag come UO;SV facciamo in modo che la select
				 * permetta di selezionare sia UO che SV
				 */
				return "UO;SV";
			}
			
			@Override
			public boolean showOpzioniInvioAssegnazioneButton() {
				/**
				 * Mostra o nasconde le opzioni di invio
				 */
				return false;
			}
		};
		invioAItem.setStartRow(false);
		invioAItem.setEndRow(true);
		invioAItem.setName("invioA");
		invioAItem.setShowTitle(false);
		invioAItem.setCanEdit(true);
		invioAItem.setColSpan(5);
		invioAItem.setNotReplicable(true);
		invioAItem.setFlgSenzaLD(true);
		invioAItem.setFlgUdFolder("U");
		invioAItem.setAttribute("obbligatorio", true);
		
		SelectGWTRestDataSource vistatoriDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,	new String[] {"cognomeNome"}, true);
		ListGridField vistatoriCodiceField = new ListGridField("codice", "Cod.");
		vistatoriCodiceField.setWidth(90);
		ListGridField vistatoriCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//
		ListGridField vistatoriUsernameField = new ListGridField("username", "Username");
		vistatoriUsernameField.setWidth(90);

		
		vistatoreItem = new FilteredSelectItemWithDisplay("idVistatore", "Nominativo", vistatoriDS) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				form.setValue("desUtenteVistatore", record.getAttributeAsString("cognomeNome"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				form.setValue("idVistatore", "");
				form.setValue("desUtenteVistatore", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					form.setValue("idVistatore", "");
					form.setValue("desUtenteVistatore", "");
				}
			}
		};
		vistatoreItem.setAutoFetchData(false);
		vistatoreItem.setAlwaysFetchMissingValues(true);
		vistatoreItem.setFetchMissingValues(true);
		vistatoreItem.setPickListFields(vistatoriCodiceField, vistatoriCognomeNomeField, vistatoriUsernameField);
		vistatoreItem.setPickListWidth(400);
		vistatoreItem.setFilterLocally(true);
		vistatoreItem.setValueField("idUtente");
		vistatoreItem.setRequired(true);
		vistatoreItem.setClearable(true);
		vistatoreItem.setShowIcons(true);		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			vistatoreItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		vistatoreItem.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});	
		
		
		desUtenteVistatoreItem = new HiddenItem("desUtenteVistatore");
		
		SelectGWTRestDataSource firmatariDS = new SelectGWTRestDataSource("LoadComboUtentiDataSource", "idUtente", FieldType.TEXT,	new String[] {"cognomeNome"}, true);
		ListGridField firmatariCodiceField = new ListGridField("codice", "Cod.");
		firmatariCodiceField.setWidth(90);
		ListGridField firmatariCognomeNomeField = new ListGridField("cognomeNome", "Cognome e nome");//
		ListGridField firmatariUsernameField = new ListGridField("username", "Username");
		vistatoriUsernameField.setWidth(90);
		
		
		firmatarioItem = new FilteredSelectItemWithDisplay("idFirmatario", "Nominativo", firmatariDS) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				form.setValue("desUtenteFirmatario", record.getAttributeAsString("cognomeNome"));
			}
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				form.setValue("idFirmatario", "");
				form.setValue("desUtenteFirmatario", "");
			};
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					form.setValue("idFirmatario", "");
					form.setValue("desUtenteFirmatario", "");
				}
			}
		};
		firmatarioItem.setAutoFetchData(false);
		firmatarioItem.setAlwaysFetchMissingValues(true);
		firmatarioItem.setFetchMissingValues(true);
		firmatarioItem.setPickListFields(firmatariCodiceField, firmatariCognomeNomeField, firmatariUsernameField);
		firmatarioItem.setPickListWidth(400);
		firmatarioItem.setFilterLocally(true);
		firmatarioItem.setValueField("idUtente");
		firmatarioItem.setRequired(true);
		firmatarioItem.setClearable(true);
		firmatarioItem.setShowIcons(true);		
		if (AurigaLayout.getParametroDBAsBoolean("NRO_UTENTI_NONSTD")) {
			firmatarioItem.setEmptyPickListMessage(I18NUtil.getMessages().emptyPickListDimNonStdMessage());
		}
		firmatarioItem.setItemHoverFormatter(new FormItemHoverFormatter() {
			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("cognomeNome") : null;
			}
		});	
		
		desUtenteFirmatarioItem = new HiddenItem("desUtenteFirmatario");
	
	}

	private void createCheckboxItem() {
		
		flgInviaPassoSuccessivoItem = new CheckboxItem("flgInviaPassoSuccessivo", "Invia al passo successivo di firma/numerazione/trasmissione ai destinatari");
		flgInviaPassoSuccessivoItem.setStartRow(true);
		flgInviaPassoSuccessivoItem.setColSpan(1);
		flgInviaPassoSuccessivoItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});
		
		flgInviaAlVistoDiItem = new CheckboxItem("flgInviaAlVistoDi", "Invia al visto di");
		flgInviaAlVistoDiItem.setStartRow(true);
		flgInviaAlVistoDiItem.setColSpan(1);
		flgInviaAlVistoDiItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});

		flgFirmaItem = new CheckboxItem("flgFirma", "Invio alla firma digitale di");
		flgFirmaItem.setStartRow(true);
		flgFirmaItem.setColSpan(1);
		flgFirmaItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});
		
		flgInvioAItem = new CheckboxItem("flgInvioA", "Invio a");
		flgInvioAItem.setStartRow(true);
		flgInvioAItem.setColSpan(1);
		flgInvioAItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});
		
		flgRestituzioneRedattoriItem = new CheckboxItem("flgRestituzioneRedattori", "Restituzione ai redattori");
		flgRestituzioneRedattoriItem.setStartRow(true);
		flgRestituzioneRedattoriItem.setColSpan(1);
		flgRestituzioneRedattoriItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});
		
		flgTrasmissioneMailAiDestinatariItem = new CheckboxItem("flgTrasmissioneMailAiDestinatari", "Trasmissione e-mail automatica ai destinatari documento");
		flgTrasmissioneMailAiDestinatariItem.setStartRow(true);
		flgTrasmissioneMailAiDestinatariItem.setColSpan(1);
		flgTrasmissioneMailAiDestinatariItem.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});
	}

	private void clickOnCheckbox(String nameCheckboxClicked) {
		/*
		 * Se la checkbox selezionata è diversa da quella appena cliccata allora
		 * tolgo il check dalle altre e seleziono quest'ultima per ottenere un
		 * gruppo di checkbox esclusivo
		 */		
		flgInviaPassoSuccessivoItem.setValue(flgInviaPassoSuccessivoItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgInviaAlVistoDiItem.setValue(flgInviaAlVistoDiItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgFirmaItem.setValue(flgFirmaItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgInvioAItem.setValue(flgInvioAItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgRestituzioneRedattoriItem.setValue(flgRestituzioneRedattoriItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgTrasmissioneMailAiDestinatariItem.setValue(flgTrasmissioneMailAiDestinatariItem.getName().equals(nameCheckboxClicked) ? true : false);
	}
	
	public boolean validate() {

		boolean valid = true;
		
		form.clearErrors(true);
		
		final Record currentRecord = new Record(form.getValues());
		
		if (currentRecord != null) {
			if(!currentRecord.getAttributeAsBoolean("flgInviaPassoSuccessivo") &&
			   !currentRecord.getAttributeAsBoolean("flgInviaAlVistoDi") &&
			   !currentRecord.getAttributeAsBoolean("flgFirma") && 
			   !currentRecord.getAttributeAsBoolean("flgInvioA") && 
			   !currentRecord.getAttributeAsBoolean("flgRestituzioneRedattori") && 
			   !currentRecord.getAttributeAsBoolean("flgTrasmissioneMailAiDestinatari")) {
				form.setFieldErrors("flgInviaPassoSuccessivo", "Obbligatorio selezionare almeno una azione");
				form.setFieldErrors("flgInviaAlVistoDi", "Obbligatorio selezionare almeno una azione");
				form.setFieldErrors("flgFirma", "Obbligatorio selezionare almeno una azione");
				form.setFieldErrors("flgInvioA", "Obbligatorio selezionare almeno una azione");
				form.setFieldErrors("flgRestituzioneRedattori", "Obbligatorio selezionare almeno una azione");
				form.setFieldErrors("flgTrasmissioneMailAiDestinatari", "Obbligatorio selezionare almeno una azione");
				valid = false;
			} else if(currentRecord.getAttributeAsBoolean("flgInviaAlVistoDi")) {
				String idVistatore = currentRecord.getAttributeAsString("idVistatore");
				if(idVistatore == null || "".equals(idVistatore)) {
					form.setFieldErrors("flgInviaAlVistoDi", "Obbligatorio selezionare un destinatario");
					valid = false;
				}
			} else if(currentRecord.getAttributeAsBoolean("flgFirma")) {
				String idFirmatario = currentRecord.getAttributeAsString("idFirmatario");
				if(idFirmatario == null || "".equals(idFirmatario)) {
					form.setFieldErrors("flgFirma", "Obbligatorio selezionare un destinatario");
					valid = false;
				}
			} else if(currentRecord.getAttributeAsBoolean("flgInvioA")) {
				RecordList invioA = currentRecord.getAttributeAsRecordList("invioA");
				String idUo = invioA != null && invioA.getLength() > 0  && invioA.get(0) != null ? invioA.get(0).getAttribute("idUo") : null;
				if(idUo == null || "".equals(idUo)) {
					form.setFieldErrors("flgInvioA", "Obbligatorio selezionare un destinatario");
					valid = false;
				}
			}	
		}
		
		return valid;
	}
	
	private void manageClickOnSave() {
		
		if(validate()) {

			final Record currentRecord = new Record(form.getValues());
	
			/**
			 * Se seleziono il Checkbox NESSUNA AZIONE - chiudo il popup e ricarico
			 * la lista
			 */
			if (currentRecord != null) {
				
		
				// Setto anche quanto è arrivato dal chiamante
				currentRecord.setAttribute("listUD", listaRecord);
	
				final ServiceCallback<Record> callbackAfterAzionePostFirma = new ServiceCallback<Record>() {
	
					@Override
					public void execute(Record object) {
						if (callback != null) {
							callback.execute(object);
						}
						window.destroy();
					}
				};
				
				/*TODO: capire quale metodo chiamare per il post firma*/
	
				GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource");
				lArchivioDatasource.executecustom("azioneSuccessivaIterFirmaBozze", currentRecord, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							manageResponse(listaRecord, response);
						}
					}
	
					/**
					 * Metodo per la gestione del risultato nell'apposizione del
					 * visto. Vengono qui gestiti eventuali errori oppure il buon
					 * esito dell'operazione
					 * 
					 * @param listaRecordUd
					 * @param response
					 */
					private void manageResponse(final RecordList listaRecordUd, DSResponse response) {
	
						Record result = response.getData()[0];
						Map<String, String> mapErrorMessages = result.getAttributeAsMap("errorMessages");
	
						manageError(callbackAfterAzionePostFirma, listaRecordUd, mapErrorMessages);
					}
	
				});
			}
		}
	}

	private static RecordList buildRecordListFromRecord(Record record) {
		RecordList recordList = new RecordList();
		if (record.getAttribute("idUd") != null && !"".equals(record.getAttribute("idUd"))) {
			// se arrivo dal dettaglio documento setto l'idUdFolder come nei
			// record della lista
			record.setAttribute("idUdFolder", record.getAttribute("idUd"));
		}
		recordList.add(record);
		return recordList;
	}
	
		
	private RecordList getListaErrori(Map<String, String> mapErrorMessages, RecordList listaRecord) {
		
		RecordList listaErrori = new RecordList();
		
		if(mapErrorMessages != null && !mapErrorMessages.isEmpty()){
			// Per ogni errore individuato
			for (Map.Entry<String, String> itemMessageError : mapErrorMessages.entrySet()) {
	
				String segnatura = "";
				String value = "";
				/*
				 * Controllo quali di questi record è andato in errore Quelli che
				 * NON hanno generato l'errore sono quelli che devono essere inviati
				 * al popup per l'azione successiva all'apposizione del visto
				 */
				for (int indexRecordList = 0; indexRecordList < listaRecord.getLength(); indexRecordList++) {
	
					if (listaRecord.get(indexRecordList).getAttribute("idUdFolder").contains(itemMessageError.getKey())) {
						// Se il record selezionato è andato in errore
						segnatura = listaRecord.get(indexRecordList).getAttribute("segnatura");
						value = itemMessageError.getValue();
						break;
					}
				}
				
				// Il record è uno di quelli che è andato in errore e quindi lo
				// inserisco nella relativa lista
				Record record = new Record();
				record.setAttribute("idError", segnatura);
				record.setAttribute("descrizione", value);
				listaErrori.add(record); // Aggiungo il record alla lista degli errori
			}
		}

		return listaErrori;
	}

	private void manageError(final ServiceCallback<Record> callbackAfterManageError, final RecordList listaRecordUd,
			Map<String, String> mapErrorMessages) {

		// Controllo lo stato degli errori
		final RecordList listaErrori = getListaErrori(mapErrorMessages, listaRecordUd);

		if (listaErrori.getLength() == 0) {
			// Allora non ci sono stati errori
			Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));

			if (callbackAfterManageError != null) {
				callbackAfterManageError.execute(new Record());
			}
		} else if (listaRecordUd.getLength() == 1) {
			/*
			 * Allora c'è solo un record che si doveva analizzare e questo è
			 * andato in errore Creo e stampo il messaggio di errore
			 */
			String error = null;
			
			// Prelevo l'errore indicato
			for (Map.Entry<String, String> item : mapErrorMessages.entrySet()) {
				error = item.getValue();
			}
			
			Layout.addMessage(new MessageBean(error, "", MessageType.ERROR));

			/*
			 * Ritorno con la callback al chiamante
			 */
			if (callbackAfterManageError != null) {
				callbackAfterManageError.execute(null);
			}

		} else if (listaRecordUd.getLength() > 1) {

			/*
			 * Visualizzo la tabella contenente gli errori rilevati
			 */
			ErroreMassivoPopup erroreMassivo = new ErroreMassivoPopup("azioneVisto", "Estremi documento", listaErrori,
					mapErrorMessages.size(), 600, 300, new ServiceCallback<Record>() {

						@Override
						public void execute(Record response) {

							/*
							 * Se ALCUNI elementi sono andati in errore allora
							 * si genera la tabella di errore e poi si procede
							 * con la selezione dell'azione successiva. Se TUTTI
							 * gli elementi sono andati in errore allora non si
							 * visualizza la finestra per richiedere l'azione
							 * successiva
							 */
							if (listaRecordUd.getLength() == listaErrori.getLength()) {

								/*
								 * Sono andati tutti in errore. Ritorno al
								 * chiamante tramite callback ritornando un
								 * record nullo che indica appunto il fatto che
								 * non ci sono record che sono andati a buon
								 * fine
								 */
								if (callbackAfterManageError != null) {
									callbackAfterManageError.execute(null);
								}

							} else {

								/*
								 * Ritorno un record vuoto per far capire che
								 * qualcosa è andato a buon fine
								 */

								if (callbackAfterManageError != null) {
									callbackAfterManageError.execute(new Record());
								}
							}
						}
					});
			erroreMassivo.show();
		}
	}

}
