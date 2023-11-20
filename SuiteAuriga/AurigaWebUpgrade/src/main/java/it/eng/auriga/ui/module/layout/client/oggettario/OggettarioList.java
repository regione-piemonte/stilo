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
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressEvent;
import com.smartgwt.client.widgets.grid.events.BodyKeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class OggettarioList extends CustomList {
	
	
	private ListGridField idModelloField;
	private ListGridField nomeField;	
	private ListGridField oggettoField;
	private ListGridField codOrigineField;
	private ListGridField flgCreatoDaField;
	private ListGridField flgTipoModelloField;
	private ListGridField flgValidoField;
	private ListGridField flgDiSistemaField;
	private ListGridField flgXRegInEntrataField;
	private ListGridField flgXRegInUscitaField;
	private ListGridField flgXRegInterneField;
	private ListGridField noteField;	
	private ListGridField uteInsField;		
	private ListGridField tsInsField;		
	private ListGridField uteLastUpdField;	
	private ListGridField tsLastUpdField;
	private ListGridField denominazioneUoField;
	private ListGridField numeroLivelliField;
	private ListGridField flgVisibileAlleSottoUoField;
	private ListGridField flgModificabileDalleSottoUoField;


	public OggettarioList(String nomeEntita) {
		
		super(nomeEntita);
	
		idModelloField = new ListGridField("idModello", I18NUtil.getMessages().oggettario_list_idModelloField_title());
		idModelloField.setHidden(true);		
				
		nomeField = new ListGridField("nome", I18NUtil.getMessages().oggettario_list_nomeField_title());
		
		oggettoField = new ListGridField("oggetto", I18NUtil.getMessages().oggettario_list_oggettoField_title());
		
		codOrigineField = new ListGridField("codOrigine", I18NUtil.getMessages().oggettario_list_codOrigineField_title());
		
		flgCreatoDaField = new ListGridField("flgCreatoDa", I18NUtil.getMessages().oggettario_list_flgCreatoDaField_title());	
		flgCreatoDaField.setType(ListGridFieldType.ICON);
		flgCreatoDaField.setWidth(30);
		flgCreatoDaField.setIconWidth(16);
		flgCreatoDaField.setIconHeight(16);
		Map<String, String> creatoDaValueIcons = new HashMap<String, String>();		
		creatoDaValueIcons.put("true", "ok.png");
		creatoDaValueIcons.put("false", "blank.png");
		creatoDaValueIcons.put("", "blank.png");
		flgCreatoDaField.setValueIcons(creatoDaValueIcons);		
		flgCreatoDaField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgCreatoDaMe"))) {
					return I18NUtil.getMessages().oggettario_flgCreatoDaMe_true_value();
				}				
				return null;
			}
		});	
		
		flgTipoModelloField = new ListGridField("flgTipoModello", I18NUtil.getMessages().oggettario_list_flgTipoModelloField_title());	
		flgTipoModelloField.setType(ListGridFieldType.ICON);
		flgTipoModelloField.setWidth(30);
		flgTipoModelloField.setIconWidth(16);
		flgTipoModelloField.setIconHeight(16);
		Map<String, String> tipoModelloValueIcons = new HashMap<String, String>();		
		tipoModelloValueIcons.put("PB", "modelli/modello_pb.png");
		tipoModelloValueIcons.put("UO", "modelli/modello_uo.png");
		tipoModelloValueIcons.put("PR", "modelli/modello_pr.png");
		flgTipoModelloField.setValueIcons(tipoModelloValueIcons);		
		flgTipoModelloField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("PB".equals(record.getAttribute("flgTipoModello"))) {
					return "pubblico";
				} else if("UO".equals(record.getAttribute("flgTipoModello"))) {
					return "valido per una UO";
				} else if("PR".equals(record.getAttribute("flgTipoModello"))) {
					return "privato di un utente";
				}				
				return null;
			}
		});		
		
		flgValidoField = new ListGridField("flgValido", I18NUtil.getMessages().oggettario_list_flgValidoField_title());	
		flgValidoField.setType(ListGridFieldType.ICON);
		flgValidoField.setWidth(30);
		flgValidoField.setIconWidth(16);
		flgValidoField.setIconHeight(16);
		Map<String, String> flgValidoValueIcons = new HashMap<String, String>();		
		flgValidoValueIcons.put("true", "ok.png");
		flgValidoValueIcons.put("false", "blank.png");
		flgValidoValueIcons.put("", "blank.png");
		flgValidoField.setValueIcons(flgValidoValueIcons);		
		flgValidoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgValido"))) {
					return I18NUtil.getMessages().oggettario_flgValido_true_value();
				}				
				return null;
			}
		});	

		flgDiSistemaField = new ListGridField("flgDiSistema", I18NUtil.getMessages().oggettario_list_flgDiSistemaField_title());	
		flgDiSistemaField.setType(ListGridFieldType.ICON);
		flgDiSistemaField.setWidth(30);
		flgDiSistemaField.setIconWidth(16);
		flgDiSistemaField.setIconHeight(16);
		Map<String, String> flgDiSistemaValueIcons = new HashMap<String, String>();		
		flgDiSistemaValueIcons.put("true", "lock.png");
		flgDiSistemaValueIcons.put("false", "blank.png");
		flgDiSistemaValueIcons.put("", "blank.png");
		flgDiSistemaField.setValueIcons(flgDiSistemaValueIcons);
		flgDiSistemaField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgDiSistema"))) {
					return I18NUtil.getMessages().oggettario_flgDiSistema_true_value();
				}				
				return null;
			}
		});
		
		flgXRegInEntrataField = new ListGridField("flgXRegInEntrata", I18NUtil.getMessages().oggettario_list_flgXRegInEntrataField_title());	
		flgXRegInEntrataField.setType(ListGridFieldType.ICON);
		flgXRegInEntrataField.setWidth(30);
		flgXRegInEntrataField.setIconWidth(16);
		flgXRegInEntrataField.setIconHeight(16);
		Map<String, String> flgXRegInEntrataValueIcons = new HashMap<String, String>();		
		flgXRegInEntrataValueIcons.put("true", "ok.png");
		flgXRegInEntrataValueIcons.put("false", "blank.png");
		flgXRegInEntrataValueIcons.put("", "blank.png");
		flgXRegInEntrataField.setValueIcons(flgXRegInEntrataValueIcons);		
		flgXRegInEntrataField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgXRegInEntrata"))) {
					return I18NUtil.getMessages().oggettario_flgXRegInEntrata_true_value();
				}				
				return null;
			}
		});	
		
		flgXRegInUscitaField = new ListGridField("flgXRegInUscita", I18NUtil.getMessages().oggettario_list_flgXRegInUscitaField_title());	
		flgXRegInUscitaField.setType(ListGridFieldType.ICON);
		flgXRegInUscitaField.setWidth(30);
		flgXRegInUscitaField.setIconWidth(16);
		flgXRegInUscitaField.setIconHeight(16);
		Map<String, String> flgXRegInUscitaValueIcons = new HashMap<String, String>();		
		flgXRegInUscitaValueIcons.put("true", "ok.png");
		flgXRegInUscitaValueIcons.put("false", "blank.png");
		flgXRegInUscitaValueIcons.put("", "blank.png");
		flgXRegInUscitaField.setValueIcons(flgXRegInUscitaValueIcons);		
		flgXRegInUscitaField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgXRegInUscita"))) {
					return I18NUtil.getMessages().oggettario_flgXRegInUscita_true_value();
				}				
				return null;
			}
		});
		
		flgXRegInterneField = new ListGridField("flgXRegInterne", I18NUtil.getMessages().oggettario_list_flgXRegInterneField_title());	
		flgXRegInterneField.setType(ListGridFieldType.ICON);
		flgXRegInterneField.setWidth(30);
		flgXRegInterneField.setIconWidth(16);
		flgXRegInterneField.setIconHeight(16);
		Map<String, String> flgXRegInterneValueIcons = new HashMap<String, String>();		
		flgXRegInterneValueIcons.put("true", "ok.png");
		flgXRegInterneValueIcons.put("false", "blank.png");
		flgXRegInterneValueIcons.put("", "blank.png");
		flgXRegInterneField.setValueIcons(flgXRegInterneValueIcons);		
		flgXRegInterneField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgXRegInterne"))) {
					return I18NUtil.getMessages().oggettario_flgXRegInterne_true_value();
				}				
				return null;
			}
		});	
		
		flgVisibileAlleSottoUoField = new ListGridField("flgVisibileDaSottoUo", I18NUtil.getMessages().oggettario_list_flgVisibileAlleSottoUoField_title());	
		flgVisibileAlleSottoUoField.setType(ListGridFieldType.ICON);
		flgVisibileAlleSottoUoField.setWidth(30);
		flgVisibileAlleSottoUoField.setIconWidth(16);
		flgVisibileAlleSottoUoField.setIconHeight(16);
		Map<String, String> flgVisibileAlleSottoUoValueIcons = new HashMap<String, String>();		
		flgVisibileAlleSottoUoValueIcons.put("true", "ok.png");
		flgVisibileAlleSottoUoValueIcons.put("false", "blank.png");
		flgVisibileAlleSottoUoValueIcons.put("", "blank.png");
		flgVisibileAlleSottoUoField.setValueIcons(flgVisibileAlleSottoUoValueIcons);		
		flgVisibileAlleSottoUoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgVisibileDaSottoUo"))) {
					return I18NUtil.getMessages().oggettario_flgVisibileAlleSottoUo_true_value();
				}				
				return null;
			}
		});	
		
		flgModificabileDalleSottoUoField = new ListGridField("flgModificabileDaSottoUo", I18NUtil.getMessages().oggettario_list_flgModificabileDalleSottoUoField_title());	
		flgModificabileDalleSottoUoField.setType(ListGridFieldType.ICON);
		flgModificabileDalleSottoUoField.setWidth(30);
		flgModificabileDalleSottoUoField.setIconWidth(16);
		flgModificabileDalleSottoUoField.setIconHeight(16);
		Map<String, String> flgModificabileDalleSottoUoValueIcons = new HashMap<String, String>();		
		flgModificabileDalleSottoUoValueIcons.put("true", "ok.png");
		flgModificabileDalleSottoUoValueIcons.put("false", "blank.png");
		flgModificabileDalleSottoUoValueIcons.put("", "blank.png");
		flgModificabileDalleSottoUoField.setValueIcons(flgModificabileDalleSottoUoValueIcons);		
		flgModificabileDalleSottoUoField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {

				if("true".equals(record.getAttribute("flgModificabileDaSottoUo"))) {
					return I18NUtil.getMessages().oggettario_flgModificabileDalleSottoUo_true_value();
				}				
				return null;
			}
		});	
		
		noteField = new ListGridField("note", I18NUtil.getMessages().oggettario_list_noteField_title());
		
		uteInsField = new ListGridField("uteIns", I18NUtil.getMessages().oggettario_list_uteInsField_title());	
		
		tsInsField = new ListGridField("tsIns", I18NUtil.getMessages().oggettario_list_tsInsField_title());	
		tsInsField.setType(ListGridFieldType.DATE);
		tsInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		uteLastUpdField = new ListGridField("uteLastUpd", I18NUtil.getMessages().oggettario_list_uteLastUpdField_title());	
		
		tsLastUpdField = new ListGridField("tsLastUpd", I18NUtil.getMessages().oggettario_list_tsLastUpdField_title());	
		tsLastUpdField.setType(ListGridFieldType.DATE);
		tsLastUpdField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		denominazioneUoField = new ListGridField("denominazioneUo", I18NUtil.getMessages().oggettario_list_denominazioneUoModello_title());	
		
		numeroLivelliField = new ListGridField("numeroLivelli", I18NUtil.getMessages().oggettario_list_numeroLivelliField_title());	
		
		setFields(new ListGridField[] {
				
				idModelloField,
				nomeField,
				oggettoField,
				codOrigineField,
				flgCreatoDaField,
				flgTipoModelloField,
				flgValidoField,
				flgDiSistemaField,
				flgXRegInEntrataField,
				flgXRegInUscitaField,
				flgXRegInterneField,
				flgVisibileAlleSottoUoField,
				flgModificabileDalleSottoUoField,
				noteField,	
				uteInsField,		
				tsInsField,	
				uteLastUpdField,	
				tsLastUpdField,
				denominazioneUoField,
				numeroLivelliField
		});  
		
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			setArrowKeyAction("focus");
			
			addBodyKeyPressHandler(new BodyKeyPressHandler() {
	            
	            @Override
	            public void onBodyKeyPress(BodyKeyPressEvent event) {
	                if (EventHandler.getKey().equalsIgnoreCase("Enter") == true) {
						Integer focusRow2 = getFocusRow();
						ListGridRecord record = getRecord(focusRow2);
	//					manageDetailButtonClick(record);
						if(isRecordSelezionabileForLookup(record)) {
							manageLookupButtonClick(record);
						}
	//                    System.out.println("ENTER PRESSED !!!!" + listGrid.getSelectedRecord());
	                }
	            }
	        });		
		} 
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

		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsBoolean("flgDiSistema");
		SC.ask(flgDiSistema ? I18NUtil.getMessages().oggettario_annullamentoLogicoAsk_message() : I18NUtil.getMessages().oggettario_eliminazioneFisicaAsk_message(), new BooleanCallback() {					
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
	}
	
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}
    
	@Override
	protected boolean showModifyButtonField() {
		return true;
	}
    
    @Override
	protected boolean showDeleteButtonField() {
		return true;
	}
    
    @Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
    	final boolean flgCreatoDaMe = record.getAttribute("flgCreatoDa") != null && record.getAttributeAsBoolean("flgCreatoDa");
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsBoolean("flgDiSistema");
		final boolean flgModificabile  = record.getAttribute("flgModificabile") != null && record.getAttributeAsBoolean("flgModificabile");
		return OggettarioLayout.isRecordAbilToMod(flgModificabile, flgCreatoDaMe, flgDiSistema);
	}
    
    @Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
    	final boolean flgCreatoDaMe = record.getAttribute("flgCreatoDa") != null && record.getAttributeAsBoolean("flgCreatoDa");
		final boolean flgDiSistema = record.getAttribute("flgDiSistema") != null && record.getAttributeAsBoolean("flgDiSistema");
		final boolean flgCancellabile  = record.getAttribute("flgCancellabile") != null && record.getAttributeAsBoolean("flgCancellabile");
		return OggettarioLayout.isRecordAbilToDel(flgCancellabile, flgCreatoDaMe, flgDiSistema);
	}
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/	
}