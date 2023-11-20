/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;

public abstract class ListaDatiContabiliATERSIRItem extends GridItem  {

	protected ListGridField denominazioneBeneficiarioField;
	protected ListGridField cfPIvaBeneficiarioField;
	protected ListGridField importoField;
	protected ListGridField codiceCapitoloField;
	protected ListGridField descrizioneCapitoloField;
	protected ListGridField nrImpegnoField;
	protected ListGridField dataImpegnoField;
	protected ListGridField annoCompetenzaField;
	protected ListGridField codiceCIGField;
	protected ListGridField codiceCUPField;
	
	// hidden 
	protected ListGridField id;	
	protected ListGridField descrizioneImpegnoField;
	protected ListGridField codiceMissioneField;
	protected ListGridField descrizioneMissioneField;
	protected ListGridField codiceProgrammaField;
	protected ListGridField descrizioneProgrammaField;
	protected ListGridField codiceTitoloField;
	protected ListGridField descrizioneTitoloField;
	protected ListGridField codiceMacroAggregatoField;
	protected ListGridField descrizioneMacroAggregatoField;
	protected ListGridField annoRegistrazioneField;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
		
	protected int count = 0;
	
	public ListaDatiContabiliATERSIRItem(String name) {
		
		super(name, "ListaDatiContabiliATERSIR");
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		// Beneficiario (Denominazione)
		denominazioneBeneficiarioField = new ListGridField("denominazioneBeneficiario", "Beneficiario");
		
		// Beneficiario (C.F/P.IVA)
		cfPIvaBeneficiarioField = new ListGridField("cfPIvaBeneficiario", "C.F/P.IVA");
				
		// Importo (€)
		importoField = new ListGridField("importo", "Importo (&euro;)");
		importoField.setType(ListGridFieldType.FLOAT);	
		importoField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});
		
        // Codice capitolo
		codiceCapitoloField = new ListGridField("codiceCapitolo", "Capitolo");
		codiceCapitoloField.setAttribute("custom", true);
		codiceCapitoloField.setCellAlign(Alignment.RIGHT);
		
		// Descrizione capitolo
		descrizioneCapitoloField = new ListGridField("descrizioneCapitolo", "Descrizione Capitolo");
		
		// N° Impegno
		nrImpegnoField = new ListGridField("nrImpegno", "N° Impegno");
		nrImpegnoField.setAttribute("custom", true);
		nrImpegnoField.setCellAlign(Alignment.RIGHT);

		
		// Data impegno
		dataImpegnoField = new ListGridField("dataImpegno", "Data impegno");
		dataImpegnoField.setType(ListGridFieldType.DATE);
		dataImpegnoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
				
		// Anno competenza
		annoCompetenzaField = new ListGridField("annoCompetenza", "Anno competenza");
		annoCompetenzaField.setType(ListGridFieldType.INTEGER);		
		
		// Codice CIG
		codiceCIGField = new ListGridField("codiceCIG", "CIG");
		codiceCIGField.setAttribute("custom", true);
		codiceCIGField.setCellAlign(Alignment.RIGHT);

		// Codice CUP
		codiceCUPField = new ListGridField("codiceCUP", "CUP");
		codiceCUPField.setAttribute("custom", true);
		codiceCUPField.setCellAlign(Alignment.RIGHT);

		// hidden
		id                              = new ListGridField("id"); id.setHidden(true); id.setCanHide(false);
		descrizioneImpegnoField         = new ListGridField("descrizioneImpegno"); descrizioneImpegnoField.setHidden(true); descrizioneImpegnoField.setCanHide(false);
		codiceMissioneField             = new ListGridField("codiceMissione"); codiceMissioneField.setHidden(true); codiceMissioneField.setCanHide(false);
		descrizioneMissioneField        = new ListGridField("descrizioneMissione"); descrizioneMissioneField.setHidden(true); descrizioneMissioneField.setCanHide(false);
		codiceProgrammaField            = new ListGridField("codiceProgramma"); codiceProgrammaField.setHidden(true); codiceProgrammaField.setCanHide(false);
		descrizioneProgrammaField       = new ListGridField("descrizioneProgramma"); descrizioneProgrammaField.setHidden(true); descrizioneProgrammaField.setCanHide(false);
		codiceTitoloField               = new ListGridField("codiceTitolo"); codiceTitoloField.setHidden(true); codiceTitoloField.setCanHide(false);
		descrizioneTitoloField          = new ListGridField("descrizioneTitolo"); descrizioneTitoloField.setHidden(true); descrizioneTitoloField.setCanHide(false);
		codiceMacroAggregatoField       = new ListGridField("codiceMacroAggregato"); codiceMacroAggregatoField.setHidden(true); codiceMacroAggregatoField.setCanHide(false);
		descrizioneMacroAggregatoField  = new ListGridField("descrizioneMacroAggregato"); descrizioneMacroAggregatoField.setHidden(true); descrizioneMacroAggregatoField.setCanHide(false);
		annoRegistrazioneField          = new ListGridField("annoRegistrazione"); annoRegistrazioneField.setHidden(true); annoRegistrazioneField.setCanHide(false);
		
		setGridFields(denominazioneBeneficiarioField,
					  cfPIvaBeneficiarioField,
					  importoField,
					  codiceCapitoloField,
					  descrizioneCapitoloField,
					  nrImpegnoField,
					  dataImpegnoField,
					  annoCompetenzaField,
					  codiceCIGField,
					  codiceCUPField,
					  // hidden
					  id,
					  descrizioneImpegnoField,
					  codiceMissioneField,
					  descrizioneMissioneField,
					  codiceProgrammaField,
					  descrizioneProgrammaField,
					  codiceTitoloField,
					  descrizioneTitoloField,
					  codiceMacroAggregatoField,
					  descrizioneMacroAggregatoField,
					  annoRegistrazioneField
					);	
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
		grid.addSort(new SortSpecifier("annoCompetenza", SortDirection.ASCENDING));
		grid.addSort(new SortSpecifier("codiceCapitolo", SortDirection.ASCENDING));
		return grid;		
	}
	
	@Override
	protected void updateGridItemValue() {
		super.updateGridItemValue();
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
		ToolStripButton refreshListButton = new ToolStripButton();   
		refreshListButton.setIcon("buttons/refreshList.png");   
		refreshListButton.setIconSize(16);
		refreshListButton.setPrefix("refreshListButton");
		refreshListButton.setPrompt(I18NUtil.getMessages().refreshListButton_prompt());
		refreshListButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickRefreshListButton();   	
			}   
		});  
		if (isShowRefreshListButton()) {
			buttons.add(refreshListButton);
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
	
	public void onClickExportXlsButton() {		

		if (getGrid().getDataAsRecordList() != null && getGrid().getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}
		
		if (getGrid().isGrouped()) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "", MessageType.ERROR));
			return;
		} 
		
		LinkedHashMap<String, String> mappa = createFieldsMap(true);
		String[] fields = new String[mappa.keySet().size()];
		String[] headers = new String[mappa.keySet().size()];
		int n = 0;
		for (String key : mappa.keySet()) {
			fields[n] = key;
			headers[n] = mappa.get(key);
			n++;
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "listaDatiContabiliATERSIR");
		record.setAttribute("formatoExport", "xls");
		record.setAttribute("criteria", (String) null);
		record.setAttribute("fields", fields);
		record.setAttribute("headers", headers);
		record.setAttribute("records", extractRecords(fields));
		record.setAttribute("overflow", false);
		
		final GWTRestDataSource lNuovaPropostaAtto2DataSource = new GWTRestDataSource(AurigaLayout.isAttivaNuovaPropostaAtto2Completa() ? "NuovaPropostaAtto2CompletaDataSource" : "NuovaPropostaAtto2DataSource");
		lNuovaPropostaAtto2DataSource.performCustomOperation("export", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
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
				rec.setAttribute(fieldName, getGrid().getRecords()[i].getAttribute(fieldName));
			}
			// Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
			records[i] = rec;
		}
		return records;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if(isEditable() && isShowEditButtons()) {
						member.show();	
					} else {
						member.hide();						
					}
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("refreshListButton"))
					{
						if (isShowRefreshListButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
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
			getGrid().setCanReorderRecords(canEdit);
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
	
	@Override
	protected void setCanEditForEachGridField(ListGridField field) {
		field.setCanEdit(false);
	}
	
	@Override
	public void setGridFields(ListGridField... fields) {		
		setGridFields("listaDatiContabiliATERSIR", fields);
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final DatiContabiliATERSIRWindow lDatiContabiliATERSIRWindow = new DatiContabiliATERSIRWindow(this, "datiContabiliATERSIRWindow", record, false);
		lDatiContabiliATERSIRWindow.show();
	}
	
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();		
		final DatiContabiliATERSIRWindow lDatiContabiliATERSIRWindow = new DatiContabiliATERSIRWindow(ListaDatiContabiliATERSIRItem.this, "datiContabiliATERSIRWindow", record, true) {
					
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
		};
		lDatiContabiliATERSIRWindow.show();
	}
	
	@Override
	public void onClickNewButton() {
		grid.deselectAllRecords();		
		DatiContabiliATERSIRWindow lDatiContabiliATERSIRWindow = new DatiContabiliATERSIRWindow(ListaDatiContabiliATERSIRItem.this, "datiContabiliAVBWindow", null, true) {
					
					@Override
					public void saveData(Record newRecord) {
						// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB						
						newRecord.setAttribute("id", "NEW_" + count++);
						addData(newRecord);				
					}
				};
				lDatiContabiliATERSIRWindow.show();
	}
	
	@Override
	public void setData(RecordList data) {
		super.setData(data);
	}

	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	public boolean isShowExportXlsButton() {
		return true;
	}
	
	public void onClickRefreshListButton() {
		
	}

	public boolean isShowRefreshListButton() {
		return true;
	}
	
	public String[] getCIGValueMap() {
		return null;
	}
	
	public RecordList getCIGCUPRecordList() {
		return null;
	}
	
	public abstract boolean isGrigliaEditabile();	
}