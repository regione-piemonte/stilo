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
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class AzioneApposizionePopup extends ModalWindow {

	private AzioneApposizionePopup window;

	private DynamicForm form;

	// Decommentare queste e i pezzi di codice dove si trovano se si vogliono
	// utilizzare queste checkbox
	// private CheckboxItem appostoAccettatoItem;
	// private CheckboxItem appostoRifiutatoItem;

	private TextAreaItem motivazioneItem;

	private HStack buttons;
	private Button confermaButton;
	private Button annullaButton;
	private ServiceCallback<Record> finalCallback;

	private TipologiaApposizione tipologiaApposizione;

	private RecordList recordSelezionatiPerFirma;

	private boolean daLista = false;
	private String prefTipoFirma;

	// Per identificare se si è cliccato su una checkbox o meno
	// private boolean clickedOnCheck = false;

	private Record detailRecord;
	private RecordList listaRecord;
	private boolean apposizioneForzata;

	/**
	 * Questo costruttore viene chiamato nel momento in cui si cerca di eseguire
	 * l'apposizione ad un documento da dettaglio. In questo caso il campo
	 * record contiene l'ud del documento di cui si sta visualizzando il
	 * dettaglio
	 * 
	 * @param record
	 * @param finalCallback
	 */
	public AzioneApposizionePopup(Record record, Record detailRecord, TipologiaApposizione tipologiaApposizione,
			boolean apposizioneForzata, String prefTipoFirma, ServiceCallback<Record> callbackParam) {

		this(buildRecordListFromRecord(record), detailRecord, tipologiaApposizione, false, apposizioneForzata,
				prefTipoFirma,callbackParam);
	}

	/**
	 * Questo costruttore viene chiamato nel momento in cui si ha un RecordList;
	 * questo è il caso in cui selezioniamo gli elementi dalla lista e ne
	 * richiediamo l'apposizione
	 * 
	 * @param listaRecord
	 *            è un recordList contenente, per ogni elemento, l'idUd che si
	 *            deve elaborare
	 * @param finalCallback
	 */
	public AzioneApposizionePopup(final RecordList listaRecord, Record detailRecord,
			TipologiaApposizione tipologiaApposizione, boolean daLista, boolean apposizioneForzata,
			String prefTipoFirma, final ServiceCallback<Record> callbackParam) {

		super("azioneApposizionePopup", true);

		window = this;

		finalCallback = callbackParam;
		this.tipologiaApposizione = tipologiaApposizione;
		this.daLista = daLista;
		this.prefTipoFirma = prefTipoFirma;
		// detailRecord rappresenta il record del dettaglio di una ud.
		// Necessario nel caso di firma di una singola ud da dettaglio
		this.detailRecord = detailRecord;
		this.listaRecord = listaRecord;
		this.apposizioneForzata = apposizioneForzata;

		if (apposizioneForzata) {
			/*
			 * Allora si è approvato e quindi si può procedere direttamente
			 * senza richiedere la motivazione che non è obbligatoria
			 */
			manageClickOnConfirmButton(true);
		} else {
			/*
			 * Allora si è rifiutata l'apposizione del visto e quindi si
			 * richiede la motivazione che in questo caso è obbligatoria
			 */

			// Impostazioni del form
			setFormConfig();

			// Creo gli item del form
			// createCheckboxItem(); //Decommentare se si vogliono utilizzare le
			// checkbox di apposizione e rifiuto
			createTextItem();

			SpacerItem spacer = new SpacerItem();
			spacer.setColSpan(1);
			spacer.setStartRow(true);

			// Aggiungo gli item al form (contenente anche le checkbox)
			// form.setFields(spacer, appostoAccettatoItem,
			// spacer, appostoRifiutatoItem,
			// motivazioneItem);

			// Aggiungo la text per la motivazione nel form
			form.setFields(motivazioneItem);

			// Creo i due pulsanti
			createButtons();

			// Creo la bottoniera
			buttons = createButtonStack();

			setLayout();
		}

	}

	public DynamicForm getForm() {
		return form;
	}

	private void setLayout() {

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(form);

		portletLayout.addMember(layout);
		portletLayout.addMember(buttons);

		setBody(portletLayout);
	}

	/**
	 * Metodo che setta le configurazioni iniziali del form
	 */
	private void setFormConfig() {

		if (tipologiaApposizione == TipologiaApposizione.VISTO) {
			if (apposizioneForzata) {
				setTitle(I18NUtil.getMessages().apposizioneVisto_popup_title());
				setIcon("ok.png");
			} else {
				setTitle(I18NUtil.getMessages().rifiutoApposizioneVisto_popup_title());
				setIcon("ko.png");
			}
		} else if (tipologiaApposizione == TipologiaApposizione.FIRMA) {
			if (apposizioneForzata) {
				setTitle(I18NUtil.getMessages().apposizioneFirma_popup_title());
				setIcon("file/mini_sign.png");
			} else {
				setTitle(I18NUtil.getMessages().rifiutoApposizioneFirma_popup_title());
				setIcon("file/rifiuto_apposizione.png");
			}
		}

		setAutoCenter(true);
		setAlign(Alignment.CENTER);
		setTop(50);

		// Per settare gli elementi da inserire nell'header
		// setHeaderControls(HeaderControls.HEADER_ICON,
		// HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);

		// Per la rimozione degli elementi dal pulsante con la rotellina in alto
		// a destra
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				if (finalCallback != null) {
					finalCallback.execute(new Record());
				}
				markForDestroy();
			}
		});

		form = new DynamicForm();
		form.setKeepInParentRect(true);
		form.setWidth100();
		form.setHeight100();
		form.setNumCols(4);
		form.setColWidths("1", "*", "*", "*");
		form.setCellPadding(5);
		form.setWrapItemTitles(false);
	}

	/**
	 * Metodo che crea i pulsanti
	 * 
	 * @param listaRecord
	 */
	private void createButtons() {

		confermaButton = new Button(I18NUtil.getMessages().apposizione_button_salva());
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				manageClickOnConfirmButton();
			}
		});

		annullaButton = new Button(I18NUtil.getMessages().apposizione_button_annulla());
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				// Chiudo la finestra
				markForDestroy();
			}
		});

	}

	/**
	 * Metodo che crea lo stack di pulsanti
	 * 
	 * @return
	 */
	private HStack createButtonStack() {
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);
		
		return _buttons;
	}

	/**
	 * Metodo che crea le checkbox
	 */
	// private void createCheckboxItem() {
	//
	// String titleAppostoAccettatoItem = "";
	//
	// if(tipologiaApposizione == TipologiaApposizione.FIRMA){
	// //Nel caso di firma
	// titleAppostoAccettatoItem =
	// I18NUtil.getMessages().apposizione_label_apposta_firma();
	// }else if(tipologiaApposizione == TipologiaApposizione.VISTO){
	// //Nel caso di visto
	// titleAppostoAccettatoItem =
	// I18NUtil.getMessages().apposizione_label_apposto_visto();
	// }
	//
	// appostoAccettatoItem = new CheckboxItem("flgAppostoAccettato",
	// titleAppostoAccettatoItem);
	// appostoAccettatoItem.setStartRow(false);
	// appostoAccettatoItem.setEndRow(true);
	// appostoAccettatoItem.setColSpan(2);
	// appostoAccettatoItem.addChangeHandler(new ChangeHandler() {
	//
	// @Override
	// public void onChange(ChangeEvent event) {
	// clickOnCheckbox(event.getItem().getName());
	// }
	// });
	//
	//
	// String titleAppostoRifiutatoItem = "";
	// if(tipologiaApposizione == TipologiaApposizione.FIRMA){
	// //Nel caso di firma
	// titleAppostoRifiutatoItem =
	// I18NUtil.getMessages().apposizione_label_rifiutata_firma();
	// }else if(tipologiaApposizione == TipologiaApposizione.VISTO){
	// //Nel caso di visto
	// titleAppostoRifiutatoItem =
	// I18NUtil.getMessages().apposizione_label_rifiutato_visto();
	// }
	//
	// appostoRifiutatoItem = new CheckboxItem("flgAppostoRifiutato",
	// titleAppostoRifiutatoItem);
	// appostoRifiutatoItem.setStartRow(false);
	// appostoRifiutatoItem.setEndRow(true);
	// appostoRifiutatoItem.setColSpan(2);
	// appostoRifiutatoItem.addChangeHandler(new ChangeHandler() {
	//
	// @Override
	// public void onChange(ChangeEvent event) {
	// clickOnCheckbox(event.getItem().getName());
	// }
	// });
	// }

	/**
	 * Metodo che crea la textItem delle motivazioni
	 */
	private void createTextItem() {

		motivazioneItem = new TextAreaItem("messaggioAppostoRifiutato", "Motivazione");
		motivazioneItem.setStartRow(true);
		motivazioneItem.setEndRow(true);
		motivazioneItem.setLength(4000);
		motivazioneItem.setColSpan(2);
		motivazioneItem.setHeight(200);
		motivazioneItem.setWidth(600);
		motivazioneItem.setRequired(true);

	}

	// private void clickOnCheckbox(String nameCheckboxClicked){
	//
	// //Identifica il fatto che si è cliccato su una checkbox
	// clickedOnCheck = true;
	//
	// /*
	// * Se la checkbox selezionata è diversa da quella appena cliccata allora
	// * tolgo il check dalle altre e seleziono quest'ultima per ottenere un
	// * gruppo di checkbox esclusivo
	// */
	// appostoAccettatoItem.setValue(appostoAccettatoItem.getName().equals(nameCheckboxClicked)
	// ? true : false);
	// appostoRifiutatoItem.setValue(appostoRifiutatoItem.getName().equals(nameCheckboxClicked)
	// ? true : false);
	//
	// /*
	// * Eseguo la validazione della textArea in modo tale che compaia o meno
	// * il !
	// */
	// motivazioneItem.validate();
	// }

	/**
	 * Questo metodo crea un recordList a partire dal record
	 * 
	 * @param record
	 * @return
	 */
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

	/**
	 * Metodo che gestisce il comportamento da tenere al click su Salva in base
	 * alla topologia di apposizione che si sta eseguendo
	 * 
	 * @param listaRecordUd
	 */
	private void manageClickOnSave(final RecordList listaRecordUd) {
		if (listaRecordUd.isEmpty()) {
			Layout.addMessage(new MessageBean("Non sono stati selezionati documenti", "", MessageType.ERROR));
		} else {
			if (tipologiaApposizione == TipologiaApposizione.VISTO) {
				manageApposizioneVisto(listaRecordUd, new ServiceCallback<Record>() {

					@Override
					public void execute(Record response) {
						if (response != null) {
							openAzioneSuccessivaPopup(apposizioneForzata);
							// !appostoRifiutatoItem.getValueAsBoolean()
						}
					}

				});
			} else if (tipologiaApposizione == TipologiaApposizione.FIRMA) {
				manageApposizioneFirma(listaRecordUd);
			}

		}
	}

	private void manageApposizioneVisto(final RecordList listaRecordUd,
			final ServiceCallback<Record> callbackAfterApposizioneVisto) {

		// Prelevo i record delle ud che si vogliono firmare. Questa variabile
		// serve in seguito
		recordSelezionatiPerFirma = listaRecordUd;

		// Ci sono dei documenti che devono essere contrassegnati come visti.
		Record record = new Record();
		record.setAttribute("listaRecord", listaRecordUd);

		// Controllo quale checkbox è stata selezionata
		// if(appostoRifiutatoItem.getValueAsBoolean()){
		// /*
		// * è stata selezionata la checkbox per rifiutare di apporre il visto
		// * Inserisco nel record da mandare alla stored tale esito
		// */
		// record.setAttribute("esito", "rifiutato");
		// }else{
		// record.setAttribute("esito", "apposto");
		// }

		// Controllo se si è selezionata l'apposizione o meno del visto/firma
		if (!apposizioneForzata) {
			/*
			 * è stata selezionata la checkbox per rifiutare di apporre il visto
			 * Inserisco nel record da mandare alla stored tale esito
			 */
			record.setAttribute("esito", "rifiutato");
		} else {
			record.setAttribute("esito", "apposto");
		}

		// Imposto la motivazione
		record.setAttribute("motivazione", getMotivazioneText());

		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder",
				FieldType.TEXT);
		lArchivioDatasource.executecustom("vistoDocumento", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					manageResponse(listaRecordUd, response);
				}
			}

			/**
			 * Metodo per la gestione del risultato nell'apposizione del visto.
			 * Vengono qui gestiti eventuali errori oppure il buon esito
			 * dell'operazione
			 * 
			 * @param listaRecordUd
			 * @param response
			 */
			private void manageResponse(final RecordList listaRecordUd, DSResponse response) {

				// Prelevo i valori che sono stati passati dalla store
				Record result = response.getData()[0];
				Map<String, String> mapErrorMessages = result.getAttributeAsMap("errorMessages");
				recordSelezionatiPerFirma = result.getAttributeAsRecordList("listaRecord");

				manageError("azioneVisto", callbackAfterApposizioneVisto, listaRecordUd, mapErrorMessages);
			}
		});
	}

	private void manageApposizioneFirma(final RecordList listaRecordUd) {

		// Ci sono dei documenti che devono essere contrassegnati con l'apposto
		// o meno.
		final Record recordApposizioneDetails = new Record();
		recordApposizioneDetails.setAttribute("listaRecord", listaRecordUd);

		// Controllo quale checkbox è stata selezionata
		// if(appostoRifiutatoItem.getValueAsBoolean()){
		// /*
		// * è stata selezionata la checkbox per rifiutare l'apposizione
		// * Inserisco nel record da mandare alla stored tale esito
		// */
		// recordApposizioneDetails.setAttribute("esito", "rifiutato");
		// }else{
		// recordApposizioneDetails.setAttribute("esito", "apposto");
		// }

		// Controllo se si è selezionata l'apposizione o meno del visto/firma
		if (!apposizioneForzata) {
			/*
			 * è stata selezionata la checkbox per rifiutare di apporre il visto
			 * Inserisco nel record da mandare alla stored tale esito
			 */
			recordApposizioneDetails.setAttribute("esito", "rifiutato");
		} else {
			recordApposizioneDetails.setAttribute("esito", "apposto");
		}

		// Imposto la motivazione
		recordApposizioneDetails.setAttribute("motivazione", getMotivazioneText());

		/*
		 * Se si è accettata l'apposizione allora si deve eseguire la firma,
		 * altrimenti si passa alla fase successiva
		 */
		// if(appostoAccettatoItem.getValueAsBoolean()){
		if (apposizioneForzata) {
			/*
			 * Se il parametro daLista vale true allora vuol dire che si è
			 * arrivati a questo popup dalla lista (come azione massiva),
			 * altrimenti si arriva da dettaglio
			 */
			if (daLista) {

				// Eseguo la chiamata per eseguire la firma massiva da lista
				firmaMassiva(recordApposizioneDetails, new ServiceCallback<Record>() {

					@Override
					public void execute(Record response) {

						// Se ci sono elementi da considerare li seleziono per
						// la successiva azione
						if (recordSelezionatiPerFirma != null && recordSelezionatiPerFirma.getLength() != 0) {

							// La lista potrebbe essere stata aggiornata per via
							// di eventuali errori, quindi la setto nuovamente
							recordApposizioneDetails.setAttribute("listaRecord", recordSelezionatiPerFirma);

							callFirmatoDocumento(recordApposizioneDetails, recordSelezionatiPerFirma,
									new ServiceCallback<Record>() {

										@Override
										public void execute(Record response) {

											// Se response è diverso da null
											// vuol dire che qualcosa deve
											// essere mandato alla scelta
											// dell'azione successiva
											if (response != null) {
												openAzioneSuccessivaPopup(true); // true
																					// perchè
																					// si
																					// è
																					// eseguita
																					// l'apposizione
											}
										}
									});
						}
					}
				});

			} else {

				manageFirmaSingola(recordApposizioneDetails, new ServiceCallback<Record>() {

					@Override
					public void execute(Record response) {

						callFirmatoDocumento(recordApposizioneDetails, listaRecordUd, new ServiceCallback<Record>() {

							@Override
							public void execute(Record response) {

								if (response != null) {
									// Prelevo i record da mandare all'azione
									// successiva
									// RecordList
									// recordToSendToAzioneSuccessivaPopup =
									// response.getAttributeAsRecordList("listaRecordUd");
									openAzioneSuccessivaPopup(true); // true
																		// perchè
																		// si è
																		// eseguita
																		// l'apposizione
								}
							}
						});

					}
				});
			}
		} else {
			/*
			 * Se si è selezionata la voce di rifiuto si deve chiamare la stored
			 * per la rimozione dalla lista
			 */
			callFirmatoDocumento(recordApposizioneDetails, listaRecordUd, new ServiceCallback<Record>() {

				@Override
				public void execute(Record response) {

					if (response != null) {

						// Prelevo i record da mandare all'azione successiva
						openAzioneSuccessivaPopup(false); // false perchè si è
															// eseguito il
															// rifiuto
					}

				}
			});
		}

	}

	private void callFirmatoDocumento(Record record, final RecordList listaRecordUd,
			final ServiceCallback<Record> callbackAfterFirmatoDocumento) {
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder",
				FieldType.TEXT);
		lArchivioDatasource.executecustom("firmatoDocumento", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

					manageResponse(listaRecordUd, response);
				}
			}

			/**
			 * Metodo per la gestione del risultato dell'apposizione. Vengono
			 * qui gestiti eventuali errori oppure il buon esito dell'operazione
			 * 
			 * @param listaRecordUd
			 * @param response
			 */
			private void manageResponse(final RecordList listaRecordUd, DSResponse response) {

				// Prelevo i valori che sono stati passati dalla store
				Record result = response.getData()[0];
				Map<String, String> mapErrorMessages = result.getAttributeAsMap("errorMessages");
				recordSelezionatiPerFirma = result.getAttributeAsRecordList("listaRecord");

				manageError("azioneFirma", callbackAfterFirmatoDocumento, listaRecordUd, mapErrorMessages);
			}

		});
	}

	private void openAzioneSuccessivaPopup(boolean apposizioneStatus) {

		// Apro il popup che permette di scegliere l'azione successiva
		AzioneSuccessivaPopup popup = new AzioneSuccessivaPopup(recordSelezionatiPerFirma, apposizioneStatus,
				tipologiaApposizione, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {

						// Torno al chiamante che aggiorna la lista
						if (finalCallback != null) {
							finalCallback.execute(new Record());
						}
					}
				});
		popup.show();
	}

	/**
	 * Metodo che esegue la validazione del form. Controlla se è inserita la
	 * motivazione o meno in base a che option è stata selezionata. Ricordare
	 * che: SE IMPOSTO IL FLAG DI RIFIUTO ALLORA E' OBBLIGATORIA UNA MOTIVAZIONE
	 * 
	 * @return
	 */
	private boolean validateForm() {

		// Prelevo la motivazione inserita
		String motivazioneText = motivazioneItem.getValueAsString();
		if (motivazioneText != null && !motivazioneText.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo che restituisce il testo contenuto all'interno della textArea
	 * della motivazione
	 * 
	 * @return
	 */
	private String getMotivazioneText() {
		if (motivazioneItem != null && motivazioneItem.getValueAsString() != null
				&& !"".equals(motivazioneItem.getValueAsString())) {
			return motivazioneItem.getValueAsString();
		} else {
			return "";
		}
	}

	/**
	 * Metodi per la gestione della firma
	 * 
	 * @param lRecordSelezionati
	 */

	/**
	 * Questo metodo viene chiamato nel momento in cui si cerca di eseguire la
	 * FIRMA MASSIVA DA LISTA
	 * 
	 * @param lRecordSelezionati
	 */
	public void firmaMassiva(Record lRecordSelezionati, final ServiceCallback<Record> callbackManageFirma) {

		// Prelevo i record delle ud che si vogliono firmare. Questa variabile
		// serve in seguito
		recordSelezionatiPerFirma = lRecordSelezionati.getAttributeAsRecordList("listaRecord");

		/**
		 * Modifica alla chiamata del datasource per firmare anche gli allegati
		 * oltre al primario
		 * 
		 * Eseguo la chiamata a questo datasource per ottenere la lista dei file
		 * che devono essere firmati
		 */
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder",
				FieldType.TEXT);
		lArchivioDatasource.addParam("prefTipoFirma", prefTipoFirma);
		lArchivioDatasource.performCustomOperation("getListaPerFirmaMassivaConAllegati", lRecordSelezionati,
				new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							// Sono stati ritornati i file che devono essere firmati
							if(response.getData()[0].getAttributeAsRecordList("files") != null &&
									!response.getData()[0].getAttributeAsRecordList("files").isEmpty()) {
								firmaFileMassiva(response.getData()[0], callbackManageFirma);
							}
						}
					}
				}, new DSRequest());
	}

	/**
	 * Metodo che avvia il modulo di firma e che gestisce la relativa callback
	 * 
	 * @param record
	 */
	private void firmaFileMassiva(Record record, final ServiceCallback<Record> callbackManageFirma) {

		// Avvio l'applet per eseguire la firma
		FirmaUtility.firmaMultipla(record.getAttributeAsRecordArray("files"), new FirmaMultiplaCallback() {

			@Override
			public void execute(Map<String, Record> files, Record[] filesAndUd) {

				manageFirmaMassivaCallBack(files, filesAndUd, callbackManageFirma);
			}
		});
	}

	/**
	 * Metodo che gestisce la callback dalla firma massiva
	 * 
	 * @param files
	 * @param filesAndUd
	 */
	protected void manageFirmaMassivaCallBack(final Map<String, Record> files, final Record[] filesAndUd, final ServiceCallback<Record> callbackManageFirma) {

		if (recordSelezionatiPerFirma.getLength() == 1) {
			Layout.showWaitPopup("Elaborazione del documento firmato in corso...");
		} else {
			Layout.showWaitPopup("Elaborazione dei documenti firmati in corso...");
		}

		// Inserisco all'interno di un record i file che devono essere elaborati
		final Record lRecord = new Record();
		Record[] lRecords = new Record[files.size()];
		int i = 0;
		for (String lString : files.keySet()) {
			Record recordToInsert = files.get(lString);
			for (Record recordConUd : filesAndUd) {
				if (recordConUd.getAttribute("idFile").equals(lString)) {
					recordToInsert.setAttribute("idUd", recordConUd.getAttribute("idUd"));
//					recordToInsert.setAttribute("uriVerPreFirma", recordConUd.getAttribute("uri"));
//					recordToInsert.setAttribute("nomeFileVerPreFirma", recordConUd.getAttribute("nomeFile"));
//					InfoFileRecord infoFile = recordConUd.getAttribute("infoFile") != null ? new InfoFileRecord(recordConUd.getAttribute("infoFile")) : null;
//					recordToInsert.setAttribute("infoFileVerPreFirma", infoFile);
				}//TODO brek
			}
			lRecords[i] = recordToInsert;
			i++;
		}
		lRecord.setAttribute("files", lRecords);

		/**
		 * Chiamata al datasource per eseguire l'aggiornamento dei file
		 */
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.performCustomOperation("aggiornaDocumentoFirmato", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lCallbackRecord = response.getData()[0];
					Record[] lRecordsInError = lCallbackRecord.getAttributeAsRecordArray("files");
					List<String> documentiInError = new ArrayList<String>();
					final List<String> idUdDocumentiInError = new ArrayList<String>();
					if (lRecordsInError.length > 0) {
						for (Record lRecordInError : lRecordsInError) {
							String idDoc = lRecordInError.getAttribute("idFile");
							String idUdInError = findRecordFromIdDoc(idDoc, filesAndUd);
							for (int i = 0; i < recordSelezionatiPerFirma.getLength(); i++) {
								idUdDocumentiInError.add(idUdInError);
								Record lRecordSelected = recordSelezionatiPerFirma.get(i);
								String idUd = lRecordSelected.getAttribute("idUdFolder");
								if (idUd.equals(idUdInError)) {
									String estremi = lRecordSelected.getAttribute("segnatura");
									documentiInError.add(estremi);
								}
							}
						}
						if (documentiInError.size() > 0) {
							if (documentiInError.size() == files.size()) {
								if (documentiInError.size() == 1) {
									Layout.addMessage(new MessageBean("Il documento selezionato non è stato firmato correttamente", "", MessageType.ERROR));
								} else {
									Layout.addMessage(new MessageBean("Tutti i documenti selezionati non sono stati firmati correttamente", "",	MessageType.ERROR));
								}
							} else {
								String message = null;
								if (documentiInError.size() == 1) {
									message = "Il documento " + documentiInError.get(0) + " non è stato firmato correttamente";
								} else {
									message = "I documenti ";
									boolean first = true;
									for (String lStr : documentiInError) {
										if (first)
											first = false;
										else
											message += ", ";
										message += lStr;
									}
									message += " non sono stati firmati correttamente";
								}
								Layout.addMessage(new MessageBean(message, "", MessageType.WARNING));
								/*
								 * Record da ritornare alla callback contentente
								 * gli id delle ud che devono essere inviati
								 * all'azione successiva; che quindi NON sono
								 * andati in errore
								 */
								getUdToSendToAzioneSuccessivaPopup(idUdDocumentiInError);
								callbackManageFirma.execute(new Record());
							}
						}
					} else {
						if (recordSelezionatiPerFirma.getLength() == 1) {
							Layout.addMessage(new MessageBean("Il documento selezionato è stato firmato con successo", "", MessageType.INFO));
						} else {
							Layout.addMessage(new MessageBean("I documenti selezionati sono stati firmati con successo", "", MessageType.INFO));
						}
						/*
						 * Record da ritornare alla callback contentente gli id
						 * delle ud che devono essere inviati all'azione
						 * successiva; che quindi NON sono andati in errore
						 */
						callbackManageFirma.execute(new Record());
					}
				} else {
					Layout.addMessage( new MessageBean("Si è verificato un errore durante la firma", "", MessageType.ERROR));
				}
			}
		}, new DSRequest());
	}

	/**
	 * Metodo che, in base alle ud che sono andate in errore nell'operazione
	 * precedente, determina quali sono i nuovi record su cui lavorare
	 * 
	 * @param idUdDocumentiInError
	 */
	private void getUdToSendToAzioneSuccessivaPopup(List<String> idUdDocumentiInError) {

		final RecordList listaRecordAzioneSuccessiva = new RecordList();
		for (int i = 0; i < recordSelezionatiPerFirma.getLength(); i++) {
			String idUd = recordSelezionatiPerFirma.get(i).getAttribute("idUdFolder");

			// Per ogni ud dei record che sono stati selezionati per la firma
			// controllo se è tra gli elementi che sono andati in errore
			if (!idUdDocumentiInError.contains(idUd)) {

				// Il record è stato elaborato con successo
				Record record = new Record();
				record.setAttribute("idUdFolder", idUd);
				listaRecordAzioneSuccessiva.add(record);
			}
		}

		/*
		 * Inserisco tali elementi nella lista visibile a livello globale Se
		 * listaRecordAzioneSuccessiva è vuota allora vuol dire che nessun
		 * record è stato elaborato con successo e quindi è giusto che il valore
		 * venga assegnato a recordSelezionatiPerFirma che rappresenta i record
		 * che andranno all'azione successiva. Se invece ha dei valori viene
		 * comunque aggiornata tale variabile e i rispettivi valori
		 */
		recordSelezionatiPerFirma = listaRecordAzioneSuccessiva;
	}

	private String findRecordFromIdDoc(String idDoc, Record[] filesAndUd) {
		for (Record lRecord : filesAndUd) {
			if (idDoc.equals(lRecord.getAttribute("idFile"))) {
				return lRecord.getAttribute("idUd");
			}
		}
		return null;
	}

	/**
	 * 
	 * GESTIONE FIRMA SINGOLA
	 */
	public void manageFirmaSingola(Record lRecordSelezionati, final ServiceCallback<Record> callbackManageFirma) {

		if (detailRecord != null) {

			final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder",
					FieldType.TEXT);
			lArchivioDatasource.performCustomOperation("getListaPerFirmaSingolaConAllegati", detailRecord,
					new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {

								clickFirmaSingolaFile(response.getData()[0], callbackManageFirma);
							}
						}
					}, new DSRequest());
		}
	}

	private void clickFirmaSingolaFile(Record record, final ServiceCallback<Record> callbackManageFirma) {

		if (record.getAttributeAsRecordArray("files").length == 0) {
			Layout.addMessage(new MessageBean("Nessun file da firmare", "", MessageType.ERROR));
		} else {
			FirmaUtility.firmaMultipla(record.getAttributeAsRecordArray("files"), new FirmaMultiplaCallback() {

				@Override
				public void execute(Map<String, Record> files, Record[] filesAndUd) {

					manageFirmaCallBack(files, filesAndUd, callbackManageFirma);
				}
			});
		}
	}

	protected void manageFirmaCallBack(final Map<String, Record> files, final Record[] filesAndUd, final ServiceCallback<Record> callbackManageFirma) {

		Layout.showWaitPopup("Elaborazione del documento firmato in corso...");
		final Record lRecord = new Record();
		final Record[] lRecords = new Record[files.size()];
		int i = 0;
		for (String lString : files.keySet()) {
			Record recordToInsert = files.get(lString);
			for (Record recordConUd : filesAndUd) {
				if (recordConUd.getAttribute("idFile").equals(lString)) {
					recordToInsert.setAttribute("idUd", recordConUd.getAttribute("idUd"));
//					recordToInsert.setAttribute("uriVerPreFirma", recordConUd.getAttribute("uri"));
//					recordToInsert.setAttribute("nomeFileVerPreFirma", recordConUd.getAttribute("nomeFile"));
//					InfoFileRecord infoFile = recordConUd.getAttribute("infoFile") != null ? new InfoFileRecord(recordConUd.getAttribute("infoFile")) : null;
//					recordToInsert.setAttribute("infoFileVerPreFirma", infoFile);
				}
			}
			lRecords[i] = recordToInsert;
			i++;
		}
		lRecord.setAttribute("files", lRecords);
		
		final GWTRestDataSource lArchivioDatasource = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
		lArchivioDatasource.performCustomOperation("aggiornaDocumentoFirmato", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					//TODO
					Record lCallbackRecord = response.getData()[0];
					Record[] lRecordsInError = lCallbackRecord.getAttributeAsRecordArray("files");
					if (lRecordsInError.length > 0 && lRecordsInError.length == lRecords.length) {
						// La firma di TUTTI i file è andata in errore
						Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma del documento", "", MessageType.ERROR));
						return;
					} else if (lRecordsInError.length > 0) {
						// La firma di ALCUNI file è andata in errore
						// Layout.addMessage(new MessageBean("Alcuni file del
						// documento non sono stati firmati correttamente", "",
						// MessageType.WARNING));
						SC.ask("Alcuni file del documento non sono stati firmati correttamente. Continuare comunque con l'azione successiva?",
								new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										if (value) {
											callbackManageFirma.execute(new Record());
										}
									}
								});
					} else if (lRecordsInError.length == 0) {
						// Il documento è stato firmato correttamente
						Layout.addMessage(new MessageBean("Il documento è stato firmato con successo", "", MessageType.INFO));
						callbackManageFirma.execute(new Record());
					}
				} else {
					Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma del documento", "", MessageType.ERROR));
				}
			}
		}, new DSRequest());
	}

	public void manageAzioneSuccessivaFirmaSingola(final ServiceCallback<Record> callbackManageFirma) {
		// AzioneSuccessivaPopup popup = new AzioneSuccessivaPopup(detailRecord,
		// !appostoRifiutatoItem.getValueAsBoolean(), tipologiaApposizione, new
		// ServiceCallback<Record>() {
		AzioneSuccessivaPopup popup = new AzioneSuccessivaPopup(detailRecord, apposizioneForzata, tipologiaApposizione,
				new ServiceCallback<Record>() {

					@Override
					public void execute(Record response) {
						callbackManageFirma.execute(response);
					}
				});
		popup.show();
	}

	private void manageClickOnConfirmButton() {
		manageClickOnConfirmButton(false);
	}

	private void manageClickOnConfirmButton(boolean skipValidate) {
		// if(clickedOnCheck){

		if (skipValidate || motivazioneItem.validate()) {

			// Chiudo la finestra
			markForDestroy();

			// Se si è cliccato su una checkbox allora si può procedere con il
			// salvataggio
			manageClickOnSave(listaRecord);

		} else {
			Layout.addMessage(new MessageBean("Per procedere indicare una motivazione", "", MessageType.WARNING));
		}

		// }else{
		// //Altrimenti viene generato un messaggio poichè non si è selezionata
		// nessuna checkbox
		// Layout.addMessage(new MessageBean("Non è stata selezionata
		// un'operazione da eseguire", "", MessageType.WARNING));
		// }
	}
	
	/**
	 * Metodo che controlla lo stato degli errori che sono stati ritornati
	 * dall'apposizione di visto o di firma e determina quali sono i record che
	 * devono andare all'operazione successiva Inoltre viene ritornato un
	 * RecordList contenente la mappa degli errori da visualizzare
	 * 
	 * @param mapErrorMessages
	 *            la mappa degli errori generati dall'operazione precedente
	 * @return la RecordList degli errori da visualizzare
	 */
	private RecordList getListaErrori(Map<String, String> mapErrorMessages) {

		RecordList newRecordSelezionatiPerFirma = new RecordList();
		RecordList listaErrori = new RecordList();

		// Per ogni record che è selezionato per l'operazione controllo se è
		// stato generato un errore
		for (int indexRecordList = 0; indexRecordList < recordSelezionatiPerFirma.getLength(); indexRecordList++) {

			String segnatura = "";
			String value = "";
			// Per ogni errore che è stato generato
			for (Map.Entry<String, String> itemMessageError : mapErrorMessages.entrySet()) {

				if (recordSelezionatiPerFirma.get(indexRecordList).getAttribute("idUdFolder")
						.contains(itemMessageError.getKey())) {
					// Se il record selezionato è andato in errore
					segnatura = recordSelezionatiPerFirma.get(indexRecordList).getAttribute("segnatura");
					value = itemMessageError.getValue();
					break;
				}
			}

			if ("".equals(segnatura)) {
				// Allora non è stato generato nessun errore e quindi aggiungo
				// tale record a quelli andati a buon fine
				newRecordSelezionatiPerFirma.add(recordSelezionatiPerFirma.get(indexRecordList));
			} else {
				// Il record è uno di quelli che è andato in errore e quindi lo
				// inserisco nella relativa lista
				Record record = new Record();
				record.setAttribute("idError", segnatura);
				record.setAttribute("descrizione", value);

				listaErrori.add(record); // Aggiungo il record alla lista degli errori
			}
		}

		/*
		 * Se dalla gestione degli errori è stata modificata la recordList delle
		 * ud da analizzare la assegno alla rispettiva variabile
		 */
		if (!newRecordSelezionatiPerFirma.isEmpty()) {
			recordSelezionatiPerFirma = newRecordSelezionatiPerFirma;
		}

		return listaErrori;
	}

	/**
	 * Metodo per la gestione degli errori indicati all'interno della
	 * listaErrori Visualizza i messaggi di errore e, al termine
	 * dell'esecuzione, ritorna alla callback indicata
	 * 
	 * @param callbackAfterManageError
	 *            callback da richiamare al termine dell'operazione
	 * @param listaRecordUd
	 * @param mapErrorMessages
	 * @param listaErrori
	 */
	private void manageError(String nomeAzione, final ServiceCallback<Record> callbackAfterManageError, final RecordList listaRecordUd,
			Map<String, String> mapErrorMessages) {

		// Controllo lo stato degli errori
		final RecordList listaErrori = getListaErrori(mapErrorMessages);

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
			error = listaErrori.get(0).getAttribute("idError") + " - " + listaErrori.get(0).getAttribute("descrizione");

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
			ErroreMassivoPopup erroreMassivo = new ErroreMassivoPopup(nomeAzione, "Estremi documento", listaErrori,
					listaErrori.getLength(), 600, 300, new ServiceCallback<Record>() {

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
