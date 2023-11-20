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

public class TopograficoList extends CustomList {
	
	private ListGridField idTopograficoField;
	private ListGridField codiceRapidoField;
	private ListGridField nomeField;
	private ListGridField descrizioneField;
	private ListGridField codiceOrigineField;
	private ListGridField flgCreatodameField;
	private ListGridField flgValidoField;
	private ListGridField recProtettoField;
	private ListGridField noteField;
	private ListGridField descUtenteInsField;
	private ListGridField dataInsField;
	private ListGridField descUtenteUltModField;
	private ListGridField dataUltModField;
	
	public TopograficoList(String nomeEntita) {
		
		super(nomeEntita);
		
		idTopograficoField 		= new ListGridField("idTopografico", 	I18NUtil.getMessages().topografico_list_idTopograficoField_title());
		idTopograficoField.setHidden(true);		
		
		codiceRapidoField 		= new ListGridField("codiceRapido", 	I18NUtil.getMessages().topografico_list_codiceRapidoField_title());
		
		nomeField 				= new ListGridField("nome", 			I18NUtil.getMessages().topografico_list_nomeField_title());
		
		descrizioneField 		= new ListGridField("descrizione", 		I18NUtil.getMessages().topografico_list_descrizioneField_title());
		
		codiceOrigineField 		= new ListGridField("codiceOrigine", 	I18NUtil.getMessages().topografico_list_codiceOrigineField_title());
		
		flgCreatodameField 		= new ListGridField("flgCreatodame", 	I18NUtil.getMessages().topografico_list_flgCreatodameField_title());		
		flgCreatodameField.setType(ListGridFieldType.ICON);
		flgCreatodameField.setWidth(30);
		flgCreatodameField.setIconWidth(16);
		flgCreatodameField.setIconHeight(16);
		Map<String, String> flgCreatoDaMeValueIcons = new HashMap<String, String>();		
		flgCreatoDaMeValueIcons.put("1", "ok.png");
		flgCreatoDaMeValueIcons.put("0", "blank.png");
		flgCreatoDaMeValueIcons.put("", "blank.png");
		flgCreatodameField.setValueIcons(flgCreatoDaMeValueIcons);		
		flgCreatodameField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgCreatodame"))) {
					return I18NUtil.getMessages().topografico_flgCreatodame_1_value();
				}				
				return null;
			}
		});	
	
		flgValidoField          = new ListGridField("flgValido", 		I18NUtil.getMessages().topografico_list_flgValidoField_title());
		flgValidoField.setType(ListGridFieldType.ICON);
		flgValidoField.setWidth(30);
		flgValidoField.setIconWidth(16);
		flgValidoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put("1", "ok.png");
		flgValidoValueIcons.put("0", "blank.png");
		flgValidoValueIcons.put("", "blank.png");
		flgValidoField.setValueIcons(flgValidoValueIcons);
		flgValidoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgValido"))) {
					return I18NUtil.getMessages().topografico_flgValido_1_value();
				}				
				return null;
			}
		});

		recProtettoField = new ListGridField("recProtetto", 		I18NUtil.getMessages().topografico_list_recProtettoField_title());
		recProtettoField.setType(ListGridFieldType.ICON);
		recProtettoField.setWidth(30);
		recProtettoField.setIconWidth(16);
		recProtettoField.setIconHeight(16);
		Map<String, String> recProtettoValueIcons = new HashMap<String, String>();		
		recProtettoValueIcons.put("1", "lock.png");
		recProtettoValueIcons.put("0", "blank.png");
		recProtettoValueIcons.put("", "blank.png");
		recProtettoField.setValueIcons(recProtettoValueIcons);
		recProtettoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("recProtetto"))) {
					return I18NUtil.getMessages().topografico_recProtetto_1_value();
				}				
				return null;
			}
		});
		
		noteField 				= new ListGridField("note", 			I18NUtil.getMessages().topografico_list_noteField_title());
		
		descUtenteInsField 		= new ListGridField("descUtenteIns", 	I18NUtil.getMessages().topografico_list_descUtenteInsField_title());
		
		dataInsField 			= new ListGridField("dataIns", 			I18NUtil.getMessages().topografico_list_dataInsField_title());
		dataInsField.setType(ListGridFieldType.DATE);
		dataInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		descUtenteUltModField 	= new ListGridField("descUtenteUltMod", I18NUtil.getMessages().topografico_list_descUtenteUltModField_title());
		
		dataUltModField 		= new ListGridField("dataUltMod", 		I18NUtil.getMessages().topografico_list_dataUltModField_title());
		dataUltModField.setType(ListGridFieldType.DATE);
		dataUltModField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		setFields(new ListGridField[] {
			    idTopograficoField,
				codiceRapidoField,				
				nomeField, 				
				descrizioneField,
				codiceOrigineField,
				flgCreatodameField,				
				flgValidoField,
				recProtettoField,
				noteField,
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
		
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		SC.ask(recProtetto ? "Sei sicuro di voler procedere alla cancellazione/annullamento del topografico?" : "Sei sicuro di voler procedere all''eliminazione fisica del topografico?", new BooleanCallback() {					
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
		return TopograficoLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return TopograficoLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return TopograficoLayout.isRecordAbilToMod(recProtetto);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equalsIgnoreCase("1");
		return TopograficoLayout.isRecordAbilToDel(flgValido, recProtetto);	
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
