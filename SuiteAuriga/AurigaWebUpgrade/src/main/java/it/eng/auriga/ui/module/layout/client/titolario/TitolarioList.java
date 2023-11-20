/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.organigramma.OrganigrammaLayout;
import it.eng.utility.ui.module.core.shared.bean.TreeNodeBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.GenericCallback;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu;
import it.eng.utility.ui.module.layout.client.common.NavigationContextMenu.NavigationContextMenuFrom;

public class TitolarioList extends CustomList {

	private ListGridField folderUp;
	private ListGridField icona;
	private ListGridField iconaFlgClassificaChiusa;
	private ListGridField idClassificazione;
	private ListGridField tipo;
	private ListGridField descrizione;
	private ListGridField descrizioneEstesa;
	private ListGridField paroleChiave;
	private ListGridField indice;
	private ListGridField indiceXOrd;
	private ListGridField tsValidaDal;
	private ListGridField tsValidaFinoAl;
	private ListGridField flgSelXFinalita;
	private ListGridField idClassificaSup;
	private ListGridField desClassificaSup;
	private ListGridField score;
	private ListGridField periodoConservAnni;
	private ListGridField iconaFlgNumContinua;
	
	public TitolarioList(final String nomeEntita) {

		super(nomeEntita);

		idClassificazione = new ListGridField("idClassificazione");
		idClassificazione.setHidden(true);
		idClassificazione.setCanHide(false);

		folderUp = new ControlListGridField("folderUp");
		folderUp.setAttribute("custom", true);
		folderUp.setShowHover(true);
		folderUp.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idClassificaSup") != null && !"".equals(record.getAttribute("idClassificaSup"))) {
					return buildImgButtonHtml("buttons/folderUp.png");
				}
				return null;
			}
		});
		folderUp.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (record.getAttribute("idClassificaSup") != null && !"".equals(record.getAttribute("idClassificaSup"))) {
					return I18NUtil.getMessages().titolario_list_folderUpButton_prompt() + " " + record.getAttribute("desClassificaSup");
				}
				return null;
			}
		});
		folderUp.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (record.getAttribute("idClassificaSup") != null && !"".equals(record.getAttribute("idClassificaSup"))) {
					if (layout instanceof TitolarioLayout) {
						((TitolarioLayout) layout).esploraFromList((record.getAttribute("idClassificaSup")));
					}
				}
			}
		});

		icona = new ControlListGridField("icona");
		icona.setAttribute("custom", true);
		icona.setShowHover(true);
		icona.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (layout instanceof TitolarioLayout) {
					return buildImgButtonHtml(nomeEntita + "/tipo/" + record.getAttributeAsString("nroLivello") + ".png");
				}
				return buildIconHtml(nomeEntita + "/tipo/" + record.getAttributeAsString("tipo") + ".png");
			}
		});
		icona.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (layout instanceof TitolarioLayout) {
					return I18NUtil.getMessages().titolario_list_iconaFolderButton_prompt();
				}
				return null;
			}
		});
		icona.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				Record record = event.getRecord();
				if (layout instanceof TitolarioLayout) {
					((TitolarioLayout) layout).esploraFromList((record.getAttribute("idClassificazione")));
				}
			}
		});

		iconaFlgClassificaChiusa = new ControlListGridField("iconaFlgClassificaChiusa");
		iconaFlgClassificaChiusa.setAttribute("custom", true);
		iconaFlgClassificaChiusa.setShowHover(true);
		iconaFlgClassificaChiusa.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("flgAttiva")!=null && "0".equals(record.getAttributeAsString("flgAttiva"))) {
					return buildIconHtml(nomeEntita + "/tipo/classificaChiusa.png");
				} 
				return null;
			}
		});
		iconaFlgClassificaChiusa.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("flgAttiva")!=null && "0".equals(record.getAttributeAsString("flgAttiva"))) {
					return "Classifica chiusa";
				}
				return null;
			}
		});


		tipo = new ListGridField("tipo");
		tipo.setHidden(true);
		tipo.setCanHide(false);

		descrizione = new ListGridField("descrizione", I18NUtil.getMessages().titolario_list_descrizioneField_title());

		descrizioneEstesa = new ListGridField("descrizioneEstesa", I18NUtil.getMessages().titolario_list_descrizioneEstesaField_title());

		paroleChiave = new ListGridField("paroleChiave", I18NUtil.getMessages().titolario_list_paroleChiaveField_title());

		indice = new ListGridField("indice", I18NUtil.getMessages().titolario_list_indiceField_title());
		// indice.setType(ListGridFieldType.INTEGER);
		indice.setHidden(true);
		indice.setCanHide(false);

		indiceXOrd = new ListGridField("indiceXOrd", I18NUtil.getMessages().titolario_list_indiceField_title());
		indiceXOrd.setDisplayField("indice");
		indiceXOrd.setSortByDisplayField(false);

		tsValidaDal = new ListGridField("tsValidaDal", I18NUtil.getMessages().titolario_list_tsValidaDalField_title());
		tsValidaDal.setType(ListGridFieldType.DATE);
		tsValidaDal.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsValidaDal.setWrap(false);

		tsValidaFinoAl = new ListGridField("tsValidaFinoAl", I18NUtil.getMessages().titolario_list_tsValidaFinoAlField_title());
		tsValidaFinoAl.setType(ListGridFieldType.DATE);
		tsValidaFinoAl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsValidaFinoAl.setWrap(false);

		idClassificaSup = new ListGridField("idClassificaSup");
		idClassificaSup.setHidden(true);
		idClassificaSup.setCanHide(false);

		desClassificaSup = new ListGridField("desClassificaSup");
		desClassificaSup.setHidden(true);
		desClassificaSup.setCanHide(false);

		flgSelXFinalita = new ListGridField("flgSelXFinalita");
		flgSelXFinalita.setHidden(true);
		flgSelXFinalita.setCanHide(false);

		score = new ListGridField("score", I18NUtil.getMessages().titolario_list_scoreField_title());
		score.setType(ListGridFieldType.INTEGER);
		score.setSortByDisplayField(false);
		score.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				Integer score = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if (score != null) {
					String res = "";
					for (int i = 0; i < score; i++) {
						res += "<img src=\"images/score.png\" size=\"10\"/>";
					}
					return res;
				}
				return null;
			}
		});

		periodoConservAnni = new ListGridField("periodoConservAnni", I18NUtil.getMessages().titolario_list_periodoConservAnni());

		// Icona tipo numerazione (C=continua, R=annuale)
		iconaFlgNumContinua = new ListGridField("iconaFlgNumContinua", I18NUtil.getMessages().titolario_list_flgTipoNumerazione());		
		iconaFlgNumContinua.setType(ListGridFieldType.ICON);
		iconaFlgNumContinua.setWidth(30);
		iconaFlgNumContinua.setIconWidth(16);
		iconaFlgNumContinua.setIconHeight(16);
		iconaFlgNumContinua.setShowHover(true);
		iconaFlgNumContinua.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
	
					// Numerazione continua
					if (record.getAttributeAsBoolean("flgNumContinua")){
						return buildIconHtml("lettere/lettera_C_verde.png");
					}
					// Numerazione annuale
					else{
						return buildIconHtml("lettere/lettera_R_arancione.png");	
					}
			}
		});
		iconaFlgNumContinua.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsBoolean("flgTipoNumerazione")!=null) {
					// Numerazione continua
					if (record.getAttributeAsBoolean("flgNumContinua")){
						return "num. fascicoli continua senza rinnovo annuale";
					}
					// Numerazione annuale
					else{
                    	return "num. fascicoli con rinnovo annuale";	
					}
				}
				return null;
			}
		});
		
		setFields(folderUp, 
				  icona, 
				  iconaFlgClassificaChiusa, 
				  idClassificazione, 
				  tipo, 
				  descrizione, 
				  descrizioneEstesa, 
				  paroleChiave, 
				  indice, 
				  indiceXOrd, 
				  tsValidaDal, 
				  tsValidaFinoAl,
				  idClassificaSup, 
				  desClassificaSup, 
				  flgSelXFinalita, 
				  score, 
				  periodoConservAnni,
				  iconaFlgNumContinua
				  );

		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			setArrowKeyAction("focus");
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Enter") == true) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
						if(isRecordSelezionabileForLookup(record)) {
							manageLookupButtonClick(record);
						}
	                }
	            }
	        });		
		}
	}

	@Override
	protected int getButtonsFieldWidth() {
		return 50;
	}

	@Override
	public void reloadFieldsFromCriteria(AdvancedCriteria criteria) {
		boolean isFulltextSearch = false;
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("searchFulltext".equals(crit.getFieldName())) {
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");
					String parole = (String) value.get("parole");
					if (parole != null && !"".equals(parole)) {
						isFulltextSearch = true;
					}
				}
			}
		}
		if (isFulltextSearch) {
			score.setHidden(false);
		} else {
			score.setHidden(true);
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				try {
					refreshFields();
				} catch (Exception e) {
				}
				markForRedraw();
			}
		});
	}

	@Override
	public void manageContextClick(final Record record) {
		if (record != null) {
			Record lRecord = new Record();
			lRecord.setAttribute("idFolder", record.getAttributeAsString("idClassificazione"));
			lRecord.setAttribute("idNode", ((CustomAdvancedTreeLayout) layout).getNavigator().getCurrentNode().getIdNode());
			((CustomAdvancedTreeLayout) layout).getTree().getDataSource().performCustomOperation("calcolaPercorsoFromList", lRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record data = response.getData()[0];
						RecordList percorso = data.getAttributeAsRecordList("percorso");
						Record currentNode = percorso.get(percorso.getLength() - 1);
						TreeNodeBean node = new TreeNodeBean();
						node.setIdNode(currentNode.getAttributeAsString("idNode"));
						node.setNome(currentNode.getAttributeAsString("nome"));
						node.setParentId(currentNode.getAttributeAsString("parentId"));
						node.setFlgEsplodiNodo(currentNode.getAttributeAsString("flgEsplodiNodo"));
						node.setIdFolder(currentNode.getAttributeAsString("idFolder"));
						node.setIdLibreria(currentNode.getAttributeAsString("idLibreria"));
						node.setAltriAttributi(currentNode.getAttributeAsMap("altriAttributi"));
						final Menu navigationContextMenu = new NavigationContextMenu((CustomAdvancedTreeLayout) layout, node,
								NavigationContextMenuFrom.LIST_RECORD);
						Scheduler.get().scheduleDeferred(new ScheduledCommand() {

							@Override
							public void execute() {
								navigationContextMenu.showContextMenu();
								navigationContextMenu.hide();
								showRowContextMenu(getRecord(getRecordIndex(record)), navigationContextMenu);
							}
						});
					}
				}
			}, new DSRequest());
		}
	}

	public void showRowContextMenu(final ListGridRecord record, final Menu navigationContextMenu) {

		final Menu contextMenu = new Menu();
		final Menu altreOpMenu = createAltreOpMenu(record);
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
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				if (contextMenu.getItems().length > 0) {
					contextMenu.showContextMenu();
				}
			}
		});
	}

	public Menu createAltreOpMenu(final ListGridRecord listRecord) {

		final String idClassificazione = listRecord.getAttribute("idClassificazione");
		final String nroLivello = listRecord.getAttribute("nroLivello");
		final String indice = listRecord.getAttribute("indice");

		Menu altreOpMenu = new Menu();

		MenuItem visualizzaMenuItem = new MenuItem("Visualizza", "buttons/detail.png");
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(listRecord);
			}
		});
		altreOpMenu.addItem(visualizzaMenuItem);

		if (OrganigrammaLayout.isAbilToMod()) {
			MenuItem modificaMenuItem = new MenuItem("Modifica", "buttons/modify.png");
			modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					manageModifyButtonClick(listRecord);
				}
			});
			altreOpMenu.addItem(modificaMenuItem);
		}

		if (((TitolarioLayout) getLayout()).isAbilToInsInLivello(nroLivello != null ? new BigDecimal(nroLivello) : null)) {
			MenuItem nuovaClassificaItem = new MenuItem("Nuova sotto-classifica", "buttons/new.png");
			nuovaClassificaItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

				@Override
				public void onClick(MenuItemClickEvent event) {
					((TitolarioLayout) getLayout()).setIdNodeToOpenByIdFolder(listRecord.getAttribute("idClassificazione"), new GenericCallback() {
						
						@Override
						public void execute() {
							deselectAllRecords();
							Map<String, String> mappa = new HashMap<String, String>();
							mappa.put("nroLivello", nroLivello);
							mappa.put("idPianoClassif", ((TitolarioLayout) getLayout()).getIdPianoClassif());
							mappa.put("idClassificaSup", idClassificazione);
							mappa.put("livelloCorrente", indice);
							getLayout().getDetail().editNewRecord(mappa);
							getLayout().getDetail().getValuesManager().clearErrors(true);
							getLayout().newMode();							
						}
					});
				}
			});
			altreOpMenu.addItem(nuovaClassificaItem);
		}

		altreOpMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));

		return altreOpMenu;
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getLayout().getDetail().getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(final DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record record = response.getData()[0];
					((TitolarioLayout) getLayout()).setIdNodeToOpenByIdFolder(record.getAttribute("idClassificaSup"), new GenericCallback() {
						
						@Override
						public void execute() {
							layout.getDetail().editRecord(record, recordNum);
							layout.getDetail().getValuesManager().clearErrors(true);
							callback.execute(response, null, new DSRequest());
						}
					});
				}
			}
		}, new DSRequest());
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
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);
			ImgButton lookupButton = buildLookupButton(record);

			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);
			recordCanvas.setMembersMargin(7);

			recordCanvas.addMember(detailButton);

			if (layout.isLookup()) {
				if (!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton); // aggiungo il bottone SELEZIONA
			}

			lCanvasReturn = recordCanvas;

		}

		return lCanvasReturn;
	}

	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return record.getAttributeAsBoolean("flgSelXFinalita");
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		return true;
	}

	protected boolean showModifyButtonField() {
		return TitolarioLayout.isAbilToMod();
	}

	protected boolean showDeleteButtonField() {
		return TitolarioLayout.isAbilToDel();
	}
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		SC.ask(I18NUtil.getMessages().deleteButtonAsk_message(), new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				if(value) {							
					removeData(record, new DSCallback() {								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout != null ? layout.getTipoEstremiRecord(record) : ""), "", MessageType.INFO));
								((TitolarioLayout) getLayout()).setIdNodeToOpenByIdFolder(record.getAttribute("idClassificaSup"), new GenericCallback() {
									
									@Override
									public void execute() {
										if(layout != null) layout.hideDetailAfterSave();
									}
								});								
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout != null ? layout.getTipoEstremiRecord(record) : ""), "", MessageType.ERROR));										
							}																																	
						}
					});													
				}
			}
		});  
	}
}