/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaMultiplaCallback;
import it.eng.auriga.ui.module.layout.client.archivio.CambiaStatoPostDiscussionePopup;
import it.eng.auriga.ui.module.layout.client.archivio.ContenutiFascicoloPopup;
import it.eng.auriga.ui.module.layout.client.archivio.OpzioniAttiDaScaricarePopup;
import it.eng.auriga.ui.module.layout.client.archivio.OpzioniTrasmissioneAttiPopup;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.OperazioniEffettuateWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.GridItem.GridMultiToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class DiscussioneSedutaDetail extends CustomDetail {
	
	private TabSet tabSet;
	
	// 1 TAB
	private Tab tabDatiSeduta;
	private VLayout layoutTabDatiSeduta;
	private DetailSection sectionDatiDiscussioneSeduta;
	private DynamicForm formDatiDiscussione;	
	private HiddenItem idSedutaItem;
	private TextItem numeroItem;
	private ImgButtonItem operazioniEffettuateButton;
	private ImgButtonItem visualizzaContenutiFascicoloButton;
	private DateTimeItem dtPrimaConvocazioneItem;
	private TextItem luogoPrimaConvocazioneItem;
	private DateTimeItem dtSecondaConvocazioneItem;
	private TextItem luogoSecondaConvocazioneItem;
	private TextItem desStatoSedutaItem;
	private CheckboxItem disattivatoRiordinoAutomaticoItem;
	private TextItem tipoSessioneItem;
	private DynamicForm formListaDatiDiscussione;	
	private HiddenItem esitiXtipoArgomentoItem;
	private DetailSection sectionListaDatiDiscussioneSeduta;
	private HiddenItem listaArgomentiOdgEliminatiItem;
	private ListaDatiDiscussioneSedutaItem listaDatiDiscussioneSedutaItem;
	private GridMultiToolStripButton cambiaStatoMultiButton;
	private GridMultiToolStripButton downloadSchedaSintesiMultiButton;
	private GridMultiToolStripButton downloadFileAttiMultiButton;
	private GridMultiToolStripButton trasmettiMultiButton;
	
	// 2 TAB
	private Tab tabDatiPrimaConv;
	private VLayout layoutTabDatiPrimaConv;
	private DetailSection sectionDatiPrimaConv;
	private DynamicForm formDatiPrimaConv; 
	private DateTimeItem dtInizioLavoriPrimaConvItem;
	private DateTimeItem dtFineLavoriPrimaConvItem;
	private HiddenItem verbaleInfoPrimaConvocazioneItem;
	private DetailSection sectionListaDatiPrimaConv;
	private DynamicForm formListaDatiPrimaConv; 
	private ListaDatiDiscussionePrimaConvocazioneItem listaDatiDiscussionePrimaConvItem;
	private DetailSection verbalePrimaConvSection;
	private DynamicForm formVerbalePrimaConv;
	private CKEditorItem editorVerbalePrimaConvItem;
	
	// 3 TAB
	private Tab tabDatiSecondaConv;
	private VLayout layoutTabDatiSecondaConv;
	private DetailSection sectionDatiSecondaConv;
	private DynamicForm formDatiSecondaConv;
	private DateTimeItem dtInizioLavoriSecondaConvItem;
	private DateTimeItem dtFineLavoriSecondaConvItem;
//	private HiddenItem fileVerbaleSecondaConvItem;
	private HiddenItem verbaleInfoSecondaConvocazioneItem;
	private DetailSection sectionListaDatiSecondaConv;
	private DynamicForm formListaDatiSecondaConv;
	private ListaDatiDiscussioneSecondaConvocazioneItem listaDatiDiscussioneSecondaConvItem;
	private DetailSection verbaleSecondaConvSection;
	private DynamicForm formVerbaleSecondaConv;
	private CKEditorItem editorVerbaleSecondaConvItem;
//	private ButtonItem visualizzaVerbaleSecondaConvItem;
	
	private ToolStrip detailToolStrip;
	private DetailToolStripButton salvaButton;
	private DetailToolStripButton annullaSedutaButton;	
	private DetailToolStripButton anteprimaNumAttiButton;
	private DetailToolStripButton verbaleButton;
	private DetailToolStripButton salvaChiudiLavoriButton;
	private DetailToolStripButton generaFileEsitiButton;
	private DetailToolStripButton vistoAttiAdottatiButton;
	private DetailToolStripButton firmaAttiAdottatiButton; //TODO Da togliere?
	private DetailToolStripButton archiviaSedutaButton;
	
	private Record recordCommissione;
	
	private String statoSeduta;
	private String organoCollegiale;
	private String codCircoscrizione;
	private RecordList listaAttiDaFirmare;
	
	private static String prima_convocazione = "1a_convocazione";
	private static String prima_convocazione_deserta = "1a_convocazione_deserta";
	private static String seconda_convocazione = "2a_convocazione";
	private static String lavori_conclusi = "lavori_conclusi";

	private TextAreaItem listaNomiCommissioniConvocateItem;
	
	
	public DiscussioneSedutaDetail(String nomeEntita, Record recordCommissione, String organoColleggiale) {
		super(nomeEntita);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		this.recordCommissione = recordCommissione;
		
		this.organoCollegiale = organoColleggiale;
		
		this.codCircoscrizione = recordCommissione != null && recordCommissione.getAttributeAsRecord("odgInfo") != null &&
						recordCommissione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") != null ?
						recordCommissione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") : null;
						
		this.statoSeduta = recordCommissione != null && recordCommissione.getAttributeAsString("stato") != null ?
				recordCommissione.getAttributeAsString("stato") : null;
				
		createTabSet();
		createDetailToolStrip();

		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(tabSet);
		mainLayout.addMember(detailToolStrip);
		
		setMembers(mainLayout);	
	}
	
	protected void createTabSet() {
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(10);
		
		/**
		 *  Tab Seduta
		 */

		tabDatiSeduta = new Tab("<b>" + getTitleTabDatiSeduta() + "</b>");
		tabDatiSeduta.setAttribute("tabID", "HEADER");
		tabDatiSeduta.setPrompt(getTitleTabDatiSeduta());


		layoutTabDatiSeduta = createLayoutTab(getLayoutTabDatiSeduta(), lVLayoutSpacer);

		// Aggiungo i layout ai tab
		tabDatiSeduta.setPane(layoutTabDatiSeduta);

		tabSet.addTab(tabDatiSeduta);
		
		/**
		 *  Tab Prima Convocazione
		 */
		
		tabDatiPrimaConv = new Tab("<b>" + getTitleTabDatiPrimaConvocazione() + "</b>");
		tabDatiPrimaConv.setAttribute("tabID", "PRIMA_CONV");
		tabDatiPrimaConv.setPrompt(getTitleTabDatiPrimaConvocazione());

		layoutTabDatiPrimaConv = createLayoutTab(getLayoutTabDatiPrimaConvocazione(), lVLayoutSpacer);

		// Aggiungo i layout ai tab
		tabDatiPrimaConv.setPane(layoutTabDatiPrimaConv);

		tabSet.addTab(tabDatiPrimaConv);
		
		/**
		 * Tab Seconda Convocazione
		 */
		if(!AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE")) {
			tabDatiSecondaConv = new Tab("<b>" + getTitleTabDatiSecondaConvocazione() + "</b>");
			tabDatiSecondaConv.setAttribute("tabID", "SECONDA_CONV");
			tabDatiSecondaConv.setPrompt(getTitleTabDatiSecondaConvocazione());
	
			layoutTabDatiSecondaConv = createLayoutTab(getLayoutTabDatiSecondaConvocazione(), lVLayoutSpacer);
	
			// Aggiungo i layout ai tab
			tabDatiSecondaConv.setPane(layoutTabDatiSecondaConv);

			tabSet.addTab(tabDatiSecondaConv);
		}
	}
	
	protected void createDetailToolStrip() {
		
		detailToolStrip = new ToolStrip();
		detailToolStrip.setWidth100();
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); // push all buttons to the right

		createButtons();
		
		detailToolStrip.addButton(salvaButton);
		detailToolStrip.addButton(annullaSedutaButton);
		detailToolStrip.addButton(anteprimaNumAttiButton);
		detailToolStrip.addButton(verbaleButton);
//		detailToolStrip.addButton(salvaChiudiVerbaleButton);
		detailToolStrip.addButton(salvaChiudiLavoriButton);
		detailToolStrip.addButton(generaFileEsitiButton);
		detailToolStrip.addButton(vistoAttiAdottatiButton);
		detailToolStrip.addButton(firmaAttiAdottatiButton);
		detailToolStrip.addButton(archiviaSedutaButton);
	}
	
	private void createButtons() {
		
		salvaButton = new DetailToolStripButton("Salva", "buttons/save.png");
		salvaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				if(validate()) {
					buildSave();
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
				}
			}
		});
		
		annullaSedutaButton = new DetailToolStripButton("Annulla seduta", "delibere/annullaSeduta.png");
		annullaSedutaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				MotivoAnnullamentoSedutePopup motivoAnn = new MotivoAnnullamentoSedutePopup() {
					
					@Override
					public void manageOnOkButtonClick(Record values) {
						if (values != null) {
							String motivazione = values.getAttributeAsString("motivazione");
							buildAnnullaSeduta(motivazione);
						}
					};
				};
				motivoAnn.show();
			}
		});
				
		anteprimaNumAttiButton = new DetailToolStripButton("Anteprima numerazione atti", "file/preview.png");
		anteprimaNumAttiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				buildAnteprimaNumAtti();
			}
		});
				
		verbaleButton = new DetailToolStripButton("Verbale", "richiesteAccessoAtti/azioni/approvazione.png");
		verbaleButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {	
				
				Menu menuVerbale = new Menu();
				
				MenuItem generaVerbaleMenuItem = new MenuItem("Genera", "protocollazione/generaDaModello.png");
				generaVerbaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						if(validate()) {
							buildGeneraVerbale();
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
						}
					}
				});
				menuVerbale.addItem(generaVerbaleMenuItem);
				
				if(isAbilVisualizzaVerbale()) {
					MenuItem visualizzaVerbaleMenuItem = new MenuItem("Visualizza", "file/preview.png");
					visualizzaVerbaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(validate()) {
								buildVisualizzaVerbale();
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
							}
						}
					});
					menuVerbale.addItem(visualizzaVerbaleMenuItem);
				}
				
				if(isAbilToFirmaVerbale() && isAbilVisualizzaVerbale()) {
					MenuItem firmaVerbaleMenuItem = new MenuItem("Firma", "file/mini_sign.png");
					firmaVerbaleMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(validate()) {
								buildFirmaVerbale();
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
							}
						}
					});
					menuVerbale.addItem(firmaVerbaleMenuItem);
				}
				
				menuVerbale.showContextMenu();
			}
		});
		
//		salvaChiudiVerbaleButton = new DetailToolStripButton("Salva e chiudi verbale", "richiesteAccessoAtti/azioni/approvazione.png");
//		salvaChiudiVerbaleButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
//
//			@Override
//			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
//				if(validate()) {
//					buildSalvaChiudiVerbale();
//				} else {
//					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
//				}
//			}
//		});
		
		salvaChiudiLavoriButton = new DetailToolStripButton("Salva e chiudi lavori", "delibere/salvaechiudi_lavori.png");
		salvaChiudiLavoriButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(validateChiusuraLavori()) {
					SC.ask("L'operazione di chiusura lavori rende definitivi i dati degli esiti della seduta e comporta la numerazione degli atti " + 
							"approvati laddove previsto. Dopo tale operazione gli esiti e le date dei lavori della seduta " + 
							"non saranno più modificabili. Confermi di voler procedere?", new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {									
									if(value) {
										buildSalvaChiudiLavori();
									}							
								}
							}
					);
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
				}
			}
		});
		
		generaFileEsitiButton = new DetailToolStripButton("Genera file esiti", "delibere/generafile_esiti.png");
		generaFileEsitiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				buildGeneraFileEsiti();
			}
		});
		
		vistoAttiAdottatiButton = new DetailToolStripButton("Visto atti", "pratiche/task/buttons/conferma.png");
		vistoAttiAdottatiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				buildVistoAttiAdottati();				
			}
		});		
		
		firmaAttiAdottatiButton = new DetailToolStripButton("Firma atti", "pratiche/task/buttons/firma.png");
		firmaAttiAdottatiButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				buildFirmaAttiAdottati();				
			}
		});		
		
		archiviaSedutaButton = new DetailToolStripButton("Sposta in storico", "delibere/archiviaSeduta.png");
		archiviaSedutaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SC.ask("Sei sicuro di voler archiviare la seduta in storico ? La seduta non potrà più essere ripristinata in discussione.", new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {									
						if(value) {
							buildArchiviaSeduta();
						}							
					}
				});
			}
		});		
		
	}
	
	private void saveAndReload(final Record recordToSave) {	
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.executecustom("saveDiscussioneSeduta", recordToSave, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
					Record recordToGet = dsResponse.getData()[0];
					recordToGet.setAttribute("organoCollegiale", organoCollegiale);
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
					lGWTRestDataSource.call(recordToGet, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							caricaDettaglio(object);	
						}
					});
				}
			}
		});
	}
	
	protected void buildSave() {		
		Record recordToSave = getRecordToSave();
		recordToSave.setAttribute("organoCollegiale", organoCollegiale);		
		saveAndReload(recordToSave);
	}
	
	protected void buildAnnullaSeduta(String motivazione) {
		final Record detailRecord = getRecordToSave();
		detailRecord.setAttribute("motivoAnnullamento", motivazione);
		new GWTRestService<Record, Record>("DiscussioneSedutaDataSource").executecustom("annullaSeduta", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {										
					Layout.addMessage(new MessageBean("Annullamento seduta avvenuto con successo", "", MessageType.INFO));
					Record recordToGet = dsResponse.getData()[0];
					recordToGet.setAttribute("organoCollegiale", organoCollegiale);
					new GWTRestService<Record, Record>("DiscussioneSedutaDataSource").call(recordToGet, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {							
							caricaDettaglio(object);	
						}
					});
				}
			}
		});		
	}
	
	protected void buildArchiviaSeduta() {
		final Record detailRecord = getRecordToSave();
		new GWTRestService<Record, Record>("DiscussioneSedutaDataSource").executecustom("archiviaSeduta", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {										
					Layout.addMessage(new MessageBean("Archiviazione seduta avvenuta con successo", "", MessageType.INFO));
					Record recordToGet = dsResponse.getData()[0];
					recordToGet.setAttribute("organoCollegiale", organoCollegiale);
					new GWTRestService<Record, Record>("DiscussioneSedutaDataSource").call(recordToGet, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {							
							caricaDettaglio(object);	
						}
					});
				}
			}
		});		
	}
	
	protected void buildAnteprimaNumAtti() {		
		Record record = getRecordToSave();
		record.setAttribute("listaArgomentiOdg", listaDatiDiscussioneSedutaItem != null ? listaDatiDiscussioneSedutaItem.getValueAsRecordList() : null);
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.executecustom("simulaNumerazioneAttiSeduta", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Anteprima atti avvenuta con successo", "", MessageType.INFO));
					Record recordArgomenti = dsResponse.getData()[0];
					Record recordToModify = getRecordToSave();
					recordToModify.setAttribute("listaArgomentiOdg", recordArgomenti.getAttributeAsRecordList("listaArgomentiOdg"));
					caricaDettaglio(recordToModify);
				}
			}
		});
	}
	
	public boolean isAbilVisualizzaVerbale() {		
		if(recordCommissione != null) {
			final Record verbaleInfo = isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? recordCommissione.getAttributeAsRecord("verbaleInfoSecondaConvocazione") : recordCommissione.getAttributeAsRecord("verbaleInfoPrimaConvocazione");
			if(verbaleInfo != null && verbaleInfo.getAttributeAsString("idDoc") != null && !"".equalsIgnoreCase(verbaleInfo.getAttributeAsString("idDoc"))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAbilToFirmaVerbale() {
		if(recordCommissione != null) {
			final Record verbaleInfo = isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? recordCommissione.getAttributeAsRecord("verbaleInfoSecondaConvocazione") : recordCommissione.getAttributeAsRecord("verbaleInfoPrimaConvocazione");
			boolean flgDaFirmare = verbaleInfo != null && verbaleInfo.getAttributeAsBoolean("flgDaFirmare") != null ? verbaleInfo.getAttributeAsBoolean("flgDaFirmare") : false;	
			return flgDaFirmare;
		}
		return false;
	}
	
	public void buildVisualizzaVerbale() {
		if(recordCommissione != null) {
			final Record verbaleInfo = isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? recordCommissione.getAttributeAsRecord("verbaleInfoSecondaConvocazione") : recordCommissione.getAttributeAsRecord("verbaleInfoPrimaConvocazione");
			String display = verbaleInfo.getAttributeAsString("displayFilename");
			String uri = verbaleInfo.getAttribute("uri");
			boolean remoteUri = false;
			final Record info = getRecordInfoVerbale(verbaleInfo);
			preview(display, uri, remoteUri, new InfoFileRecord(InfoFileRecord.buildInfoFileRecord(info.getJsObj())));
		}		
	}
	
	public void buildGeneraVerbale() {			
		final Record detailRecord = getRecordToSave();
		detailRecord.setAttribute("datiDiscPrimaConvEditorVerbale", editorVerbalePrimaConvItem != null ? editorVerbalePrimaConvItem.getValue() : null);
		detailRecord.setAttribute("datiDiscSecondaConvEditorVerbale", editorVerbaleSecondaConvItem != null ? editorVerbaleSecondaConvItem.getValue() : null);
		generaModelloVerbale(detailRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(final Record recordFileGenerato) {				
				final String uri = recordFileGenerato.getAttributeAsString("uri");
				final String nomeFile = recordFileGenerato.getAttributeAsString("nomeFile");
				final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileGenerato.getAttributeAsRecord("infoFile").getJsObj()));
				new PreviewWindowWithCallback(uri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						final Record verbaleInfo = isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? detailRecord.getAttributeAsRecord("verbaleInfoSecondaConvocazione") : detailRecord.getAttributeAsRecord("verbaleInfoPrimaConvocazione");
						verbaleInfo.setAttribute("uri", uri);
						verbaleInfo.setAttribute("displayFilename", nomeFile);
						verbaleInfo.setAttribute("mimetype", infoFile.getMimetype());
						verbaleInfo.setAttribute("encoding", AurigaLayout.getEncoding());
						verbaleInfo.setAttribute("algoritmo", AurigaLayout.getAlgoritmoImpronta());
						verbaleInfo.setAttribute("impronta", infoFile.getImpronta());
						verbaleInfo.setAttribute("flgPdfizzabile", infoFile.isConvertibile());
						verbaleInfo.setAttribute("dimensione", infoFile.getBytes());
						verbaleInfo.setAttribute("flgFirmato", infoFile.isFirmato());
						verbaleInfo.setAttribute("tipoFirma", infoFile.getTipoFirma());
						if (isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE")) {		
							detailRecord.setAttribute("verbaleInfoSecondaConvocazione", verbaleInfo);
						} else {
							detailRecord.setAttribute("verbaleInfoPrimaConvocazione", verbaleInfo);
						}
						detailRecord.setAttribute("organoCollegiale", organoCollegiale);
						saveAndReload(detailRecord);
					}
				});
			}
		});	
	}
	
	public void generaModelloVerbale(Record detailRecord, final ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.executecustom(isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? "generaModelloVerbaleSecondaConvocazione" : "generaModelloVerbalePrimaConvocazione", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {										
					Record recordFileGenerato = dsResponse.getData()[0];
					if(callback != null) {
						callback.execute(recordFileGenerato);
					}
				}
			}
		});	
	}
	
	public void buildFirmaVerbale() {		
		final Record detailRecord = getRecordToSave();
		final Record verbaleInfo = isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? detailRecord.getAttributeAsRecord("verbaleInfoSecondaConvocazione") : detailRecord.getAttributeAsRecord("verbaleInfoPrimaConvocazione");
		final String uri = verbaleInfo.getAttributeAsString("uri");
		final String nomeFile = verbaleInfo.getAttributeAsString("displayFilename");
		final Record info = getRecordInfoVerbale(verbaleInfo);
		final InfoFileRecord infoFile = new InfoFileRecord(InfoFileRecord.buildInfoFileRecord(info.getJsObj()));		
		new PreviewWindowWithCallback(uri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {						
				String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
				String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
				FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, uri, nomeFile, infoFile, new FirmaCallback() {

					@Override
					public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
						verbaleInfo.setAttribute("uri", uriFileFirmato);
						verbaleInfo.setAttribute("displayFilename", nomeFileFirmato);
						verbaleInfo.setAttribute("mimetype", infoFileFirmato.getMimetype());
						verbaleInfo.setAttribute("encoding", AurigaLayout.getEncoding());
						verbaleInfo.setAttribute("algoritmo", AurigaLayout.getAlgoritmoImpronta());
						verbaleInfo.setAttribute("impronta", infoFileFirmato.getImpronta());
						verbaleInfo.setAttribute("flgPdfizzabile", infoFileFirmato.isConvertibile());
						verbaleInfo.setAttribute("dimensione", infoFileFirmato.getBytes());
						verbaleInfo.setAttribute("flgFirmato", infoFileFirmato.isFirmato());
						verbaleInfo.setAttribute("tipoFirma", infoFileFirmato.getTipoFirma());
						if(infoFileFirmato.getFirmatari() != null) {
							RecordList listaFirmatari = new RecordList();
							for(int i = 0; i < infoFileFirmato.getFirmatari().length; i++) {
								Record lRecordFirmatario = new Record();
								lRecordFirmatario.setAttribute("firmatario", infoFileFirmato.getFirmatari()[i]);
								listaFirmatari.add(lRecordFirmatario);
							}
							verbaleInfo.setAttribute("listaFirmatari", listaFirmatari);
						}
						verbaleInfo.setAttribute("azioneDaFare", "firma");							
						Record recordToSave = getRecordToSave();
						recordToSave.setAttribute("organoCollegiale", organoCollegiale);
						if (isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE")) {		
							recordToSave.setAttribute("verbaleInfoSecondaConvocazione", verbaleInfo);
						} else {
							recordToSave.setAttribute("verbaleInfoPrimaConvocazione", verbaleInfo);
						}
						saveAndReload(recordToSave);
					}
				});
			}
		});
	}
	
	private Record getRecordInfoVerbale(Record verbaleInfo) {
		Record infoFile = new Record();
		infoFile.setAttribute("correctFileName", verbaleInfo.getAttribute("displayFilename"));
		infoFile.setAttribute("mimetype", verbaleInfo.getAttribute("mimetype"));
		infoFile.setAttribute("encoding", verbaleInfo.getAttribute("encoding"));
		infoFile.setAttribute("algoritmo", verbaleInfo.getAttribute("algoritmo"));
		infoFile.setAttribute("impronta", verbaleInfo.getAttribute("impronta"));
		infoFile.setAttribute("convertibile", verbaleInfo.getAttributeAsBoolean("flgPdfizzabile"));
		infoFile.setAttribute("bytes", verbaleInfo.getAttributeAsLong("dimensione"));
		infoFile.setAttribute("firmato", verbaleInfo.getAttributeAsBoolean("flgFirmato"));
		infoFile.setAttribute("firmaValida", true);
		infoFile.setAttribute("tipoFirma", verbaleInfo.getAttribute("tipoFirma"));
		if(verbaleInfo.getAttributeAsRecordList("listaFirmatari") != null) {
			String[] firmatari = new String[verbaleInfo.getAttributeAsRecordList("listaFirmatari").getLength()];
			for(int i = 0; i < verbaleInfo.getAttributeAsRecordList("listaFirmatari").getLength(); i++) {
				firmatari[i] = verbaleInfo.getAttributeAsRecordList("listaFirmatari").get(i).getAttribute("firmatario");
			}
			infoFile.setAttribute("firmatari", firmatari);
		}	
		return infoFile;
	}
	
	protected void buildSalvaChiudiLavori() {
		Record recordToSave = getRecordToSave();
		recordToSave.setAttribute("stato", "lavori_conclusi");
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.addParam("isChiusuraLavori", "true");
		lGWTRestDataSource.executecustom("saveDiscussioneSeduta", recordToSave, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
					Record recordToGet = dsResponse.getData()[0];
					recordToGet.setAttribute("organoCollegiale", organoCollegiale);
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
					lGWTRestDataSource.call(recordToGet, new ServiceCallback<Record>() {
						
						@Override
						public void execute(final Record object) {
												
							if(object.getAttributeAsRecord("odgInfo") != null && 
									object.getAttributeAsRecord("odgInfo").getAttributeAsString("nomeModello") != null && 
									!"".equalsIgnoreCase(object.getAttributeAsRecord("odgInfo").getAttributeAsString("nomeModello"))) {
								
								GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
								object.setAttribute("organoCollegiale", organoCollegiale);
								lGWTRestDataSource.executecustom("generaModelloPerSalvaChiudiLavori", object, new DSCallback() {
									
									@Override
									public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
										
										if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
											
											final Record recordToGet = dsResponse.getData()[0];											
											Record recordFileGenerato = recordToGet.getAttributeAsRecord("fileGenerato");
												
											final boolean flgDaFirmare = recordToGet.getAttributeAsRecord("odgInfo").getAttributeAsBoolean("flgDaFirmare");
											final String idDoc = recordToGet.getAttributeAsRecord("odgInfo").getAttributeAsString("idDoc");								
											
											final String nomeFile =  recordFileGenerato.getAttributeAsString("nomeFile");
											final String documentUri = recordFileGenerato.getAttributeAsString("uri");										
											final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(recordFileGenerato.getAttributeAsObject("infoFile"));
																						
											new PreviewWindowWithCallback(documentUri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {

												@Override
												public void execute(Record objectFirma) {													
													if (flgDaFirmare) {
														// Leggo gli eventuali parametri per forzare il tipo d firma
														String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
														String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
														FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, documentUri, nomeFile, infoFile, new FirmaCallback() {

															@Override
															public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {																
																buildVersionaChiudiLavori(recordToGet, idDoc, uriFileFirmato, nomeFileFirmato, infoFileFirmato);
															}
														});
													} else {
														buildVersionaChiudiLavori(recordToGet, idDoc, documentUri, nomeFile, infoFile);
													}
												}
											});
										}
									}
								});
							} else {
								caricaDettaglio(object);
							}
						}
					});
				}
			}
		});		
	}
	
	protected void buildGeneraFileEsiti() {
		Record recordToGen = getRecordToSave();
		recordToGen.setAttribute("stato", "lavori_conclusi");
		if(recordToGen.getAttributeAsRecord("odgInfo") != null && recordToGen.getAttributeAsRecord("odgInfo").getAttributeAsString("nomeModello") != null &&  !"".equalsIgnoreCase(recordToGen.getAttributeAsRecord("odgInfo").getAttributeAsString("nomeModello"))) {
			
			GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
			recordToGen.setAttribute("organoCollegiale", organoCollegiale);
			lGWTRestDataSource.executecustom("generaModelloPerSalvaChiudiLavori", recordToGen, new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						
						final Record recordToGet = dsResponse.getData()[0];											
						Record recordFileGenerato = recordToGet.getAttributeAsRecord("fileGenerato");
							
						final boolean flgDaFirmare = recordToGet.getAttributeAsRecord("odgInfo").getAttributeAsBoolean("flgDaFirmare");
						final String idDoc = recordToGet.getAttributeAsRecord("odgInfo").getAttributeAsString("idDoc");								
						
						final String nomeFile =  recordFileGenerato.getAttributeAsString("nomeFile");
						final String documentUri = recordFileGenerato.getAttributeAsString("uri");										
						final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileRecord(recordFileGenerato.getAttributeAsObject("infoFile"));
																	
						new PreviewWindowWithCallback(documentUri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {

							@Override
							public void execute(Record objectFirma) {													
								if (flgDaFirmare) {
									// Leggo gli eventuali parametri per forzare il tipo d firma
									String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
									String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
									FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, documentUri, nomeFile, infoFile, new FirmaCallback() {

										@Override
										public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {																
											buildVersionaChiudiLavori(recordToGet, idDoc, uriFileFirmato, nomeFileFirmato, infoFileFirmato);
										}
									});
								} else {
									buildVersionaChiudiLavori(recordToGet, idDoc, documentUri, nomeFile, infoFile);
								}
							}
						});
					}
				}
			});
		} else {
			caricaDettaglio(recordToGen);
		}
	}
	
	protected void getListaAttiDaFirmare(final ServiceCallback<RecordList> callback) {
		if(callback != null) {
			final RecordList listaArgomentiOdgSelezionati = new RecordList();
			if(listaDatiDiscussioneSedutaItem != null && listaDatiDiscussioneSedutaItem.getGridSelectedRecords() != null) {
				for (int i = 0; i < listaDatiDiscussioneSedutaItem.getGridSelectedRecords().length; i++) {
					listaArgomentiOdgSelezionati.add(listaDatiDiscussioneSedutaItem.getGridSelectedRecords()[i]);						
				}
			}
			if(listaArgomentiOdgSelezionati.getLength() > 0) {
				SC.ask("Vuoi firmare solo gli atti selezionati? In caso contrario verranno firmati tutti gli atti che sono in attesa di tua firma.", new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {									
						if(value) {
							boolean hasAttiDaNonFirmare = false;
							for (int i = 0; i < listaArgomentiOdgSelezionati.getLength(); i++) {
								Record lRecord = listaArgomentiOdgSelezionati.get(i);
								if(lRecord.getAttribute("attoDaFirmare") == null || !"1".equals(lRecord.getAttribute("attoDaFirmare"))) {
									hasAttiDaNonFirmare = true;
									break;
								}							
							}
							if(hasAttiDaNonFirmare) {
								Layout.addMessage(new MessageBean("Alcuni degli atti selezionati non sono in attesa di tua firma", "", MessageType.ERROR));
								callback.execute(null);
							} else {
								callback.execute(listaArgomentiOdgSelezionati);
							}
						} else {
							callback.execute(getListaAttiDaFirmare());
						}
					}
				});
			} else {
				callback.execute(getListaAttiDaFirmare());
			}
		}
	}
	
	protected RecordList getListaAttiDaFirmare() {
		RecordList listaAttiDaFirmare = null;
		RecordList listaArgomentiOdg = listaDatiDiscussioneSedutaItem != null ? listaDatiDiscussioneSedutaItem.getValueAsRecordList() : null;
		if(listaArgomentiOdg != null) {
			listaAttiDaFirmare = new RecordList();
			for (int i = 0; i < listaArgomentiOdg.getLength(); i++) {
				Record lRecord= listaArgomentiOdg.get(i);
				if(lRecord.getAttribute("attoDaFirmare") != null && "1".equals(lRecord.getAttribute("attoDaFirmare"))) {
					listaAttiDaFirmare.add(lRecord);
				}
			}
		}
		return listaAttiDaFirmare;
	}
	
	protected void buildFirmaAttiAdottati() {
		/**
		 * Nella lista atti in discussione quando si fa l'azione di "Firma atti" devono essere firmati SOLO gli atti selezionati o tutti gli atti con stato pronto_da_firmare
		 * Firmare vorrebbe dire fare la firma del file unione e degli allegati parte integrante per ciascun atto + mandare in pubblicazione
		 */
		getListaAttiDaFirmare(new ServiceCallback<RecordList>() {
			
			@Override
			public void execute(RecordList lRecordList) {
				if(lRecordList != null) {
					listaAttiDaFirmare = lRecordList;
					if(listaAttiDaFirmare != null) {
						final Record lRecord = new Record();
						lRecord.setAttribute("listaRecord", listaAttiDaFirmare);
						firmaAttiAdottati(lRecord, new ServiceCallback<Record>() {
	
							@Override
							public void execute(Record response) {
								Record recordToGet = getRecordToSave();
								recordToGet.setAttribute("organoCollegiale", organoCollegiale);
								GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
								lGWTRestDataSource.call(recordToGet, new ServiceCallback<Record>() {
									
									@Override
									public void execute(Record object) {
										caricaDettaglio(object);
									}
								});
							}
						});
					}
				}
			}
		});
	}
	
	public void firmaAttiAdottati(Record lRecordSelezionati, final ServiceCallback<Record> callbackFirma) {
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.performCustomOperation("getListaPerFirmaAttiAdottati", lRecordSelezionati,
			new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						// Sono stati ritornati i file che devono essere firmati
						if(response.getData()[0].getAttributeAsRecordList("files") != null && !response.getData()[0].getAttributeAsRecordList("files").isEmpty()) {
							firmaFileAttiAdottati(response.getData()[0], callbackFirma);
						} else {
							Layout.addMessage(new MessageBean("Non ci sono atti adottati da firmare", "", MessageType.ERROR));
						}
					}
				}
			}, new DSRequest()
		);
	}
	
	private void firmaFileAttiAdottati(Record record, final ServiceCallback<Record> callbackFirma) {
		// Avvio l'applet per eseguire la firma
		String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
		String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
		FirmaUtility.firmaMultipla(true, appletTipoFirmaAtti, hsmTipoFirmaAtti, record.getAttributeAsRecordArray("files"), new FirmaMultiplaCallback() {

			@Override
			public void execute(Map<String, Record> files, Record[] filesAndUd) {
				manageFirmaFileAttiAdottatiCallBack(files, filesAndUd, callbackFirma);
			}
		});
	}
	
	protected void manageFirmaFileAttiAdottatiCallBack(final Map<String, Record> files, final Record[] filesAndUd, final ServiceCallback<Record> callbackFirma) {
		Layout.showWaitPopup("Elaborazione degli atti firmati in corso...");		
		// Inserisco all'interno di un record i file che devono essere elaborati
		final Record lRecord = new Record();
		Record[] lRecords = new Record[files.size()];
		int i = 0;
		for (String lString : files.keySet()) {
			Record recordToInsert = files.get(lString);
			for (Record recordConUd : filesAndUd) {
				if (recordConUd.getAttribute("idFile").equals(lString)) {
					recordToInsert.setAttribute("idUd", recordConUd.getAttribute("idUd"));
				}
			}
			lRecords[i] = recordToInsert;
			i++;
		}
		lRecord.setAttribute("files", lRecords);
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("DiscussioneSedutaDataSource");
		lGWTRestDataSource.performCustomOperation("aggiornaDocumentoFirmato", lRecord, new DSCallback() {

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
							for (int i = 0; i < listaAttiDaFirmare.getLength(); i++) {
								idUdDocumentiInError.add(idUdInError);
								Record lRecordSelected = listaAttiDaFirmare.get(i);
								String idUd = lRecordSelected.getAttribute("idUd");
								if (idUd.equals(idUdInError)) {
									String estremi = lRecordSelected.getAttribute("estremiPropostaUDXOrd");
									documentiInError.add(estremi);
								}
							}
						}
						if (documentiInError.size() > 0) {
							if (documentiInError.size() == files.size()) {
								Layout.addMessage(new MessageBean("Tutti gli atti non sono stati firmati correttamente", "", MessageType.ERROR));								
							} else {
								String message = null;
								if (documentiInError.size() == 1) {
									message = "L'atto " + documentiInError.get(0) + " non è stato firmato correttamente";
								} else {
									message = "Gli atti ";
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
								callbackFirma.execute(new Record());
							}
						}
					} else {
						Layout.addMessage(new MessageBean("Tutti gli atti sono stati firmati con successo", "", MessageType.INFO));
						callbackFirma.execute(new Record());
					}
				} else {
					Layout.addMessage(new MessageBean("Si è verificato un errore durante la firma", "", MessageType.ERROR));
				}
			}
		}, new DSRequest());
	}
		
	protected void buildVistoAttiAdottati() {
		/**
		 * Nella lista atti in discussione quando si fa l’azione di "Visto atti" devono essere firmati SOLO gli atti con stato pronto_da_firmare
		 * Firmare vorrebbe dire fare la firma del file unione e degli allegati parte integrante per ciascun atto + mandare in pubblicazione
		 */
		listaAttiDaFirmare = getListaAttiDaFirmare();
		if(listaAttiDaFirmare != null && listaAttiDaFirmare.getLength() > 0) {
			
			Layout.showWaitPopup("Visto atti in corso: potrebbe richiedere qualche secondo. Attendere…");				
			
			Record lRecord = new Record();
			lRecord.setAttribute("listaRecord", listaAttiDaFirmare);
			lRecord.setAttribute("organoCollegiale", organoCollegiale);
			
			final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("DiscussioneSedutaDataSource");
			lGWTRestDataSource.executecustom("vistoAttiAdottati", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					massiveOperationCallback(response, listaAttiDaFirmare, "idUd", "Visto atti effettuato con successo",
							"Tutti i record selezionati per il visto sono andati in errore!",
							"Alcuni dei record selezionati per il visto sono andati in errore!", null);
				}
			});
		} else {
			Layout.addMessage(new MessageBean("Non ci sono atti adottati da vistare", "", MessageType.ERROR));		
		}
	}	
	
	private String findRecordFromIdDoc(String idDoc, Record[] filesAndUd) {
		for (Record lRecord : filesAndUd) {
			if (idDoc.equals(lRecord.getAttribute("idFile"))) {
				return lRecord.getAttribute("idUd");
			}
		}
		return null;
	}
	
	private boolean isStatoLavoriConclusi() {
		if(recordCommissione != null && recordCommissione.getAttribute("stato") != null
				&& lavori_conclusi.equalsIgnoreCase(recordCommissione.getAttribute("stato"))) {
			return true;
		}
		return false;
	}
	
	private boolean showStatoLavoriConclusi() {
		
		boolean isShow = false;
		if(recordCommissione != null && recordCommissione.getAttribute("stato") != null
				&& lavori_conclusi.equalsIgnoreCase(recordCommissione.getAttribute("stato"))) {		
			Record odgInfo = recordCommissione != null ? recordCommissione.getAttributeAsRecord("odgInfo") : null;
			if(odgInfo != null && odgInfo.getAttributeAsString("uriModello") != null &&
					!"".equalsIgnoreCase(odgInfo.getAttributeAsString("uriModello"))) {
				isShow = true;
			}
		}
		return isShow;
	}
	
	private boolean isButtonEnabled() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/GNT/G");
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNS/G");
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMM/G");
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMG/G");
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/OPR/G");
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNF/G");
		} else {
			return false;
		}
	}
	
	private boolean isFirmaEnabled() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/GNT/F");
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNS/F");
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMM/F");
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMG/F");
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/OPR/F");
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNF/F");
		} else {
			return false;
		}
	}
	
	private boolean showVistoAttiAdottatiButton() {
		Record odgInfo = recordCommissione != null ? recordCommissione.getAttributeAsRecord("odgInfo") : null;
		return odgInfo != null ? odgInfo.getAttributeAsBoolean("flgShowVistoSegretario") : false;		
	}
	
	private boolean showFirmaAttiAdottatiButton() {
		if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return false;
		}
		return isStatoLavoriConclusi();		
	}
	
	private boolean isVistoEnabled() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/GNT/VST");
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNS/VST");
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMM/VST");
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMG/VST");
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/OPR/VST");
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNF/VST");
		} else {
			return false;
		}
	}
	
	private boolean showVerbaleButton() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_VERBALE_SEDUTA_GNT");
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_VERBALE_SEDUTA_CNS");
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_VERBALE_SEDUTA_CMM");
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_VERBALE_SEDUTA_CMG");
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_VERBALE_SEDUTA_OPR");
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			return AurigaLayout.getParametroDBAsBoolean("SHOW_VERBALE_SEDUTA_CNF");
		} else {
			return false;
		}
	}
	
	private boolean isAbilAnnullaSeduta() {	
		Record odgInfo = recordCommissione != null ? recordCommissione.getAttributeAsRecord("odgInfo") : null;
		return odgInfo != null ? odgInfo.getAttributeAsBoolean("flgAbilAnnullaSeduta") : false;
	}
	
	private boolean isAbilArchiviaSeduta() {	
		Record odgInfo = recordCommissione != null ? recordCommissione.getAttributeAsRecord("odgInfo") : null;
		return odgInfo != null ? odgInfo.getAttributeAsBoolean("flgAbilArchiviaSeduta") : false;
	}
	
	private boolean isAbilAnteprimaNumAtti() {
		return !"COMMISSIONE".equalsIgnoreCase(organoCollegiale);
	}
		
	public void caricaDettaglio(Record record) {
		recordCommissione = record;
		editRecord(record);
		
		if(record != null && record.getAttributeAsString("stato") != null) {	
			statoSeduta = record.getAttributeAsString("stato");
		}
		
		if (editorVerbalePrimaConvItem != null) {
			editorVerbalePrimaConvItem.setValue(record.getAttribute("datiDiscPrimaConvEditorVerbale"));
		}
		if (editorVerbaleSecondaConvItem != null) {
			editorVerbaleSecondaConvItem.setValue(record.getAttribute("datiDiscSecondaConvEditorVerbale"));
		}
		
		if(numeroItem != null) {
			numeroItem.setCanEdit(false);
		}
		
		if(desStatoSedutaItem != null) {
			desStatoSedutaItem.setCanEdit(false);
		}
		
		if(tipoSessioneItem != null) {
			tipoSessioneItem.setCanEdit(false);
		}
		
		if(disattivatoRiordinoAutomaticoItem != null) {
			if(isButtonEnabled()) {
				disattivatoRiordinoAutomaticoItem.setCanEdit(true);
			} else {
				disattivatoRiordinoAutomaticoItem.setCanEdit(false);
			}
		}
		
		if(listaDatiDiscussioneSedutaItem != null) {			
			listaDatiDiscussioneSedutaItem.setCanEdit(listaDatiDiscussioneSedutaItem.getCanEdit());
			listaDatiDiscussioneSedutaItem.redrawMultiselectButtons();
			//Abilitazione colonna lista atti in base allo stato della seduta
			listaDatiDiscussioneSedutaItem.setCanEditEsitoDiscussioneGridField();
		}
		
		// Salva
		salvaButton.setDisabled(!isButtonEnabled());
		
		// Annulla seduta
		if(isAbilAnnullaSeduta()) {			
			annullaSedutaButton.show();
			annullaSedutaButton.setDisabled(!isButtonEnabled());
		} else {
			annullaSedutaButton.hide();
		}
		
		// Anteprima numerazione atti
		if(isAbilAnteprimaNumAtti()) {
			anteprimaNumAttiButton.show();
			anteprimaNumAttiButton.setDisabled(!isButtonEnabled());
		} else {
			anteprimaNumAttiButton.hide();
		}
			
		// Verbale
		if(showVerbaleButton()) {
			verbaleButton.show();
			verbaleButton.setDisabled(!isButtonEnabled());
		} else {
			verbaleButton.hide();
		}
				
		// Genera file esiti & Salva e chiudi lavori
		if(showStatoLavoriConclusi()) {
			generaFileEsitiButton.show();
			salvaChiudiLavoriButton.hide();
		} else {
			salvaChiudiLavoriButton.show();
			generaFileEsitiButton.hide();
		}
		
		// Visto atti adottati
		if(showVistoAttiAdottatiButton()) {
			vistoAttiAdottatiButton.show();
			vistoAttiAdottatiButton.setDisabled(!isVistoEnabled());
		} else {
			vistoAttiAdottatiButton.hide();
		}
		
		// Firma atti adottati
		if(showFirmaAttiAdottatiButton()) {
			firmaAttiAdottatiButton.show();			
			firmaAttiAdottatiButton.setDisabled(!isFirmaEnabled());
		} else {
			firmaAttiAdottatiButton.hide();
		}
		
		// Archivia seduta in storico
		if(isAbilArchiviaSeduta()) {			
			archiviaSedutaButton.show();
			archiviaSedutaButton.setDisabled(!isButtonEnabled());
		} else {
			archiviaSedutaButton.hide();
		}
		
		listaNomiCommissioniConvocateItem.setCanEdit(false);
		
		markForRedraw();
	}
	
	private void buildSaveAfterSalvaChiudiVerbale(Record recordToSave) {
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.executecustom("saveDiscussioneSeduta", recordToSave, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
					Record recordToGet = dsResponse.getData()[0];
					recordToGet.setAttribute("organoCollegiale", organoCollegiale);
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
					lGWTRestDataSource.call(recordToGet, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {							
							caricaDettaglio(object);	
						}
					});
				}
			}
		});
	}
	
	private void buildVersionaChiudiLavori(final Record recordToGet,String idDoc, String documentUri,String nomeFile, InfoFileRecord infoFile) {
		
		Record recordToVers = new Record();
		recordToVers.setAttribute("idFile", idDoc);
		recordToVers.setAttribute("uri", documentUri);
		recordToVers.setAttribute("nomeFile", nomeFile);		
		recordToVers.setAttribute("infoFile", infoFile);		
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.executecustom("versionaDocumento", recordToVers, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					caricaDettaglio(recordToGet);
				}
			}
		});
	}
	
	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		layoutTab.addMember(spacerLayout);
		layoutTab.setRedrawOnResize(true);
		return layoutTab;
	}
	
	private String getTitleTabDatiSeduta() {
		return "Seduta";
	}
	
	private String getTitleTabDatiPrimaConvocazione() {
		return AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? "Convocazione" : "1° Convocazione";
	}
	
	private String getTitleTabDatiSecondaConvocazione() {
		return "2° Convocazione";
	}
	
	private String getTitleDtPrimaConvocazione() {
		return AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? "Convocazione il" : "1a convocazione il";
	}
	
	public VLayout getLayoutTabDatiSeduta() {
		
		formDatiDiscussione = new DynamicForm();
		formDatiDiscussione.setTabID("HEADER");		
		formDatiDiscussione.setValuesManager(vm);
		formDatiDiscussione.setWidth("100%");
		formDatiDiscussione.setHeight("5");
		formDatiDiscussione.setPadding(5);
		formDatiDiscussione.setWrapItemTitles(false);
		formDatiDiscussione.setNumCols(10);
		formDatiDiscussione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		formDatiDiscussione.setValuesManager(vm);
		
		idSedutaItem = new HiddenItem("idSeduta");
		
		numeroItem = new TextItem("numero", setTitleAlign("N°", 100, false));
		numeroItem.setStartRow(true);
		numeroItem.setColSpan(1);
		numeroItem.setCanEdit(false);
		numeroItem.setWidth(140);
		
		operazioniEffettuateButton = new ImgButtonItem("operazioniEffettuate", "protocollazione/operazioniEffettuate.png",
				I18NUtil.getMessages().protocollazione_detail_operazioniEffettuateButton_prompt());
		operazioniEffettuateButton.setAlwaysEnabled(true);
		operazioniEffettuateButton.setColSpan(1);
		operazioniEffettuateButton.setEndRow(false);
		operazioniEffettuateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idSeduta = idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
				String title = "Operazioni sulla seduta di "+ organoCollegiale + " N° " + numeroItem.getValueAsString();
				if(dtPrimaConvocazioneItem.getValueAsDate() != null) {
					title += " del " + DateUtil.format(dtPrimaConvocazioneItem.getValueAsDate()).substring(0, 10);
				}
				new OperazioniEffettuateWindow(idSeduta, title);
			}
		});
		operazioniEffettuateButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String idSeduta = idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
				if(idSeduta!=null && !"".equals(idSeduta)) {
					return true;
				}
				return false;
			}
		});
		
		visualizzaContenutiFascicoloButton = new ImgButtonItem("visualizzaContenutiFascicoloButton", "menu/archivio.png"
				, "Mostra contenuti fascicolo");
		visualizzaContenutiFascicoloButton.setEndRow(false);
		visualizzaContenutiFascicoloButton.setColSpan(1);
		visualizzaContenutiFascicoloButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {						
				// Setto la radice come nodo di partenza predefinito
				Record lRecord = new Record();
				lRecord.setAttribute("idUdFolder", getIdFascicolo());
				lRecord.setAttribute("idNode", "/");
				lRecord.setAttribute("provenienza", "ORGANI_COLLEGIALI");
				ContenutiFascicoloPopup contenutiFascicoliPopup = new ContenutiFascicoloPopup(lRecord);
				contenutiFascicoliPopup.show();
			}
		});	
		visualizzaContenutiFascicoloButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
		
				if(getIdFascicolo() != null)
					return true;
				return false;
			}
		});

		// Commissioni convocate
		listaNomiCommissioniConvocateItem = new TextAreaItem("listaNomiCommissioniConvocate", "Commissioni");
		listaNomiCommissioniConvocateItem.setCanEdit(false);
		listaNomiCommissioniConvocateItem.setColSpan(8);
		listaNomiCommissioniConvocateItem.setStartRow(true);
		listaNomiCommissioniConvocateItem.setLength(4000);
		listaNomiCommissioniConvocateItem.setHeight(60);
		listaNomiCommissioniConvocateItem.setWidth(550);
		listaNomiCommissioniConvocateItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "COMMISSIONE".equalsIgnoreCase(organoCollegiale);
			}
		});
		
		dtPrimaConvocazioneItem = new DateTimeItem("dtPrimaConvocazione", getTitleDtPrimaConvocazione());
		dtPrimaConvocazioneItem.setCanEdit(false);
		dtPrimaConvocazioneItem.setStartRow(true);
		dtPrimaConvocazioneItem.setColSpan(2);
		
		luogoPrimaConvocazioneItem = new TextItem("luogoPrimaConvocazione", "Luogo");
		luogoPrimaConvocazioneItem.setWidth(250);
		luogoPrimaConvocazioneItem.setCanEdit(false);
		luogoPrimaConvocazioneItem.setStartRow(false);
		luogoPrimaConvocazioneItem.setEndRow(true);
		luogoPrimaConvocazioneItem.setColSpan(5);
		
		dtSecondaConvocazioneItem = new DateTimeItem("dtSecondaConvocazione", "2a convocazione il");
		dtSecondaConvocazioneItem.setStartRow(true);
		dtSecondaConvocazioneItem.setColSpan(2);
		dtSecondaConvocazioneItem.setCanEdit(false);
		dtSecondaConvocazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE");
			}
		}); 
		
		luogoSecondaConvocazioneItem = new TextItem("luogoSecondaConvocazione", "Luogo");
		luogoSecondaConvocazioneItem.setWidth(250);
		luogoSecondaConvocazioneItem.setStartRow(false);
		luogoSecondaConvocazioneItem.setColSpan(5);
		luogoSecondaConvocazioneItem.setCanEdit(false);
		luogoSecondaConvocazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE");
			}
		}); 
		
		desStatoSedutaItem = new TextItem("desStato", "Stato");
		desStatoSedutaItem.setColSpan(2);
		desStatoSedutaItem.setWidth(140);
		desStatoSedutaItem.setCanEdit(false);
		desStatoSedutaItem.setStartRow(true);
		
		disattivatoRiordinoAutomaticoItem = new CheckboxItem("disattivatoRiordinoAutomatico", "disattiva riordino automatico atti in OdG");
		disattivatoRiordinoAutomaticoItem.setColSpan(1);
		disattivatoRiordinoAutomaticoItem.setWidth(50);
		disattivatoRiordinoAutomaticoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return showRiordinoAutomatico();
			}
		});
		
		tipoSessioneItem = new TextItem("tipoSessione", setTitleAlign("Tipo sessione", 100, false));
		tipoSessioneItem.setColSpan(2);
		tipoSessioneItem.setWidth(140);
		tipoSessioneItem.setCanEdit(false);
		tipoSessioneItem.setStartRow(true);

		formDatiDiscussione.setItems(
				idSedutaItem,
				numeroItem,
				operazioniEffettuateButton,
				visualizzaContenutiFascicoloButton,
				listaNomiCommissioniConvocateItem,
				dtPrimaConvocazioneItem,luogoPrimaConvocazioneItem,
				dtSecondaConvocazioneItem,luogoSecondaConvocazioneItem,
				desStatoSedutaItem,
				disattivatoRiordinoAutomaticoItem,
				tipoSessioneItem
		);
		
		sectionDatiDiscussioneSeduta = new DetailSection("Dati seduta", true, true, false, formDatiDiscussione);
		sectionDatiDiscussioneSeduta.setHeight(5);
		
		formListaDatiDiscussione = new DynamicForm();
		formListaDatiDiscussione.setTabID("HEADER");		
		formListaDatiDiscussione.setValuesManager(vm);
		formListaDatiDiscussione.setWidth("100%");
		formListaDatiDiscussione.setHeight("100%");
		formListaDatiDiscussione.setPadding(5);
		formListaDatiDiscussione.setWrapItemTitles(false);
		formListaDatiDiscussione.setNumCols(10);
		formListaDatiDiscussione.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		formListaDatiDiscussione.setValuesManager(vm);
		
		esitiXtipoArgomentoItem = new HiddenItem("esitiXtipoArgomento"); 
		
		listaArgomentiOdgEliminatiItem = new HiddenItem("listaArgomentiOdgEliminati");
		
		listaDatiDiscussioneSedutaItem = new ListaDatiDiscussioneSedutaItem("listaArgomentiOdg", organoCollegiale, codCircoscrizione) {
			
			public RecordList getEsitiXtipoArgomento() {
				return esitiXtipoArgomentoItem.getValueAsRecordList();
			}
			
			@Override
			public String getIdSeduta() {
				return idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
			}
			
			@Override
			public String getStatoSeduta() {
				return statoSeduta != null ? statoSeduta : null;
			}
			
			@Override
			protected boolean showVistoSegretario() {
				return showVistoAttiAdottatiButton();		
			}
			
			@Override
			protected boolean showMultiselectButton() {
				if(isFirmaEnabled()) {
					return true;
				}
				return super.showMultiselectButton();
			}
			
			@Override
			public void onClickRemoveButton(ListGridRecord record) {
				
				onClickRemoveButton(record, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						RecordList recordList = formListaDatiDiscussione.getValueAsRecordList("listaArgomentiOdgEliminati");
						if( recordList == null ) {
							recordList = new RecordList();
						}
						recordList.add(object);
						listaArgomentiOdgEliminatiItem.setValue(recordList);
					}
				});
			}
			
			@Override
			protected GridMultiToolStripButton[] getGridMultiselectButtons() {
				List<GridMultiToolStripButton> gridMultiselectButtons = new ArrayList<GridMultiToolStripButton>();
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_CAMBIO_STATO_MASSIVO_IN_DISCUSSIONE_SEDUTA")
						&& !"COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
				 
					if (cambiaStatoMultiButton == null) {
						cambiaStatoMultiButton = new GridMultiToolStripButton("delibere/cambio_stato.png", grid, "Cambio stato", true) {
							
							@Override
							public boolean toShow() {
								return isStatoLavoriConclusi();
							}
	
							@Override
							public void doSomething() {
								int maxNumSelectRecord = -1;								
								if(AurigaLayout.getParametroDB("MAX_NRO_ATTI_CAMBIO_STATO_SEDUTA") != null && !"".equals(AurigaLayout.getParametroDB("MAX_NRO_ATTI_CAMBIO_STATO_SEDUTA"))) {	
									maxNumSelectRecord = Integer.parseInt(AurigaLayout.getParametroDB("MAX_NRO_ATTI_CAMBIO_STATO_SEDUTA"));
								}		
								if (maxNumSelectRecord > -1 && grid.getSelectedRecords() != null && grid.getSelectedRecords().length > maxNumSelectRecord) {
									Layout.addMessage(new MessageBean("E' possibile selezionare al massimo " + maxNumSelectRecord + " record per effettuare l'operazione richiesta", "", MessageType.ERROR));
									return;
								}
								boolean hasAllRecordWithStatoTestoInLavorazione = true;
								final RecordList listaAtti = new RecordList();
								for (int i = 0; i < grid.getSelectedRecords().length; i++) {
									listaAtti.add(grid.getSelectedRecords()[i]);
									String statoRevisioneTesto = grid.getSelectedRecords()[i].getAttribute("statoRevisioneTesto"); 
									if(statoRevisioneTesto == null || !statoRevisioneTesto.equalsIgnoreCase("testo_in_lavorazione")) {
										hasAllRecordWithStatoTestoInLavorazione = false;
									}
								}
								final boolean showAlertWithGeneraFileUnione = !hasAllRecordWithStatoTestoInLavorazione;
								new CambiaStatoPostDiscussionePopup() {
									
									@Override
									public void onClickOkButton(final Record object, final DSCallback callback) {
										if(showAlertWithGeneraFileUnione && object.getAttribute("flgGeneraFileUnione") != null && "1".equals(object.getAttribute("flgGeneraFileUnione"))) {
											SC.say("Il cambio stato selezionato verrà applicato solo agli atti con stato \"testo in lavorazione\".", new BooleanCallback() {
												
												@Override
												public void execute(Boolean value) {
													cambiaStatoAtti(listaAtti, object.getAttribute("statoUd"), object.getAttribute("flgGeneraFileUnione"));
												}
											});
										} else {
											cambiaStatoAtti(listaAtti, object.getAttribute("statoUd"), object.getAttribute("flgGeneraFileUnione"));
										}
										this.markForDestroy();
									}
								};
							}
						};
					}
					gridMultiselectButtons.add(cambiaStatoMultiButton);
				}
				
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_SCARICO_SCHEDE_SINTESI_ATTI_SEDUTA")) {
					 
					if (downloadSchedaSintesiMultiButton == null) {
						downloadSchedaSintesiMultiButton = new GridMultiToolStripButton("file/download_manager.png", grid, 
								I18NUtil.getMessages().convocazione_seduta_detail_downloadSchedaSintesiMultiButton_title(), true) {
							
							@Override
							public boolean toShow() {
								return true;
							}
	
							@Override
							public void doSomething() {
								
								final RecordList listaAtti = new RecordList();
								for (int i = 0; i < grid.getSelectedRecords().length; i++) {
									listaAtti.add(grid.getSelectedRecords()[i]);
								}
								Record record = new Record();
								record.setAttribute("listaRecord", listaAtti);
								
								final GWTRestDataSource convocazioneSedutaDS = new GWTRestDataSource("ConvocazioneSedutaDataSource");	
								convocazioneSedutaDS.setForceToShowPrompt(false);	
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().convocazione_seduta_detail_downloadSchedaSintesiMultiButton_richiesta_download(),"", MessageType.WARNING));
								convocazioneSedutaDS.executecustom("scaricaSchedaSintesi", record, new DSCallback() {

									public void execute(DSResponse response, Object rawData, DSRequest request) {
										
										if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Record recToDown = response.getData()[0];
											if(recToDown != null && recToDown.getAttributeAsString("uriUnioneSchedaSintesi") != null
													&& !"".equalsIgnoreCase(recToDown.getAttributeAsString("uriUnioneSchedaSintesi"))) {
												Record lRecord = new Record();
												String nomeFile = "Schede_Sintesi_seduta_" + organoCollegiale + "_n_" + numeroItem.getValueAsString();
												lRecord.setAttribute("displayFilename", nomeFile +".pdf");
												lRecord.setAttribute("uri", recToDown.getAttributeAsString("uriUnioneSchedaSintesi"));
												lRecord.setAttribute("sbustato", "false");
												lRecord.setAttribute("remoteUri", "false");
												DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
											} else {
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().convocazione_seduta_detail_downloadSchedaSintesiMultiButton_errore_download(),"", MessageType.ERROR));
											}
										} else {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().convocazione_seduta_detail_downloadSchedaSintesiMultiButton_errore_download(),"", MessageType.ERROR));
										} 										
									}
								});
							}
						};
					}
					gridMultiselectButtons.add(downloadSchedaSintesiMultiButton);
				}
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_SCARICO_ZIP_FILE_ATTI_SEDUTA")) {
					 
					if (downloadFileAttiMultiButton == null) {
						downloadFileAttiMultiButton = new GridMultiToolStripButton("buttons/download_zip.png", grid, 
								I18NUtil.getMessages().convocazione_seduta_detail_downloadFileAttiMultiButton_title(), true) {
							
							@Override
							public boolean toShow() {
								return true;
							}
	
							@Override
							public void doSomething() {
								
								final RecordList listaAtti = new RecordList();
								for (int i = 0; i < grid.getSelectedRecords().length; i++) {
									listaAtti.add(grid.getSelectedRecords()[i]);
								}
								
								final String idSeduta = getIdSeduta();
								OpzioniAttiDaScaricarePopup opzioniAttiDaScaricarePopup = new OpzioniAttiDaScaricarePopup() {
									
									@Override
									public void onClickOkButton(DSCallback callback) {
										
										Record record = new Record();
										record.setAttribute("listaArgomentiOdgXmlBean", listaAtti);
										record.setAttribute("flgInclusiPareri", _form.getValue("flgInclusiPareri"));
										record.setAttribute("flgInclAllegatiPI", _form.getValue("flgInclAllegatiPI"));
										record.setAttribute("flgIntXPubbl", _form.getValue("flgIntXPubbl"));
										GWTRestDataSource convocazioneSedutaDS = new GWTRestDataSource("ConvocazioneSedutaDataSource");
										convocazioneSedutaDS.addParam("idSeduta", idSeduta);
										convocazioneSedutaDS.setForceToShowPrompt(false);	
										convocazioneSedutaDS.executecustom("scaricaFileArgSedutaXDownload", record, new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Record recToDown = response.getData()[0];
													if(recToDown != null && recToDown.getAttributeAsString("uriUnioneFile") != null
															&& !"".equalsIgnoreCase(recToDown.getAttributeAsString("uriUnioneFile"))) {
														Record lRecord = new Record();
														String nomeFile = "Atti_seduta_" + organoCollegiale + "_n_" + numeroItem.getValueAsString();
														lRecord.setAttribute("displayFilename", nomeFile + ".zip");
														lRecord.setAttribute("uri", recToDown.getAttributeAsString("uriUnioneFile"));
														lRecord.setAttribute("sbustato", "false");
														lRecord.setAttribute("remoteUri", "false");
														DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
													} else {
														Layout.addMessage(new MessageBean(I18NUtil.getMessages().convocazione_seduta_detail_downloadFileAttiMultiButton_errore_download(),"", MessageType.ERROR));
													}
												}
											}
										});
										this.markForDestroy();
									}
								};
								opzioniAttiDaScaricarePopup.show();
							}
						};
					}
					gridMultiselectButtons.add(downloadFileAttiMultiButton);
				}
				if(isAbilTrasmettiAttiMulti()) {
					 
					if (trasmettiMultiButton == null) {
						trasmettiMultiButton = new GridMultiToolStripButton("delibere/trasmissione_atti_seduta.png", grid, 
								I18NUtil.getMessages().convocazione_seduta_detail_trasmettiileAttiMultiButton_title(), true) {
							
							@Override
							public boolean toShow() {
								return true;
							}
								
							@Override
							public void doSomething() {
								
								final RecordList listaAtti = new RecordList();
								for (int i = 0; i < grid.getSelectedRecords().length; i++) {
									listaAtti.add(grid.getSelectedRecords()[i]);
								}
								
								final String idSeduta = getIdSeduta();
								OpzioniTrasmissioneAttiPopup opzioniTrasmissioneAttiPopup = new OpzioniTrasmissioneAttiPopup() {
									
									@Override
									public void onClickOkButton(DSCallback callback) {
										
										Record record = new Record();
										record.setAttribute("listaArgomentiOdgXmlBean", listaAtti);
										record.setAttribute("flgInclusiPareri", _vm.getValue("flgInclusiPareri"));
										record.setAttribute("flgInclAllegatiPI", _vm.getValue("flgInclAllegatiPI"));
										record.setAttribute("flgIntXPubbl", _vm.getValue("flgIntXPubbl"));
										record.setAttribute("flgInvioAttiTrasmessi", _vm.getValue("flgInvioAttiTrasmessi"));								
										record.setAttribute("mittente", _vm.getValue("mittente"));
										record.setAttribute("destinatari", _vm.getValue("destinatari"));
										record.setAttribute("destinatariCC", _vm.getValue("destinatariCC"));
										record.setAttribute("oggetto", _vm.getValue("oggetto"));
										record.setAttribute("body", _vm.getValue("body"));
										
										GWTRestDataSource convocazioneSedutaDS = new GWTRestDataSource("ConvocazioneSedutaDataSource");
										convocazioneSedutaDS.addParam("idSeduta", idSeduta);	
										convocazioneSedutaDS.executecustom("trasmissioneAttiSelezionati", record, new DSCallback() {

											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {											
													Layout.addMessage(new MessageBean("Richiesta acquisita con successo. La preparazione e invio delle mail avverrà in qualche minuto, potrai verificare l'avvenuto invio aggiornando la lista atti e controllando la colonna Trasmissione/i atto"
															,"", MessageType.INFO));
												}
											}
										});
										this.markForDestroy();
										
									}

									@Override
									public String getOrganoCollegiale() {
										return organoCollegiale;
									}
								};
								opzioniTrasmissioneAttiPopup.show();
							}
						};
					}
					gridMultiselectButtons.add(trasmettiMultiButton);
				}
				
				return gridMultiselectButtons.toArray(new GridMultiToolStripButton[gridMultiselectButtons.size()]);
			}
			
		};
		listaDatiDiscussioneSedutaItem.setStartRow(true);
		listaDatiDiscussioneSedutaItem.setShowTitle(false);
		listaDatiDiscussioneSedutaItem.setHeight("95%");
		
		formListaDatiDiscussione.setItems(esitiXtipoArgomentoItem, listaArgomentiOdgEliminatiItem, listaDatiDiscussioneSedutaItem);
		
		sectionListaDatiDiscussioneSeduta = new DetailSection("Proposte di delibere e altri argomenti in discussione", false, true, false, formListaDatiDiscussione);
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(sectionDatiDiscussioneSeduta);
		lVLayout.addMember(sectionListaDatiDiscussioneSeduta);
				
		return lVLayout;
	}
	
	private Boolean isAbilTrasmettiAttiMulti() {
		if(codCircoscrizione != null && !"".equalsIgnoreCase(codCircoscrizione)) {
			if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_GIUNTA_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_CONSIGLIO_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_COMMISSIONE_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_COMITATO_GESTIONE_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_ORGANISMO_PATERNARIATO_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_CONFERENZA_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_GIUNTA")) {
					return true;
				} else {
					return false;
				}
			} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_CONSIGLIO")) {
					return true;
				} else {
					return false;
				}
			} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_COMMISSIONE")) {
					return true;
				} else {
					return false;
				}
			} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_COMITATO_GESTIONE")) {
					return true;
				} else {
					return false;
				}
			} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_ORGANISMO_PATERNARIATO")) {
					return true;
				} else {
					return false;
				}
			} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_TRASM_ATTI_SEDUTA_CONFERENZA")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
	
	private Boolean showRiordinoAutomatico() {
		if(codCircoscrizione != null && !"".equalsIgnoreCase(codCircoscrizione)) {
			if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_GIUNTA_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_CONSIGLIO_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_COMMISSIONE_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_COMITATO_GESTIONE_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_ORGANISMO_PATERNARIATO_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_CONFERENZA_CIRC")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_GIUNTA")) {
					return true;
				} else {
					return false;
				}
			} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_CONSIGLIO")) {
					return true;
				} else {
					return false;
				}
			} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_COMMISSIONE")) {
					return true;
				} else {
					return false;
				}
			} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_COMITATO_GESTIONE")) {
					return true;
				} else {
					return false;
				}
			} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_ORGANISMO_PATERNARIATO")) {
					return true;
				} else {
					return false;
				}
			} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
				if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_RIORDINO_AUTOMATICO_SEDUTA_CONFERENZA")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
	
	public void cambiaStatoAtti(final RecordList listaAtti, String statoUd, String flgGeneraFileUnione) {
		
		Layout.showWaitPopup("Cambio stato atti in corso: potrebbe richiedere qualche secondo. Attendere…");
		
		Record record = new Record();
		record.setAttribute("listaRecord", listaAtti);
		record.setAttribute("organoCollegiale", organoCollegiale);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("DiscussioneSedutaDataSource");
		lGwtRestDataSource.extraparam.put("stato", statoUd);
		lGwtRestDataSource.extraparam.put("flgGeneraFileUnione", flgGeneraFileUnione);		
		lGwtRestDataSource.executecustom("cambiaStatoAtti", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				massiveOperationCallback(response, listaAtti, "idUd", "Cambio stato effettuato con successo",
						"Tutti i record selezionati per il cambio stato sono andati in errore!",
						"Alcuni dei record selezionati per il cambio stato sono andati in errore!", null);
			}
		});
	}
	
	public void massiveOperationCallback(DSResponse response, RecordList lista, String pkField, String successMessage,
			String completeErrorMessage, String partialErrorMessage, DSCallback callback) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			Map errorMessages = data.getAttributeAsMap("errorMessages");
			String errorMsg = null;
			RecordList listaErrori = new RecordList();
			if (errorMessages != null && errorMessages.size() > 0) {
				if (lista.getLength() > errorMessages.size()) {
					errorMsg = partialErrorMessage != null ? partialErrorMessage : "";
				} else {
					errorMsg = completeErrorMessage != null ? completeErrorMessage : "";
				}
				for (int i = 0; i < lista.getLength(); i++) {
					Record record = lista.get(i);
					if (errorMessages.get(record.getAttribute(pkField)) != null) {
						Record recordErrore = new Record();
						recordErrore.setAttribute("idError", getEstremiAttoFromRecord(record));						
						errorMsg += "<br/>" + getEstremiAttoFromRecord(record) + ": " + errorMessages.get(record.getAttribute(pkField));		
						recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute(pkField)));
						listaErrori.add(recordErrore);
					}
				}
			}						
			Layout.hideWaitPopup();
			if(listaErrori != null && listaErrori.getLength() > 0) {
				ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "Estremi", listaErrori, lista.getLength(), 600, 300);
				errorePopup.show();
			} else if (errorMsg != null && !"".equals(errorMsg)) {
				Layout.addMessage(new MessageBean(errorMsg, "", MessageType.ERROR));
			} else {
				if (successMessage != null && !"".equalsIgnoreCase(successMessage)) {
					Layout.addMessage(new MessageBean(successMessage, "", MessageType.INFO));
				}
				if (callback != null) {
					callback.execute(new DSResponse(), null, new DSRequest());
				}
			}
			Record recordToGet = getRecordToSave();
			recordToGet.setAttribute("organoCollegiale", organoCollegiale);
			GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
			lGWTRestDataSource.call(recordToGet, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record object) {
					caricaDettaglio(object);						
				}
			});
		} else {
			Layout.hideWaitPopup();
		}
	}
	
	private String getEstremiAttoFromRecord(Record record){
		return record.getAttribute("tipo") + " " + record.getAttribute("codTipo") + " " + record.getAttribute("estremiPropostaUD");
	}
	
	public VLayout getLayoutTabDatiPrimaConvocazione() {
		
		formDatiPrimaConv = new DynamicForm();
		formDatiPrimaConv.setTabID("PRIMA_CONV");		
		formDatiPrimaConv.setValuesManager(vm);
		formDatiPrimaConv.setWidth("100%");
		formDatiPrimaConv.setHeight("5");
		formDatiPrimaConv.setPadding(5);
		formDatiPrimaConv.setWrapItemTitles(false);
		formDatiPrimaConv.setNumCols(12);
		formDatiPrimaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		dtInizioLavoriPrimaConvItem = new DateTimeItem("datiDiscPrimaConvDtInizioLavori", "Inizio lavori alle");
		dtInizioLavoriPrimaConvItem.setStartRow(false);
		dtInizioLavoriPrimaConvItem.setColSpan(1);
		dtInizioLavoriPrimaConvItem.setWidth(150);
		dtInizioLavoriPrimaConvItem.setCanEdit(isAbilPrimaConvocazione() || isStatoLavoriConclusi());
		
		dtFineLavoriPrimaConvItem = new DateTimeItem("datiDiscPrimaConvDtFineLavori", "Fine lavori alle");
		dtFineLavoriPrimaConvItem.setStartRow(false);
		dtFineLavoriPrimaConvItem.setColSpan(1);
		dtFineLavoriPrimaConvItem.setWidth(150);
		dtFineLavoriPrimaConvItem.setCanEdit(isAbilPrimaConvocazione() || isStatoLavoriConclusi());
		
//		fileVerbalePrimaConvItem = new HiddenItem("fileVerbalePrimaConvocazione");
		verbaleInfoPrimaConvocazioneItem = new HiddenItem("verbaleInfoPrimaConvocazione");
		
		formDatiPrimaConv.setItems(dtInizioLavoriPrimaConvItem, dtFineLavoriPrimaConvItem/*, fileVerbalePrimaConvItem*/, verbaleInfoPrimaConvocazioneItem);
		
		sectionDatiPrimaConv = new DetailSection("Dati convocazione", true, true, false, formDatiPrimaConv);
		
		formListaDatiPrimaConv = new DynamicForm();
		formListaDatiPrimaConv.setTabID("PRIMA_CONV");		
		formListaDatiPrimaConv.setValuesManager(vm);
		formListaDatiPrimaConv.setWidth("100%");
		formListaDatiPrimaConv.setHeight("5");
		formListaDatiPrimaConv.setPadding(5);
		formListaDatiPrimaConv.setWrapItemTitles(false);
		formListaDatiPrimaConv.setNumCols(12);
		formListaDatiPrimaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		sectionListaDatiPrimaConv = new DetailSection("Presenze all'appello iniziale", false, true, false, formListaDatiPrimaConv);
		
		listaDatiDiscussionePrimaConvItem = new ListaDatiDiscussionePrimaConvocazioneItem("listaDatiDiscPrimaConvPresenzeAppelloIniziale", organoCollegiale, isAbilPrimaConvocazione())		{
			
			@Override
			public String getIdSeduta() {
				return idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
			}
		};  
		listaDatiDiscussionePrimaConvItem.setStartRow(true);
		listaDatiDiscussionePrimaConvItem.setShowTitle(false);
		listaDatiDiscussionePrimaConvItem.setHeight(245);
		
		formListaDatiPrimaConv.setItems(listaDatiDiscussionePrimaConvItem);

		buildVerbalePrimaConvSection();
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(sectionDatiPrimaConv);
		lVLayout.addMember(sectionListaDatiPrimaConv);
		lVLayout.addMember(verbalePrimaConvSection);
				
		return lVLayout;
	}
	
	public VLayout getLayoutTabDatiSecondaConvocazione() {
		
		formDatiSecondaConv = new DynamicForm();
		formDatiSecondaConv.setTabID("SECONDA_CONV");		
		formDatiSecondaConv.setValuesManager(vm);
		formDatiSecondaConv.setWidth("100%");
		formDatiSecondaConv.setHeight("5");
		formDatiSecondaConv.setPadding(5);
		formDatiSecondaConv.setWrapItemTitles(false);
		formDatiSecondaConv.setNumCols(12);
		formDatiSecondaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		dtInizioLavoriSecondaConvItem = new DateTimeItem("datiDiscSecondaConvDtInizioLavori", "Inizio lavori alle");
		dtInizioLavoriSecondaConvItem.setStartRow(false);
		dtInizioLavoriSecondaConvItem.setColSpan(1);
		dtInizioLavoriSecondaConvItem.setWidth(150);
		dtInizioLavoriSecondaConvItem.setCanEdit((isAbilSecondaConvocazione() || isStatoLavoriConclusi()) && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE"));
		
		dtFineLavoriSecondaConvItem = new DateTimeItem("datiDiscSecondaConvDtFineLavori", "Fine lavori alle");
		dtFineLavoriSecondaConvItem.setStartRow(false);
		dtFineLavoriSecondaConvItem.setColSpan(1);
		dtFineLavoriSecondaConvItem.setWidth(150);
		dtFineLavoriSecondaConvItem.setCanEdit((isAbilSecondaConvocazione() || isStatoLavoriConclusi()) && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE"));
		
		verbaleInfoSecondaConvocazioneItem = new HiddenItem("verbaleInfoSecondaConvocazione");
		
		formDatiSecondaConv.setItems(dtInizioLavoriSecondaConvItem, dtFineLavoriSecondaConvItem, verbaleInfoSecondaConvocazioneItem);
		
		sectionDatiSecondaConv = new DetailSection("Dati convocazione", true, true, false, formDatiSecondaConv);
		
		formListaDatiSecondaConv = new DynamicForm();
		formListaDatiSecondaConv.setTabID("SECONDA_CONV");		
		formListaDatiSecondaConv.setValuesManager(vm);
		formListaDatiSecondaConv.setWidth("100%");
		formListaDatiSecondaConv.setHeight("5");
		formListaDatiSecondaConv.setPadding(5);
		formListaDatiSecondaConv.setWrapItemTitles(false);
		formListaDatiSecondaConv.setNumCols(12);
		formListaDatiSecondaConv.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		listaDatiDiscussioneSecondaConvItem = new ListaDatiDiscussioneSecondaConvocazioneItem("listaDatiDiscSecondaConvPresenzeAppelloIniziale", organoCollegiale, isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE"))		{
			
			@Override
			public String getIdSeduta() {
				return idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
			}
		};  
		listaDatiDiscussioneSecondaConvItem.setStartRow(true);
		listaDatiDiscussioneSecondaConvItem.setShowTitle(false);
		listaDatiDiscussioneSecondaConvItem.setHeight(245);
		
		formListaDatiSecondaConv.setItems(listaDatiDiscussioneSecondaConvItem);
		
		sectionListaDatiSecondaConv = new DetailSection("Presenze all'appello iniziale", false, true, false, formListaDatiSecondaConv);

		buildVerbaleSecondaConvSection();
		
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(100);
		lVLayout.addMember(sectionDatiSecondaConv);
		lVLayout.addMember(sectionListaDatiSecondaConv);
		lVLayout.addMember(verbaleSecondaConvSection);
				
		return lVLayout;
	}
	
	
	private void buildVerbalePrimaConvSection() {
		
		formVerbalePrimaConv = new DynamicForm();
		formVerbalePrimaConv.setTabID("PRIMA_CONV");		
		formVerbalePrimaConv.setValuesManager(vm);
		formVerbalePrimaConv.setWidth("100%");
		formVerbalePrimaConv.setHeight("5");
		formVerbalePrimaConv.setPadding(5);
		formVerbalePrimaConv.setNumCols(1);
		formVerbalePrimaConv.setWrapItemTitles(false);
		
		SpacerItem spacer1 = new SpacerItem();
		spacer1.setColSpan(1);
		spacer1.setWidth(50);
		spacer1.setStartRow(true);
		
		editorVerbalePrimaConvItem = new CKEditorItem("datiDiscPrimaConvEditorVerbale", 4000, "restricted");
		editorVerbalePrimaConvItem.setShowTitle(false);
		editorVerbalePrimaConvItem.setColSpan(20);
		editorVerbalePrimaConvItem.setWidth("100%");
		editorVerbalePrimaConvItem.setCanEdit(isAbilPrimaConvocazione());
		
		formVerbalePrimaConv.setItems(spacer1,editorVerbalePrimaConvItem);
		verbalePrimaConvSection = new DetailSection("Verbale", true, true, false, formVerbalePrimaConv);
	}
	
	private void buildVerbaleSecondaConvSection() {
		
		formVerbaleSecondaConv = new DynamicForm();
		formVerbaleSecondaConv.setTabID("SECONDA_CONV");		
		formVerbaleSecondaConv.setValuesManager(vm);
		formVerbaleSecondaConv.setWidth("100%");
		formVerbaleSecondaConv.setHeight("5");
		formVerbaleSecondaConv.setPadding(5);
		formVerbaleSecondaConv.setNumCols(1);
		formVerbaleSecondaConv.setWrapItemTitles(false);
		
		SpacerItem spacer1 = new SpacerItem();
		spacer1.setColSpan(1);
		spacer1.setWidth(50);
		spacer1.setStartRow(true);
		
		editorVerbaleSecondaConvItem = new CKEditorItem("datiDiscSecondaConvEditorVerbale", 4000, "restricted");
		editorVerbaleSecondaConvItem.setShowTitle(false);
		editorVerbaleSecondaConvItem.setColSpan(20);
		editorVerbaleSecondaConvItem.setWidth("100%");
		editorVerbaleSecondaConvItem.setCanEdit(isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE"));
		
		formVerbaleSecondaConv.setItems(spacer1,editorVerbaleSecondaConvItem);
		verbaleSecondaConvSection = new DetailSection("Verbale", true, true, false, formVerbaleSecondaConv);
	}
	
	private Boolean isAbilPrimaConvocazione() {
		Boolean isCanEdit = false;
		if(recordCommissione != null && recordCommissione.getAttribute("stato") != null
				&& prima_convocazione.equalsIgnoreCase(recordCommissione.getAttribute("stato"))) {
			isCanEdit = true;
		}
		return isCanEdit;
	}
	
	private boolean isAbilSecondaConvocazione() {
		Boolean isCanEdit = false;
		if(recordCommissione != null && recordCommissione.getAttribute("stato") != null) {
			if(prima_convocazione_deserta.equalsIgnoreCase(recordCommissione.getAttribute("stato")) ||
			   seconda_convocazione.equalsIgnoreCase(recordCommissione.getAttribute("stato")) ) {
					isCanEdit = true;	
			}
		}
		return isCanEdit;
	}
	
	public void clickPreviewFile() {
		
		final Record detailRecord = getRecordToSave();
		final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		final String display = odgInfo.getAttributeAsString("displayFilename");
		final String uri = odgInfo.getAttributeAsString("uri");
		final Boolean remoteUri = false;
		final InfoFileRecord info = new InfoFileRecord(odgInfo);
		if (info != null && info.getMimetype() != null && info.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
			if (info != null && info.isFirmato() && info.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {

					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(display, uri, remoteUri, info);
					}
				}
			});
		} else {
			preview(display, uri, remoteUri, info);
		}
	}
	
	public void preview(final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, null, false, false);
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	private void generaVisualizzaAnteprimaOdg(Record detailRecord){
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("DiscussioneSedutaDataSource");
		lGWTRestDataSource.executecustom("generaAnteprimaOdg", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordFileGenerato = dsResponse.getData()[0];
					String uri = recordFileGenerato.getAttributeAsString("uri");
					InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileGenerato.getAttributeAsRecord("infoFile").getJsObj()));
					String nomeFile = recordFileGenerato.getAttributeAsString("nomeFile");
					preview(nomeFile, uri, false, infoFile);
				}
			}
		});
	}
	
	private String getIdFascicolo() {
		final Record detailRecord = getRecordToSave();
		final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		if(odgInfo != null && odgInfo.getAttributeAsString("idFascicolo") != null &&
				!"".equalsIgnoreCase(odgInfo.getAttributeAsString("idFascicolo"))) {
			return odgInfo.getAttributeAsString("idFascicolo");
		}
		return null;
	}
	
	public Record getRecordToSave() {
		
		Record lRecordToSave = new Record(getValuesManager().getValues());
		
		lRecordToSave.setAttribute("organoCollegiale", organoCollegiale);
		
		// 1 TAB
		addFormValues(lRecordToSave, formDatiDiscussione);	
		addFormValues(lRecordToSave, formListaDatiDiscussione);	
		
		// 2 TAB
		addFormValues(lRecordToSave, formDatiPrimaConv); 
		addFormValues(lRecordToSave, formListaDatiPrimaConv); 
		addFormValues(lRecordToSave, formVerbalePrimaConv);
		
		// 3 TAB
		addFormValues(lRecordToSave, formDatiSecondaConv);
		addFormValues(lRecordToSave, formListaDatiSecondaConv);
		addFormValues(lRecordToSave, formVerbaleSecondaConv);
		
		return lRecordToSave;
	}
	
	public boolean validateChiusuraLavori() {
		clearErrors();		
		boolean valid = true;
		if(isAbilPrimaConvocazione()) {
			if(dtFineLavoriPrimaConvItem.getValueAsDate() == null) {
				formDatiPrimaConv.setFieldErrors("datiDiscPrimaConvDtFineLavori", "Campo obbligatorio per chiusura lavori");
				valid = false;
			}
		}
		if(isAbilSecondaConvocazione() && !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE")) {
			if(dtFineLavoriSecondaConvItem.getValueAsDate() == null) {
				formDatiSecondaConv.setFieldErrors("datiDiscSecondaConvDtFineLavori", "Campo obbligatorio per chiusura lavori");
				valid = false;
			}
		}
		if (valid) {
			return super.validate();
		} else {
			showTabErrors();
			reopenAllSections();	
			return false;
		}
	}
	
	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}	
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}
	
}