/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
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
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.FirmaUtility.FirmaCallback;
import it.eng.auriga.ui.module.layout.client.archivio.ContenutiFascicoloPopup;
import it.eng.auriga.ui.module.layout.client.archivio.OpzioniAttiDaScaricarePopup;
import it.eng.auriga.ui.module.layout.client.archivio.OpzioniTrasmissioneAttiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.NuovoMessaggioWindow;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.OperazioniEffettuateWindow;
import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewWindowWithCallback;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.common.GridItem.GridMultiToolStripButton;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;


/**
 * 
 * @author DANCRIST
 *
 */

public class ConvocazioneSedutaDetail extends CustomDetail {
		
	private TabSet tabSet;
	
	private HiddenItem odgInfoItem;
	private HiddenItem idSedutaItem;
	private HiddenItem convocazioneInfoItem;
	private HiddenItem listaCommissioniItem;
	
	private Tab tabDatiSeduta;
	private VLayout layoutTabDatiSeduta;
	private DetailSection sectionDatiSeduta;
	private DynamicForm formDatiSeduta;
	private DynamicForm formListaDatiSeduta;
	private TextItem numeroItem;
	private ImgButtonItem operazioniEffettuateButton;
	private ImgButtonItem visualizzaContenutiFascicoloButton;
	private DateTimeItem dtPrimaConvocazioneItem;
	private TextItem luogoPrimaConvocazioneItem;
	private DateTimeItem dtSecondaConvocazioneItem;
	private TextItem luogoSecondaConvocazioneItem;
	private TextItem desStatoSedutaItem;
	private CheckboxItem disattivatoRiordinoAutomaticoItem;
	private SelectItem tipoSessioneItem;
	private TextAreaItem listaNomiCommissioniConvocateItem;
	private HiddenItem listaIdCommissioniConvocateItem;
	
	private ImgButtonItem modificaListaNomiCommissioniConvocateButton;

	private DetailSection sectionListaDatiSeduta;
	private HiddenItem listaArgomentiOdgEliminatiItem;
	private ListaDatiConvocazioneSedutaItem listaDatiConvocazioneSedutaItem;
	private GridMultiToolStripButton downloadSchedaSintesiMultiButton;
	private GridMultiToolStripButton downloadFileAttiMultiButton;
	private GridMultiToolStripButton trasmettiMultiButton;
	
	private Tab tabDatiPartecipanti;
	private VLayout layoutTabDatiPartecipanti;
	private DynamicForm formDatiPartecipanti;
	private ListaDatiPartecipantiSedutaItem listaDatiPartecipantiSedutaItem;
	
	private ToolStrip detailToolStrip;
	
	private DetailToolStripButton salvaButton;
	private DetailToolStripButton annullaSedutaButton;
	private DetailToolStripButton convocazioneButton;
	private DetailToolStripButton odgButton;
	private DetailToolStripButton invioEmailButton;

	private Record recordConvocazione;
	private String statoSeduta;
	private String organoCollegiale;
	private String codCircoscrizione;
	private String listaCommissioni;
	
	private static final long MILLISECONDS_IN_SECOND = 1000l;
	private static final long SECONDS_IN_MINUTE = 60l;
	
	public ConvocazioneSedutaDetail(String nomeEntita, Record recordConvocazione, String organoCollegiale) {
		
		super(nomeEntita);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		this.recordConvocazione = recordConvocazione;
		
		this.organoCollegiale = organoCollegiale;
		
		this.codCircoscrizione = recordConvocazione != null && recordConvocazione.getAttributeAsRecord("odgInfo") != null &&
				recordConvocazione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") != null ?
				recordConvocazione.getAttributeAsRecord("odgInfo").getAttributeAsString("codCircoscrizione") : null;
				
		this.statoSeduta = recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null ?
				recordConvocazione.getAttributeAsString("stato") : null;
		
		this.listaCommissioni = recordConvocazione != null ? recordConvocazione.getAttribute("listaCommissioni") : null;
			
		createTabSet();
		createDetailToolStrip();

		VLayout mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		mainLayout.addMember(tabSet);
		mainLayout.addMember(detailToolStrip);
		
		setMembers(mainLayout);	
	
		setCanEdit(!isOdGChiuso());
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
		
		/**
		 *  Tab Seduta
		 */

		tabDatiSeduta = new Tab("<b>" + getTitleTabDatiSeduta() + "</b>");
		tabDatiSeduta.setAttribute("tabID", "HEADER");
		tabDatiSeduta.setPrompt(getTitleTabDatiSeduta());

		VLayout lVLayoutSpacerSeduta = new VLayout();
		lVLayoutSpacerSeduta.setWidth100();
		lVLayoutSpacerSeduta.setHeight(10);

		layoutTabDatiSeduta = createLayoutTab(getLayoutTabDatiSeduta(), lVLayoutSpacerSeduta);

		// Aggiungo i layout ai tab
		tabDatiSeduta.setPane(layoutTabDatiSeduta);

		tabSet.addTab(tabDatiSeduta);
		
		/**
		 *  Tab Partecipanti
		 */
		
		tabDatiPartecipanti = new Tab("<b>" + getTitleTabDatiPartecipanti() + "</b>");
		tabDatiPartecipanti.setAttribute("tabID", "PARTECIPANTI");
		tabDatiPartecipanti.setPrompt(getTitleTabDatiPartecipanti());

		layoutTabDatiPartecipanti = createLayoutTab(getLayoutTabDatiPartecipanti(), null);

		// Aggiungo i layout ai tab
		tabDatiPartecipanti.setPane(layoutTabDatiPartecipanti);
		
		tabSet.addTab(tabDatiPartecipanti);
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
		detailToolStrip.addButton(convocazioneButton);
		detailToolStrip.addButton(odgButton);
		detailToolStrip.addButton(invioEmailButton);

	}
	
	private void createButtons() {
		
		salvaButton = new DetailToolStripButton("Salva", "buttons/save.png");
		salvaButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if(validate()) {
					if(isValidateDtPrimaConvocazione()) {
						clickSaveButton();
					} else {
						SC.ask("Attenzione: la data e ora di convocazione è già passata. La seduta quindi sarà consultabile solo dalla funzione " +
								"sedute in discussione. Confermi di voler salvare ?", new BooleanCallback() {
									
									@Override
									public void execute(Boolean value) {
										if(value) {
											clickSaveButton();
										}								
									}
								});
					}
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
		
		convocazioneButton = new DetailToolStripButton("Convocazione", "richiesteAccessoAtti/azioni/setAppuntamento.png");
		convocazioneButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				Menu menuConvocazione = new Menu();
				
				MenuItem generaConvocazioneMenuItem = new MenuItem("Genera", "protocollazione/generaDaModello.png");
				generaConvocazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						if(validate()) {
							buildGeneraConvocazione();
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
						}
					}
				});
				menuConvocazione.addItem(generaConvocazioneMenuItem);
				
				if(isAbilVisualizzaConvocazione()) {
					MenuItem visualizzaConvocazioneMenuItem = new MenuItem("Visualizza", "file/preview.png");
					visualizzaConvocazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(validate()) {
								buildVisualizzaConvocazione();
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
							}
						}
					});
					menuConvocazione.addItem(visualizzaConvocazioneMenuItem);
				}
				
				if(isAbilToFirmaConvocazione() && isAbilVisualizzaConvocazione()) {
					MenuItem firmaConvocazioneMenuItem = new MenuItem("Firma", "file/mini_sign.png");
					firmaConvocazioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(validate()) {
								buildFirmaConvocazione();
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
							}
						}
					});
					menuConvocazione.addItem(firmaConvocazioneMenuItem);
				}
				
				menuConvocazione.showContextMenu();
			}
		});
		
		odgButton = new DetailToolStripButton("OdG", "delibere/ordinedelgiorno.png");
		odgButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				final Record detailRecord = getRecordToSave();
				final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
				
				Menu menuOdG = new Menu();
				
				if(isAbilToConsolidaOdG()) { 
					MenuItem consolidaOdGMenuItem = new MenuItem("Consolida", "ok.png");
					
					if(odgInfo.getAttribute("uriModello") != null && !"".equals(odgInfo.getAttribute("uriModello"))) {
						if(odgInfo.getAttribute("modalitaOdG") != null) {
							if("solo_completo".equalsIgnoreCase(odgInfo.getAttribute("modalitaOdG"))) {
								consolidaOdGMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
									
									@Override
									public void onClick(MenuItemClickEvent event) {
										if(validate()) {
											consolidaOdG(detailRecord, "completo");
										} else {
											Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
										}
									}
								});
							} else if("anche_supplemento".equalsIgnoreCase(odgInfo.getAttribute("modalitaOdG"))) {
								RecordList listaOdGConsolidati = odgInfo.getAttributeAsRecordList("listaOdGConsolidati");					
								if(listaOdGConsolidati != null && !listaOdGConsolidati.isEmpty()) {
									// Se ho già un OdG consolidato			
									consolidaOdGMenuItem.setSubmenu(buildMenuConsolidaOdG(detailRecord, false));
								} else {
									consolidaOdGMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
										
										@Override
										public void onClick(MenuItemClickEvent event) {
											if(validate()) {
												consolidaOdG(detailRecord, "completo");
											} else {
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
											}
										}
									});
								}
							} else if("anche_supplemento_urgente".equalsIgnoreCase(odgInfo.getAttribute("modalitaOdG"))) {
								RecordList listaOdGConsolidati = odgInfo.getAttributeAsRecordList("listaOdGConsolidati");					
								if(listaOdGConsolidati != null && !listaOdGConsolidati.isEmpty()) {
									// Se ho già un OdG consolidato			
									consolidaOdGMenuItem.setSubmenu(buildMenuConsolidaOdG(detailRecord, true));
								} else {
									consolidaOdGMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
										
										@Override
										public void onClick(MenuItemClickEvent event) {
											if(validate()) {
												consolidaOdG(detailRecord, "completo");
											} else {
												Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
											}
										}
									});
								}
							}
						}
					}
					menuOdG.addItem(consolidaOdGMenuItem);
				}
				
				if(isAbilToFirmaOdG()) {
					MenuItem firmaOdGMenuItem = new MenuItem("Firma", "file/mini_sign.png");
					firmaOdGMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(validate()) {
								firmaOdG();
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
							}
						}
					});
					menuOdG.addItem(firmaOdGMenuItem);		
				}
				
				if(isAbilToRichPubblOdG()) {
					MenuItem richPubblOdGMenuItem = new MenuItem("Richiedi pubblicazione", "buttons/richiesta_pubblicazione.png");
					richPubblOdGMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(validate()) {								
								SC.ask("Sei sicuro di voler mandare in pubblicazione questa versione dell'OdG?", new BooleanCallback() {
									
									@Override
									public void execute(Boolean value) {
										if (value) {
											Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
								    		odgInfo.setAttribute("flgRichPubblicazione", value);	
								    		odgInfo.setAttribute("azioneDaFare", "richiesta_pubblicazione");			    		
								    		Record recordToSave = getRecordToSave();
											recordToSave.setAttribute("organoCollegiale", organoCollegiale);												
											recordToSave.setAttribute("odgInfo", odgInfo);										
											saveAndReload(recordToSave);								
										}
									}
								});
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
							}
						}
					});
					menuOdG.addItem(richPubblOdGMenuItem);
				}

				MenuItem visualizzaOdGMenuItem = new MenuItem("Visualizza", "file/preview.png");
				String uri = odgInfo.getAttributeAsString("uri");				
				if (uri != null && !"".equalsIgnoreCase(uri)) {
					// Se ho già un OdG consolidato			
					visualizzaOdGMenuItem.setSubmenu(buildMenuAnteprimaOdG(detailRecord));
				} else {
					visualizzaOdGMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							anteprimaOdG(detailRecord, "completo");												
						}
					});
				}
				menuOdG.addItem(visualizzaOdGMenuItem);
				
				if(isAbilToListaOdG()) {
					MenuItem listaOdGMenuItem = new MenuItem("Lista", "file/version.png");
					listaOdGMenuItem.setSubmenu(buildMenuListaOdG(detailRecord));
					menuOdG.addItem(listaOdGMenuItem);
				}
				
				if(isAbilToChiudiOdG()) { 
					MenuItem chiudiOdGMenuItem = new MenuItem("Chiudi", "archivio/archiviaConcludi.png");
					chiudiOdGMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							if(validate()) {
								Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
								odgInfo.setAttribute("azioneDaFare", "chiusura");
								Record recordToSave = getRecordToSave();
								recordToSave.setAttribute("organoCollegiale", organoCollegiale);
								recordToSave.setAttribute("stato", "OdG_chiuso");		
								recordToSave.setAttribute("odgInfo", odgInfo);
								saveAndReload(recordToSave);
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
							}
						}
					});	
					menuOdG.addItem(chiudiOdGMenuItem);		
				}
				
				menuOdG.showContextMenu();
			}
		});
		
		invioEmailButton = new DetailToolStripButton("Invia email", "delibere/sendmail_sedute.png");
		invioEmailButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				if(validate()) {										
					if(isAbilToInviaConvocazione() || isAbilToInviaOdG()) {					
						final Menu invioMenu = new Menu();						
						if(isAbilToInviaConvocazione()) {
							// Invio convocazione
							MenuItem invioConvocazioneItem = new MenuItem("Convocazione");
							invioConvocazioneItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
								@Override
								public void onClick(MenuItemClickEvent event) {							
									clickInviaEmailConvocazione();
								}
							});
							invioMenu.addItem(invioConvocazioneItem);
						}						
						if(isAbilToInviaOdG()) { 
							// Invio OdG
							MenuItem invioOdGItem = new MenuItem("OdG");
							invioOdGItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
								@Override
								public void onClick(MenuItemClickEvent event) {							
									clickInviaEmailOdG();
								}
							});							
							invioMenu.addItem(invioOdGItem);
						}						
						if(isAbilToInviaConvocazione() && isAbilToInviaOdG()) {
							// Invio convocazione e OdG
							MenuItem invioEntrambiItem = new MenuItem("Convocazione e OdG");
							invioEntrambiItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
		
								@Override
								public void onClick(MenuItemClickEvent event) {							
									clickInviaEmailConvocazioneOdG();
								}
							});
							invioMenu.addItem(invioEntrambiItem);
						}					
						invioMenu.showContextMenu();
					}
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
				}
			}
		});
	}

	public void caricaDettaglio(Record record) {
		recordConvocazione = record;
		editRecord(record);
		
		if(record != null && record.getAttributeAsString("stato") != null) {	
			statoSeduta = record.getAttributeAsString("stato");
		}
		
		if(numeroItem != null) {
			numeroItem.setCanEdit(false);
		}
		
		if(desStatoSedutaItem != null) {
			desStatoSedutaItem.setCanEdit(false);
		}
		
		if(disattivatoRiordinoAutomaticoItem != null) {
			if(isAbilToSave()) {
				disattivatoRiordinoAutomaticoItem.setCanEdit(true);
			} else {
				disattivatoRiordinoAutomaticoItem.setCanEdit(false);
			}
		}
		
		if(listaDatiConvocazioneSedutaItem != null) {
			listaDatiConvocazioneSedutaItem.setCanEdit(listaDatiConvocazioneSedutaItem.getCanEdit());
			listaDatiConvocazioneSedutaItem.redrawMultiselectButtons();
		}
		
		if(listaDatiPartecipantiSedutaItem != null) {
			listaDatiPartecipantiSedutaItem.setCanEdit(listaDatiPartecipantiSedutaItem.getCanEdit());
		}
		
		if(isAbilToSave()) { 
			salvaButton.show();
			salvaButton.setDisabled(!isButtonEnabled());			
		} else {
			salvaButton.hide();
		}
		
		if(isAbilConvocazione()) {
			convocazioneButton.show();
		} else {
			convocazioneButton.hide();
		}
		
		if(isAbilOdG()) {
			odgButton.show();
		} else {
			odgButton.hide();
		}
		
		if(isAbilAnnullaSeduta()) {			
			annullaSedutaButton.show();
			annullaSedutaButton.setDisabled(!isButtonEnabled());			
		} else {
			annullaSedutaButton.hide();
		}
		
		if(isAbilToInviaConvocazione() || isAbilToInviaOdG()) {
			invioEmailButton.show();
			invioEmailButton.setDisabled(!isButtonEnabled());
		} else {
			invioEmailButton.hide();
		}
		
		listaNomiCommissioniConvocateItem.setCanEdit(false);
		
		markForRedraw();
	}
	
	public void inviaMail(final String uri, final String display, final InfoFileRecord infoFile, final String destinatari, DSCallback sendCallback) {
		
		new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", sendCallback) {
			
			@Override
			public boolean isHideSalvaBozzaButton() {
				return true;
			}
			
			@Override
			public boolean getDefaultSaveSentEmail() {
				return true; // forzo il valore di default del check salvaInviati a true
			}
			
			@Override
			public Record getInitialRecordNuovoMessaggio() {				
				Record lRecord = new Record();
				lRecord.setAttribute("destinatari", destinatari);
				lRecord.setAttribute("destinatariCC", "");		
				RecordList attachRecordList = new RecordList();
				Record attach = new Record();
				attach.setAttribute("fileNameAttach", display);
				attach.setAttribute("infoFileAttach", infoFile);
				attach.setAttribute("uriAttach", uri);
				attachRecordList.add(attach);
				lRecord.setAttribute("attach", attachRecordList);				
				return lRecord;
			}
		};	
	}
	
	public void inviaMailOdGConv(final String uriOdg, final String displayOdg, final InfoFileRecord infoFileOdg, 
					final String uriConvocazione, final String displayConvocazione, final InfoFileRecord infoFileConvocazione,
			final String destinatari, DSCallback sendCallback) {
		
		new NuovoMessaggioWindow("nuovo_messaggio","invioNuovoMessaggio", sendCallback) {
			
			@Override
			public boolean isHideSalvaBozzaButton() {
				return true;
			}
			
			@Override
			public boolean getDefaultSaveSentEmail() {
				return true; // forzo il valore di default del check salvaInviati a true
			}
			
			@Override
			public Record getInitialRecordNuovoMessaggio() {				
				Record lRecord = new Record();
				lRecord.setAttribute("destinatari", destinatari);
				lRecord.setAttribute("destinatariCC", "");		
				RecordList attachRecordList = new RecordList();
				Record attachOdg = new Record();
				attachOdg.setAttribute("fileNameAttach", displayOdg);
				attachOdg.setAttribute("infoFileAttach", infoFileOdg);
				attachOdg.setAttribute("uriAttach", uriOdg);
				attachRecordList.add(attachOdg);
				Record attachConvocazione = new Record();
				attachConvocazione.setAttribute("fileNameAttach", displayConvocazione);
				attachConvocazione.setAttribute("infoFileAttach", infoFileConvocazione);
				attachConvocazione.setAttribute("uriAttach", uriConvocazione);
				attachRecordList.add(attachConvocazione);
				
				lRecord.setAttribute("attach", attachRecordList);				
				return lRecord;
			}
		};	
	}
	
	public boolean isAbilToSave() {	
		if(recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null) {
			if("nuova".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato")) ||
			   "OdG_in_preparazione".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato"))) {
					return true;
			}
		}
		return false;
	}
	
	public boolean isAbilOdG() {		
		Record odgInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("odgInfo") : null;
		if(odgInfo.getAttributeAsString("uriModello") != null &&
					!"".equalsIgnoreCase(odgInfo.getAttributeAsString("uriModello"))) {
				return true;
		}
		return false;
	}
	
	private boolean isButtonEnabled() {
		if("GIUNTA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/GNT/C");
		} else if("CONSIGLIO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNS/C");
		} else if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMM/C");
		} else if("COMITATO_GESTIONE".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CMG/C");
		} else if("ORGANISMO_PATERNARIATO".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/OPR/C");
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			return Layout.isPrivilegioAttivo("ORC/CNF/C");
		} else {
			return false;
		}
	}
	
	public boolean isAbilAnnullaSeduta() {	
		Record odgInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("odgInfo") : null;
		return odgInfo != null ? odgInfo.getAttributeAsBoolean("flgAbilAnnullaSeduta") : false;
	}
	
	public boolean isAbilToListaOdG() {
		Record odgInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("odgInfo") : null;
		RecordList listaOdGConsolidati = odgInfo != null ? odgInfo.getAttributeAsRecordList("listaOdGConsolidati") : null;
		if(listaOdGConsolidati != null && !listaOdGConsolidati.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean isAbilToConsolidaOdG() {
		if(recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null) {
			if("nuova".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato")) ||
			   "OdG_in_preparazione".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato"))) {
					return true;
			}
		}
		return false;
	}
	
	public boolean isAbilToChiudiOdG() {
		if(recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null) {
			if("nuova".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato")) ||
			   "OdG_in_preparazione".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato"))) {
				Record odgInfo = recordConvocazione.getAttributeAsRecord("odgInfo");
				RecordList listaOdGConsolidati = odgInfo != null ? odgInfo.getAttributeAsRecordList("listaOdGConsolidati") : null;
				// se c'è almeno un odg consolidato
				if(listaOdGConsolidati != null && !listaOdGConsolidati.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAbilToInviaOdG() {
		Record odgInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("odgInfo") : null;
		RecordList listaOdGConsolidati = odgInfo != null ? odgInfo.getAttributeAsRecordList("listaOdGConsolidati") : null;
		// se c'è almeno un odg consolidato
		if(listaOdGConsolidati != null && !listaOdGConsolidati.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean isAbilToFirmaOdG() {		
		Record odgInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("odgInfo") : null;
		if(odgInfo != null) {
			if( (odgInfo.getAttributeAsString("idDoc") != null && !"".equalsIgnoreCase(odgInfo.getAttributeAsString("idDoc"))) &&
			    (odgInfo.getAttributeAsBoolean("flgDaFirmare") != null && odgInfo.getAttributeAsBoolean("flgDaFirmare"))) {
					return true;
			}
		} 
		return false;
	}	
	
	public boolean isAbilToRichPubblOdG() {
		Record odgInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("odgInfo") : null;
		if(odgInfo != null) {
			if( (odgInfo.getAttributeAsString("idDoc") != null && !"".equalsIgnoreCase(odgInfo.getAttributeAsString("idDoc"))) &&
			    (odgInfo.getAttributeAsBoolean("flgFirmato") != null && odgInfo.getAttributeAsBoolean("flgFirmato"))) {
				return !odgInfo.getAttributeAsBoolean("flgRichPubblicazione");
			}
		}
		return false;
	}
	
	// CONVOCAZIONE //
	public boolean isAbilConvocazione() {
		Record convocazioneInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("convocazioneInfo") : null;
		if(convocazioneInfo.getAttributeAsString("uriModello") != null && 
				!"".equalsIgnoreCase(convocazioneInfo.getAttributeAsString("uriModello"))) {
			return true;
		}
		return false;
	}
	
	public boolean isAbilToInviaConvocazione() {		
		Record convocazioneInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("convocazioneInfo") : null;
		if(convocazioneInfo != null && convocazioneInfo.getAttributeAsString("idDoc") != null 
				&& !"".equalsIgnoreCase(convocazioneInfo.getAttributeAsString("idDoc"))) {
			return true;
		}
		return false;
	}
	
	public boolean isAbilVisualizzaConvocazione() {		
		Record convocazioneInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("convocazioneInfo") : null;
		if(convocazioneInfo != null && convocazioneInfo.getAttributeAsString("idDoc") != null 
				&& !"".equalsIgnoreCase(convocazioneInfo.getAttributeAsString("idDoc"))) {
			return true;
		}
		return false;
	}
	
	public boolean isAbilToFirmaConvocazione() {
		Record convocazioneInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("convocazioneInfo") : null;
		boolean flgDaFirmare = convocazioneInfo != null && convocazioneInfo.getAttributeAsBoolean("flgDaFirmare") != null 
				? convocazioneInfo.getAttributeAsBoolean("flgDaFirmare") : false;	
		return flgDaFirmare;
	}
	
	public void buildVisualizzaConvocazione() {
		Record convocazioneInfo = recordConvocazione != null ? recordConvocazione.getAttributeAsRecord("convocazioneInfo") : null;
		String display = convocazioneInfo.getAttributeAsString("displayFilename");
		String uri = convocazioneInfo.getAttribute("uri");
		boolean remoteUri = false;
		final Record info = getRecordInfoConvocazione(convocazioneInfo);
		preview(display, uri, remoteUri, new InfoFileRecord(InfoFileRecord.buildInfoFileRecord(info.getJsObj())));
	}
	
	public void preview(final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display, null, false, false);
	}
	
	public void buildAnnullaSeduta(String motivazione) {		
		final Record detailRecord = getRecordToSave();
		detailRecord.setAttribute("motivoAnnullamento", motivazione);
		new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource").executecustom("annullaSeduta", detailRecord, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {										
					Layout.addMessage(new MessageBean("Annullamento seduta avvenuto con successo", "", MessageType.INFO));
					Record recordToGet = dsResponse.getData()[0];
					new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource").call(recordToGet, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {							
							caricaDettaglio(object);	
						}
					});
				}
			}
		});		
	}
	
	public void buildGeneraConvocazione() {	
		
		final Record detailRecord = getRecordToSave();
		String testoHtml = detailRecord.getAttributeAsRecord("convocazioneInfo") != null && 
				detailRecord.getAttributeAsRecord("convocazioneInfo").getAttributeAsString("testoHtml") != null ?
						detailRecord.getAttributeAsRecord("convocazioneInfo").getAttributeAsString("testoHtml") : "";
		String dataHtml = detailRecord.getAttributeAsRecord("convocazioneInfo") != null && 
				detailRecord.getAttributeAsRecord("convocazioneInfo").getAttributeAsString("dettSeduteHtml") != null ?
						detailRecord.getAttributeAsRecord("convocazioneInfo").getAttributeAsString("dettSeduteHtml") : "";
		boolean showEditorDettSedute = detailRecord.getAttributeAsRecord("convocazioneInfo") != null && 
				detailRecord.getAttributeAsRecord("convocazioneInfo").getAttributeAsBoolean("showEditorDettSedute") != null &&
						detailRecord.getAttributeAsRecord("convocazioneInfo").getAttributeAsBoolean("showEditorDettSedute")  ? true : false;
						
		Record recordToModConv = new Record();	
		recordToModConv.setAttribute("testoHtml", testoHtml);
		recordToModConv.setAttribute("showEditorDettSedute", showEditorDettSedute);
		recordToModConv.setAttribute("dettSeduteHtml", dataHtml);
		ModelloConvocazioneWindow lModelloConvocazioneWindow = new ModelloConvocazioneWindow(recordToModConv) {
			
			public void manageOnOkButtonClick(Record record) {
				
				Record convocazioneInfo = detailRecord.getAttributeAsRecord("convocazioneInfo");
				convocazioneInfo.setAttribute("testoHtml", record.getAttributeAsString("testoModello"));
				if(record.getAttributeAsBoolean("showEditorDettSedute") != null && record.getAttributeAsBoolean("showEditorDettSedute")) {
					convocazioneInfo.setAttribute("dettSeduteHtml", record.getAttributeAsString("dettSeduteHtml"));
				}
				convocazioneInfo.setAttribute("azioneDaFare", "salvataggio");
				detailRecord.setAttribute("convocazioneInfo", convocazioneInfo);
				
				anteprimaConvocazione(detailRecord);	
			}
		};
		lModelloConvocazioneWindow.show();
	}
	
	public void anteprimaConvocazione(final Record detailRecord) {
		generaModelloConvocazione(detailRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(final Record recordFileGenerato) {
				
				final String uri = recordFileGenerato.getAttributeAsString("uri");
				final String nomeFile = recordFileGenerato.getAttributeAsString("nomeFile");
				final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileGenerato.getAttributeAsRecord("infoFile").getJsObj()));
				new PreviewWindowWithCallback(uri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						Record convocazioneInfo = detailRecord.getAttributeAsRecord("convocazioneInfo");						
						convocazioneInfo.setAttribute("uri", uri);
						convocazioneInfo.setAttribute("displayFilename", nomeFile);
						convocazioneInfo.setAttribute("mimetype", infoFile.getMimetype());
						convocazioneInfo.setAttribute("encoding", AurigaLayout.getEncoding());
						convocazioneInfo.setAttribute("algoritmo", AurigaLayout.getAlgoritmoImpronta());
						convocazioneInfo.setAttribute("impronta", infoFile.getImpronta());
						convocazioneInfo.setAttribute("flgPdfizzabile", infoFile.isConvertibile());
						convocazioneInfo.setAttribute("dimensione", infoFile.getBytes());
						convocazioneInfo.setAttribute("flgFirmato", infoFile.isFirmato());
						convocazioneInfo.setAttribute("tipoFirma", infoFile.getTipoFirma());
						detailRecord.setAttribute("convocazioneInfo", convocazioneInfo);
						detailRecord.setAttribute("organoCollegiale", organoCollegiale);
						saveAndReload(detailRecord);
					}
				});
			}
		});		
	}
	
	public void generaModelloConvocazione(Record detailRecord, final ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
		lGWTRestDataSource.executecustom("generaModelloConvocazione", detailRecord, new DSCallback() {
			
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
	
	public void buildFirmaConvocazione() {
		
		final Record detailRecord = getRecordToSave();
		final Record convocazioneInfo = detailRecord.getAttributeAsRecord("convocazioneInfo");
		
		final String uri = convocazioneInfo.getAttributeAsString("uri");
		final String nomeFile = convocazioneInfo.getAttributeAsString("displayFilename");
		final Record info = getRecordInfoConvocazione(convocazioneInfo);
		final InfoFileRecord infoFile = new InfoFileRecord(InfoFileRecord.buildInfoFileRecord(info.getJsObj()));
		
		new PreviewWindowWithCallback(uri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {						

					String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
					String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
					FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, uri, nomeFile, infoFile, new FirmaCallback() {

						@Override
						public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
							convocazioneInfo.setAttribute("uri", uriFileFirmato);
							convocazioneInfo.setAttribute("displayFilename", nomeFileFirmato);
							convocazioneInfo.setAttribute("mimetype", infoFileFirmato.getMimetype());
							convocazioneInfo.setAttribute("encoding", AurigaLayout.getEncoding());
							convocazioneInfo.setAttribute("algoritmo", AurigaLayout.getAlgoritmoImpronta());
							convocazioneInfo.setAttribute("impronta", infoFileFirmato.getImpronta());
							convocazioneInfo.setAttribute("flgPdfizzabile", infoFileFirmato.isConvertibile());
							convocazioneInfo.setAttribute("dimensione", infoFileFirmato.getBytes());
							convocazioneInfo.setAttribute("flgFirmato", infoFileFirmato.isFirmato());
							convocazioneInfo.setAttribute("tipoFirma", infoFileFirmato.getTipoFirma());
							if(infoFileFirmato.getFirmatari() != null) {
								RecordList listaFirmatari = new RecordList();
								for(int i = 0; i < infoFileFirmato.getFirmatari().length; i++) {
									Record lRecordFirmatario = new Record();
									lRecordFirmatario.setAttribute("firmatario", infoFileFirmato.getFirmatari()[i]);
									listaFirmatari.add(lRecordFirmatario);
								}
								convocazioneInfo.setAttribute("listaFirmatari", listaFirmatari);
							}
							convocazioneInfo.setAttribute("azioneDaFare", "firma");
							
							Record recordToSave = getRecordToSave();
							recordToSave.setAttribute("organoCollegiale", organoCollegiale);
							recordToSave.setAttribute("convocazioneInfo", convocazioneInfo);										
							saveAndReload(recordToSave);
						}
					});
				}
		});
	}
	
	public void firmaOdG() {
		
		final Record detailRecord = getRecordToSave();
		final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		
		final String uri = odgInfo.getAttributeAsString("uri");
		final String nomeFile = odgInfo.getAttributeAsString("displayFilename");
		final Record info = getRecordInfoOdG(odgInfo);
		final InfoFileRecord infoFile = new InfoFileRecord(InfoFileRecord.buildInfoFileRecord(info.getJsObj()));
		
		new PreviewWindowWithCallback(uri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {						

					String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
					String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
					FirmaUtility.firmaMultipla(true, false, appletTipoFirmaAtti, hsmTipoFirmaAtti, uri, nomeFile, infoFile, new FirmaCallback() {

						@Override
						public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
							odgInfo.setAttribute("uri", uriFileFirmato);
							odgInfo.setAttribute("displayFilename", nomeFileFirmato);
							odgInfo.setAttribute("mimetype", infoFileFirmato.getMimetype());
							odgInfo.setAttribute("encoding", AurigaLayout.getEncoding());
							odgInfo.setAttribute("algoritmo", AurigaLayout.getAlgoritmoImpronta());
							odgInfo.setAttribute("impronta", infoFileFirmato.getImpronta());
							odgInfo.setAttribute("flgPdfizzabile", infoFileFirmato.isConvertibile());
							odgInfo.setAttribute("dimensione", infoFileFirmato.getBytes());
							odgInfo.setAttribute("flgFirmato", infoFileFirmato.isFirmato());
							odgInfo.setAttribute("tipoFirma", infoFileFirmato.getTipoFirma());
							if(infoFileFirmato.getFirmatari() != null) {
								RecordList listaFirmatari = new RecordList();
								for(int i = 0; i < infoFileFirmato.getFirmatari().length; i++) {
									Record lRecordFirmatario = new Record();
									lRecordFirmatario.setAttribute("firmatario", infoFileFirmato.getFirmatari()[i]);
									listaFirmatari.add(lRecordFirmatario);
								}
								odgInfo.setAttribute("listaFirmatari", listaFirmatari);
							}
							odgInfo.setAttribute("azioneDaFare", "firma");
							Record recordToSave = getRecordToSave();
							recordToSave.setAttribute("organoCollegiale", organoCollegiale);
							recordToSave.setAttribute("odgInfo", odgInfo);										
							saveAndReload(recordToSave);
						}
					});
				}
		});
	}
	
	public Menu buildMenuListaOdG(Record detailRecord) {
		Menu menuAnteprimaOdG = new Menu();
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		final String destinatari = detailRecord.getAttribute("destinatari");
		RecordList listaOdGConsolidati = odgInfo.getAttributeAsRecordList("listaOdGConsolidati");
		if(listaOdGConsolidati != null) {
			for(int i = 0; i < listaOdGConsolidati.getLength(); i++) {
				final Record odgConsolidatoInfo = listaOdGConsolidati.get(i);
				final String uri = odgConsolidatoInfo.getAttributeAsString("uri");
				final String display = odgConsolidatoInfo.getAttributeAsString("displayFilename");
				final Record info = new Record();
				info.setAttribute("correctFileName", odgConsolidatoInfo.getAttribute("displayFilename"));
				info.setAttribute("mimetype", odgConsolidatoInfo.getAttribute("mimetype"));
				info.setAttribute("encoding", odgConsolidatoInfo.getAttribute("encoding"));
				info.setAttribute("algoritmo", odgConsolidatoInfo.getAttribute("algoritmo"));
				info.setAttribute("impronta", odgConsolidatoInfo.getAttribute("impronta"));
				info.setAttribute("convertibile", odgConsolidatoInfo.getAttributeAsBoolean("flgPdfizzabile"));
				info.setAttribute("bytes", odgConsolidatoInfo.getAttributeAsLong("dimensione"));
				info.setAttribute("firmato", odgConsolidatoInfo.getAttributeAsBoolean("flgFirmato"));
				info.setAttribute("firmaValida", true);
				info.setAttribute("tipoFirma", odgConsolidatoInfo.getAttribute("tipoFirma"));
				if(odgConsolidatoInfo.getAttribute("firmatario") != null && !"".equals(odgConsolidatoInfo.getAttribute("firmatario"))) { 
					StringSplitterClient lStringSplitterClient = new StringSplitterClient(odgConsolidatoInfo.getAttribute("firmatario"), ";");
					info.setAttribute("firmatari", lStringSplitterClient.getTokens());
				}
				MenuItem menuItem = new MenuItem(odgConsolidatoInfo.getAttribute("dtConsolidamento"));
				if(odgConsolidatoInfo.getAttributeAsBoolean("flgRichPubblicazione")) {
					menuItem.setIcon("buttons/richiesta_pubblicazione.png");
				}
				Menu subMenu = new Menu();
				MenuItem visualizzaMenuItem = new MenuItem("Visualizza"); 
				visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						PreviewControl.switchPreview(uri, true, InfoFileRecord.buildInfoFileRecord(info.getJsObj()), "FileToExtractBean", display);
					}
				});
				MenuItem scaricaMenuItem = new MenuItem("Scarica");
				if(display != null && display.toLowerCase().endsWith(".p7m")) {
					Menu scaricaFirmatoMenu = new Menu();
					MenuItem firmato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadFirmatoMenuItem_prompt());
					firmato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", display);
							lRecord.setAttribute("uri", uri);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", "true");
							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}
					});
					MenuItem sbustato = new MenuItem(I18NUtil.getMessages().protocollazione_detail_downloadSbustatoMenuItem_prompt());
					sbustato.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", display);
							lRecord.setAttribute("uri", uri);
							lRecord.setAttribute("sbustato", "true");
							lRecord.setAttribute("remoteUri", "true");
							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}
					});
					scaricaFirmatoMenu.setItems(firmato, sbustato);
					scaricaMenuItem.setSubmenu(scaricaFirmatoMenu);		
				} else {
					scaricaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
						
						@Override
						public void onClick(MenuItemClickEvent event) {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", display);
							lRecord.setAttribute("uri", uri);
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", "true");
							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}
					});
				}												
				MenuItem inviaMenuItem = new MenuItem("Invia via e-mail");
				inviaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
					
					@Override
					public void onClick(MenuItemClickEvent event) {
						inviaMail(uri, display, InfoFileRecord.buildInfoFileRecord(info.getJsObj()), destinatari, new DSCallback() {
							
							@Override
							public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
								
							}
						});				
					}
				});
				subMenu.setItems(visualizzaMenuItem, scaricaMenuItem, inviaMenuItem);
				menuItem.setSubmenu(subMenu);												
				menuItems.add(menuItem);
			}
		}
		menuAnteprimaOdG.setItems(menuItems.toArray(new MenuItem[menuItems.size()]));	
		return menuAnteprimaOdG;
	}
	
	public Menu buildMenuConsolidaOdG(final Record detailRecord, boolean aggiungiAncheSupplementoUrgente) {
		Menu menuConsolidaOdG = new Menu();
		MenuItem completoMenuItem = new MenuItem("OdG completo");
		completoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				if(validate()) {
					consolidaOdG(detailRecord, "completo");
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
				}
			}
		});
		MenuItem supplementoMenuItem = new MenuItem(aggiungiAncheSupplementoUrgente ? "supplemento" : "solo supplemento");
		supplementoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				if(validate()) {
					consolidaOdG(detailRecord, "supplemento");
				} else {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
				}
			}
		});
		if(aggiungiAncheSupplementoUrgente) {		
			MenuItem supplementoUrgenteMenuItem = new MenuItem("supplemento d'urgenza");
			supplementoUrgenteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					if(validate()) {
						consolidaOdG(detailRecord, "supplemento urgente");
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
					}
				}
			});
			menuConsolidaOdG.setItems(completoMenuItem, supplementoMenuItem, supplementoUrgenteMenuItem);
		} else {
			menuConsolidaOdG.setItems(completoMenuItem, supplementoMenuItem);
		}
		return menuConsolidaOdG;
	}
		
	public Menu buildMenuAnteprimaOdG(final Record detailRecord) {
		Menu menuAnteprimaOdG = new Menu();
		MenuItem completoMenuItem = new MenuItem("OdG completo");
		completoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				anteprimaOdG(detailRecord, "completo");
			}
		});
		MenuItem supplementoMenuItem = new MenuItem("solo supplemento");
		supplementoMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				anteprimaOdG(detailRecord, "supplemento");
			}
		});
		menuAnteprimaOdG.setItems(completoMenuItem, supplementoMenuItem);
		return menuAnteprimaOdG;
	}		
	
	public void anteprimaOdG(Record detailRecord, String tipoOdGConsolidato) {
		generaModelloOdG(detailRecord, tipoOdGConsolidato, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordFileGenerato) {
				String uri = recordFileGenerato.getAttributeAsString("uri");
				String nomeFile = recordFileGenerato.getAttributeAsString("nomeFile");
				InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileGenerato.getAttributeAsRecord("infoFile").getJsObj()));
				PreviewControl.switchPreview(uri, false, infoFile, "FileToExtractBean", nomeFile);
			}
		});		
	}
	
	public void generaModelloOdG(Record detailRecord, String tipoOdGConsolidato, final ServiceCallback<Record> callback) {
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
		lGWTRestDataSource.addParam("tipoOdGConsolidato", tipoOdGConsolidato);
		lGWTRestDataSource.executecustom("generaModelloOdg", detailRecord, new DSCallback() {
			
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
	
	public void consolidaOdG(final Record detailRecord, final String tipoOdGConsolidato) {
		final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		generaModelloOdG(detailRecord, tipoOdGConsolidato, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record recordFileGenerato) {
				
				final String uri = recordFileGenerato.getAttributeAsString("uri");
				final String nomeFile = recordFileGenerato.getAttributeAsString("nomeFile");
				final InfoFileRecord infoFile = InfoFileRecord.buildInfoFileString(JSON.encode(recordFileGenerato.getAttributeAsRecord("infoFile").getJsObj()));
				
				new PreviewWindowWithCallback(uri, false, infoFile, "FileToExtractBean", nomeFile, new ServiceCallback<Record>() {
	
						@Override
						public void execute(Record object) {						
//							boolean flgDaFirmare = odgInfo != null && odgInfo.getAttributeAsBoolean("flgDaFirmare") != null ? odgInfo.getAttributeAsBoolean("flgDaFirmare") : false;		
//							if (flgDaFirmare) {
//								// Leggo gli eventuali parametri per forzare il tipo d firma
//								String appletTipoFirmaAtti = AurigaLayout.getParametroDB("APPLET_TIPO_FIRMA_ATTI");
//								String hsmTipoFirmaAtti = AurigaLayout.getParametroDB("HSM_TIPO_FIRMA_ATTI");			
//								FirmaUtility.firmaMultipla(appletTipoFirmaAtti, hsmTipoFirmaAtti, uri, nomeFile, infoFile, new FirmaCallback() {
//	
//									@Override
//									public void execute(String nomeFileFirmato, String uriFileFirmato, InfoFileRecord infoFileFirmato) {
//										odgInfo.setAttribute("uri", uriFileFirmato);
//										odgInfo.setAttribute("displayFilename", nomeFileFirmato);
//										odgInfo.setAttribute("mimetype", infoFileFirmato.getMimetype());
//										odgInfo.setAttribute("encoding", AurigaLayout.getEncoding());
//										odgInfo.setAttribute("algoritmo", AurigaLayout.getAlgoritmoImpronta());
//										odgInfo.setAttribute("impronta", infoFileFirmato.getImpronta());
//										odgInfo.setAttribute("flgPdfizzabile", infoFileFirmato.isConvertibile());
//										odgInfo.setAttribute("dimensione", infoFileFirmato.getBytes());
//										odgInfo.setAttribute("flgFirmato", infoFileFirmato.isFirmato());
//										odgInfo.setAttribute("tipoFirma", infoFileFirmato.getTipoFirma());
//										if(infoFileFirmato.getFirmatari() != null) {
//											RecordList listaFirmatari = new RecordList();
//											for(int i = 0; i < infoFileFirmato.getFirmatari().length; i++) {
//												Record lRecordFirmatario = new Record();
//												lRecordFirmatario.setAttribute("firmatario", infoFileFirmato.getFirmatari()[i]);
//												listaFirmatari.add(lRecordFirmatario);
//											}
//											odgInfo.setAttribute("listaFirmatari", listaFirmatari);
//										}
//										odgInfo.setAttribute("azioneDaFare", "consolidamento");
//										odgInfo.setAttribute("tipoOdGConsolidato", tipoOdGConsolidato);
//										Record recordToSave = getRecordToSave();
//										recordToSave.setAttribute("organoCollegiale", organoCollegiale);
//										recordToSave.setAttribute("odgInfo", odgInfo);										
//										saveAndReload(recordToSave);
//									}
//								});
//							} else {
								odgInfo.setAttribute("uri", uri);
								odgInfo.setAttribute("displayFilename", nomeFile);
								odgInfo.setAttribute("mimetype", infoFile.getMimetype());
								odgInfo.setAttribute("encoding", AurigaLayout.getEncoding());
								odgInfo.setAttribute("algoritmo", AurigaLayout.getAlgoritmoImpronta());
								odgInfo.setAttribute("impronta", infoFile.getImpronta());
								odgInfo.setAttribute("flgPdfizzabile", infoFile.isConvertibile());
								odgInfo.setAttribute("dimensione", infoFile.getBytes());
								odgInfo.setAttribute("flgFirmato", infoFile.isFirmato());
								odgInfo.setAttribute("tipoFirma", infoFile.getTipoFirma());
								if(infoFile.getFirmatari() != null) {
									RecordList listaFirmatari = new RecordList();
									for(int i = 0; i < infoFile.getFirmatari().length; i++) {
										Record lRecordFirmatario = new Record();
										lRecordFirmatario.setAttribute("firmatario", infoFile.getFirmatari()[i]);
										listaFirmatari.add(lRecordFirmatario);
									}
									odgInfo.setAttribute("listaFirmatari", listaFirmatari);
								}								
								odgInfo.setAttribute("azioneDaFare", "consolidamento");
								odgInfo.setAttribute("tipoOdGConsolidato", tipoOdGConsolidato);
								Record recordToSave = getRecordToSave();
								recordToSave.setAttribute("organoCollegiale", organoCollegiale);
								recordToSave.setAttribute("odgInfo", odgInfo);										
								saveAndReload(recordToSave);
							//}
						}
				}).setShowCloseButton(false);
			}
		});	
	}
	
	public void clickInviaEmailOdG() {
		
		Record detailRecord = getRecordToSave();
		String destinatari = detailRecord.getAttribute("destinatari");

		final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		String uri = odgInfo.getAttributeAsString("uri");				
		String display = odgInfo.getAttributeAsString("displayFilename");		
		final Record info = getRecordInfoOdG(odgInfo);
		
		if (uri != null && !"".equalsIgnoreCase(uri)) {
			inviaMail(uri, display, InfoFileRecord.buildInfoFileRecord(info.getJsObj()), destinatari, new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					
					Record recordToSave = getRecordToSave();
					recordToSave.setAttribute("organoCollegiale", organoCollegiale);												
					odgInfo.setAttribute("azioneDaFare", "invio_email");
	
					Record recordMail = dsResponse.getData()[0];
					odgInfo.setAttribute("invioOdgEmailTo", recordMail.getAttributeAsString("destinatari"));
					odgInfo.setAttribute("invioOdgEmailCC", recordMail.getAttributeAsString("destinatariCC"));
					odgInfo.setAttribute("oggettoEmail", recordMail.getAttributeAsString("oggetto"));
					odgInfo.setAttribute("idEmail", recordMail.getAttributeAsString("idEmail"));
					recordToSave.setAttribute("odgInfo", odgInfo);
					saveAndReload(recordToSave);
				}
			});
		}
	}
	
	public void clickInviaEmailConvocazioneOdG() {
		
		Record detailRecord = getRecordToSave();
		String destinatari = detailRecord.getAttribute("destinatari");

		// ODG
		final Record odgInfo = detailRecord.getAttributeAsRecord("odgInfo");
		String uriOdg = odgInfo.getAttributeAsString("uri");				
		String displayOdg = odgInfo.getAttributeAsString("displayFilename");	
		final Record infoFileOdg = getRecordInfoOdG(odgInfo);
		
		//CONVOCAZIONE
		final Record convocazioneInfo = detailRecord.getAttributeAsRecord("convocazioneInfo");
		String uriConvocazione = convocazioneInfo.getAttributeAsString("uri");				
		String displayConvocazione = convocazioneInfo.getAttributeAsString("displayFilename");		
		final Record infoFileConvocazione = getRecordInfoConvocazione(convocazioneInfo);		
		
		if ((uriOdg != null && !"".equalsIgnoreCase(uriOdg)) && (uriConvocazione != null && !"".equalsIgnoreCase(uriConvocazione))) {
			inviaMailOdGConv(uriOdg, displayOdg, InfoFileRecord.buildInfoFileRecord(infoFileOdg.getJsObj()),
					uriConvocazione, displayConvocazione, InfoFileRecord.buildInfoFileRecord(infoFileConvocazione.getJsObj()),
					destinatari, new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					
					Record recordToSave = getRecordToSave();
					recordToSave.setAttribute("organoCollegiale", organoCollegiale);												
					odgInfo.setAttribute("azioneDaFare", "invio_email");
					convocazioneInfo.setAttribute("azioneDaFare", "invio_email");
	
					Record recordMail = dsResponse.getData()[0];
					odgInfo.setAttribute("invioOdgEmailTo", recordMail.getAttributeAsString("destinatari"));
					odgInfo.setAttribute("invioOdgEmailCC", recordMail.getAttributeAsString("destinatariCC"));
					odgInfo.setAttribute("oggettoEmail", recordMail.getAttributeAsString("oggetto"));
					odgInfo.setAttribute("idEmail", recordMail.getAttributeAsString("idEmail"));
					recordToSave.setAttribute("odgInfo", odgInfo);
					
					convocazioneInfo.setAttribute("invioOdgEmailTo", recordMail.getAttributeAsString("destinatari"));
					convocazioneInfo.setAttribute("invioOdgEmailCC", recordMail.getAttributeAsString("destinatariCC"));
					convocazioneInfo.setAttribute("oggettoEmail", recordMail.getAttributeAsString("oggetto"));
					convocazioneInfo.setAttribute("idEmail", recordMail.getAttributeAsString("idEmail"));
					recordToSave.setAttribute("convocazioneInfo", convocazioneInfo);
					saveAndReload(recordToSave);
				}
			});
		}
	}
	
	public void clickInviaEmailConvocazione() {
		
		Record detailRecord = getRecordToSave();
		String destinatari = detailRecord.getAttribute("destinatari");

		final Record convocazioneInfo = detailRecord.getAttributeAsRecord("convocazioneInfo");
		String uri = convocazioneInfo.getAttributeAsString("uri");				
		String display = convocazioneInfo.getAttributeAsString("displayFilename");	
		final Record info = getRecordInfoConvocazione(convocazioneInfo);		
		
		if (uri != null && !"".equalsIgnoreCase(uri)) {
			inviaMail(uri, display, InfoFileRecord.buildInfoFileRecord(info.getJsObj()), destinatari, new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					
					Record recordToSave = getRecordToSave();
					recordToSave.setAttribute("organoCollegiale", organoCollegiale);												
					convocazioneInfo.setAttribute("azioneDaFare", "invio_email");
	
					Record recordMail = dsResponse.getData()[0];
					convocazioneInfo.setAttribute("invioConvocazioneEmailTo", recordMail.getAttributeAsString("destinatari"));//TODO
					convocazioneInfo.setAttribute("invioConvocazioneEmailCC", recordMail.getAttributeAsString("destinatariCC"));// TODO
					convocazioneInfo.setAttribute("oggettoEmail", recordMail.getAttributeAsString("oggetto"));
					convocazioneInfo.setAttribute("idEmail", recordMail.getAttributeAsString("idEmail"));
					recordToSave.setAttribute("convocazioneInfo", convocazioneInfo);
					saveAndReload(recordToSave);
				}
			});
		}
	}
	
	private void saveAndReload(final Record recordToSave) {	
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
		lGWTRestDataSource.executecustom("salvaConvocazioneSeduta", recordToSave, new DSCallback() {
			
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				
				if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Salvataggio avvenuto con successo", "", MessageType.INFO));
					Record recordToGet = dsResponse.getData()[0];
					GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
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
	
	private Boolean isOdGChiuso() {
		if(recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null &&
			"OdG_chiuso".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato"))) {
				return true;		   
		}			
		return false;
	}
	
	protected VLayout createLayoutTab(VLayout layout, VLayout spacerLayout) {
		
		VLayout layoutTab = new VLayout();
		layoutTab.setWidth100();
		layoutTab.setHeight100();
		layoutTab.addMember(layout);
		if(spacerLayout != null) {
			layoutTab.addMember(spacerLayout);
		}
		layoutTab.setRedrawOnResize(true);
		
		return layoutTab;
	}
	
	private String getTitleTabDatiSeduta() {
		return "Seduta";
	}
	
	private String getTitleTabDatiPartecipanti() {
		return "Partecipanti";
	}
	
	private String getTitleDtPrimaConvocazione() {
		return AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE") ? "Convocazione il" : "1a convocazione il";
	}
	
	public VLayout getLayoutTabDatiSeduta() {
		
		formDatiSeduta = new DynamicForm();
		formDatiSeduta.setTabID("HEADER");		
		formDatiSeduta.setValuesManager(vm);
		formDatiSeduta.setWidth("100%");
		formDatiSeduta.setHeight("5");
		formDatiSeduta.setPadding(5);
		formDatiSeduta.setWrapItemTitles(false);
		formDatiSeduta.setNumCols(10);
		formDatiSeduta.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		formDatiSeduta.setValuesManager(vm);
		
		odgInfoItem = new HiddenItem("odgInfo");
		idSedutaItem = new HiddenItem("idSeduta");
		convocazioneInfoItem = new HiddenItem("convocazioneInfo");
		listaCommissioniItem = new HiddenItem("listaCommissioni");
		
		listaIdCommissioniConvocateItem = new HiddenItem("listaIdCommissioniConvocate");
		
		numeroItem = new TextItem("numero", setTitleAlign("N°", 100, false));
		numeroItem.setStartRow(true);
		numeroItem.setColSpan(1);
		numeroItem.setCanEdit(false);
		numeroItem.setWidth(117);
		
		operazioniEffettuateButton = new ImgButtonItem("operazioniEffettuate", "protocollazione/operazioniEffettuate.png", I18NUtil.getMessages().protocollazione_detail_operazioniEffettuateButton_prompt());
		operazioniEffettuateButton.setAlwaysEnabled(true);
		operazioniEffettuateButton.setColSpan(1);
		operazioniEffettuateButton.setEndRow(false);
		operazioniEffettuateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				String idSeduta = idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
				String data = DateUtil.format(dtPrimaConvocazioneItem.getValueAsDate()).substring(0, 10);
				String title = "Operazioni sulla seduta di "+ organoCollegiale + " N° " + numeroItem.getValueAsString() + " del " + data;
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
		listaNomiCommissioniConvocateItem.setColSpan(7);
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
		
		modificaListaNomiCommissioniConvocateButton = new ImgButtonItem("modificaListaCommissioniConvocate", "buttons/modify.png", "Commissioni");
		modificaListaNomiCommissioniConvocateButton.setPrompt("Modifica commissioni");
		modificaListaNomiCommissioniConvocateButton.setAlwaysEnabled(true);
		modificaListaNomiCommissioniConvocateButton.setColSpan(1);
		modificaListaNomiCommissioniConvocateButton.setEndRow(false);
		modificaListaNomiCommissioniConvocateButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				// Apre select a scelta multipla popolata con le commissioni				
				apriSceltaCommissione(new ServiceCallback<Record>() {

					@Override
					public void execute(Record lRecord) {
							
						RecordList listaCommissioniSelezionate = new RecordList();
						listaCommissioniSelezionate = lRecord.getAttributeAsRecordList("listaCommissioniSelezionate");
						if (listaCommissioniSelezionate != null && !listaCommissioniSelezionate.isEmpty()) {
							String listaNomiCommissioniConvocate = "";
							String listaIdCommissioniConvocate = "";
							for (int i = 0; i < listaCommissioniSelezionate.getLength(); i++) {
								Record lRecordDef = listaCommissioniSelezionate.get(i);
								String key = lRecordDef.getAttribute("key");
								String value = lRecordDef.getAttribute("value");
								
								if (value!=null && !value.equalsIgnoreCase("")){
									listaNomiCommissioniConvocate   += value + "; \r\n";
									listaIdCommissioniConvocate += key + ";";
								}
							}
							listaNomiCommissioniConvocateItem.setValue(listaNomiCommissioniConvocate);
							formDatiSeduta.setValue("listaNomiCommissioniConvocate", listaNomiCommissioniConvocate);
							
							listaIdCommissioniConvocateItem.setValue(listaIdCommissioniConvocate);
							formDatiSeduta.setValue("listaIdCommissioniConvocate", listaIdCommissioniConvocate);
							markForRedraw();							
						}						
					}
				}, (String) formDatiSeduta.getValue("listaIdCommissioniConvocate"));
			}
		});
		modificaListaNomiCommissioniConvocateButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return "COMMISSIONE".equalsIgnoreCase(organoCollegiale);
			}
		});
					
		dtPrimaConvocazioneItem = new DateTimeItem("dtPrimaConvocazione", getTitleDtPrimaConvocazione());
		dtPrimaConvocazioneItem.setStartRow(true);
		dtPrimaConvocazioneItem.setColSpan(2);
		dtPrimaConvocazioneItem.setRequired(true);
		
		luogoPrimaConvocazioneItem = new TextItem("luogoPrimaConvocazione", "Luogo");
		luogoPrimaConvocazioneItem.setStartRow(false);
		luogoPrimaConvocazioneItem.setEndRow(true);
		luogoPrimaConvocazioneItem.setColSpan(5);
		
		dtSecondaConvocazioneItem = new DateTimeItem("dtSecondaConvocazione", "2a convocazione il");
		dtSecondaConvocazioneItem.setStartRow(true);
		dtSecondaConvocazioneItem.setColSpan(2);
		dtSecondaConvocazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE");
			}
		}); 
		
		luogoSecondaConvocazioneItem = new TextItem("luogoSecondaConvocazione", "Luogo");
		luogoSecondaConvocazioneItem.setStartRow(false);
		luogoSecondaConvocazioneItem.setColSpan(5);
		luogoSecondaConvocazioneItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return !AurigaLayout.getParametroDBAsBoolean("HIDE_2_CONVOCAZIONE");
			}
		}); 
		
		desStatoSedutaItem = new TextItem("desStato", setTitleAlign("Stato", 100, false));
		desStatoSedutaItem.setStartRow(true);
		desStatoSedutaItem.setColSpan(2);
		desStatoSedutaItem.setCanEdit(false);
		desStatoSedutaItem.setWidth(140);
		
		disattivatoRiordinoAutomaticoItem = new CheckboxItem("disattivatoRiordinoAutomatico", "disattiva riordino automatico atti in OdG");
		disattivatoRiordinoAutomaticoItem.setColSpan(1);
		disattivatoRiordinoAutomaticoItem.setWidth(50);
		disattivatoRiordinoAutomaticoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return showRiordinoAutomatico();
			}
		});
		
		GWTRestDataSource tipoSessioneDS = new GWTRestDataSource("LoadComboTipoSessioneOdgDataSource");
		tipoSessioneDS.addParam("tipo_sessione", organoCollegiale);
		
		tipoSessioneItem = new SelectItem("tipoSessione", setTitleAlign("Tipo sessione", 100, false));
		tipoSessioneItem.setValueField("key");
		tipoSessioneItem.setDisplayField("value");
		tipoSessioneItem.setOptionDataSource(tipoSessioneDS);
		tipoSessioneItem.setAutoFetchData(false);
		tipoSessioneItem.setAlwaysFetchMissingValues(true);
		tipoSessioneItem.setFetchMissingValues(true);
		tipoSessioneItem.setStartRow(true);
		tipoSessioneItem.setColSpan(2);
		tipoSessioneItem.setWidth(140);
		
		formDatiSeduta.setItems(
				odgInfoItem,
				idSedutaItem,
				convocazioneInfoItem,
				listaCommissioniItem,
				numeroItem,
				operazioniEffettuateButton,
				visualizzaContenutiFascicoloButton,
				listaNomiCommissioniConvocateItem,
				modificaListaNomiCommissioniConvocateButton,
				dtPrimaConvocazioneItem,luogoPrimaConvocazioneItem,
				dtSecondaConvocazioneItem,luogoSecondaConvocazioneItem,
				desStatoSedutaItem,
				disattivatoRiordinoAutomaticoItem,
				tipoSessioneItem,
				listaIdCommissioniConvocateItem
		);
		
		sectionDatiSeduta = new DetailSection("Dati seduta", true, true, true, formDatiSeduta);
		sectionDatiSeduta.setHeight(5);
		
		formListaDatiSeduta = new DynamicForm();
		formListaDatiSeduta.setTabID("HEADER");		
		formListaDatiSeduta.setValuesManager(vm);
		formListaDatiSeduta.setWidth("100%");
		formListaDatiSeduta.setHeight("100%");
		formListaDatiSeduta.setPadding(5);
		formListaDatiSeduta.setWrapItemTitles(false);
		formListaDatiSeduta.setNumCols(10);
		formListaDatiSeduta.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, "*");
		
		listaArgomentiOdgEliminatiItem = new HiddenItem("listaArgomentiOdgEliminati");
		
		listaDatiConvocazioneSedutaItem = new ListaDatiConvocazioneSedutaItem("listaArgomentiOdg", organoCollegiale, codCircoscrizione) {
			
			@Override
			public void onClickRemoveButton(ListGridRecord record) {
				
				onClickRemoveButton(record, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record object) {
						RecordList recordList = formListaDatiSeduta.getValueAsRecordList("listaArgomentiOdgEliminati");
						if( recordList == null ) {
							recordList = new RecordList();
						}
						recordList.add(object);
						listaArgomentiOdgEliminatiItem.setValue(recordList);
					}
				});
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
			protected GridMultiToolStripButton[] getGridMultiselectButtons() {
				List<GridMultiToolStripButton> gridMultiselectButtons = new ArrayList<GridMultiToolStripButton>();
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
								return showDownloadFileAttiMultiButton();
							}
								
							@Override
							public void doSomething() {
								
								final RecordList listaAtti = new RecordList();
								for (int i = 0; i < grid.getSelectedRecords().length; i++) {
									listaAtti.add(grid.getSelectedRecords()[i]);
								}
								
								final String idSeduta = getIdSeduta();
								OpzioniAttiDaScaricarePopup opzioniAttiDaScaricarePopup = new OpzioniAttiDaScaricarePopup () {
									
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
								return showTrasmissioneAttiMultiButton();
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
										convocazioneSedutaDS.setForceToShowPrompt(false);	
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
		listaDatiConvocazioneSedutaItem.setStartRow(true);
		listaDatiConvocazioneSedutaItem.setShowTitle(false);
		listaDatiConvocazioneSedutaItem.setHeight("95%");
		
		formListaDatiSeduta.setItems(listaArgomentiOdgEliminatiItem, listaDatiConvocazioneSedutaItem);
		
		sectionListaDatiSeduta = new DetailSection("OdG - proposte di delibere e altri argomenti da discutere", false, true, false, formListaDatiSeduta);
				
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(sectionDatiSeduta);
		lVLayout.addMember(sectionListaDatiSeduta);
				
		return lVLayout;
	}
	
	public VLayout getLayoutTabDatiPartecipanti() {
		
		formDatiPartecipanti = new DynamicForm();
		formDatiPartecipanti.setTabID("PARTECIPANTI");		
		formDatiPartecipanti.setValuesManager(vm);
		formDatiPartecipanti.setWidth("100%");
		formDatiPartecipanti.setHeight("100%");
		formDatiPartecipanti.setPadding(5);
		formDatiPartecipanti.setWrapItemTitles(false);
		formDatiPartecipanti.setNumCols(12);
		formDatiPartecipanti.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		formDatiPartecipanti.setValuesManager(vm);

		listaDatiPartecipantiSedutaItem = new ListaDatiPartecipantiSedutaItem("listaPresenzeOdg", organoCollegiale, statoSeduta, listaCommissioni) {
			
			@Override
			public String getIdSeduta() {
				return idSedutaItem.getValue() != null ? (String) idSedutaItem.getValue() : null;
			}
		};
		listaDatiPartecipantiSedutaItem.setStartRow(true);
		listaDatiPartecipantiSedutaItem.setShowTitle(false);
		listaDatiPartecipantiSedutaItem.setHeight("95%");		
		
		formDatiPartecipanti.setItems(listaDatiPartecipantiSedutaItem);
				
		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight100();
		lVLayout.addMember(formDatiPartecipanti);
				
		return lVLayout;
	}
	
	protected String setTitleAlign(String title, int width, boolean required) {
		if (title != null && title.startsWith("<span")) {
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}
	
	private Record getRecordInfoConvocazione(Record convocazioneInfo) {
		Record infoFile = new Record();
		infoFile.setAttribute("correctFileName", convocazioneInfo.getAttribute("displayFilename"));
		infoFile.setAttribute("mimetype", convocazioneInfo.getAttribute("mimetype"));
		infoFile.setAttribute("encoding", convocazioneInfo.getAttribute("encoding"));
		infoFile.setAttribute("algoritmo", convocazioneInfo.getAttribute("algoritmo"));
		infoFile.setAttribute("impronta", convocazioneInfo.getAttribute("impronta"));
		infoFile.setAttribute("convertibile", convocazioneInfo.getAttributeAsBoolean("flgPdfizzabile"));
		infoFile.setAttribute("bytes", convocazioneInfo.getAttributeAsLong("dimensione"));
		infoFile.setAttribute("firmato", convocazioneInfo.getAttributeAsBoolean("flgFirmato"));
		infoFile.setAttribute("firmaValida", true);
		infoFile.setAttribute("tipoFirma", convocazioneInfo.getAttribute("tipoFirma"));
		if(convocazioneInfo.getAttributeAsRecordList("listaFirmatari") != null) {
			String[] firmatari = new String[convocazioneInfo.getAttributeAsRecordList("listaFirmatari").getLength()];
			for(int i = 0; i < convocazioneInfo.getAttributeAsRecordList("listaFirmatari").getLength(); i++) {
				firmatari[i] = convocazioneInfo.getAttributeAsRecordList("listaFirmatari").get(i).getAttribute("firmatario");
			}
			infoFile.setAttribute("firmatari", firmatari);
		}	
		return infoFile;
	}
	
	private Record getRecordInfoOdG(Record odgInfo) {
		Record infoFile = new Record();
		infoFile.setAttribute("correctFileName", odgInfo.getAttribute("displayFilename"));
		infoFile.setAttribute("mimetype", odgInfo.getAttribute("mimetype"));
		infoFile.setAttribute("encoding", odgInfo.getAttribute("encoding"));
		infoFile.setAttribute("algoritmo", odgInfo.getAttribute("algoritmo"));
		infoFile.setAttribute("impronta", odgInfo.getAttribute("impronta"));
		infoFile.setAttribute("convertibile", odgInfo.getAttributeAsBoolean("flgPdfizzabile"));
		infoFile.setAttribute("bytes", odgInfo.getAttributeAsLong("dimensione"));
		infoFile.setAttribute("firmato", odgInfo.getAttributeAsBoolean("flgFirmato"));
		infoFile.setAttribute("firmaValida", true);
		infoFile.setAttribute("tipoFirma", odgInfo.getAttribute("tipoFirma"));
		if(odgInfo.getAttributeAsRecordList("listaFirmatari") != null) {
			String[] firmatari = new String[odgInfo.getAttributeAsRecordList("listaFirmatari").getLength()];
			for(int i = 0; i < odgInfo.getAttributeAsRecordList("listaFirmatari").getLength(); i++) {
				firmatari[i] = odgInfo.getAttributeAsRecordList("listaFirmatari").get(i).getAttribute("firmatario");
			}
			infoFile.setAttribute("firmatari", firmatari);
		}	
		return infoFile;
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
	
	private void clickSaveButton() {
		Record recordToSave = getRecordToSave();
		recordToSave.setAttribute("organoCollegiale", organoCollegiale);
		saveAndReload(recordToSave);
	}
	
	/**
	 * Verifica se la data di prima convocazione è una data passata
	 */
	private Boolean isValidateDtPrimaConvocazione() {
		Boolean isValid = false;
		Date today = new Date();
		Date dtPrimaConvocazione = getValueAsDate("dtPrimaConvocazione");
		
		/**
		 * GESTIONE FUSO ORARIO LATO CLIENT
		 */
		int timezoneOffset = dtPrimaConvocazione.getTimezoneOffset();		
		long MILLISECONDS_IN_MINUTE = (MILLISECONDS_IN_SECOND * SECONDS_IN_MINUTE) ;
		dtPrimaConvocazione = new Date(dtPrimaConvocazione.getTime() + (timezoneOffset * MILLISECONDS_IN_MINUTE));
		
		if(dtPrimaConvocazione.after(today)) {
			isValid = true;
		} else {
			isValid = false;
		}
		
		return isValid;
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
	
	private Boolean showDownloadFileAttiMultiButton() {
		return recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null && !"nuova".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato"));
	}
	
	private Boolean showTrasmissioneAttiMultiButton() {
		return recordConvocazione != null && recordConvocazione.getAttributeAsString("stato") != null && !"nuova".equalsIgnoreCase(recordConvocazione.getAttributeAsString("stato"));
	}
	
	public Record getRecordToSave() {
		
		Record lRecordToSave = new Record(getValuesManager().getValues());
		
		lRecordToSave.setAttribute("organoCollegiale", organoCollegiale);
		
		addFormValues(lRecordToSave, formDatiSeduta);
		
		addFormValues(lRecordToSave, formListaDatiSeduta);
		
		addFormValues(lRecordToSave, formDatiPartecipanti);
		
		return lRecordToSave;
	}
	
	@Override
	public void clearTabErrors() {
		clearTabErrors(tabSet);
	}	
	
	@Override
	public void showTabErrors() {
		showTabErrors(tabSet);
	}

	public static void apriSceltaCommissione(final ServiceCallback<Record> callback, final String listaIdCommissioniConvocateSalvate) {
		
		GWTRestDataSource convocazioneCommissioneDS = new GWTRestDataSource("LoadComboConvocazioneCommissioneSource", "key", FieldType.TEXT);
		convocazioneCommissioneDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getData().length == 0) {
					Layout.addMessage(new MessageBean("Nessuna commissione configurata", "", MessageType.ERROR));
				} else {
					 new SceltaCommissionePopup(response.getData(), listaIdCommissioniConvocateSalvate, callback);
				}
			}
		});
	}
	
	public Date getValueAsDate(String fieldName) {
		return vm.getValue(fieldName) != null ? (Date) vm.getValue(fieldName) : null;
	}
	
}