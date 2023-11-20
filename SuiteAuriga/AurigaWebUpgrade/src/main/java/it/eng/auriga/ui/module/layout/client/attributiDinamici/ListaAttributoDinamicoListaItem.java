/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;

public abstract class ListaAttributoDinamicoListaItem extends GridItem {

	private AttributiDinamiciDetail detail;
	protected Record attrLista;
	private RecordList datiDettLista;
	private ArrayList<Map> variazioniAttrLista;
	private CallbackGenericFunction callbackGeneric;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected List<Record> listaOrdinamenti;
	
	protected List<Integer> colonne;
	protected Map<Integer, Record> mappaColonne;
	
	
	protected int count = 0;

	private String uriFileImportExcel;
	
	public ListaAttributoDinamicoListaItem(AttributiDinamiciDetail detail, Record attrLista, RecordList datiDettLista) {
		this(detail, attrLista, datiDettLista, null);
	}
	
	public ListaAttributoDinamicoListaItem(Record attrLista, RecordList datiDettLista, CallbackGenericFunction callback) {
		this(null, attrLista, datiDettLista, callback);
	}

	public ListaAttributoDinamicoListaItem(AttributiDinamiciDetail detail, Record attrLista, RecordList datiDettLista, CallbackGenericFunction callback) {
		
		super(attrLista.getAttribute("nome"), "listaAttributoDinamicoLista");
		
		this.setDetail(detail);
		this.attrLista = attrLista;
		this.datiDettLista = datiDettLista;
		this.callbackGeneric = callback;
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		if (datiDettLista != null && datiDettLista.getLength() > 0) {

			mappaColonne = new HashMap<Integer, Record>(datiDettLista.getLength());

			for (int i = 0; i < datiDettLista.getLength(); i++) {

				Record dett = datiDettLista.get(i);

				mappaColonne.put(new Integer(dett.getAttribute("numero")), dett);
			}

			colonne = new ArrayList<Integer>(mappaColonne.keySet());
			
			Collections.sort(colonne);

			List<ListGridField> gridFields = new ArrayList<ListGridField>();
			
			// Aggiungo i field custom
			List<ListGridField> listaListGridFieldsCustom = getListaListGridFieldsCustom();
			if (listaListGridFieldsCustom != null && listaListGridFieldsCustom.size() > 0) {
				for (ListGridField listGridField : listaListGridFieldsCustom) {
					gridFields.add(listGridField);
				}
			}
			
			listaOrdinamenti = new ArrayList<Record>();

			for (int i = 0; i < colonne.size(); i++) {

				Integer nroColonna = colonne.get(i);

				final Record dett = mappaColonne.get(nroColonna);

				ListGridField field = new ListGridField(dett.getAttribute("nome"), dett.getAttribute("label"));
				if (dett.getAttribute("larghezzaListGridField") != null && !"".equalsIgnoreCase(dett.getAttribute("larghezzaListGridField"))) {
					field.setWidth(dett.getAttribute("larghezzaListGridField"));
				}
				
				if (dett.getAttribute("nascondiSoloSuGrid") != null && "1".equalsIgnoreCase(dett.getAttribute("nascondiSoloSuGrid"))) {
					field.setHidden(true);
					field.setCanHide(false);
				}
								
				if ("DATE".equals(dett.getAttribute("tipo"))) {
					field.setType(ListGridFieldType.DATE);
					field.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);					
				} else if ("DATETIME".equals(dett.getAttribute("tipo"))) {
					field.setType(ListGridFieldType.DATE);
					field.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);					
				} else if ("TEXT".equals(dett.getAttribute("tipo"))) {
					
				} else if ("TEXT-AREA".equals(dett.getAttribute("tipo"))) {
					
				} else if ("CHECK".equals(dett.getAttribute("tipo"))) {
					field.setType(ListGridFieldType.BOOLEAN);					
				} else if ("INTEGER".equals(dett.getAttribute("tipo"))) {
					field.setType(ListGridFieldType.INTEGER);					
				} else if ("EURO".equals(dett.getAttribute("tipo"))) {
					field.setType(ListGridFieldType.FLOAT);	
					field.setCellFormatter(new CellFormatter() {
						
						@Override
						public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
							return NumberFormatUtility.getFormattedValue(record.getAttribute(dett.getAttribute("nome")));				
						}
					});
				} else if ("DECIMAL".equals(dett.getAttribute("tipo"))) {
					field.setType(ListGridFieldType.FLOAT);	
					field.setCellFormatter(new CellFormatter() {
						
						@Override
						public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
							return NumberFormatUtility.getFormattedValue(record.getAttribute(dett.getAttribute("nome")));				
						}
					});
				} else if ("COMBO-BOX".equals(dett.getAttribute("tipo"))) {
					
				} else if ("RADIO".equals(dett.getAttribute("tipo"))) {
					
				} else if ("DOCUMENT".equals(dett.getAttribute("tipo"))) {
					
				} else if ("CKEDITOR".equals(dett.getAttribute("tipo"))) {
					
					field.setAttribute("custom", true);
					field.setCellFormatter(new CellFormatter() {
						
						@Override
						public String format(Object value, ListGridRecord record, int rowNum, int colNum) {		
							String result = record.getAttribute(dett.getAttribute("nome"));
							if (result==null) return null;
							String lStringValue = htmlToText(result);
							if (lStringValue.length()>Layout.getGenericConfig().getMaxValueLength()){
								return lStringValue.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
							} else return lStringValue;
						}
					});					
					
					field.setHoverCustomizer(new HoverCustomizer() {
						
						@Override
						public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
							String result = record.getAttribute(dett.getAttribute("nome"));
							if (result==null) return null;
							String lStringValue = htmlToText(result);
							if (lStringValue.length()>Layout.getGenericConfig().getMaxValueLength()){
								return lStringValue.substring(0,Layout.getGenericConfig().getMaxValueLength()) + "...";
							} else return lStringValue;	
						}
					});

				} else if ("DOCUMENTLIST".equals(dett.getAttribute("tipo"))) {
					field.setAttribute("custom", true);
					field.setCellFormatter(new CellFormatter() {
						
						@Override
						public String format(Object value, ListGridRecord record, int rowNum, int colNum) {		
							String result = "";
							Record[] filesRecord = record.getAttributeAsRecordArray(dett.getAttribute("nome"));
							if (filesRecord != null) {
								for (int i = 0; i < filesRecord.length; i++) {
									Record fileRecord = filesRecord[i];
									Record documento = fileRecord.getAttributeAsRecord("documento");
									if (documento != null) {
										result += (("".equalsIgnoreCase(result)) ? "" : "<br>") + documento.getAttribute("nomeFile");
									}
								}
							}
							return result;
						}
					});					
					
					field.setHoverCustomizer(new HoverCustomizer() {
						
						@Override
						public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
							String result = "";
							Record[] filesRecord = record.getAttributeAsRecordArray(dett.getAttribute("nome"));
							if (filesRecord != null) {
								for (int i = 0; i < filesRecord.length; i++) {
									Record fileRecord = filesRecord[i];
									Record documento = fileRecord.getAttributeAsRecord("documento");
									if (documento != null) {
										result += (("".equalsIgnoreCase(result)) ? "" : "\n") + documento.getAttribute("nomeFile");
									}
								}
							}
							return result;		
						}
					});
					
				} else if ("CUSTOM".equals(dett.getAttribute("tipo"))) {
					
				}

				if (field != null) {
					gridFields.add(field);
				}
				
				// Aggiorno l'ordinamento
				// Verifico se la colonna elaborata ha un ordinamento, e mantengo una lista degli ordinamenti ordinata per numero di ordinamento
				if (dett.getAttribute("nrPosizColonnaOrdinamento") != null){
					// Estraggo nunero di ordinamento e direzione
					int numeroColonnaOrdinamento = dett.getAttribute("nrPosizColonnaOrdinamento") != null ?  Integer.valueOf(dett.getAttribute("nrPosizColonnaOrdinamento")) : 0;
					 
					int nrPosizColonnaOrdinamento = 0;
					
					if (listaOrdinamenti!=null && !listaOrdinamenti.isEmpty() && listaOrdinamenti.size() > 0){
						nrPosizColonnaOrdinamento = listaOrdinamenti.get(listaOrdinamenti.size() - 1).getAttribute("nrPosizColonnaOrdinamento") != null ?  Integer.valueOf(listaOrdinamenti.get(listaOrdinamenti.size() - 1).getAttribute("nrPosizColonnaOrdinamento")) : 0;
					}
					
					if (listaOrdinamenti.isEmpty() || (nrPosizColonnaOrdinamento < numeroColonnaOrdinamento ) ) {
						// Non ho ancora nessun ordinamneto o il numero di ordinamento è superiore a quello dell'ultimo elemento in lista
						listaOrdinamenti.add(dett);
					} else {
						// Aggiorno la lista delgi ordinamenti inserendo l'elemento alla giusta posizione
						for (int pos = 0; pos < listaOrdinamenti.size(); pos++) {
							if (listaOrdinamenti.get(pos).getAttributeAsInt("nrPosizColonnaOrdinamento") > numeroColonnaOrdinamento) {
								listaOrdinamenti.add(pos, dett);
								break;
							}
						}
					}
				}
			}

			setGridFields(gridFields.toArray(new ListGridField[gridFields.size()]));

		};	
		
		if (attrLista.getAttribute("obbligatorio") != null && "1".equals(attrLista.getAttribute("obbligatorio"))) {
			setAttribute("obbligatorio", true);			
		}

		// il fatto che sia modificabile o no lo gestisco da NuovaPropostaAtto2CompletaDetail
		setAttribute("modificabile", true);
//		if (attrLista.getAttribute("modificabile") != null) {
//			if ("1".equals(attrLista.getAttribute("modificabile"))) {
//				setAttribute("modificabile", true);
//			} else if ("0".equals(attrLista.getAttribute("modificabile"))) {
//				setAttribute("modificabile", false);
//			}
//		}
	}

	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
		// Inserisco gli orinamenti dalla lista ordinata
		if (!listaOrdinamenti.isEmpty()) {
			for (Record ordinamento : listaOrdinamenti) {
				SortDirection sortDirection = (ordinamento.getAttribute("versoOrdinamento") != null && "desc".equalsIgnoreCase(ordinamento.getAttribute("versoOrdinamento"))) ? SortDirection.DESCENDING : SortDirection.ASCENDING;
				grid.addSort(new SortSpecifier(ordinamento.getAttribute("nome"), sortDirection));
			}
		}
		return grid;		
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		ToolStripButton newButton = new ToolStripButton();   
		newButton.setIcon("buttons/new.png");   
		newButton.setIconSize(16);
		newButton.setPrefix("newButton");
		newButton.setPrompt(I18NUtil.getMessages().newButton_prompt());
		newButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickNewButton();   	
			}   
		});  
		if (isShowNewButton()) {
			buttons.add(newButton);
		}				
		ToolStripButton exportXlsButton = new ToolStripButton();   
		exportXlsButton.setIcon("export/xls.png"); 
		exportXlsButton.setIconSize(16);
		exportXlsButton.setPrefix("exportXlsButton");
		exportXlsButton.setPrompt("Esporta in formato xls");
		exportXlsButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickExportXlsButton();   	
			}   
		});  
		if (isShowExportXlsButton()) {
			buttons.add(exportXlsButton);
		}
		
		return buttons;
	}
	
	@Override
	public List<Canvas> buildCustomEditCanvas() {
		
		List<Canvas> editCanvas = new ArrayList<Canvas>();
		FileUploadItemWithFirmaAndMimeType uploadFileItem = new FileUploadItemWithFirmaAndMimeType(new UploadItemCallBackHandler() {

			@Override
			public void uploadEnd(final String displayFileName, final String uri) {
				grid.deselectAllRecords();
				uriFileImportExcel = uri;
			}

			@Override
			public void manageError(String error) {
				String errorMessage = "Errore nel caricamento del file";
				if (error != null && !"".equals(error))
					errorMessage += ": " + error;
				Layout.addMessage(new MessageBean(errorMessage, "", MessageType.WARNING));
			}
		}, new ManageInfoCallbackHandler() {

			@Override
			public void manageInfo(InfoFileRecord info) {
				if (info == null || info.getMimetype() == null 	|| (!info.getMimetype().equals("application/excel") 
						&& !info.getMimetype().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
					GWTRestDataSource.printMessage(new MessageBean("Il file risulta in un formato non riconosciuto o non ammesso. I formati validi risultano essere xls/xlsx", "", MessageType.ERROR));
				} else {
					onClickImportXlsButton(uriFileImportExcel, info.getMimetype());
				}				
			}
		});
		
		uploadFileItem.setPickerIconSrc("setPickerIconSrc");
		uploadFileItem.setPrompt("Importa dati da Excel");
		
		DynamicForm otherNewButtonsForm = new DynamicForm();
		otherNewButtonsForm.setHeight(1);
		otherNewButtonsForm.setWidth(1);
		otherNewButtonsForm.setOverflow(Overflow.VISIBLE);
		otherNewButtonsForm.setPrefix("otherNewButtons");
		otherNewButtonsForm.setNumCols(20);
		
		List<FormItem> listaOtherNewButtonsFormFields = new ArrayList<FormItem>();
		listaOtherNewButtonsFormFields.add(uploadFileItem);
		otherNewButtonsForm.setFields(listaOtherNewButtonsFormFields.toArray(new FormItem[listaOtherNewButtonsFormFields.size()]));
		editCanvas.add(otherNewButtonsForm);
		return editCanvas;
	}	
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		Boolean modificabile = (getAttributeAsBoolean("modificabile") == null || getAttributeAsBoolean("modificabile")) ? canEdit : false;	
		setEditable(modificabile);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {	
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if(isEditable() && isShowEditButtons()) {
						member.show();	
					} else {
						member.hide();						
					}															
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("exportXlsButton"))
					{
						if (isShowExportXlsButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}
				}
			}	
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(modificabile);
			redrawRecordButtons();
		}
	}	
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					
		if(isShowEditButtons()) {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
			if(isShowModifyButton()) {
				if(modifyButtonField == null) {
					modifyButtonField = buildModifyButtonField();					
				}
				buttonsList.add(modifyButtonField);
			}
			if(isShowDeleteButton()) {
				if(deleteButtonField == null) {
					deleteButtonField = buildDeleteButtonField();					
				}
				buttonsList.add(deleteButtonField);
			}
		} else {
			if(detailButtonField == null) {
				detailButtonField = buildDetailButtonField();					
			}
			buttonsList.add(detailButtonField);
		}
		return buttonsList;
	}
	
	protected ControlListGridField buildDetailButtonField() {
		ControlListGridField detailButtonField = new ControlListGridField("detailButton");  
		detailButtonField.setAttribute("custom", true);	
		detailButtonField.setShowHover(true);		
		detailButtonField.setCanReorder(false);
		detailButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(!isShowEditButtons() || !isEditable() || isAlwaysShowDetailButtom()) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(!isShowEditButtons() || !isEditable() || isAlwaysShowDetailButtom()) {			
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(!isShowEditButtons() || !isEditable() || isAlwaysShowDetailButtom()) {
					event.cancel();
					onClickDetailButton(event.getRecord());		
				}
			}
		});		
		return detailButtonField;
	}
	
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons()) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons()) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(final RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					SC.ask("Sei sicuro di voler eliminare il record ?", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value) {
								onClickDeleteButton(event.getRecord());
							}
						}
					});
				}
			}
		});			 
		return deleteButtonField;
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaAttributoDinamicoLista", fields);
	}
	
	@Override
	public void onClickNewButton() {
		onClickNewButton(null);		
	}
	
	public void onClickNewButton(Record record) {
		grid.deselectAllRecords();
		new ListaAttributoDinamicoListaWindow(this, "attributoListaWindow", true, record, true) {
			
			@Override
			public void saveData(Record newRecord) {
				newRecord.setAttribute("id", "NEW_" + count++);
				addData(newRecord);				
			}
			
			@Override
			public String getSaveButtonLabel() {
				return getListaAttributoDinamicoListaWindowSaveButtonLabel();
			}
			
			@Override
			public String getSaveButtonIcon() {
				return getListaAttributoDinamicoListaWindowSaveButtonIcon();
			}
			
			@Override
			public boolean isListaAttributoDinamicoListaWindowGestioneContenutiTabellaTrasp() {
				return isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp();
			}
			
			
			@Override
			public boolean customValidateWindowTrasp(Record recordValori) {
				return customValidateItemTabellaTrasp(recordValori);
			}
		};
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		new ListaAttributoDinamicoListaWindow(this, "attributoListaWindow", false, record, false) {
			@Override
			public String getSaveButtonLabel() {
				return getListaAttributoDinamicoListaWindowSaveButtonLabel();
			}
			
			@Override
			public String getSaveButtonIcon() {
				return getListaAttributoDinamicoListaWindowSaveButtonIcon();
			}
			
			@Override
			public boolean isListaAttributoDinamicoListaWindowGestioneContenutiTabellaTrasp() {
				return isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp();
			}
		};
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();
		new ListaAttributoDinamicoListaWindow(this, "attributoListaWindow", false, record, true) {
			
			@Override
			public void saveData(final Record updatedRecord) {
				updateData(updatedRecord, record);	
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
		    		
					@Override
					public void execute() {
						grid.selectSingleRecord(updatedRecord);
					}
		    	});		
			}	
			
			@Override
			public String getSaveButtonLabel() {
				return getListaAttributoDinamicoListaWindowSaveButtonLabel();
			}
			
			@Override
			public String getSaveButtonIcon() {
				return getListaAttributoDinamicoListaWindowSaveButtonIcon();
			}
			
			@Override
			public boolean isListaAttributoDinamicoListaWindowGestioneContenutiTabellaTrasp() {
				return isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp();
			}
		};
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	public boolean isShowExportXlsButton() {
		return true;
	}
	
	public boolean isShowImportXlsButton() {
		return false;
	}
	
	public void onClickExportXlsButton() {	
		
		//TODO
		if (getGrid().getDataAsRecordList() != null && getGrid().getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}
		
		if (getGrid().isGrouped()) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "", MessageType.ERROR));
			return;
		} 
		
		Layout.showWaitPopup("Export in corso...");
		
		LinkedHashMap<String, String> mappa = createFieldsMap(true);
		
		// Aggiungo le colonne nascoste sul grid 
		aggiungiColonneNascosteSuGrid(mappa);
		
		String[] fields = new String[mappa.keySet().size()];
		String[] headers = new String[mappa.keySet().size()];
		int n = 0;
		for (String key : mappa.keySet()) {
			fields[n] = key;
			headers[n] = mappa.get(key);
			n++;
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "listaAttributoDinamicoLista");
		record.setAttribute("formatoExport", "xls");
		record.setAttribute("criteria", (String) null);
		record.setAttribute("fields", fields);
		record.setAttribute("headers", headers);
		record.setAttribute("records", extractRecords(fields));
		record.setAttribute("overflow", false);
		
		GWTRestService<Record, Record> lAttributiDinamiciDocDatasource = new GWTRestService<Record, Record>("AttributiDinamiciDocDatasource");
		lAttributiDinamiciDocDatasource.performCustomOperation("export", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {					
					String filename = "Results." + record.getAttribute("formatoExport");
					String url = response.getData()[0].getAttribute("tempFileOut");
					// se l'esportazione ha restituito un uri allora lancio il download del documento generato, altrimenti
					// vuol dire che è abilitato per questo datasource l'esportazione asincrona e quindi la generazione è stata schedulata
					if (url != null) {
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename)
								+ "&url=" + URL.encode(url));
					}
				}
				
				/*
				 * else { Layout.addMessage(new MessageBean("Si è verificato un errore durante l'esportazione della lista", "", MessageType.ERROR)); }
				 */
			}
		}, new DSRequest());
	}	
	
	protected void aggiungiColonneNascosteSuGrid(LinkedHashMap<String, String> mappa){
		
		for (int i = 0; i < colonne.size(); i++) {
			
			Integer nroColonna = colonne.get(i);

			final Record dett = mappaColonne.get(nroColonna);
			
			if (dett.getAttribute("nascondiSoloSuGrid") != null && "1".equalsIgnoreCase(dett.getAttribute("nascondiSoloSuGrid"))) {
				
				if (dett.getAttribute("nome").endsWith("_hidden#") == false) {
				
					ListGridField field = new ListGridField(dett.getAttribute("nome"), dett.getAttribute("label"));
					String fieldName = field.getName();
					String fieldTitle = field.getTitle();

					/* ho messo dopo la modifica dei fieldName che finiscono in XOrd, perchè non voglio che nn siano cambiati */
					if (field.getDisplayField() != null)
						fieldName = field.getDisplayField();

					if (!(field instanceof ControlListGridField) && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
						mappa.put(fieldName, fieldTitle);
					}
				}
			}		
		}
	}
		
	protected LinkedHashMap<String, String> createFieldsMap(Boolean includeXord) {
		LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();

		for (int i = 0; i < getGrid().getFields().length; i++) {

			ListGridField field = getGrid().getFields()[i];
			String fieldName = field.getName();

			if (fieldName.endsWith("XOrd") && includeXord) {

				String fieldTitle = field.getTitle() + " (Ordinamento)";

				if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField)  && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
					mappa.put(fieldName, fieldTitle);
				}
			}

			if (fieldName.endsWith("XOrd")) {
				fieldName = fieldName.substring(0, fieldName.lastIndexOf("XOrd"));
			}
			String fieldTitle = field.getTitle();

			/* ho messo dopo la modifica dei fieldName che finiscono in XOrd, perchè non voglio che nn siano cambiati */
			if (field.getDisplayField() != null)
				fieldName = field.getDisplayField();

			if (!(getGrid().getFieldByName(fieldName) instanceof ControlListGridField) && !"_checkboxField".equals(fieldName) && !"checkboxField".equals(fieldName)) {
				mappa.put(fieldName, fieldTitle);
			}
		}
		return mappa;
	}
	
	protected Record[] extractRecords(String[] fields) {
		Record[] records = new Record[getGrid().getRecords().length];
		for (int i = 0; i < getGrid().getRecords().length; i++) {
			Record rec = new Record();
			for (String fieldName : fields) {
				// Se e' un DOCUMENTLIST
				if (isDOCUMENTLIST(fieldName)){
					Record[] filesRecord = getGrid().getRecords()[i].getAttributeAsRecordArray(fieldName);
					if (filesRecord != null) {
						String result = "";
						for (int j = 0; j < filesRecord.length; j++) {
							Record fileRecord = filesRecord[j];
							Record documento = fileRecord.getAttributeAsRecord("documento");
							if (documento != null) {
								result += (("".equalsIgnoreCase(result)) ? "" : ";") + documento.getAttribute("nomeFile");
							}
						}
						rec.setAttribute(fieldName, (String)result);	
					}
				}
				else{
					rec.setAttribute(fieldName, getGrid().getRecords()[i].getAttribute(fieldName));
				}
			}
			records[i] = rec;
		}
		return records;
	}
	
	public abstract boolean isGrigliaEditabile();

	public Record getAttrLista() {
		return attrLista;
	}
	
	public void setAttrLista(Record attrLista) {
		this.attrLista = attrLista;
	}
	
	public RecordList getDatiDettLista() {
		return datiDettLista;
	}

	public void setDatiDettLista(RecordList datiDettLista) {
		this.datiDettLista = datiDettLista;
	}

	public CallbackGenericFunction getCallbackGeneric() {
		return callbackGeneric;
	}

	public void setCallbackGeneric(CallbackGenericFunction callbackGeneric) {
		this.callbackGeneric = callbackGeneric;
	}

	public AttributiDinamiciDetail getDetail() {
		return detail;
	}

	public void setDetail(AttributiDinamiciDetail detail) {
		this.detail = detail;
	}
	
	@Override
	public Boolean validate() {		
		boolean valid = super.validate();
		showTabErrors(valid);
		return valid;
	}
	
	public Boolean validateSenzaObbligatorieta() {
		//TODO
		return true;
	}

	public void showTabErrors(boolean valid) {
		try {
			TabSet tabSet = getForm() != null ? getForm().getTabSet() : null;
			String tabID = getForm() != null ? getForm().getTabID() : null;
			if (!valid && tabSet != null && tabID != null && !"".equals(tabID)) {
				tabSet.showTabErrors(tabID);
			}
		} catch (Exception e) {
		}
	}

	public void mostraVariazione() {
		grid.setBodyBackgroundColor("#FFFAAF");
		grid.setBorder("2px solid #FF0");				
	}

	public void mostraVariazioniColRighe(ArrayList<Map> variazioniAttrLista) {
		this.variazioniAttrLista = variazioniAttrLista;		
		grid.redraw();
	}
	
	@Override
	public String getGridBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		try {
			if(variazioniAttrLista != null) {
				String colName = grid.getFieldName(colNum);
				Map variazioniRiga = variazioniAttrLista.get(rowNum);
				String flgVariazione = variazioniRiga != null ? (String) variazioniRiga.get(colName) : null;
				if (flgVariazione != null && "1".equals(flgVariazione)) {
					return it.eng.utility.Styles.cellVariazione;				
				}
			}
			return null;
		} catch(Exception e) {
			return null;
		}
	}
	
	public void manageOnChangedRequiredItemInCanvas() {
		
	}
	
	public List<ListGridField> getListaListGridFieldsCustom(){
		return null;
	}
	
	public String getListaAttributoDinamicoListaWindowSaveButtonLabel() {
		return I18NUtil.getMessages().formChooser_ok();
	}
	
	public String getListaAttributoDinamicoListaWindowSaveButtonIcon() {
		return "ok.png";
	}
	
	abstract public void onClickImportXlsButton(String uriFileImportExcel, String mimetype);
	
	public static String htmlToText(String html) {
		String plainText = "";
		plainText = html.replaceAll("\\<[^>]*>","").replaceAll("\n", " ");
        return plainText;
    }

	public boolean isDOCUMENTLIST(String fieldName) {
		boolean out = false;
		for (int i = 0; i < datiDettLista.getLength(); i++) {
			Record dett = datiDettLista.get(i);
			if(dett.getAttribute("nome").equalsIgnoreCase(fieldName)){
				if ("DOCUMENTLIST".equalsIgnoreCase(dett.getAttribute("tipo"))) {
					out = true;
				}
			}
		}
		return out;
	}
	
	public boolean isListaAttributoDinamicoListaItemGestioneContenutiTabellaTrasp() {
		return false;
	}
		
	public boolean customValidateItemTabellaTrasp(Record recordValori) {
		return true;
	}
}