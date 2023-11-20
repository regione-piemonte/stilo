/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class TipiDocList extends CustomList {
	
	private ListGridField idTipoDocField;
	private ListGridField codTipoDocField;
	private ListGridField nomeTipoDocField;
	private ListGridField descrizioneTipoDocField;
	private ListGridField flgDiSistemaField;
	private ListGridField tsInsField;	
	private ListGridField uteInsField;	
	private ListGridField tsLastUpdField;
	private ListGridField uteLastUpdField;	
	private ListGridField flgAnnField;
	private ListGridField flgSelXFinalitaField;
	//private ListGridField scoreField;

	public TipiDocList(String nomeEntita) {
		
		super(nomeEntita);
			
		// Colonne visibili
		codTipoDocField             = new ListGridField("codTipoDoc",    		I18NUtil.getMessages().tipi_doc_list_codTipoDocField_title());             
		nomeTipoDocField      		= new ListGridField("nomeTipoDoc",    		I18NUtil.getMessages().tipi_doc_list_nomeTipoDocField_title());             
		
		
		
		
		
		
		/*
		scoreField = new ListGridField("score", I18NUtil.getMessages().tipi_doc_list_scoreField_title());	
		scoreField.setType(ListGridFieldType.INTEGER);
		scoreField.setSortByDisplayField(false);
		scoreField.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				
				Integer score = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if(score != null) {
					String res = "";
					for(int i = 0; i < score; i++) {
						res += "<img src=\"images/score.png\" size=\"10\"/>";
					}
					return res;
				}
				return null;
			}
		});		
		*/
		
        // Colonne disattivate	
		
		 
		// Colonne hidden
		descrizioneTipoDocField = new ListGridField("descrizioneTipoDoc",  	I18NUtil.getMessages().tipi_doc_list_descrizioneTipoDocField_title());		descrizioneTipoDocField.setHidden(true);          	descrizioneTipoDocField.setCanHide(false);		
		idTipoDocField       	= new ListGridField("idTipoDoc",      		I18NUtil.getMessages().tipi_doc_list_idTipoDocField_title());				idTipoDocField.setHidden(true);	   					idTipoDocField.setCanHide(false);	
		flgSelXFinalitaField  	= new ListGridField("flgSelXFinalita"); 																			 	flgSelXFinalitaField.setHidden(true); 				flgSelXFinalitaField.setCanHide(false);
		uteInsField           	= new ListGridField("uteIns",         		I18NUtil.getMessages().tipi_doc_list_uteInsField_title());		   			uteInsField.setHidden(true);          				uteInsField.setCanHide(false);
		uteLastUpdField       	= new ListGridField("uteLastUpd",     		I18NUtil.getMessages().tipi_doc_list_uteLastUpdField_title());	   			uteLastUpdField.setHidden(true);          			uteLastUpdField.setCanHide(false);
		tsInsField            	= new ListGridField("tsIns",          		I18NUtil.getMessages().tipi_doc_list_tsInsField_title());	        		tsInsField.setType(ListGridFieldType.DATE);         tsInsField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);					tsInsField.setHidden(true);          		tsInsField.setCanHide(false);
		tsLastUpdField        	= new ListGridField("tsLastUpd",      		I18NUtil.getMessages().tipi_doc_list_tsLastUpdField_title());				tsLastUpdField.setType(ListGridFieldType.DATE);		tsLastUpdField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);				tsLastUpdField.setHidden(true);          	tsLastUpdField.setCanHide(false);
		
		flgDiSistemaField = new ListGridField("flgDiSistema", I18NUtil.getMessages().tipi_doc_list_flgDiSistemaField_title());	
		flgDiSistemaField.setType(ListGridFieldType.ICON);
		flgDiSistemaField.setWidth(30);
		flgDiSistemaField.setIconWidth(16);
		flgDiSistemaField.setIconHeight(16);
		Map<String, String> flgDiSistemaValueIcons = new HashMap<String, String>();		
		flgDiSistemaValueIcons.put("1", "lock.png");
		flgDiSistemaValueIcons.put("0", "blank.png");
		flgDiSistemaValueIcons.put("", "blank.png");
		flgDiSistemaField.setValueIcons(flgDiSistemaValueIcons);
		flgDiSistemaField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgDiSistema"))) {
					return I18NUtil.getMessages().tipi_doc_flgDiSistema_1_value();
				}				
				return null;
			}
		});
		flgDiSistemaField.setHidden(true);          	flgDiSistemaField.setCanHide(false);
		
		
		flgAnnField = new ListGridField("flgAnn", I18NUtil.getMessages().tipi_doc_list_flgAnnField_title(), 50);
		flgAnnField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgAnnField.setType(ListGridFieldType.ICON);
		flgAnnField.setWidth(30);
		flgAnnField.setIconWidth(16);
		flgAnnField.setIconHeight(16);
		Map<String, String> flgAnnValueIcons = new HashMap<String, String>();		
		flgAnnValueIcons.put("1", "ko.png");
		flgAnnValueIcons.put("0", "blank.png");
		flgAnnValueIcons.put("", "blank.png");
		flgAnnField.setValueIcons(flgAnnValueIcons);
		flgAnnField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				if("1".equals(record.getAttribute("flgAnn"))) {
					return I18NUtil.getMessages().tipi_doc_flgAnn_1_value();
				}				
				return null;
			}
		});
		
		flgAnnField.setHidden(true);          	flgAnnField.setCanHide(false);
		
		setFields(new ListGridField[] {
										// Colonne visibili
										codTipoDocField,
										nomeTipoDocField, 
										descrizioneTipoDocField, 
										
										// Colonne disattivate	
										tsInsField,
										uteInsField,
										tsLastUpdField,
										uteLastUpdField,	
										
										
										// Colonne invisibili										
										flgSelXFinalitaField,
										idTipoDocField,
										flgDiSistemaField,
										flgAnnField
										//scoreField,
					
		});  
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	@Override
	public void reloadFieldsFromCriteria(AdvancedCriteria criteria) {
		boolean isFulltextSearch = false;
		if(criteria != null && criteria.getCriteria() != null) {
			for(Criterion crit : criteria.getCriteria()) {
				if("searchFulltext".equals(crit.getFieldName())) {
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");					
					String parole = (String) value.get("parole");
					if(parole != null && !"".equals(parole)) {
						isFulltextSearch = true;
					}
				}
			}
		}
		
		/*
		if(isFulltextSearch) {
			scoreField.setHidden(false);
		} else {
			scoreField.setHidden(true);
		}	*/	
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {		
				try {
					refreshFields();
				} catch(Exception e) {}
				markForRedraw();
			}
		});	
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
	
	@Override  
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if(layout.isLookup() && record != null) {			
			if(record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}			        
		} 
		return super.getCellCSSText(record, rowNum, colNum);
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
		
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		SC.ask(flgDiSistema ? I18NUtil.getMessages().tipi_doc_annullamentoLogicoAsk_message() : I18NUtil.getMessages().tipi_doc_eliminazioneFisicaAsk_message(), new BooleanCallback() {					
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
							}
						}
					});													
				}
			}
		});         
	}
	
	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return record.getAttributeAsBoolean("flgSelXFinalita");
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
		return TipiDocLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return TipiDocLayout.isAbilToDel();
	}	
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		return TipiDocLayout.isRecordAbilToMod(flgDiSistema);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		return TipiDocLayout.isRecordAbilToDel(flgValido, flgDiSistema);	
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
