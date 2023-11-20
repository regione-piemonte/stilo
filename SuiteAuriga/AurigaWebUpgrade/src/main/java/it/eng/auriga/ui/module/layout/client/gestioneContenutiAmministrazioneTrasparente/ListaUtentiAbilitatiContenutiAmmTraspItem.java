/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.GroupValueFunction;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.gestioneUtenti.LookupGestioneUtentiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;


public class ListaUtentiAbilitatiContenutiAmmTraspItem extends GridItem {
	
	protected ListaUtentiAbilitatiContenutiAmmTraspItem instance = this;
	
	protected ListGridField flgSelectedField;
	protected ListGridField idOggettoPrivField;
	protected ListGridField tipoOggettoPrivField;
	protected ListGridField codiceOggettoPrivField;
	protected ListGridField denominazioneOggettoPrivField;
	protected ListGridField listaPrivilegiOggettoPrivField;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected GridMultiToolStripButton cambiaPrivilegiMultiButton;
	protected GridMultiToolStripButton cancellaPrivilegiMultiButton;
	protected LinkedHashMap<String, String> listaPrivilegiValueMap = new LinkedHashMap<String, String>();		
	
	protected String idSezione;
	
	public ListaUtentiAbilitatiContenutiAmmTraspItem(String name) {
		
		super(name, "lista_utenti_abilitati_contenuti_amministrazione_trasparente");
		
		setGridPkField("idOggettoPriv");
		
		// Nascoste
		flgSelectedField                   = new ListGridField("flgSelected");                   flgSelectedField.setHidden(true);                    flgSelectedField.setCanHide(false);                    flgSelectedField.setCanSort(true);                    flgSelectedField.setCanEdit(false);				
		idOggettoPrivField                 = new ListGridField("idOggettoPriv");                 idOggettoPrivField.setHidden(true);                  idOggettoPrivField.setCanHide(false);                  idOggettoPrivField.setCanSort(true);                  idOggettoPrivField.setCanEdit(false);				
		tipoOggettoPrivField               = new ListGridField("tipoOggettoPriv");               tipoOggettoPrivField.setHidden(true);                tipoOggettoPrivField.setCanHide(false);                tipoOggettoPrivField.setCanSort(true);                tipoOggettoPrivField.setCanEdit(false);
		codiceOggettoPrivField             = new ListGridField("codiceOggettoPriv");             codiceOggettoPrivField.setHidden(true);              codiceOggettoPrivField.setCanHide(false);              codiceOggettoPrivField.setCanSort(true);              codiceOggettoPrivField.setCanEdit(false);
		
		// Visibili
		denominazioneOggettoPrivField   = new ListGridField("denominazioneOggettoPriv",  "Denominazione");   denominazioneOggettoPrivField.setCanSort(true);    denominazioneOggettoPrivField.setCanEdit(false);		
		listaPrivilegiOggettoPrivField  = new ListGridField("listaPrivilegiOggettoPriv", "Privilegi");       listaPrivilegiOggettoPrivField.setCanSort(true);   listaPrivilegiOggettoPrivField.setCanEdit(isEditable());
		listaPrivilegiOggettoPrivField.setAttribute("custom", true);
		listaPrivilegiOggettoPrivField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaLista(record);
			}
		});
		
		// Opzioni Abilitazioni 
		listaPrivilegiValueMap.put("P",  I18NUtil.getMessages().contenuti_amministrazione_trasparente_privilegi_entita_sezione_P_value());
		listaPrivilegiValueMap.put("G",  I18NUtil.getMessages().contenuti_amministrazione_trasparente_privilegi_entita_sezione_G_value());
				
		GWTRestDataSource listaPrivilegiValueMapDS = getLoadComboPrivilegiDatasource();
		SelectItem privilegiFunzioneItem = new SelectItem("privilegiFunzione");		
		privilegiFunzioneItem.setValueField("key");
		privilegiFunzioneItem.setDisplayField("value");
		privilegiFunzioneItem.setOptionDataSource(listaPrivilegiValueMapDS);
		privilegiFunzioneItem.setAutoFetchData(false);
		privilegiFunzioneItem.setAlwaysFetchMissingValues(true);
		privilegiFunzioneItem.setFetchMissingValues(true);
		privilegiFunzioneItem.setWidth(200);
		privilegiFunzioneItem.setMultiple(true);
		listaPrivilegiOggettoPrivField.setEditorProperties(privilegiFunzioneItem); 
		
		setGridFields(  // Nascoste
				        flgSelectedField,
				        idOggettoPrivField,
				        tipoOggettoPrivField,
				        codiceOggettoPrivField,
				        
				        // Visibili
				        denominazioneOggettoPrivField,
				        listaPrivilegiOggettoPrivField
		             );
	}
	
	@Override
	protected GridMultiToolStripButton[] getGridMultiselectButtons() {
		List<GridMultiToolStripButton> gridMultiselectButtons = new ArrayList<GridMultiToolStripButton>();

		if (cambiaPrivilegiMultiButton == null) {
			cambiaPrivilegiMultiButton = new GridMultiToolStripButton("archivio/archiviaConcludi.png", grid, "Cambia privilegi", false) {
					
					@Override
					public boolean toShow() {
						return true;
					}

					@Override
					public void doSomething() {
						cambiaPrivilegiPopup("cambiaPrivilegiMultiButton");
					}
				};
		}
		gridMultiselectButtons.add(cambiaPrivilegiMultiButton);
				
		if (cancellaPrivilegiMultiButton == null) {
			cancellaPrivilegiMultiButton = new GridMultiToolStripButton("buttons/delete2.png", grid, "Cancella privilegi", false) {
					
					@Override
					public boolean toShow() {
						return true;
					}

					@Override
					public void doSomething() {
						cancellaPrivilegiMulti();
					}
				};
		}
		gridMultiselectButtons.add(cancellaPrivilegiMultiButton);

		
		return gridMultiselectButtons.toArray(new GridMultiToolStripButton[gridMultiselectButtons.size()]);
	}
		
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditEvent(ListGridEditEvent.CLICK);
		return grid;
	}

	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();
		
		ToolStripButton addButton = new ToolStripButton();   
		addButton.setIcon("buttons/add.png");   
		addButton.setIconSize(16);
		addButton.setPrefix("Aggiungi utente");
		addButton.setPrompt("Aggiungi utente");
		addButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickAddButton();    
			}   
		});  
		buttons.add(addButton);

		return buttons;
	}
	
	public void onClickAddButton() {
		
		Record record = new Record();
		
		// IdSezioneIn l'ID del nodo selezionato
		record.setAttribute("idSezione", getIdSezione());

		// TipoEntitaIn -- (valori UO/UT) Se UO indica uffici, UT utenti
		record.setAttribute("tipoEntita", "UO");
		
		// leggo le UO abilitate e i privilegi
		GWTRestDataSource lDataSource = new GWTRestDataSource("ContenutiAmmTraspTreeDatasource");				
		lDataSource.performCustomOperation("leggiEntitaAbilitate", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {							
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record result = response.getData()[0];	
					Record filterValues = new Record();
					filterValues.setAttribute("listaPrivilegiUOAbilitate", result.getAttributeAsRecordList("listaPrivilegiEntitaAbilitate"));
					LookupGestioneUtenti lookupGestioneUtenti = new LookupGestioneUtenti(filterValues);
					lookupGestioneUtenti.show();
				} 
				Layout.hideWaitPopup();
			}
		}, new DSRequest());
	}
	
	public class LookupGestioneUtenti extends LookupGestioneUtentiPopup {

		public LookupGestioneUtenti(Record filterValues) {
			super(filterValues);
		}

		@Override
		public void manageLookupBack(Record record) {
			setValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {
			setValuesFromRecord(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
		}		
		
		
		@Override
		public void afterManageOnCloseClick(boolean flg) {
			if (flg)
				cambiaPrivilegiPopup("afterManageOnCloseClick");
		}
		
		@Override
		protected void onDestroy() {
			super.onDestroy();
			cambiaPrivilegiPopup("afterManageOnCloseClick");
		}
	}
	
	public void cambiaPrivilegiPopup(final String callFrom){
		
	    GWTRestDataSource listaPrivilegiValueMapDS = getLoadComboPrivilegiDatasource(); 
	
	    // Apro la popup per selezionare i privilegi
		new CambiaPrivilegiPopup("Cambia privilegi", listaPrivilegiValueMapDS) {
			
			@Override
			public void onClickOkButton(Record object, final DSCallback callback) {
				String listaPrivilegi = object.getAttribute("privilegiFunzione");	
				// Se provengo dalla lista con gli oggetti da importare 
				if (callFrom.equalsIgnoreCase("afterManageOnCloseClick")){
					// Aggiorno i privilegi su tutti i nuovi oggetti importati
					for (ListGridRecord record : grid.getRecords()) {
						String flgSelected = record.getAttribute("flgSelected");
						// Se e' un oggetto nuovo importato
						if (flgSelected !=null && flgSelected.equalsIgnoreCase("1")){
							if(listaPrivilegi != null && !"".equals(listaPrivilegi)) {
								record.setAttribute("listaPrivilegiOggettoPriv", ((String) listaPrivilegi).split(","));			
							} else {
								record.setAttribute("listaPrivilegiOggettoPriv",  new String[0]);	
							}	
							// lo deseleziono
							record.setAttribute("flgSelected",(String)null);	
						}						
						grid.refreshRow(grid.getRecordIndex(record));
					}
					grid.deselectAllRecords();
				}
				
				// Se provengo dal click del bottone "cambiaPrivilegiMultiButton"
				if (callFrom.equalsIgnoreCase("cambiaPrivilegiMultiButton")){
					// Aggiorno i privilegi su tutti i record selezionati
					for (int i = 0; i < grid.getSelectedRecords().length; i++) {
						Record recordSelected = new Record();
						recordSelected = grid.getSelectedRecords()[i];
						if(listaPrivilegi != null && !"".equals(listaPrivilegi)) {
							recordSelected.setAttribute("listaPrivilegiOggettoPriv", ((String) listaPrivilegi).split(","));			
						} else {
							recordSelected.setAttribute("listaPrivilegiOggettoPriv",  new String[0]);	
						}
						grid.refreshRow(grid.getRecordIndex(recordSelected));
					}
					grid.deselectAllRecords();
				}					
				this.markForDestroy();
			}
		};
	}

	public void setValuesFromRecord(Record record) {
		
		// Prendo i dati restituiti dalla lookup
		Record recordToInsert = new Record();
		recordToInsert.setAttribute("flgSelected",                  "1");
		recordToInsert.setAttribute("tipoOggettoPriv",               getTipoOggettoPriv());
		recordToInsert.setAttribute("idOggettoPriv",                 record.getAttribute("idUser"));				
		recordToInsert.setAttribute("codiceOggettoPriv",             record.getAttribute("username"));		
		recordToInsert.setAttribute("denominazioneOggettoPriv",      record.getAttribute("cognomeNome"));
		
		// Aggiungo i dati a quelli gia' presenti
		instance.addData(recordToInsert);						
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
				if(!isShowEditButtons() || !isEditable()) {
					return buildImgButtonHtml("buttons/detail.png");
				}
				return null;
			}
		});
		detailButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(!isShowEditButtons() || !isEditable()) {			
					return I18NUtil.getMessages().detailButton_prompt();
				}
				return null;
			}
		});		
		detailButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(!isShowEditButtons() || !isEditable()) {
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
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons()) {
					event.cancel();
					onClickDeleteButton(event.getRecord());
				}
			}
		});			 
		return deleteButtonField;
	}
	
	public void onClickDetailButton(final ListGridRecord record) {		
	}
	
	public void onClickModifyButton(final ListGridRecord record) {		
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		if("listaPrivilegiOggettoPriv".equalsIgnoreCase(field.getName())) {
			field.setCanEdit(isEditable());
		} else {
			field.setCanEdit(false);
		}
	}	
	
	@Override
	public void setGridFields(ListGridField... fields) {
		setGridFields(getNomeLista(), fields);
	}
	
	protected String getTipoOggettoPriv(){
		return "UT";
	}
	
	protected static GWTRestDataSource getLoadComboPrivilegiDatasource() {
		GWTRestDataSource loadComboPrivilegiDS = new GWTRestDataSource("LoadComboPrivilegiEntitaSezioneDataSource");
		return loadComboPrivilegiDS;
	}
	
	protected String trasformaLista(ListGridRecord record) {
		String ret = "";
		if (record.getAttribute("listaPrivilegiOggettoPriv") != null && !record.getAttribute("listaPrivilegiOggettoPriv").equalsIgnoreCase("")){
			String[] listaValoriString = record.getAttribute("listaPrivilegiOggettoPriv").split(",");
			List<String> listaValoriList  = Arrays.asList(listaValoriString);
			StringBuffer lStringBuffer = new StringBuffer();
			for (int i = 0; i < listaValoriList.size(); i++) {
				String codice = listaValoriList.get(i).trim();
				String desc = (String) listaPrivilegiValueMap.get(codice);			     
				if (i > 0)
					lStringBuffer.append(", ");
				
				lStringBuffer.append(desc);				
			}
			ret = lStringBuffer.toString();
		}
		return ret;
	}
	
	protected void cancellaPrivilegiMulti() {
		
		SC.ask("Vuoi cancellare gli utenti selezionati ? ", new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {				
				if(value) {
					RecordList listaSelectedRecords = new RecordList();
					for (int i = 0; i < grid.getSelectedRecords().length; i++) {
						listaSelectedRecords.add(grid.getSelectedRecords()[i]);
					}					
					// Cancello tutti i record selezionati
					for (int i = 0; i < listaSelectedRecords.getLength(); i++) {
						Record recordSelected = listaSelectedRecords.get(i);
						removeData(recordSelected);
					}
					grid.deselectAllRecords();												
				}
			}
		}); 
	}
	
	public String getIdSezione() {
		return idSezione;
	}

	public void setIdSezione(String idSezione) {
		this.idSezione = idSezione;
	}
}