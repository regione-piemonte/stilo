/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
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

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

public abstract class ListaDatiContabiliDinamicaItem extends GridItem {
	
	protected Record attrLista;
	protected RecordList datiDettLista;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
	
	protected int count = 0;

	public ListaDatiContabiliDinamicaItem(Record attrLista, ArrayList<Map> dettAttrLista) {
		
		super(attrLista.getAttribute("nome"), "listaDatiContabili");
		
		this.attrLista = attrLista;
		this.datiDettLista = new RecordList();
		try {
			for (Map dett : dettAttrLista) {
				datiDettLista.add(new Record(dett));
			}
		} catch (Exception e) {
		}
				
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		if (datiDettLista != null && datiDettLista.getLength() > 0) {

			Map<Integer, Record> mappaColonne = new HashMap<Integer, Record>(datiDettLista.getLength());

			for (int i = 0; i < datiDettLista.getLength(); i++) {

				Record dett = datiDettLista.get(i);

				mappaColonne.put(new Integer(dett.getAttribute("numero")), dett);

			}

			List<Integer> colonne = new ArrayList<Integer>(mappaColonne.keySet());
			Collections.sort(colonne);

			List<ListGridField> gridFields = new ArrayList<ListGridField>();

			for (int i = 0; i < colonne.size(); i++) {

				Integer nroColonna = colonne.get(i);

				final Record dett = mappaColonne.get(nroColonna);

				ListGridField field = new ListGridField(dett.getAttribute("nome"), dett.getAttribute("label"));
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
					
				} else if ("CUSTOM".equals(dett.getAttribute("tipo"))) {
					
				}

				if (field != null) {
					gridFields.add(field);
				}
			}

			setGridFields(gridFields.toArray(new ListGridField[gridFields.size()]));

		};	
		
		if (attrLista.getAttribute("obbligatorio") != null && "1".equals(attrLista.getAttribute("obbligatorio"))) {
			setAttribute("obbligatorio", true);			
		}

		// il fatto che sia modificabile o no lo gestisco da NuovaPropostaAtto2Detail
		setAttribute("modificabile", true);
//		if (attrLista.getAttribute("modificabile") != null) {
//			if ("1".equals(attrLista.getAttribute("modificabile"))) {
//				setAttribute("modificabile", true);
//			} else if ("0".equals(attrLista.getAttribute("modificabile"))) {
//				setAttribute("modificabile", false);
//			}
//		}
	}
	
	public Record getAttrLista() {
		return attrLista;
	}
	
	public RecordList getDatiDettLista() {
		return datiDettLista;
	}

	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
//		grid.addSort(new SortSpecifier("fieldName1", SortDirection.ASCENDING));
//		grid.addSort(new SortSpecifier("fieldName2", SortDirection.ASCENDING));
//		grid.addSort(new SortSpecifier("fieldName3", SortDirection.ASCENDING));
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
		ToolStripButton datiStoriciButton = new ToolStripButton();   
		datiStoriciButton.setIcon("pratiche/task/buttons/datiStorici.png");   
		datiStoriciButton.setIconSize(16);
		datiStoriciButton.setPrefix("datiStoriciButton");
		datiStoriciButton.setPrompt("Visualizza dati contabili richiesti");  
		datiStoriciButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				  onClickDatiStoriciButton();   	
			}   
		});  
//		if (isShowDatiStoriciButton()) {
			buttons.add(datiStoriciButton);
//		}				
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
	
	public void setData(RecordList data) {
		super.setData(data);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if(member instanceof ToolStripButton) {
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("datiStoriciButton"))
					{
						if (isShowDatiStoriciButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
					}
				}
			}	
		}
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
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("datiStoriciButton"))
					{
						if (isShowDatiStoriciButton())								
								((ToolStripButton) member).show();
						else
							((ToolStripButton) member).hide();
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
		setGridFields("listaDatiContabili", fields);
	}
	
	@Override
	public void onClickNewButton() {
		grid.deselectAllRecords();
		ListaDatiContabiliDinamicaWindow lListaDatiContabiliDinamicaWindow = new ListaDatiContabiliDinamicaWindow(this, "datiContabiliWindow", null, true) {
			
			@Override
			public void saveData(Record newRecord) {
				newRecord.setAttribute("id", "NEW_" + count++);
				addData(newRecord);				
			}
		};
		lListaDatiContabiliDinamicaWindow.show();
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final ListaDatiContabiliDinamicaWindow lListaDatiContabiliDinamicaWindow = new ListaDatiContabiliDinamicaWindow(this, "datiContabiliWindow", record, false);
		lListaDatiContabiliDinamicaWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();
		final ListaDatiContabiliDinamicaWindow lListaDatiContabiliDinamicaWindow = new ListaDatiContabiliDinamicaWindow(this, "datiContabiliWindow", record, true) {
			
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
		lListaDatiContabiliDinamicaWindow.show();
	}
	
	public void onClickDeleteButton(ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);
	}
	
	public boolean isShowDatiStoriciButton() {
		return false;
	}
	
	public void onClickDatiStoriciButton() {
		
	}
	
	public boolean isShowRefreshListButton() {
		return true;
	}
	
	public void onClickRefreshListButton() {
		
	}
	
	public boolean isShowExportXlsButton() {
		return true;
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
		record.setAttribute("nomeEntita", "listaDatiContabili");
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
			records[i] = rec;
		}
		return records;
	}
	
	public abstract boolean isGrigliaEditabile();
	
}
