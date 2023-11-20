/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.AssegnazioneItem;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;

public class AzioneSuccessivaPopup extends Window {

	private AzioneSuccessivaPopup window;

	private DynamicForm form;

	private CheckboxItem flgRestituzioneAlMittente;
	private CheckboxItem flgMandaAlMittenteDocumento;

	private CheckboxItem flgProtRegItem;
	protected AssegnazioneItem protRegAssegnazioneItem;

	private CheckboxItem flgFirmaItem;
	protected AssegnazioneItem invioFirmaDigitaleAssegnazioneItem;

	private CheckboxItem flgVistoEleItem;
	protected AssegnazioneItem invioVistoElettronicoAssegnazioneItem;

	private CheckboxItem flgNoAzioneItem;

	private Button confermaButton;
	private ServiceCallback<Record> callback;
	private boolean apposizioneStatus;
	private TipologiaApposizione tipologiaApposizione;

	private RecordList listaRecord;

	public AzioneSuccessivaPopup(Record record, boolean apposizioneStatus, TipologiaApposizione tipologiaApposizione,
			ServiceCallback<Record> callback) {

		this(buildRecordListFromRecord(record), apposizioneStatus, tipologiaApposizione, callback);
	}

	public AzioneSuccessivaPopup(final RecordList listaRecord, boolean apposizioneStatus,
			TipologiaApposizione tipologiaApposizione, final ServiceCallback<Record> callback) {

		window = this;
		this.callback = callback;

		this.apposizioneStatus = apposizioneStatus;
		this.tipologiaApposizione = tipologiaApposizione;
		this.listaRecord = listaRecord;

		setIsModal(true);
		setModalMaskOpacity(50);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowCloseButton(true);
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

		createCheckboxItem();

		createAssegnazioneItems();

		List<FormItem> listItems = new ArrayList<FormItem>();

		if (apposizioneStatus) {
						
			/*
			 * Se è stata eseguita l'apposizione firma o visto
			 */
						
			if (listaRecord.getLength() == 1 && (listaRecord.get(0).getAttribute("idMittUDOut") == null || "".equals(listaRecord.get(0).getAttribute("idMittUDOut")))) {
				/*
				 * Se si ha una sola UD e se viene ritornata dalla store il
				 * valore idMittUDOut nullo allora non si deve visualizzare il
				 * pulsante per mandare al mittente indicato sull'ud
				 */				
			} else {				
				listItems.add(flgMandaAlMittenteDocumento);							
			}
			
			listItems.add(flgProtRegItem);
			listItems.add(protRegAssegnazioneItem);
			listItems.add(flgFirmaItem);
			listItems.add(invioFirmaDigitaleAssegnazioneItem);
			listItems.add(flgVistoEleItem);
			listItems.add(invioVistoElettronicoAssegnazioneItem);
			listItems.add(flgRestituzioneAlMittente);
			listItems.add(flgNoAzioneItem);

		} else {
			
			/*
			 * Se è stata rifiutata l'apposizione firma o visto
			 */
			
			listItems.add(flgRestituzioneAlMittente);
			
			if (listaRecord.getLength() == 1 && (listaRecord.get(0).getAttribute("idMittUDOut") == null || "".equals(listaRecord.get(0).getAttribute("idMittUDOut")))) {
				/*
				 * Se si ha una sola UD oppure se viene ritornata dalla store il
				 * valore idMittUDOut nullo allora non si deve visualizzare il
				 * pulsante per mandare al mittente indicato sull'ud
				 */
			} else {
				listItems.add(flgMandaAlMittenteDocumento);				
			}
			
			listItems.add(flgProtRegItem);
			listItems.add(protRegAssegnazioneItem);
			listItems.add(flgFirmaItem);
			listItems.add(invioFirmaDigitaleAssegnazioneItem);
			listItems.add(flgVistoEleItem);
			listItems.add(invioVistoElettronicoAssegnazioneItem);
			listItems.add(flgNoAzioneItem);	
			
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

		// Entra nel ramo then di questo if solamente nel caso in cui si abbia
		// un solo file
		if (listaRecord != null && listaRecord.getLength() == 1) {
			Record record = listaRecord.get(0);
			if (record.getAttribute("idUd") != null && !"".equals(record.getAttribute("idUd"))) {
				// se arrivo dal dettaglio documento ho già le informazioni che
				// mi servono
				setInitialValues(record);
			} else if (record.getAttribute("idUdFolder") != null && !"".equals(record.getAttribute("idUdFolder"))) {
				// è il record della lista quindi devo caricare il dettaglio
				// documento per recuperarmi i dati del mittente
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
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
			RecordList listaMittenti = detailRecord.getAttributeAsRecordList("listaMittenti");
			if (listaMittenti != null && !listaMittenti.isEmpty()) {
				Record mittente = listaMittenti.get(0);
				RecordList lProtRegAssegnazioneRecordList = new RecordList();
				Record lProtRegAssegnazioneRecord = new Record();
				if (mittente.getAttribute("idUoSoggetto") != null
						&& !"".equals(mittente.getAttribute("idUoSoggetto"))) {
					lProtRegAssegnazioneRecord.setAttribute("tipo", "SV;UO");
					lProtRegAssegnazioneRecord.setAttribute("codRapido", mittente.getAttribute("codRapidoMittente"));
					lProtRegAssegnazioneRecord.setAttribute("typeNodo", "UO");
					lProtRegAssegnazioneRecord.setAttribute("organigramma",
							"UO" + mittente.getAttribute("idUoSoggetto"));
					lProtRegAssegnazioneRecord.setAttribute("idUo", mittente.getAttribute("idUoSoggetto"));
					if (mittente.getAttribute("denominazioneMittente") != null
							&& !"".equals(mittente.getAttribute("denominazioneMittente"))) {
						lProtRegAssegnazioneRecord.setAttribute("descrizione",
								mittente.getAttribute("denominazioneMittente"));
					} else {
						lProtRegAssegnazioneRecord.setAttribute("descrizione",
								mittente.getAttribute("cognomeMittente") + " " + mittente.getAttribute("nomeMittente"));
					}
				} else if (mittente.getAttribute("idScrivaniaSoggetto") != null
						&& !"".equals(mittente.getAttribute("idScrivaniaSoggetto"))) {
					lProtRegAssegnazioneRecord.setAttribute("tipo", "SV;UO");
					lProtRegAssegnazioneRecord.setAttribute("codRapido", mittente.getAttribute("codRapidoMittente"));
					lProtRegAssegnazioneRecord.setAttribute("typeNodo", "SV");
					lProtRegAssegnazioneRecord.setAttribute("organigramma",
							"SV" + mittente.getAttribute("idScrivaniaSoggetto"));
					lProtRegAssegnazioneRecord.setAttribute("idUo", mittente.getAttribute("idScrivaniaSoggetto"));
					if (mittente.getAttribute("denominazioneMittente") != null
							&& !"".equals(mittente.getAttribute("denominazioneMittente"))) {
						lProtRegAssegnazioneRecord.setAttribute("descrizione",
								mittente.getAttribute("denominazioneMittente"));
					} else {
						lProtRegAssegnazioneRecord.setAttribute("descrizione",
								mittente.getAttribute("cognomeMittente") + " " + mittente.getAttribute("nomeMittente"));
					}
				}
				lProtRegAssegnazioneRecordList.add(lProtRegAssegnazioneRecord);
				lRecord.setAttribute("protRegAssegnazione", lProtRegAssegnazioneRecordList);
				lRecord.setAttribute("invioFirmaDigitaleAssegnazione", lRecordListAssegnazione);
				lRecord.setAttribute("invioVistoElettronicoAssegnazione", lRecordListAssegnazione);
			}
		} else {
			// Setto il codice rapido di default
			lRecord.setAttribute("protRegAssegnazione", lRecordListAssegnazione);
			lRecord.setAttribute("invioFirmaDigitaleAssegnazione", lRecordListAssegnazione);
			lRecord.setAttribute("invioVistoElettronicoAssegnazione", lRecordListAssegnazione);
		}

		lRecord = setFlagInitialValue(lRecord);

		form.editRecord(lRecord);
	}

	private Record setFlagInitialValue(Record lRecord) {

		if (!apposizioneStatus) {
			/*
			 * Allora ci si è rifiutati di apporre la firma o il visto Deve
			 * essere impostato come default il valore del flag reinvio al
			 * mittente
			 */
			lRecord.setAttribute("flgRestituzioneAlMittente", true);
		} else if (tipologiaApposizione == TipologiaApposizione.FIRMA) {
			// Per il settaggio della checkbox iniziale in caso il valore sia
			// inserito da DB
			String azioneDefaultPostFirma = AurigaLayout.getParametroDB("AZIONE_DEFAULT_POST_FIRMA");
			if (azioneDefaultPostFirma != null) {
				if (azioneDefaultPostFirma.equals("registrazione")) {
					lRecord.setAttribute("flgProtReg", true);
				} else if (azioneDefaultPostFirma.equals("firma")) {
					lRecord.setAttribute("flgFirma", true);
				} else if (azioneDefaultPostFirma.equals("visto")) {
					lRecord.setAttribute("flgVistoEle", true);
				} else if (azioneDefaultPostFirma.equals("reinvio_mit")) {
					lRecord.setAttribute("flgRestituzioneAlMittente", true);
				} else if (azioneDefaultPostFirma.equals("registrazione_mitt_ud")) {
					lRecord.setAttribute("flgMandaAlMittenteDocumento", true);
				} else {
					lRecord.setAttribute("flgNoAzione", true);
				}
			} else {
				lRecord.setAttribute("flgNoAzione", true);
			}
		} else if (tipologiaApposizione == TipologiaApposizione.VISTO) {
			// Per il settaggio della checkbox iniziale in caso il valore sia
			// inserito da DB
			String azioneDefaultPostFirma = AurigaLayout.getParametroDB("AZIONE_DEFAULT_POST_VISTO");
			if (azioneDefaultPostFirma != null) {
				if (azioneDefaultPostFirma.equals("registrazione")) {
					lRecord.setAttribute("flgProtReg", true);
				} else if (azioneDefaultPostFirma.equals("firma")) {
					lRecord.setAttribute("flgFirma", true);
				} else if (azioneDefaultPostFirma.equals("visto")) {
					lRecord.setAttribute("flgVistoEle", true);
				} else if (azioneDefaultPostFirma.equals("reinvio_mit")) {
					lRecord.setAttribute("flgRestituzioneAlMittente", true);
				} else if (azioneDefaultPostFirma.equals("registrazione_mitt_ud")) {
					lRecord.setAttribute("flgMandaAlMittenteDocumento", true);
				} else {
					lRecord.setAttribute("flgNoAzione", true);
				}
			} else {
				lRecord.setAttribute("flgNoAzione", true);
			}
		}

		return lRecord;
	}

	private void createAssegnazioneItems() {

		// Assegnazione item per l-invio alla protocollazione/registrazione
		protRegAssegnazioneItem = new AssegnazioneItem() {

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
		protRegAssegnazioneItem.setStartRow(false);
		protRegAssegnazioneItem.setEndRow(true);
		protRegAssegnazioneItem.setName("protRegAssegnazione");
		protRegAssegnazioneItem.setShowTitle(false);
		protRegAssegnazioneItem.setCanEdit(true);
		protRegAssegnazioneItem.setColSpan(5);
		protRegAssegnazioneItem.setNotReplicable(true);
//		protRegAssegnazioneItem.setFlgSenzaLD(false);
		protRegAssegnazioneItem.setFlgUdFolder("U");
		protRegAssegnazioneItem.setAttribute("obbligatorio", true);

		// Assegnazione item per l'invio alla firma digitale
		invioFirmaDigitaleAssegnazioneItem = new AssegnazioneItem() {

			@Override
			public String getTipoAssegnatari() {
				/*
				 * Impostando il flag come SV facciamo in modo che la select
				 * permetta di selezionare solo SV
				 */
				return "SV";
			}
			
			@Override
			public boolean showOpzioniInvioAssegnazioneButton() {
				/**
				 * Mostra o nasconde le opzioni di invio
				 */
				return false;
			}
		};
		invioFirmaDigitaleAssegnazioneItem.setStartRow(false);
		invioFirmaDigitaleAssegnazioneItem.setName("invioFirmaDigitaleAssegnazione");
		invioFirmaDigitaleAssegnazioneItem.setShowTitle(false);
		invioFirmaDigitaleAssegnazioneItem.setCanEdit(true);
		invioFirmaDigitaleAssegnazioneItem.setColSpan(5);
		invioFirmaDigitaleAssegnazioneItem.setFlgUdFolder("U");
		invioFirmaDigitaleAssegnazioneItem.setNotReplicable(true);
		invioFirmaDigitaleAssegnazioneItem.setFlgSenzaLD(true);
		invioFirmaDigitaleAssegnazioneItem.setAttribute("obbligatorio", true);

		// Assegnazione item per l'invio al visto elettronico
		invioVistoElettronicoAssegnazioneItem = new AssegnazioneItem() {

			@Override
			public String getTipoAssegnatari() {
				/*
				 * Impostando il flag come SV facciamo in modo che la select
				 * permetta di selezionare solo SV
				 */
				return "SV";
			}
			
			@Override
			public boolean showOpzioniInvioAssegnazioneButton() {
				/**
				 * Mostra o nasconde le opzioni di invio
				 */
				return false;
			}
		};
		invioVistoElettronicoAssegnazioneItem.setStartRow(false);
		invioVistoElettronicoAssegnazioneItem.setName("invioVistoElettronicoAssegnazione");
		invioVistoElettronicoAssegnazioneItem.setShowTitle(false);
		invioVistoElettronicoAssegnazioneItem.setCanEdit(true);
		invioVistoElettronicoAssegnazioneItem.setColSpan(5);
		invioVistoElettronicoAssegnazioneItem.setFlgUdFolder("U");
		invioVistoElettronicoAssegnazioneItem.setNotReplicable(true);
		invioVistoElettronicoAssegnazioneItem.setFlgSenzaLD(true);
		invioVistoElettronicoAssegnazioneItem.setAttribute("obbligatorio", true);
	}

	private void createCheckboxItem() {

		flgProtRegItem = new CheckboxItem("flgProtReg", "Invio alla protocollazione/registrazione presso ");
		flgProtRegItem.setStartRow(true);
		flgProtRegItem.setColSpan(1);
		flgProtRegItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});

		flgFirmaItem = new CheckboxItem("flgFirma", "Invio alla firma digitale ");
		flgFirmaItem.setStartRow(true);
		flgFirmaItem.setColSpan(1);
		flgFirmaItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});

		flgVistoEleItem = new CheckboxItem("flgVistoEle", "Invio al visto elettronico di ");
		flgVistoEleItem.setStartRow(true);
		flgVistoEleItem.setColSpan(1);
		flgVistoEleItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});

		flgNoAzioneItem = new CheckboxItem("flgNoAzione", "Nessuna azione");
		flgNoAzioneItem.setStartRow(true);
		flgNoAzioneItem.setColSpan(1);
		flgNoAzioneItem.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});

		flgRestituzioneAlMittente = new CheckboxItem("flgRestituzioneAlMittente", "Restituzione al mittente");
		flgRestituzioneAlMittente.setStartRow(true);
		flgRestituzioneAlMittente.setColSpan(1);
		flgRestituzioneAlMittente.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				clickOnCheckbox(event.getItem().getName());
			}
		});

		flgMandaAlMittenteDocumento = new CheckboxItem("flgMandaAlMittenteDocumento",
				"Manda per protocollazione al mittente indicato sul documento");
		flgMandaAlMittenteDocumento.setStartRow(true);
		flgMandaAlMittenteDocumento.setColSpan(1);
		flgMandaAlMittenteDocumento.addChangeHandler(new ChangeHandler() {

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
		flgProtRegItem.setValue(flgProtRegItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgFirmaItem.setValue(flgFirmaItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgVistoEleItem.setValue(flgVistoEleItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgNoAzioneItem.setValue(flgNoAzioneItem.getName().equals(nameCheckboxClicked) ? true : false);
		flgRestituzioneAlMittente
				.setValue(flgRestituzioneAlMittente.getName().equals(nameCheckboxClicked) ? true : false);
		flgMandaAlMittenteDocumento
				.setValue(flgMandaAlMittenteDocumento.getName().equals(nameCheckboxClicked) ? true : false);
	}
	
	public boolean validate() {

		boolean valid = true;
		
		form.clearErrors(true);
		
		final Record currentRecord = new Record(form.getValues());
		
		if (currentRecord != null) {
			if(currentRecord.getAttributeAsBoolean("flgNoAzione") != null 
					&& currentRecord.getAttributeAsBoolean("flgNoAzione")) {
				return true;
			} else if(currentRecord.getAttributeAsBoolean("flgRestituzioneAlMittente") != null && currentRecord.getAttributeAsBoolean("flgRestituzioneAlMittente")) {
				if(listaRecord.getLength() == 1) {
					String tipoMittUltimoInvioUDOut = listaRecord.get(0).getAttribute("tipoMittUltimoInvioUDOut");
					String idMittUltimoInvioUDOut = listaRecord.get(0).getAttribute("idMittUltimoInvioUDOut");
					if(tipoMittUltimoInvioUDOut == null || "".equals(tipoMittUltimoInvioUDOut) || idMittUltimoInvioUDOut == null || "".equals(idMittUltimoInvioUDOut)) {
						form.setFieldErrors("flgRestituzioneAlMittente", "Nessun mittente valorizzato");
						valid = false;
					}				
				}
			} else if(currentRecord.getAttributeAsBoolean("flgMandaAlMittenteDocumento") != null && currentRecord.getAttributeAsBoolean("flgMandaAlMittenteDocumento")) {
				if(listaRecord.getLength() == 1) {
					String tipoMittIntUDOut = listaRecord.get(0).getAttribute("tipoMittIntUDOut");
					String idMittUDOut = listaRecord.get(0).getAttribute("idMittUDOut");
					if(tipoMittIntUDOut == null || "".equals(tipoMittIntUDOut) || idMittUDOut == null || "".equals(idMittUDOut)) {
						form.setFieldErrors("flgMandaAlMittenteDocumento", "Nessun mittente valorizzato");
						valid = false;
					}
				}
			} else if(currentRecord.getAttributeAsBoolean("flgProtReg") != null && currentRecord.getAttributeAsBoolean("flgProtReg")) {
				RecordList protRegAssegnazione = currentRecord.getAttributeAsRecordList("protRegAssegnazione");
				String gruppo = protRegAssegnazione != null && protRegAssegnazione.getLength() > 0 ? protRegAssegnazione.get(0).getAttribute("gruppo") : null;
				String idUo = protRegAssegnazione != null && protRegAssegnazione.getLength() > 0 ? protRegAssegnazione.get(0).getAttribute("idUo") : null;
				if((gruppo == null || "".equals(gruppo)) && (idUo == null || "".equals(idUo)) ) {
					form.setFieldErrors("flgProtReg", "Obbligatorio selezionare un destinatario");
					valid = false;
				}
			} else if(currentRecord.getAttributeAsBoolean("flgFirma") != null && currentRecord.getAttributeAsBoolean("flgFirma")) {
				RecordList invioFirmaDigitaleAssegnazione = currentRecord.getAttributeAsRecordList("invioFirmaDigitaleAssegnazione");
				String idUo = invioFirmaDigitaleAssegnazione != null && invioFirmaDigitaleAssegnazione.getLength() > 0 ? invioFirmaDigitaleAssegnazione.get(0).getAttribute("idUo") : null;
				if(idUo == null || "".equals(idUo)) {
					form.setFieldErrors("flgFirma", "Obbligatorio selezionare un destinatario");
					valid = false;
				}
			} else if(currentRecord.getAttributeAsBoolean("flgVistoEle") != null && currentRecord.getAttributeAsBoolean("flgVistoEle")) {
				RecordList invioVistoElettronicoAssegnazione = currentRecord.getAttributeAsRecordList("invioVistoElettronicoAssegnazione");
				String idUo = invioVistoElettronicoAssegnazione != null && invioVistoElettronicoAssegnazione.getLength() > 0 ? invioVistoElettronicoAssegnazione.get(0).getAttribute("idUo") : null;
				if(idUo == null || "".equals(idUo)) {
					form.setFieldErrors("flgVistoEle", "Obbligatorio selezionare un destinatario");
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
				if (currentRecord.getAttributeAsBoolean("flgNoAzione") != null
						&& currentRecord.getAttributeAsBoolean("flgNoAzione")) {
					if (callback != null) {
						callback.execute(new Record());
					}
					window.destroy();
				} else {
					// TODO CANCELLARE?
					// String idUdToSave = null;
					// if(listaRecord != null && !listaRecord.isEmpty()){
					// for(int i=0; i < listaRecord.getLength(); i++){
					// String idUd = listaRecord.get(i).getAttribute("idUdFolder");
					// if(idUdToSave == null){
					// idUdToSave = idUd;
					// }else{
					// idUdToSave += ";" + idUd;
					// }
					// }
					// currentRecord.setAttribute("idUd", idUdToSave);
					// }
		
					// Setto anche quanto è arrivato dal chiamante
					currentRecord.setAttribute("listUD", listaRecord);
		
					final ServiceCallback<Record> callbackAfterAzionePostFirma = new ServiceCallback<Record>() {
		
						@Override
						public void execute(Record object) {
							callback.execute(object);
							window.destroy();
						}
					};
		
					GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource");
					lArchivioDatasource.executecustom("azionePostFirma", currentRecord, new DSCallback() {
		
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
		
							// TODO CANCELLARE?
							// if (mapErrorMessages != null && mapErrorMessages.size() >
							// 0) {
							// if(listaRecord.getLength() == 1){
							// String error = null;
							// for(Map.Entry<String, String> item :
							// mapErrorMessages.entrySet()){
							// error = item.getValue();
							// }
							// Layout.addMessage(new MessageBean(error, "",
							// MessageType.ERROR));
							// }else{
							// RecordList listaErrori = new RecordList();
							// for(Map.Entry<String, String> item :
							// mapErrorMessages.entrySet()){
							// Record record = new Record();
							// record.setAttribute("idError", item.getKey());
							// record.setAttribute("descrizione", item.getValue());
							// listaErrori.add(record);
							// }
							// ErroreMassivoPopup erroreMassivo = new
							// ErroreMassivoPopup("azioneSuccessivaFirmaUD",
							// "Estremi documento", listaErrori,
							// mapErrorMessages.size(), 600, 300);
							// erroreMassivo.show();
							// }
							// } else{
							// Layout.addMessage(new MessageBean("Operazione effettuata
							// con successo", "", MessageType.INFO));
							// if(callback != null){
							// Record recordToSend = new Record();
							// Record recordData = new Record();
							// recordData.setAttribute("flgUdFolder", "U"); //Forzo il
							// valore ad U visto che mi trovo in una protocollazione
							// recordData.setAttribute("idUdFolder",
							// currentRecord.getAttribute("idUd"));
							// recordToSend.setAttribute("listaRecordUd", recordData);
							// callback.execute(recordToSend);
							// }
							// window.destroy();
							// }
						}
		
					});
				}
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
