/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.DocumentDetail;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtEntrataItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtInternaItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.OperazioniEffettuateWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.AltreVieItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.EsibentiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.InteressatiItem;
import it.eng.auriga.ui.module.layout.shared.util.TipoRichiedente;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class RichiestaAccessoAttiDetail extends DocumentDetail {

	protected RichiestaAccessoAttiDetail instance;

	private final int TITTLE_ALIGN_WIDTH = 130;
	private final int TITTLE_ALIGN_WIDTH_2 = 178;
	private final int ALT_POPUP_ERR_MASS = 300;
	private final int LARG_POPUP_ERR_MASS = 600;

	// VLayout
	private VLayout lVLayoutMain;

	// DetailSection
	protected RichiestaAccessoAttiDetailSection estremiRichiestaSection;
	protected RichiestaAccessoAttiDetailSection indirizzoRiferimentoSection;
	protected RichiestaAccessoAttiDetailSection attiRichiestiSection;
	protected RichiestaAccessoAttiDetailSection richiedenteEsternoSection;
	protected RichiestaAccessoAttiDetailSection richiedenteInternoSection;
	protected RichiestaAccessoAttiDetailSection richiedenteDelegatoDaSection;
	protected RichiestaAccessoAttiDetailSection esibentiSection;
	protected RichiestaAccessoAttiDetailSection motivoRichiestaSection;
	protected RichiestaAccessoAttiDetailSection incaricatoDelPrelievoSection;

	// DynamicForm
	protected DynamicForm tipoProtocollazioneForm;
	protected DynamicForm estremiRichiestaForm;
	protected DynamicForm richiedenteInternoForm;
	protected DynamicForm indirizzoRiferimentoForm;
	protected DynamicForm attiRichiestiForm;
	protected DynamicForm richiedenteEsternoForm;
	protected DynamicForm richiedenteDelegatoDaForm;
	protected DynamicForm esibentiForm;
	protected DynamicForm motivoRichiestaForm;
	protected DynamicForm incaricatoPrelievoUffRichiedenteForm;
	protected DynamicForm incaricatoPrelievoPerRichEsternoForm;
	protected DynamicForm datiAppuntamentoForm;
	protected DynamicForm datiPrelievoForm;

	// Item
	protected HiddenItem idUdItem;
	protected HiddenItem flgTipoProvItem;
	protected HiddenItem idEmailArrivoItem;
	protected HiddenItem mezzoTrasmissioneItem;
	protected HiddenItem tipoDocumentoItem;
	protected HiddenItem nomeTipoDocumentoItem;
	protected HiddenItem flgTipoDocConVieItem;
	protected HiddenItem codStatoRichAccessoAttiItem;
	protected HiddenItem idNodoDefaultRicercaAttiItem;
	protected RadioGroupItem tipoRichiedenteItem;
	protected ExtendedTextItem nroProtocolloRichiestaItem; // Numero del protocollo della richiesta
	protected AnnoItem annoProtocolloRichiestaItem; // Anno del protocollo della richiesta
	protected ExtendedDateItem dataProtocolloRichiestaItem; // Data del protocollo della richiesta
	protected ImgButtonItem visualizzaDettStdProtButton; // Visualizza il dettaglio del protocollo nella modalita standard
	protected ImgButtonItem lookupArchivioButton; // Lookup per caricare il documento essistente
	protected ImgButtonItem operazioniEffettuateButton;
	protected HiddenItem siglaPraticaSuSistUfficioRichiedenteItem; // Sigla della pratica sui sistemi ufficio richiedente
	protected TextItem numeroPraticaSuSistUfficioRichiedenteItem; // Numero della pratica sui sistemi ufficio richiedente
	protected AnnoItem annoPraticaSuSistUfficioRichiedenteItem; // Anno della pratica sui sistemi ufficio richiedente
	protected TextItem desStatoRichAccessoAttiItem; // Anno della pratica sui sistemi ufficio richiedente

	protected MittenteProtInternaItem richiedentiInterniItem; // Richiedente interno

	protected AltreVieItem indirizzoRiferimentoItem; // Via di riferimento

	protected CheckboxItem flgRichAttiFabbricaVisuraSueItem;
	protected CheckboxItem flgRichModificheVisuraSueItem;
	protected AttiRichiestiItem attiRichiestiItem;

	protected MittenteProtEntrataItem richiedentiEsterniItem; // Richiedente esterno

	protected InteressatiItem richiedentiDelegatiDaItem; // Richiedente delegato da

	protected EsibentiItem esibentiItem;

	protected SelectItem motivoRichiestaItem; // Motivo della richiesta
	protected TextAreaItem dettagliRichiestaItem; // Dettagli della richiesta

	protected SelezionaUtenteItems incaricatoPrelPerUffRichiedenteItems;
	protected SelezionaUtenteItems incaricatoPrelievoPerRichEsternoItem;
	protected TextItem dataAppuntamentoItem;
	protected TextItem orarioAppuntamentoItem;
	protected TextItem provenienzaAppuntamentoItem;
	protected TextItem dataPrelievoItem;
	protected TextItem dataRestituzionePrelievoItem;
	protected TextItem restituzionePrelievoAttestataDaItem;

	// Toolstrip contenente i bottoni di dettaglio
	protected ToolStrip detailToolStrip;
	protected DetailToolStripButton editButton;
	protected DetailToolStripButton saveButton;
	protected DetailToolStripButton reloadDetailButton;
	protected DetailToolStripButton undoButton;

	/*
	 * Creato questo flag come "semaforo" di accesso al pulsante
	 * di salvataggio
	 */
	private boolean abilSaveButton = true;

	// Bottoni delle azioni relative alle richieste di accesso
	protected DetailToolStripButton invioInApprovazioneButton;
	protected DetailToolStripButton approvazioneButton;
	protected DetailToolStripButton invioEsitoVerificaArchivioButton;
	protected DetailToolStripButton abilitaAppuntamentoDaAgendaButton;
	protected DetailToolStripButton setAppuntamentoButton;
	protected DetailToolStripButton annullamentoAppuntamentoButton;
	protected DetailToolStripButton registraPrelievoButton;
	protected DetailToolStripButton registraRestituzioneButton;
	protected DetailToolStripButton annullamentoButton;
	protected DetailToolStripButton stampaFoglioPrelievoButton;
	protected DetailToolStripButton chiusuraButton;
	protected DetailToolStripButton riaperturaButton;
	protected DetailToolStripButton ripristinoButton;
	
	protected Map initialValuesRichiesta;

	public RichiestaAccessoAttiDetail(String nomeEntita) {

		super(nomeEntita);

		instance = this;

		init();

	}

	protected void init() {

		setPaddingAsLayoutMargin(false);

		lVLayoutMain = new VLayout();
		lVLayoutMain.setOverflow(Overflow.AUTO);

		// Inserisco uno spazio per staccare la prima section dalla barra della finestra
		HLayout spacerLayout = new HLayout();
		spacerLayout.setHeight(8);
		lVLayoutMain.addMember(spacerLayout);

		buildEstremiRichiestaSection();
		buildRichiedenteInternoSection();
		buildIndirizzoRiferimentoSection();
		buildAttiRichiestiSection();
		buildRichiedenteEsternoSection();
		buildRichiedenteDelegatoDaSection();
		buildEsibenteRichiestaSection();
		buildMotivoRichiestaSection();
		buildIncaricatoDelPrelievoSection();

		VLayout spacer = new VLayout();
		spacer.setHeight100();
		spacer.setWidth100();

		lVLayoutMain.addMember(spacer);

		createDetailToolStrip();

		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(lVLayoutMain);
		mainLayout.addMember(detailToolStrip);

		setMembers(mainLayout);
	}

	protected void createDetailToolStrip() {

		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editMode();
			}
		});

		saveButton = new DetailToolStripButton(getSaveButtonTitle(), "buttons/save.png");
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (abilSaveButton) {
					/*
					 * 
					 * Imposto il flag per disabilitare il pulsante per il salvataggio per evitare il problema che, in alcune situazioni, può essere
					 * eseguito un doppio salvataggio/registrazione cliccando rapidamente.
					 */
					abilSaveButton = false;

					/*
					 * Riabilitiamo il pulsante dopo che è trascorso un certo tempo. 
					 * In questo modo si evita il problema per cui, in caso di errori js o
					 * imprevisti, il pulsante rimanga disabilitato obbligando l'utente a chiudere la finestra
					 * per procedere.
					 */

					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {

							new Timer() {

								public void run() {
									// Riabilito il pulsante dopo che è passato il tempo predefinito.
									abilSaveButton = true;
								}
							}.schedule(5000); // Tempo dopo il quale si avvia l'esecuzione

							onSaveButtonClick();
						}
					});
				}
			}
		});

		reloadDetailButton = new DetailToolStripButton(I18NUtil.getMessages().reloadDetailButton_prompt(), "buttons/reloadDetail.png");
		reloadDetailButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						editMode();
					}
				});
			}
		});

		undoButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
		undoButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						viewMode();
					}
				});
			}
		});

		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right
		detailToolStrip.addButton(editButton);
		detailToolStrip.addButton(saveButton);
		detailToolStrip.addButton(reloadDetailButton);
		detailToolStrip.addButton(undoButton);

		for (DetailToolStripButton detailToolStripButton : getAzioniButtons()) {
			detailToolStrip.addButton(detailToolStripButton);
		}
	}

	public void onSaveButtonClick() {

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				motivoRichiestaSection.openIfhasValue();
				incaricatoDelPrelievoSection.openIfhasValue();
				vm.clearErrors(true);
				final Record lRecordToSave = getRecordToSave();
				final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
				lGwtRestDataSource.addParam("isRichiestaAccessoAtti", "true");
				if (validate()) {
					Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
					try {
						DSCallback callback = new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record record = response.getData()[0];
									try {
										lGwtRestDataSource.getData(record, new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													if (getReloadDetailAfterSave()) {
														Record record = response.getData()[0];
														editRecord(record);
														getValuesManager().clearErrors(true);
														if (layout != null) {
															layout.viewMode();
														} else {
															viewMode();
														}
														abilSaveButton = true; //Riabilito il pulsante di salvataggio
														Layout.hideWaitPopup();
													} else {
														if (estremiRichiestaSection != null) {
															estremiRichiestaSection.setVisible(true);
														}
//														AurigaLayout.addMessage(new MessageBean("Protocollazione accesso atti SUE effettuata con successo", "", MessageType.INFO));
														Record record = response.getData()[0];
														//instance.markForDestroy();
														editRecord(record);
														getValuesManager().clearErrors(true);
														if (layout != null) {
															layout.viewMode();
														} else {
															viewMode();
														}
														abilSaveButton = true; //Riabilito il pulsante di salvataggio
														Layout.hideWaitPopup();
														afterProtocollaRichAccessoAttiSUECallback();
													}	
												}
											}
										});
									} catch (Exception e) {
										abilSaveButton = true; //Riabilito il pulsante di salvataggio
										Layout.hideWaitPopup();
									}
								} else {
									abilSaveButton = true; //Riabilito il pulsante di salvataggio
									Layout.hideWaitPopup();
								}
							}
						};
						if (lRecordToSave.getAttribute("idUd") != null && !"".equals(lRecordToSave.getAttribute("idUd"))) {
							lGwtRestDataSource.updateData(lRecordToSave, callback);
						} else {
							lGwtRestDataSource.addData(lRecordToSave, callback);
						}
					} catch (Exception e) {
						Layout.hideWaitPopup();
						abilSaveButton = true; //Riabilito il pulsante di salvataggio
					}

				} else {
					// apriSectionInError();
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
					abilSaveButton = true; //Riabilito il pulsante di salvataggio
				}
			}
		});
	}

	@Override
	public Record getRecordToSave() {

		Record recordToSave = super.getRecordToSave();

		// Tolgo il mittente se è vuoto
		recordToSave.setAttribute("listaMittenti", checkRecordArrayToSave(recordToSave.getAttributeAsRecordArray("listaMittenti"), false,
				"denominazioneMittente", "cognomeMittente", "nomeMittente"));

		return recordToSave;
	}

	private void apriSectionInError() {
		if (estremiRichiestaSection != null) {
			estremiRichiestaSection.openIfhasErrors();
		}
		if (indirizzoRiferimentoSection != null) {
			indirizzoRiferimentoSection.openIfhasErrors();
		}
		if (attiRichiestiSection != null) {
			attiRichiestiSection.openIfhasErrors();
		}
		if (richiedenteEsternoSection != null) {
			richiedenteEsternoSection.openIfhasErrors();
		}
		if (richiedenteDelegatoDaSection != null) {
			richiedenteDelegatoDaSection.openIfhasErrors();
		}
		if (esibentiSection != null) {
			esibentiSection.openIfhasErrors();
		}
		if (motivoRichiestaSection != null) {
			motivoRichiestaSection.openIfhasErrors();
		}
		if (incaricatoDelPrelievoSection != null) {
			incaricatoDelPrelievoSection.openIfhasErrors();
		}

	}

	private RecordList checkRecordArrayToSave(Record[] recordToCheck, boolean aggiungiRecordSeVuota, String... listProperty) {

		RecordList recordListTosave = new RecordList();
		if (recordToCheck != null) {
			for (Record record : recordToCheck) {
				if (checkRecordToSave(record, listProperty)) {
					if (recordListTosave == null) {
						recordListTosave = new RecordList();
					}
					recordListTosave.add(record);
				}
			}
		}
		if (aggiungiRecordSeVuota && recordListTosave.getLength() == 0) {
			recordListTosave.add(new Record());
		}
		return recordListTosave;
	}

	private boolean checkRecordToSave(Record recordToCheck, String... listProperty) {

		boolean isValid = false;
		for (String property : listProperty) {
			String valueToCheck = recordToCheck.getAttributeAsString(property);
			if (valueToCheck != null && !"".equalsIgnoreCase(valueToCheck)) {
				isValid = true;
			}
		}
		return isValid;
	}

	private void buildEstremiRichiestaSection() {

		tipoProtocollazioneForm = new DynamicForm();
		tipoProtocollazioneForm.setValuesManager(vm);
		tipoProtocollazioneForm.setWidth("100%");
		tipoProtocollazioneForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		tipoProtocollazioneForm.setHeight("5");
		tipoProtocollazioneForm.setPadding(5);
		tipoProtocollazioneForm.setNumCols(10);
		tipoProtocollazioneForm.setWrapItemTitles(false);
		tipoProtocollazioneForm.setTabID("HEADER");

		LinkedHashMap<String, String> tipoProtocolloMap = new LinkedHashMap<String, String>();
		tipoProtocolloMap.put(TipoRichiedente.RICH_ESTERNO.getValue(), TipoRichiedente.RICH_ESTERNO.getDescrizione());
		tipoProtocolloMap.put(TipoRichiedente.RICH_INTERNO.getValue(), TipoRichiedente.RICH_INTERNO.getDescrizione());
		tipoRichiedenteItem = new RadioGroupItem("tipoRichiedente", setTitleAlign("Richiedente", TITTLE_ALIGN_WIDTH_2 - 5, false));
		tipoRichiedenteItem.setValueMap(tipoProtocolloMap);
		tipoRichiedenteItem.setEndRow(true);
		tipoRichiedenteItem.setVertical(false);
		tipoRichiedenteItem.setWrap(false);
		tipoRichiedenteItem.setRequired(true);

		tipoRichiedenteItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (event != null && event.getValue() != null) {
					setItemRichiedenteEsternoInterno((String) event.getValue());
				}
				//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)			
//				estremiRichiestaForm.setValue("nroProtocolloPregresso", "");
//				estremiRichiestaForm.setValue("annoProtocolloPregresso", "");
//				estremiRichiestaForm.setValue("dataProtocolloPregresso", "");
			}
		});

		tipoProtocollazioneForm.setItems(tipoRichiedenteItem);

		estremiRichiestaForm = new DynamicForm();
		estremiRichiestaForm.setValuesManager(vm);
		estremiRichiestaForm.setWidth("100%");
		estremiRichiestaForm.setNumCols(20);
		estremiRichiestaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		estremiRichiestaForm.setHeight("5");
		estremiRichiestaForm.setPadding(5);
		estremiRichiestaForm.setWrapItemTitles(false);
		estremiRichiestaForm.setTabID("HEADER");

		idUdItem = new HiddenItem("idUd");
		flgTipoProvItem = new HiddenItem("tipoProvItem");
		idEmailArrivoItem = new HiddenItem("idEmailArrivo");
		mezzoTrasmissioneItem = new HiddenItem("mezzoTrasmissione");
		
		tipoDocumentoItem = new HiddenItem("tipoDocumento");
		nomeTipoDocumentoItem = new HiddenItem("nomeTipoDocumento");
		flgTipoDocConVieItem = new HiddenItem("flgTipoDocConVie");
		codStatoRichAccessoAttiItem = new HiddenItem("codStatoRichAccessoAtti");
		idNodoDefaultRicercaAttiItem = new HiddenItem("idNodoDefaultRicercaAtti");

		nroProtocolloRichiestaItem = new ExtendedTextItem("nroProtocolloPregresso", setTitleAlign("Prot. Gen. N°", TITTLE_ALIGN_WIDTH_2, false)) {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		nroProtocolloRichiestaItem.setLength(7);
		nroProtocolloRichiestaItem.setWidth(120);
		nroProtocolloRichiestaItem.setAttribute("obbligatorio", isNroProtocolloRichiestaObbigatorio());
		nroProtocolloRichiestaItem.setRequired(isNroProtocolloRichiestaObbigatorio());
		nroProtocolloRichiestaItem.setKeyPressFilter("[0-9]");
		
		nroProtocolloRichiestaItem.addChangedBlurHandler(new ChangedHandler() {

			public void onChanged(ChangedEvent event) {
				estremiRichiestaForm.setValue("idUd", "");
				recuperaDatiProtocollo();
			}
		});
		
		CustomValidator idUdValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)						
//				if (!isRichiedenteInterno(new Record(vm.getValues()))) {
				if (isNroProtocolloRichiestaObbigatorio() || isAnnoProtocolloRichiestaObbigatorio()) {
					String idUd = estremiRichiestaForm.getValueAsString("idUd");
					if (idUd != null && !"".equals(idUd)) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		};
		// FIXME Sistemare messaggio
		idUdValidator.setErrorMessage("Nessuna pratica associata");
		
		nroProtocolloRichiestaItem.setValidators(idUdValidator);

		annoProtocolloRichiestaItem = new AnnoItem("annoProtocolloPregresso", "Anno") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		annoProtocolloRichiestaItem.setAttribute("obbligatorio", isAnnoProtocolloRichiestaObbigatorio());
		annoProtocolloRichiestaItem.setRequired(isAnnoProtocolloRichiestaObbigatorio());
		annoProtocolloRichiestaItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				estremiRichiestaForm.setValue("idUd", "");
				if (dataProtocolloRichiestaItem.getValue() != null) {
					try {
						String annoData = DateUtil.formatAsShortDate((Date) dataProtocolloRichiestaItem.getValue()).substring(6);
						String anno = (String) annoProtocolloRichiestaItem.getValue();
						if (anno != null && anno.length() == 4 && !anno.equals(annoData)) {
							dataProtocolloRichiestaItem.setValue((Date) null);
						} else {
							annoProtocolloRichiestaItem.setValue(annoData);
						}
					} catch (Exception e) {
					}
				}
				recuperaDatiProtocollo();
			}
		});

		dataProtocolloRichiestaItem = new ExtendedDateItem("dataProtocolloPregresso", "del") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setCanFocus(canEdit ? true : false);
			}
		};
		dataProtocolloRichiestaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				estremiRichiestaForm.setValue("idUd", "");
				if (dataProtocolloRichiestaItem.getValue() != null) {
					try {
						annoProtocolloRichiestaItem.setValue(DateUtil.formatAsShortDate((Date) dataProtocolloRichiestaItem.getValue()).substring(6));
						recuperaDatiProtocollo();
					} catch (Exception e) {
					}
				}
			}
		});
		
		visualizzaDettStdProtButton = new ImgButtonItem("visualizzaDettStdProtButton", "buttons/dati_protocollo.png", "Visualizza dati protocollo");
		visualizzaDettStdProtButton.setAlwaysEnabled(true);
		visualizzaDettStdProtButton.setEndRow(false);
		visualizzaDettStdProtButton.setColSpan(1);
		visualizzaDettStdProtButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {	
				String idUd = (idUdItem.getValue() != null) ? String.valueOf(idUdItem.getValue()) : null;
				String estremi = "Protocollo Generale ";
				if (nroProtocolloRichiestaItem.getValue() != null && !"".equals(nroProtocolloRichiestaItem.getValue())) {
					estremi += nroProtocolloRichiestaItem.getValue() + " ";
				}
				if (dataProtocolloRichiestaItem.getValue() != null && !"".equals(dataProtocolloRichiestaItem.getValue())) {
					estremi += "del " + DateUtil.format((Date) dataProtocolloRichiestaItem.getValue());
				} else if (annoProtocolloRichiestaItem.getValue() != null && !"".equals(annoProtocolloRichiestaItem.getValue())) {
					estremi += "del " + annoProtocolloRichiestaItem.getValue();
				}
				Record lRecord = new Record();
				lRecord.setAttribute("idUd", idUd);
				String title = (estremi != null && !"".equals(estremi) ? "Dettaglio " + estremi : "");
				DettaglioRegProtAssociatoWindow dettaglioProtWindow = new DettaglioRegProtAssociatoWindow(lRecord, "#DETT_STD_PROTOCOLLO", title);
			}
		});
		
		visualizzaDettStdProtButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
				if (record.getAttributeAsBoolean("abilVisualizzaDettStdProt")) {
					String idUd = (idUdItem.getValue() != null) ? String.valueOf(idUdItem.getValue()) : null;	
					return idUd != null && !"".equals(idUd);			
				}
				return false;
			}
		});
		
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Ricerca in archivio");
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				// Setto la radice come nodo di partenza predefinito
				String idNodoRicerca = "/";
				// Leggo il nodo di partenza dal dettaglio
				// FIXME mettere il nodo di ricerca
				//idNodoRicerca = ((AttiRichiestiItem) getItem()).getIdNodoRicerca();
				FascicoloLookupArchivio lookupArchivioPopup = new FascicoloLookupArchivio(estremiRichiestaForm.getValuesAsRecord(), idNodoRicerca);
				lookupArchivioPopup.show();
			}
		});
		
		lookupArchivioButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(showLookupArchivioInEstremiRichiestaSection()){				
					//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)
					return isEstremiProtococolloPraticaToEnable() /*&& !isRichiedenteInterno(new Record(vm.getValues()))*/;				
				}
				return false;
			}
		});

		operazioniEffettuateButton = new ImgButtonItem("operazioniEffettuate", "protocollazione/operazioniEffettuate.png",
				I18NUtil.getMessages().protocollazione_detail_operazioniEffettuateButton_prompt());
		operazioniEffettuateButton.setAlwaysEnabled(true);
		operazioniEffettuateButton.setColSpan(1);
		operazioniEffettuateButton.setEndRow(false);
		operazioniEffettuateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUd = (idUdItem.getValue() != null) ? String.valueOf(idUdItem.getValue()) : null;
				String estremi = "Protocollo Generale ";
				if (nroProtocolloRichiestaItem.getValue() != null && !"".equals(nroProtocolloRichiestaItem.getValue())) {
					estremi += nroProtocolloRichiestaItem.getValue() + " ";
				}
				if (dataProtocolloRichiestaItem.getValue() != null && !"".equals(dataProtocolloRichiestaItem.getValue())) {
					estremi += "del " + DateUtil.format((Date) dataProtocolloRichiestaItem.getValue());
				} else if (annoProtocolloRichiestaItem.getValue() != null && !"".equals(annoProtocolloRichiestaItem.getValue())) {
					estremi += "del " + annoProtocolloRichiestaItem.getValue();
				}
				OperazioniEffettuateWindow operazioniEffettuateWindow = new OperazioniEffettuateWindow(idUd, "U",
						I18NUtil.getMessages().operazionieffettuateDoc_window_title(estremi));
			}
		});

		operazioniEffettuateButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				String idUd = (idUdItem.getValue() != null) ? String.valueOf(idUdItem.getValue()) : null;
				return ((idUd != null) && (!"".equalsIgnoreCase(idUd)));
			}
		});

		SpacerItem desStatoRichAccessoAttiItemSpacer = new SpacerItem();
		desStatoRichAccessoAttiItemSpacer.setWidth(20);

		desStatoRichAccessoAttiItem = new TextItem("desStatoRichAccessoAtti", "Stato della richiesta") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
				setTextBoxStyle(it.eng.utility.Styles.textItemBold);
				setTabIndex(-1);
			}
		};
		desStatoRichAccessoAttiItem.setWidth(350);
		desStatoRichAccessoAttiItem.setColSpan(10);
		desStatoRichAccessoAttiItem.setStartRow(false);
		desStatoRichAccessoAttiItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (item.getValue() != null && !"".equalsIgnoreCase(((String) item.getValue()).trim()));
			}
		});

		siglaPraticaSuSistUfficioRichiedenteItem = new HiddenItem("siglaPraticaSuSistUfficioRichiedente");

		numeroPraticaSuSistUfficioRichiedenteItem = new TextItem("numeroPraticaSuSistUfficioRichiedente",
				setTitleAlign("ID nel sistema uff. richiedente N°", TITTLE_ALIGN_WIDTH_2, false)) {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		numeroPraticaSuSistUfficioRichiedenteItem.setWidth(120);
		numeroPraticaSuSistUfficioRichiedenteItem.setStartRow(true);
		numeroPraticaSuSistUfficioRichiedenteItem.setKeyPressFilter("[0-9]");

		annoPraticaSuSistUfficioRichiedenteItem = new AnnoItem("annoPraticaSuSistUfficioRichiedente", "Anno") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};

		estremiRichiestaForm.setItems(
				idUdItem, tipoDocumentoItem, nomeTipoDocumentoItem, flgTipoDocConVieItem, codStatoRichAccessoAttiItem, idNodoDefaultRicercaAttiItem, 
				nroProtocolloRichiestaItem, annoProtocolloRichiestaItem, dataProtocolloRichiestaItem, visualizzaDettStdProtButton, 
				lookupArchivioButton, operazioniEffettuateButton, desStatoRichAccessoAttiItemSpacer, desStatoRichAccessoAttiItem, 
				siglaPraticaSuSistUfficioRichiedenteItem, numeroPraticaSuSistUfficioRichiedenteItem, annoPraticaSuSistUfficioRichiedenteItem);

		estremiRichiestaSection = new RichiestaAccessoAttiDetailSection("Estremi richiesta", false, true, false, tipoProtocollazioneForm, estremiRichiestaForm);
		
		lVLayoutMain.addMember(estremiRichiestaSection);
	}

	private void buildRichiedenteInternoSection() {

		richiedenteInternoForm = new DynamicForm();
		richiedenteInternoForm.setValuesManager(vm);
		richiedenteInternoForm.setWidth("100%");
		richiedenteInternoForm.setHeight("5");
		richiedenteInternoForm.setPadding(5);
		richiedenteInternoForm.setNumCols(20);
		richiedenteInternoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
		richiedenteInternoForm.setWrapItemTitles(false);
		richiedenteInternoForm.setTabID("HEADER");

		richiedentiInterniItem = new MittenteProtInternaItem() {

			@Override
			public boolean getShowItemSelTipoMittente() {
				return false;
			}

			@Override
			public boolean getAllowOnlyUnitaDiPersonale() {
				return true;
			}

			@Override
			public boolean getShowFlgAssegnaAlMittente() {
				return false;
			}
		};
		richiedentiInterniItem.setName("listaRichiedentiInterni");
		richiedentiInterniItem.setShowTitle(false);
		richiedentiInterniItem.setShowNewButton(false);
		richiedentiInterniItem.setNotReplicable(true);

		richiedentiInterniItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (tipoRichiedenteItem.getValue() != null
						&& TipoRichiedente.RICH_INTERNO.getValue().equalsIgnoreCase((String) tipoRichiedenteItem.getValue())) {
					richiedentiInterniItem.setAttribute("obbligatorio", true);
				} else {
					richiedentiInterniItem.setAttribute("obbligatorio", false);
				}
				return true;
			}
		});

		richiedenteInternoForm.setFields(richiedentiInterniItem);

		richiedenteInternoSection = new RichiestaAccessoAttiDetailSection("Richiedente interno", true, true, true, richiedenteInternoForm);
		
		lVLayoutMain.addMember(richiedenteInternoSection);
	}

	private void buildIndirizzoRiferimentoSection() {

		// Indirizzo di riferimento
		indirizzoRiferimentoForm = new DynamicForm();
		indirizzoRiferimentoForm.setValuesManager(vm);
		indirizzoRiferimentoForm.setWidth("100%");
		indirizzoRiferimentoForm.setHeight("5");
		indirizzoRiferimentoForm.setPadding(5);

		indirizzoRiferimentoItem = new AltreVieItem() {

			@Override
			public boolean showFlgFuoriComune() {
				return false;
			}

			@Override
			public boolean getFlgFuoriComune() {
				return false;
			}

			@Override
			public boolean isIndirizzoObbligatorioInCanvas() {
				return showIndirizzoRiferimentoSection();
			}

			@Override
			public boolean isCivicoObbligatorioInCanvas() {
				return showIndirizzoRiferimentoSection();
			}

			@Override
			public boolean isForceCapNonObbligatorioInCanvas() {
				return true;
			}

			@Override
			public boolean getShowStato() {
				return false;
			}
			
			@Override
			public boolean isControllaIndirizzoAfterEditRecordInCanvas() {
				return isControllaIndirizzoAfterEditRecord();
			}
	
		};
		indirizzoRiferimentoItem.setName("listaAltreVie");
		indirizzoRiferimentoItem.setShowTitle(false);
		indirizzoRiferimentoItem.setAttribute("obbligatorio", true);
		indirizzoRiferimentoItem.setNotReplicable(true);

		indirizzoRiferimentoForm.setFields(indirizzoRiferimentoItem);

		indirizzoRiferimentoSection = new RichiestaAccessoAttiDetailSection("Indirizzo di riferimento", true, true, true, indirizzoRiferimentoForm);
		
		lVLayoutMain.addMember(indirizzoRiferimentoSection);
	}

	private void buildAttiRichiestiSection() {

		attiRichiestiForm = new DynamicForm();
		attiRichiestiForm.setValuesManager(vm);
		attiRichiestiForm.setWidth("100%");
		attiRichiestiForm.setHeight("5");
		attiRichiestiForm.setNumCols(15);
		attiRichiestiForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
		attiRichiestiForm.setPadding(5);
		
		flgRichAttiFabbricaVisuraSueItem = new CheckboxItem("flgRichAttiFabbricaVisuraSue", "Atti di fabbrica");
		flgRichAttiFabbricaVisuraSueItem.setStartRow(true);
		flgRichAttiFabbricaVisuraSueItem.setColSpan(1);
		flgRichAttiFabbricaVisuraSueItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
//				if (record.getAttribute("mezzoTrasmissione") != null && "PEC".equalsIgnoreCase(record.getAttributeAsString("mezzoTrasmissione"))) {
//					flgRichAttiFabbricaVisuraSueItem.setCanEdit(false);		
//				}
				return showRichAttiFabbricaVisuraSueItem();
			}
		});
		
		flgRichModificheVisuraSueItem = new CheckboxItem("flgRichModificheVisuraSue", "Modifiche");
		flgRichModificheVisuraSueItem.setStartRow(true);
		flgRichModificheVisuraSueItem.setColSpan(1);
		flgRichModificheVisuraSueItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				Record record = new Record(vm.getValues());
//				if (record.getAttribute("mezzoTrasmissione") != null && "PEC".equalsIgnoreCase(record.getAttributeAsString("mezzoTrasmissione"))) {
//					flgRichModificheVisuraSueItem.setCanEdit(false);		
//				}
				return showRichModificheVisuraSueItem();
			}
		});

		attiRichiestiItem = new AttiRichiestiItem() {

			@Override
			public boolean isElencoAttiRichiestiInCanvasEditable() {
				return isElencoAttiRichiestiEditable();
			}

			@Override
			public boolean isEstremiAttiRichiestiInCanvasEditable() {
				return isEstremiAttiRichiestiEditable();
			}

			@Override
			public boolean isStatoAttiInCanvasToShow() {
				return isStatoAttiRichiestiToShow();
			}

			@Override
			public boolean isStatoAttiInCanvasEditable() {
				return isStatoAttiRichiestiEditable();
			}

			@Override
			public boolean isNoteUffRichiedenteInCanvasToEnable() {
				return isNoteUffRichiedenteToEnable();
			}

			@Override
			public boolean isNoteCittadellaInCanvasToEnable() {
				return isNoteCittadellaToEnable();
			}

			@Override
			public Record getIndirizzoImpostato() {
				if (showIndirizzoRiferimentoSection() && indirizzoRiferimentoItem.getAllCanvas() != null
						&& indirizzoRiferimentoItem.getAllCanvas().length > 0) {
					Record recordToReturn = indirizzoRiferimentoItem.getAllCanvas()[0].getFormValuesAsRecord();
					// Ricavo il codice dl tipo documento urbanistica
					recordToReturn.setAttribute("tipoDocumento", tipoDocumentoItem.getValue() != null ? (String) tipoDocumentoItem.getValue() : "");
					return recordToReturn;
				} else {
					return null;
				}
			}

			@Override
			public String getIdNodoRicerca() {
				if ((idNodoDefaultRicercaAttiItem != null) && (idNodoDefaultRicercaAttiItem.getValue() != null)
						&& (!"".equalsIgnoreCase((String) idNodoDefaultRicercaAttiItem.getValue()))) {
					return (String) idNodoDefaultRicercaAttiItem.getValue();
				} else {
					return super.getIdNodoRicerca();
				}
			}
		};
		attiRichiestiItem.setName("listaAttiRichiesti");
		attiRichiestiItem.setTitle("Atti richiesti");
		attiRichiestiItem.setShowTitle(false);
		attiRichiestiItem.setStartRow(true);

		attiRichiestiForm.setFields(flgRichAttiFabbricaVisuraSueItem, flgRichModificheVisuraSueItem, attiRichiestiItem);

		attiRichiestiSection = new RichiestaAccessoAttiDetailSection("Atti richiesti", true, true, false, attiRichiestiForm);
		
		lVLayoutMain.addMember(attiRichiestiSection);
	}

	private void buildRichiedenteEsternoSection() {

		richiedenteEsternoForm = new DynamicForm();
		richiedenteEsternoForm.setValuesManager(vm);
		richiedenteEsternoForm.setWidth("100%");
		richiedenteEsternoForm.setHeight("5");
		richiedenteEsternoForm.setPadding(5);

		richiedentiEsterniItem = new MittenteProtEntrataItem() {
			
			@Override
			public boolean isEmailMittenteItemObbligatorio() {
				return isEmailRichiedenteEsternoItemObbligatorio();
			}
			
			@Override
			public boolean getShowTelefono() {
				return true;
			}

			@Override
			public boolean getShowEmail() {
				return true;
			}

			@Override
			public boolean isRequiredDenominazione(boolean hasValue) {
				return hasValue;
			}
		};
		richiedentiEsterniItem.setName("listaMittenti");
		richiedentiEsterniItem.setShowTitle(false);
		richiedentiEsterniItem.setShowNewButton(false);
		richiedentiEsterniItem.setNotReplicable(true);

		richiedenteEsternoForm.setFields(richiedentiEsterniItem);

		richiedenteEsternoSection = new RichiestaAccessoAttiDetailSection("Richiedente esterno", true, isRichiedenteEsternoSectionShowOpen(), isRichiedenteEsternoSectionObbligatoria(), richiedenteEsternoForm);
		
		lVLayoutMain.addMember(richiedenteEsternoSection);
	}

	private void buildRichiedenteDelegatoDaSection() {

		richiedenteDelegatoDaForm = new DynamicForm();
		richiedenteDelegatoDaForm.setValuesManager(vm);
		richiedenteDelegatoDaForm.setWidth("100%");
		richiedenteDelegatoDaForm.setHeight("5");
		richiedenteDelegatoDaForm.setPadding(5);

		richiedentiDelegatiDaItem = new InteressatiItem() {

			@Override
			public boolean getShowTelefono() {
				return true;
			}

			@Override
			public boolean getShowEmail() {
				return true;
			}

			@Override
			public boolean isRequiredDenominazione(boolean hasValue) {
				return hasValue;
			}
		};
		richiedentiDelegatiDaItem.setName("listaRichiedentiDelegati");
		richiedentiDelegatiDaItem.setShowTitle(false);
		richiedentiDelegatiDaItem.setNotReplicable(true);
		richiedentiDelegatiDaItem.setShowNewButton(false);

		richiedenteDelegatoDaForm.setFields(richiedentiDelegatiDaItem);

		richiedenteDelegatoDaSection = new RichiestaAccessoAttiDetailSection("Richiedente delegato da", true, false, false, richiedenteDelegatoDaForm);
		
		lVLayoutMain.addMember(richiedenteDelegatoDaSection);
	}

	private void buildEsibenteRichiestaSection() {

		esibentiForm = new DynamicForm();
		esibentiForm.setValuesManager(vm);
		esibentiForm.setWidth("100%");
		esibentiForm.setHeight("5");
		esibentiForm.setPadding(5);

		esibentiItem = new EsibentiItem() {

			@Override
			public boolean getShowAncheMittente() {
				return false;
			}

			@Override
			public boolean isRequiredDenominazione(boolean hasValue) {
				return hasValue;
			}

			@Override
			public boolean validazioneItemAbilitata() {
				return true;
			}
		};
		esibentiItem.setName("listaEsibenti");
		esibentiItem.setShowTitle(false);
		esibentiItem.setNotReplicable(true);

		esibentiForm.setFields(esibentiItem);

		esibentiSection = new RichiestaAccessoAttiDetailSection("Esibente della richiesta (se diverso dal richiedente)", true, false, false, esibentiForm);
		
		lVLayoutMain.addMember(esibentiSection);
	}

	private void buildMotivoRichiestaSection() {

		motivoRichiestaForm = new DynamicForm();
		motivoRichiestaForm.setValuesManager(vm);
		motivoRichiestaForm.setWidth("100%");
		motivoRichiestaForm.setHeight("5");
		motivoRichiestaForm.setPadding(5);
		motivoRichiestaForm.setNumCols(10);
		motivoRichiestaForm.setWrapItemTitles(false);
		motivoRichiestaForm.setTabID("HEADER");

		final GWTRestDataSource motivoRichiestaDS = new GWTRestDataSource("LoadComboMotivoRichiestaDataSource", "key", FieldType.TEXT);
		motivoRichiestaItem = new SelectItem("motivoRichiestaAccessoAtti", setTitleAlign("Motivo della richiesta", TITTLE_ALIGN_WIDTH, false));

		motivoRichiestaItem.setValueField("key");
		motivoRichiestaItem.setDisplayField("value");
		motivoRichiestaItem.setOptionDataSource(motivoRichiestaDS);
		motivoRichiestaItem.setWidth(200);
		motivoRichiestaItem.setStartRow(true);

		dettagliRichiestaItem = new TextAreaItem("dettagliRichiestaAccessoAtti", setTitleAlign("Dettagli richiesta", TITTLE_ALIGN_WIDTH, false));
		dettagliRichiestaItem.setWidth(800);
		dettagliRichiestaItem.setHeight(50);
		dettagliRichiestaItem.setStartRow(false);

		motivoRichiestaForm.setItems(motivoRichiestaItem, dettagliRichiestaItem);

		motivoRichiestaSection = new RichiestaAccessoAttiDetailSection("Motivo e dettagli richiesta", true, false, false, motivoRichiestaForm);
		
		lVLayoutMain.addMember(motivoRichiestaSection);
	}

	private void buildIncaricatoDelPrelievoSection() {

		incaricatoPrelievoUffRichiedenteForm = new DynamicForm();
		incaricatoPrelievoUffRichiedenteForm.setValuesManager(vm);
		incaricatoPrelievoUffRichiedenteForm.setWidth("100%");
		incaricatoPrelievoUffRichiedenteForm.setPadding(5);
		incaricatoPrelievoUffRichiedenteForm.setNumCols(20);
		incaricatoPrelievoUffRichiedenteForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
		incaricatoPrelievoUffRichiedenteForm.setWrapItemTitles(false);
		incaricatoPrelievoUffRichiedenteForm.setTabID("HEADER");

		TitleItem titoloIncaricatoPrelievoUffRichiedente = new TitleItem(setTitleAlign("Per l'ufficio richiedente", TITTLE_ALIGN_WIDTH, false));
		titoloIncaricatoPrelievoUffRichiedente.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return true;
			}
		});

		incaricatoPrelPerUffRichiedenteItems = new SelezionaUtenteItems(incaricatoPrelievoUffRichiedenteForm, "idIncaricatoPrelievoPerUffRichiedente",
				"usernameIncaricatoPrelievoPerUffRichiedente", "codRapidoIncaricatoPrelievoPerUffRichiedente", "cognomeIncaricatoPrelievoPerUffRichiedente",
				"nomeIncaricatoPrelievoPerUffRichiedente", "codiceFiscaleIncaricatoPrelievoPerUffRichiedente", "emailIncaricatoPrelievoPerUffRichiedente",
				"telefonoIncaricatoPrelievoPerUffRichiedente") {

			@Override
			protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean codiceFiscaleItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean emailItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean telefonoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		};

		List<FormItem> lista = new ArrayList<FormItem>();
		lista.add(titoloIncaricatoPrelievoUffRichiedente);
		lista.addAll(incaricatoPrelPerUffRichiedenteItems);

		incaricatoPrelievoUffRichiedenteForm.setItems(lista.toArray(new FormItem[lista.size()]));

		incaricatoPrelievoPerRichEsternoForm = new DynamicForm();
		incaricatoPrelievoPerRichEsternoForm.setValuesManager(vm);
		incaricatoPrelievoPerRichEsternoForm.setWidth("100%");
		incaricatoPrelievoPerRichEsternoForm.setPadding(5);
		incaricatoPrelievoPerRichEsternoForm.setNumCols(20);
		incaricatoPrelievoPerRichEsternoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
		incaricatoPrelievoPerRichEsternoForm.setWrapItemTitles(false);
		incaricatoPrelievoPerRichEsternoForm.setTabID("HEADER");

		TitleItem titoloIncaricatoPrelievoPerRichEsterno = new TitleItem(setTitleAlign("Per il richiedente esterno", TITTLE_ALIGN_WIDTH, false));
		titoloIncaricatoPrelievoPerRichEsterno.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return true;
			}
		});

		incaricatoPrelievoPerRichEsternoItem = new SelezionaUtenteItems(incaricatoPrelievoPerRichEsternoForm, "idIncaricatoPrelievoPerRichEsterno",
				"usernameIncaricatoPrelievoPerRichEsterno", "codRapidoIncaricatoPrelievoPerRichEsterno", "cognomeIncaricatoPrelievoPerRichEsterno",
				"nomeIncaricatoPrelievoPerRichEsterno", "codiceFiscaleIncaricatoPrelievoPerRichEsterno", "emailIncaricatoPrelievoPerRichEsterno",
				"telefonoIncaricatoPrelievoPerRichEsterno") {

			@Override
			protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean cercaInRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean cercaInOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean lookupRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}

			@Override
			protected boolean lookupOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
				return false;
			}
		};

		lista = new ArrayList<FormItem>();
		lista.add(titoloIncaricatoPrelievoPerRichEsterno);
		lista.addAll(incaricatoPrelievoPerRichEsternoItem);

		incaricatoPrelievoPerRichEsternoForm.setItems(lista.toArray(new FormItem[lista.size()]));

		SpacerItem vSpace = new SpacerItem();
		vSpace.setHeight(20);

		datiAppuntamentoForm = new DynamicForm();
		datiAppuntamentoForm.setValuesManager(vm);
		datiAppuntamentoForm.setWidth("100%");
		datiAppuntamentoForm.setHeight("5");
		datiAppuntamentoForm.setPadding(5);
		datiAppuntamentoForm.setNumCols(20);
		datiAppuntamentoForm.setColWidths("1", "1", "1", "80", "1", "1", "*");
		datiAppuntamentoForm.setWrapItemTitles(false);

		dataAppuntamentoItem = new TextItem("dataAppuntamento", setTitleAlign("Appuntamento il", TITTLE_ALIGN_WIDTH, false));
		dataAppuntamentoItem.setStartRow(true);
		dataAppuntamentoItem.setWidth(70);
		orarioAppuntamentoItem = new TextItem("orarioAppuntamento", "alle");
		orarioAppuntamentoItem.setWidth(50);
		provenienzaAppuntamentoItem = new TextItem("provenienzaAppuntamento", "Fissato tramite");
		provenienzaAppuntamentoItem.setWidth(120);
		provenienzaAppuntamentoItem.setColSpan(2);

		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(40);

		dataPrelievoItem = new TextItem("dataPrelievo", "Data prelievo effettivo");
		dataPrelievoItem.setWidth(70);
		dataRestituzionePrelievoItem = new TextItem("dataRestituzionePrelievo", "Restituzione il");
		dataRestituzionePrelievoItem.setWidth(70);
		restituzionePrelievoAttestataDaItem = new TextItem("restituzionePrelievoAttestataDa", "attestata da");
		restituzionePrelievoAttestataDaItem.setWidth(120);

		datiAppuntamentoForm.setItems(vSpace, dataAppuntamentoItem, orarioAppuntamentoItem, provenienzaAppuntamentoItem, spacer, dataPrelievoItem,
				dataRestituzionePrelievoItem, restituzionePrelievoAttestataDaItem);

		incaricatoDelPrelievoSection = new RichiestaAccessoAttiDetailSection("Dati prelievo", true, false, false, incaricatoPrelievoUffRichiedenteForm,
				incaricatoPrelievoPerRichEsternoForm, datiAppuntamentoForm);
		
		lVLayoutMain.addMember(incaricatoDelPrelievoSection);
	}

	public boolean showDetailToolStrip() {
		return getLayout() == null;
	}
	
	public boolean showLookupArchivioInEstremiRichiestaSection() {
		//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)
		//va filtrata solo per PG, intanto la nascondo
		return false;
	}

	public boolean showEstremiRichiestaSection() {
		return true;
	}
	
	public boolean showIndirizzoRiferimentoSection() {

		if (flgTipoDocConVieItem != null) {
			return flgTipoDocConVieItem.getValue() != null && (Boolean) flgTipoDocConVieItem.getValue();
		}
		return false;
	}
	
	public boolean isNroProtocolloRichiestaObbigatorio() {
		return true;
	}
	
	public boolean isAnnoProtocolloRichiestaObbigatorio() {
		return true;
	}
	
	public boolean showRichAttiFabbricaVisuraSueItem() {
		return false;
	}
	
	public boolean showRichModificheVisuraSueItem() {
		return false;
	}
	
	public boolean showSectionAttiRichiestiSection() {
		return true;
	}
	
	public boolean isRichiedenteEsternoSectionObbligatoria() {
		return false;
	}
	
	public boolean isRichiedenteEsternoSectionShowOpen() {
		return false;
	}
		
	public boolean isEmailRichiedenteEsternoItemObbligatorio() {
		return false;
	}
	
	public boolean showIncaricatoDelPrelievoSection() {
		return true;
	}
	
	public boolean isControllaIndirizzoAfterEditRecord() {
		return false;
	}
	
	public boolean isNewMode() {
		return this.mode == null || this.mode.equals("new");
	}
	
	public String getSaveButtonTitle() {
		return I18NUtil.getMessages().saveButton_prompt();
	}
	
	public boolean getReloadDetailAfterSave() {
		return true;
	}
	
	public void afterProtocollaRichAccessoAttiSUECallback() {
		
	}

	@Override
	public void setCanEdit(boolean canEdit) {

		super.setCanEdit(canEdit);
		
		// Posso cambiare il tipo di protocollazione interna o esterna solo in new mode		
//		tipoRichiedenteItem.setCanEdit(isNewMode());
//		nroProtocolloRichiestaItem.setCanEdit(canEdit && isEstremiProtococolloPraticaToEnable() && !isRichiedenteInterno(new Record(vm.getValues())));
//		annoProtocolloRichiestaItem.setCanEdit(canEdit && isEstremiProtococolloPraticaToEnable() && !isRichiedenteInterno(new Record(vm.getValues())));
//		dataProtocolloRichiestaItem.setCanEdit(canEdit && isEstremiProtococolloPraticaToEnable() && !isRichiedenteInterno(new Record(vm.getValues())));
		
		//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)
		tipoRichiedenteItem.setCanEdit(false);
		nroProtocolloRichiestaItem.setCanEdit(isNewMode());
		annoProtocolloRichiestaItem.setCanEdit(isNewMode());
		dataProtocolloRichiestaItem.setCanEdit(isNewMode());	
		
		numeroPraticaSuSistUfficioRichiedenteItem.setCanEdit(canEdit && isEstremiProtococolloPraticaToEnable());
		annoPraticaSuSistUfficioRichiedenteItem.setCanEdit(canEdit && isEstremiProtococolloPraticaToEnable());

		richiedentiInterniItem.setCanEdit(canEdit && isRichiedenteInternoToEnable());

		indirizzoRiferimentoItem.setCanEdit(canEdit && isIndirizzoDiRiferimentoToEnable());

		// Nascondo il pulsante di aggiunta se non ho le abilitazioni per modificare la lista degli atti
		// La gestione del bottone di rimozione è invece fatta nel relativo canvas
		if (!isElencoAttiRichiestiEditable()) {
			attiRichiestiItem.setMaxLength(attiRichiestiItem.getTotalMembers());
		}

		richiedentiEsterniItem.setCanEdit(canEdit && isRichiedenteEsternoToEnable());

		richiedentiDelegatiDaItem.setCanEdit(canEdit && isRichiedenteDelegatoToEnable());

		esibentiItem.setCanEdit(canEdit && isEsibenteDellaRichiestaToEnable());

		motivoRichiestaItem.setCanEdit(canEdit && isMotivoEDettagliDellaRichiestaToEnable());
		dettagliRichiestaItem.setCanEdit(canEdit && isMotivoEDettagliDellaRichiestaToEnable());

		incaricatoPrelPerUffRichiedenteItems.setCanEdit(canEdit && isDatiPrelievoUffRichiedenteESoggEsternoToEnable());
		incaricatoPrelievoPerRichEsternoItem.setCanEdit(canEdit && isDatiPrelievoUffRichiedenteESoggEsternoToEnable());

		dataAppuntamentoItem.setCanEdit(false);
		orarioAppuntamentoItem.setCanEdit(false);
		provenienzaAppuntamentoItem.setCanEdit(false);
		dataPrelievoItem.setCanEdit(false);
		dataRestituzionePrelievoItem.setCanEdit(false);
		restituzionePrelievoAttestataDaItem.setCanEdit(false);
	}

	@Override
	public void editNewRecord() {

		super.editNewRecord();

		initialValuesRichiesta = new HashMap();
		
		estremiRichiestaSection.setTitle("Estremi richiesta");
		
		if (estremiRichiestaSection != null) {
			estremiRichiestaSection.setVisible(showEstremiRichiestaSection());
		}
		
		if (indirizzoRiferimentoSection != null) {
			indirizzoRiferimentoSection.setVisible(showIndirizzoRiferimentoSection());
		}
		
		if (attiRichiestiSection != null) {
			attiRichiestiSection.setVisible(showSectionAttiRichiestiSection());
		}
		
		if (incaricatoDelPrelievoSection != null) {
			incaricatoDelPrelievoSection.setVisible(showIncaricatoDelPrelievoSection());
		}

		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}

		refreshTabIndex();
	}

	@Override
	public void editNewRecord(@SuppressWarnings("rawtypes") Map initialValues) {

		super.editNewRecord(initialValues);
	
		initialValuesRichiesta = new HashMap();
		if(initialValues != null) {
			initialValuesRichiesta.putAll(initialValues);
		}
		
		String siglaPraticaSuSistUfficioRichiedente = (String) initialValues.get("siglaPraticaSuSistUfficioRichiedente");
		setSiglaPraticaSuSistUfficioRichiedente(siglaPraticaSuSistUfficioRichiedente);

		estremiRichiestaSection.setTitle("Estremi richiesta");
		String nomeTipoDocumento = (String) initialValues.get("nomeTipoDocumento");
		if (nomeTipoDocumento != null && !"".equals(nomeTipoDocumento)) {
			estremiRichiestaSection.setTitle("Estremi richiesta - Tipo : " + nomeTipoDocumento);
		}

		if (estremiRichiestaSection != null) {
			estremiRichiestaSection.setVisible(showEstremiRichiestaSection());
		}
		
		if (indirizzoRiferimentoSection != null) {
			indirizzoRiferimentoSection.setVisible(showIndirizzoRiferimentoSection());
		}
		
		if (attiRichiestiSection != null) {
			attiRichiestiSection.setVisible(showSectionAttiRichiestiSection());
		}
		
		if (incaricatoDelPrelievoSection != null) {
			incaricatoDelPrelievoSection.setVisible(showIncaricatoDelPrelievoSection());
		}
		
		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}

		refreshTabIndex();
	}

	@Override
	public void editRecord(Record record) {

		super.editRecord(record);

		String siglaPraticaSuSistUfficioRichiedente = record.getAttributeAsString("siglaPraticaSuSistUfficioRichiedente");
		setSiglaPraticaSuSistUfficioRichiedente(siglaPraticaSuSistUfficioRichiedente);

		estremiRichiestaSection.setTitle("Estremi richiesta");
		String nomeTipoDocumento = (String) record.getAttributeAsString("nomeTipoDocumento");
		if (nomeTipoDocumento != null && !"".equals(nomeTipoDocumento)) {
			estremiRichiestaSection.setTitle("Estremi richiesta - Tipo : " + nomeTipoDocumento);
		}

		if (indirizzoRiferimentoSection != null) {
			indirizzoRiferimentoSection.setVisible(showIndirizzoRiferimentoSection());
		}

		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}

		if (isRichiedenteInterno(record)) {
			setItemRichiedenteEsternoInterno(TipoRichiedente.RICH_INTERNO.getValue());
		} else {
			setItemRichiedenteEsternoInterno(TipoRichiedente.RICH_ESTERNO.getValue());
		}

		refreshTabIndex();
	}

	@Override
	public void newMode() {

		super.newMode();

		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}
		editButton.hide();
		saveButton.show();
		reloadDetailButton.hide();
		undoButton.hide();

		// Apro la sezione richiesta atti
		if (attiRichiestiSection != null) {
			attiRichiestiSection.open();			
		}

		// In newmode il tipo di default è protocollazione esterno
		if(tipoRichiedenteItem.getValue() == null || "".equals(tipoRichiedenteItem.getValue())) {
			tipoRichiedenteItem.setValue(TipoRichiedente.RICH_ESTERNO.getValue());
			setItemRichiedenteEsternoInterno(TipoRichiedente.RICH_ESTERNO.getValue());
		}

		invioInApprovazioneButton.hide();
		approvazioneButton.hide();
		invioEsitoVerificaArchivioButton.hide();
		abilitaAppuntamentoDaAgendaButton.hide();
		setAppuntamentoButton.hide();
		annullamentoAppuntamentoButton.hide();
		registraPrelievoButton.hide();
		registraRestituzioneButton.hide();
		annullamentoButton.hide();
		stampaFoglioPrelievoButton.hide();
		chiusuraButton.hide();
		riaperturaButton.hide();
		ripristinoButton.hide();

		refreshTabIndex();
	}

	protected void setSiglaPraticaSuSistUfficioRichiedente(String sigla) {
		if (sigla != null && !"".equalsIgnoreCase(sigla)) {
			estremiRichiestaForm.setValue("siglaPraticaSuSistUfficioRichiedente", sigla);
			numeroPraticaSuSistUfficioRichiedenteItem.setTitle("ID nel sistema uff. richiedente " + sigla + " N°");
		} else {
			estremiRichiestaForm.setValue("siglaPraticaSuSistUfficioRichiedente", (String) null);
			numeroPraticaSuSistUfficioRichiedenteItem.setTitle("ID nel sistema uff. richiedente N°");
		}
	}

	@Override
	public void editMode() {

		super.editMode();

		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}
		editButton.hide();
		saveButton.show();
		reloadDetailButton.show();
		undoButton.show();

		// Se non ho atti richiesti, in modifica apro sempre una riga vuota
		if (attiRichiestiSection != null && attiRichiestiSection.isOpen() && attiRichiestiItem != null && attiRichiestiItem.getAllCanvas().length == 0) {
			attiRichiestiItem.onClickNewButton();
		}

		invioInApprovazioneButton.hide();
		approvazioneButton.hide();
		invioEsitoVerificaArchivioButton.hide();
		abilitaAppuntamentoDaAgendaButton.hide();
		setAppuntamentoButton.hide();
		annullamentoAppuntamentoButton.hide();
		registraPrelievoButton.hide();
		registraRestituzioneButton.hide();
		annullamentoButton.hide();
		stampaFoglioPrelievoButton.hide();
		chiusuraButton.hide();
		riaperturaButton.hide();
		ripristinoButton.hide();

	}

	@Override
	public void viewMode() {

		super.viewMode();

		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		} else {
			detailToolStrip.show();
		}
		editButton.show();
		saveButton.hide();
		reloadDetailButton.hide();
		undoButton.hide();

		Record record = new Record(vm.getValues());
		if (record.getAttributeAsBoolean("abilModificaDati")) {
			editButton.show();
		} else {
			editButton.hide();
		}
		if (record.getAttributeAsBoolean("abilInvioInApprovazione")) {
			invioInApprovazioneButton.show();
		} else {
			invioInApprovazioneButton.hide();
		}
		if (record.getAttributeAsBoolean("abilApprovazione")) {
			approvazioneButton.show();
		} else {
			approvazioneButton.hide();
		}
		if (record.getAttributeAsBoolean("abilInvioEsitoVerificaArchivio")) {
			invioEsitoVerificaArchivioButton.show();
		} else {
			invioEsitoVerificaArchivioButton.hide();
		}
		if (record.getAttributeAsBoolean("abilAbilitaAppuntamentoDaAgenda")) {
			abilitaAppuntamentoDaAgendaButton.show();
		} else {
			abilitaAppuntamentoDaAgendaButton.hide();
		}
		if (record.getAttributeAsBoolean("abilSetAppuntamento")) {
			setAppuntamentoButton.show();
		} else {
			setAppuntamentoButton.hide();
		}
		if (record.getAttributeAsBoolean("abilAnnullamentoAppuntamento")) {
			annullamentoAppuntamentoButton.show();
		} else {
			annullamentoAppuntamentoButton.hide();
		}
		if (record.getAttributeAsBoolean("abilRegistraPrelievo")) {
			registraPrelievoButton.show();
		} else {
			registraPrelievoButton.hide();
		}
		if (record.getAttributeAsBoolean("abilRegistraRestituzione")) {
			registraRestituzioneButton.show();
		} else {
			registraRestituzioneButton.hide();
		}
		if (record.getAttributeAsBoolean("abilAnnullamento")) {
			annullamentoButton.show();
		} else {
			annullamentoButton.hide();
		}
		if (record.getAttributeAsBoolean("abilStampaFoglioPrelievo")) {
			stampaFoglioPrelievoButton.show();
		} else {
			stampaFoglioPrelievoButton.hide();
		}

		// CHIUDI RICHIESTA
		if (record.getAttributeAsBoolean("abilRichAccessoAttiChiusura")) {
			chiusuraButton.show();
		} else {
			chiusuraButton.hide();
		}

		// RIAPRI RICHIESTA
		if (record.getAttributeAsBoolean("abilRichAccessoAttiRiapertura")) {
			riaperturaButton.show();
		} else {
			riaperturaButton.hide();
		}

		// RIPRISTINA RICHIESTA
		if (record.getAttributeAsBoolean("abilRichAccessoAttiRipristino")) {
			ripristinoButton.show();
		} else {
			ripristinoButton.hide();
		}
	}

	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}

	public List<DetailToolStripButton> getAzioniButtons() {

		List<DetailToolStripButton> azioniButtons = new ArrayList<DetailToolStripButton>();
		if (invioInApprovazioneButton == null) {
			invioInApprovazioneButton = new DetailToolStripButton("Manda in approvazione", "richiesteAccessoAtti/azioni/invioInApprovazione.png");
			invioInApprovazioneButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("INVIO_IN_APPROVAZIONE");
				}
			});
		}
		azioniButtons.add(invioInApprovazioneButton);

		if (approvazioneButton == null) {
			approvazioneButton = new DetailToolStripButton("Approva", "richiesteAccessoAtti/azioni/approvazione.png");
			approvazioneButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("APPROVAZIONE");
				}
			});
		}
		azioniButtons.add(approvazioneButton);

		if (invioEsitoVerificaArchivioButton == null) {
			invioEsitoVerificaArchivioButton = new DetailToolStripButton("Invia esito verifica al rich.",
					"richiesteAccessoAtti/azioni/invioEsitoVerificaArchivio.png");
			invioEsitoVerificaArchivioButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("INVIO_ESITO_VERIFICA_ARCHIVIO");
				}
			});
		}
		azioniButtons.add(invioEsitoVerificaArchivioButton);

		if (abilitaAppuntamentoDaAgendaButton == null) {
			abilitaAppuntamentoDaAgendaButton = new DetailToolStripButton("Abilita appuntamento da Agenda",
					"richiesteAccessoAtti/azioni/abilitaAppuntamentoDaAgenda.png");
			abilitaAppuntamentoDaAgendaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("ABILITA_APPUNTAMENTO_DA_AGENDA");
				}
			});
		}
		azioniButtons.add(abilitaAppuntamentoDaAgendaButton);

		if (setAppuntamentoButton == null) {
			setAppuntamentoButton = new DetailToolStripButton("Fissa appuntamento", "richiesteAccessoAtti/azioni/setAppuntamento.png");
			setAppuntamentoButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("SET_APPUNTAMENTO");
				}
			});
		}
		azioniButtons.add(setAppuntamentoButton);

		if (annullamentoAppuntamentoButton == null) {
			annullamentoAppuntamentoButton = new DetailToolStripButton("Annulla appuntamento", "richiesteAccessoAtti/azioni/annullamentoAppuntamento.png");
			annullamentoAppuntamentoButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("ANNULLAMENTO_APPUNTAMENTO");
				}
			});
		}
		azioniButtons.add(annullamentoAppuntamentoButton);

		if (registraPrelievoButton == null) {
			registraPrelievoButton = new DetailToolStripButton("Registra prelievo effettivo", "richiesteAccessoAtti/azioni/registraPrelievo.png");
			registraPrelievoButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("REGISTRA_PRELIEVO");
				}
			});
		}
		azioniButtons.add(registraPrelievoButton);

		if (registraRestituzioneButton == null) {
			registraRestituzioneButton = new DetailToolStripButton("Registra restituzione", "richiesteAccessoAtti/azioni/registraRestituzione.png");
			registraRestituzioneButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("REGISTRA_RESTITUZIONE");
				}
			});
		}
		azioniButtons.add(registraRestituzioneButton);

		if (annullamentoButton == null) {
			annullamentoButton = new DetailToolStripButton("Elimina richiesta", "richiesteAccessoAtti/azioni/annullamento.png");
			annullamentoButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("ANNULLAMENTO");
				}
			});
		}
		azioniButtons.add(annullamentoButton);

		if (stampaFoglioPrelievoButton == null) {
			stampaFoglioPrelievoButton = new DetailToolStripButton("Stampa foglio prelievo", "richiesteAccessoAtti/azioni/stampaFoglioPrelievo.png");
			stampaFoglioPrelievoButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneStampaFoglioPrelievoSingola();
				}
			});
		}
		azioniButtons.add(stampaFoglioPrelievoButton);

		// CHIUDI RICHIESTA
		if (chiusuraButton == null) {
			chiusuraButton = new DetailToolStripButton("Chiudi richiesta", "richiesteAccessoAtti/azioni/chiudi.png");
			chiusuraButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("CHIUSURA");
				}
			});
		}
		azioniButtons.add(chiusuraButton);

		// RIAPRI RICHIESTA
		if (riaperturaButton == null) {
			riaperturaButton = new DetailToolStripButton("Riapri richiesta", "richiesteAccessoAtti/azioni/riapri.png");
			riaperturaButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("RIAPERTURA");
				}
			});
		}
		azioniButtons.add(riaperturaButton);

		// RIPRISTINA RICHIESTA
		if (ripristinoButton == null) {
			ripristinoButton = new DetailToolStripButton("Ripristina richiesta", "richiesteAccessoAtti/azioni/ripristina.png");
			ripristinoButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					manageAzioneSuRichAccessoAttiSingola("RIPRISTINO");
				}
			});
		}
		azioniButtons.add(ripristinoButton);

		return azioniButtons;
	}

	private Record buildRecordSingleUd() {

		String idUd = vm.getValues().get("idUd") + "";

		final Record recordUd = new Record();
		recordUd.setAttribute("idUd", idUd);
		recordUd.setAttribute("elencoIdFolderAttiRich", getElencoIdFolderAttiRich(new Record(vm.getValues())));
		final RecordList listaRecord = new RecordList();
		listaRecord.add(recordUd);
		Record recordListaIdUd = new Record();
		recordListaIdUd.setAttribute("listaRecord", listaRecord);
		recordListaIdUd.setAttribute("numRichiesteConScansioneTotOParz", getNumRichiesteConScansioniParzialiOTotali(new Record(vm.getValues())));
		return recordListaIdUd;
	}

	private void manageAzioneSuRichAccessoAttiSingola(final String codOperazione) {
		// Forzo il caricamento della lista alla chiusra del dettaglio
		// perchè cambio lo stato della pratica
		setSaved(true);

		final boolean closeDetailAfterOperation = ((codOperazione != null) && ("ANNULLAMENTO".equalsIgnoreCase(codOperazione)
				|| "RIAPERTURA".equalsIgnoreCase(codOperazione) || "RIPRISTINO".equalsIgnoreCase(codOperazione) || "CHIUSURA".equalsIgnoreCase(codOperazione)))
						? true : false;

		final Record record = buildRecordSingleUd();
		record.setAttribute("codOperazione", codOperazione);

		AzioneSuRichAccessoAttiPopup lAzioneSuRichAccessoAttiPopup = new AzioneSuRichAccessoAttiPopup(record) {

			@Override
			public void onClickOkButton(DSCallback service) {

				record.setAttribute("dataAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValue("dataAppuntamento") : null);
				record.setAttribute("orarioAppuntamento", datiAppuntamentoForm != null ? datiAppuntamentoForm.getValueAsString("orarioAppuntamento") : null);
				record.setAttribute("dataPrelievo", datiPrelievoForm != null ? datiPrelievoForm.getValue("dataPrelievo") : null);
				record.setAttribute("dataRestituzione", datiRestituzioneForm != null ? datiRestituzioneForm.getValue("dataRestituzione") : null);
				record.setAttribute("motivi", motiviForm != null ? motiviForm.getValue("motivi") : null);
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
				try {
					GWTRestService<Record, Record> lSetAzioneSuRichAccessoAttiDataSource = new GWTRestService<Record, Record>(
							"SetAzioneSuRichAccessoAttiDataSource");
					lSetAzioneSuRichAccessoAttiDataSource.addData(record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Layout.hideWaitPopup();
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record record = response.getData()[0];
								boolean success = manageErroreSingoloAzioneRichiestaAtti(record, closeDetailAfterOperation);
								azioneSuRichAccessoAttiPopup.markForDestroy();
								if (success) {
									if (closeDetailAfterOperation) {
										Scheduler.get().scheduleDeferred(new ScheduledCommand() {

											@Override
											public void execute() {
												if (instance != null && instance.getLayout() != null) {
													instance.getLayout().hideDetail(true);
												}
											}
										});
									}
								}
							}
						}
					});
				} catch (Exception e) {
					Layout.hideWaitPopup();
				}
			}
		};
	}

	private void manageAzioneStampaFoglioPrelievoSingola() {
		Record recordSingolo = buildRecordSingleUd();
		Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere…");
		try {
			new GWTRestService<Record, Record>("SetAzioneSuRichAccessoAttiDataSource").executecustom("generaFoglioPrelievo", recordSingolo, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record result = response.getData()[0];
						boolean inError = result.getAttributeAsBoolean("recuperoModelloInError");
						if (inError) {
							Layout.addMessage(new MessageBean(result.getAttribute("recuperoModelloErrorMessage"), "", MessageType.ERROR));
						} else {
							manageErroreSingoloAzioneRichiestaAtti(result, false);
							String nomeFilePdf = result.getAttribute("nomeFileFoglioPrelievo");
							String uriFilePdf = result.getAttribute("uriFileFoglioPrelievo");
							if (uriFilePdf != null && !"".equalsIgnoreCase(uriFilePdf)) {
								InfoFileRecord infoFilePdf = InfoFileRecord
										.buildInfoFileString(JSON.encode(result.getAttributeAsRecord("infoFileFoglioPrelievo").getJsObj()));
								PreviewControl.switchPreview(uriFilePdf, false, infoFilePdf, "FileToExtractBean", nomeFilePdf);
							}
						}
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}

	// Restituisce il numero di richieste che contengono atti scanzionati parzialmente o totalmente
	// In dettaglio avrò al massimo una richiesta (quella visualizzata), la logica è stata generalizzata
	// per poter essere in futuro usata su azioni massive (quindi con più richieste)
	private int getNumRichiesteConScansioniParzialiOTotali(Record record) {
		int result = 0;
		Record[] attiRichiesti = record.getAttributeAsRecordArray("listaAttiRichiesti");
		if (attiRichiesti != null) {
			for (int i = 0; i < attiRichiesti.length; i++) {
				String statoScansione = attiRichiesti[i].getAttribute("statoScansione");
				if ((statoScansione != null) && (("P".equalsIgnoreCase(statoScansione)) || ("C".equalsIgnoreCase(statoScansione)))) {
					result += 1;
				}
			}
		}
		return result;
	}

	// Restituisce l'elenco degli idFolder degli atti richiesti, separati da ;
	private String getElencoIdFolderAttiRich(Record record) {
		String strElencoIdFolderAttiRich = "";
		Record[] attiRichiesti = record.getAttributeAsRecordArray("listaAttiRichiesti");
		if (attiRichiesti != null) {
			for (int i = 0; i < attiRichiesti.length; i++) {
				Record atto = attiRichiesti[i];
				// Devo inserire solamente gli atti presenti in cittadella (in quando sono gli unici che devono apparire nel foglio prelievo)
				if ((atto != null) && (atto.getAttribute("stato") != null) && (atto.getAttribute("stato").toLowerCase().startsWith("presente"))) {
					// L'atto è presente in cittadella, lo inserisco nella lista degli atti
					strElencoIdFolderAttiRich = strElencoIdFolderAttiRich + atto.getAttribute("idFolder") + ";";
				}
			}
			strElencoIdFolderAttiRich = (strElencoIdFolderAttiRich.length() > 1)
					? strElencoIdFolderAttiRich.substring(0, strElencoIdFolderAttiRich.length() - 1) : "";
		}
		return strElencoIdFolderAttiRich;
	}

	private boolean isEstremiProtococolloPraticaToEnable() {
		// Gli estremi di protocollo della pratica sono modificabili solamente da abilitazionmi RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isRichiedenteInternoToEnable() {
		// Sezione "Richiedente interno" va disabilitato se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isIndirizzoDiRiferimentoToEnable() {
		// Gli estremi di protocollo della pratica sono modificabili solamente da abilitazionmi RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isStatoAttiRichiestiToShow() {
		// Lo stato degli atti richiesti va nascosto quando si inserisce la pratica & #codSatoRichAccessoAtti in (I, DA)
		// String codStatoRichAccessoAtti = codStatoRichAccessoAttiItem.getValue() != null ? (String) codStatoRichAccessoAttiItem.getValue() : "";
		// int index = "I,DA".indexOf(codStatoRichAccessoAtti);
		// return !((this.mode != null && this.mode.equalsIgnoreCase("new")) || index > -1);

		// Lo stato degli atti va nascosto quando si inserisce la pratica
		// return !(this.mode != null && this.mode.equalsIgnoreCase("new"));

		// Lo stato atti va sempre mostrato
		return true;
	}

	private boolean isStatoAttiRichiestiEditable() {
		// Lo stato degli atti richiesti va abilitato se (#codSatoRichAccessoAtti == (DVC)) oppure (richiesta in ('ADF', 'ADPA', 'AF') e utente ha privilegio
		// RAA/CIT/VRF) e non sono in new mode
		if (this.mode != null && this.mode.equalsIgnoreCase("new")) {
			return false;
		} else {
			String codStatoRichAccessoAtti = codStatoRichAccessoAttiItem.getValue() != null ? (String) codStatoRichAccessoAttiItem.getValue() : "";
			int index = "(DVC)".indexOf(codStatoRichAccessoAtti);
			if (index > -1) {
				return true;
			} else if ("(ADF)".indexOf(codStatoRichAccessoAtti) > -1 || "(ADPA)".indexOf(codStatoRichAccessoAtti) > -1
					|| "(AF)".indexOf(codStatoRichAccessoAtti) > -1) {
				return Layout.isPrivilegioAttivo("RAA/CIT/VRF");
			} else {
				return false;
			}
		}
	}

	private boolean isElencoAttiRichiestiEditable() {
		// Si possono aggiungere o eliminare atti solamente solamente da abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isEstremiAttiRichiestiEditable() {
		// Gli estremi degli atti richietsi sono modificabili solamente da abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isNoteUffRichiedenteToEnable() {
		// Note uff. richiedente sugli atti vanno disabilitate se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isNoteCittadellaToEnable() {
		// Note cittadella sugli atti vanno disabilitate se l’utente non ha una delle abilitazioni RAA/CIT/VRF o RAA/CIT/PRL o RAA/CIT/RST
		return (Layout.isPrivilegioAttivo("RAA/CIT/VRF") || Layout.isPrivilegioAttivo("RAA/CIT/PRL") || Layout.isPrivilegioAttivo("RAA/CIT/RST"));
	}

	private boolean isRichiedenteEsternoToEnable() {
		// Sezione "Richiedente esterno" va disabilitato se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isRichiedenteDelegatoToEnable() {
		// Sezione "Richiedente delegato da" va disabilitato se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isEsibenteDellaRichiestaToEnable() {
		// Sezione "Esibente della richiesta" va disabilitato se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isMotivoEDettagliDellaRichiestaToEnable() {
		// Sezione "Motivo e dettagli richiesta" va disabilitato se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
	}

	private boolean isDatiPrelievoUffRichiedenteESoggEsternoToEnable() {
		// sono disabilitate se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT o RAA/FAP o RAA/CIT/PRL
		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT") || Layout.isPrivilegioAttivo("RAA/FAP")
				|| Layout.isPrivilegioAttivo("RAA/CIT/PRL"));
	}

	private boolean manageErroreSingoloAzioneRichiestaAtti(Record object, boolean skipReloadAfterSuccess) {

		boolean verify = false;
		Map errorMessages = object.getAttributeAsMap("errorMessages");
		String errorMsg = null;
		if (errorMessages != null && errorMessages.size() > 0) {
			// Ho al massiamo un record in errore
			String descrizione = (String) errorMessages.get(idUdItem.getValue() + "");
			errorMsg = descrizione.substring(descrizione.indexOf("@#@") + 3, descrizione.length());
			Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
		} else {
			verify = true;
			Layout.addMessage(new MessageBean("Operazione effettuata con successo", "", MessageType.INFO));
			if (!skipReloadAfterSuccess) {
				reload(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (layout != null) {
							layout.viewMode();
						} else {
							viewMode();
						}
					}
				});
			}
		}
		return verify;
	}

	protected boolean isRichiedenteInterno(Record dettaglio) {
		return dettaglio.getAttribute("tipoRichiedente") != null
				&& TipoRichiedente.RICH_INTERNO.getValue().equalsIgnoreCase(dettaglio.getAttributeAsString("tipoRichiedente"));
	}

	private void setItemRichiedenteEsternoInterno(String tipoRichiedente) {
		//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)
		if (tipoRichiedente.equalsIgnoreCase(TipoRichiedente.RICH_ESTERNO.getValue())) {			
//			if (nroProtocolloRichiestaItem != null) {
//				nroProtocolloRichiestaItem.setAttribute("obbligatorio", true);
//				nroProtocolloRichiestaItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Prot. Gen. N°"));
//				nroProtocolloRichiestaItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);;
//			}
//			if (annoProtocolloRichiestaItem != null) {
//				annoProtocolloRichiestaItem.setAttribute("obbligatorio", true);
//				annoProtocolloRichiestaItem.setTitle(FrontendUtil.getRequiredFormItemTitle("Anno"));
//				annoProtocolloRichiestaItem.setTitleStyle(it.eng.utility.Styles.formTitleBold);
//			}
//			nroProtocolloRichiestaItem.setCanEdit(true);
//			annoProtocolloRichiestaItem.setCanEdit(true);
//			dataProtocolloRichiestaItem.setCanEdit(true);
			richiedentiInterniItem.setAttribute("obbligatorio", false);
			richiedenteEsternoSection.setVisible(true);
			richiedenteInternoSection.setVisible(false);			
		} else {			
//			if (nroProtocolloRichiestaItem != null) {
//				nroProtocolloRichiestaItem.setTitle("Rich. accesso atti N°");
//				nroProtocolloRichiestaItem.setAttribute("obbligatorio", false);
//				nroProtocolloRichiestaItem.setAttribute("obbligatorio", false);
//			}
//			if (annoProtocolloRichiestaItem != null) {
//				annoProtocolloRichiestaItem.setTitle("Anno");
//				annoProtocolloRichiestaItem.setRequired(false);
//				annoProtocolloRichiestaItem.setAttribute("obbligatorio", false);
//			}
//			nroProtocolloRichiestaItem.setCanEdit(false);
//			annoProtocolloRichiestaItem.setCanEdit(false);
//			dataProtocolloRichiestaItem.setCanEdit(false);
			richiedentiInterniItem.setAttribute("obbligatorio", true);
			richiedenteEsternoSection.setVisible(false);
			richiedenteInternoSection.setVisible(true);			
		}
		estremiRichiestaForm.markForRedraw();
	}
	
	protected boolean hasEstremiProtocolloGenerale() {
		boolean hasNumProtocolloGenerale = nroProtocolloRichiestaItem.getValue() != null && !"".equals(nroProtocolloRichiestaItem.getValue());
		boolean hasAnnoProtocolloGenerale = annoProtocolloRichiestaItem.getValue() != null && !"".equals(annoProtocolloRichiestaItem.getValue());
		return hasNumProtocolloGenerale && hasAnnoProtocolloGenerale;
	}
	
	protected void recuperaDatiProtocollo() {
		if (hasEstremiProtocolloGenerale()) {
			Record lRecord = new Record();
			lRecord.setAttribute("nroProtocollo", nroProtocolloRichiestaItem.getValueAsString());
			lRecord.setAttribute("annoProtocollo", annoProtocolloRichiestaItem.getValueAsString());
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSource.executecustom("recuperaIdUd", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {

					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = response.getData()[0];
						String idUd = record.getAttributeAsString("idUd");
						if ((idUd != null) && (!"".equalsIgnoreCase(idUd))){
							recuperaDatiProtocolloFromIdUd(idUd);
						}					
					}
				}
			});
		}
	}
	
	protected void recuperaDatiProtocolloFromIdUd(String idUd){	
		Record lRecord = new Record();
		lRecord.setAttribute("idUd", idUd);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSource.addParam("isRecuperoDatiProtXNuovaRichAccessoAtti", "true");
		lGwtRestDataSource.getData(lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					String idFolder = record.getAttribute("idFolder");
					if ((idFolder == null) || ("".equalsIgnoreCase(idFolder))){
						// Layout.addMessage(new MessageBean("Non esiste un fascicolo/pratica con gli estremi di protocollo specificati", "", MessageType.ERROR));
					}					
					record.setAttribute("tipoDocumento", initialValuesRichiesta.get("tipoDocumento"));
					record.setAttribute("nomeTipoDocumento", initialValuesRichiesta.get("nomeTipoDocumento"));
					record.setAttribute("flgTipoDocConVie", initialValuesRichiesta.get("flgTipoDocConVie"));
					record.setAttribute("siglaPraticaSuSistUfficioRichiedente", initialValuesRichiesta.get("siglaPraticaSuSistUfficioRichiedente"));
					record.setAttribute("idNodoDefaultRicercaAtti", initialValuesRichiesta.get("idNodoDefaultRicercaAtti"));
					editRecord(record);	
					newMode();
					markForRedraw();					
				}
			}
		});
	}
	
	private void setFormValuesFromRecordArchivio(Record record) {
		estremiRichiestaForm.clearErrors(true);
		estremiRichiestaForm.setValue("idUd", record.getAttribute("idUdFolder"));
		// Carico i dati del fascicolo		
		if (idUdItem.getValue() != null){
			recuperaDatiProtocolloFromIdUd((String) idUdItem.getValue());
		}	
		estremiRichiestaForm.markForRedraw();
	}		

	/**
	 * Classe che implementa una sezione nella maschera di richiesta accesso atti
	 * 
	 */
	public class RichiestaAccessoAttiDetailSection extends DetailSection {

		public RichiestaAccessoAttiDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final DynamicForm... forms) {
			this(pTitle, null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public RichiestaAccessoAttiDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final DynamicForm... forms) {
			this(pTitle, pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public RichiestaAccessoAttiDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final String pBackgroundColor, final DynamicForm... forms) {
			super(pTitle, pHeight, pCanCollapse, pShowOpen, pIsRequired, pBackgroundColor, forms);
		}

		@Override
		public boolean isOpenable() {
			return true;
		}

		@Override
		public String getOpenErrorMessage() {
			return null;
		}

		@Override
		public boolean showFirstCanvasWhenEmptyAfterOpen() {
			return true;
		}
	}
	
	public class FascicoloLookupArchivio extends LookupArchivioPopup {

		public FascicoloLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);
		}
		
		@Override
		public String getWindowTitle() {
			return "Fascicolazione";
		}
		
		@Override
		public String getFinalita() {
			return "COLLEGA_UD";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

}