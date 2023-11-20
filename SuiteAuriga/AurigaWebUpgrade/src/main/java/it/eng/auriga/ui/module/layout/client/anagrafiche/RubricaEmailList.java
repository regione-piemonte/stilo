/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
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

public class RubricaEmailList extends CustomList {
	
	private ListGridField idVoceRubricaField;		
	private ListGridField tipoIndirizzoField;
	private ListGridField nomeField;            
	private ListGridField indirizzoEmailField;	
	private ListGridField tipoAccountField;      
	private ListGridField flgPECverificataField;
	private ListGridField flgPresenteInIPAField;
	private ListGridField codiceIPAField;
	private ListGridField codAmministrazioneField;
	private ListGridField codAooField;
	private ListGridField tipoSoggettoRifField;
	private ListGridField flgValidoField;           // flag valido
	private ListGridField utenteInsField;
	private ListGridField dataInsField;
	private ListGridField utenteUltModField;
	private ListGridField dataUltModField;
	private ListGridField idProvSoggRifField;
	private ListGridField idFruitoreCasellaField;
	
	public RubricaEmailList(String nomeEntita) {
		
		super(nomeEntita);
		
		// ID indirizzo
		idVoceRubricaField = new ListGridField("idVoceRubrica", I18NUtil.getMessages().rubricaemail_list_idField_title());
		idVoceRubricaField.setHidden(true);		
		
		
		// Tipo indirizzo ( S=account semplice, G=gruppi)
		tipoIndirizzoField = new ListGridField("tipoIndirizzo",I18NUtil.getMessages().rubricaemail_list_tipoIndirizzoField_title());
		tipoIndirizzoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		tipoIndirizzoField.setType(ListGridFieldType.ICON);
		tipoIndirizzoField.setWidth(30);
		tipoIndirizzoField.setIconWidth(16);
		tipoIndirizzoField.setIconHeight(16);
		Map<String, String> tipoIndirizzoValueIcons = new HashMap<String, String>();		
		tipoIndirizzoValueIcons.put("S", "anagrafiche/rubrica_email/tipoIndirizzo/S.png");
		tipoIndirizzoValueIcons.put("G", "anagrafiche/rubrica_email/tipoIndirizzo/G.png");
		tipoIndirizzoValueIcons.put("",  "blank.png");
		tipoIndirizzoField.setValueIcons(tipoIndirizzoValueIcons);	
		tipoIndirizzoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("S".equals(record.getAttribute("tipoIndirizzo"))) {
					return I18NUtil.getMessages().rubricaemail_tipoIndirizzo_S_Alt_value();
				}	
				else if("G".equals(record.getAttribute("tipoIndirizzo"))) {
					return I18NUtil.getMessages().rubricaemail_tipoIndirizzo_G_Alt_value();
				}
				else
					return null;
			}
		});	
		
		
		// Nome 
		nomeField = new ListGridField("nome", I18NUtil.getMessages().rubricaemail_list_nomeField_title());
		
		// Indirizzo email
		indirizzoEmailField = new ListGridField("indirizzoEmail", I18NUtil.getMessages().rubricaemail_list_indirizzoEmailField_title());
		
		// Tipo account ( C=casella PEC, O=casella ordinaria, null) 
		tipoAccountField = new ListGridField("tipoAccount", I18NUtil.getMessages().rubricaemail_list_tipoAccountField_title());
		tipoAccountField.setType(ListGridFieldType.ICON);
		tipoAccountField.setWidth(30);
		tipoAccountField.setIconWidth(16);
		tipoAccountField.setIconHeight(16);
		Map<String, String> tipoAccountValueIcons = new HashMap<String, String>();		
		tipoAccountValueIcons.put("C", "anagrafiche/rubrica_email/PEC.png");
		tipoAccountValueIcons.put("O", "anagrafiche/rubrica_email/PEO.png");
		tipoAccountValueIcons.put("",  "blank.png");
		tipoAccountField.setValueIcons(tipoAccountValueIcons);		
		tipoAccountField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("C".equals(record.getAttribute("tipoAccount"))) {
					return I18NUtil.getMessages().rubricaemail_tipoAccount_C_Alt_value();
				}	
				else if("O".equals(record.getAttribute("tipoAccount"))) {
					return I18NUtil.getMessages().rubricaemail_tipoAccount_O_Alt_value();
				}
				else
				   return null;
			}
		});	
		
		// Flag PEC verificata  ( 1 = si, 0/null = no)
        flgPECverificataField = new ListGridField("flgPECverificata", 	I18NUtil.getMessages().rubricaemail_list_flgPECverificataField_title());
        flgPECverificataField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);		
        flgPECverificataField.setType(ListGridFieldType.ICON);
        flgPECverificataField.setWidth(30);
        flgPECverificataField.setIconWidth(16);
        flgPECverificataField.setIconHeight(16);
		Map<String, String> flgPECverificataValueIcons = new HashMap<String, String>();		
		flgPECverificataValueIcons.put("true",  "anagrafiche/rubrica_email/pec_verificata.png");
		flgPECverificataValueIcons.put("false", "blank.png");
		flgPECverificataValueIcons.put("",      "blank.png");
		flgPECverificataField.setValueIcons(flgPECverificataValueIcons);		
		flgPECverificataField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("true".equals(record.getAttribute("flgPECverificata"))) {
					if("C".equals(record.getAttribute("tipoAccount"))) {
						return I18NUtil.getMessages().rubricaemail_flgPECverificata_1_Alt_value();						
					} else if("O".equals(record.getAttribute("tipoAccount"))) {
						return I18NUtil.getMessages().rubricaemail_flgPEOverificata_1_Alt_value();
					}					
				}
				return null;
			}
		});	
		
		
		// Flag email presente in IPA ( 1=si, 0/null=no)
		flgPresenteInIPAField = new ListGridField("flgPresenteInIPA", I18NUtil.getMessages().rubricaemail_list_flgPresenteInIPAField_title());
		flgPresenteInIPAField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);		
		flgPresenteInIPAField.setType(ListGridFieldType.ICON);
		flgPresenteInIPAField.setWidth(30);
		flgPresenteInIPAField.setIconWidth(16);
		flgPresenteInIPAField.setIconHeight(16);
		Map<String, String> flgPresenteInIPAFieldValueIcons = new HashMap<String, String>();		
		flgPresenteInIPAFieldValueIcons.put("true",  "ipa.png");
		flgPresenteInIPAFieldValueIcons.put("false", "blank.png");
		flgPresenteInIPAFieldValueIcons.put("",      "blank.png");
		flgPresenteInIPAField.setValueIcons(flgPresenteInIPAFieldValueIcons);		
		flgPresenteInIPAField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("true".equals(record.getAttribute("flgPresenteInIPA"))) {
					return I18NUtil.getMessages().rubricaemail_flgPresenteInIPA_1_Alt_value();
				}	
				else
				    return null;
			}
		});	
		
	
		// Codice Amministrazione
		codAmministrazioneField    = new ListGridField("codAmministrazione", I18NUtil.getMessages().rubricaemail_list_codAmministrazioneField_title());
		codAmministrazioneField.setHidden(true);		
		
		
		// Codice Aoo
		codAooField    = new ListGridField("codAoo", I18NUtil.getMessages().rubricaemail_list_codAooField_title());
		codAooField.setHidden(true);		

		// Codice IPA ( cod_amm + cod_aoo )
		codiceIPAField = new ListGridField("codiceIPA", I18NUtil.getMessages().rubricaemail_list_codiceIPAField_title());
		
		
		// Tipo Soggetto di riferimento ( UO=uo/ufficio interno, UT=utente di sistema, C=casella gestita dal sistema, AOOI=aoo interna, AOOE=aoo esterna, A=altro soggetto) 
		tipoSoggettoRifField = new ListGridField("tipoSoggettoRif", I18NUtil.getMessages().rubricaemail_list_tipoSoggettoRifField_title());
		tipoSoggettoRifField.setType(ListGridFieldType.ICON);
		tipoSoggettoRifField.setWidth(60);
		tipoSoggettoRifField.setIconWidth(32);
		tipoSoggettoRifField.setIconHeight(32);
		Map<String, String> tipoSoggettoRifValueIcons = new HashMap<String, String>();		
		tipoSoggettoRifValueIcons.put("UO",   "anagrafiche/rubrica_email/tipoSoggettoRif/UO.png");
		tipoSoggettoRifValueIcons.put("UT",   "anagrafiche/rubrica_email/tipoSoggettoRif/UT.png");
		tipoSoggettoRifValueIcons.put("C",    "anagrafiche/rubrica_email/tipoSoggettoRif/C.png");
		tipoSoggettoRifValueIcons.put("AOOI", "anagrafiche/rubrica_email/tipoSoggettoRif/AOOI.png");
		tipoSoggettoRifValueIcons.put("AOOE", "anagrafiche/rubrica_email/tipoSoggettoRif/AOOE.png");
		tipoSoggettoRifValueIcons.put("A",    "anagrafiche/rubrica_email/tipoSoggettoRif/A.png");		
		tipoSoggettoRifValueIcons.put("",     "blank.png");
		tipoSoggettoRifField.setValueIcons(tipoSoggettoRifValueIcons);		
		tipoSoggettoRifField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("UO".equals(record.getAttribute("tipoSoggettoRif"))) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_UO_Alt_value();
				}	
				else if("UT".equals(record.getAttribute("tipoSoggettoRif"))) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_UT_Alt_value();
				}
				else if("C".equals(record.getAttribute("tipoSoggettoRif"))) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_C_Alt_value();
				}
				else if("AOOI".equals(record.getAttribute("tipoSoggettoRif"))) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_AOOI_Alt_value();
				}
				else if("AOOE".equals(record.getAttribute("tipoSoggettoRif"))) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_AOOE_Alt_value();
				}
				else if("A".equals(record.getAttribute("tipoSoggettoRif"))) {
					return I18NUtil.getMessages().rubricaemail_tipoSoggettoRif_A_Alt_value();
				}
				return null;
			}
		});	
				
		// Flag validita' (1=valido,0=annullato)
		flgValidoField = new ListGridField("flgValido", I18NUtil.getMessages().rubricaemail_list_flgValidoField_title());
		flgValidoField.setType(ListGridFieldType.ICON);
		flgValidoField.setWidth(30);
		flgValidoField.setIconWidth(16);
		flgValidoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put("true", "ok.png");
		flgValidoValueIcons.put("false", "ko.png");
		flgValidoValueIcons.put("", "blank.png");
		flgValidoField.setValueIcons(flgValidoValueIcons);
		flgValidoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("true".equals(record.getAttribute("flgValido"))) {
					return I18NUtil.getMessages().rubricaemail_flgValido_1_value();
				} else if ("false".equals(record.getAttribute("flgValido"))){
					return I18NUtil.getMessages().rubricaemail_flgValido_0_value();
				} else return null;				
			}
		});
		
		// Utente inserimento
		utenteInsField = new ListGridField("utenteIns", I18NUtil.getMessages().rubricaemail_list_utenteInsField_title());
				
		// Data inserimento
		dataInsField = new ListGridField("dataIns", I18NUtil.getMessages().rubricaemail_list_dataInsField_title());
		dataInsField.setType(ListGridFieldType.DATE);
		dataInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		// Utente ultima modifica
		utenteUltModField = new ListGridField("utenteUltMod", I18NUtil.getMessages().rubricaemail_list_utenteUltModField_title());
				
		// Data ultima modifica
		dataUltModField = new ListGridField("dataUltMod", I18NUtil.getMessages().rubricaemail_list_dataUltModField_title());
		dataUltModField.setType(ListGridFieldType.DATE);
		dataUltModField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);		
		
		// id prov. sogg rif.
		idProvSoggRifField = new ListGridField("idProvSoggRif", I18NUtil.getMessages().rubricaemail_list_idProvSoggRifField_title());
		idProvSoggRifField.setHidden(true);
		
		// id fruitore
		idFruitoreCasellaField = new ListGridField("idFruitoreCasella", I18NUtil.getMessages().rubricaemail_list_idFruitoreCasellaField_title());
		idFruitoreCasellaField.setHidden(true);			
		
		setFields(new ListGridField[] { tipoIndirizzoField,
										nomeField,
				                        indirizzoEmailField,
				                        tipoAccountField,
				                        flgPECverificataField,
				                        flgPresenteInIPAField,
				                        codiceIPAField,
				                        tipoSoggettoRifField,
				                        flgValidoField,
				                        utenteInsField,
				                        dataInsField,
				                        utenteUltModField,
				                        dataUltModField });  		
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
		
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		if (record.getAttribute("tipoIndirizzo").equals("S")) {
			layout.changeDetail((GWTRestDataSource)getDataSource(), new RubricaEmailDetail(nomeEntita));
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
		} else if (record.getAttribute("tipoIndirizzo").equals("G")) {
			layout.changeDetail((GWTRestDataSource)getDataSource(), new RubricaGruppoEmailDetail(nomeEntita));
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
		
		SC.ask(I18NUtil.getMessages().soggetti_annullamentoLogicoAsk_message(), new BooleanCallback() {					
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
	
	@Override  
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return getLayout().getFinalita() == null || !"SEL_SOLO_SINGOLI".equals(getLayout().getFinalita()) || !"G".equals(record.getAttributeAsString("tipoIndirizzo"));
	}
	
	@Override  
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {		
		if(layout.isLookup() && record != null) {			
			boolean flgSelezionabile = getLayout().getFinalita() == null || !"SEL_SOLO_SINGOLI".equals(getLayout().getFinalita()) || !"G".equals(record.getAttributeAsString("tipoIndirizzo"));
			if(flgSelezionabile) {
				return "font-weight:bold; color:#1D66B2";				
			} else {				
				return "font-weight:normal; color:gray";
			}			        
		} 
		return super.getCellCSSText(record, rowNum, colNum);
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
		return RubricaEmailLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return RubricaEmailLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		boolean idProvSoggRifValorizzato = record.getAttribute("idProvSoggRif") != null && !"".equals(record.getAttribute("idProvSoggRif"));
		return RubricaEmailLayout.isRecordAbilToMod(idProvSoggRifValorizzato);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		boolean idProvSoggRifValorizzato = record.getAttribute("idProvSoggRif") != null && !"".equals(record.getAttribute("idProvSoggRif"));
		boolean flgValido = record.getAttribute("flgValido") != null   && record.getAttributeAsString("flgValido").equalsIgnoreCase("true");
		return RubricaEmailLayout.isRecordAbilToDel(flgValido, idProvSoggRifValorizzato);	
	}
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
