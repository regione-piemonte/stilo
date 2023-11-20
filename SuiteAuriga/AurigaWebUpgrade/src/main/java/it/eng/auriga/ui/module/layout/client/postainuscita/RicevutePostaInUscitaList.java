/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class RicevutePostaInUscitaList extends CustomList {
	
	private ListGridField idEmailField;
	private ListGridField categoriaField;
	private ListGridField dataRicezioneField;
	private ListGridField mittenteField;
	
	public RicevutePostaInUscitaList(String nomeEntita) {
		
		super(nomeEntita);
		
		idEmailField = new ListGridField("idEmail");
		idEmailField.setHidden(true);				
		
		categoriaField = new ListGridField("categoria", I18NUtil.getMessages().ricevutepostainuscita_list_categoriaField_title());
		
		dataRicezioneField = new ListGridField("dataRicezione", I18NUtil.getMessages().ricevutepostainuscita_list_dataRicezioneField_title());
		dataRicezioneField.setType(ListGridFieldType.DATE);
		dataRicezioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		mittenteField = new ListGridField("mittente", I18NUtil.getMessages().ricevutepostainuscita_list_mittenteField_title());

		setFields(new ListGridField[] {
			idEmailField,
			categoriaField,				
			dataRicezioneField, 				
			mittenteField 
		});  
		
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 50;
	}
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {	
		
		Record lRecord = new Record();
		final String idEmail = record.getAttribute("idEmail");
		lRecord.setAttribute("idEmail", idEmail);
		
		if(!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						Record record = response.getData()[0];
						layout.getDetail().editRecord(record, recordNum);	
						layout.getDetail().getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					} 				
				}
			}, new DSRequest());
		
		} else {
			layout.changeDetail((GWTRestDataSource)getDataSource(), new DettaglioPostaElettronica("posta_elettronica"));
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						Record record = response.getData()[0];
						layout.getDetail().editRecord(record, recordNum);	
						layout.getDetail().getValuesManager().clearErrors(true);
						callback.execute(response, null, new DSRequest());
					} 				
				}
			}, new DSRequest());
		} 	
			
	}	
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {	
								
		Canvas lCanvasReturn = null;
		
		if (fieldName.equals("buttons")) {	
														
			ImgButton detailButton = buildDetailButton(record);  
			ImgButton lookupButton = buildLookupButton(record);	
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
			
			recordCanvas.addMember(detailButton);	
			
			if(layout.isLookup()) {
				if(!isRecordSelezionabileForLookup(record)) {
					lookupButton.disable();
				}
				recordCanvas.addMember(lookupButton);		// aggiungo il bottone SELEZIONA				
			}
			
			lCanvasReturn = recordCanvas;
					
		}	
		
		return lCanvasReturn;
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
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
