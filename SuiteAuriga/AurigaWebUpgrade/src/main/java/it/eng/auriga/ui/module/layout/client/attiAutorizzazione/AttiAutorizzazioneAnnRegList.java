/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class AttiAutorizzazioneAnnRegList extends CustomList {

	private ListGridField idAttoField;
	private ListGridField nroBozzaField;
	private ListGridField nroAttoField;
	private ListGridField tsRegBozzaField;
	private ListGridField oggettoField;
	private ListGridField tsRegAttoField;
	
	private ControlListGridField chiudiAttoButtonField;
	
	public AttiAutorizzazioneAnnRegList(String nomeEntita) {
		
		super(nomeEntita);			
		
		// hidden
		idAttoField= new ListGridField("idAtto");   idAttoField.setHidden(true);   idAttoField.setCanHide(false);	
		
		// visibili
		nroBozzaField     = new ListGridField("nroBozza" , "Bozza N°");
		nroAttoField      = new ListGridField("nroAtto"  , "Atto N°");
		tsRegBozzaField   = new ListGridField("tsRegBozza", "Creato il");tsRegBozzaField.setType(ListGridFieldType.DATE); tsRegBozzaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		oggettoField      = new ListGridField("oggetto", "Oggetto");
		tsRegAttoField    = new ListGridField("tsRegAtto", "Chiuso il");tsRegAttoField.setType(ListGridFieldType.DATE); tsRegAttoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		chiudiAttoButtonField = new ControlListGridField("chiudiAtto");   
		chiudiAttoButtonField.setAttribute("custom", true);			
		chiudiAttoButtonField.setShowHover(true);			
		chiudiAttoButtonField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				boolean isAttoChiuso = record.getAttribute("flgAttoChiuso") != null && "1".equals(record.getAttribute("flgAttoChiuso"));
				if(!isAttoChiuso) {
					return buildImgButtonHtml("attiAutorizzazione/chiudiAtto.png");												
				} 				
				return null;					
			}
		});
		chiudiAttoButtonField.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				boolean isAttoChiuso = record.getAttribute("flgAttoChiuso") != null && "1".equals(record.getAttribute("flgAttoChiuso"));
				if(!isAttoChiuso) {
					return "Chiudi atto";											
				} 				
				return null;
			}
		});	
		chiudiAttoButtonField.addRecordClickHandler(new RecordClickHandler() {				
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				final Record record = event.getRecord();
				boolean isAttoChiuso = record.getAttribute("flgAttoChiuso") != null && "1".equals(record.getAttribute("flgAttoChiuso"));
				if(!isAttoChiuso) {
					SC.ask("Sei sicuro di voler chiudere l'atto di autorizzazione all'annullamento?", new BooleanCallback() {					
						@Override
						public void execute(Boolean value) {
							if(value) {											
								getDataSource().performCustomOperation("chiudiAtto", record, new DSCallback() {
									@Override
									public void execute(DSResponse response,
											Object rawData, DSRequest request) {
										if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
											Layout.addMessage(new MessageBean("L'atto è stato chiuso", "", MessageType.INFO));	
											layout.doSearch();												
										}	
									}							
								}, new DSRequest());													
							}
						}
					});    
				}
			}
		});			
		
		setFields(new ListGridField[] {
				//  hidden
				idAttoField,
				// visibili
				nroBozzaField,
				nroAttoField,
				tsRegBozzaField,
				oggettoField,
				tsRegAttoField,
				chiudiAttoButtonField
		});  		
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 75;
	}
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {							
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
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
	@Override  
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {  
		
		Canvas lCanvasReturn = null;
		if (fieldName.equals("buttons")) {	
						
			ImgButton detailButton = buildDetailButton(record);  
			ImgButton modifyButton = buildModifyButton(record);
			ImgButton lookupButton = buildLookupButton(record);
									
			if(!isRecordAbilToView(record)) {	
				detailButton.disable();								
			}			
					
			if(!isRecordAbilToMod(record)) {	
				modifyButton.disable();			
			}			
			
			// creo la colonna BUTTON
			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);   
			recordCanvas.setWidth(getButtonsFieldWidth());
			recordCanvas.setAlign(Alignment.RIGHT);
			recordCanvas.setLayoutRightMargin(3);   
			recordCanvas.setMembersMargin(7);
			
			recordCanvas.addMember(detailButton);			
			recordCanvas.addMember(modifyButton);
									
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
	
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		SC.ask(I18NUtil.getMessages().deleteButtonAsk_message(), new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				if(value) {							
					removeData(record, new DSCallback() {								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
								layout.hideDetail(true);
							} else {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(layout.getTipoEstremiRecord(record)), "", MessageType.ERROR));										
							}																																	
						}
					});													
				}
			}
		});              
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
	protected boolean showModifyButtonField() {
		return true;
	}

    @Override
    protected boolean isRecordAbilToMod(ListGridRecord record) {
    	boolean isAttoChiuso = record.getAttribute("flgAttoChiuso") != null && "1".equals(record.getAttribute("flgAttoChiuso"));
        return AttiAutorizzazioneAnnRegLayout.isRecordAbilToMod(isAttoChiuso);
    }
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}