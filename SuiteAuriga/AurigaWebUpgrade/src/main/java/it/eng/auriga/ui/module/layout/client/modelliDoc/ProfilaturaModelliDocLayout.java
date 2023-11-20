/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.tipoFascicoliAggr.DettaglioTipoFascicoliAggregatiWindow;
import it.eng.auriga.ui.module.layout.client.tipologieDocumentali.DettaglioTipologiaDocumentaleWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.IDocumentItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;

public class ProfilaturaModelliDocLayout extends VLayout {
		
	protected ValuesManager vm;
	protected String idModello;
	protected String nomeModello;
	protected String nomeTabella;
	protected String tipoEntitaAssociata;
	protected String idEntitaAssociata;
	protected String nomeEntitaAssociata;
	protected String profilaVariabileFunctionName;
	
	protected TabSet tabSet;
	protected Tab tabProfilatura;
	protected Tab tabGenerazioneDoc;
	
	protected DynamicForm fileModelloForm;
	protected AllegatiItem fileModelloItem;
	
	protected DetailSection entitaAssociataSection;
	protected DynamicForm entitaAssociataForm;
	protected TextItem nomeEntitaAssociataItem;
	protected ImgButtonItem dettaglioTipoDocButton;
	protected ImgButtonItem dettaglioTipoFolderButton;
	protected ImgButtonItem dettaglioTipoProcButton;
	
	protected DetailSection profilaturaSection;
	protected DynamicForm profilaturaForm;
	protected HiddenItem flgProfCompletaItem;
	protected AssociazioniAttributiCustomItem associazioniAttributiCustomItem;
	
	protected HTMLFlow htmlFlow;
	
	protected VLayout attributiDinamiciLayout;	
	protected AttributiDinamiciDetail attributiDinamiciDetail;
	
	protected Button salvaProfilaturaButton;
	protected Button generaDocDaModelloButton;
	
	public ProfilaturaModelliDocLayout(String pIdModello, String pNomeModello, String pNomeTabella, String pTipoEntitaAssociata, String pIdEntitaAssociata, String pNomeEntitaAssociata, String pRowid, final String pProfilaVariabileFunctionName) {
				
		this.vm = new ValuesManager();
		this.idModello = pIdModello;
		this.nomeModello = pNomeModello;
		this.nomeTabella = pNomeTabella;
		this.tipoEntitaAssociata = pTipoEntitaAssociata;
		this.idEntitaAssociata = pIdEntitaAssociata;
		this.nomeEntitaAssociata = pNomeEntitaAssociata;
		this.profilaVariabileFunctionName = pProfilaVariabileFunctionName;
		
		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		setAutoDraw(false);
		setCanHover(false);
		setPadding(5);
		setMembersMargin(5);
		
		setStyleName(it.eng.utility.Styles.detailLayoutWithTabSet);
		
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		tabSet.setWidth100();
		tabSet.setHeight100();
		tabSet.setBorder("0px");
		tabSet.setCanFocus(false);
		tabSet.setTabIndex(-1);
		tabSet.setPaneMargin(0);
		
		tabProfilatura = new Tab("<b>Profilatura</b>");
		tabProfilatura.setAttribute("tabID", "PROFILATURA");
		tabProfilatura.setPrompt("Profilatura del modello");
		tabProfilatura.setPane(getLayoutTabProfilatura());
		
		tabGenerazioneDoc = new Tab("<b>Generazione doc. da modello</b>");
		tabGenerazioneDoc.setAttribute("tabID", "GENERAZIONE_DOC");
		tabGenerazioneDoc.setPrompt("Generazione del documento a partire dal modello");
		tabGenerazioneDoc.setPane(getLayoutTabGenerazioneDoc());
		tabGenerazioneDoc.addTabSelectedHandler(new TabSelectedHandler() {
			
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				if(validate()) {
					Record record = getRecordToSave();	
					GWTRestDataSource lModelliDocDatasource = new GWTRestDataSource("ModelliDocDatasource");
					lModelliDocDatasource.executecustom("caricaAttributiCustom", record, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record recordAttributiCustom = response.getData()[0];
								RecordList attributiAdd = recordAttributiCustom.getAttributeAsRecordList("attributiAdd");
								if (attributiAdd != null && !attributiAdd.isEmpty()) {
									attributiDinamiciDetail = new AttributiDinamiciDetail("attributiDinamici", attributiAdd, recordAttributiCustom
											.getAttributeAsMap("mappaDettAttrLista"), recordAttributiCustom.getAttributeAsMap("mappaValoriAttrLista"), recordAttributiCustom
											.getAttributeAsMap("mappaVariazioniAttrLista"), recordAttributiCustom.getAttributeAsMap("mappaDocumenti"), null,
											tabSet, "HEADER");
									attributiDinamiciDetail.setCanEdit(true);
									attributiDinamiciLayout.setMembers(attributiDinamiciDetail);															
								}
							}						
						}					
					});
				} else {
					// tolgo il messaggio perchè quando seleziono il tab viene richiamato questo metodo due volte (in mouseDown e in mouseUp)
//					AurigaLayout.addMessage(new MessageBean("Profilatura non completa", "", MessageType.ERROR));						
					tabSet.selectTab(tabProfilatura);						
				}	
			}
		});

		tabSet.addTab(tabProfilatura);
		tabSet.addTab(tabGenerazioneDoc);
		
		addMember(tabSet);
	}
	
	protected VLayout getLayoutTabProfilatura() {
		
		createFileModelloForm();
		createEntitaAssociataSection();
		createProfilaturaSection();
		
		salvaProfilaturaButton = new Button("Salva");   
		salvaProfilaturaButton.setIcon("buttons/save.png");
		salvaProfilaturaButton.setIconSize(16); 
		salvaProfilaturaButton.setAutoFit(false);
		salvaProfilaturaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				salvaProfilatura();
			}
		});
		
		HStack buttons = new HStack(5);
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(salvaProfilaturaButton);
		
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();
		
		VLayout profilaturaLayout = new VLayout();
		profilaturaLayout.setHeight100();
		profilaturaLayout.setWidth100();
		profilaturaLayout.setOverflow(Overflow.AUTO);
		profilaturaLayout.addMember(fileModelloForm);
		profilaturaLayout.addMember(entitaAssociataSection);
		profilaturaLayout.addMember(profilaturaSection);		
		profilaturaLayout.addMember(spacerLayout);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();		
		lVLayout.addMember(profilaturaLayout);
		lVLayout.addMember(buttons);
		lVLayout.setShowResizeBar(true);
		lVLayout.setResizeBarTarget("next");
		
		htmlFlow = new HTMLFlow();
		htmlFlow.setHeight100();
		htmlFlow.setWidth100();
		htmlFlow.setOverflow(Overflow.AUTO);
		htmlFlow.setBackgroundColor("white");
		htmlFlow.setPadding(5);
		
		HLayout lHLayout = new HLayout();
		lHLayout.setHeight100();
		lHLayout.setWidth100();
		lHLayout.setMembers(lVLayout, htmlFlow);
		
		VLayout lTabLayout = new VLayout();
		lTabLayout.setHeight100();
		lTabLayout.setWidth100();
		lTabLayout.setMembers(lHLayout);
		
		return lTabLayout;
	}
	
	protected VLayout getLayoutTabGenerazioneDoc() {
			
		generaDocDaModelloButton = new Button("Genera");   
		generaDocDaModelloButton.setIcon("buttons/gear.png");
		generaDocDaModelloButton.setIconSize(16); 
		generaDocDaModelloButton.setAutoFit(false);
		generaDocDaModelloButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				generaDocDaModello();
			}
		});
		
		HStack buttons = new HStack(5);
		buttons.setHeight(30);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(generaDocDaModelloButton);
		
		attributiDinamiciLayout = new VLayout();
		attributiDinamiciLayout.setHeight100();
		attributiDinamiciLayout.setWidth100();
		
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();		
		lVLayout.addMember(attributiDinamiciLayout);
		lVLayout.addMember(buttons);
		
		VLayout lTabLayout = new VLayout();
		lTabLayout.setHeight100();
		lTabLayout.setWidth100();
		lTabLayout.setMembers(lVLayout);
		
		return lTabLayout;
	}
	
	protected void salvaProfilatura() {
		
		vm.clearErrors(true);		
		Record record = getRecordToSave();
		GWTRestDataSource lModelliDocDatasource = new GWTRestDataSource("ModelliDocDatasource");
		lModelliDocDatasource.executecustom("salvaProfilatura", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					AurigaLayout.addMessage(new MessageBean("Salvataggio profilatura avvenuto con successo", "", MessageType.INFO));								
					Record recordProfilatura = response.getData()[0];
					vm.editRecord(recordProfilatura);					
					if(recordProfilatura.getAttributeAsBoolean("flgProfCompleta") != null && recordProfilatura.getAttributeAsBoolean("flgProfCompleta")) {
//						AurigaLayout.addMessage(new MessageBean("Profilatura completa", "", MessageType.INFO));								
						tabSet.selectTab(tabGenerazioneDoc);
					} else {
						AurigaLayout.addMessage(new MessageBean("Profilatura non completa", "", MessageType.WARNING));			
					}											
				}				
			}			
		});		
	}	
	
	protected void generaDocDaModello() {		
		
		boolean valid = true;
		if (attributiDinamiciDetail != null) {
			for (DynamicForm form : attributiDinamiciDetail.getForms()) {
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
		
		if(valid) {
		
			Record record = getRecordToSave();	
			final Record attributiDinamiciRecord = attributiDinamiciDetail.getRecordToSave();
			record.setAttribute("valori", attributiDinamiciDetail.getMappaValori(attributiDinamiciRecord));
			record.setAttribute("tipiValori", attributiDinamiciDetail.getMappaTipiValori(attributiDinamiciRecord));
			record.setAttribute("colonneListe", attributiDinamiciDetail.getMappaColonneListe(attributiDinamiciRecord));
			GWTRestDataSource lModelliDocDatasource = new GWTRestDataSource("ModelliDocDatasource");
			lModelliDocDatasource.executecustom("generaDocDaModello", record, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record recordPreview = response.getData()[0];
						if (recordPreview.getAttribute("nomeFile") != null && recordPreview.getAttribute("nomeFile").endsWith(".pdf")) {
							PreviewControl.switchPreview(recordPreview.getAttribute("uri"), false, new InfoFileRecord(recordPreview.getAttributeAsRecord("infoFile")), "FileToExtractBean", recordPreview.getAttribute("nomeFile"));
						}else {
							Record lRecord = new Record();
							lRecord.setAttribute("displayFilename", recordPreview.getAttribute("nomeFile"));
							lRecord.setAttribute("uri", recordPreview.getAttribute("uri"));
							lRecord.setAttribute("sbustato", "false");
							lRecord.setAttribute("remoteUri", "false");
							DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");
						}
					}				
				}			
			});	
		
		}
	}
	
	public void profilaVariabile(String nomeVariabile) {
		
		final Record recordToSave = getRecordToSave();	
		if(recordToSave.getAttributeAsRecordList("listaAssociazioniAttributiCustom") != null && recordToSave.getAttributeAsRecordList("listaAssociazioniAttributiCustom").getLength() > 0) {
			for(int i = 0; i < recordToSave.getAttributeAsRecordList("listaAssociazioniAttributiCustom").getLength(); i++) {
				final Record recordAssociazioniAttributiCustom = recordToSave.getAttributeAsRecordList("listaAssociazioniAttributiCustom").get(i);
				if(recordAssociazioniAttributiCustom.getAttribute("nomeVariabileModello").equalsIgnoreCase(nomeVariabile)) {
//					if(recordAssociazioniAttributiCustom.getAttribute("nomeAttributoCustom") != null && !"".equals(recordAssociazioniAttributiCustom.getAttribute("nomeAttributoCustom"))) {
//						SC.say("La variabile " + nomeVariabile + " è associata all'attributo " + recordAssociazioniAttributiCustom.getAttribute("nomeAttributoCustom"));	
//					} else {
//						SC.warn("La variabile " + nomeVariabile + " non è associata a nessun attributo");
//					}
					RecordList listaAssociazioniAttributiCustom = new RecordList();
					listaAssociazioniAttributiCustom.add(recordAssociazioniAttributiCustom);	
					Record recordAssociazioneAttributiCustom = new Record();
					recordAssociazioneAttributiCustom.setAttribute("listaAssociazioniAttributiCustom", listaAssociazioniAttributiCustom);
					final int pos = i;
					AssociaAttributoCustomPopup lAssociaAttributoCustomPopup = new AssociaAttributoCustomPopup("Associa con attributo custom", nomeTabella, idEntitaAssociata, recordAssociazioneAttributiCustom) {
						
						@Override
						public void onOkButtonClick(RecordList listaAssociazioniAttributiCustom) {
							if(listaAssociazioniAttributiCustom != null && listaAssociazioniAttributiCustom.getLength() > 0) {								
								recordToSave.getAttributeAsRecordList("listaAssociazioniAttributiCustom").set(pos, listaAssociazioniAttributiCustom.get(0));
								vm.editRecord(recordToSave);
							}		
						}
					};
					return;
				} else {
					boolean flgComplex = recordAssociazioniAttributiCustom.getAttributeAsBoolean("flgComplex") != null && recordAssociazioniAttributiCustom.getAttributeAsBoolean("flgComplex");
					if(flgComplex && (nomeVariabile.startsWith(recordAssociazioniAttributiCustom.getAttribute("nomeVariabileModello")) || (recordAssociazioniAttributiCustom.getAttribute("aliasVariabileModello") != null && nomeVariabile.startsWith(recordAssociazioniAttributiCustom.getAttribute("aliasVariabileModello"))))) {
						String nomeAttributoComplex;
						boolean isSottoattributoCustom;
						if (recordAssociazioniAttributiCustom.getAttribute("tipoAssociazioneVariabileModello") != null && "attributoCustom".equalsIgnoreCase(recordAssociazioniAttributiCustom.getAttribute("tipoAssociazioneVariabileModello"))) {
							nomeAttributoComplex = recordAssociazioniAttributiCustom.getAttribute("nomeAttributoCustom");
							isSottoattributoCustom = true;
						} else {
							nomeAttributoComplex = recordAssociazioniAttributiCustom.getAttribute("nomeAttributoLibero");
							isSottoattributoCustom = false;
						}
						if(nomeAttributoComplex != null && !"".equals(nomeAttributoComplex)) {
							for(int j = 0; j < recordAssociazioniAttributiCustom.getAttributeAsRecordList("listaAssociazioniSottoAttributiComplex").getLength(); j++) {
								final Record recordAssociazioniSottoAttributiComplex = recordAssociazioniAttributiCustom.getAttributeAsRecordList("listaAssociazioniSottoAttributiComplex").get(j);
								if(recordAssociazioniSottoAttributiComplex.getAttribute("nomeVariabileModello").equalsIgnoreCase(nomeVariabile)) {
//									if(recordAssociazioniSottoAttributiComplex.getAttribute("nomeAttributoCustom") != null && !"".equals(recordAssociazioniSottoAttributiComplex.getAttribute("nomeAttributoCustom"))) {
//										SC.say("La variabile " + nomeVariabile + " è associata al sotto-attributo " + recordAssociazioniSottoAttributiComplex.getAttribute("nomeAttributoCustom") + " di " + recordAssociazioniAttributiCustom.getAttribute("nomeAttributoCustom"));	
//									} else {
//										SC.warn("La variabile " + nomeVariabile + " non è associata a nessun sotto-attributo di " + recordAssociazioniAttributiCustom.getAttribute("nomeAttributoCustom"));
//									}
									RecordList listaAssociazioniSottoAttributiComplex = new RecordList();
									listaAssociazioniSottoAttributiComplex.add(recordAssociazioniSottoAttributiComplex);	
									Record recordAssociazioneSottoAttributiComplex = new Record();
									recordAssociazioneSottoAttributiComplex.setAttribute("nomeAttributoCustom", nomeAttributoComplex);
									recordAssociazioneSottoAttributiComplex.setAttribute("listaAssociazioniSottoAttributiComplex", listaAssociazioniSottoAttributiComplex);
									final int pos = j;
									new AssociazioniSottoAttributiComplexPopup("Associa con sotto-attributo di " + nomeAttributoComplex, isSottoattributoCustom ? nomeTabella : null, isSottoattributoCustom ? idEntitaAssociata : null, recordAssociazioneSottoAttributiComplex) {
										
										@Override
										public void onOkButtonClick(RecordList listaAssociazioniSottoAttributiComplex) {
											if(listaAssociazioniSottoAttributiComplex != null && listaAssociazioniSottoAttributiComplex.getLength() > 0) {	
												recordAssociazioniAttributiCustom.getAttributeAsRecordList("listaAssociazioniSottoAttributiComplex").set(pos, listaAssociazioniSottoAttributiComplex.get(0));
												vm.editRecord(recordToSave);
											}			
										}
									};									
									return;
								}								
							}
						} else {
//							SC.warn("La variabile " + nomeVariabile + " non è associata a nessun sotto-attributo");
							RecordList listaAssociazioniAttributiCustom = new RecordList();
							listaAssociazioniAttributiCustom.add(recordAssociazioniAttributiCustom);	
							Record recordAssociazioneAttributiCustom = new Record();
							recordAssociazioneAttributiCustom.setAttribute("listaAssociazioniAttributiCustom", listaAssociazioniAttributiCustom);
							final int pos = i;
							AssociaAttributoCustomPopup lAssociaAttributoCustomPopup = new AssociaAttributoCustomPopup("Associa con attributo custom", nomeTabella, idEntitaAssociata, recordAssociazioneAttributiCustom) {
								
								@Override
								public void onOkButtonClick(RecordList listaAssociazioniAttributiCustom) {
									if(listaAssociazioniAttributiCustom != null && listaAssociazioniAttributiCustom.getLength() > 0) {								
										recordToSave.getAttributeAsRecordList("listaAssociazioniAttributiCustom").set(pos, listaAssociazioniAttributiCustom.get(0));
										vm.editRecord(recordToSave);
									}						
								}
							};
							return;
						}						
					}
				}
			}
		}		
	}	
	
	protected void createFileModelloForm() {
		
		fileModelloForm = new DynamicForm();
		fileModelloForm.setValuesManager(vm);
		fileModelloForm.setWidth100();
		fileModelloForm.setPadding(5);
		fileModelloForm.setWrapItemTitles(false);
		fileModelloForm.setNumCols(10);
		fileModelloForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		fileModelloForm.setVisibility(Visibility.HIDDEN);
		
		fileModelloItem = new AllegatiItem() {
			
			@Override
			public String getTitleNomeFileAllegato() {
				return "Seleziona modello odt";
			}
			
			@Override
			public boolean validateFormatoFileAllegato(InfoFileRecord lInfoFileRecord) {									
				if (lInfoFileRecord.isFirmato() || !lInfoFileRecord.getCorrectFileName().toLowerCase().endsWith(".odt")) {
					return false;
				}
				return true;	
			}

			@Override
			public String getFormatoFileNonValidoErrorMessage() {
				return "Il file non è un odt come atteso";
			}

			@Override
			public boolean showNumeroAllegato() {
				return false;
			};

			@Override
			public boolean showTipoAllegato() {
				return false;
			}

			@Override
			public boolean showDescrizioneFileAllegato() {
				return false;
			}
			
			@Override
			public boolean isHideAcquisisciDaScannerInAltreOperazioniButton() {
				return true;
			}
			
			@Override
			public boolean isHideFirmaInAltreOperazioniButton() {
				return true;
			}
			
			@Override
			public boolean isHideTimbraInAltreOperazioniButton() {
				return true;
			}
			
			@Override
			public boolean getShowVersioneOmissis() {
				return false;
			}
			
			@Override
			public void manageOnChanged() {
				manageAttivaProfilaturaModello();				
			}
		};
		fileModelloItem.setName("listaModelli");
		fileModelloItem.setShowTitle(false);
		fileModelloItem.setNotReplicable(true);
		fileModelloItem.setAttribute("obbligatorio", true);
		fileModelloItem.setHideVisualizzaVersioniButton(true);
				
		fileModelloForm.setFields(fileModelloItem);		
	}
	
	protected void createEntitaAssociataSection() {

		createEntitaAssociataForm();

		entitaAssociataSection = new DetailSection("Oggetto associato al modello", true, true, true, entitaAssociataForm);		
	}
	
	protected void createEntitaAssociataForm() {
		
		entitaAssociataForm = new DynamicForm();
		entitaAssociataForm.setValuesManager(vm);
		entitaAssociataForm.setWidth100();
		entitaAssociataForm.setPadding(5);
		entitaAssociataForm.setWrapItemTitles(false);
		entitaAssociataForm.setNumCols(10);
		entitaAssociataForm.setColWidths(120, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		String titleNomeEntitaAssociataItem = "Tipo oggetto";
		if(tipoEntitaAssociata != null) {
			if("TD".equals(tipoEntitaAssociata)) {
				titleNomeEntitaAssociataItem = "Tipo documento";
			} else if("TF".equals(tipoEntitaAssociata)) {
				titleNomeEntitaAssociataItem = "Tipo folder";
			} else if("TP".equals(tipoEntitaAssociata)) {
				titleNomeEntitaAssociataItem = "Tipo di processo/procedimento";
			}
		}
		
		nomeEntitaAssociataItem = new TextItem("nomeEntitaAssociata", titleNomeEntitaAssociataItem);
		nomeEntitaAssociataItem.setDefaultValue(nomeEntitaAssociata);
		nomeEntitaAssociataItem.setCanEdit(false);
				
		dettaglioTipoDocButton = new ImgButtonItem("dettaglioTipoDocButton", "buttons/detail.png", "Apri dettaglio tipologia documentale " + nomeEntitaAssociata);
		dettaglioTipoDocButton.setAlwaysEnabled(true);
		dettaglioTipoDocButton.setColSpan(1);
		dettaglioTipoDocButton.setEndRow(false);		
		dettaglioTipoDocButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record recordTipoDoc = new Record();
				recordTipoDoc.setAttribute("idTipoDoc", idEntitaAssociata);
				recordTipoDoc.setAttribute("nomeTipoDoc", nomeEntitaAssociata);
				String title = "Dettaglio tipologia documentale " + nomeEntitaAssociata;
				DettaglioTipologiaDocumentaleWindow dettaglioTipologiaDocumentaleWindow = new DettaglioTipologiaDocumentaleWindow(recordTipoDoc, title) {
					
					@Override
					public void manageOnCloseClickAfterSaved() {
						reloadCombo();
					}
				};
			}
		});
		dettaglioTipoDocButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoEntitaAssociata != null && "TD".equalsIgnoreCase(tipoEntitaAssociata);			
			}
		});
		
		dettaglioTipoFolderButton = new ImgButtonItem("dettaglioTipoFolderButton", "buttons/detail.png", "Apri dettaglio tipologia fascicoli e altri aggregati " + nomeEntitaAssociata);
		dettaglioTipoFolderButton.setAlwaysEnabled(true);
		dettaglioTipoFolderButton.setColSpan(1);
		dettaglioTipoFolderButton.setEndRow(false);		
		dettaglioTipoFolderButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record recordTipoFolder = new Record();
				recordTipoFolder.setAttribute("idFolderType", idEntitaAssociata);
				recordTipoFolder.setAttribute("nome", nomeEntitaAssociata);
				String title = "Dettaglio tipologia fascicoli e altri aggregati " + nomeEntitaAssociata;
				DettaglioTipoFascicoliAggregatiWindow dettaglioTipoFascicoliAggregatiWindow = new DettaglioTipoFascicoliAggregatiWindow(recordTipoFolder, title) {
					
					@Override
					public void manageOnCloseClickAfterSaved() {
						reloadCombo();
					}
				};
			}
		});
		dettaglioTipoFolderButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoEntitaAssociata != null && "TF".equalsIgnoreCase(tipoEntitaAssociata);
			}
		});
		
		dettaglioTipoProcButton = new ImgButtonItem("dettaglioTipoProcButton", "buttons/detail.png", "Apri dettaglio tipologia processi/procedimenti " + nomeEntitaAssociata);
		dettaglioTipoProcButton.setAlwaysEnabled(true);
		dettaglioTipoProcButton.setColSpan(1);
		dettaglioTipoProcButton.setEndRow(false);		
		dettaglioTipoProcButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				/*TODO apre il dettaglio del tipo procedimento associato, dove devono essere gestiti i metadati specifici (attributi custom)
				Record recordTipoProc = new Record();
				recordTipoProc.setAttribute("idProcessType", idEntitaAssociata);
				recordTipoProc.setAttribute("nomeProcessType", nomeEntitaAssociata);
				String title = "Dettaglio tipologia processi/procedimenti " + nomeEntitaAssociata;
				DettaglioTipoProcedimentiWindow dettaglioTipoProcedimentiWindow = new DettaglioTipoProcedimentiWindow(recordTipoProc, title) {
					
					@Override
					public void manageOnCloseClickAfterSaved() {
						reloadCombo();
					}
				};
				*/
			}
		});
		dettaglioTipoProcButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoEntitaAssociata != null && "TP".equalsIgnoreCase(tipoEntitaAssociata);
			}
		});
		
		entitaAssociataForm.setFields(nomeEntitaAssociataItem, dettaglioTipoDocButton, dettaglioTipoFolderButton, dettaglioTipoProcButton);
	}
	
	protected void createProfilaturaSection() {

		createProfilaturaForm();

		profilaturaSection = new DetailSection("Associazioni con gli attributi custom", true, true, true, profilaturaForm);
		profilaturaSection.setVisibility(Visibility.HIDDEN);
	}
	
	protected void createProfilaturaForm() {

		profilaturaForm = new DynamicForm();
		profilaturaForm.setValuesManager(vm);
		profilaturaForm.setWidth100();
		profilaturaForm.setPadding(5);
		profilaturaForm.setWrapItemTitles(false);
		profilaturaForm.setNumCols(10);
		profilaturaForm.setColWidths(120, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		flgProfCompletaItem = new HiddenItem("flgProfCompleta");
		
		associazioniAttributiCustomItem = new AssociazioniAttributiCustomItem() {
			
			@Override
			public String getNomeTabella() {
				return nomeTabella;
			};
			
			@Override
			public String getIdEntitaAssociata() {
				return idEntitaAssociata;
			};
			
			@Override
			public boolean isAssociazioneSottoAttributo() {
				return false;
			}
			
		};
		associazioniAttributiCustomItem.setName("listaAssociazioniAttributiCustom");
		associazioniAttributiCustomItem.setShowTitle(false);
		associazioniAttributiCustomItem.setNotReplicable(true);
		associazioniAttributiCustomItem.setAttribute("obbligatorio", true);
		
		profilaturaForm.setFields(flgProfCompletaItem, associazioniAttributiCustomItem);
	}
	
	protected void manageAttivaProfilaturaModello() {
		Record record = getRecordToSave();
		if(record.getAttributeAsRecordList("listaModelli") != null && record.getAttributeAsRecordList("listaModelli").getLength() > 0) {
			String uriModello = record.getAttributeAsRecordList("listaModelli").get(0).getAttribute("uriFileAllegato");
			if(uriModello != null && !"".equals(uriModello)) {
				GWTRestDataSource lModelliDocDatasource = new GWTRestDataSource("ModelliDocDatasource");
				lModelliDocDatasource.addParam("profilaVariabileFunctionName", profilaVariabileFunctionName);
				Layout.showWaitPopup("Caricamento in corso...");
				lModelliDocDatasource.executecustom("caricaProfilatura", record, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						Layout.hideWaitPopup();
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record recordProfilatura = response.getData()[0];
							vm.editRecord(recordProfilatura);
							htmlFlow.setContents(recordProfilatura.getAttribute("html"));
							profilaturaSection.show();													  
						}				
					}			
				});
			} else {
				htmlFlow.setContents("");
				profilaturaSection.hide();
			}
		}			
	}
	
	public Record getRecordToSave() {		
		Record record = new Record(vm.getValues());		
		record.setAttribute("idModello", idModello);		
		record.setAttribute("nomeModello", nomeModello);		
		record.setAttribute("nomeTabella", nomeTabella);
		record.setAttribute("tipoEntitaAssociata", tipoEntitaAssociata);
		record.setAttribute("idEntitaAssociata", idEntitaAssociata);
		record.setAttribute("nomeEntitaAssociata", nomeEntitaAssociata);
		record.setAttribute("html", "");
		return record;
	}
	
	public Boolean validate() {		
		vm.clearErrors(true);
		Boolean valid = vm.validate();		
		if(vm.getErrors() != null && !vm.getErrors().isEmpty()) {
			for(Object key : vm.getErrors().keySet()) {
				FormItem item = vm.getItem((String) key);
				if(item != null && item.getForm() != null && item.getForm().getDetailSection() != null) {
					item.getForm().getDetailSection().open();
				}
			}
		}		
		for (DynamicForm form : vm.getMembers()) {
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
		return valid;
	}
	
	public void reloadCombo() {
		associazioniAttributiCustomItem.reloadCombo();
	}
	
	public void nuovoDettaglio() {
		fileModelloForm.show();
		vm.clearErrors(true);
		vm.editNewRecord();
		setCanEdit(true);
	}
	
	public void caricaDettaglio(Record record) {
		fileModelloForm.hide();
		vm.clearErrors(true);
		vm.editRecord(record);
		setCanEdit(true);
		manageAttivaProfilaturaModello();
	}
		
	public void setCanEdit(boolean canEdit) {
		for (DynamicForm form : vm.getMembers()) {
			setCanEdit(canEdit, form);
		}
		nomeEntitaAssociataItem.setCanEdit(false);
	}
	
	public void setCanEdit(boolean canEdit, DynamicForm form) {
		if (form != null) {
			for (FormItem item : form.getFields()) {
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(canEdit);
					item.redraw();
				}
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
}
