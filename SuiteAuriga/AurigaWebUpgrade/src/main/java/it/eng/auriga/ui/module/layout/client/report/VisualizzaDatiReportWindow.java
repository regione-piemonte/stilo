/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class VisualizzaDatiReportWindow extends ModalWindow {

	private CustomList list;
	private int level = 0;
	private Integer[] idSoggetti = new Integer[3];
	
	protected  ToolStrip buttons;
	protected  DetailToolStripButton backToListButton;
	protected  DetailToolStripButton exportPdfButton;
	protected  DetailToolStripButton exportXLSButton;

	public VisualizzaDatiReportWindow(final Record pRecord) {
		
		super("datiReport", false);
		
		setTitle("Statistiche sui protocolli - Selezione parametri del report");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setWidth(600);
		setHeight(200);
		
		list = new CustomList("datiReport", false){
			protected com.smartgwt.client.widgets.Canvas createRecordComponent(com.smartgwt.client.widgets.grid.ListGridRecord record, Integer colNum) {
				return null;
			};
		};		
		
		ListGridField idSoggettoField = new ListGridField("idSoggetto");
		idSoggettoField.setHidden(true);
		idSoggettoField.setCanHide(false);
		idSoggettoField.setCanGroupBy(false);
		
		ListGridField chiField = new ListGridField("label");
		chiField.setTitle("Chi");
		
		ListGridField valoreField = new ListGridField("valore");
		valoreField.setType(ListGridFieldType.INTEGER);
		if (pRecord.getAttributeAsString("tipoReport").equals("tempi_medi_tra_assegnazione_e_presa_in_carico")){
			valoreField.setTitle("Valore (in ore)");
		} else valoreField.setTitle("Valore");
		
		ListGridField percField = new ListGridField("perc");
		percField.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				String value = record.getAttribute(fieldName);
				if(value != null && !"".equals(value)) {
					String tempValue = value.replace(",", ".");
					BigDecimal valueXOrd = new BigDecimal(tempValue).multiply(new BigDecimal(100));
					return valueXOrd;
				}
				return null;
			}
		});
		if (pRecord.getAttributeAsString("tipoReport").equals("tempi_medi_tra_assegnazione_e_presa_in_carico")){
			percField.setTitle("Percentile");
		} else percField.setTitle("%");
		
		list.setFields(idSoggettoField, chiField, valoreField, percField);		
		
		list.getRecordClickHandler().removeHandler();
		list.addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				String idSoggettoString = event.getRecord().getAttributeAsString("idSoggetto");
				Integer idSoggetto = Integer.valueOf(idSoggettoString.replaceAll("\\.", ""));
				if (level < 2){
					level++;
					idSoggetti[level] = idSoggetto;
					retrieveDataset(pRecord, idSoggetto);
				}
			}
		});
		
		backToListButton = new DetailToolStripButton(I18NUtil.getMessages().backToListButton_prompt(), "buttons/back.png");
		backToListButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				back(pRecord);
			}   
		}); 
		
		exportPdfButton = new DetailToolStripButton(I18NUtil.getMessages().exportButton_prompt(), "export/pdf.png");
		exportPdfButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				exportPdf(pRecord);
			}   
		}); 
		
		exportXLSButton = new DetailToolStripButton(I18NUtil.getMessages().exportButton_prompt(), "export/xls.png");
		exportXLSButton.addClickHandler(new ClickHandler() {   
			@Override
			public void onClick(ClickEvent event) {   
				exportXls(pRecord);
			}   
		}); 
		
		buttons = new ToolStrip();   
		buttons.setWidth100();       
		buttons.setHeight(30);
		buttons.addButton(backToListButton);
		buttons.addButton(exportPdfButton);
		buttons.addButton(exportXLSButton);  
		buttons.addFill(); //push all buttons to the right 
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET 
		VLayout portletLayout = new VLayout();  
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		
		layout.addMember(list);
				
		portletLayout.addMember(layout);		
		portletLayout.addMember(buttons);
						
		setBody(portletLayout);
		
		idSoggetti[0] = null;
		retrieveDataset(pRecord,  null);
		setAttribute("edgeTop", 46, true);
		
		setIcon("menu/statisticheProtocolli.png");
	}
	
	protected void exportXls(Record pRecord) {

		export("xls", pRecord);			
	}
	
	protected void exportPdf(Record pRecord) {

		export("pdf", pRecord);
	}

	protected void export(final String formatoExport, Record recordInput) {
		
		Record record = new Record();
		record.setAttribute("nomeEntita", getTitle());
		record.setAttribute("formatoExport", formatoExport);
		record.setAttribute("csvSeparator", "|*|");
		record.setAttribute("criteria", new Criteria());
		LinkedHashMap<String, String> mappa = new LinkedHashMap<String, String>();					
		for (int i = 0; i < list.getFields().length; i++) {
			ListGridField field = list.getFields()[i];
			String fieldName = field.getName();
			if(fieldName.endsWith("XOrd")) {
				fieldName = fieldName.substring(0, fieldName.lastIndexOf("XOrd"));
			}
			String fieldTitle = field.getTitle();
			if(!list.getControlFields().contains(fieldName) && !"_checkboxField".equals(fieldName)) {						
				mappa.put(fieldName, fieldTitle);
			}
		}
		String[] fields = new String[mappa.keySet().size()];
		String[] headers = new String[mappa.keySet().size()];
		int n = 0;
		for(String key : mappa.keySet()) {
			fields[n] = key;						
			headers[n] = mappa.get(key);
			n++;
		}
		
		if(recordInput != null) {
			String intestazione = "Report: ";
			if(recordInput.getAttributeAsString("tipoReport") != null &&
					!"".equals(recordInput.getAttributeAsString("tipoReport"))){
				if("assegnazioni_eseguite".equals(recordInput.getAttributeAsString("tipoReport"))){
					intestazione += "Assegnazioni eseguite ";
				} else if("registrazioni_a_protocollo_effettuate".equals(recordInput.getAttributeAsString("tipoReport"))){
					intestazione += "Registrazioni a Protocollo Effettuate ";
				} else if("assegnazioni_senza_presa_in_carico".equals(recordInput.getAttributeAsString("tipoReport"))){
					intestazione += "Assegnazioni senza prese in carico ";
				} else if("tempi_medi_tra_assegnazione_e_presa_in_carico".equals(recordInput.getAttributeAsString("tipoReport"))){
					intestazione += "Tempi medi tra assegnazione e presa in carico ";
				}
			}
			
			intestazione += "Periodo ";
			
			if(recordInput.getAttributeAsString("da") != null &&
					!"".equals(recordInput.getAttributeAsString("da"))){
				intestazione += "dal " + DateUtil.format((Date) recordInput.getAttributeAsDate("da")) + " ";
			}
			if(recordInput.getAttributeAsString("a") != null &&
					!"".equals(recordInput.getAttributeAsString("a"))){
				intestazione += "al " + DateUtil.format((Date) recordInput.getAttributeAsDate("a")) +  " ";
			}
			if(recordInput.getAttributeAsString("tipoDiRegistrazione") != null &&
					!"".equals(recordInput.getAttributeAsString("tipoDiRegistrazione"))){
				intestazione += " per registrazioni: ";
			    String tipiReg [] = recordInput.getAttributeAsString("tipoDiRegistrazione").split(",");
			    for(String item : tipiReg){
			    	if("I".equals(item)){
						intestazione += "Interna, ";
					} if("U".equals(item)){
						intestazione += "Uscita, ";
					} else if("E".equals(item)){
						intestazione += "Entrata, ";
					}
			    }
			} else {
				intestazione += "per tutte le registrazioni";
			}
			record.setAttribute("intestazioneReport", intestazione);
		}
		
		record.setAttribute("fields", fields);					
		record.setAttribute("headers", headers);										
		Record[] records = new Record[list.getRecords().length];						
		for (int i = 0; i < list.getRecords().length; i++) {	
			Record rec = new Record();
			for(String fieldName : fields) {
				rec.setAttribute(fieldName, list.getRecords()[i].getAttribute(fieldName));								
			}
			records[i] = rec;
		}			
		record.setAttribute("records", records);	
		GWTRestService<Record, Record> lReportDatasource = new GWTRestService("ReportDatasource");
		lReportDatasource.performCustomOperation("export", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String filename = "Results." + formatoExport;
				String uri = response.getData()[0].getAttribute("tempFileOut");
				
				 com.google.gwt.user.client.Window.Location.assign(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + URL.encode(filename) + "&url=" + URL.encode(uri));
			}
		}, new DSRequest());
	}

	protected void back(Record pRecord) {
		level--;
		retrieveDataset(pRecord, idSoggetti[level]);
	}

	protected void retrieveDataset(Record pRecord, Integer idSoggetto) {
		pRecord.setAttribute("level", level);
		pRecord.setAttribute("idSoggetto", idSoggetto);
		
		GWTRestService<Record, Record> lReportDatasource = new GWTRestService("ReportDatasource");
		lReportDatasource.call(pRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				buildWindowAndDataSet(object);
			}
		});
	}

	protected void buildWindowAndDataSet(Record lRecord) {
		setTitle(lRecord.getAttribute("title"));
		Record[] lRecords = lRecord.getAttributeAsRecordArray("dataset");		
		if(lRecords.length > 0) {
			list.setData(lRecords);
			if (level == 1){
				backToListButton.show();
			}
			if (level == 0){
				backToListButton.hide();
			}
			show();
		} else {
			Layout.addMessage(new MessageBean("Nessun risultato trovato", "", MessageType.ERROR));
			if (level == 0){
				markForDestroy();
			}
		}
	}
	
}