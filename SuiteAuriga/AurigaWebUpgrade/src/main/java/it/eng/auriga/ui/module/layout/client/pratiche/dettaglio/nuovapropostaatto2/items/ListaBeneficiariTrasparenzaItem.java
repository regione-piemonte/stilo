/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
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
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.shared.bean.CelleExcelBeneficiariEnum;
import it.eng.utility.ui.module.core.client.callback.UploadItemCallBackHandler;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.GridItem;
import it.eng.utility.ui.module.layout.client.common.IDatiSensibiliItem;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;
import it.eng.utility.ui.module.layout.client.common.file.ManageInfoCallbackHandler;

public abstract class ListaBeneficiariTrasparenzaItem extends GridItem implements IDatiSensibiliItem {
	
	protected ListGridField id;	
	protected ListGridField mostraErrori;
	
	private ListGridField tipo;
	private ListGridField tipoPersona;
	private ListGridField cognome;
	private ListGridField nome;
	private ListGridField ragioneSociale;
	private ListGridField codFiscalePIVA;
	private ListGridField importo;
	private ListGridField flgPrivacy;
	
	protected ControlListGridField detailButtonField;
	protected ControlListGridField modifyButtonField;
	protected ControlListGridField deleteButtonField;
		
	protected HashMap<String, HashSet<String>> mappaErrori = new HashMap<String, HashSet<String>>();
	
	protected int count = 0;
	
//	/**RICORDARSI DI TENERE GLI INDICI ALLINEATI ANCHE NELL'IMPORT (BeneficiariTrasparenzaDataSource)  **/
//	private static final int CELLA_TIPO_PERSONA = 0;
//	private static final int CELLA_NOME = 1;
//	private static final int CELLA_COGNOME = 2;
//	private static final int CELLA_RAGIONE_SOCIALE = 3;
//	private static final int CELLA_CODFIS_PIVA = 4;
//	private static final int CELLA_IMPORTO = 5;
//	private static final int CELLA_FLG_PRIVACY = 6;
//	private static final int CELLA_TIPO = 7;
//	
//	/** AGGIORNARE NUMERO CAMPI TOTALI SE SI AGGIUNGE UNA COLONNA**/
//	private static final int NUMERO_CAMPI = 8;
	
	private String uriFileImportExcel;
	
	public ListaBeneficiariTrasparenzaItem(String name) {
		
		super(name, "listaBeneficiariTrasparenza");
		
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
		
		tipo = new ListGridField("tipo", getTitleTipo());
		tipo.setValueMap("mandatario", "mandante");
		tipo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showTipo();
			}
		});
		
		tipoPersona = new ListGridField("tipoPersona", getTitleTipoPersona());		
		Map<String, String> tipoPersonaValueMap = getValueMapTipoPersona();
		if(tipoPersonaValueMap != null && tipoPersonaValueMap.size() > 0) {
			tipoPersona.setValueMap(tipoPersonaValueMap);			
		} else {			
			tipoPersona.setValueMap("fisica", "giuridica");
		}	
		tipoPersona.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showTipoPersona();
			}
		});
		
		cognome = new ListGridField("cognome", getTitleCognome());
		cognome.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showCognome();
			}
		});		
		
		nome = new ListGridField("nome", getTitleNome());
		nome.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showNome();
			}
		});	
	
		ragioneSociale = new ListGridField("ragioneSociale", getTitleRagioneSociale());
		ragioneSociale.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showRagioneSociale();
			}
		});		
		
		codFiscalePIVA = new ListGridField("codFiscalePIVA", getTitleCodFiscalePIVA());
		codFiscalePIVA.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showCodFiscalePIVA();
			}
		});		
		
		importo = new ListGridField("importo", getTitleImporto());
		importo.setType(ListGridFieldType.FLOAT);	
		importo.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("importo"));				
			}
		});	
		importo.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showImporto();
			}
		});	
		
		// da lista dati contabili sib
		flgPrivacy = new ListGridField("flgPrivacy", getTitleFlgPrivacy());
		flgPrivacy.setType(ListGridFieldType.ICON);
		flgPrivacy.setWidth(30);
		flgPrivacy.setIconWidth(16);
		flgPrivacy.setIconHeight(16);
		Map<String, String> flgPrivacyIcons = new HashMap<String, String>();
		flgPrivacyIcons.put("true", "ok.png");
		flgPrivacyIcons.put("false", "blank.png");
		flgPrivacyIcons.put("undefined", "blank.png");
		flgPrivacyIcons.put("", "blank.png");
		flgPrivacy.setValueIcons(flgPrivacyIcons);
		flgPrivacy.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {				
				if (record.getAttribute("flgPrivacy") != null && "true".equals(record.getAttribute("flgPrivacy"))) {
					return getTitleFlgPrivacy();
				}
				return null;
			}
		});
		flgPrivacy.setShowIfCondition(new ListGridFieldIfFunction() {
			
			@Override
			public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
				return showFlgPrivacy();
			}
		});	
		
		setGridFields(
			id,
			mostraErrori,
			tipo,
			tipoPersona,
			cognome,
			nome,
			ragioneSociale,
			codFiscalePIVA,
			importo,
			flgPrivacy
		);		
	}
	
	@Override
	public ListGrid buildGrid() {
		ListGrid grid = super.buildGrid();
//		grid.setStyleName(it.eng.utility.Styles.noBorderItem);
		grid.setShowAllRecords(true);
		// Ordinamenti iniziali		
//		grid.addSort(new SortSpecifier("tipoPersona", SortDirection.ASCENDING));
//		grid.addSort(new SortSpecifier("ragioneSociale", SortDirection.ASCENDING));
//		grid.addSort(new SortSpecifier("cognome", SortDirection.ASCENDING));
//		grid.addSort(new SortSpecifier("nome", SortDirection.ASCENDING));
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
		
		ToolStripButton downloadTemplateExcelButton = new ToolStripButton();   
		downloadTemplateExcelButton.setIcon("file/download_manager.png");   
		downloadTemplateExcelButton.setIconSize(16);
		downloadTemplateExcelButton.setPrefix("downloadTemplateExcel");
		downloadTemplateExcelButton.setPrompt("Download template excel beneficiario/i");
		downloadTemplateExcelButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickDownloadTemplateExcelButton();   	
			}   
		});  
		
		buttons.add(downloadTemplateExcelButton);
		
		ToolStripButton exportXlsButton = new ToolStripButton();   
		exportXlsButton.setIcon("export/xls.png"); 
		exportXlsButton.setIconSize(16);
		exportXlsButton.setPrefix("exportXlsButton");
		exportXlsButton.setPrompt("Esporta in formato xls");
		exportXlsButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				onClickExportBeneficiariButton();   	
			}   
		});  
		
		buttons.add(exportXlsButton);

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
		
		if (isShowNewButton()) {

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
		}
		
		return editCanvas;
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
					if (member.getPrefix() != null && member.getPrefix().equalsIgnoreCase("exportXlsButton")){
						if (isShowExportXlsButton()) {								
								((ToolStripButton) member).show();
						}
						else {
							((ToolStripButton) member).hide();
						}
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
		setGridFields("listaBeneficiariTrasparenza", fields);
	}	
	
	@Override
	public void onClickNewButton() {		
		grid.deselectAllRecords();
		int riga = grid.getDataAsRecordList().getLength();
		BeneficiariTrasparenzaWindow lBeneficiariTrasparenzaWindow = new BeneficiariTrasparenzaWindow(this, "beneficiariTrasparenzaWindow",  null, riga, true) {
			
			@Override
			public void saveData(Record newRecord) {
				// assegno un id temporaneo (con prefisso new_) a tutte le nuove righe create e non ancora salvate in DB						
				newRecord.setAttribute("id", "NEW_" + count++);
				addData(newRecord);				
			}
		};
		lBeneficiariTrasparenzaWindow.show();
	}
	
	public void onClickDetailButton(final ListGridRecord record) {
		int riga = grid.getRecordIndex(record);
		final BeneficiariTrasparenzaWindow lBeneficiariTrasparenzaWindow = new BeneficiariTrasparenzaWindow(this, "beneficiariTrasparenzaWindow", record, riga, false);
		lBeneficiariTrasparenzaWindow.show();
	}
	
	public void onClickModifyButton(final ListGridRecord record) {
		// Prima di effettuare la modifica devo deselezionare il record o quando viene sostituito con il nuovo aumenta di altezza, finchè non ci passi sopra col cursore del mouse
		// Finita la modifica lo riseleziono				
		grid.deselectAllRecords();	
		int riga = grid.getRecordIndex(record);
		final BeneficiariTrasparenzaWindow lBeneficiariTrasparenzaWindow = new BeneficiariTrasparenzaWindow(this, "beneficiariTrasparenzaWindow", record, riga, true) {
			
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
		lBeneficiariTrasparenzaWindow.show();
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
	
	public boolean isShowExportXlsButton() {
		return true;
	}
	
	abstract public void onClickImportXlsButton(String uriFileImportExcel, String mimetype);
	
	abstract public void onClickDownloadTemplateExcelButton();	
	
	public void onClickExportBeneficiariButton() {		
		if (getGrid().getDataAsRecordList() != null && getGrid().getDataAsRecordList().getLength() <= 0) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando la lista è vuota", "", MessageType.ERROR));
			return;
		}
		
		if (getGrid().isGrouped()) {
			Layout.addMessage(new MessageBean("Non è consentita l'esportazione su file quando c'è un raggruppamento attivo sulla lista", "", MessageType.ERROR));
			return;
		} 
		
		LinkedHashMap<String, String> mappa = createFieldsMap(true);
//		String[] fields = new String[mappa.keySet().size()];
//		String[] headers = new String[mappa.keySet().size()];
		String[] fields = new String[CelleExcelBeneficiariEnum.values().length];
		String[] headers = new String[CelleExcelBeneficiariEnum.values().length];
		
		/**
		 * 
		 * DEVO TENERE ALLINEATI L'ORDINE DELLE COLONNE SIA NELL EXPORT CHE NELL IMPORT, COSI NON HO PROBLEMI NELL IMPORT IN QUANTO 
		 * LA LETTURA DEI CAMPI LI E' STATICA E POSIZIONALE, QUINDI DEVO SAPERE ALLA COLONNA X ESATTAMENTE CHE TIPO DI DATO C'è
		 * 
		 * **/
		
		for (String key : mappa.keySet()) {			
			/*campi da escludere nell export*/
			if(!key.equalsIgnoreCase("mostraErrori")) {				
				String header = mappa.get(key);
				if(header.equalsIgnoreCase(getTitleTipoPersona())) {
					header = "Tipo persona";
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.TIPO_PERSONA)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.TIPO_PERSONA)] = header;
				}else if(header.equalsIgnoreCase(getTitleNome())) {
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.NOME)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.NOME)] = header;
				}else if(header.equalsIgnoreCase(getTitleCognome())) {
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.COGNOME)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.COGNOME)] = header;
				}else if(header.equalsIgnoreCase(getTitleRagioneSociale())) {
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.RAGIONE_SOCIALE)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.RAGIONE_SOCIALE)] = header;
				}else if(header.equalsIgnoreCase(getTitleCodFiscalePIVA())) {
					header = "C.F/P. IVA";
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.CF_PIVA)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.CF_PIVA)] = header;
				}else if(header.equalsIgnoreCase(getTitleImporto())) {
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.IMPORTO)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.IMPORTO)] = header;
				}else if(header.equalsIgnoreCase(getTitleFlgPrivacy())) {
					header = "Dati protetti da privacy";
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.PROT_PRIVACY)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.PROT_PRIVACY)] = header;
				}else if(header.equalsIgnoreCase(getTitleTipo())) {
					fields[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.TIPO)] = key;
					headers[CelleExcelBeneficiariEnum.getIndexFromCell(CelleExcelBeneficiariEnum.TIPO)] = header;
				}
			}
		}
		
		final Record record = new Record();
		record.setAttribute("nomeEntita", "listaBeneficiariTrasparenza");
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
	
	};
		
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
				if("flgPrivacy".equalsIgnoreCase(fieldName)) {
					String valueField = getGrid().getRecords()[i].getAttribute(fieldName);					
					rec.setAttribute(fieldName, "true".equalsIgnoreCase(valueField) ? "si" : "no");
				} else {
					rec.setAttribute(fieldName, getGrid().getRecords()[i].getAttribute(fieldName));
				}
			}
			// Devo gestire nell'esportazione le colonne che hanno un CellFormatter settato
//			String numeroCapitolo = getGrid().getRecords()[i].getAttribute("numeroCapitolo");
//			String descrizioneCapitolo = getGrid().getRecords()[i].getAttribute("descrizioneCapitolo");
//			if(numeroCapitolo != null && !"".equals(numeroCapitolo)) {
//				if(descrizioneCapitolo != null && !"".equals(descrizioneCapitolo)) {
//					rec.setAttribute("descrizioneCapitolo", numeroCapitolo + " - " + descrizioneCapitolo);
//				} else {
//					rec.setAttribute("descrizioneCapitolo", numeroCapitolo);
//				}				
//			}	
			records[i] = rec;
		}
		return records;
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
			
			if(isRequiredTipo()) {
				if(record.getAttribute("tipo") == null || "".equals(record.getAttribute("tipo"))) {
					mappaErrori.get(id).add("Campo \"" + getTitleTipo() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredTipoPersona()) {
				if(record.getAttribute("tipoPersona") == null || "".equals(record.getAttribute("tipoPersona"))) {
					mappaErrori.get(id).add("Campo \"" + getTitleTipoPersona() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredCognome() && isTipoPersonaFisica(record)) {
				if(record.getAttribute("cognome") == null || "".equals(record.getAttribute("cognome"))) {
					mappaErrori.get(id).add("Campo \"" + getTitleCognome() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredNome() && isTipoPersonaFisica(record)) {
				if(record.getAttribute("nome") == null || "".equals(record.getAttribute("nome"))) {
					mappaErrori.get(id).add("Campo \"" + getTitleNome() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredRagioneSociale() && isTipoPersonaGiuridica(record)) {
				if(record.getAttribute("ragioneSociale") == null || "".equals(record.getAttribute("ragioneSociale"))) {
					mappaErrori.get(id).add("Campo \"" + getTitleRagioneSociale() + "\" obbligatorio");
					valid = false;				
				}
			}
			if(isRequiredCodFiscalePIVA() && isTipoPersonaValorizzato(record)) {				
				if (isTipoPersonaFisica(record)) {
					if(record.getAttribute("codFiscalePIVA") == null || "".equals(record.getAttribute("codFiscalePIVA"))) {
						mappaErrori.get(id).add("Campo \"" + getTitleCodFiscale() + "\" obbligatorio");
						valid = false;				
					}
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					if(!regExp.test((String) record.getAttribute("codFiscalePIVA"))) {
						mappaErrori.get(id).add("Valore \"" + getTitleCodFiscale() + "\" non valido");
						valid = false;	
					}
				} else {
					if(record.getAttribute("codFiscalePIVA") == null || "".equals(record.getAttribute("codFiscalePIVA"))) {
						mappaErrori.get(id).add("Campo \"" + getTitleCodFiscalePIVA() + "\" obbligatorio");
						valid = false;				
					}
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscalePIVARegExp());
					if(!regExp.test((String) record.getAttribute("codFiscalePIVA"))) {
						mappaErrori.get(id).add("Valore \"" + getTitleCodFiscalePIVA() + "\" non valido");
						valid = false;	
					}
				}
			}
			if(isRequiredImporto() && isTipoPersonaValorizzato(record)) {
				if(record.getAttribute("importo") == null || "".equals(record.getAttribute("importo"))) {
					mappaErrori.get(id).add("Campo \"" + getTitleImporto() + "\" obbligatorio");
					valid = false;				
				}
				RegExp importoPrecisionRegExp = RegExp.compile("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1,2})?)$");
				if(!importoPrecisionRegExp.test((String) record.getAttribute("importo"))) {
					mappaErrori.get(id).add("Valore \"" + getTitleImporto() + "\" non valido o superato il limite di 2 cifre decimali");
					valid = false;	
				}				
				if(record.getAttribute("importo") != null && !"".equals(record.getAttribute("importo"))) {
					double importo = new Double(NumberFormat.getFormat("#,##0.00").parse(record.getAttribute("importo"))).doubleValue();			
					// Per i beneficiari deve essere possibile inserire un importo pari a zero
//					if(importo <= 0) {
//						mappaErrori.get(id).add("Valore \"" + getTitleImporto() + "\" non valido: l'importo deve essere maggiore di zero");
//						valid = false;							
//					}
					if(importo < 0) {
						mappaErrori.get(id).add("Valore \"" + getTitleImporto() + "\" non valido: l'importo deve essere maggiore o uguale di zero");
						valid = false;							
					}
				}				
			}
			
			grid.refreshRow(grid.getRecordIndex(record));
		}
		
		return valid;
	}
	
	protected boolean isTipoMandatario(Record record) {
		boolean isTipoMandatario = record.getAttribute("tipo") != null && "mandatario".equalsIgnoreCase(record.getAttribute("tipo"));
		return isTipoMandatario;
	}
	
	protected boolean isTipoMandante(Record record) {
		boolean isTipoMandante = record.getAttribute("tipo") != null && "mandante".equalsIgnoreCase(record.getAttribute("tipo"));
		return isTipoMandante;
	}
	
	protected boolean isTipoPersonaFisica(Record record) {
		boolean isTipoPersonaFisica = record.getAttribute("tipoPersona") != null && "fisica".equalsIgnoreCase(record.getAttribute("tipoPersona"));
		return isTipoPersonaFisica;
	}

	protected boolean isTipoPersonaGiuridica(Record record) {
		boolean isTipoPersonaGiuridica = record.getAttribute("tipoPersona") != null && "giuridica".equalsIgnoreCase(record.getAttribute("tipoPersona"));
		return isTipoPersonaGiuridica;
	}
	
	protected boolean isTipoPersonaValorizzato(Record record) {
		boolean isTipoPersonaValorizzato = record.getAttribute("tipoPersona") != null && !"".equals(record.getAttribute("tipoPersona"));
		return isTipoPersonaValorizzato;
	}
		
	public abstract boolean isGrigliaEditabile();
	
	public boolean showTipo() {
		return false;
	}
	
	public String getTitleTipo() {
		return "Tipo";
	}
	
	public boolean isRequiredTipo() {
		return false;
	}
	
	public boolean isEditableTipo() {
		return true;
	}
	
	public boolean showTipoPersona() {
		return true;
	}
	
	public String getTitleTipoPersona() {
		return "Persona";
	}
	
	public boolean isRequiredTipoPersona() {
		return false;
	}						
	
	public HashMap<String, String> getValueMapTipoPersona() {
		return null;
	}
	
	public String getDefaultValueTipoPersona() {
		return null;
	}
		
	public boolean isEditableTipoPersona() {
		return true;
	}
	
	public boolean showCognome() {
		return true;
	}
	
	public String getTitleCognome() {
		return "Cognome";
	}	
	
	public boolean isRequiredCognome() {
		return false;
	}	
	
	public boolean isEditableCognome() {
		return true;
	}	
	
	public boolean showNome() {
		return true;
	}	
	
	public String getTitleNome() {
		return "Nome";
	}	
	
	public boolean isRequiredNome() {
		return false;
	}	
	
	public boolean isEditableNome() {
		return true;
	}	
	
	public boolean showRagioneSociale() {
		return true;
	}
		
	public String getTitleRagioneSociale() {
		return "Ragione sociale";
	}
		
	public boolean isRequiredRagioneSociale() {
		return false;
	}
		
	public boolean isEditableRagioneSociale() {
		return true;
	}	
	
	public boolean showCodFiscalePIVA() {
		return true;
	}	
	
	public String getTitleCodFiscalePIVA() {
		return "C.F./P.I.";
	}	
	
	public String getTitleCodFiscale() {
		return "C.F.";
	}	
	
	public boolean isRequiredCodFiscalePIVA() {
		return false;
	}	
	
	public boolean isEditableCodFiscalePIVA() {
		return true;
	}	 
	
	public boolean showImporto() {
		return true;
	}	
	
	public String getTitleImporto() {
		return "Importo";
	}	
	
	public boolean isRequiredImporto() {
		return false;
	}
		
	public boolean isEditableImporto() {
		return true;
	}
		
	public boolean showFlgPrivacy() {
		return false;
	}
	
	public String getTitleFlgPrivacy() {
		return null;
	}
	
	public boolean getDefaultValueAsBooleanFlgPrivacy() {
		return false;
	}
	
	public boolean isEditableFlgPrivacy() {
		return false;
	}
	
	@Override
	public boolean hasDatiSensibili() {
		if(showFlgPrivacy()) {	
			for (ListGridRecord record : grid.getRecords()) {
				boolean flgPrivacy = record.getAttribute("flgPrivacy") != null ? Boolean.parseBoolean(record.getAttribute("flgPrivacy")) : false;
				if(flgPrivacy) {
					return true;
				}
			}
		}
		return false;
	}

}
