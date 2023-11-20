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

public class GruppiSoggettiList extends CustomList {

	private ListGridField idGruppoField;			// ID gruppo
	private ListGridField codiceRapidoField;		// Codice rapido
	private ListGridField nomeField;				// Nome
	//	private ListGridField flgSoggettiInterniField;	// flag soggetti interni
	private ListGridField flgSoggettiGruppoField;	// Tipo (E: tutti esterni | I: tutti interni | M: misto | O: tutti in organigramma)	
	private ListGridField dtValiditaDaField;     	// Valido dal 
	private ListGridField dtValiditaAField;      	// Valido al	
	private ListGridField descUtenteInsField;   	// Creato da 
	private ListGridField dataInsField;				// Creato il 	
	private ListGridField descUtenteUltModField; 	// Ultimo agg. effettuato da
	private ListGridField dataUltModField;       	// Ultimo agg. effettuato il	
	private ListGridField flgValidoField;           // flag valido
	private ListGridField recProtettoField;      	// Rec. protetto	

	public GruppiSoggettiList(String nomeEntita) {

		super(nomeEntita);

		idGruppoField = new ListGridField("idGruppo", I18NUtil.getMessages().gruppisoggetti_list_idField_title());

		codiceRapidoField = new ListGridField("codiceRapido", I18NUtil.getMessages().gruppisoggetti_list_codiceRapidoField_title());

		nomeField = new ListGridField("nome", I18NUtil.getMessages().gruppisoggetti_list_nomeField_title());

		//		flgSoggettiInterniField = new ListGridField("flgSoggettiInterni", I18NUtil.getMessages().gruppisoggetti_list_flgSoggettiInterniField_title());
		//		flgSoggettiInterniField.setType(ListGridFieldType.ICON);
		//		flgSoggettiInterniField.setWidth(30);
		//		flgSoggettiInterniField.setIconWidth(16);
		//		flgSoggettiInterniField.setIconHeight(16);
		//		Map<String, String> flgSoggettiInterniValueIcons = new HashMap<String, String>();		
		//		flgSoggettiInterniValueIcons.put("1", "ok.png");
		//		flgSoggettiInterniValueIcons.put("0", "blank.png");
		//		flgSoggettiInterniValueIcons.put("", "blank.png");
		//		flgSoggettiInterniField.setValueIcons(flgSoggettiInterniValueIcons);
		//		flgSoggettiInterniField.setHoverCustomizer(new HoverCustomizer() {			
		//			@Override
		//			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
		//				
		//				if("1".equals(record.getAttribute("flgSoggettiInterni"))) {
		//					return I18NUtil.getMessages().gruppisoggetti_flgSoggettiInterni_1_value();
		//				}				
		//				return null;
		//			}
		//		});

		flgSoggettiGruppoField = new ListGridField("flgSoggettiGruppo", I18NUtil.getMessages().protocollazione_detail_tipoItem_title());
		flgSoggettiGruppoField.setType(ListGridFieldType.ICON);
		flgSoggettiGruppoField.setWidth(30);
		flgSoggettiGruppoField.setIconWidth(16);
		flgSoggettiGruppoField.setIconHeight(16);
		Map<String, String> flgSoggettiGruppoValueIcons = new HashMap<String, String>();		
		flgSoggettiGruppoValueIcons.put("E", "protocollazione/flagSoggettiGruppo/E.png");
		flgSoggettiGruppoValueIcons.put("I", "protocollazione/flagSoggettiGruppo/I.png");
		flgSoggettiGruppoValueIcons.put("M", "protocollazione/flagSoggettiGruppo/M.png");
		flgSoggettiGruppoValueIcons.put("O", "protocollazione/flagSoggettiGruppo/O.png"); 		
		flgSoggettiGruppoValueIcons.put("", "blank.png");
		flgSoggettiGruppoField.setValueIcons(flgSoggettiGruppoValueIcons);
		flgSoggettiGruppoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if(record.getAttribute("flgSoggettiGruppo") != null) {
					if("E".equals(record.getAttribute("flgSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_E_value();
					} else if("I".equals(record.getAttribute("flgSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_I_value();
					} else if("M".equals(record.getAttribute("flgSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_M_value();
					} else if("O".equals(record.getAttribute("flgSoggettiGruppo"))) {
						return I18NUtil.getMessages().protocollazione_detail_flagSoggettiGruppo_O_value();
					}					
				}
				return null;
			}
		});

		dtValiditaDaField = new ListGridField("dtValiditaDa", I18NUtil.getMessages().gruppisoggetti_list_dtValiditaDaField_title());
		dtValiditaDaField.setType(ListGridFieldType.DATE);
		dtValiditaDaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		dtValiditaAField = new ListGridField("dtValiditaA", I18NUtil.getMessages().gruppisoggetti_list_dtValiditaAField_title());
		dtValiditaAField.setType(ListGridFieldType.DATE);
		dtValiditaAField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);

		descUtenteInsField = new ListGridField("descUtenteIns", I18NUtil.getMessages().gruppisoggetti_list_descUtenteInsField_title());

		dataInsField = new ListGridField("dataIns", I18NUtil.getMessages().gruppisoggetti_list_dataInsField_title());
		dataInsField.setType(ListGridFieldType.DATE);
		dataInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		descUtenteUltModField = new ListGridField("descUtenteUltMod", I18NUtil.getMessages().gruppisoggetti_list_descUtenteUltModField_title());

		dataUltModField = new ListGridField("dataUltMod", I18NUtil.getMessages().gruppisoggetti_list_dataUltModField_title());		
		dataUltModField.setType(ListGridFieldType.DATE);
		dataUltModField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);

		flgValidoField = new ListGridField("flgValido", I18NUtil.getMessages().gruppisoggetti_list_flgValidoField_title());
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
					return I18NUtil.getMessages().gruppisoggetti_flgValido_1_value();
				}				
				return null;
			}
		});

		recProtettoField = new ListGridField("recProtetto", I18NUtil.getMessages().gruppisoggetti_list_recProtettoField_title());
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
					return I18NUtil.getMessages().gruppisoggetti_recProtetto_1_value();
				}				
				return null;
			}
		});

		setFields(new ListGridField[] { idGruppoField,
				codiceRapidoField,				
				nomeField, 	
				//										flgSoggettiInterniField,
				flgSoggettiGruppoField,
				dtValiditaDaField,
				dtValiditaAField,
				descUtenteInsField,
				dataInsField,
				descUtenteUltModField,
				dataUltModField,
				recProtettoField
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
		SC.ask(recProtetto ? I18NUtil.getMessages().gruppisoggetti_annullamentoLogicoAsk_message() : I18NUtil.getMessages().gruppisoggetti_eliminazioneFisicaAsk_message(), new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				
				if(value) {		
					removeData(record, new DSCallback() {																
						@Override
						public void execute(DSResponse response,
								Object rawData, DSRequest request) {
							
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterDelete_message(layout.getTipoEstremiRecord(record)), "", MessageType.INFO));
								//layout.hideDetail(true);																					
								layout.hideDetail();
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
		return GruppiSoggettiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return GruppiSoggettiLayout.isAbilToDel();
	}
		
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean recProtetto = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return GruppiSoggettiLayout.isRecordAbilToMod(recProtetto);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean recProtetto = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		final boolean flgValido = record.getAttribute("flgValido") != null   && record.getAttributeAsString("flgValido").equalsIgnoreCase("1");
		return GruppiSoggettiLayout.isRecordAbilToDel(flgValido, recProtetto);
	}	 
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/

}
