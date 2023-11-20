/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

public class DocumentiDaRifirmareList extends CustomList {

	private ListGridField idDocField;
	private ListGridField firmatarioField;
	
	public DocumentiDaRifirmareList(String nomeEntita) {
		
		super(nomeEntita);			
		
		idDocField = new ListGridField("idDoc" , "Id. documento");
		
		firmatarioField = new ListGridField("firmatario", "Firmatario");
				
		setFields(new ListGridField[] {
				idDocField,
				firmatarioField
		});  
		
	}
	
	public void scarica(Record lRecord) {
		getLayout().getDatasource().performCustomOperation("getFile", lRecord, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record lRecordFile = response.getData()[0];
					scaricaFile(lRecordFile.getAttribute("idFile"), lRecordFile.getAttribute("nomeFile"), lRecordFile.getAttribute("uri"));
				}
			}
		}, new DSRequest());
	}
	
	public void scaricaFile(String idDoc, String display, String uri) {
		Record lRecord = new Record();
		lRecord.setAttribute("displayFilename", display);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("sbustato", "false");
		lRecord.setAttribute("remoteUri", "true");
		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");		
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		
		return true;
	}
	
	@Override
	protected ControlListGridField buildDetailButtonField() {
		
		ControlListGridField scaricaButton = new ControlListGridField("scaricaButton");  
		scaricaButton.setAttribute("custom", true);	
		scaricaButton.setShowHover(true);		
		scaricaButton.setCanReorder(false);
		scaricaButton.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {			
				return buildImgButtonHtml("file/download_manager.png");								
			}
		});
		scaricaButton.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					
				return "Scarica file";					
			}
		});		
		scaricaButton.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				scarica(record);
			}
		});
		return scaricaButton;					
	}
	
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
