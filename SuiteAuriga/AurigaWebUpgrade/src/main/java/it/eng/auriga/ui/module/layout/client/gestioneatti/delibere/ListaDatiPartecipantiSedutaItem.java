/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridEditorContext;
import com.smartgwt.client.widgets.grid.ListGridEditorCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ListaDatiPartecipantiSedutaItem extends GridItem {
	
	private ListaDatiPartecipantiSedutaItem instance = this;
	
	protected ListGridField idUser;
	protected ListGridField denominazione;
	protected ListGridField incarico;
	protected ListGridField ruolo;
	protected ListGridField delegato;
	protected ListGridField flgPresenza;
	protected ListGridField flgExtra;
	
	private String organoCollegiale;
	private String statoSeduta;
	private String listaCommissioni;
	
	protected ControlListGridField removeButtonField;

	public ListaDatiPartecipantiSedutaItem(String name, String organoCollegiale, String statoSeduta, String listaCommissioni) {
		
		super(name, "lista_dati_partecipanti_seduta");
		
		this.organoCollegiale = organoCollegiale;
		this.statoSeduta = statoSeduta;
		this.listaCommissioni = listaCommissioni;
		
		setGridPkField("idUser");
		setShowPreference(true);
		setShowNewButton(true);
		setShowModifyButton(false);
		setShowDeleteButton(false);
		setCanEdit(!isOdGChiuso());  
		
		idUser = new ListGridField("idUser");
		idUser.setHidden(true);
		idUser.setCanHide(false);
		idUser.setCanSort(true);
		idUser.setCanEdit(false);
		
		denominazione = new ListGridField("denominazione", "Cognome e nome");
		denominazione.setCanSort(true);
		denominazione.setCanEdit(false);
		
		incarico = new ListGridField("incarico", "Incarico");
		incarico.setCanSort(true);
		incarico.setCanEdit(false);
		
		ruolo = new ListGridField("ruolo", "Ruolo in seduta");
		ruolo.setCanSort(true);
		ruolo.setCanEdit(!isOdGChiuso());		
		GWTRestDataSource tipoRuoloDS = new GWTRestDataSource("LoadComboTipoRuoloSedutaDataSource");
		tipoRuoloDS.addParam("tipo_sessione", organoCollegiale);		
		SelectItem tipoRuoloItem = new SelectItem("tipoRuolo");
		tipoRuoloItem.setValueField("key");
		tipoRuoloItem.setDisplayField("value");
		tipoRuoloItem.setOptionDataSource(tipoRuoloDS);
		tipoRuoloItem.setAutoFetchData(true);
		tipoRuoloItem.setAlwaysFetchMissingValues(true);
		tipoRuoloItem.setFetchMissingValues(true);
		tipoRuoloItem.setStartRow(false);
		tipoRuoloItem.setColSpan(1);
		tipoRuoloItem.setWidth(200);	
		tipoRuoloItem.setAllowEmptyValue(true);
		ruolo.setEditorProperties(tipoRuoloItem); 
		
		delegato = new ListGridField("delegato", "Delegato");
		delegato.setCanSort(true);
		delegato.setCanEdit(!isOdGChiuso());			
		SelectItem delegatoItem = new SelectItem();
		delegatoItem.setValueField("key");
		delegatoItem.setDisplayField("value");
		if ("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			GWTRestDataSource delegatoCommissioneDS = new GWTRestDataSource("LoadComboDelegatoSedutaDataSource");
			delegatoCommissioneDS.addParam("tipo_sessione", organoCollegiale);		
			delegatoItem.setOptionDataSource(delegatoCommissioneDS);
		} else if("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			GWTRestDataSource delegatoConferenzaDS = new GWTRestDataSource("LoadComboDelegatoConferenzaSedutaDataSource");
			delegatoConferenzaDS.addParam("tipo_sessione", organoCollegiale);		
			delegatoItem.setOptionDataSource(delegatoConferenzaDS);
		}
		delegatoItem.setAutoFetchData(false);
		delegatoItem.setAlwaysFetchMissingValues(true);
		delegatoItem.setFetchMissingValues(true);
		delegatoItem.setStartRow(false);
		delegatoItem.setColSpan(1);
		delegatoItem.setWidth(200);	
		delegatoItem.setAllowEmptyValue(true);
		delegatoItem.setRedrawOnChange(true);
		delegato.setCellAlign(Alignment.LEFT);
		delegato.setEditorProperties(delegatoItem); 
		delegato.setAttribute("custom", true);
		delegato.setShowHover(true);
		delegato.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDenominazioneDelegato(record);
			}
		});
		delegato.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDenominazioneDelegato(record);
			}
		});
			
		LinkedHashMap<String, String> flgPresenzaValueMap = new LinkedHashMap<String, String>();
		flgPresenzaValueMap.put("1", "presente");
		flgPresenzaValueMap.put("0", "assente");		
		flgPresenza = new ListGridField("flgPresenza", "Presenza");
		flgPresenza.setCanSort(true);
		flgPresenza.setCanEdit(!isOdGChiuso());
		flgPresenza.setValueMap(flgPresenzaValueMap);
		RadioGroupItem radioPresenzaItem = new RadioGroupItem("radioPresenza");
		radioPresenzaItem.setShowTitle(false);
		radioPresenzaItem.setValueMap(flgPresenzaValueMap);
		radioPresenzaItem.setDefaultValue("1");
		radioPresenzaItem.setVertical(true);
		radioPresenzaItem.setWrap(false);		
		flgPresenza.setEditorProperties(radioPresenzaItem);		
		
		flgExtra = new ListGridField("flgExtra", "Extra");
		flgExtra.setType(ListGridFieldType.ICON);
		flgExtra.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgExtra.setAlign(Alignment.CENTER);
		flgExtra.setWrap(false);
		flgExtra.setWidth(30);
		flgExtra.setAttribute("custom", true);
		flgExtra.setShowHover(true);
		flgExtra.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgExtra = (String) record.getAttribute("flgExtra");
				if (flgExtra != null && "1".equalsIgnoreCase(flgExtra)) {
					return buildIconHtml("public.png");
				}
				return null;
			}
		});
		flgExtra.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgExtra = (String) record.getAttribute("flgExtra");
				if (flgExtra != null && "1".equalsIgnoreCase(flgExtra)) {
					return "Extra componenti commissioni convocate";
				}
				return null;
			}
		});
		
		if ("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
			setGridFields(
					idUser,
					denominazione,
					delegato,
					ruolo,
					flgPresenza,
					flgExtra
			);
		} else if ("CONFERENZA".equalsIgnoreCase(organoCollegiale)) {
			setGridFields(
					idUser,
					denominazione,
					delegato,
					ruolo,
					flgPresenza
			);
		} else {
			setGridFields(
					idUser,
					denominazione,
					incarico,
					ruolo,
					flgPresenza
			);
		}
	}
		
	@Override
	public ListGrid buildGrid() {
		
		ListGrid grid = super.buildGrid();

		grid.setCanDragRecordsOut(true);  
		grid.setCanAcceptDroppedRecords(true);  
		grid.setDragDataAction(DragDataAction.MOVE); 
		grid.setCanResizeFields(true);
		grid.setEditorCustomizer(new ListGridEditorCustomizer() {
				
			@Override
			public FormItem getEditor(ListGridEditorContext context) {
				if(context.getEditField().getName().equals("delegato")) {
					String flgExtra = context.getEditedRecord().getAttribute("flgExtra");
					if(flgExtra != null && "1".equalsIgnoreCase(flgExtra)) {
						TextItem lTextItem = new TextItem();
						lTextItem.setCanEdit(false);
						return lTextItem;
					}
				}
				return null;				
			}
		});	
			
		return grid;
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("lista_dati_partecipanti_seduta", fields);
	}
	
	@Override
	protected List<ControlListGridField> getButtonsFields() {
		List<ControlListGridField> buttonsList = new ArrayList<ControlListGridField>();					

		if(removeButtonField == null) {
			removeButtonField = buildRemoveButtonField();
		}
		buttonsList.add(removeButtonField);
		
		return buttonsList;
	}
	
	protected ControlListGridField buildRemoveButtonField() {
		
		ControlListGridField removeButtonField = new ControlListGridField("removeButton");  
		removeButtonField.setAttribute("custom", true);	
		removeButtonField.setShowHover(true);		
		removeButtonField.setCanReorder(false);
		removeButtonField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(record.getAttributeAsString("flgExtra") != null && "1".equals(record.getAttributeAsString("flgExtra"))) {
					return buildImgButtonHtml("buttons/remove.png");
				}
				return null;
			}
		});
		removeButtonField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {	
				if(record.getAttributeAsString("flgExtra") != null && "1".equals(record.getAttributeAsString("flgExtra"))) {
					return "Rimuovi";
				}
				return null;
			}
		});		
		removeButtonField.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				if(event.getRecord().getAttributeAsString("flgExtra") != null && "1".equals(event.getRecord().getAttributeAsString("flgExtra"))) {
					onClickRemoveButton(event.getRecord());
				}
			}
		});	
		removeButtonField.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return true;
			}
		});
		
		return removeButtonField;
	}

	public void onClickRemoveButton(final ListGridRecord record) {
		
		instance.removeData(record);
	}
	
	@Override
	public List<ToolStripButton> buildCustomEditButtons() {
		
		List<ToolStripButton> buttons = new ArrayList<ToolStripButton>();	
		
		ToolStripButton addPartecipanteButton = new ToolStripButton();   
		addPartecipanteButton.setIcon("buttons/new.png");   
		addPartecipanteButton.setIconSize(16);
		addPartecipanteButton.setPrefix("aggiunta_partecipante");
		addPartecipanteButton.setPrompt("Aggiungi partecipante");
		addPartecipanteButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				if("COMMISSIONE".equalsIgnoreCase(organoCollegiale)) {
					SceltaComponentiExtraCommissioniPopup sceltaComponentiExtraCommissioniPopup = new SceltaComponentiExtraCommissioniPopup(getIdSeduta()) {

						@Override
						public void manageOnOkButtonClick(ListGridRecord[] selectedRecords) {
							for(int i = 0; i < selectedRecords.length; i++) {
								ListGridRecord listGridRecord = selectedRecords[i];
								listGridRecord.setAttribute("flgExtra", "1");
								listGridRecord.setAttribute("flgPresenza", "1");
								addData(listGridRecord);
							}
						}					
					};
					sceltaComponentiExtraCommissioniPopup.show();
				} else {
					PartecipanteMultiLookupRubrica lPartecipanteMultiLookupRubrica = new PartecipanteMultiLookupRubrica(null);				
					lPartecipanteMultiLookupRubrica.show();
				}

			}   
		}); 

		if(isCommissione()) {
			buttons.add(addPartecipanteButton);
		}
	
		ToolStripButton refreshButton = new ToolStripButton();   
		refreshButton.setIcon("buttons/refreshList.png");   
		refreshButton.setIconSize(16);
		refreshButton.setPrefix("Ricarica");
		refreshButton.setPrompt("Ricarica");
		refreshButton.addClickHandler(new ClickHandler() {	
			
			@Override
			public void onClick(ClickEvent event) {
				onClickRefreshListButton();
			}   
		});  
		buttons.add(refreshButton);
			
		return buttons;
	}
	
	public void onClickRefreshListButton() {
		
		Record lRecord = new Record();
		lRecord.setAttribute("organoCollegiale", organoCollegiale);
		lRecord.setAttribute("idSeduta", getIdSeduta());
		lRecord.setAttribute("listaCommissioni", listaCommissioni);
		GWTRestService<Record, Record> lGWTRestDataSource = new GWTRestService<Record, Record>("ConvocazioneSedutaDataSource");
		lGWTRestDataSource.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				RecordList recordList = object.getAttributeAsRecordList("listaPresenzeOdg");
				setData(recordList);
			}
		});
	}
	
	private Boolean isOdGChiuso() {
		return statoSeduta != null && "OdG_chiuso".equalsIgnoreCase(statoSeduta);
	}
	
	public class PartecipanteMultiLookupRubrica extends LookupSoggettiPopup {

		private RecordList multiLookupList = new RecordList(); 
	
		public PartecipanteMultiLookupRubrica(Record record) {
			super(record, null, false);			
		}

		@Override
		public String getFinalita() {
			return "SEL_SOGG_EST";
		}
		
		@Override
		public void manageLookupBack(Record record) {
									
		}
		
		@Override
		public void manageMultiLookupBack(Record record) {
			Record recordToInsert = new Record();
			recordToInsert.setAttribute("idRubrica", record.getAttribute("idSoggetto"));
			recordToInsert.setAttribute("idUser", record.getAttribute("idUtente"));
			recordToInsert.setAttribute("denominazione", record.getAttribute("denominazione"));
			multiLookupList.add(recordToInsert);
			instance.addData(recordToInsert);
			
		}
		
		@Override
		public void manageMultiLookupUndo(Record record) {
			String idRubricaToRemove = record.getAttribute("id") != null ? record.getAttribute("id") : "";
			int posToRemove = -1;
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.getLength(); i++) {
					if (idRubricaToRemove.equalsIgnoreCase(multiLookupList.get(i).getAttribute("idRubrica"))) {
						posToRemove = i;
					}
				}
				if (posToRemove > -1) {
					multiLookupList.removeAt(posToRemove);
				}
				posToRemove = -1;
				if (instance.getData() != null) {
					RecordList listaPartecipanti = instance.getData();
					for (int i = 0; i < listaPartecipanti.getLength(); i++) {
						Record partecipante = listaPartecipanti.get(i);
						if (partecipante.getAttribute("idRubrica") != null && idRubricaToRemove.equalsIgnoreCase(partecipante.getAttribute("idRubrica"))) {
							posToRemove = i;
						}
					}					
					if (posToRemove > -1) {
						listaPartecipanti.removeAt(posToRemove);
					}
				}
			}
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("aggiunta_partecipante")) {
					if( (getIdSeduta() != null && !"".equalsIgnoreCase(getIdSeduta())) && isCommissione()) {
						member.show();	
					} else {
						member.hide();						
					}
				}
			}		
			layoutListaSelectItem.show();
			saveLayoutListaButton.show();
			getGrid().setCanReorderRecords(canEdit);
			redrawRecordButtons();
		}
	}	
	
	public String getIdSeduta() {
		return null;
	}
	
	public Boolean isCommissione() {
		return organoCollegiale != null && "COMMISSIONE".equalsIgnoreCase(organoCollegiale);
	}
	
	protected String getDenominazioneDelegato(ListGridRecord record) {
		String ret = "";
		if (record.getAttribute("delegato") != null && !record.getAttribute("delegato").equalsIgnoreCase("")){
			String[] listaValoriString = record.getAttribute("delegato").split("\\|\\*\\|");
			List<String> listaValoriList  = Arrays.asList(listaValoriString);
			String nomeDelegato = null;
			if (listaValoriList.size() > 1){
				nomeDelegato = listaValoriList.get(1).trim();
			} else {
				nomeDelegato = record.getAttribute("decodificaDelegato");
			}
			ret = nomeDelegato;
		}
		return ret;
	}
}