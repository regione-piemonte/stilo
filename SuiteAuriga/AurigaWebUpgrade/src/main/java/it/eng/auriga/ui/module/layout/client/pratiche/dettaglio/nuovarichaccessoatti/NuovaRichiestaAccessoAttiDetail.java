/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.DocumentDetail;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.auriga.ui.module.layout.client.postainarrivo.PostaInArrivoRegistrazioneWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtEntrataItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.MittenteProtInternaItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.OperazioniEffettuateWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewDocWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.AltreVieItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.pgweb.InteressatiItem;
import it.eng.auriga.ui.module.layout.client.richiestaAccessoAtti.AzioneSuRichAccessoAttiPopup;
import it.eng.auriga.ui.module.layout.client.timbra.FileDaTimbrareBean;
import it.eng.auriga.ui.module.layout.client.timbra.TimbraWindow;
import it.eng.auriga.ui.module.layout.client.timbra.TimbroUtil;
import it.eng.auriga.ui.module.layout.shared.util.AzioniRapide;
import it.eng.auriga.ui.module.layout.shared.util.TipoRichiedente;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class NuovaRichiestaAccessoAttiDetail extends DocumentDetail {

//	private final int TITTLE_ALIGN_WIDTH_MOTIVO_DETT_RICH = 125;
	
	protected static String _TAB_HEADER_ID = "HEADER";

	protected NuovaRichiestaAccessoAttiDetail instance;
	
	protected String tipoDocumento;
	protected String rowidDoc;
	protected Record recordFromLoadDett;
	
	protected VLayout mainLayout;
	
	protected TabSet tabSet;
	protected Tab tabDatiPrincipali;
	
	// DetailSection
	protected NuovaRichiestaAccessoAttiDetailSection estremiRichiestaSection;
	protected NuovaRichiestaAccessoAttiDetailSection richiedenteEsternoSection;
	protected NuovaRichiestaAccessoAttiDetailSection richiedenteInternoSection;
	protected NuovaRichiestaAccessoAttiDetailSection indirizzoRiferimentoSection;
	protected NuovaRichiestaAccessoAttiDetailSection attiRichiestiSection;
	protected NuovaRichiestaAccessoAttiDetailSection documentiIstruttoriaSection;
	protected NuovaRichiestaAccessoAttiDetailSection richiedenteDelegatoDaSection;
	// protected NuovaRichiestaAccessoAttiDetailSection esibentiSection;
	// protected NuovaRichiestaAccessoAttiDetailSection motivoRichiestaSection;
	// protected NuovaRichiestaAccessoAttiDetailSection incaricatoDelPrelievoSection;

	// DynamicForm
	protected DynamicForm tipoProtocollazioneForm;
	protected DynamicForm estremiRichiestaForm;
	protected DynamicForm richiedenteEsternoForm;
	protected DynamicForm richiedenteInternoForm;
	protected DynamicForm indirizzoRiferimentoForm;
	protected DynamicForm attiRichiestiForm;
	protected DynamicForm documentiIstruttoriaForm;
	protected DynamicForm richiedenteDelegatoDaForm;
	// protected DynamicForm esibentiForm;
	// protected DynamicForm motivoRichiestaForm;
	// protected DynamicForm incaricatoPrelievoUffRichiedenteForm;
	// protected DynamicForm incaricatoPrelievoPerRichEsternoForm;
	// protected DynamicForm datiAppuntamentoForm;
	// protected DynamicForm datiPrelievoForm;
	
	// Item
	protected HiddenItem idUdItem;
	protected HiddenItem rowidDocItem;
	protected HiddenItem tipoDocumentoItem;
	protected HiddenItem nomeTipoDocumentoItem;
	protected HiddenItem flgTipoDocConVieItem;
	protected HiddenItem codStatoRichAccessoAttiItem;
	protected HiddenItem idNodoDefaultRicercaAttiItem;
	protected HiddenItem  mezzoTrasmissioneItem;
	protected RadioGroupItem tipoRichiedenteItem;
	protected ExtendedTextItem nroProtocolloRichiestaItem; // Numero del protocollo della richiesta
	protected AnnoItem annoProtocolloRichiestaItem; // Anno del protocollo della richiesta
	protected ExtendedDateItem dataProtocolloRichiestaItem; // Data del protocollo della richiesta
	protected ImgButtonItem visualizzaDocumentiRichiestaButton; // Documenti della richiesta
	protected ImgButtonItem visualizzaDettStdProtButton; // Visualizza il dettaglio del protocollo nella modalita standard
	protected HiddenItem idEmailArrivoItem;
	protected ImgButtonItem iconaEmailRicevutaItem;
	protected ImgButtonItem lookupArchivioButton; // Lookup per caricare il documento essistente
	protected ImgButtonItem operazioniEffettuateButton;
	// protected HiddenItem siglaPraticaSuSistUfficioRichiedenteItem; // Sigla della pratica sui sistemi ufficio richiedente
	// protected TextItem numeroPraticaSuSistUfficioRichiedenteItem; // Numero della pratica sui sistemi ufficio richiedente
	// protected AnnoItem annoPraticaSuSistUfficioRichiedenteItem; // Anno della pratica sui sistemi ufficio richiedente
	// protected TextItem desStatoRichAccessoAttiItem; // Anno della pratica sui sistemi ufficio richiedente
//	protected SelezionaScrivaniaItem listaRespIstruttoriaItem;
	protected SelectItem utenteRicercatoreItem;
	protected HiddenItem cognomeNomeRicercatoreItem;
	
	protected MittenteProtEntrataItem richiedentiEsterniItem; // Richiedente esterno

	protected MittenteProtInternaItem richiedentiInterniItem; // Richiedente interno

	protected AltreVieItem indirizzoRiferimentoItem; // Via di riferimento

	protected CheckboxItem flgRichAttiFabbricaVisuraSueItem;
	protected CheckboxItem flgRichModificheVisuraSueItem;
	protected NuovoAttiRichiestiItem attiRichiestiItem;
	protected CheckboxItem flgAltriAttiDaRicercareVisuraSueItem;

	protected AllegatiItem listaDocumentiIstruttoriaItem;
	
	protected InteressatiItem richiedentiDelegatiDaItem; // Richiedente delegato da

//	protected EsibentiItem esibentiItem;

//	protected SelectItem motivoRichiestaItem; // Motivo della richiesta
//	protected TextAreaItem dettagliRichiestaItem; // Dettagli della richiesta

//	protected SelezionaUtenteItems incaricatoPrelPerUffRichiedenteItems;
//	protected SelezionaUtenteItems incaricatoPrelievoPerRichEsternoItem;
//	protected TextItem dataAppuntamentoItem;
//	protected TextItem orarioAppuntamentoItem;
//	protected TextItem provenienzaAppuntamentoItem;
//	protected TextItem dataPrelievoItem;
//	protected TextItem dataRestituzionePrelievoItem;
//	protected TextItem restituzionePrelievoAttestataDaItem;
	
	protected LinkedHashMap<String, String> attributiAddDocTabs;
	protected HashMap<String, VLayout> attributiAddDocLayouts;
	protected HashMap<String, AttributiDinamiciDetail> attributiAddDocDetails;
	protected Map<String, Object> attributiDinamiciDocDaCopiare;

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

	public NuovaRichiestaAccessoAttiDetail(String nomeEntita) {
		this(nomeEntita, null, null);
	}
	
	public NuovaRichiestaAccessoAttiDetail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs) {
		this(nomeEntita, attributiAddDocTabs, null);
	}
	
	public NuovaRichiestaAccessoAttiDetail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs, String idTipoDoc) {

		super(nomeEntita);

		instance = this;
		this.attributiAddDocTabs = attributiAddDocTabs != null ? attributiAddDocTabs : new LinkedHashMap<String, String>();
		this.tipoDocumento = idTipoDoc;
		
		setPaddingAsLayoutMargin(false);
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		if(!skipSuperBuild()) {
			build();
		}		
	}
	
	public boolean skipSuperBuild() {
		return false;
	}
	
	public void build() {
		
		createMainLayout();
		setMembers(mainLayout);
	}
	
	public boolean isAvvioRichAccessoAtti() {
		return false;
	}	
	
	public boolean isEseguibile() {
		return editing;
	}
	
	public String getMessaggioTab(String tabID) {
		return null;
	}
	
	/**
	 * Metodo per costruire il layout della maschera
	 * 
	 */
	protected void createMainLayout() {
		
		createTabSet();
		createDetailToolStrip();

		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();		
		mainLayout.addMember(tabSet);
		mainLayout.addMember(detailToolStrip);

		if (!showDetailToolStrip()) {
			detailToolStrip.hide();
		}
	}
	
	/**
	 * Metodo per costruire il TabSet
	 * 
	 */
	protected void createTabSet() throws IllegalStateException {

		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		
		createTabDatiPrincipali();
		tabSet.addTab(tabDatiPrincipali);
	}
	
	/**
	 * Metodo per costruire il tab "Dati principali"
	 * 
	 */
	protected void createTabDatiPrincipali() {

		tabDatiPrincipali = new Tab("<b>Dati principali</b>");
		tabDatiPrincipali.setAttribute("tabID", _TAB_HEADER_ID);
		tabDatiPrincipali.setPrompt("Dati principali");
		tabDatiPrincipali.setPane(createTabPane(getLayoutDatiPrincipali()));
	}

	/**
	 * Metodo che restituisce il layout del tab "Dati principali"
	 * 
	 */
	public VLayout getLayoutDatiPrincipali() {

		VLayout layoutDatiPrincipali = new VLayout(5);
		
		buildEstremiRichiestaSection();
		layoutDatiPrincipali.addMember(estremiRichiestaSection);
		
		buildRichiedenteEsternoSection();
		layoutDatiPrincipali.addMember(richiedenteEsternoSection);
		
		buildRichiedenteInternoSection();
		layoutDatiPrincipali.addMember(richiedenteInternoSection);
		
		buildIndirizzoRiferimentoSection();
		layoutDatiPrincipali.addMember(indirizzoRiferimentoSection);
		
		buildAttiRichiestiSection();
		layoutDatiPrincipali.addMember(attiRichiestiSection);
	
		buildDocumentiIstruttoriaSection();
		layoutDatiPrincipali.addMember(documentiIstruttoriaSection);
	
		buildRichiedenteDelegatoDaSection();
		layoutDatiPrincipali.addMember(richiedenteDelegatoDaSection);
		
//		buildEsibenteRichiestaSection();
//		layoutDatiPrincipali.addMember(esibentiSection);
		
//		buildMotivoRichiestaSection();
//		layoutDatiPrincipali.addMember(motivoRichiestaSection);
		
//		buildIncaricatoDelPrelievoSection();
//		layoutDatiPrincipali.addMember(incaricatoDelPrelievoSection);
	
		return layoutDatiPrincipali;
	}

	/**
	 * Metodo per costruire il pane associato ad un tab generico
	 * 
	 */
	protected VLayout createTabPane(VLayout layout) {

		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		
		return layoutTab;
	}

	protected void createDetailToolStrip() {

		editButton = new DetailToolStripButton(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				editMode();
			}
		});

		saveButton = new DetailToolStripButton(I18NUtil.getMessages().saveButton_prompt(), "buttons/save.png");
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
//				motivoRichiestaSection.openIfhasValue();
//				incaricatoDelPrelievoSection.openIfhasValue();
				vm.clearErrors(true);
				final Record lRecordToSave = getRecordToSave();
				final GWTRestDataSource lNuovaRichiestaAccessoAttiDatasource = new GWTRestDataSource("NuovaRichiestaAccessoAttiDatasource");
				if (validate()) {
					Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
					try {
						DSCallback callback = new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record record = response.getData()[0];
									try {
										lNuovaRichiestaAccessoAttiDatasource.getData(record, new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
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
							lNuovaRichiestaAccessoAttiDatasource.updateData(lRecordToSave, callback);
						} else {
							lNuovaRichiestaAccessoAttiDatasource.addData(lRecordToSave, callback);
						}
					} catch (Exception e) {
						Layout.hideWaitPopup();
						abilSaveButton = true; //Riabilito il pulsante di salvataggio
					}

				} else {
					// openSectionsIfHasError();
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
					abilSaveButton = true; //Riabilito il pulsante di salvataggio
				}
			}
		});
	}

	@Override
	public Record getRecordToSave() {

		Record recordToSave = super.getRecordToSave();
//		Se non arrivano i valori aggiungere con addtabsvalue
//		I ckeditor vanno aggiunti manulamente
//		Qua metto altre cose costruite non lette direttamete dalla maschera

		// Tolgo il mittente se è vuoto
		recordToSave.setAttribute("listaMittenti", checkRecordArrayToSave(recordToSave.getAttributeAsRecordArray("listaMittenti"), false,
				"denominazioneMittente", "cognomeMittente", "nomeMittente"));
		
		// Attributi dinamici doc
		if (attributiAddDocDetails != null) {
			recordToSave.setAttribute("rowidDoc", rowidDoc);
			recordToSave.setAttribute("valori", getAttributiDinamiciDoc());
			recordToSave.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			recordToSave.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}
		
		return recordToSave;
	}

	protected void openSectionsIfHasError() {
		
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
//		if (esibentiSection != null) {
//			esibentiSection.openIfhasErrors();
//		}
//		if (motivoRichiestaSection != null) {
//			motivoRichiestaSection.openIfhasErrors();
//		}
//		if (incaricatoDelPrelievoSection != null) {
//			incaricatoDelPrelievoSection.openIfhasErrors();
//		}
		if (documentiIstruttoriaSection != null) {
			documentiIstruttoriaSection.openIfhasErrors();
		}
	}
	
	protected void openSectionsIfHasValue() {
		
		if (estremiRichiestaSection != null) {
			estremiRichiestaSection.openIfhasValue();;
		}
		if (indirizzoRiferimentoSection != null) {
			indirizzoRiferimentoSection.openIfhasValue();
		}
		if (attiRichiestiSection != null) {
			attiRichiestiSection.openIfhasValue();
		}
		if (richiedenteEsternoSection != null) {
			richiedenteEsternoSection.openIfhasValue();
		}
		if (richiedenteDelegatoDaSection != null) {
			richiedenteDelegatoDaSection.openIfhasValue();
		}
//		if (esibentiSection != null) {
//			esibentiSection.openIfhasValue();
//		}
//		if (motivoRichiestaSection != null) {
//			motivoRichiestaSection.openIfhasValue();
//		}
//		if (incaricatoDelPrelievoSection != null) {
//			incaricatoDelPrelievoSection.openIfhasValue();
//		}
		if (documentiIstruttoriaSection != null) {
			documentiIstruttoriaSection.openIfhasValue();
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
		tipoProtocollazioneForm.setTabID(_TAB_HEADER_ID);

		LinkedHashMap<String, String> tipoProtocolloMap = new LinkedHashMap<String, String>();
		tipoProtocolloMap.put(TipoRichiedente.RICH_ESTERNO.getValue(), TipoRichiedente.RICH_ESTERNO.getDescrizione());
		tipoProtocolloMap.put(TipoRichiedente.RICH_INTERNO.getValue(), TipoRichiedente.RICH_INTERNO.getDescrizione());
		tipoRichiedenteItem = new RadioGroupItem("tipoRichiedente", "Richiedente");
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
		estremiRichiestaForm.setTabID(_TAB_HEADER_ID);

		idUdItem = new HiddenItem("idUd");
		rowidDocItem = new HiddenItem("rowidDoc");
		
		tipoDocumentoItem = new HiddenItem("tipoDocumento");
		nomeTipoDocumentoItem = new HiddenItem("nomeTipoDocumento");
		flgTipoDocConVieItem = new HiddenItem("flgTipoDocConVie");
		codStatoRichAccessoAttiItem = new HiddenItem("codStatoRichAccessoAtti");
		idNodoDefaultRicercaAttiItem = new HiddenItem("idNodoDefaultRicercaAtti");
		mezzoTrasmissioneItem = new HiddenItem("mezzoTrasmissione");

		nroProtocolloRichiestaItem = new ExtendedTextItem("nroProtocolloPregresso", "Prot. Gen. N°") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(canEdit);
				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
				setTabIndex(canEdit ? 0 : -1);
			}
		};
		nroProtocolloRichiestaItem.setLength(7);
		nroProtocolloRichiestaItem.setWidth(120);
		nroProtocolloRichiestaItem.setAttribute("obbligatorio", true);
		nroProtocolloRichiestaItem.setRequired(true);
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
					String idUd = estremiRichiestaForm.getValueAsString("idUd");
					if (idUd != null && !"".equals(idUd)) {
						return true;
					} else {
						return false;
					}
//				} else {
//					return true;
//				}
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
		annoProtocolloRichiestaItem.setAttribute("obbligatorio", true);
		annoProtocolloRichiestaItem.setRequired(true);
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
		
		visualizzaDocumentiRichiestaButton = new ImgButtonItem("visualizzaDocumentiRichiestaButton", "allegati.png", "File della richiesta");
		visualizzaDocumentiRichiestaButton.setAlwaysEnabled(true);
		visualizzaDocumentiRichiestaButton.setEndRow(false);
		visualizzaDocumentiRichiestaButton.setColSpan(1);
		visualizzaDocumentiRichiestaButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idUd = vm.getValueAsString("idUd");
				ListGridRecord lListGridRecord = new ListGridRecord();
				lListGridRecord.setAttribute("idUd", idUd);
				lListGridRecord.setAttribute("flgUdFolder", "U");
				
				showRowContextMenu(lListGridRecord, null);
			}
		});
		
		visualizzaDettStdProtButton = new ImgButtonItem("visualizzaDettStdProtButton", "buttons/dati_protocollo.png", "Visualizza dati protocollo");
		visualizzaDettStdProtButton.setAlwaysEnabled(true);
		visualizzaDettStdProtButton.setEndRow(false);
		visualizzaDettStdProtButton.setColSpan(1);
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
		
		idEmailArrivoItem = new HiddenItem("idEmailArrivo");
		
		iconaEmailRicevutaItem = new ImgButtonItem("iconaEmailRicevuta", "mail/ricevuta.png",
				I18NUtil.getMessages().archivio_detail_emailRicevutaAlt_value());
		iconaEmailRicevutaItem.setAlwaysEnabled(true);
		iconaEmailRicevutaItem.setEndRow(false);
		iconaEmailRicevutaItem.setColSpan(1);
		iconaEmailRicevutaItem.setIconWidth(16);
		iconaEmailRicevutaItem.setIconHeight(16);
		iconaEmailRicevutaItem.setIconVAlign(VerticalAlignment.BOTTOM);
		iconaEmailRicevutaItem.setAlign(Alignment.LEFT);
		iconaEmailRicevutaItem.setWidth(16);
		iconaEmailRicevutaItem.setRedrawOnChange(true);
		iconaEmailRicevutaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (idEmailArrivoItem.getValue() != null && !idEmailArrivoItem.getValue().equals(""));
			}
		});
		iconaEmailRicevutaItem.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record record = new Record();
				record.setAttribute("idEmail", (String) idEmailArrivoItem.getValue());
				record.setAttribute("flgIo", "I");
				new PostaInArrivoRegistrazioneWindow(record);
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

//		SpacerItem desStatoRichAccessoAttiItemSpacer = new SpacerItem();
//		desStatoRichAccessoAttiItemSpacer.setWidth(20);
//
//		desStatoRichAccessoAttiItem = new TextItem("desStatoRichAccessoAtti", "Stato della richiesta") {
//
//			@Override
//			public void setCanEdit(Boolean canEdit) {
//				super.setCanEdit(false);
//				setTextBoxStyle(it.eng.utility.Styles.textItemBold);
//				setTabIndex(-1);
//			}
//		};
//		desStatoRichAccessoAttiItem.setWidth(350);
//		desStatoRichAccessoAttiItem.setColSpan(10);
//		desStatoRichAccessoAttiItem.setStartRow(false);
//		desStatoRichAccessoAttiItem.setShowIfCondition(new FormItemIfFunction() {
//
//			@Override
//			public boolean execute(FormItem item, Object value, DynamicForm form) {
//				return (item.getValue() != null && !"".equalsIgnoreCase(((String) item.getValue()).trim()));
//			}
//		});
//
//		siglaPraticaSuSistUfficioRichiedenteItem = new HiddenItem("siglaPraticaSuSistUfficioRichiedente");
//
//		numeroPraticaSuSistUfficioRichiedenteItem = new TextItem("numeroPraticaSuSistUfficioRichiedente",
//				setTitleAlign("ID nel sistema uff. richiedente N°", TITTLE_ALIGN_WIDTH_2, false)) {
//
//			@Override
//			public void setCanEdit(Boolean canEdit) {
//				super.setCanEdit(canEdit);
//				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
//				setTabIndex(canEdit ? 0 : -1);
//			}
//		};
//		numeroPraticaSuSistUfficioRichiedenteItem.setWidth(120);
//		numeroPraticaSuSistUfficioRichiedenteItem.setStartRow(true);
//		numeroPraticaSuSistUfficioRichiedenteItem.setKeyPressFilter("[0-9]");
//
//		annoPraticaSuSistUfficioRichiedenteItem = new AnnoItem("annoPraticaSuSistUfficioRichiedente", "Anno") {
//
//			@Override
//			public void setCanEdit(Boolean canEdit) {
//				super.setCanEdit(canEdit);
//				setTextBoxStyle(canEdit ? it.eng.utility.Styles.textItem : it.eng.utility.Styles.textItemBold);
//				setTabIndex(canEdit ? 0 : -1);
//			}
//		};

//		listaRespIstruttoriaItem = new SelezionaScrivaniaItem() {
//			
//			@Override
//			public void manageChangedScrivaniaSelezionata() {
//				
//			}
//			
//			@Override
//			public Boolean getShowRemoveButton() {
//				return false;
//			};
//			
//			@Override
//			protected VLayout creaVLayout() {
//				VLayout lVLayout = super.creaVLayout();
//				lVLayout.setWidth100();
//				lVLayout.setPadding(11);
//				lVLayout.setMargin(4);
//				lVLayout.setIsGroup(true);
//				lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
//				lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">Resp. Istruttoria</span>");
//				return lVLayout;
//			}
//		};
//		listaRespIstruttoriaItem.setName("listaRespIstruttoria");
//		listaRespIstruttoriaItem.setStartRow(true);
//		listaRespIstruttoriaItem.setShowTitle(false);
//		listaRespIstruttoriaItem.setColSpan(20);
//		listaRespIstruttoriaItem.setNotReplicable(true);		
		
		final GWTRestDataSource utenteRicercatoreDS = new GWTRestDataSource("RicercatoriVisureDataSource", "idUtenteRicercatore", FieldType.TEXT);

		utenteRicercatoreItem = new SelectItem("idUtenteRicercatore", "Ricercatore incaricato") {

			@Override
			public void onOptionClick(Record record) {
				cognomeNomeRicercatoreItem.setValue(record.getAttributeAsString("cognomeNomeRicercatore"));
			}
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					cognomeNomeRicercatoreItem.setValue("");
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				cognomeNomeRicercatoreItem.setValue("");
			};
		};
		utenteRicercatoreItem.setWidth(300);
		utenteRicercatoreItem.setColSpan(5);
		utenteRicercatoreItem.setAlign(Alignment.CENTER);
		utenteRicercatoreItem.setValueField("idUtenteRicercatore");
		utenteRicercatoreItem.setDisplayField("cognomeNomeRicercatore");
		utenteRicercatoreItem.setOptionDataSource(utenteRicercatoreDS);
		utenteRicercatoreItem.setAutoFetchData(false);
		utenteRicercatoreItem.setAlwaysFetchMissingValues(true);
		utenteRicercatoreItem.setFetchMissingValues(true);
		utenteRicercatoreItem.setAllowEmptyValue(true);
		
		cognomeNomeRicercatoreItem = new HiddenItem("cognomeNomeRicercatore");
						
		estremiRichiestaForm.setItems(
				idUdItem, rowidDocItem, tipoDocumentoItem, nomeTipoDocumentoItem, flgTipoDocConVieItem, codStatoRichAccessoAttiItem, idNodoDefaultRicercaAttiItem, mezzoTrasmissioneItem,
				nroProtocolloRichiestaItem, annoProtocolloRichiestaItem, dataProtocolloRichiestaItem, visualizzaDocumentiRichiestaButton, visualizzaDettStdProtButton, 
				idEmailArrivoItem, iconaEmailRicevutaItem, lookupArchivioButton, operazioniEffettuateButton, /*listaRespIstruttoriaItem,*/ utenteRicercatoreItem, cognomeNomeRicercatoreItem);

		estremiRichiestaSection = new NuovaRichiestaAccessoAttiDetailSection("Estremi richiesta", false, true, false, tipoProtocollazioneForm, estremiRichiestaForm);		
	}

	private void buildRichiedenteEsternoSection() {

		richiedenteEsternoForm = new DynamicForm();
		richiedenteEsternoForm.setValuesManager(vm);
		richiedenteEsternoForm.setWidth("100%");
		richiedenteEsternoForm.setHeight("5");
		richiedenteEsternoForm.setPadding(5);
		richiedenteEsternoForm.setTabID(_TAB_HEADER_ID);		
		
		richiedentiEsterniItem = new MittenteProtEntrataItem() {

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

		richiedenteEsternoSection = new NuovaRichiestaAccessoAttiDetailSection("Richiedente esterno", true, false, false, richiedenteEsternoForm);
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
		richiedenteInternoForm.setTabID(_TAB_HEADER_ID);

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

		richiedenteInternoSection = new NuovaRichiestaAccessoAttiDetailSection("Richiedente interno", true, true, true, richiedenteInternoForm);
	}

	private void buildIndirizzoRiferimentoSection() {

		// Indirizzo di riferimento
		indirizzoRiferimentoForm = new DynamicForm();
		indirizzoRiferimentoForm.setValuesManager(vm);
		indirizzoRiferimentoForm.setWidth("100%");
		indirizzoRiferimentoForm.setHeight("5");
		indirizzoRiferimentoForm.setPadding(5);
		indirizzoRiferimentoForm.setTabID(_TAB_HEADER_ID);

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
		};
		indirizzoRiferimentoItem.setName("listaAltreVie");
		indirizzoRiferimentoItem.setShowTitle(false);
		indirizzoRiferimentoItem.setAttribute("obbligatorio", true);
		indirizzoRiferimentoItem.setNotReplicable(true);

		indirizzoRiferimentoForm.setFields(indirizzoRiferimentoItem);

		indirizzoRiferimentoSection = new NuovaRichiestaAccessoAttiDetailSection("Indirizzo di riferimento", true, true, true, indirizzoRiferimentoForm);
	}

	private void buildAttiRichiestiSection() {

		attiRichiestiForm = new DynamicForm();
		attiRichiestiForm.setValuesManager(vm);
		attiRichiestiForm.setWidth("100%");
		attiRichiestiForm.setHeight("5");
		attiRichiestiForm.setNumCols(15);
		attiRichiestiForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
		attiRichiestiForm.setPadding(5);
		attiRichiestiForm.setTabID(_TAB_HEADER_ID);
		
		flgRichAttiFabbricaVisuraSueItem = new CheckboxItem("flgRichAttiFabbricaVisuraSue", "Atti di fabbrica");
		flgRichAttiFabbricaVisuraSueItem.setStartRow(true);
		flgRichAttiFabbricaVisuraSueItem.setColSpan(1);
		flgRichAttiFabbricaVisuraSueItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (mezzoTrasmissioneItem.getValue() != null && "PEC".equalsIgnoreCase(mezzoTrasmissioneItem.getValue().toString())) {
					flgRichAttiFabbricaVisuraSueItem.setCanEdit(true);
				} else {
					flgRichAttiFabbricaVisuraSueItem.setCanEdit(false);
				}
				return true;
			}
		});
		
		flgRichModificheVisuraSueItem = new CheckboxItem("flgRichModificheVisuraSue", "Modifiche");
		flgRichModificheVisuraSueItem.setStartRow(true);
		flgRichModificheVisuraSueItem.setColSpan(1);
		flgRichModificheVisuraSueItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if (mezzoTrasmissioneItem.getValue() != null && "PEC".equalsIgnoreCase(mezzoTrasmissioneItem.getValue().toString())) {
					flgRichModificheVisuraSueItem.setCanEdit(true);
				} else {
					flgRichModificheVisuraSueItem.setCanEdit(false);
				}
				return true;
			}
		});
		
		attiRichiestiItem = new NuovoAttiRichiestiItem() {

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
			public boolean isTaskArchivioInCanvas() {
				return isTaskArchivio();
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
		
		flgAltriAttiDaRicercareVisuraSueItem = new CheckboxItem("flgAltriAttiDaRicercareVisuraSue", "Atti ancora da individuare");
		flgAltriAttiDaRicercareVisuraSueItem.setStartRow(true);
		flgAltriAttiDaRicercareVisuraSueItem.setColSpan(1);

		attiRichiestiForm.setFields(flgRichAttiFabbricaVisuraSueItem, flgRichModificheVisuraSueItem, attiRichiestiItem, flgAltriAttiDaRicercareVisuraSueItem);

		attiRichiestiSection = new NuovaRichiestaAccessoAttiDetailSection("Atti richiesti", true, false, false, attiRichiestiForm);
	}
	
	
	private void buildDocumentiIstruttoriaSection() {
		
		documentiIstruttoriaForm = new DynamicForm();
		documentiIstruttoriaForm.setValuesManager(vm);
		documentiIstruttoriaForm.setWidth100();
		documentiIstruttoriaForm.setPadding(5);
		documentiIstruttoriaForm.setWrapItemTitles(false);
		documentiIstruttoriaForm.setNumCols(20);
		documentiIstruttoriaForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		documentiIstruttoriaForm.setTabSet(tabSet);
		documentiIstruttoriaForm.setTabID(_TAB_HEADER_ID);
		
		listaDocumentiIstruttoriaItem = new AllegatiItem() {
			
			@Override
			public GWTRestDataSource getTipiFileAllegatoDataSource() {
				GWTRestDataSource lLoadComboTipoDocInProcessDataSource = new GWTRestDataSource("LoadComboTipoDocInProcessDataSource", "idTipoDoc",
						FieldType.TEXT, true);
				lLoadComboTipoDocInProcessDataSource.addParam("idProcess", getIdProcessTask());
				return lLoadComboTipoDocInProcessDataSource;
			}
			
			@Override
			public boolean showFilterEditorInTipiFileAllegato() {
				return false;
			}

			public String getIdProcess() {
				return getIdProcessTask();
			}

			@Override
			public String getIdProcessType() {
				return getIdProcessTypeTask();
			}

			public String getIdTaskCorrenteAllegati() {
				return getIdTaskCorrente();
			}

			public HashSet<String> getTipiModelliAttiAllegati() {
				return getTipiModelliAttiInDocumentiIstruttoria();
			}

			public boolean showGeneraDaModello() {
				return true;
			}
			
			@Override
			public boolean isModelloModificabile(String idModello) {
				return isModelloModificabileInAllegati(idModello);
			}

			@Override
			public Record getRecordCaricaModelloAllegato(String idModello, String tipoModello) {
				return getRecordCaricaModelloInDocumentiIstruttoria(idModello, tipoModello);
			}
			
			@Override
			public void visualizzaVersioni(Record allegatoRecord) {
				final String nroProgr = allegatoRecord.getAttributeAsString("numeroProgrAllegato");
				final String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");						
				final GWTRestDataSource lProtocolloDataSource = new GWTRestDataSource("ProtocolloDataSource");
				Record lRecordToLoad = new Record();
				lRecordToLoad.setAttribute("idUd", allegatoRecord.getAttributeAsString("idUdAppartenenza"));
				lProtocolloDataSource.getData(lRecordToLoad, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordProtocollo = response.getData()[0];
							ProtocollazioneDetail.visualizzaVersioni(idDoc, "DI", nroProgr, "", recordProtocollo);
						}
					}
				});
			}
			
			@Override
			public Integer getWidthDescrizioneFileAllegato() {
				return 250;
			}
			
			@Override
			public Integer getWidthNomeFileAllegato() {
				return 250;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return false;
			}
			
			@Override
			public boolean showVisualizzaFileUdButton() {
				return false;
			}
			
			@Override
			public void caricaModelloAllegato(String idModello, String tipoModello, String flgConvertiInPdf, ServiceCallback<Record> callback) {
				caricaModelloInDocumentiIstruttoria(idModello, tipoModello, flgConvertiInPdf, callback);
			}
			
			@Override
			public boolean canBeEditedByApplet() {
				return true;
			}
			
			@Override
			public boolean isDocumentiIstruttoria() {
				return true;
			}
			
			@Override
			public boolean isDocPraticaVisure() {
				return true;
			}
			
		};
		listaDocumentiIstruttoriaItem.setName("listaDocumentiIstruttoria");
		listaDocumentiIstruttoriaItem.setShowTitle(false);
		listaDocumentiIstruttoriaItem.setShowFlgParteDispositivo(false);
		listaDocumentiIstruttoriaItem.setShowFlgNoPubblAllegato(false);
		listaDocumentiIstruttoriaItem.setColSpan(20);
		
		documentiIstruttoriaForm.setFields(listaDocumentiIstruttoriaItem);	
		
		documentiIstruttoriaSection = new NuovaRichiestaAccessoAttiDetailSection("Documenti istruttoria", true, false, false, documentiIstruttoriaForm);
	}

	private void buildRichiedenteDelegatoDaSection() {

		richiedenteDelegatoDaForm = new DynamicForm();
		richiedenteDelegatoDaForm.setValuesManager(vm);
		richiedenteDelegatoDaForm.setWidth("100%");
		richiedenteDelegatoDaForm.setHeight("5");
		richiedenteDelegatoDaForm.setPadding(5);
		richiedenteDelegatoDaForm.setTabID(_TAB_HEADER_ID);		
		
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

		richiedenteDelegatoDaSection = new NuovaRichiestaAccessoAttiDetailSection("Richiedente delegato da", true, false, false, richiedenteDelegatoDaForm);
	}

//	private void buildEsibenteRichiestaSection() {
//
//		esibentiForm = new DynamicForm();
//		esibentiForm.setValuesManager(vm);
//		esibentiForm.setWidth("100%");
//		esibentiForm.setHeight("5");
//		esibentiForm.setPadding(5);
//		esibentiForm.setTabID(_TAB_HEADER_ID);		
//				
//		esibentiItem = new EsibentiItem() {
//
//			@Override
//			public boolean getShowAncheMittente() {
//				return false;
//			}
//
//			@Override
//			public boolean isRequiredDenominazione(boolean hasValue) {
//				return hasValue;
//			}
//
//			@Override
//			public boolean validazioneItemAbilitata() {
//				return true;
//			}
//		};
//		esibentiItem.setName("listaEsibenti");
//		esibentiItem.setShowTitle(false);
//		esibentiItem.setNotReplicable(true);
//
//		esibentiForm.setFields(esibentiItem);
//
//		esibentiSection = new NuovaRichiestaAccessoAttiDetailSection("Esibente della richiesta (se diverso dal richiedente)", true, false, false, esibentiForm);
//	}

//	private void buildMotivoRichiestaSection() {
//
//		motivoRichiestaForm = new DynamicForm();
//		motivoRichiestaForm.setValuesManager(vm);
//		motivoRichiestaForm.setWidth("100%");
//		motivoRichiestaForm.setHeight("5");
//		motivoRichiestaForm.setPadding(5);
//		motivoRichiestaForm.setNumCols(10);
//		motivoRichiestaForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
//		motivoRichiestaForm.setWrapItemTitles(false);
//		motivoRichiestaForm.setTabID(_TAB_HEADER_ID);
//
//		final GWTRestDataSource motivoRichiestaDS = new GWTRestDataSource("LoadComboMotivoRichiestaDataSource", "key", FieldType.TEXT);
//		motivoRichiestaItem = new SelectItem("motivoRichiestaAccessoAtti", setTitleAlign("Motivo della richiesta", TITTLE_ALIGN_WIDTH_MOTIVO_DETT_RICH, false));
//
//		motivoRichiestaItem.setValueField("key");
//		motivoRichiestaItem.setDisplayField("value");
//		motivoRichiestaItem.setOptionDataSource(motivoRichiestaDS);
//		motivoRichiestaItem.setWidth(200);
//		motivoRichiestaItem.setStartRow(true);
//
//		dettagliRichiestaItem = new TextAreaItem("dettagliRichiestaAccessoAtti", setTitleAlign("Dettagli richiesta", TITTLE_ALIGN_WIDTH_MOTIVO_DETT_RICH, false));
//		dettagliRichiestaItem.setWidth(600);
//		dettagliRichiestaItem.setHeight(50);
//		dettagliRichiestaItem.setStartRow(false);
//		
//		SpacerItem motivoRichiestaSpacerItem = new SpacerItem();
//		motivoRichiestaSpacerItem.setWidth(15);
//	
//		motivoRichiestaForm.setItems(motivoRichiestaItem, motivoRichiestaSpacerItem, dettagliRichiestaItem);
//
//		motivoRichiestaSection = new NuovaRichiestaAccessoAttiDetailSection("Motivo e dettagli richiesta", true, false, false, motivoRichiestaForm);
//	}

//	private void buildIncaricatoDelPrelievoSection() {
//
//		incaricatoPrelievoUffRichiedenteForm = new DynamicForm();
//		incaricatoPrelievoUffRichiedenteForm.setValuesManager(vm);
//		incaricatoPrelievoUffRichiedenteForm.setWidth("100%");
//		incaricatoPrelievoUffRichiedenteForm.setNumCols(20);
//		incaricatoPrelievoUffRichiedenteForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");
//		incaricatoPrelievoUffRichiedenteForm.setWrapItemTitles(false);
//		incaricatoPrelievoUffRichiedenteForm.setTabID(_TAB_HEADER_ID);
////	incaricatoPrelievoUffRichiedenteForm.setPadding(5);
//		incaricatoPrelievoUffRichiedenteForm.setMargin(4);
//		incaricatoPrelievoUffRichiedenteForm.setIsGroup(true);
//		incaricatoPrelievoUffRichiedenteForm.setStyleName(it.eng.utility.Styles.detailSection);
//		incaricatoPrelievoUffRichiedenteForm.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">Per l'ufficio richiedente</span>");
//			
//		// Uso questo spacer per mettere un margine che separi i campi dal bordo del form
//		SpacerItem spacerMargin = new SpacerItem();
//		spacerMargin.setColSpan(1);
//		spacerMargin.setWidth(5);
//		spacerMargin.setHeight(40);
//
//		incaricatoPrelPerUffRichiedenteItems = new SelezionaUtenteItems(incaricatoPrelievoUffRichiedenteForm, "idIncaricatoPrelievoPerUffRichiedente",
//				"usernameIncaricatoPrelievoPerUffRichiedente", "codRapidoIncaricatoPrelievoPerUffRichiedente", "cognomeIncaricatoPrelievoPerUffRichiedente",
//				"nomeIncaricatoPrelievoPerUffRichiedente", "codiceFiscaleIncaricatoPrelievoPerUffRichiedente", "emailIncaricatoPrelievoPerUffRichiedente",
//				"telefonoIncaricatoPrelievoPerUffRichiedente") {
//
//			@Override
//			protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//
//			@Override
//			protected boolean codiceFiscaleItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//
//			@Override
//			protected boolean emailItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//
//			@Override
//			protected boolean telefonoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//		};
//
//		List<FormItem> lista = new ArrayList<FormItem>();
//		lista.add(spacerMargin);
//		lista.addAll(incaricatoPrelPerUffRichiedenteItems);
//		lista.add(spacerMargin);
//
//		incaricatoPrelievoUffRichiedenteForm.setItems(lista.toArray(new FormItem[lista.size()]));
//
//		incaricatoPrelievoPerRichEsternoForm = new DynamicForm();
//		incaricatoPrelievoPerRichEsternoForm.setValuesManager(vm);
//		incaricatoPrelievoPerRichEsternoForm.setWidth("100%");
//		incaricatoPrelievoPerRichEsternoForm.setNumCols(20);
//		incaricatoPrelievoPerRichEsternoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*", "*");
//		incaricatoPrelievoPerRichEsternoForm.setWrapItemTitles(false);
//		incaricatoPrelievoPerRichEsternoForm.setTabID(_TAB_HEADER_ID);
////	incaricatoPrelievoPerRichEsternoForm.setPadding(5);
//		incaricatoPrelievoPerRichEsternoForm.setMargin(4);
//		incaricatoPrelievoPerRichEsternoForm.setIsGroup(true);
//		incaricatoPrelievoPerRichEsternoForm.setStyleName(it.eng.utility.Styles.detailSection);
//		incaricatoPrelievoPerRichEsternoForm.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">Per il richiedente esterno</span>");
//
//		incaricatoPrelievoPerRichEsternoItem = new SelezionaUtenteItems(incaricatoPrelievoPerRichEsternoForm, "idIncaricatoPrelievoPerRichEsterno",
//				"usernameIncaricatoPrelievoPerRichEsterno", "codRapidoIncaricatoPrelievoPerRichEsterno", "cognomeIncaricatoPrelievoPerRichEsterno",
//				"nomeIncaricatoPrelievoPerRichEsterno", "codiceFiscaleIncaricatoPrelievoPerRichEsterno", "emailIncaricatoPrelievoPerRichEsterno",
//				"telefonoIncaricatoPrelievoPerRichEsterno") {
//
//			@Override
//			protected boolean codRapidoItemShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//
//			@Override
//			protected boolean cercaInRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//
//			@Override
//			protected boolean cercaInOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//
//			@Override
//			protected boolean lookupRubricaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//
//			@Override
//			protected boolean lookupOrganigrammaButtonShowIfCondition(FormItem item, Object value, DynamicForm form) {
//				return false;
//			}
//		};
//
//		lista = new ArrayList<FormItem>();
//		lista.add(spacerMargin);
//		lista.addAll(incaricatoPrelievoPerRichEsternoItem);
//		lista.add(spacerMargin);
//
//		incaricatoPrelievoPerRichEsternoForm.setItems(lista.toArray(new FormItem[lista.size()]));
//
//		datiAppuntamentoForm = new DynamicForm();
//		datiAppuntamentoForm.setValuesManager(vm);
//		datiAppuntamentoForm.setWidth("100%");
//		datiAppuntamentoForm.setHeight("5");
//		datiAppuntamentoForm.setPadding(5);
//		datiAppuntamentoForm.setNumCols(20);
//		datiAppuntamentoForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "*");
//		datiAppuntamentoForm.setWrapItemTitles(false);
//		datiAppuntamentoForm.setTabID(_TAB_HEADER_ID);		
//
//		dataAppuntamentoItem = new TextItem("dataAppuntamento", "Appuntamento il");
//		dataAppuntamentoItem.setStartRow(true);
//		dataAppuntamentoItem.setWidth(70);
//		
//		orarioAppuntamentoItem = new TextItem("orarioAppuntamento", "alle");
//		orarioAppuntamentoItem.setWidth(50);
//		
//		provenienzaAppuntamentoItem = new TextItem("provenienzaAppuntamento", "Fissato tramite");
//		provenienzaAppuntamentoItem.setWidth(120);
//		provenienzaAppuntamentoItem.setColSpan(2);
//
//		SpacerItem spacer = new SpacerItem();
//		spacer.setWidth(25);
//
//		dataPrelievoItem = new TextItem("dataPrelievo", "Data prelievo effettivo");
//		dataPrelievoItem.setWidth(70);
//		
//		dataRestituzionePrelievoItem = new TextItem("dataRestituzionePrelievo", "Restituzione il");
//		dataRestituzionePrelievoItem.setWidth(70);
//		
//		restituzionePrelievoAttestataDaItem = new TextItem("restituzionePrelievoAttestataDa", "attestata da");
//		restituzionePrelievoAttestataDaItem.setWidth(120);
//
//		datiAppuntamentoForm.setItems(dataAppuntamentoItem, orarioAppuntamentoItem, provenienzaAppuntamentoItem, spacer, dataPrelievoItem,
//				dataRestituzionePrelievoItem, restituzionePrelievoAttestataDaItem);
//
//		incaricatoDelPrelievoSection = new NuovaRichiestaAccessoAttiDetailSection("Dati prelievo", true, false, false, incaricatoPrelievoUffRichiedenteForm,
//				incaricatoPrelievoPerRichEsternoForm, datiAppuntamentoForm);
//	}
	
	protected String getIdUd() {
		return vm.getValueAsString("idUd");
	}
	protected String getIdProcessTask() {
		return null;
	}

	protected String getIdProcessTypeTask() {
		return null;
	}
	
	protected String getIdTaskCorrente() {
		return null;
	}
	
	/**
	 * Metodo che restituisce la mappa dei modelli relativi agli atti associati
	 * al task, da passare alla sezione dei documenti istruttoria
	 * 
	 */
	public HashSet<String> getTipiModelliAttiInDocumentiIstruttoria() {
		return null;
	}
	
	/**
	 * Metodo che costruisce il record per la chiamata al servizio che genera
	 * l'allegato da modello iniettando i valori presenti in maschera
	 * 
	 */
	public Record getRecordCaricaModelloInDocumentiIstruttoria(String idModello, String tipoModello) {
		
		final Record modelloRecord = new Record();
		modelloRecord.setAttribute("idModello", idModello);
		modelloRecord.setAttribute("tipoModello", tipoModello);	
		modelloRecord.setAttribute("idUd", getIdUd());
		if (attributiAddDocDetails != null) {
			modelloRecord.setAttribute("valori", getAttributiDinamiciDoc());
			modelloRecord.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
			modelloRecord.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
		}		
		return modelloRecord;
	}

	public boolean showDetailToolStrip() {
		return getLayout() == null;
	}
	
	public boolean showLookupArchivioInEstremiRichiestaSection() {
		//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)
		//va filtrata solo per PG, intanto la nascondo
		return false;
	}

	public boolean showIndirizzoRiferimentoSection() {

		if (flgTipoDocConVieItem != null) {
			return flgTipoDocConVieItem.getValue() != null && (Boolean) flgTipoDocConVieItem.getValue();
		}
		return false;
	}
	
	public boolean isNewMode() {
		return this.mode == null || this.mode.equals("new");
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
		
//		numeroPraticaSuSistUfficioRichiedenteItem.setCanEdit(canEdit && isEstremiProtococolloPraticaToEnable());
//		annoPraticaSuSistUfficioRichiedenteItem.setCanEdit(canEdit && isEstremiProtococolloPraticaToEnable());

		richiedentiInterniItem.setCanEdit(canEdit && isRichiedenteInternoToEnable());

		// indirizzoRiferimentoItem è sempre read only
		indirizzoRiferimentoItem.setCanEdit(false);

		// Nascondo il pulsante di aggiunta se non ho le abilitazioni per modificare la lista degli atti
		// La gestione del bottone di rimozione è invece fatta nel relativo canvas
		if (!isElencoAttiRichiestiEditable()) {
			attiRichiestiItem.setMaxLength(attiRichiestiItem.getTotalMembers());
			flgAltriAttiDaRicercareVisuraSueItem.setCanEdit(false);
		}

		// richiedentiEsterniItem è sempre readOnly
		richiedentiEsterniItem.setCanEdit(false);

		// richiedentiDelegatiDaItem è sempre read only
		richiedentiDelegatiDaItem.setCanEdit(false);

//		esibentiItem.setCanEdit(canEdit && isEsibenteDellaRichiestaToEnable());

//		motivoRichiestaItem.setCanEdit(canEdit && isMotivoEDettagliDellaRichiestaToEnable());
//		dettagliRichiestaItem.setCanEdit(canEdit && isMotivoEDettagliDellaRichiestaToEnable());

//		incaricatoPrelPerUffRichiedenteItems.setCanEdit(canEdit && isDatiPrelievoUffRichiedenteESoggEsternoToEnable());
//		incaricatoPrelievoPerRichEsternoItem.setCanEdit(canEdit && isDatiPrelievoUffRichiedenteESoggEsternoToEnable());

//		dataAppuntamentoItem.setCanEdit(false);
//		orarioAppuntamentoItem.setCanEdit(false);
//		provenienzaAppuntamentoItem.setCanEdit(false);
//		dataPrelievoItem.setCanEdit(false);
//		dataRestituzionePrelievoItem.setCanEdit(false);
//		restituzionePrelievoAttestataDaItem.setCanEdit(false);
	}

	@Override
	public void editNewRecord() {

		super.editNewRecord();

		initialValuesRichiesta = new HashMap();
		
		estremiRichiestaSection.setTitle("Estremi richiesta");

		if (indirizzoRiferimentoSection != null) {
			indirizzoRiferimentoSection.setVisible(showIndirizzoRiferimentoSection());
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

		manageLoadSelectInEditNewRecord(initialValues, utenteRicercatoreItem, "idUtenteRicercatore", new String[] {"cognomeNomeRicercatore"}, null);
		
		super.editNewRecord(initialValues);
		
		initialValuesRichiesta = new HashMap();
		if(initialValues != null) {
			initialValuesRichiesta.putAll(initialValues);
		}
		
		String siglaPraticaSuSistUfficioRichiedente = (String) initialValues.get("siglaPraticaSuSistUfficioRichiedente");
//		setSiglaPraticaSuSistUfficioRichiedente(siglaPraticaSuSistUfficioRichiedente);

		estremiRichiestaSection.setTitle("Estremi richiesta");
		String nomeTipoDocumento = (String) initialValues.get("nomeTipoDocumento");
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
	}

	@Override
	public void editRecord(Record record) {

		manageLoadSelectInEditRecord(record, utenteRicercatoreItem, "idUtenteRicercatore", new String[] {"cognomeNomeRicercatore"}, null);
		
		super.editRecord(record);
		
		String siglaPraticaSuSistUfficioRichiedente = record.getAttributeAsString("siglaPraticaSuSistUfficioRichiedente");
//		setSiglaPraticaSuSistUfficioRichiedente(siglaPraticaSuSistUfficioRichiedente);

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
	}

//	protected void setSiglaPraticaSuSistUfficioRichiedente(String sigla) {
//		if (sigla != null && !"".equalsIgnoreCase(sigla)) {
//			estremiRichiestaForm.setValue("siglaPraticaSuSistUfficioRichiedente", sigla);
//			numeroPraticaSuSistUfficioRichiedenteItem.setTitle("ID nel sistema uff. richiedente " + sigla + " N°");
//		} else {
//			estremiRichiestaForm.setValue("siglaPraticaSuSistUfficioRichiedente", (String) null);
//			numeroPraticaSuSistUfficioRichiedenteItem.setTitle("ID nel sistema uff. richiedente N°");
//		}
//	}

	@Override
	public void editMode() {

		super.editMode();

		openSectionsIfHasValue();

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

		openSectionsIfHasValue();

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
	
	protected void manageAzioneSuRichAccessoAttiSingola(String codOperazione) {
		manageAzioneSuRichAccessoAttiSingola(codOperazione, null);
	}

	protected void manageAzioneSuRichAccessoAttiSingola(final String codOperazione, final ServiceCallback<Record> callback) {
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
								azioneSuRichAccessoAttiPopup.markForDestroy();
								if(callback != null) {
									callback.execute(record);
								} else {
									boolean success = manageErroreSingoloAzioneRichiestaAtti(record, closeDetailAfterOperation);
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

//	private boolean isIndirizzoDiRiferimentoToEnable() {
//		// Gli estremi di protocollo della pratica sono modificabili solamente da abilitazionmi RAA/IR o RAA/AUT
//		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
//	}

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

//	private boolean isRichiedenteEsternoToEnable() {
//		// Sezione "Richiedente esterno" va disabilitato se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
//		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
//	}

//	private boolean isRichiedenteDelegatoToEnable() {
//		// Sezione "Richiedente delegato da" va disabilitato se l’utente non ha una delle abilitazioni RAA/IR o RAA/AUT
//		return (Layout.isPrivilegioAttivo("RAA/IR") || Layout.isPrivilegioAttivo("RAA/AUT"));
//	}

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

	protected boolean manageErroreSingoloAzioneRichiestaAtti(Record object, boolean skipReloadAfterSuccess) {

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
	
	@Override
	public boolean customValidate() {
		boolean valid = super.customValidate(); 
		if (attributiAddDocDetails != null) {
			for (String key : attributiAddDocDetails.keySet()) {
				AttributiDinamiciDetail detail = attributiAddDocDetails.get(key);
				if(!detail.customValidate()) {
					valid = false;
				}
				for (DynamicForm form : detail.getForms()) {
					form.clearErrors(true);
					valid = form.validate() && valid;
					for (FormItem item : form.getFields()) {
						if (item instanceof ReplicableItem) {
							ReplicableItem lReplicableItem = (ReplicableItem) item;
							boolean itemValid = lReplicableItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lReplicableItem != null && lReplicableItem.getForm() != null && lReplicableItem.getForm().getDetailSection() != null) {
									lReplicableItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof IDocumentItem) {
							IDocumentItem lIDocumentItem = (IDocumentItem) item;
							boolean itemValid = lIDocumentItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lIDocumentItem != null && lIDocumentItem.getForm() != null && lIDocumentItem.getForm().getDetailSection() != null) {
									lIDocumentItem.getForm().getDetailSection().open();
								}
							}
						} else if (item instanceof CKEditorItem) {
							CKEditorItem lCKEditorItem = (CKEditorItem) item;
							boolean itemValid = lCKEditorItem.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(lCKEditorItem != null && lCKEditorItem.getForm() != null && lCKEditorItem.getForm().getDetailSection() != null) {
									lCKEditorItem.getForm().getDetailSection().open();
								}
							}
						} else {
							boolean itemValid = item.validate();
							valid = itemValid && valid;
							if(!itemValid) {
								if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
									item.getForm().getDetailSection().open();
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	public void salvaAttributiDinamiciDoc(boolean flgIgnoreObblig, String rowidDoc, String activityNameWF, String esitoActivityWF, final ServiceCallback<Record> callback) {
		
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			Record lRecordDoc = new Record();
			lRecordDoc.setAttribute("rowId", rowidDoc);
			lRecordDoc.setAttribute("valori", getAttributiDinamiciDoc());
			lRecordDoc.setAttribute("tipiValori", getTipiAttributiDinamiciDoc());
//			lRecordDoc.setAttribute("colonneListe", getColonneListeAttributiDinamiciDoc());
			lRecordDoc.setAttribute("activityNameWF", activityNameWF);
			lRecordDoc.setAttribute("esitoActivityWF", esitoActivityWF);
			GWTRestService<Record, Record> lGWTRestService = new GWTRestService<Record, Record>("AttributiDinamiciDocDatasource");
			if (flgIgnoreObblig) {
				lGWTRestService.addParam("flgIgnoreObblig", "1");
			}
			lGWTRestService.call(lRecordDoc, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (callback != null) {
						callback.execute(object);
					}
				}
			});
		} else {
			if (callback != null) {
				callback.execute(new Record());
			}
		}
	}
	
	public void caricaAttributiDinamiciDoc(String nomeFlussoWF, String processNameWF, String activityName, String idTipoDoc, String rowidDoc) {
		
		attributiAddDocLayouts = new HashMap<String, VLayout>();
		attributiAddDocDetails = new HashMap<String, AttributiDinamiciDetail>();
		if (attributiAddDocTabs != null && attributiAddDocTabs.size() > 0) {
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
			lGwtRestService.addParam("nomeFlussoWF", nomeFlussoWF);
			lGwtRestService.addParam("processNameWF", processNameWF);
			lGwtRestService.addParam("activityNameWF", activityName);
			Record lAttributiDinamiciRecord = new Record();
			lAttributiDinamiciRecord.setAttribute("nomeTabella", "DMT_DOCUMENTS");
			lAttributiDinamiciRecord.setAttribute("rowId", rowidDoc);
			lAttributiDinamiciRecord.setAttribute("tipoEntita", idTipoDoc);
			lGwtRestService.call(lAttributiDinamiciRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					RecordList attributiAdd = object.getAttributeAsRecordList("attributiAdd");
					if (attributiAdd != null && !attributiAdd.isEmpty()) {
						for (final String key : attributiAddDocTabs.keySet()) {
							RecordList attributiAddCategoria = new RecordList();
							for (int i = 0; i < attributiAdd.getLength(); i++) {
								Record attr = attributiAdd.get(i);
								if (attr.getAttribute("categoria") != null && attr.getAttribute("categoria").equalsIgnoreCase(key)) {
									attributiAddCategoria.add(attr);
								}
							}
							if (!attributiAddCategoria.isEmpty()) {
								AttributiDinamiciDetail detail = new AttributiDinamiciDetail("attributiDinamici", attributiAddCategoria, object
										.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
										.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), null, tabSet, key);
								detail.setCanEdit(new Boolean(isEseguibile()));
								String messaggioTab = getMessaggioTab(key);
								if (messaggioTab != null && !"".equals(messaggioTab)) {
									Label labelMessaggioTab = new Label(messaggioTab);
									labelMessaggioTab.setAlign(Alignment.LEFT);
									labelMessaggioTab.setWidth100();
									labelMessaggioTab.setHeight(2);
									labelMessaggioTab.setPadding(5);
									detail.addMember(labelMessaggioTab, 0);
								}
								attributiAddDocDetails.put(key, detail);
								VLayout layout = new VLayout();
								layout.setHeight100();
								layout.setWidth100();
								layout.setMembers(detail);
								attributiAddDocLayouts.put(key, layout);
								VLayout layoutTab = new VLayout();
								layoutTab.addMember(layout);
								Tab tab = new Tab("<b>" + attributiAddDocTabs.get(key) + "</b>");
								tab.setAttribute("tabID", key);
								tab.setPrompt(attributiAddDocTabs.get(key));
								tab.setPane(layoutTab);
								tabSet.addTab(tab);
							}
						}						
					}
					afterCaricaAttributiDinamiciDoc();
				}
			});
		} else {
			afterCaricaAttributiDinamiciDoc();
		}
	}
	
	/**
	 * Metodo che viene richiamato alla fine del caricamento degli attributi
	 * dinamici del documento
	 * 
	 */
	protected void afterCaricaAttributiDinamiciDoc() {
		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
			attributiDinamiciDocDaCopiare = null;
		}
	}


	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento dopo l'azione del bottone "Nuova protocollazione come copia"
	 * 
	 */
	protected void setAttributiDinamiciDocDaCopiare(Map<String, Object> attributiDinamiciDocDaCopiare) {
		this.attributiDinamiciDocDaCopiare = attributiDinamiciDocDaCopiare;
		if (attributiDinamiciDocDaCopiare != null) {
			setAttributiDinamiciDoc(attributiDinamiciDocDaCopiare);
		}
	}

	/**
	 * Metodo per settare i valori degli attributi dinamici associati al
	 * documento
	 * 
	 */
	protected void setAttributiDinamiciDoc(Map<String, Object> valori) {
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					attributiAddDocDetails.get(key).editNewRecord(valori);
				}
			}
		}
	}

	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici 
	 * associati al documento, nella modalità per il caricamento dei dati a maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NOMI delle colonne 
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDocForLoad() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(detailRecord.toMap());
				}
			}
		}
		return attributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera dei valori degli attributi dinamici
	 * associati al documento, nella modalità per il salvataggio dei dati da maschera:
	 * gli attributi LISTA hanno i valori interni mappati con i NUMERI delle colonne
	 * 
	 */
	public Map<String, Object> getAttributiDinamiciDoc() {
		Map<String, Object> attributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (attributiDinamiciDoc == null) {
						attributiDinamiciDoc = new HashMap<String, Object>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal vm, 
					// i valori degli attributi lista non li prende correttamente
					// final Record detailRecord = new Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					attributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaValori(detailRecord));
				}
			}
		}
		return attributiDinamiciDoc;
	}

	/**
	 * Metodo per il recupero da maschera dei tipi degli attributi dinamici
	 * associati al documento
	 * 
	 */
	public Map<String, String> getTipiAttributiDinamiciDoc() {
		Map<String, String> tipiAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (tipiAttributiDinamiciDoc == null) {
						tipiAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					tipiAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaTipiValori(detailRecord));
				}
			}
		}
		return tipiAttributiDinamiciDoc;
	}
	
	/**
	 * Metodo per il recupero da maschera delle mappe delle colonne degli attributi dinamici
	 * di tipo LISTA associati al documento
	 * 
	 */
	public Map<String, String> getColonneListeAttributiDinamiciDoc() {
		Map<String, String> mappaColonneListaAttributiDinamiciDoc = null;
		if (attributiAddDocTabs != null) {
			for (String key : attributiAddDocTabs.keySet()) {
				if (attributiAddDocDetails != null && attributiAddDocDetails.get(key) != null) {
					if (mappaColonneListaAttributiDinamiciDoc == null) {
						mappaColonneListaAttributiDinamiciDoc = new HashMap<String, String>();
					}
					// ATTENZIONE: se provo a prendere i valori direttamente dal
					// vm, i valori degli attributi lista non li prende
					// correttamente
					// final Record detailRecord = new
					// Record(attributiAddDocDetails.get(key).getValuesManager().getValues());
					final Record detailRecord = attributiAddDocDetails.get(key).getRecordToSave();
					mappaColonneListaAttributiDinamiciDoc.putAll(attributiAddDocDetails.get(key).getMappaColonneListe(detailRecord));
				}
			}
		}
		return mappaColonneListaAttributiDinamiciDoc;
	}
	
	// Questo metodo è sovrascritto in TaskNuovaRichiestaAcessoAtti
	public void caricaModelloInDocumentiIstruttoria(String idModello, String tipoModello, String flgConvertiInPdf, final ServiceCallback<Record> callback) {
		final GWTRestDataSource lGeneraDaModelloWithDatiDocDataSource = new GWTRestDataSource("GeneraDaModelloWithDatiDocDataSource");
		lGeneraDaModelloWithDatiDocDataSource.addParam("flgConvertiInPdf", flgConvertiInPdf);
		lGeneraDaModelloWithDatiDocDataSource.executecustom("caricaModello", listaDocumentiIstruttoriaItem.getRecordCaricaModelloAllegato(idModello, tipoModello), new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					if (callback != null) {
						callback.execute(response.getData()[0]);
					}
				}
			}
		});
	}
	
	// Questo metodo è sovrascritto in TaskNuovaRichiestaAcessoAtti
	public boolean isModelloModificabileInAllegati(String idModello) {
		return true;
	}
	
	/**
	 * Metodo che viene sovrascritto in TaskNuovaRichiestaAccessoAttiDetail per verificare se 
	 * #TaskArchvio è true
	 */
	public boolean isTaskArchivio() {
		return false;
	}
	
	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {
		if (record.getAttribute("flgUdFolder").equals("U")) {
			GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
			lGwtRestDataSourceProtocollo.addParam("flgSoloAbilAzioni", "1");
			Record lRecordToLoad = new Record();
			lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
			lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record detailRecord = response.getData()[0];						
						
						final Menu contextMenu = new Menu();
						final Menu altreOpMenu = createUdAltreOpMenu(record, detailRecord);
						for (int i = 0; i < altreOpMenu.getItems().length; i++) {
							contextMenu.addItem(altreOpMenu.getItems()[i], i);
						}
		
						contextMenu.showContextMenu();
						
					}
				}
			});
		} else if (record.getAttribute("flgUdFolder").equals("F")) {
			if ((record.getAttribute("nroFascicolo") != null && !"".equals(record.getAttribute("nroFascicolo")))
					|| (record.getAttribute("percorsoNome") != null && !"".equals(record.getAttribute("percorsoNome")))) {
				// se è un fascicolo o un sotto-fascicolo
				GWTRestDataSource lGwtRestDataSourceArchivio = new GWTRestDataSource("ArchivioDatasource", "idUdFolder", FieldType.TEXT);
				lGwtRestDataSourceArchivio.addParam("flgSoloAbilAzioni", "1");
				lGwtRestDataSourceArchivio.performCustomOperation("get", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							
							final Record detailRecord = response.getData()[0];
							Record recordDestPref = new Record();						
							RecordList listaAzioniRapide = new RecordList();
							Record recordAzioneRapidaAssegna = new Record();
							recordAzioneRapidaAssegna.setAttribute("azioneRapida", AzioniRapide.ASSEGNA_FOLDER.getValue()); 
							listaAzioniRapide.add(recordAzioneRapidaAssegna);
							Record recordAzioneRapidaInvioCC = new Record();
							recordAzioneRapidaInvioCC.setAttribute("azioneRapida", AzioniRapide.INVIO_CC_FOLDER.getValue()); 
							listaAzioniRapide.add(recordAzioneRapidaInvioCC);
							recordDestPref.setAttribute("listaAzioniRapide", listaAzioniRapide);										
							GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LoadComboDestinatariPreferiti");
							lGwtRestDataSource.performCustomOperation("getDestinatariPreferitiUtente", recordDestPref,
									new DSCallback() {

										@Override
										public void execute(DSResponse responseDestPref, Object rawDataDestPref,
												DSRequest requestDestPref) {
											if (responseDestPref.getStatus() == DSResponse.STATUS_SUCCESS) {												
												final Menu contextMenu = new Menu();
												// TODEL controllare
												// final Menu altreOpMenu = createFolderAltreOpMenu(record, detailRecord, destinatariPreferiti);
												final Menu altreOpMenu = new Menu();
												for (int i = 0; i < altreOpMenu.getItems().length; i++) {
													contextMenu.addItem(altreOpMenu.getItems()[i], i);
												}
												if (navigationContextMenu != null) {
													for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
														contextMenu.addItem(navigationContextMenu.getItems()[i], i);
													}
													MenuItem separatorMenuItem = new MenuItem();
													separatorMenuItem.setIsSeparator(true);
													contextMenu.addItem(separatorMenuItem, navigationContextMenu.getItems().length);
												}
												contextMenu.showContextMenu();
											}
										}
									}, new DSRequest());
						}
					}
				}, new DSRequest());
			} else {
				// se è una classifica
				if (navigationContextMenu != null) {
					final Menu contextMenu = new Menu();
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {

						@Override
						public void execute() {
							for (int i = 0; i < navigationContextMenu.getItems().length; i++) {
								contextMenu.addItem(navigationContextMenu.getItems()[i], i);
							}
							if (contextMenu.getItems().length > 0) {
								contextMenu.showContextMenu();
							}
						}
					});
				}
			}
		}
	}
	
	public Menu createUdAltreOpMenu(final ListGridRecord listRecord, final Record detailRecord) {
		
		Record filePrimarioOmissis=detailRecord.getAttributeAsRecord("filePrimarioOmissis");
		
		Menu fileAssociatiSubMenu = new Menu();
		fileAssociatiSubMenu.setEmptyMessage("Nessun file su cui<br/>hai diritti di accesso");
		
		//File primario senza omissis
		if ((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") == null) || "".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioIntegraleMenuItem, true);
			fileAssociatiSubMenu.addItem(filePrimarioIntegraleMenuItem);
			
		}
		
		//File primario con versione omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") != null
				&& !"".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			//File primario integrale
			MenuItem filePrimarioIntegraleMenuItem = new MenuItem("File primario (vers. integrale) - " + detailRecord.getAttributeAsString("nomeFilePrimario"));
			buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioIntegraleMenuItem, true);
			fileAssociatiSubMenu.addItem(filePrimarioIntegraleMenuItem);
			
			//File primario omissis
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " +filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioOmissisMenuItem, false);
			fileAssociatiSubMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		//File primario solo omissis
		else if((detailRecord.getAttributeAsString("uriFilePrimario") == null
				|| "".equals(detailRecord.getAttributeAsString("uriFilePrimario"))) && 
				((filePrimarioOmissis.getAttributeAsString("uriFile") != null) && !"".equals(filePrimarioOmissis.getAttributeAsString("uriFile")))) {
			
			MenuItem filePrimarioOmissisMenuItem = new MenuItem("File primario (vers. con omissis) - " + filePrimarioOmissis.getAttributeAsString("nomeFile"));
			buildFilePrimarioMenuItem(listRecord,detailRecord,filePrimarioOmissisMenuItem, false);
			fileAssociatiSubMenu.addItem(filePrimarioOmissisMenuItem);
		}
		
		
		//Aggiungo al menu gli allegati
		RecordList listaAllegati = detailRecord.getAttributeAsRecordList("listaAllegati");
		
		if (listaAllegati != null) {
			for (int n = 0; n < listaAllegati.getLength(); n++) {
				final int nroAllegato = n;
				final Record allegatoRecord = listaAllegati.get(n);
				
				//Allegato senza omissis
				if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato, true);
					fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
				}
				
				//Entrambi versioni di allegati
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					//Allegato integrale
					MenuItem fileAllegatoIntegraleMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" (vers. integrale) - " + allegatoRecord.getAttributeAsString("nomeFileAllegato"));
					buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoIntegraleMenuItem, nroAllegato,true);
					fileAssociatiSubMenu.addItem(fileAllegatoIntegraleMenuItem);
					
					//Alegato omissis
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
				}
				
				//Allegato solo omissis
				else if((allegatoRecord.getAttributeAsString("uriFileAllegato")==null || "".equals(allegatoRecord.getAttributeAsString("uriFileAllegato"))) && (allegatoRecord.getAttributeAsString("uriFileOmissis")!=null && !"".equals(allegatoRecord.getAttributeAsString("uriFileOmissis")))) {
					
					MenuItem fileAllegatoOmissisMenuItem = new MenuItem("Allegato N°"+allegatoRecord.getAttributeAsString("numeroProgrAllegato") +" (vers. con omissis) - " + allegatoRecord.getAttributeAsString("nomeFileOmissis"));
					buildAllegatiMenuItem(listRecord,detailRecord,allegatoRecord, fileAllegatoOmissisMenuItem, nroAllegato,false);
					fileAssociatiSubMenu.addItem(fileAllegatoOmissisMenuItem);
				}
			}
		}
			
		return fileAssociatiSubMenu;
	}
	
	private void buildFilePrimarioMenuItem(final ListGridRecord listRecord, final Record detailRecord, MenuItem filePrimarioMenuItem, final boolean fileIntegrale) {
		Menu operazioniFilePrimarioSubmenu = new Menu();
		InfoFileRecord lInfoFileRecord;

		//file primario integrale
		if(fileIntegrale){
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
		}
		//file primario omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(detailRecord.getAttributeAsRecord("filePrimarioOmissis").getAttributeAsObject("infoFile"));
		}
		
		MenuItem visualizzaFilePrimarioMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				if(fileIntegrale) {
					String idUd = detailRecord.getAttributeAsString("idUd");
					String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
					String display = detailRecord.getAttributeAsString("nomeFilePrimario");
					String uri = detailRecord.getAttributeAsString("uriFilePrimario");
					String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
					Object infoFile = detailRecord.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
				else {
					String idUd = detailRecord.getAttributeAsString("idUd");
					final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
					String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
					String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
					String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
					String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
					Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
					visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
				}
			}
		});
		visualizzaFilePrimarioMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFilePrimarioSubmenu.addItem(visualizzaFilePrimarioMenuItem);
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFilePrimarioMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(),
					"file/previewEdit.png");
			visualizzaEditFilePrimarioMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							if(fileIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
								String display = detailRecord.getAttributeAsString("nomeFilePrimario");
								String uri = detailRecord.getAttributeAsString("uriFilePrimario");
								String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
								Object infoFile = detailRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
								String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
								String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
								String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
								String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
								Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFilePrimarioMenuItem
					.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFilePrimarioSubmenu.addItem(visualizzaEditFilePrimarioMenuItem);
		}
		MenuItem scaricaFilePrimarioMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFilePrimarioSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						Object infoFile = detailRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						Object infoFile = filePrimarioOmissis.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFilePrimarioSubMenu.setItems(firmato, sbustato);
			scaricaFilePrimarioMenuItem.setSubmenu(scaricaFilePrimarioSubMenu);
		} else {
			scaricaFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if(fileIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						String display = detailRecord.getAttributeAsString("nomeFilePrimario");
						String uri = detailRecord.getAttributeAsString("uriFilePrimario");
						String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					else {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
						String idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						String display = filePrimarioOmissis.getAttributeAsString("nomeFile");
						String uri = filePrimarioOmissis.getAttributeAsString("uriFile");
						String remoteUri = filePrimarioOmissis.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
				}
			});
		}
		operazioniFilePrimarioSubmenu.addItem(scaricaFilePrimarioMenuItem);

//		if() {     TODO: timbro
			buildTimbraButtons(listRecord, detailRecord, lInfoFileRecord, operazioniFilePrimarioSubmenu);	
//		}
		
		if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
			String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
			MenuItem timbroConformitaCustomPrimarioMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
			timbroConformitaCustomPrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			timbroConformitaCustomPrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "COPIA_CONFORME_CUSTOM");
							timbraFilePrimario(detailRecord);
						}
					});

			operazioniFilePrimarioSubmenu.addItem(timbroConformitaCustomPrimarioMenuItem);
		}

		// Attestato conformità all’originale
		MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(), "file/attestato.png");
		attestatoConformitaOriginaleMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final InfoFileRecord file;
						final String uri;
						final String idUd = detailRecord.getAttributeAsString("idUd");
						final String idDoc;

						if (fileIntegrale) {
							file = InfoFileRecord
									.buildInfoFileRecord(detailRecord.getAttributeAsObject("infoFile"));
							uri = detailRecord.getAttributeAsString("uriFilePrimario");
							idDoc = detailRecord.getAttributeAsString("idDocPrimario");
						} else {
							final Record filePrimarioOmissis = detailRecord.getAttributeAsRecord("filePrimarioOmissis");
							file = InfoFileRecord
									.buildInfoFileRecord(filePrimarioOmissis.getAttributeAsObject("infoFile"));
							uri = filePrimarioOmissis.getAttributeAsString("uriFile");
							idDoc = filePrimarioOmissis.getAttributeAsString("idDoc");
						}
						SC.ask("Vuoi firmare digitalmente l'attestato?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									creaAttestato(idUd, idDoc, listRecord, file, uri, true);
								} else {
									creaAttestato(idUd, idDoc, listRecord, file, uri, false);
								}
							}
						});

					}
				});
		attestatoConformitaOriginaleMenuItem
				.setEnabled(lInfoFileRecord != null);
		operazioniFilePrimarioSubmenu.addItem(attestatoConformitaOriginaleMenuItem);

		filePrimarioMenuItem.setSubmenu(operazioniFilePrimarioSubmenu);
	}
		
	
	
	private void buildAllegatiMenuItem(final ListGridRecord listRecord, final Record detailRecord, final Record allegatoRecord, MenuItem fileAllegatoMenuItem,final int nroAllegato, final boolean allegatoIntegrale) {
		Menu operazioniFileAllegatoSubmenu = new Menu();
		InfoFileRecord lInfoFileRecord;
		
		//se è un allegato integrale
		if(allegatoIntegrale) {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
		}
		//versione con omissis
		else {
			lInfoFileRecord = InfoFileRecord
					.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
		}
		
		MenuItem visualizzaFileAllegatoMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_previewButton_prompt(), "file/preview.png");
		visualizzaFileAllegatoMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						//se è un allegato integrale
						if(allegatoIntegrale) {
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
							String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
							String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
						//versione con omissis
						else{
							String idUd = detailRecord.getAttributeAsString("idUd");
							final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
							String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
							String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
							String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
							Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
							visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
						}
					}
				});
		visualizzaFileAllegatoMenuItem.setEnabled(PreviewWindow.canBePreviewed(lInfoFileRecord));
		operazioniFileAllegatoSubmenu.addItem(visualizzaFileAllegatoMenuItem);
		if (!AurigaLayout.getParametroDBAsBoolean("HIDE_JPEDAL_APPLET")) {
			MenuItem visualizzaEditFileAllegatoMenuItem = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_previewEditButton_prompt(), "file/previewEdit.png");
			visualizzaEditFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							// se è un allegato integrale
							if (allegatoIntegrale) {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
							// versione con omissis
							else {
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati")
										.get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
								visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
							}
						}
					});
			visualizzaEditFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			operazioniFileAllegatoSubmenu.addItem(visualizzaEditFileAllegatoMenuItem);
		}
		MenuItem scaricaFileAllegatoMenuItem = new MenuItem("Scarica", "file/download_manager.png");
		
		// Se è firmato P7M
		if (lInfoFileRecord != null && lInfoFileRecord.isFirmato()
				&& lInfoFileRecord.getTipoFirma().startsWith("CAdES")) {
			Menu scaricaFileAllegatoSubMenu = new Menu();
			MenuItem firmato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
			firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						scaricaFile(idUd, idDoc, display, uri, remoteUri);
					}
					
				}
			});
			MenuItem sbustato = new MenuItem(
					I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
			sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					//se è un allegato integrale
					if(allegatoIntegrale) {
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
						String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
						String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
					//versione con omissis
					else{
						String idUd = detailRecord.getAttributeAsString("idUd");
						final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
						String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
						String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
						String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
						String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
						Object infoFile = allegatoRecord.getAttributeAsObject("infoFileOmissis");
						scaricaFileSbustato(idUd, idDoc, display, uri, remoteUri, infoFile);
					}
				}
			});
			scaricaFileAllegatoSubMenu.setItems(firmato, sbustato);
			scaricaFileAllegatoMenuItem.setSubmenu(scaricaFileAllegatoSubMenu);
		} else {
			scaricaFileAllegatoMenuItem
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							if(allegatoIntegrale) {
								//se è un allegato integrale
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
								String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
								String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
							//versione con omissis
							else{
								String idUd = detailRecord.getAttributeAsString("idUd");
								final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
								String idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
								String display = allegatoRecord.getAttributeAsString("nomeFileOmissis");
								String uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
								String remoteUri = allegatoRecord.getAttributeAsString("remoteUriOmissis");
								scaricaFile(idUd, idDoc, display, uri, remoteUri);
							}
						}
					});
		}

		operazioniFileAllegatoSubmenu.addItem(scaricaFileAllegatoMenuItem);

//		if() {     TODO: timbro
			buildTimbraAllegato(listRecord, detailRecord, nroAllegato, operazioniFileAllegatoSubmenu,
					lInfoFileRecord);	
//		}
		
			
		if (lInfoFileRecord != null && Layout.isPrivilegioAttivo("SCC")) {
			String labelConformitaCustom = AurigaLayout.getParametroDB("LABEL_COPIA_CONFORME_CUSTOM");
			MenuItem timbroConformitaCustomAllegatoMenuItem = new MenuItem(labelConformitaCustom, "file/copiaConformeCustom.png");
			timbroConformitaCustomAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			timbroConformitaCustomAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "COPIA_CONFORME_CUSTOM");
							timbraFileAllegato(detailRecord, nroAllegato);
						}
					});

			operazioniFileAllegatoSubmenu.addItem(timbroConformitaCustomAllegatoMenuItem);

		}	
	

		// Attestato conformità all’originale
		MenuItem attestatoConformitaOriginaleMenuItem = new MenuItem(
				I18NUtil.getMessages().protocollazione_detail_attestatoConformitaMenuItem(),
				"file/attestato.png");
		attestatoConformitaOriginaleMenuItem
				.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final InfoFileRecord fileAllegato;
						final String uri;
						final String idUd = detailRecord.getAttributeAsString("idUd");
						final String idDoc;
						
						//se è un allegato integrale
						if(allegatoIntegrale) {
						
							fileAllegato = InfoFileRecord
									.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFile"));
							uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
							idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");

						}
						//versione con omissis
						else {
							fileAllegato = InfoFileRecord
									.buildInfoFileRecord(allegatoRecord.getAttributeAsObject("infoFileOmissis"));
							uri = allegatoRecord.getAttributeAsString("uriFileOmissis");
							idDoc = allegatoRecord.getAttributeAsString("idDocOmissis");
						}
						SC.ask("Vuoi firmare digitalmente l'attestato?", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									creaAttestato(idUd, idDoc, listRecord, fileAllegato, uri, true);
								} else {
									creaAttestato(idUd, idDoc, listRecord, fileAllegato, uri, false);
								}
							}
						});
					}
				});
		attestatoConformitaOriginaleMenuItem.setEnabled(lInfoFileRecord != null);
		operazioniFileAllegatoSubmenu.addItem(attestatoConformitaOriginaleMenuItem);
		fileAllegatoMenuItem.setSubmenu(operazioniFileAllegatoSubmenu);

	}
	
	protected void creaAttestato(final String idUd, final String idDoc, ListGridRecord listRecord, final InfoFileRecord infoFileAllegato, final String uriFileAllegato, final boolean attestatoFirmato) {

		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", listRecord.getAttribute("idUd"));
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocolloDataSource");
		lGwtRestDataSourceProtocollo.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record record = response.getData()[0];
				record.setAttribute("infoFileAttach", infoFileAllegato);
				record.setAttribute("uriAttach", uriFileAllegato);
				record.setAttribute("idUd", idUd);
				record.setAttribute("idDoc", idDoc);
				record.setAttribute("attestatoFirmatoDigitalmente", attestatoFirmato);

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ArchivioDatasource");
				lGwtRestDataSource.extraparam.put("attestatoFirmato", Boolean.toString(attestatoFirmato));
				lGwtRestDataSource.extraparam.put("urlContext", GWT.getHostPageBaseURL());
				lGwtRestDataSource.executecustom("stampaAttestato", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Record data = response.getData()[0];
						final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(data.getAttributeAsObject("infoFileOut"));
						final String filename = infoFile.getCorrectFileName();
						final String uri = data.getAttribute("tempFileOut");
						if (!attestatoFirmato) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", filename);
							lRecord.setAttribute("uri", uri);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", false);

							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						} else {
							String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
							String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");				
							FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, uri, filename, infoFile, new FirmaCallback() {

								@Override
								public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord info) {
									Record lRecord = new Record();
									lRecord.setAttribute("displayFilename", nomeFileFirmato);
									lRecord.setAttribute("uri", uriFileFirmato);
									lRecord.setAttribute("sbustato", "false");
									lRecord.setAttribute("remoteUri", false);
									DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
								}
							});
						}
					}
				});
			}
		});
	}
	
	public void visualizzaFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaEditFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");
		visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		visualizzaFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaEditFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String idDoc = allegatoRecord.getAttributeAsString("idDocAllegato");
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");
		visualizzaEditFile(detailRecord, idUd, idDoc, display, uri, remoteUri, infoFile);
	}

	public void visualizzaFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		PreviewControl.switchPreview(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
		// PreviewWindow lPreviewWindow = new PreviewWindow(uri, Boolean.valueOf(remoteUri), info, "FileToExtractBean", display);
	}

	public void visualizzaEditFile(final Record detailRecord, String idUd, String idDoc, final String display, final String uri, final String remoteUri,
			Object infoFile) {
		addToRecent(idUd, idDoc);
		InfoFileRecord info = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		
		FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), info.getMimetype(), idUd
				,rotazioneTimbroPref,posizioneTimbroPref);
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("LoadTimbraDefault");
		lGwtRestService.call(lFileDaTimbrareBean, new ServiceCallback<Record>() {

			@Override
			public void execute(Record lOpzioniTimbro) {
				boolean timbroEnabled = detailRecord != null && detailRecord.getAttribute("flgTipoProv") != null
						&& !"".equals(detailRecord.getAttribute("flgTipoProv")) && detailRecord.getAttribute("idUd") != null
						&& !"".equals(detailRecord.getAttribute("idUd"));
				PreviewDocWindow previewDocWindow = new PreviewDocWindow(uri, display, Boolean.valueOf(remoteUri), timbroEnabled, lOpzioniTimbro) {

					@Override
					public void uploadCallBack(String filePdf, String uriPdf, String record) {

					}
				};
				previewDocWindow.show();
			}
		});
	}

	public void scaricaFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		scaricaFile(idUd, idDoc, display, uri, remoteUri);
	}


	public void scaricaFile(String idUd, String idDoc, String display, String uri, String remoteUri) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", remoteUri);
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}


	public void scaricaFileSbustato(String idUd, String idDoc, String display, String uri, String remoteUri, Object infoFile) {
		addToRecent(idUd, idDoc);
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "true");
		lRecord.setAttribute("remoteUri", remoteUri);
		InfoFileRecord lInfoFileRecord = new InfoFileRecord(infoFile);
		lRecord.setAttribute("correctFilename", lInfoFileRecord.getCorrectFileName());
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
	}

	public void timbraFilePrimario(Record detailRecord) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		String display = detailRecord.getAttributeAsString("nomeFilePrimario");
		String uri = detailRecord.getAttributeAsString("uriFilePrimario");
		String remoteUri = detailRecord.getAttributeAsString("remoteUriFilePrimario");
		Object infoFile = detailRecord.getAttributeAsObject("infoFile");

		String idDoc = detailRecord.getAttributeAsString("idDoc");
		String tipoTimbro = detailRecord.getAttributeAsString("tipoTimbro");
		String finalita = detailRecord.getAttributeAsString("finalita") !=null ? detailRecord.getAttributeAsString("finalita") : "";

		idDoc = idDoc != null ? idDoc : "";
		tipoTimbro = tipoTimbro != null ? tipoTimbro : "";

		timbraFile(idDoc, tipoTimbro, idUd, display, uri, remoteUri, infoFile, finalita);
	}

	public void timbraFileAllegato(Record detailRecord, int nroAllegato) {
		String idUd = detailRecord.getAttributeAsString("idUd");
		final Record allegatoRecord = detailRecord.getAttributeAsRecordList("listaAllegati").get(nroAllegato);
		String display = allegatoRecord.getAttributeAsString("nomeFileAllegato");
		String uri = allegatoRecord.getAttributeAsString("uriFileAllegato");
		String remoteUri = allegatoRecord.getAttributeAsString("remoteUri");
		Object infoFile = allegatoRecord.getAttributeAsObject("infoFile");

		String idDoc = detailRecord.getAttributeAsString("idDocPrimario");
		String tipoTimbro = detailRecord.getAttributeAsString("tipoTimbro");
		String finalita = detailRecord.getAttributeAsString("finalita") !=null ? detailRecord.getAttributeAsString("finalita") : "";

		idDoc = idDoc != null ? idDoc : "";
		tipoTimbro = tipoTimbro != null ? tipoTimbro : "";

		timbraFile(idDoc, tipoTimbro, idUd, display, uri, remoteUri, infoFile, finalita);
	}

	public void timbraFile(String idDoc, String tipoTimbro, String idUd, String display, String uri, String remoteUri, Object infoFile, String finalita) {
		InfoFileRecord precInfo = new InfoFileRecord(infoFile);
		
		String rotazioneTimbroPref = AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("rotazioneTimbro") : "";
		String posizioneTimbroPref = AurigaLayout.getImpostazioneTimbro("posizioneTimbro") != null ?
				AurigaLayout.getImpostazioneTimbro("posizioneTimbro") : "";
		String tipoPaginaPref = AurigaLayout.getImpostazioneTimbro("tipoPagina") != null ?
				AurigaLayout.getImpostazioneTimbro("tipoPagina") : "";
		
		/*Controllo introdotto per la nuova modalità di timbratura per i file firmati che devono saltare la scelta della preferenza*/
		boolean skipSceltaTimbratura = AurigaLayout.getImpostazioneTimbroAsBoolean("skipSceltaOpzioniTimbroSegnatura");
		boolean flgBustaPdfTimbratura = false;
		
		if(precInfo.isFirmato() && AurigaLayout.getParametroDBAsBoolean("ATTIVA_BUSTA_PDF_FILE_FIRMATO") && !finalita.equalsIgnoreCase("CONFORMITA_ORIG_DIGITALE_STAMPA")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = true;
		}
		
		if(finalita.equalsIgnoreCase("COPIA_CONFORME_CUSTOM")) {
			skipSceltaTimbratura = true;
			flgBustaPdfTimbratura = false;
		}
		
		if(skipSceltaTimbratura){
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			record.setAttribute("nomeFile", display);
			record.setAttribute("uri", uri);
			record.setAttribute("remote", remoteUri);
			record.setAttribute("mimetype", precInfo.getMimetype());
			record.setAttribute("rotazioneTimbroPref", rotazioneTimbroPref);
			record.setAttribute("posizioneTimbroPref", posizioneTimbroPref);
			record.setAttribute("skipScelteOpzioniCopertina", "true");
			if (finalita.equals("CONFORMITA_ORIG_CARTACEO")) {
				record.setAttribute("tipoPagina", "tutte");
			}else {
				record.setAttribute("tipoPagina", tipoPaginaPref);
			}
			record.setAttribute("finalita", finalita);
				
			if(flgBustaPdfTimbratura) {
				TimbroUtil.callStoreLoadFilePerTimbroConBusta(record);
			}else {
				TimbroUtil.buildDatiSegnatura(record);
			}
		}else{
		
			FileDaTimbrareBean lFileDaTimbrareBean = new FileDaTimbrareBean(uri, display, Boolean.valueOf(remoteUri), precInfo.getMimetype(), idUd, idDoc,
					tipoTimbro,"D",rotazioneTimbroPref,posizioneTimbroPref);
			lFileDaTimbrareBean.setAttribute("finalita", finalita);
			lFileDaTimbrareBean.setAttribute("tipoPagina", tipoPaginaPref);
			lFileDaTimbrareBean.setAttribute("skipScelteOpzioniCopertina", "false");
			TimbraWindow lTimbraWindow = new TimbraWindow("timbra", true, lFileDaTimbrareBean);
			lTimbraWindow.show();
		}
	}
	
	private void buildTimbraAllegato(final ListGridRecord listRecord, final Record detailRecord, final int nroAllegato,
			Menu operazioniFileAllegatoSubmenu, InfoFileRecord lInfoFileRecord) {

		boolean flgAddSubMenuTimbra = false;

		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		Menu timbraSubMenu = new Menu();	
		
		MenuItem apponiSegnaturaRegistrazioneFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiSegnatura(), "file/timbra.gif");
		apponiSegnaturaRegistrazioneFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						timbraFileAllegato(detailRecord, nroAllegato);
					}
				});
		apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
		timbraSubMenu.addItem(apponiSegnaturaRegistrazioneFileAllegatoMenuItem);

		if (detailRecord != null && detailRecord.getAttributeAsBoolean("attivaTimbroTipologia") != null
				&& detailRecord.getAttributeAsBoolean("attivaTimbroTipologia")) {

			MenuItem timbraFileAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiTipologia(), "file/timbra.gif");
			timbraFileAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idDoc = listRecord.getAttribute("idDocPrimario");
					detailRecord.setAttribute("idDoc", idDoc);
					detailRecord.setAttribute("tipoTimbra", "T");
					timbraFileAllegato(detailRecord, nroAllegato);
				}
			});
			timbraFileAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			flgAddSubMenuTimbra=true;
			timbraSubMenu.addItem(timbraFileAllegatoMenuItem);

		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null
					&& (lInfoFileRecord.getMimetype().equals("application/pdf")
							|| lInfoFileRecord.getMimetype().startsWith("image"))) {

				MenuItem timbroConformitaOriginaleAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(),"file/timbra.gif");
				timbroConformitaOriginaleAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaOriginaleAllegatoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_CARTACEO");
								timbraFileAllegato(detailRecord, nroAllegato);
							}
						});

				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbroConformitaOriginaleAllegatoMenuItem);
			}
		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
			
			flgAddSubMenuTimbra=true;

			MenuItem timbroCopiaConformeAllegatoMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
			timbroCopiaConformeAllegatoMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			Menu sottoMenuAllegatoTimbroCopiaConformeItem = new Menu();
			
			MenuItem timbroCopiaConformeStampaAllegatoItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
			timbroCopiaConformeStampaAllegatoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_STAMPA");
							timbraFileAllegato(detailRecord, nroAllegato);
						}
					});
			
			sottoMenuAllegatoTimbroCopiaConformeItem.addItem(timbroCopiaConformeStampaAllegatoItem);
			
			MenuItem timbroCopiaConformeDigitaleAllegatoItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");			
			timbroCopiaConformeDigitaleAllegatoItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
							timbraFileAllegato(detailRecord, nroAllegato);
						}
					});
			
			sottoMenuAllegatoTimbroCopiaConformeItem.addItem(timbroCopiaConformeDigitaleAllegatoItem);

			timbroCopiaConformeAllegatoMenuItem.setSubmenu(sottoMenuAllegatoTimbroCopiaConformeItem);
			timbraSubMenu.addItem(timbroCopiaConformeAllegatoMenuItem);
		}
		
		//Se ho piu voci aggiungo il sottoMenu Timbra
		if(flgAddSubMenuTimbra) {
			timbraMenuItem.setSubmenu(timbraSubMenu);
			operazioniFileAllegatoSubmenu.addItem(timbraMenuItem);
			//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
		}else {
			apponiSegnaturaRegistrazioneFileAllegatoMenuItem.setTitle("Timbra");
			operazioniFileAllegatoSubmenu.addItem(apponiSegnaturaRegistrazioneFileAllegatoMenuItem);
		}
	
	}

	private void buildTimbraButtons(final ListGridRecord listRecord, final Record detailRecord, InfoFileRecord lInfoFileRecord,
			Menu operazioniFilePrimarioSubmenu) {
		
		boolean flgAddSubMenuTimbra = false;

		MenuItem timbraMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbraMenuItem(), "file/timbra.gif");
		Menu timbraSubMenu = new Menu();
		
		MenuItem apponiSegnaturaRegistrazioneFilePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiSegnatura(), "file/timbra.gif");
		apponiSegnaturaRegistrazioneFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						timbraFilePrimario(detailRecord);
					}
				});
		apponiSegnaturaRegistrazioneFilePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
		timbraSubMenu.addItem(apponiSegnaturaRegistrazioneFilePrimarioMenuItem);

		if (detailRecord != null && detailRecord.getAttributeAsBoolean("attivaTimbroTipologia") != null
				&& detailRecord.getAttributeAsBoolean("attivaTimbroTipologia")) {
			
			MenuItem timbraFilePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().archivio_list_file_timbra_datiTipologia(), "file/timbra.gif");
			timbraFilePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					String idDoc = listRecord.getAttribute("idDocPrimario");
					detailRecord.setAttribute("idDoc", idDoc);
					detailRecord.setAttribute("tipoTimbro", "T");
					timbraFilePrimario(detailRecord);
				}
			});
			timbraFilePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			flgAddSubMenuTimbra=true;
			timbraSubMenu.addItem(timbraFilePrimarioMenuItem);
		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CON_ORIGINALE_CARTACEO")) {
			if (lInfoFileRecord != null && lInfoFileRecord.getMimetype() != null
					&& (lInfoFileRecord.getMimetype().equals("application/pdf")
							|| lInfoFileRecord.getMimetype().startsWith("image"))) {

				MenuItem timbroConformitaOriginalePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroConformitaOriginaleMenuItem(),"file/timbra.gif");
				timbroConformitaOriginalePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
				timbroConformitaOriginalePrimarioMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

							@Override
							public void onClick(MenuItemClickEvent event) {
								detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_CARTACEO");
								timbraFilePrimario(detailRecord);
							}
						});

				flgAddSubMenuTimbra=true;
				timbraSubMenu.addItem(timbroConformitaOriginalePrimarioMenuItem);
			}
		}

		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRO_CONFORMITA") && lInfoFileRecord != null) {
		
			flgAddSubMenuTimbra=true;

			MenuItem timbroCopiaConformePrimarioMenuItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem(), "file/timbra.gif");
			timbroCopiaConformePrimarioMenuItem.setEnabled(lInfoFileRecord != null && lInfoFileRecord.isConvertibile());
			Menu sottoMenuTimbroCopiaConformeItem = new Menu();
			
			MenuItem timbroCopiaConformeStampaItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_stampa(), "file/timbra.gif");	
			timbroCopiaConformeStampaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_STAMPA");
							timbraFilePrimario(detailRecord);
						}
					});

			sottoMenuTimbroCopiaConformeItem.addItem(timbroCopiaConformeStampaItem);
			
			MenuItem timbroCopiaConformeDigitaleItem = new MenuItem(I18NUtil.getMessages().protocollazione_detail_timbroCopiaConformeMenuItem_suppDigitale(), "file/timbra.gif");	
			timbroCopiaConformeDigitaleItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							detailRecord.setAttribute("finalita", "CONFORMITA_ORIG_DIGITALE_SUPP_DIG");
							timbraFilePrimario(detailRecord);
						}
					});

			sottoMenuTimbroCopiaConformeItem.addItem(timbroCopiaConformeDigitaleItem);
			
			timbroCopiaConformePrimarioMenuItem.setSubmenu(sottoMenuTimbroCopiaConformeItem);
			timbraSubMenu.addItem(timbroCopiaConformePrimarioMenuItem);
		
		}
		
		//Se ho piu voci aggiungo il sottoMenu Timbra
		if(flgAddSubMenuTimbra) {
			timbraMenuItem.setSubmenu(timbraSubMenu);
			operazioniFilePrimarioSubmenu.addItem(timbraMenuItem);
			//Se ho un unica voce la aggiungo ad altreOperazioni con voce "Timbra"
		}else {
			apponiSegnaturaRegistrazioneFilePrimarioMenuItem.setTitle("Timbra");
			operazioniFilePrimarioSubmenu.addItem(apponiSegnaturaRegistrazioneFilePrimarioMenuItem);
		}

	}
	
	public void addToRecent(String idUd, String idDoc) {
		if (idUd != null && !"".equals(idUd) && idDoc != null && !"".equals(idDoc)) {
			Record record = new Record();
			record.setAttribute("idUd", idUd);
			record.setAttribute("idDoc", idDoc);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AddToRecentDataSource");
			lGwtRestDataSource.addData(record, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
				}
			});
		}
	}

	/**
	 * Classe che implementa una sezione nella maschera di richiesta accesso atti
	 * 
	 */
	public class NuovaRichiestaAccessoAttiDetailSection extends DetailSection {

		public NuovaRichiestaAccessoAttiDetailSection(String pTitle, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final DynamicForm... forms) {
			this(pTitle, null, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public NuovaRichiestaAccessoAttiDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
				final DynamicForm... forms) {
			this(pTitle, pHeight, pCanCollapse, pShowOpen, pIsRequired, null, forms);
		}

		public NuovaRichiestaAccessoAttiDetailSection(String pTitle, final Integer pHeight, final boolean pCanCollapse, final boolean pShowOpen, boolean pIsRequired,
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
