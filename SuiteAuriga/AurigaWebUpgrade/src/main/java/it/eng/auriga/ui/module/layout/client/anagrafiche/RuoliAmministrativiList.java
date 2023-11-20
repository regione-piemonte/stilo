/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.HashMap;
import java.util.Map;

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
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class RuoliAmministrativiList extends CustomList {
	
	private ListGridField idRuoloField;
	private ListGridField descrizioneRuoloField;
	private ListGridField flgValidoField;
	private ListGridField descUtenteInsField;
	private ListGridField dataInsField;
	private ListGridField descUtenteUltModField;
	private ListGridField dataUltModField;
	
	public RuoliAmministrativiList(String nomeEntita) {
		
		super(nomeEntita);
		
		// Hidden
		idRuoloField 		  = new ListGridField("idRuolo", I18NUtil.getMessages().ruoli_amministrativi_list_idRuoloField_title()); idRuoloField.setHidden(true);		
		
		// Visibili
		descrizioneRuoloField = new ListGridField("descrizioneRuolo",I18NUtil.getMessages().ruoli_amministrativi_list_descrizioneRuoloField_title());
		
		flgValidoField          = new ListGridField("flgValido", 		I18NUtil.getMessages().ruoli_amministrativi_list_flgValidoField_title());
		flgValidoField.setType(ListGridFieldType.ICON);
		flgValidoField.setWidth(30);
		flgValidoField.setIconWidth(16);
		flgValidoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put("1", "ok.png");
		flgValidoValueIcons.put("0", "blank.png");
		flgValidoValueIcons.put("",  "blank.png");
		flgValidoField.setValueIcons(flgValidoValueIcons);
		flgValidoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgValido"))) {
					return I18NUtil.getMessages().ruoli_amministrativi_flgValido_1_value();
				}				
				return null;
			}
		});
		descUtenteInsField 		= new ListGridField("descUtenteIns", 	I18NUtil.getMessages().ruoli_amministrativi_list_descUtenteInsField_title());
		dataInsField 			= new ListGridField("dataIns", 			I18NUtil.getMessages().ruoli_amministrativi_list_dataInsField_title()); dataInsField.setType(ListGridFieldType.DATE); dataInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		descUtenteUltModField 	= new ListGridField("descUtenteUltMod", I18NUtil.getMessages().ruoli_amministrativi_list_descUtenteUltModField_title());
		dataUltModField 		= new ListGridField("dataUltMod", 		I18NUtil.getMessages().ruoli_amministrativi_list_dataUltModField_title());dataUltModField.setType(ListGridFieldType.DATE);dataUltModField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		setFields(new ListGridField[] {
										// Hidden
			    						idRuoloField,
			 
			    						// Visibili
			    						descrizioneRuoloField,
			    						flgValidoField,
			    						descUtenteInsField,
			    						dataInsField,
			    						descUtenteUltModField,
			    						dataUltModField	
									  });  
		
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
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
			ImgButton deleteButton = buildDeleteButton(record);  			
			ImgButton lookupButton = buildLookupButton(record);						
			
			if(!isRecordAbilToMod(record)) {	
				modifyButton.disable();			
			}			
			
			if(!isRecordAbilToDel(record)) {	
				deleteButton.disable();			
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
			recordCanvas.addMember(deleteButton);			
			
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
		
		SC.ask("Sei sicuro di voler procedere all'eliminazione/annullamento del ruolo ? Se il ruolo è già stato utilizzato verrà annullato logicamente" , new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {				
				if(value) {		
					removeData(record, new DSCallback() {																
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							
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
		return RuoliAmministrativiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return RuoliAmministrativiLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return RuoliAmministrativiLayout.isRecordAbilToMod(recProtetto);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equalsIgnoreCase("1");
		return RuoliAmministrativiLayout.isRecordAbilToDel(flgValido, recProtetto);
		
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}