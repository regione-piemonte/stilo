/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;

public abstract class ListaProponentiItem extends GridItem {
	
	protected ListGridField id;	
	protected ListGridField mostraErrori;
	
	private ListGridField idUo;
	private ListGridField codRapido;
	private ListGridField descrizione;
	private ListGridField flgUfficioGare;
	private ListGridField ufficioProponente;
	private ListGridField organigramma;
	private ListGridField organigrammaFromLoadDett;
	private ListGridField listaRdP;
//	private ListGridField idScrivaniaRdP;
//	private ListGridField idScrivaniaRdPFromLoadDett;
//	private ListGridField codUoScrivaniaRdP;
//	private ListGridField desScrivaniaRdP;
	private ListGridField listaDirigenti;
//	private ListGridField idScrivaniaDirigente;
//	private ListGridField idScrivaniaDirigenteFromLoadDett;
//	private ListGridField codUoScrivaniaDirigente;
//	private ListGridField desScrivaniaDirigente;
	private ListGridField listaDirettori;
//	private ListGridField idScrivaniaDirettore;	
//	private ListGridField idScrivaniaDirettoreFromLoadDett;
//	private ListGridField codUoScrivaniaDirettore;
//	private ListGridField desScrivaniaDirettore;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
		
	protected HashMap<String, HashSet<String>> mappaErrori = new HashMap<String, HashSet<String>>();
	
	protected int count = 0;
		
	public ListaProponentiItem(String name) {
		
		super(name, "listaProponenti");
		
		setGridPkField("id");
		setShowPreference(true);
		setShowEditButtons(isGrigliaEditabile());
		setShowNewButton(true);
		setShowModifyButton(true);
		setShowDeleteButton(true);
				
		id = new ListGridField("id");
		id.setHidden(true);
		id.setCanHide(false);	
		
		mostraErrori = new ListGridField("mostraErrori");
		mostraErrori.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		mostraErrori.setCanHide(false);
		mostraErrori.setCanDragResize(false);				
		mostraErrori.setWidth(25);		
		mostraErrori.setAttribute("custom", true);	
		mostraErrori.setAlign(Alignment.CENTER);
		mostraErrori.setShowHover(true);		
		mostraErrori.setCanReorder(false);
		mostraErrori.setCellFormatter(new CellFormatter() {	
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {						
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(record.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {
					return buildImgButtonHtml("exclamation.png");
				}
				return null;
			}
		});
		mostraErrori.setHoverCustomizer(new HoverCustomizer() {			
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(record.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {
					return "Mostra errori";
				}
				return null;				
			}
		});		
		mostraErrori.addRecordClickHandler(new RecordClickHandler() {	
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();				
				final ListGridRecord listRecord = event.getRecord();
				HashSet<String> listaErrori = mappaErrori != null ? mappaErrori.get(listRecord.getAttribute("id")) : null;
				if(listaErrori != null && listaErrori.size() > 0) {					
					StringBuffer buffer = new StringBuffer();
					buffer.append("<ul>");
					for(String errore : listaErrori) {
						buffer.append("<li>" + errore + "</li>");			
					}
					buffer.append("</ul>");			
					SC.warn(buffer.toString());
				}																							
			}
		});			
		
		idUo = new ListGridField("idUo"); idUo.setHidden(true); idUo.setCanHide(false);
		codRapido = new ListGridField("codRapido"); codRapido.setHidden(true); codRapido.setCanHide(false);
		flgUfficioGare = new ListGridField("flgUfficioGare"); flgUfficioGare.setHidden(true); flgUfficioGare.setCanHide(false);
		ufficioProponente = new ListGridField("ufficioProponente"); ufficioProponente.setHidden(true); ufficioProponente.setCanHide(false);
		organigramma = new ListGridField("organigramma"); organigramma.setHidden(true); organigramma.setCanHide(false);
		organigrammaFromLoadDett = new ListGridField("organigrammaFromLoadDett"); organigrammaFromLoadDett.setHidden(true); organigrammaFromLoadDett.setCanHide(false);
		
		descrizione = new ListGridField("descrizione", getTitleIdUo());
		descrizione.setAttribute("custom", true);
		descrizione.setCellAlign(Alignment.LEFT);	
		descrizione.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return getDescrizioneWithCodice(record, "codRapido", "descrizione");				
			}
		});		
		descrizione.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showIdUo();
			}
		});	
		
		
		listaRdP = new ListGridField("listaRdP", getTitleIdScrivaniaRdP());
		listaRdP.setAttribute("custom", true);
		listaRdP.setCellAlign(Alignment.LEFT);	
		listaRdP.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaListaDescrWithCodice(record, "listaRdP");
			}
		});	
		listaRdP.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showIdScrivaniaRdP();
			}
		});	

//		idScrivaniaRdP = new ListGridField("idScrivaniaRdP"); idScrivaniaRdP.setHidden(true); idScrivaniaRdP.setCanHide(false);
//		idScrivaniaRdPFromLoadDett = new ListGridField("idScrivaniaRdPFromLoadDett"); idScrivaniaRdPFromLoadDett.setHidden(true); idScrivaniaRdPFromLoadDett.setCanHide(false);
//		codUoScrivaniaRdP = new ListGridField("codUoScrivaniaRdP"); codUoScrivaniaRdP.setHidden(true); codUoScrivaniaRdP.setCanHide(false);
//		
//		desScrivaniaRdP = new ListGridField("desScrivaniaRdP", getTitleIdScrivaniaRdP());
//		desScrivaniaRdP.setAttribute("custom", true);
//		desScrivaniaRdP.setCellAlign(Alignment.LEFT);	
//		desScrivaniaRdP.setCellFormatter(new CellFormatter() {
//			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				return getDescrizioneWithCodice(record, "codUoScrivaniaRdP", "desScrivaniaRdP");				
//			}
//		});	
//		desScrivaniaRdP.setShowIfCondition(new ListGridFieldIfFunction() {
//			
//			@Override
//			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
//				return showIdScrivaniaRdP();
//			}
//		});	

		listaDirigenti = new ListGridField("listaDirigenti", getTitleIdScrivaniaDirigente());
		listaDirigenti.setAttribute("custom", true);
		listaDirigenti.setCellAlign(Alignment.LEFT);	
		listaDirigenti.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaListaDescrWithCodice(record, "listaDirigenti");			
			}
		});	
		listaDirigenti.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showIdScrivaniaDirigente();
			}
		});	
		
//		idScrivaniaDirigente = new ListGridField("idScrivaniaDirigente"); idScrivaniaDirigente.setHidden(true); idScrivaniaDirigente.setCanHide(false);
//		idScrivaniaDirigenteFromLoadDett = new ListGridField("idScrivaniaDirigenteFromLoadDett"); idScrivaniaDirigenteFromLoadDett.setHidden(true); idScrivaniaDirigenteFromLoadDett.setCanHide(false);
//		codUoScrivaniaDirigente = new ListGridField("codUoScrivaniaDirigente"); codUoScrivaniaDirigente.setHidden(true); codUoScrivaniaDirigente.setCanHide(false);
//		
//		desScrivaniaDirigente = new ListGridField("desScrivaniaDirigente", getTitleIdScrivaniaDirigente());
//		desScrivaniaDirigente.setAttribute("custom", true);
//		desScrivaniaDirigente.setCellAlign(Alignment.LEFT);	
//		desScrivaniaDirigente.setCellFormatter(new CellFormatter() {
//			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				return getDescrizioneWithCodice(record, "codUoScrivaniaDirigente", "desScrivaniaDirigente");				
//			}
//		});	
//		desScrivaniaDirigente.setShowIfCondition(new ListGridFieldIfFunction() {
//			
//			@Override
//			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
//				return showIdScrivaniaDirigente();
//			}
//		});	
		
		listaDirettori = new ListGridField("listaDirettori", getTitleIdScrivaniaDirettore());
		listaDirettori.setAttribute("custom", true);
		listaDirettori.setCellAlign(Alignment.LEFT);	
		listaDirettori.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return trasformaListaDescrWithCodice(record, "listaDirettori");			
			}
		});	
		listaDirettori.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showIdScrivaniaDirettore();
			}
		});	

//		idScrivaniaDirettore = new ListGridField("idScrivaniaDirettore"); idScrivaniaDirettore.setHidden(true); idScrivaniaDirettore.setCanHide(false);
//		idScrivaniaDirettoreFromLoadDett = new ListGridField("idScrivaniaDirettoreFromLoadDett"); idScrivaniaDirettoreFromLoadDett.setHidden(true); idScrivaniaDirettoreFromLoadDett.setCanHide(false);
//		codUoScrivaniaDirettore = new ListGridField("codUoScrivaniaDirettore"); codUoScrivaniaDirettore.setHidden(true); codUoScrivaniaDirettore.setCanHide(false);
//		
//		desScrivaniaDirettore = new ListGridField("desScrivaniaDirettore", getTitleIdScrivaniaDirettore());
//		desScrivaniaDirettore.setAttribute("custom", true);
//		desScrivaniaDirettore.setCellAlign(Alignment.LEFT);	
//		desScrivaniaDirettore.setCellFormatter(new CellFormatter() {
//			
//			@Override
//			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
//				return getDescrizioneWithCodice(record, "codUoScrivaniaDirettore", "desScrivaniaDirettore");				
//			}
//		});	
//		desScrivaniaDirettore.setShowIfCondition(new ListGridFieldIfFunction() {
//			
//			@Override
//			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
//				return showIdScrivaniaDirettore();
//			}
//		});		
		
		setGridFields(
			id,
			mostraErrori,
			idUo,
			codRapido,
			descrizione,
			flgUfficioGare,
			ufficioProponente,
			organigramma,
			organigrammaFromLoadDett,
			listaRdP,
//			idScrivaniaRdP,
//			idScrivaniaRdPFromLoadDett,
//			codUoScrivaniaRdP,
//			desScrivaniaRdP,
			listaDirigenti,
//			idScrivaniaDirigente,
//			idScrivaniaDirigenteFromLoadDett,
//			codUoScrivaniaDirigente,
//			desScrivaniaDirigente,
			listaDirettori
//			idScrivaniaDirettore,	
//			idScrivaniaDirettoreFromLoadDett,
//			codUoScrivaniaDirettore,			
//			desScrivaniaDirettore
		);		
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali
//		grid.addSort(new SortSpecifier("descrizione", SortDirection.ASCENDING));
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
		return buttons;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		setEditable(canEdit);
		super.setCanEdit(true);
		if(this.getCanvas() != null) {
			for(Canvas member : toolStrip.getMembers()) {
				if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("otherNewButtons")) {
					if(isEditable() && isShowEditButtons() && isShowNewButton()) {
						member.show();
					} else {
						member.hide();
					}
				}
				if(member instanceof ToolStripButton) {
					if(isEditable() && isShowEditButtons()) {
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
	
	public boolean isShowModifyButton(Record record) {
		return true;
	}	
		
	protected ControlListGridField buildModifyButtonField() {
		ControlListGridField modifyButtonField = new ControlListGridField("modifyButton");  
		modifyButtonField.setAttribute("custom", true);	
		modifyButtonField.setShowHover(true);		
		modifyButtonField.setCanReorder(false);
		modifyButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return buildImgButtonHtml("buttons/modify.png");
				}
				return null;
			}
		});
		modifyButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowModifyButton(record)) {
					return I18NUtil.getMessages().modifyButton_prompt();
				}
				return null;
			}
		});		
		modifyButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowModifyButton(event.getRecord())) {
					event.cancel();
					onClickModifyButton(event.getRecord());
				}
			}
		});		
		return modifyButtonField;
	}
	
	public boolean isShowDeleteButton(Record record) {
		return true;
	}	
	
	public ControlListGridField buildDeleteButtonField() {
		ControlListGridField deleteButtonField = new ControlListGridField("deleteButton");  
		deleteButtonField.setAttribute("custom", true);	
		deleteButtonField.setShowHover(true);		
		deleteButtonField.setCanReorder(false);
		deleteButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(record)) {
					return buildImgButtonHtml("buttons/delete.png");
				}
				return null;
			}
		});
		deleteButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(record)) {
					return I18NUtil.getMessages().deleteButton_prompt();	
				}
				return null;
			}
		});		
		deleteButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				if(isEditable() && isShowEditButtons() && isShowDeleteButton(event.getRecord())) {
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
		setGridFields("listaProponenti", fields);
	}	
	
	@Override
	public void onClickNewButton() {		
		grid.deselectAllRecords();
		ProponentiWindow lProponentiWindow = new ProponentiWindow(this, "proponentiWindow",  null, true) {
			
			@Override
			public void saveData(Record newRecord) {
				// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB						
				newRecord.setAttribute("id", "NEW_" + count++);
				addData(newRecord);				
			}
		};
		lProponentiWindow.show();
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		final ProponentiWindow lProponentiWindow = new ProponentiWindow(this, "proponentiWindow", record, false);
		lProponentiWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();	
		final ProponentiWindow lProponentiWindow = new ProponentiWindow(this, "proponentiWindow", record, true) {
			
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
		lProponentiWindow.show();
	}

	public void onClickDeleteButton(final ListGridRecord record) {
		grid.deselectAllRecords();
		removeData(record);		
	}
	
	@Override
	public void setData(RecordList data) {
		mappaErrori = new HashMap<String, HashSet<String>>();		
		super.setData(data);
	}
	
	@Override
	public void updateData(Record record, Record oldRecord) {
		String id = record.getAttribute("id");
		mappaErrori.put(id, new HashSet<String>());		
		super.updateData(record, oldRecord);
	}

	public boolean isShowRefreshListButton() {
		return false;
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
			// Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
			rec.setAttribute("descrizione", getDescrizioneWithCodice(getGrid().getRecords()[i], "codRapido", "descrizione"));			
			rec.setAttribute("listaRdP", trasformaListaDescrWithCodice(getGrid().getRecords()[i], "listaRdP"));
			rec.setAttribute("listaDirigenti", trasformaListaDescrWithCodice(getGrid().getRecords()[i], "listaDirigenti"));			
			rec.setAttribute("listaDirettori", trasformaListaDescrWithCodice(getGrid().getRecords()[i], "listaDirettori"));
//			rec.setAttribute("desScrivaniaDirettore", getDescrizioneWithCodice(getGrid().getRecords()[i], "codUoScrivaniaDirettore", "desScrivaniaDirettore"));	
			records[i] = rec;
		}
		return records;
	}
	
	public String getDescrizioneWithCodice(Record record, String codiceFieldName, String descrizioneFieldName) {
		String codice = record.getAttribute(codiceFieldName);
		String descrizione = record.getAttribute(descrizioneFieldName);
		if(codice != null && !"".equals(codice) && descrizione != null && !"".equals(descrizione)) {
			return codice + " - " + descrizione;
		} else if(descrizione!= null && !"".equals(descrizione)) {
			return descrizione;													
		} else if(codice != null && !"".equals(codice)) {
			return codice;
		}	
		return null;
	}
	
	protected String trasformaListaDescrWithCodice(Record record, String listaFieldName) {
		RecordList lista = record.getAttributeAsRecordList(listaFieldName);
		String ret = "";
		if (lista != null && lista.getLength() > 0) {
			StringBuffer lStringBuffer = new StringBuffer();
			for(int i = 0; i < lista.getLength(); i++) {
				Record riga = lista.get(i);
				String desWithCod = getDescrizioneWithCodice(riga, "codUoScrivania", "desScrivania");
				if(desWithCod != null && !"".equals(desWithCod)) {
					if (i > 0) {
						lStringBuffer.append(", ");
					}
					lStringBuffer.append(desWithCod);
				}
			}
			ret = lStringBuffer.toString();
		}
		return ret;
	}		
	
	@Override
	public void clearErrors() {
		super.clearErrors();
		mappaErrori = new HashMap<String, HashSet<String>>();	
		refreshRows();
	}
	
	public Map getMapErrors() {
		HashMap<String, String> errors = new HashMap<String, String>();
		if(mappaErrori != null) {
			for(String id: mappaErrori.keySet()) {
				if(mappaErrori.get(id) != null && mappaErrori.get(id).size() > 0) {
					errors.put(id, "Questa riga contiene errori");
				}
			}
		}
		return errors;
	}
	
	@Override
	public Boolean validate() {
		
		// le stesse logiche di validazione vanno replicate nel metodo valuesAreValid, ma senza popolare mappaErrori
		
		boolean valid = true;
		
		mappaErrori = new HashMap<String, HashSet<String>>();
		
		for (ListGridRecord record : grid.getRecords()) {
			
			String id = record.getAttribute("id");
			
			mappaErrori.put(id, new HashSet<String>());
			
			if(isRequiredIdUo()) {
				if(record.getAttribute("idUo") == null || "".equals(record.getAttribute("idUo"))) {
					mappaErrori.get(id).add("Campo \"" + getTitleIdUo() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredIdScrivaniaRdP()) {
				RecordList lista = record.getAttributeAsRecordList("listaRdP");
				boolean empty = true;
				if (lista != null && lista.getLength() > 0) {
					if(lista.get(0).getAttribute("idScrivania") != null && !"".equals(lista.get(0).getAttribute("idScrivania"))) {
						empty = false;
					}
				}					
				if(empty) {
					mappaErrori.get(id).add("Campo \"" + getTitleIdScrivaniaRdP() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredIdScrivaniaDirigente()) {
				RecordList lista = record.getAttributeAsRecordList("listaDirigenti");
				boolean empty = true;
				if (lista != null && lista.getLength() > 0) {
					if(lista.get(0).getAttribute("idScrivania") != null && !"".equals(lista.get(0).getAttribute("idScrivania"))) {
						empty = false;
					}
				}					
				if(empty) {
					mappaErrori.get(id).add("Campo \"" + getTitleIdScrivaniaDirigente() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredIdScrivaniaDirettore()) {
				RecordList lista = record.getAttributeAsRecordList("listaDirettori");
				boolean empty = true;
				if (lista != null && lista.getLength() > 0) {
					if(lista.get(0).getAttribute("idScrivania") != null && !"".equals(lista.get(0).getAttribute("idScrivania"))) {
						empty = false;
					}
				}					
				if(empty) {
					mappaErrori.get(id).add("Campo \"" + getTitleIdScrivaniaDirettore() + "\" obbligatorio");
					valid = false;				
				}
//				if(record.getAttribute("idScrivaniaDirettore") == null || "".equals(record.getAttribute("idScrivaniaDirettore"))) {
//					mappaErrori.get(id).add("Campo \"" + getTitleIdScrivaniaDirettore() + "\" obbligatorio");
//					valid = false;				
//				}
			}			
			grid.refreshRow(grid.getRecordIndex(record));
		}
		
		return valid;
	}
	
	public boolean isAbilSelezioneProponentiEstesa() {
		return false;
	}
	
	public String getIdTipoDocProposta() {
		return null;
	}
		
	public LinkedHashMap<String, String> getProponentiValueMap() {
		return null;
	}
	
	public LinkedHashMap<String, String> getSelezioneProponentiValueMap() {
		return null;	
	}
	
	public LinkedHashMap<String, String> getFlgUfficioGareProponentiMap() {
		return null;
	}	
		
	public boolean showIdUo() {
		return false;
	}
	
	public String getTitleIdUo() {
		return null;
	}
	
	public boolean isRequiredIdUo() {
		return false;
	}
	
	public String getAltriParamLoadComboIdUo() {
		return null;
	}
	
	public boolean isEditableIdUo() {
		return false;
	}
			
	public boolean showIdScrivaniaRdP() {
		return false;
	}
	
	public String getTitleIdScrivaniaRdP() {
		return null;
	}
	
	public boolean isRequiredIdScrivaniaRdP() {
		return false;
	}
	
	public String getAltriParamLoadComboIdScrivaniaRdP() {
		return null;
	}
	
	public boolean isEditableIdScrivaniaRdP() {
		return false;
	}
	
	public boolean showIdScrivaniaDirigente() {
		return false;
	}
	
	public String getTitleIdScrivaniaDirigente() {
		return null;
	}
	
	public boolean isRequiredIdScrivaniaDirigente() {
		return false;
	}
	
	public String getAltriParamLoadComboIdScrivaniaDirigente() {
		return null;
	}
	
	public boolean isEditableIdScrivaniaDirigente() {
		return false;
	}
	
	public boolean showIdScrivaniaDirettore() {
		return false;
	}
	
	public String getTitleIdScrivaniaDirettore() {
		return null;
	}
	
	public boolean isRequiredIdScrivaniaDirettore() {
		return false;
	}
	
	public String getAltriParamLoadComboIdScrivaniaDirettore() {
		return null;
	}
	
	public boolean isEditableIdScrivaniaDirettore() {
		return false;
	}
	
	public boolean showTipoVistoScrivaniaDirigente() {
		return false;
	}
	
	public boolean isRequiredTipoVistoScrivaniaDirigente() {
		return false;
	}
	
	public boolean showTipoVistoScrivaniaDirettore() {
		return false;
	}
	
	public boolean isRequiredTipoVistoScrivaniaDirettore() {
		return false;
	}
	
	public int getSelectItemOrganigrammaWidth() {
		return 500;
	}
	
	public void manageChangedUoSelezionata() {

	}
		
	public abstract boolean isGrigliaEditabile();
	
}
