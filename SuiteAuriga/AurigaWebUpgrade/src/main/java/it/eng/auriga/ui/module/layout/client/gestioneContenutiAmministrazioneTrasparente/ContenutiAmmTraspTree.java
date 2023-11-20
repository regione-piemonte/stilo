/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomSimpleTree;

public class ContenutiAmmTraspTree extends CustomSimpleTree {
	
	private String idNodeToScroll;

	public ContenutiAmmTraspTree(final String nomeEntita) {

		super(nomeEntita);

		TreeGridField idSezione = new TreeGridField("idSezione");
		idSezione.setHidden(true);

		TreeGridField nome = new TreeGridField("nome");
		nome.setShowHover(true);
		nome.setCellFormatter(new CellFormatter() {							
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,int colNum) {
				if(record != null) {
					record.setAttribute("realValue" + colNum, value);
				}
				if (value == null) return null;
				String lStringValue = value.toString();
				if (lStringValue.length() > 50){
					return lStringValue.substring(0, 50) + "...";
				} else return lStringValue;
			}
		});
		nome.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				Object realValue = record != null ? record.getAttribute("realValue" + colNum) : null;								
				String nome = (realValue != null) ? (String) realValue : (String) value;
				String dettagli = (record != null && record.getAttributeAsString("dettagli") !=null) ? record.getAttributeAsString("dettagli") : "";
				return "<b>" + nome + "</b><br/>" + dettagli;
			}
		});
		
		addDataArrivedHandler(new DataArrivedHandler() {
			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				if(idNodeToScroll != null && !"".equalsIgnoreCase(idNodeToScroll)) {
					final int index = getDataAsRecordList().findIndex("idNode", idNodeToScroll);								
					if(index >= 0) {													
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {
							@Override
							public void execute() {	
								try {
									scrollToRow(index);
								} catch(Exception e) {
								}
							}
						});													
					}		
				}
			}
		});

		setFields(idSezione, nome);
	}

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (layout.isLookup() && record != null) {
			if (record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}
		}
		return super.getCellCSSText(record, rowNum, colNum);
	}

	
	@Override
	public void manageOnCellClick(final Record record, int recordNum) {

		final String idNode = record.getAttributeAsString("idNode");
		String idNodeSelected = getSelectedRecord() != null ? getSelectedRecord().getAttribute("idNode") : null;
				
		final Boolean showRifNormativiButton = ((record.getAttributeAsBoolean("showRifNormativiButton") != null && record.getAttributeAsBoolean("showRifNormativiButton")) ? record.getAttributeAsBoolean("showRifNormativiButton") : false );
		final Boolean showHeaderButton = ((record.getAttributeAsBoolean("showHeaderButton") != null && record.getAttributeAsBoolean("showHeaderButton")) ? record.getAttributeAsBoolean("showHeaderButton") : false );
		final Boolean showTitoloSezioneNewButton = ((record.getAttributeAsBoolean("showTitoloSezioneNewButton") != null && record.getAttributeAsBoolean("showTitoloSezioneNewButton")) ? record.getAttributeAsBoolean("showTitoloSezioneNewButton") : false );
		final Boolean showFineSezioneNewButton = ((record.getAttributeAsBoolean("showFineSezioneNewButton") != null && record.getAttributeAsBoolean("showFineSezioneNewButton")) ? record.getAttributeAsBoolean("showFineSezioneNewButton")  : false );
		final Boolean showParagrafoNewButton = ((record.getAttributeAsBoolean("showParagrafoNewButton") != null && record.getAttributeAsBoolean("showParagrafoNewButton")) ? record.getAttributeAsBoolean("showParagrafoNewButton") : false );
		final Boolean showDocumentoSempliceNewButton = ((record.getAttributeAsBoolean("showDocumentoSempliceNewButton") != null && record.getAttributeAsBoolean("showDocumentoSempliceNewButton")) ? record.getAttributeAsBoolean("showDocumentoSempliceNewButton") : false );
		final Boolean showDocumentoConAllegatiNewButton = ((record.getAttributeAsBoolean("showDocumentoConAllegatiNewButton") != null && record.getAttributeAsBoolean("showDocumentoConAllegatiNewButton")) ? record.getAttributeAsBoolean("showDocumentoConAllegatiNewButton") : false );
		final Boolean showTabellaNewButton = ((record.getAttributeAsBoolean("showTabellaNewButton") != null && record.getAttributeAsBoolean("showTabellaNewButton")) ? record.getAttributeAsBoolean("showTabellaNewButton") : false );
		final Boolean showContenutiTabellaButton = ((record.getAttributeAsBoolean("showContenutiTabellaButton") != null && record.getAttributeAsBoolean("showContenutiTabellaButton")) ? record.getAttributeAsBoolean("showContenutiTabellaButton") : false );
		
		if (idNodeSelected == null || !idNodeSelected.equals(idNode)) {

			// Leggo i flag flgPresenteHeader , flgPresenteRifNormativi
			Record recordFlgHeaderRifNormativi = new Record();
			recordFlgHeaderRifNormativi.setAttribute("idSezione", idNode);
			
			final GWTRestDataSource contenutiAmmTraspDS = new GWTRestDataSource("ContenutiAmmTraspDatasource", "idSezione", FieldType.TEXT);
			
			contenutiAmmTraspDS.executecustom("leggiFlgHeaderRifNormativi", recordFlgHeaderRifNormativi, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS){
						if (response.getData() != null) {
							Record lRecordDb    = response.getData()[0];
							String flgPresenteHeader       = lRecordDb.getAttribute("flgPresenteHeader");
							String flgPresenteRifNormativi = lRecordDb.getAttribute("flgPresenteRifNormativi");
							
							String nomeNodo = record.getAttributeAsString("nome") != null ? record.getAttributeAsString("nome") : "";
							String searchNodeLabelContents = nomeNodo;
							Record rec = record;
							while (rec.getAttributeAsString("parentId") != null && !"".equals(rec.getAttributeAsString("parentId"))) {
								Record parent = getData().findById(rec.getAttributeAsString("parentId"));
								searchNodeLabelContents = parent.getAttributeAsString("nome") + "&nbsp;/&nbsp;" + searchNodeLabelContents;
								rec = parent;
							}

							((ContenutiAmmTraspLayout) layout).setSearchNodeLabelContents(searchNodeLabelContents);
							((ContenutiAmmTraspLayout) layout).setIdNode(idNode);
							((ContenutiAmmTraspLayout) layout).setFlgPresenteHeader(flgPresenteHeader);
							((ContenutiAmmTraspLayout) layout).setFlgPresenteRifNormativi(flgPresenteRifNormativi);
							((ContenutiAmmTraspLayout) layout).setShowRifNormativiButton(showRifNormativiButton);
							((ContenutiAmmTraspLayout) layout).setShowHeaderButton(showHeaderButton);
							((ContenutiAmmTraspLayout) layout).setShowTitoloSezioneNewButton(showTitoloSezioneNewButton);
							((ContenutiAmmTraspLayout) layout).setShowFineSezioneNewButton(showFineSezioneNewButton);
							((ContenutiAmmTraspLayout) layout).setShowParagrafoNewButton(showParagrafoNewButton);
							((ContenutiAmmTraspLayout) layout).setShowDocumentoSempliceNewButton(showDocumentoSempliceNewButton);
							((ContenutiAmmTraspLayout) layout).setShowDocumentoConAllegatiNewButton(showDocumentoConAllegatiNewButton);
							((ContenutiAmmTraspLayout) layout).setShowTabellaNewButton(showTabellaNewButton);
							((ContenutiAmmTraspLayout) layout).setShowContenutiTabellaButton(showContenutiTabellaButton);							
							((ContenutiAmmTraspLayout) layout).refreshCustomTopListButtons();
							((ContenutiAmmTraspLayout) layout).refreshCustomBottomListButtons();
							
							layout.changeLayout("contenuti_amministrazione_trasparente", 
												contenutiAmmTraspDS, 
									            new ConfigurableFilter("contenuti_amministrazione_trasparente"),
									            
									            new ContenutiAmmTraspList("contenuti_amministrazione_trasparente", 
									            		                  showRifNormativiButton,
									            		                  showHeaderButton,
									            		                  showTitoloSezioneNewButton, 
									            		                  showFineSezioneNewButton,
									            		                  showParagrafoNewButton,
									            		                  showDocumentoSempliceNewButton,
									            		                  showDocumentoConAllegatiNewButton,
									            		                  showTabellaNewButton,
									            		                  showContenutiTabellaButton),
									            
									            new ContenutiAmmTraspDetail("contenuti_amministrazione_trasparente")
									           );
							layout.setDetailAuto(false);
							layout.getRightLayout().show();
							selectSingleRecord(record);
							currentRecord = record;
							layout.markForRedraw();
							layout.reloadListAndFilter();
							layout.setMultiselect(false);
							layout.doSearch();
						}
					}
				}						
			});						
		}
	}
	
	@Override
	public void manageContextClick(Record record) {
		
		if (record != null) {
			showRowContextMenu(record);	
		}
	}

	public void showRowContextMenu(Record record) {
		
		contextMenu = new Menu();
		contextMenu.setOverflow(Overflow.VISIBLE);
		contextMenu.setShowIcons(true);
		contextMenu.setSelectionType(SelectionStyle.SINGLE);
		contextMenu.setCanDragRecordsOut(false);
		contextMenu.setWidth("*");
		contextMenu.setHeight("*");
		contextMenu = createContextMenu(record);
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				if (contextMenu.getItems() != null && contextMenu.getItems().length > 0) {
					contextMenu.showContextMenu();
				}
			}
		});
	}
	
	public Menu createContextMenu(final Record record) {
		
		Menu contextMenu = new Menu();
		
		String idSezione = record.getAttributeAsString("idNode");
		
		boolean showContextMenuTree = record.getAttributeAsBoolean("showContextMenuTree") != null && record.getAttributeAsBoolean("showContextMenuTree");
		boolean showAggiungiSottoSezioneMenu = record.getAttributeAsBoolean("showAggiungiSottoSezioneMenu") != null && record.getAttributeAsBoolean("showAggiungiSottoSezioneMenu");
		
		boolean flgSezApertaATutti = record.getAttributeAsBoolean("flgSezApertaATutti") != null && record.getAttributeAsBoolean("flgSezApertaATutti");							
		
		if (showContextMenuTree){
			
			if (showAggiungiSottoSezioneMenu) {
				// Inserisco il menu' "Aggiungi sotto-sezione" nel context-menu'
				MenuItem aggiungiSottoSezioneMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_aggiungiSottoSezioneMenuItem_title(), "buttons/add.png");
				aggiungiSottoSezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						aggiungiSottoSezioneClick(record);
					}
				});
				contextMenu.addItem(aggiungiSottoSezioneMenuItem);
		 	}
			
			// Inserisco il menu' "Aggiungi sezione successiva" nel context-menu'
			MenuItem aggiungiSezioneSuccessivaMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_aggiungiSezioneSuccessivaMenuItem_title(), "buttons/add.png");
			aggiungiSezioneSuccessivaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					aggiungiSezioneSuccessivaClick(record);
				}
			});
			contextMenu.addItem(aggiungiSezioneSuccessivaMenuItem);
			
			// Inserisco il menu' "Modifica nome" nel context-menu'
			MenuItem modificaNomeSezioneMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_modificaNomeSezioneMenuItem_title(), "buttons/modify.png");
			modificaNomeSezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					modificaNomeSezioneClick(record);
				}
			});
			contextMenu.addItem(modificaNomeSezioneMenuItem);
			
			// Inserisco il menu' "Elimina/metti off-line sezione" nel context-menu'
			MenuItem eliminaSezioneMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_eliminaSezioneMenuItem_title(), "buttons/cestino.png");
			eliminaSezioneMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					eliminaSezioneClick(record);
				}
			});	
			contextMenu.addItem(eliminaSezioneMenuItem);
			
			if(!flgSezApertaATutti) {
				// Inserisco il menu' "Strutture abilitate" nel context-menu'
				MenuItem uoAbilitateMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_uoAbilitateMenuItem_title(), "lookup/organigramma.png");
				uoAbilitateMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	
					@Override
					public void onClick(MenuItemClickEvent event) {
						uoAbilitateClick(record, "lookup/organigramma.png");
					}
				});
				contextMenu.addItem(uoAbilitateMenuItem);
			}
						
			// Inserisco il menu' "Utenti abilitati" nel context-menu'
			MenuItem utentiAbilitatiMenuItem = new MenuItem(I18NUtil.getMessages().contenuti_amministrazione_trasparente_utentiAbilitatiMenuItem_title(), "anagrafiche/soggetti/flgPersFisica/persona_fisica.png");
			utentiAbilitatiMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					utentiAbilitatiClick(record, "anagrafiche/soggetti/flgPersFisica/persona_fisica.png");
				}
			});
			contextMenu.addItem(utentiAbilitatiMenuItem);
		}
		
		contextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));
		
		if (showContextMenuTree){
		
			// Questi li aggiungo alla fine, fuori dall'ordinamento
			
			MenuItem separatorMenuItem = new MenuItem(" ");
			separatorMenuItem.setIsSeparator(true);
			contextMenu.addItem(separatorMenuItem);
			
			MenuItem sezApertaATuttiMenuItem = new MenuItem("Sezione aperta a tutti");
			sezApertaATuttiMenuItem.setChecked(flgSezApertaATutti);
			sezApertaATuttiMenuItem.addClickHandler(new ClickHandler() {  
	            public void onClick(MenuItemClickEvent event) {  
	            	MenuItem menuItem = event.getItem();
	            	menuItem.setChecked(!menuItem.getChecked());
	            	record.setAttribute("flgSezApertaATutti", menuItem.getChecked());
	        		sezApertaATuttiClick(record);
	            }
	        });
			contextMenu.addItem(sezApertaATuttiMenuItem);
				
			// Incolla contenuto
			MenuItem incollaMenuItem = new MenuItem("Incolla", "incolla.png");
			incollaMenuItem.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					if (((ContenutiAmmTraspLayout) layout).getCutNodeContenuto() != null) {
						((ContenutiAmmTraspLayout) layout).paste(record,false);
					}
				}
			});

			// Se l'id sezione selezionato e' diverso da quello in cui e' stato fatto il taglia 
			if (((ContenutiAmmTraspLayout) layout).getCutNodeContenuto() != null  && !((ContenutiAmmTraspLayout) layout).getCutNodeContenuto().getAttribute("idSezione").equals(idSezione)) {
				incollaMenuItem.setEnabled(true);
			} else {
				incollaMenuItem.setEnabled(false);
			}
			contextMenu.addItem(incollaMenuItem);
		}
		
		return contextMenu;		
	}
	
	public void aggiungiSottoSezioneClick(final Record recordIn){
		
		Record record = new Record();
		SezioneTreePopup sezioneTreePopup = new SezioneTreePopup(layout, record, I18NUtil.getMessages().contenuti_amministrazione_trasparente_aggiungiSottoSezioneMenuItem_title(), "buttons/add.png") {
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {	
				
				// IdSezioneIO = non valorizzato 
				// IdSezionePrecIn = non valorizzata
				// IdSezionePadreIn = id. sezione del nodo selezionato 
				// NomeSezioneIn = nome compilato nella pop-up
				formRecord.setAttribute("idSezione", (String)null);
				formRecord.setAttribute("idSezionePrec", (String)null);
				formRecord.setAttribute("idSezionePadre", recordIn.getAttributeAsString("idNode"));
				
				GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");
				lDataSource.addData(formRecord, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							Layout.addMessage(new MessageBean("Sezione aggiunta con successo", "", MessageType.INFO));
							if(layout != null) {
								reloadSubTreeFrom(result.getAttributeAsString("idSezionePadre"));
							}
						} 
						Layout.hideWaitPopup();
					}
				});	
				
				markForDestroy();
			}
		};
		sezioneTreePopup.show();
	}
	
	public void aggiungiSezioneSuccessivaClick(final Record recordIn){
		
		Record record = new Record();
		SezioneTreePopup sezioneTreePopup = new SezioneTreePopup(layout, record, I18NUtil.getMessages().contenuti_amministrazione_trasparente_aggiungiSezioneSuccessivaMenuItem_title(), "buttons/add.png") {
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {	
				
				// IdSezioneIO = non valorizzato
				// IdSezionePrecIn = id. sezione del nodo selezionato
				// IdSezionePadreIn = id. nodo padre del nodo selezionato 
				// NomeSezioneIn = nome compilato nella pop-up
				formRecord.setAttribute("idSezione", (String)null);
				formRecord.setAttribute("idSezionePrec",  recordIn.getAttributeAsString("idNode"));
				formRecord.setAttribute("idSezionePadre", recordIn.getAttributeAsString("parentId"));

				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
				
				GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");
				lDataSource.addData(formRecord, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							Layout.addMessage(new MessageBean("Sezione aggiunta con successo", "", MessageType.INFO));
							if(layout != null) {
								reloadSubTreeFrom(result.getAttributeAsString("idSezionePrec"));
							}
						} 
						Layout.hideWaitPopup();
					}
				});	
				
				markForDestroy();
			}
		};
		sezioneTreePopup.show();
	}
	
	public void modificaNomeSezioneClick(final Record recordIn){
		
		Record record = new Record();
		record.setAttribute("nome", recordIn.getAttributeAsString("nome"));
		SezioneTreePopup sezioneTreePopup = new SezioneTreePopup(layout, record, I18NUtil.getMessages().contenuti_amministrazione_trasparente_modificaNomeSezioneMenuItem_title(), "buttons/modify.png") {
			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {	
				
				// IdSezioneIO = id. sezione del nodo selezionato
				// IdSezionePrecIn = non valorizzata
				// IdSezionePadreIn = id. sezione del nodo padre
				// NomeSezioneIn = nome compilato nella pop-up 				
				formRecord.setAttribute("idSezione",      recordIn.getAttributeAsString("idNode"));
				formRecord.setAttribute("idSezionePrec",  (String)null);
				formRecord.setAttribute("idSezionePadre", recordIn.getAttributeAsString("parentId"));
				formRecord.setAttribute("flgSezApertaATutti", recordIn.getAttributeAsBoolean("flgSezApertaATutti") != null && recordIn.getAttributeAsBoolean("flgSezApertaATutti"));

				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
				
				GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");				
				lDataSource.updateData(formRecord, new DSCallback() {
					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							Layout.addMessage(new MessageBean("Sezione modificata con successo", "", MessageType.INFO));
							if(layout != null) {
								reloadSubTreeFrom(result.getAttributeAsString("idSezionePadre"));
							}
						} 
						Layout.hideWaitPopup();					
					}
				});
				
				markForDestroy();
			}
		};
		sezioneTreePopup.show();
	}
	
	public void eliminaSezioneClick(final Record recordIn){
		
		MotivazioneCancellazionePopup motivazioneCancellazionePopup = new MotivazioneCancellazionePopup(I18NUtil.getMessages().contenuti_amministrazione_trasparente_eliminaSezioneMenuItem_title()) {
			
			@Override
			public void onClickOkButton(Record formRecord, final DSCallback callback) {
				
				// IdSezioneIn l'ID del nodo selezionato
				// MotivoIn i motivi compilati nella pop-up
				formRecord.setAttribute("idSezione", recordIn.getAttributeAsString("idNode"));
				formRecord.setAttribute("parentId",  recordIn.getAttributeAsString("parentId"));
				
				Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
				
				GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");				
				lDataSource.performCustomOperation("removeSezione", formRecord, new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {							
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];										
							Layout.addMessage(new MessageBean("Sezione elimina con successo", "", MessageType.INFO));
							if(layout != null) {
								reloadSubTreeFrom(result.getAttributeAsString("parentId"));
							}				
						} 
						Layout.hideWaitPopup();
					}
				}, new DSRequest());
				
				markForDestroy();
			}
		};
		motivazioneCancellazionePopup.show();	
	}
	
	public void uoAbilitateClick(final Record recordIn, final String iconIn){
		
		Record record = new Record();
		
		// IdSezioneIn l'ID del nodo selezionato
		record.setAttribute("idSezione", recordIn.getAttributeAsString("idNode"));

		// TipoEntitaIn -- (valori UO/UT) Se UO indica uffici, UT utenti
		record.setAttribute("tipoEntita", "UO");
		
		// leggo le UO abilitate e i privilegi
		GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");				
		lDataSource.performCustomOperation("leggiEntitaAbilitate", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {							
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];	
					
					UoAbilitatePopup uoAbilitatePopup = new UoAbilitatePopup(I18NUtil.getMessages().contenuti_amministrazione_trasparente_uoAbilitateMenuItem_title(), iconIn) {
						@Override
						public void onClickOkButton(Record formRecord, DSCallback callback) {	
							
							// IdSezioneIn l'ID del nodo selezionato
							formRecord.setAttribute("idSezione",  recordIn.getAttributeAsString("idNode"));
							formRecord.setAttribute("parentId",   recordIn.getAttributeAsString("parentId"));
							
							// TipoEntitaIn -- (valori UO/UT) Se UO indica uffici, UT utenti
							formRecord.setAttribute("tipoEntita", "UO");
														
							Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
							
							GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");
							
							lDataSource.performCustomOperation("salvaEntitaAbilitate", formRecord, new DSCallback() {							
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {							
									if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record result = response.getData()[0];										
										Layout.addMessage(new MessageBean("Salvataggio effettuato con successo", "", MessageType.INFO));
							
										if(layout != null) {
											reloadSubTreeFrom(result.getAttributeAsString("parentId"));
										}
									} 
									Layout.hideWaitPopup();
								}
							}, new DSRequest());
							
							markForDestroy();
						}
					};
					uoAbilitatePopup.setValues(result);
					
					uoAbilitatePopup.show();
				} 
				Layout.hideWaitPopup();
			}
		}, new DSRequest());
	}
	
	public void utentiAbilitatiClick(final Record recordIn, final String iconIn){
		
		Record record = new Record();
		
		// IdSezioneIn l'ID del nodo selezionato
		record.setAttribute("idSezione", recordIn.getAttributeAsString("idNode"));

		// TipoEntitaIn -- (valori UO/UT) Se UO indica uffici, UT utenti
		record.setAttribute("tipoEntita", "UT");
		
		// leggo le UO abilitate e i privilegi
		GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");				
		lDataSource.performCustomOperation("leggiEntitaAbilitate", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {							
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];	
					
					UtentiAbilitatiPopup utentiAbilitatiPopup = new UtentiAbilitatiPopup(I18NUtil.getMessages().contenuti_amministrazione_trasparente_utentiAbilitatiMenuItem_title(), iconIn) {
						@Override
						public void onClickOkButton(Record formRecord, DSCallback callback) {	
							
							// IdSezioneIn l'ID del nodo selezionato
							formRecord.setAttribute("idSezione",  recordIn.getAttributeAsString("idNode"));
							formRecord.setAttribute("parentId",   recordIn.getAttributeAsString("parentId"));
							
							// TipoEntitaIn -- (valori UO/UT) Se UO indica uffici, UT utenti
							formRecord.setAttribute("tipoEntita", "UT");
							
							Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
							
							GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");
							
							lDataSource.performCustomOperation("salvaEntitaAbilitate", formRecord, new DSCallback() {							
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {							
									if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record result = response.getData()[0];										
										Layout.addMessage(new MessageBean("Salvataggio effettuato con successo", "", MessageType.INFO));
							
										if(layout != null) {
											reloadSubTreeFrom(result.getAttributeAsString("parentId"));
										}
									} 
									Layout.hideWaitPopup();
								}
							}, new DSRequest());
							
							markForDestroy();
						}
					};
					utentiAbilitatiPopup.show();
					
					utentiAbilitatiPopup.setValues(result);
					
				} 
				Layout.hideWaitPopup();
			}
		}, new DSRequest());
	}
	
	public void sezApertaATuttiClick(Record recordIn){

		// IdSezioneIO = id. sezione del nodo selezionato
		// IdSezionePrecIn = non valorizzata
		// IdSezionePadreIn = id. sezione del nodo padre
		// NomeSezioneIn = nome compilato nella pop-up
		// FlgPubblApertaIn = seziona aperta a tutti
		Record record = new Record();
		record.setAttribute("idSezione", recordIn.getAttributeAsString("idNode"));
		record.setAttribute("idSezionePrec", (String)null);
		record.setAttribute("idSezionePadre", recordIn.getAttributeAsString("parentId"));
		record.setAttribute("nome", recordIn.getAttributeAsString("nome"));
		record.setAttribute("flgSezApertaATutti", recordIn.getAttributeAsBoolean("flgSezApertaATutti") != null && recordIn.getAttributeAsBoolean("flgSezApertaATutti"));

		Layout.showWaitPopup(I18NUtil.getMessages().salvataggioWaitPopup_message());
		
		GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");				
		lDataSource.updateData(record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];
					Layout.addMessage(new MessageBean("Sezione modificata con successo", "", MessageType.INFO));
					if(layout != null) {
						reloadSubTreeFrom(result.getAttributeAsString("idSezionePadre"));
					}
				} 
				Layout.hideWaitPopup();					
			}
		});
	}
	
	public void manageCustomTreeNode(TreeNode node) {
		node.setIcon("titolario/tipo/TITOLARIO.png");
		node.setShowOpenIcon(false);
		node.setShowDropIcon(false);
	}
	
	public void reloadSubTreeFrom(String idNode) {
		if (idNode != null && !"".equals(idNode)) {
			TreeNode node = getTree().findById(idNode);
			if (node != null) {
				try {
					TreeNode[] parents = getTree().getParents(node);
					if (parents != null && parents.length > 0) {
						reloadTreeFrom(idNode);
						return;
					} 
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void reloadTreeFrom(String idNode) {
		selectSingleRecord(0);
		((GWTRestDataSource) instance.getDataSource()).addParam("idRootNode", idNode);
		((GWTRestDataSource) instance.getDataSource()).addParam("finalita", layout.getFinalita());
		((GWTRestDataSource) instance.getDataSource()).addParam("openRootNode", "true");
		idNodeToScroll = idNode;
		invalidateCache();	
	}
	
	public void manageReloadFolder(TreeNode node, boolean forceToOpenNode) {
		((GWTRestDataSource) instance.getDataSource()).addParam("idRootNode", node.getAttributeAsString("idNode"));
		((GWTRestDataSource) instance.getDataSource()).addParam("nroProgrRootNode", node.getAttributeAsString("nroProgr"));
		((GWTRestDataSource) instance.getDataSource()).addParam("openRootNode", forceToOpenNode || getTree().isOpen(node) ? "true" : "");
	}
}