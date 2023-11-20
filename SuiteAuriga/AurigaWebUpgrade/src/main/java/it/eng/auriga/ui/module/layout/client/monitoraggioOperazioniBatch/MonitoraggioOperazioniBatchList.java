/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import it.eng.utility.ui.module.layout.client.common.VisualizzaXML;

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
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class MonitoraggioOperazioniBatchList extends CustomList {
	
	private ListGridField idRichiestaField;
	private ListGridField tipoOperazioneField;
	private ListGridField tipoOggettiDaProcessareField;
	private ListGridField dataRichiestaField;
	private ListGridField dataSchedulazioneField;
	private ListGridField motivoRichiestaField;
	private ListGridField utenteRichiestaSottomissioneField;
	private ListGridField statoRichiestaField;
	private ListGridField estremiOperazioneDerivanteRichiestaField;
	private ListGridField dettagliOperazioneRichiestaField;
	private ListGridField inizioPrimaElaborazioneField;
	private ListGridField inizioUltimaElaborazioneField;
	private ListGridField fineUltimaElaborazioneField;
	private ListGridField dataCompletataElaborazioneField;
	private ListGridField numeroElaborazioniField;
	private ListGridField noteField;
	private ListGridField tipoEventoScatenanteField;
	private ListGridField eventoScatenanteSuTipoOggettoField;
	private ListGridField estremiOggettoSuOperazioneField;
	private ListGridField numeroOggettiDaElaborareField;
	private ListGridField numeroOggettiElaboratiConSuccessoField;
	private ListGridField numeroOggettiElaboratiConErroreField;
	private ListGridField livelloPrioritaRichiestaField;
	private ListGridField flgDettagliField;
	private ListGridField rowIdRecordField;

	public MonitoraggioOperazioniBatchList(String nomeEntita) {
		
		super(nomeEntita);
		
		idRichiestaField = new ListGridField("idRichiesta", I18NUtil.getMessages().monitoraggio_operazioni_batch_list_idRichiestaField_title());      
		idRichiestaField.setHidden(true);	
		
		tipoOperazioneField = new ListGridField("tipoOperazione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_tipoOperazioneField_title());	
		
		tipoOggettiDaProcessareField = new ListGridField("tipoOggettiDaProcessare",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_tipoOggettiDaProcessareField_title());
		
		dataRichiestaField  = new ListGridField("dataRichiesta",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_dataRichiestaField_title());      
		dataRichiestaField.setType(ListGridFieldType.DATE);dataRichiestaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		dataSchedulazioneField = new ListGridField("dataSchedulazione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_dataSchedulazioneField_title());   
		dataSchedulazioneField.setType(ListGridFieldType.DATE);
		dataSchedulazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		motivoRichiestaField = new ListGridField("motivoRichiesta",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_motivoRichiestaField_title());
		
		utenteRichiestaSottomissioneField = new ListGridField("utenteRichiestaSottomissione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_utenteRichiestaSottomissioneField_title());
		
		statoRichiestaField = new ListGridField("statoRichiesta",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_statoRichiestaField_title());
		
		estremiOperazioneDerivanteRichiestaField = new ListGridField("estremiOperazioneDerivanteRichiesta",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_estremiOperazioneDerivanteRichiestaField_title());
		
		dettagliOperazioneRichiestaField = new ListGridField("dettagliOperazioneRichiesta",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_dettagliOperazioneRichiestaField_title());
		
		inizioPrimaElaborazioneField = new ListGridField("inizioPrimaElaborazione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_inizioPrimaElaborazioneField_title()); 
		inizioPrimaElaborazioneField.setType(ListGridFieldType.DATE);
		inizioPrimaElaborazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		inizioUltimaElaborazioneField = new ListGridField("inizioUltimaElaborazione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_inizioUltimaElaborazioneField_title()); 
		inizioUltimaElaborazioneField.setType(ListGridFieldType.DATE);
		inizioUltimaElaborazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		fineUltimaElaborazioneField = new ListGridField("fineUltimaElaborazione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_fineUltimaElaborazioneField_title()); 
		fineUltimaElaborazioneField.setType(ListGridFieldType.DATE);
		fineUltimaElaborazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		dataCompletataElaborazioneField = new ListGridField("dataCompletataElaborazione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_dataCompletataElaborazioneField_title());    
		dataCompletataElaborazioneField.setType(ListGridFieldType.DATE);
		dataCompletataElaborazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		numeroElaborazioniField = new ListGridField("numeroElaborazioni",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_numeroElaborazioniField_title());
		numeroElaborazioniField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return null;
			}
		});
		
		noteField = new ListGridField("note",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_noteField_title());
		
		tipoEventoScatenanteField = new ListGridField("tipoEventoScatenante",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_tipoEventoScatenanteField_title());
		
		eventoScatenanteSuTipoOggettoField = new ListGridField("eventoScatenanteSuTipoOggetto",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_eventoScatenanteSuTipoOggettoField_title());
		
		estremiOggettoSuOperazioneField = new ListGridField("estremiOggettoSuOperazione",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_estremiOggettoSuOperazioneField_title());
		
		numeroOggettiDaElaborareField = new ListGridField("numeroOggettiDaElaborare",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_numeroOggettiDaElaborareField_title());  
		numeroOggettiDaElaborareField.setType(ListGridFieldType.INTEGER);
		
		numeroOggettiElaboratiConSuccessoField = new ListGridField("numeroOggettiElaboratiConSuccesso",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_numeroOggettiElaboratiConSuccessoField_title());
		numeroOggettiElaboratiConSuccessoField.setType(ListGridFieldType.INTEGER);   
		numeroOggettiElaboratiConSuccessoField.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToViewListaOggettiElaboratiConSuccesso(record)) {
					String numeroOggettiElaboratiConSuccesso = record.getAttribute("numeroOggettiElaboratiConSuccesso");
					return buildImgButtonPlusStringHtml("buttons/multi_doc.png", numeroOggettiElaboratiConSuccesso);			
				} 
				return buildIconHtml("blank.png");					
			}
		});
		numeroOggettiElaboratiConSuccessoField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return I18NUtil.getMessages().monitoraggio_operazioni_batch_list_listaOggettiElaboratiConSuccessoButtonField_title();
			}
		});
		numeroOggettiElaboratiConSuccessoField.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				manageListaOggettiElaboratiConSuccessoButtonClick(record);
			}
		});
		
		numeroOggettiElaboratiConErroreField = new ListGridField("numeroOggettiElaboratiConErrore", I18NUtil.getMessages().monitoraggio_operazioni_batch_list_numeroOggettiElaboratiConErroreField_title());      numeroOggettiElaboratiConErroreField.setType(ListGridFieldType.INTEGER);     
		numeroOggettiElaboratiConErroreField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if(isRecordAbilToViewListaOggettiElaboratiConErrore(record)) {
					String numeroOggettiElaboratiConErrore = record.getAttribute("numeroOggettiElaboratiConErrore");
					return buildImgButtonPlusStringHtml("buttons/multi_doc.png", numeroOggettiElaboratiConErrore);			
				} 
				return buildIconHtml("blank.png");					
			}
		});
		numeroOggettiElaboratiConErroreField.setHoverCustomizer(new HoverCustomizer() {
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return I18NUtil.getMessages().monitoraggio_operazioni_batch_list_listaOggettiElaboratiConErroreButtonField_title();
			}			
		});
		numeroOggettiElaboratiConErroreField.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				manageListaOggettiElaboratiConErroreButtonClick(record);
			}
		});
		
		livelloPrioritaRichiestaField = new ListGridField("livelloPrioritaRichiesta", I18NUtil.getMessages().monitoraggio_operazioni_batch_list_livelloPrioritaRichiestaField_title());
		livelloPrioritaRichiestaField.setType(ListGridFieldType.ICON);
		livelloPrioritaRichiestaField.setWidth(30);
		livelloPrioritaRichiestaField.setIconWidth(16);
		livelloPrioritaRichiestaField.setIconHeight(16);
		Map<String, String> livelloPrioritaRichiestaFieldValueIcons = new HashMap<String, String>();
		livelloPrioritaRichiestaFieldValueIcons.put("0", "prioritaBassa.png");
		livelloPrioritaRichiestaFieldValueIcons.put("1", "blank.png");
		livelloPrioritaRichiestaFieldValueIcons.put("2", "prioritaMedia.png");
		livelloPrioritaRichiestaFieldValueIcons.put("3", "prioritaAlta.png");
		livelloPrioritaRichiestaFieldValueIcons.put("4", "prioritaAltissima.png");		
		livelloPrioritaRichiestaFieldValueIcons.put("", "blank.png");
		livelloPrioritaRichiestaField.setValueIcons(livelloPrioritaRichiestaFieldValueIcons);
		livelloPrioritaRichiestaField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String valuePriorita = record.getAttribute("livelloPrioritaRichiesta");
				Integer priorita = valuePriorita != null && !"".equals(String.valueOf(valuePriorita)) ? new Integer(String.valueOf(valuePriorita)) : null;
				if (priorita != null) {
					String res = "";
					switch (priorita) {
					case 0: // priorita' bassa
						res = I18NUtil.getMessages().prioritaBassa_Alt_value();
						break;
					case 1: // priorita' normale
						res = "";
						break;
					case 2: // priorita' media
						res = I18NUtil.getMessages().prioritaMedia_Alt_value();
						break;
					case 3: // priorita' alta
						res = I18NUtil.getMessages().prioritaAlta_Alt_value();
						break;
					case 4: // priorita' altissima
						res = I18NUtil.getMessages().prioritaAltissima_Alt_value();
						break;
					}
					return res;
				}
				return null;
			}
		});
		
		flgDettagliField = new ListGridField("flgDettagli",I18NUtil.getMessages().monitoraggio_operazioni_batch_list_flgDettagliField_title()); 
		flgDettagliField.setCanReorder(false);	 
		flgDettagliField.setCanGroupBy(false); 
		flgDettagliField.setCanSort(false); 
		flgDettagliField.setCanFreeze(false);		      
		flgDettagliField.setCanExport(false);
		flgDettagliField.setType(ListGridFieldType.ICON); 
		flgDettagliField.setWidth(30); 
		flgDettagliField.setIconWidth(16); 
		flgDettagliField.setIconHeight(16);
		Map<String, String> flgDettagliValueIcons = new HashMap<String, String>();		
		flgDettagliValueIcons.put("1", "message/information.png");
		flgDettagliValueIcons.put("0", "blank.png");
		flgDettagliValueIcons.put("", "blank.png");
		flgDettagliField.setValueIcons(flgDettagliValueIcons);		
		flgDettagliField.setHoverCustomizer(new HoverCustomizer() {	
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if("1".equals(record.getAttribute("flgDettagli"))) {
					return I18NUtil.getMessages().monitoraggio_operazioni_batch_flgDettagli_1_value();					
				}				
				return null;
			}
		});	
		flgDettagliField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				ListGridRecord record = getRecord(event.getRecordNum());
				manageDettagliButtonClick(record);
			}
		});
		
		rowIdRecordField = new ListGridField("rowIdRecord");
		rowIdRecordField.setHidden(true); 
		rowIdRecordField.setCanHide(false);
				
		recordClickHandler.removeHandler();
		
		setFields(new ListGridField[] {
                   idRichiestaField,
                   tipoOperazioneField,
                   tipoOggettiDaProcessareField,
                   dataRichiestaField,
                   dataSchedulazioneField,
                   motivoRichiestaField,
                   utenteRichiestaSottomissioneField,
                   statoRichiestaField,
                   livelloPrioritaRichiestaField,
                   estremiOperazioneDerivanteRichiestaField,
                   dettagliOperazioneRichiestaField,
                   numeroElaborazioniField,
                   inizioPrimaElaborazioneField,
                   inizioUltimaElaborazioneField,
                   fineUltimaElaborazioneField,
                   dataCompletataElaborazioneField,
                   noteField,
                   tipoEventoScatenanteField,
                   eventoScatenanteSuTipoOggettoField,
                   estremiOggettoSuOperazioneField,
                   flgDettagliField,
                   numeroOggettiDaElaborareField,
                   numeroOggettiElaboratiConSuccessoField,
                   numeroOggettiElaboratiConErroreField,
                   rowIdRecordField
		});  
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
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
			if(!isRecordAbilToView(record)) {	
				detailButton.disable();			
			}			
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
		return MonitoraggioOperazioniBatchLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return MonitoraggioOperazioniBatchLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToView(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return MonitoraggioOperazioniBatchLayout.isRecordAbilToView(recProtetto);
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return MonitoraggioOperazioniBatchLayout.isRecordAbilToMod(recProtetto);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equalsIgnoreCase("1");
		return MonitoraggioOperazioniBatchLayout.isRecordAbilToDel(flgValido, recProtetto);	
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	
	protected void manageListaOggettiElaboratiConSuccessoButtonClick(ListGridRecord record) {					
			
		  String idRichiesta       = record.getAttribute("idRichiesta");
		  if (idRichiesta != null){
			  String tipoOperazione    = record.getAttribute("tipoOperazione");
			  String dataRichiesta     = record.getAttribute("dataRichiesta");
			  String motivoRichiesta   = record.getAttribute("motivoRichiesta");
			  ModalWindow window = new ModalWindow("monitoraggio_operazioni_batch_lista_oggetti_elaborati");			
			  OggettiElaboratiLayout body = new OggettiElaboratiLayout("monitoraggio_operazioni_batch_lista_oggetti_elaborati", idRichiesta);			
			  body.setHeight100();
			  body.setWidth100();
			  window.setIcon("buttons/multi_doc.png");
			  window.setTitle(I18NUtil.getMessages().oggetti_elaborati_list_title(tipoOperazione, dataRichiesta, motivoRichiesta));
			  window.setBody(body);					
			  window.show();	
		  }
		  else{
				SC.say("L'identificativo della richiesta e' inesistente.");
			}
		}
	  
	protected void manageListaOggettiElaboratiConErroreButtonClick(ListGridRecord record) {					
		
		  String idRichiesta       = record.getAttribute("idRichiesta");
		  if (idRichiesta != null){
			  String tipoOperazione    = record.getAttribute("tipoOperazione");
			  String dataRichiesta     = record.getAttribute("dataRichiesta");
			  String motivoRichiesta   = record.getAttribute("motivoRichiesta");
			  ModalWindow window = new ModalWindow("monitoraggio_operazioni_batch_lista_oggetti_elaborati");			
			  OggettiElaboratiLayout body = new OggettiElaboratiLayout("monitoraggio_operazioni_batch_lista_oggetti_elaborati", idRichiesta);			
			  body.setHeight100();
			  body.setWidth100();
			  window.setIcon("buttons/multi_doc.png");
			  window.setTitle(I18NUtil.getMessages().oggetti_elaborati_list_title(tipoOperazione, dataRichiesta, motivoRichiesta));
			  window.setBody(body);					
			  window.show();	
		  }
		  else{
				SC.say("L'identificativo della richiesta e' inesistente.");
			}
		}
	
	protected boolean isRecordAbilToViewListaOggettiElaboratiConSuccesso(ListGridRecord listRecord) {
		int nro = listRecord.getAttribute("numeroOggettiElaboratiConSuccesso") != null && !listRecord.getAttribute("numeroOggettiElaboratiConSuccesso").isEmpty() ? Integer.valueOf(listRecord.getAttribute("numeroOggettiElaboratiConSuccesso")) : 0;
		if (nro > 0) {
				return true;
		}
		return false;
	}
	
	protected boolean isRecordAbilToViewListaOggettiElaboratiConErrore(ListGridRecord listRecord) {
		int nro = listRecord.getAttribute("numeroOggettiElaboratiConErrore") != null && !listRecord.getAttribute("numeroOggettiElaboratiConErrore").isEmpty() ? Integer.valueOf(listRecord.getAttribute("numeroOggettiElaboratiConErrore")) : 0;
		if (nro > 0) {
				return true;
		}
		return false;
	}

	protected String buildImgButtonPlusStringHtml(String src, String val) {
			return "<div style=\"cursor:pointer\" align=\"center\">" + " " + val +  		
			             "<img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" />" + 
					"</div>";		
	}
	 
	 private void manageDettagliButtonClick(ListGridRecord recordIn) {
		 
			if("1".equals(recordIn.getAttribute("flgDettagli"))) {
				String nomeTabella = "DMT_RICH_OPER_MASSIVE";
				String nomeColonna = "XML_INFO";
				String rowIdRecord = recordIn.getAttribute("rowIdRecord");
				Record record = new Record();
				record.setAttribute("nomeTabella", nomeTabella);
				record.setAttribute("nomeColonna", nomeColonna);
				record.setAttribute("rowIdRecord", rowIdRecord);
				GWTRestDataSource datasource = new GWTRestDataSource("GetClobFromTabColDataSource");
				datasource.executecustom("getBlob", record, new DSCallback() {
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record result = response.getData()[0];
							if (result != null && result.getAttribute("message") != null) {
								Layout.addMessage(new MessageBean(result.getAttribute("message"), "", MessageType.ERROR));
							} else if (result.getAttribute("errorCode") == null || result.getAttribute("errorCode").isEmpty()) {
								String blobOut = response.getData()[0].getAttribute("blobValue");
								VisualizzaXML xmlWindow = new VisualizzaXML(blobOut, true);
								xmlWindow.show();
								xmlWindow.setTitle("Informazioni");
							}
						}else{
							Layout.addMessage(new MessageBean("Errore durante la lettura del file xml.", "", MessageType.ERROR));
						}
					}
				});
			}
	 }	 
}