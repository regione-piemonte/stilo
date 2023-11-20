/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class RegoleProtocollazioneAutomaticaCaselleList extends CustomList {

	private ListGridField idRegolaField;
	private ListGridField nomeRegolaField;
	private ListGridField dataRegDaField;
	private ListGridField dataRegAField;
	private ListGridField descrizioneRegolaField;
	private ListGridField statoRegolaField;
	private ListGridField flgValidoField;
	private ListGridField descUtenteInsField;
	private ListGridField dataInsField;
	private ListGridField descUtenteUltModField;
	private ListGridField dataUltModField;
	
	public RegoleProtocollazioneAutomaticaCaselleList(String nomeEntita) {
		
		super(nomeEntita);
		
		// Hidden
		idRegolaField = new ListGridField("idRegola"); idRegolaField.setHidden(true); idRegolaField.setCanHide(false);
		
		// Visibili
		nomeRegolaField        = new ListGridField("nomeRegola", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_nomeRegolaItem_title());
		descrizioneRegolaField = new ListGridField("descrizioneRegola", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_descrizioneRegolaItem_title());
		statoRegolaField       = new ListGridField("statoRegola", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_statoRegolaItem_title());
		dataRegDaField         = new ListGridField("vldRegolaDa", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataAttivazioneItem_title()); dataRegDaField.setType(ListGridFieldType.DATE); dataRegDaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataRegAField          = new ListGridField("vldRegolaA", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataCessazioneItem_title()); dataRegAField.setType(ListGridFieldType.DATE); dataRegAField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		descUtenteInsField 	   = new ListGridField("descUtenteIns", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_descUtenteInsItem_title());
		dataInsField 		   = new ListGridField("dataIns", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataInsItem_title()); dataInsField.setType(ListGridFieldType.DATE); dataInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		descUtenteUltModField  = new ListGridField("descUtenteUltMod", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_descUtenteUltModItem_title());
		dataUltModField 	   = new ListGridField("dataUltMod", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_dataUltModItem_title());dataUltModField.setType(ListGridFieldType.DATE);dataUltModField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		flgValidoField = new ListGridField("flgValido", I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgValidoItem_title());
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
					return I18NUtil.getMessages().regole_protocollazione_automatica_caselle_pec_peo_flgValido_1_value();
				}				
				return null;
			}
		});
		flgValidoField.setHidden(true); flgValidoField.setCanHide(false);
		
		setFields(new ListGridField[] {
				// Hidden
				idRegolaField,
				
				// Visibili
				nomeRegolaField,
				descrizioneRegolaField,
				statoRegolaField,
				dataRegDaField,
				dataRegAField,
				descUtenteInsField,
				dataInsField,
				descUtenteUltModField,
				dataUltModField,
				flgValidoField
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
		MotivazioneCancellazioneRegolaProtAutomCasellePopup motivazioneCancellazioneRegolaProtAutomCasellePopup = new MotivazioneCancellazioneRegolaProtAutomCasellePopup("Motivo cancellazione") {
			
			@Override
			public void onClickOkButton(Record object, final DSCallback callback) {
				record.setAttribute("motivoCancellazione", object.getAttribute("motivoCancellazione"));	
				removeData(record, new DSCallback() {
					
					@Override
					public void execute(final DSResponse response,Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
							layout.hideDetail(true);
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().deleteError_message(""), "", MessageType.ERROR));										
						}	
						callback.execute(response, null, new DSRequest());			
					}
				});	
			}
		};
		motivazioneCancellazioneRegolaProtAutomCasellePopup.show();
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
		return RegoleProtocollazioneAutomaticaCaselleLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return RegoleProtocollazioneAutomaticaCaselleLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return RegoleProtocollazioneAutomaticaCaselleLayout.isRecordAbilToMod(recProtetto);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equalsIgnoreCase("1");
		return RegoleProtocollazioneAutomaticaCaselleLayout.isRecordAbilToDel(flgValido, recProtetto);
		
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/	
}
