/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGrid;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class StatisticheTrasparenzaAmministrativaDetail extends CustomDetail {
	
	// DynamicForm
	private DynamicForm _form;
	
	// SelectItem
	private SelectItem tipoReportItem; 
	private SelectItem idSezioneItem;
	
	// DateItem
	private DateItem dataDaItem; 
	private DateItem dataAItem;
	private DateItem dataRifItem;
	
	// HiddenItem
	private HiddenItem nomeSezioneItem;
	
	
	public StatisticheTrasparenzaAmministrativaDetail() {

		super("statisticheTrasparenzaAmministrativa");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			setCanFocus(true);		
		}		
		//Altrimenti gli elementi risultano spostati rispetto ai bordi
		setPadding(0);
		
		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);

		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(4);
		_form.setColWidths(100, 120, 50, "*");
		_form.setWrapItemTitles(false);
		
		// Tipo report
		tipoReportItem = new SelectItem("tipoReport","Tipo di report");
		
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("log_accesso_sezioni",              "Log accesso sezioni");
		lLinkedHashMap.put("log_accesso_sezioni_e_contenuti",  "Log accesso sezioni e contenuti");
		lLinkedHashMap.put("sezioni",                          "Sezioni");
		lLinkedHashMap.put("sezioni_senza_contenuti",          "Sezioni senza contenuti");
		lLinkedHashMap.put("abilitazioni_sezioni",             "Abilitazioni strutture vs sezioni");
		lLinkedHashMap.put("abilitazioni_sezioni_vs_utenti",   "Abilitazioni utenti vs sezioni");
		lLinkedHashMap.put("contenuti_documentali_pubblicati", "Contenuti documentali pubblicati");
		tipoReportItem.setValueMap(lLinkedHashMap);
		tipoReportItem.setWidth(600);
		tipoReportItem.setColSpan(3);
		tipoReportItem.setClearable(true);
		tipoReportItem.setAllowEmptyValue(true);
		tipoReportItem.setRedrawOnChange(true);
		tipoReportItem.setRequired(true);
		tipoReportItem.setStartRow(true);
		tipoReportItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				clearValue();
				markForRedraw();
			}
		});
		
		// Periodo dal
		dataDaItem = new DateItem("da", "Periodo dal");
		dataDaItem.setWidth(120);
		dataDaItem.setRequired(true);
		dataDaItem.setStartRow(true);
		dataDaItem.setAlign(Alignment.LEFT);
		
		CustomValidator lCustomValidatorDa = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				if (value == null)
					return true;
				Date lDateA = (Date) _form.getValue("a");
				if (lDateA == null)
					return true;
				else {
					Date lDateDa = (Date) value;
					long timeDa = lDateDa.getTime();
					long timeA = lDateA.getTime();
					return timeA >= timeDa;
				}
			}
		};
		lCustomValidatorDa.setErrorMessage("La data di fine periodo deve essere maggiore di quella iniziale");
		dataDaItem.setValidators(lCustomValidatorDa);
		dataDaItem.setValidateOnChange(true);
		dataDaItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoReportItem.getValueAsString() != null && (tipoReportItem.getValueAsString().equalsIgnoreCase("log_accesso_sezioni") || tipoReportItem.getValueAsString().equalsIgnoreCase("log_accesso_sezioni_e_contenuti"));
			}
		});

		
		// Periodo al
		dataAItem = new DateItem("a", "al");
		dataAItem.setWidth(120);
		CustomValidator lCustomValidatorA = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {
				if (value == null)
					return true;
				Date lDateDa = (Date) _form.getValue("da");
				if (lDateDa == null)
					return true;
				else {
					Date lDateA = (Date) value;
					long timeDa = lDateDa.getTime();
					long timeA = lDateA.getTime();
					return timeA >= timeDa;
				}
			}
		};
		lCustomValidatorA.setErrorMessage("La data di fine periodo deve essere maggiore di quella iniziale");
		dataAItem.setValidators(lCustomValidatorA);
		dataAItem.setValidateOnChange(true);		
		dataAItem.setEndRow(true);
		dataAItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoReportItem.getValueAsString() != null && (tipoReportItem.getValueAsString().equalsIgnoreCase("log_accesso_sezioni") || tipoReportItem.getValueAsString().equalsIgnoreCase("log_accesso_sezioni_e_contenuti"));
			}
		});
				
		// Data rif.
		dataRifItem = new DateItem("dataRif", "Alla data");
		dataRifItem.setWidth(120);
		dataRifItem.setRequired(true);
		dataRifItem.setStartRow(true);
		dataRifItem.setAlign(Alignment.LEFT);
		String defaultValue = DateTimeFormat.getFormat("dd/MM/yyyy").format(new Date());
		dataRifItem.setDefaultValue(defaultValue);
		dataRifItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoReportItem.getValueAsString() != null && tipoReportItem.getValueAsString().equalsIgnoreCase("contenuti_documentali_pubblicati");
			}
		});
		
		
		// Sezione
		SelectGWTRestDataSource idSezioneDS = new SelectGWTRestDataSource("LoadComboSezionePubblTraspAmmDataSource", true, "idNode", FieldType.TEXT);
		
		idSezioneItem = new SelectItem("idSezione", "Nella sezione") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				_form.setValue("nomeSezione", record.getAttribute("nome"));				
				_form.markForRedraw();				
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					_form.clearValue("idSezione");
					_form.clearValue("nomeSezione");
					_form.markForRedraw();
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				_form.clearValue("idSezione");
				_form.clearValue("nomeSezione");
				_form.markForRedraw();
			};					
			
			@Override
			protected ListGrid builPickListProperties() {
				final TreeGrid pickListProperties = new TreeGrid();
				pickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
				pickListProperties.setSelectionType(SelectionStyle.NONE);
				pickListProperties.setShowSelectedStyle(false);
				pickListProperties.addCellClickHandler(new CellClickHandler() {
					
					@Override
					public void onCellClick(CellClickEvent event) {
						if (event.getRecord().getAttributeAsBoolean("flgToAbil")) {
							onOptionClick(event.getRecord());
						} else {
							event.cancel();
							Layout.addMessage(new MessageBean("Sezione a cui non sei abilitato: selezione non consentita", "", MessageType.ERROR));
							Scheduler.get().scheduleDeferred(new ScheduledCommand() {

								@Override
								public void execute() {
									clearSelect();
								}
							});
						}	
					}
				});	
				pickListProperties.setShowHeader(false);
		        pickListProperties.setAutoFitFieldWidths(true); 
		        pickListProperties.setShowAllRecords(true);
		        pickListProperties.setLeaveScrollbarGap(false);
		        /*
		         * Impedisce il ricaricamento generale dell'albero ad ogni esplosione dei nodi anche 
		         * se nodi foglia
		         */
		        pickListProperties.setLoadDataOnDemand(false);
		        pickListProperties.setNodeIcon("blank.png");
		        pickListProperties.setFolderIcon("blank.png");
		        return pickListProperties;				
			}
		};
		
		idSezioneItem.setShowTitle(true);
		idSezioneItem.setStartRow(true);
		idSezioneItem.setWrapTitle(false);
		idSezioneItem.setWidth(600);
		idSezioneItem.setColSpan(3);	
		idSezioneItem.setPickListWidth(750);			
		ListGridField nomeField = new ListGridField("nome");		
		ListGridField idNodeField = new ListGridField("idNode");
		idNodeField.setHidden(true);		
		ListGridField flgToAbilField = new ListGridField("flgToAbil");
		flgToAbilField.setType(ListGridFieldType.BOOLEAN);
		flgToAbilField.setHidden(true);				
		/**
		 * Con la proprietà setDataSetType("tree"); nel setPickListFields va settato per primo un campo NON hidden
		 * perchè abbiamo riscontrato problemi di creazione del componente grafico
		 */
		idSezioneItem.setPickListFields(nomeField, idNodeField, flgToAbilField);  
		idSezioneItem.setDataSetType("tree"); 
		idSezioneItem.setDisplayField("nome");
		idSezioneItem.setValueField("idNode");              
		idSezioneItem.setOptionDataSource(idSezioneDS);	
		idSezioneItem.setAutoFetchData(false);
		idSezioneItem.setAlwaysFetchMissingValues(false);
		idSezioneItem.setFetchMissingValues(false);
		idSezioneItem.setCachePickListResults(true);
		idSezioneItem.setFetchDelay(500);
		idSezioneItem.setClearable(true);
		idSezioneItem.setShowIcons(true);
		idSezioneItem.setAllowEmptyValue(true);
		idSezioneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return tipoReportItem.getValueAsString() != null && tipoReportItem.getValueAsString().equalsIgnoreCase("contenuti_documentali_pubblicati");
			}
		});
				
		nomeSezioneItem = new HiddenItem("nomeSezione");
		
		_form.setFields(tipoReportItem, dataDaItem, dataAItem, dataRifItem,idSezioneItem, nomeSezioneItem);
		
		// Bottone “Genera report” e “Chiudi”
		Button stampaButton = new Button("Genera report");
		stampaButton.setIcon("ok.png");
		stampaButton.setIconSize(16);
		stampaButton.setAutoFit(true);
		stampaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (_form.validate()) {					
					generaReport();
				}
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setName("buttons");
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(stampaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(_form);

		addMember(layout);
		addMember(_buttons);
	}

	private void generaReport(){

		String tipoReport = _form.getValueAsString("tipoReport");
		Date lDateDa      = (Date) _form.getValue("da");
		Date lDateA       = (Date) _form.getValue("a");
		Date lDateRif     = (Date) _form.getValue("dataRif");
		String idSezione = _form.getValueAsString("idSezione");
		
		if (tipoReport== null)
			tipoReport = "";
		
		if (!tipoReport.equalsIgnoreCase("contenuti_documentali_pubblicati")){
			lDateRif  = null;
			idSezione = null;
		}
		
		Record pRecord = new Record();
		pRecord.setAttribute("tipoReport", tipoReport);
		pRecord.setAttribute("da",         lDateDa);
		pRecord.setAttribute("a",          lDateA);
		pRecord.setAttribute("dataRif",    lDateRif);
		pRecord.setAttribute("idSezione",  idSezione);
		
		Layout.showWaitPopup("Elaborazione in corso...");
		
		GWTRestDataSource reporDatasource = new GWTRestDataSource("ContenutiAmmTraspDatasource");
		reporDatasource.executecustom("generaReport", pRecord, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					String filename = "Results.xls";
					String url = response.getData()[0].getAttribute("tempFileOut");
					// se l'esportazione ha restituito un uri allora lancio il download del documento generato, altrimenti
					if (url != null) {
						Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" + URL.encode(url));
					}
				}
				Layout.hideWaitPopup();
			}
		});
	}
	
	private void clearValue(){
		_form.clearErrors(true);

		dataDaItem.clearValue();
		dataAItem.clearValue();
		dataRifItem.clearValue();
		idSezioneItem.clearValue();
		nomeSezioneItem.clearValue();
		
		_form.clearValue("da");
		_form.clearValue("a");
		_form.clearValue("dataRif");
		_form.clearValue("idSezione");
		_form.clearValue("nomeSezione");				
	}
}