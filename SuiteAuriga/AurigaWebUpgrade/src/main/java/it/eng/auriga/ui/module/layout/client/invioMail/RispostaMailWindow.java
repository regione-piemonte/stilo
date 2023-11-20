/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaWindow;
import it.eng.auriga.ui.module.layout.shared.util.IndirizziEmailSplitter;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class RispostaMailWindow extends PostaElettronicaWindow {

	private RispostaMailWindow _window;
	private InvioMailLayout _layout;
	
	protected ToolStrip mainToolStrip;
	
	//Modelli
	protected GWTRestDataSource modelliDS;
	protected SelectItem modelliSelectItem;
	protected ListGrid modelliPickListProperties;

	//Firma in calce
	protected SelectItem firmaInCalceSelectItem;
	protected ListGrid firmaInCalcePickListProperties;
	protected ToolStripButton firmaPredefinitaButton;
	protected GWTRestDataSource firmeDS;
	
	private String starterMailBodyTextHtml = "";
	private String starterMailBodyText = "";
	private String fontDivHeaderMail = "<div style=\"font-size: 10pt; font-family: arial\">";
	
	public RispostaMailWindow(String tipoRel, boolean flgSoloAlMitt, String casellaRicezione, Record record, DSCallback callback) {
		super("rispostaemail", true, tipoRel, record, casellaRicezione, flgSoloAlMitt, null, null, null, callback);

		_window = this;
	}

	public RispostaMailWindow(String tipoRel, boolean flgSoloAlMitt, String casellaRicezione, Record record, CustomLayout customLayoutToDoSearch,
			DSCallback callback) {
		super("rispostaemail", true, tipoRel, record, casellaRicezione, flgSoloAlMitt, null, null, null, customLayoutToDoSearch, callback);

		_window = this;
	}

	public RispostaMailWindow(String tipoRel, boolean flgSoloAlMitt, String casellaRicezione, Record record, CustomDetail customDetailToReload,
			DSCallback callback) {
		super("rispostaemail", true, tipoRel, record, casellaRicezione, flgSoloAlMitt, null, null, null, customDetailToReload, callback);

		_window = this;
	}

	@Override
	public VLayout getLayoutDatiWindow() {
		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_caricamento_mail());

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		buildRispostaMail(tipoRel, flgSoloAlMitt, casellaRicezione);

		return _layout;
	}

	private void buildRispostaMail(String tipoRel, final boolean flgSoloAlMitt, final String casellaRicezione) {

		_layout = new InvioMailLayout(this, tipoRel, vm, null);

		_layout.setHeight100();
		_layout.setWidth100();

		final Record lRecordRisposta = new Record();
		lRecordRisposta.setAttribute("idEmail", recordPrincipale.getAttribute("idEmail"));
		lRecordRisposta.setAttribute("mailPredecessoreStatoLav", recordPrincipale.getAttribute("statoLavorazione"));

		if (recordPrincipale.getAttribute("oggetto") != null && !"".equals(recordPrincipale.getAttribute("oggetto"))) {
			lRecordRisposta.setAttribute("oggetto", "Re: " + recordPrincipale.getAttribute("oggetto"));
		}

		Record lDetailRecord = new Record();
		lDetailRecord.setAttribute("idEmail", recordPrincipale.getAttribute("idEmail"));
		lDetailRecord.setAttribute("casellaRicezione", recordPrincipale.getAttribute("casellaRicezione"));

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", lDetailRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					final Record detailRecord = response.getData()[0];
					
					lRecordRisposta.setAttribute("mittenteIniziale", recordPrincipale.getAttribute("casellaRicezione"));
					lRecordRisposta.setAttribute("mittente", "");
					lRecordRisposta.setAttribute("uriMail", detailRecord.getAttribute("uriEmail"));
					lRecordRisposta.setAttribute("idEmailPrincipale", recordPrincipale.getAttribute("idEmail"));
					lRecordRisposta.setAttribute("replyTo", detailRecord.getAttribute("replyTo"));
					
					if(recordPrincipale.getAttributeAsBoolean("richiediAllegati") != null && recordPrincipale.getAttributeAsBoolean("richiediAllegati")){
						RecordList attachList = new RecordList();
						if(detailRecord.getAttributeAsRecordList("allegati") != null && !detailRecord.getAttributeAsRecordList("allegati").isEmpty()){
							for(int i=0; i < detailRecord.getAttributeAsRecordList("allegati").getLength(); i++) {
								
								Record currentRecord = detailRecord.getAttributeAsRecordList("allegati").get(i);
								Record attach = new Record();
								attach.setAttribute("uriAttach", currentRecord.getAttribute("uriFileAllegato"));
								attach.setAttribute("fileNameAttach", currentRecord.getAttribute("nomeFileAllegato"));
								attach.setAttribute("infoFileAttach", currentRecord.getAttributeAsRecord("infoFile"));
								attachList.add(attach);
							}
						}
						lRecordRisposta.setAttribute("attach", attachList);
					}
	
					if (flgSoloAlMitt) {
						populateDestinatariRispondi(lRecordRisposta);
					} else{
						populateDestinatariRispondiATutti(casellaRicezione, detailRecord, lRecordRisposta);
					}
					
					//Creo i due header
					//if (detailRecord.getAttribute("corpoHtml") != null && !"".equals(detailRecord.getAttribute("corpoHtml"))) {
						getHeaderMailHTML(detailRecord);
					//}
					
					//if (detailRecord.getAttribute("corpo") != null && !"".equals(detailRecord.getAttribute("corpo"))) {
						getHeaderMailText(detailRecord);
					//}
					
					if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
						//Se sono nella modalità per cui devo visualizzare le firme in calce
						
						//Setto inizialmente il valore del corpo in base a quanto presente
						//if (detailRecord.getAttribute("corpoHtml") != null && !"".equals(detailRecord.getAttribute("corpoHtml"))) {
							lRecordRisposta.setAttribute("bodyHtml", starterMailBodyTextHtml);
						//} else {
							//lRecordRisposta.setAttribute("bodyHtml", "");
						//}
						
						GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
						//Pongo un extraparam per il valore della nuova firma
						corpoMailDataSource.extraparam.put("newSignature", AurigaLayout.getHtmlSignature("risposta"));
						corpoMailDataSource.performCustomOperation("addSignature", lRecordRisposta, new DSCallback() {

							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
									Record record = response.getData()[0];

									lRecordRisposta.setAttribute("bodyHtml", record.getAttribute("bodyHtml"));

									_layout.getForm().caricaMail(lRecordRisposta);
									_layout.getForm().clearErrors(true);
									
									//Imposto il valore della select con quella che è stata pre-impostata come automatica
									if(AurigaLayout.getFirmaEmailAutoRisposta() != null && !AurigaLayout.getFirmaEmailAutoRisposta().equals("")){
										firmaInCalceSelectItem.setValue(AurigaLayout.getFirmaEmailAutoRisposta());
									}
							
									_window.show();
									
									Layout.hideWaitPopup();
								}
							}
						}, new DSRequest());
					}
					else{
						//Se sono in modalità per cui devo visualizzare la firma normale
						if (detailRecord.getAttribute("corpoHtml") != null && !"".equals(detailRecord.getAttribute("corpoHtml"))) {
							if(AurigaLayout.getFirmaEmailHtml() != null){
								lRecordRisposta.setAttribute("bodyHtml", "<br/><br/>" + AurigaLayout.getFirmaEmailHtml() + starterMailBodyTextHtml);
							}else{
								lRecordRisposta.setAttribute("bodyHtml", "<br/><br/>" + starterMailBodyTextHtml);
							}
						} else {
							if(AurigaLayout.getFirmaEmailHtml() != null){
								lRecordRisposta.setAttribute("bodyHtml", "<br/><br/>" + AurigaLayout.getFirmaEmailHtml());
						    }else{
								lRecordRisposta.setAttribute("bodyHtml", "" );
							}
						}
						lRecordRisposta.setAttribute("mittenteIniziale", detailRecord.getAttribute("casellaRicezione"));
						lRecordRisposta.setAttribute("mittente", "");
						lRecordRisposta.setAttribute("uriMail", detailRecord.getAttribute("uriEmail"));
						_layout.getForm().caricaMail(lRecordRisposta);
						_layout.getForm().clearErrors(true);
						_window.show();
					}
				}
				Layout.hideWaitPopup();
			}
		}, new DSRequest());
	}
	
	
	/**
	 * RISPONDI
	 */
	private void populateDestinatariRispondi(final Record lRecordRisposta) {
		String accountMittente = lRecordRisposta != null && lRecordRisposta.getAttribute("replyTo") != null &&
				!"".equalsIgnoreCase(lRecordRisposta.getAttribute("replyTo")) ?
				lRecordRisposta.getAttribute("replyTo") : recordPrincipale.getAttribute("accountMittente");
		String destinatariPrimari = "";
		if(accountMittente.contains(" da ")){
			String[] lStringDestinatari = IndirizziEmailSplitter.split(accountMittente);
			if(lStringDestinatari != null && lStringDestinatari.length > 1){
				destinatariPrimari += lStringDestinatari[2] + ";";
				lRecordRisposta.setAttribute("destinatari", destinatariPrimari);
			}
		} else {
			lRecordRisposta.setAttribute("destinatari", accountMittente + ";");
		}
	}
	
	/**
	 * RISPONDI A TUTTI
	 */	
	private void populateDestinatariRispondiATutti(String casellaRicezione, final Record detailRecord,final Record lRecordRisposta) {
		String accountMittente = lRecordRisposta != null && lRecordRisposta.getAttribute("replyTo") != null &&
				!"".equalsIgnoreCase(lRecordRisposta.getAttribute("replyTo")) ?
				lRecordRisposta.getAttribute("replyTo") : recordPrincipale.getAttribute("accountMittente");
		String destinatariPrimari = "";
		if(accountMittente.contains(" da ")){
			String[] lStringDestinatari = IndirizziEmailSplitter.split(accountMittente);
			if(lStringDestinatari != null && lStringDestinatari.length > 1){
				destinatariPrimari += lStringDestinatari[2];
			}
		} else {
			destinatariPrimari += accountMittente;
		}
		
		// CAMPO A DELLA MASCHERA - DESTINATARI PRIMARI
		if (detailRecord.getAttributeAsRecordList("destinatariPrincipali") != null &&
				!detailRecord.getAttributeAsRecordList("destinatariPrincipali").isEmpty()) {
			for(int i = 0; i < detailRecord.getAttributeAsRecordList("destinatariPrincipali").getLength();i++) {
				Record destPrincItem = detailRecord.getAttributeAsRecordList("destinatariPrincipali").get(i);
				if (casellaRicezione == null || !casellaRicezione.equals(destPrincItem.getAttribute("account"))) {
					destinatariPrimari += ";" + destPrincItem.getAttribute("account");
				}
			}
			lRecordRisposta.setAttribute("destinatari", destinatariPrimari);
		}

		// CAMPO CC DELLA MASCHERA - DESTINATARI IN COPIA
		if (detailRecord.getAttributeAsRecordList("destinatariCC") != null && !detailRecord.getAttributeAsRecordList("destinatariCC").isEmpty()) {
			String destinatariCC = null;
			for(int i = 0; i < detailRecord.getAttributeAsRecordList("destinatariCC").getLength(); i++) {
				Record destCCItem = detailRecord.getAttributeAsRecordList("destinatariCC").get(i);
				if (casellaRicezione == null || !casellaRicezione.equals(destCCItem.getAttribute("account"))) {
					if (destinatariCC == null) {
						destinatariCC = destCCItem.getAttribute("account");
					} else {
						destinatariCC += ";" + destCCItem.getAttribute("account");
					}
				}
			}
			lRecordRisposta.setAttribute("destinatariCC", destinatariCC);
		}
	}

	/**
	 * Metodo che, in base ai dati che vengono passati dal datasource, crea l'header della mail che comparirà
	 * nel body come mail precedente
	 * @param record
	 * @param newLine
	 * @param body
	 * @return
	 */
	private String createHeaderMail(Record record, String newLine, String body){
		
		//Variabili utilizzate per inserire le informazioni principali della mail che si sta inoltrando
		String headerDa = (record.getAttribute("accountMittente") != null && !record.getAttribute("accountMittente").equals("") ) ? 
						  newLine + "<b>Da: </b>" + record.getAttribute("accountMittente") : "";
						  
		String headerInviato = (record.getAttribute("tsInvio") != null && !record.getAttribute("tsInvio").equals("") ) ? 
				  		  newLine + "<b>Inviato: </b>" + record.getAttribute("tsInvio") : "";
				  
		String headerA = "";
        RecordList destinatariPrincipaliRecord = record.getAttributeAsRecordList("destinatariPrincipali");
        if(destinatariPrincipaliRecord != null){
        	for (int i = 0; i < destinatariPrincipaliRecord.getLength(); i++) {
        		headerA += destinatariPrincipaliRecord.get(i).getAttribute("account") + ";";
			}
        }
        
	    headerA = (headerA != null && !headerA.equals("") ) ? newLine + "<b>A: </b>" + headerA : "";
						  
	    String headerCC = "";
	    RecordList destinatariCCRecord = record.getAttributeAsRecordList("destinatariCC");
	    if(destinatariCCRecord != null){
	  	  for (int i = 0; i < destinatariCCRecord.getLength(); i++) {
	  		headerCC += destinatariCCRecord.get(i).getAttribute("account") + ";";
		  }
	    }
	  
	    headerCC = (headerCC != null && !headerCC.equals("") ) ? newLine + "<b>CC: </b>" + headerCC : "";
								  
	    String headerOggetto = (record.getAttribute("oggetto") != null && !record.getAttribute("oggetto").equals("") ) ? 
			              newLine + "<b>Oggetto: </b>" + record.getAttribute("oggetto") : "";
			           
	    //Inserisco il marker per identificare che si tratta della mail che si vuole inoltrare 
		String startHeaderMarker = "<!--inizioheadermail-->";
		String endHeaderMarker = "<!--fineheadermail-->";
		    
		return newLine + newLine + newLine + newLine 
		  + startHeaderMarker
		  + fontDivHeaderMail  
		  + headerDa
		  + headerInviato 
		  + headerA  
		  + headerCC 
		  + headerOggetto 
		  + "</div>" + newLine + newLine
		  + body 
		  + endHeaderMarker;
	}

	/**
	 * Crea il bodyHTML che dovrà essere utilizzato per inizializzare la text relativa
	 * @param record
	 * @return
	 */
	private String getHeaderMailHTML(Record record) {
		
		if(starterMailBodyTextHtml == null || "".equals(starterMailBodyTextHtml)){
			starterMailBodyTextHtml = createHeaderMail(record, "<br/>", (record.getAttribute("corpoHtml") != null ?
					record.getAttribute("corpoHtml") : ""));
		}
		return starterMailBodyTextHtml;
	}

	/**
	 * Crea il bodyText che dovrà essere utilizzato per inizializzare la text relativa
	 * @param record
	 * @return
	 */
	private String getHeaderMailText(Record record) {
		
		if(starterMailBodyText == null || "".equals(starterMailBodyText)){
			starterMailBodyText = createHeaderMail(record, "\n", (record.getAttribute("corpo") != null ? record.getAttribute("corpo") : ""));
		}
		return starterMailBodyText;
	}
	
	@Override
	public String getTitleWindow() {

		return "Risposta e-mail ";
	}

	@Override
	public String getIconWindow() {

		return "postaElettronica/risposta.png";
	}

	@Override
	public void setFileAsAllegato(Record record) {

		AttachmentReplicableItem item = (AttachmentReplicableItem) _layout.getForm().getAttachmentReplicableItem();
		item.setFileAsAllegatoFromWindow(record);
	}

	@Override
	public void inviaMail(final DSCallback callback) {

		_layout.inviaMail(callback, controllaMailAperte());
	}

	@Override
	public void salvaBozza(DSCallback callback) {

		_layout.salvaBozza(callback, "R");
	}
	
	@Override
	protected VLayout createMainLayout() {

		VLayout mainLayout = super.createMainLayout();

		if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
			createMainToolstrip();
		}
		
		mainLayout = new VLayout();
		mainLayout.setHeight100();
		mainLayout.setWidth100();
		
		if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
			mainLayout.addMember(mainToolStrip, 0);
		}
		
		mainLayout.addMember(tabSetGenerale);
		mainLayout.addMember(detailToolStrip);

		return mainLayout;
	}

	private void createMainToolstrip() {

		//Creo la select dei modelli
		createModelliSelectItem();
				
		if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
			//Creo la select relativa alla firma in calce
			createSelectFirmaInCalce();
		}

		// Creo la mainToolstrip e aggiungo le select impostate
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("0px");
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);
		
		mainToolStrip.addFormItem(modelliSelectItem);
		mainToolStrip.addButton(new GestioneModelliButton(TipologiaMail.RISPOSTA, modelliSelectItem));

		if(AurigaLayout.getParametroDBAsBoolean("SHOW_FIRME_IN_CALCE")){
			mainToolStrip.addFormItem(firmaInCalceSelectItem);
			mainToolStrip.addButton(firmaPredefinitaButton);
		}

	}
	
	public void createModelliSelectItem() {
		
		createModelliDatasource(nomeEntita);

		modelliSelectItem = new SelectItem("modelli");
		modelliSelectItem.setValueField("key");
		modelliSelectItem.setDisplayField("displayValue");
		modelliSelectItem.setTitle("<b>" + I18NUtil.getMessages().protocollazione_detail_modelliSelectItem_title() + "</b>");
		modelliSelectItem.setCachePickListResults(false);
		modelliSelectItem.setRedrawOnChange(true);
		modelliSelectItem.setShowOptionsFromDataSource(true);
		modelliSelectItem.setOptionDataSource(modelliDS);
		modelliSelectItem.setAllowEmptyValue(true);
		
		ListGridField modelliRemoveField = new ListGridField("remove");
		modelliRemoveField.setType(ListGridFieldType.ICON);
		modelliRemoveField.setWidth(18);
		modelliRemoveField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {

				return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
			}
		});
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {
				
				SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						if(value) {
							final String key = event.getRecord().getAttribute("key");
							modelliDS.removeData(event.getRecord(), new DSCallback() {

								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									String value = (String) modelliSelectItem.getValue();
									if (key != null && value != null && key.equals(value)) {
										modelliSelectItem.setValue((String) null);
									}
								} 
							});
						}
					}
				});			
			}   
		});

		ListGridField modelliKeyField = new ListGridField("key");
		modelliKeyField.setHidden(true);
		ListGridField modelliDisplayValueField = new ListGridField("displayValue");
		modelliDisplayValueField.setWidth("100%");
		modelliSelectItem.setPickListFields(modelliRemoveField, modelliKeyField, modelliDisplayValueField);
		
		modelliPickListProperties = new ListGrid();
		modelliPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		modelliPickListProperties.setShowHeader(false);
		//modelliPickListProperties.setCanRemoveRecords(true);
		modelliPickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {
				String userid = (String) event.getRecord().getAttribute("userid");
				String prefName = (String) event.getRecord().getAttribute("prefName");
				if (prefName != null && !"".equals(prefName)) {
					AdvancedCriteria criteria = new AdvancedCriteria();
					criteria.addCriteria("prefName", OperatorId.EQUALS, prefName);
					criteria.addCriteria("flgIncludiPubbl", userid.startsWith("PUBLIC.") ? "1" : "0");
					if (userid.startsWith("UO.")) {
						String idUo = (String) event.getRecord().getAttribute("idUo");
						criteria.addCriteria("idUo", idUo);
					}
					modelliDS.fetchData(criteria, new DSCallback() {

						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							Record[] data = response.getData();
							if (data.length != 0) {
								Record record = data[0];

								final Map oldValues = getMapValues(); //Mappa dei campi presenti nel form
								
								//Mappa dei valori del modello da inserire nel form
								final Map newValues = new Record(JSON.decode(record.getAttribute("value"))).toMap(); 	
								
								final String mittentePref = (String) newValues.get("mittente");
								GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
								accounts.addParam("finalita", "INVIO");
								accounts.fetchData(null, new DSCallback() {
									
									@Override
									public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
										if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
											RecordList result = dsResponse.getDataAsRecordList();
											Boolean isValid = false;
											for(int i=0; i < result.getLength(); i++) {
												if(result.get(i).getAttributeAsString("key").equalsIgnoreCase(mittentePref)) {
													isValid = true;
													break;
												}
											}
											if(!isValid) {
												newValues.put("mittente", "");
											}
											/*
											 * Creo un record con i valori dei campi presenti nel form.
											 * Nel caso questi stessi campi siano presenti nel modello verranno
											 * modificati dal metodo successivo 
											 */
											final Record values = new Record(oldValues); 
											
											setNewValuesFromModel(values, oldValues, newValues);
										}
									}
								});
							}
						}
					});
				}
			}
		});

		modelliSelectItem.setPickListProperties(modelliPickListProperties);
	}
	
	private void setNewValuesFromModel(final Record values, final Map oldValues, final Map newValues) {
		//Oggetto della mail
		if(newValues.get("oggetto") != null && !"".equals(newValues.get("oggetto"))){
			/*
			 * In questo caso è presente un oggetto nel modello che si sta
			 * cercando di utilizzare.
			 * Deve essere concatenato DOPO l'oggetto già presente nella mail,
			 * ovvero DOPO l'oggetto standard impostato nella mail 
			 */
			values.setAttribute("oggetto", 
					(oldValues.get("oggetto") != null && !"".equals(oldValues.get("oggetto")) ? oldValues.get("oggetto") + " - " : "" ) + newValues.get("oggetto"));
		}
		
		//Mittente
		if(newValues.get("mittente") != null && !"".equals(newValues.get("mittente"))){
			/*
			 * Se il mittente nel modello è impostato si deve utilizzare questo,
			 * altrimenti si deve usare quello presente nella mail di partenza
			 */
			values.setAttribute("mittente", newValues.get("mittente"));
		} else {
			values.setAttribute("mittente", oldValues.get("mittente"));
		}
		
		//Destinatari
		if(newValues.get("destinatari") != null && !"".equals(newValues.get("destinatari"))){
			/*
			 * Se il destinatario nel modello è impostato si deve utilizzare questo,
			 * altrimenti si deve usare quello presente nella mail di partenza
			 */
			values.setAttribute("destinatari", newValues.get("destinatari"));
		} else{
			values.setAttribute("destinatari", oldValues.get("destinatari"));
		}
		
		// Filtro destinatari principali
		if(newValues.get("recordFiltriDestinatari") != null) {
			values.setAttribute("recordFiltriDestinatari", newValues.get("recordFiltriDestinatari"));
		} else {
			values.setAttribute("recordFiltriDestinatari", oldValues.get("recordFiltriDestinatari"));
		}
		
		//Destinatari CC
		if(newValues.get("destinatariCC") != null && !"".equals(newValues.get("destinatariCC"))){
			/*
			 * Se il destinatario CC nel modello è impostato si deve utilizzare questo,
			 * altrimenti si deve usare quello presente nella mail di partenza
			 */
			values.setAttribute("destinatariCC", newValues.get("destinatariCC"));
		} else {
			values.setAttribute("destinatariCC", oldValues.get("destinatariCC"));
		}
		
		// Filtro destinatari principali CC
		if(newValues.get("recordFiltriDestinatariCC") != null) {
			values.setAttribute("recordFiltriDestinatariCC", newValues.get("recordFiltriDestinatariCC"));
		} else {
			values.setAttribute("recordFiltriDestinatariCC", oldValues.get("recordFiltriDestinatariCC"));
		}
		
		//Destinatari CCN
		if(newValues.get("destinatariCCN") != null && !"".equals(newValues.get("destinatariCCN"))){
			/*
			 * Se il destinatario CC nel modello è impostato si deve utilizzare questo,
			 * altrimenti si deve usare quello presente nella mail di partenza
			 */
			values.setAttribute("destinatariCCN", newValues.get("destinatariCCN"));
		} else {
			values.setAttribute("destinatariCCN", oldValues.get("destinatariCCN"));
		}
		
		// Filtro destinatari principali CCN
		if(newValues.get("recordFiltriDestinatariCCN") != null) {
			values.setAttribute("recordFiltriDestinatariCCN", newValues.get("recordFiltriDestinatariCCN"));
		} else {
			values.setAttribute("recordFiltriDestinatariCCN", oldValues.get("recordFiltriDestinatariCCN"));
		}
		
		//Conferma lettura
		if(newValues.get("confermaLettura") != null && !"".equals(newValues.get("confermaLettura"))){
			/*
			 * Se il flag di conferma lettura nel modello è impostato si deve utilizzare questo,
			 * altrimenti si deve usare quello presente nella mail di partenza
			 */
			values.setAttribute("confermaLettura", newValues.get("confermaLettura"));
		} else{
			values.setAttribute("confermaLettura", oldValues.get("confermaLettura"));
		}
		
		
		//Allegati
		ArrayList<Map> lArrayList = (ArrayList<Map>) oldValues.get("attach");
		if (lArrayList != null) {
			RecordList lRecordList = new RecordList();
			for (Map lMap : lArrayList) {
				Record lRecord = new Record(lMap);
				lRecordList.add(lRecord);
			}
			values.setAttribute("attach", lRecordList);
		}
		
		//Body della mail
		if(newValues.get("bodyHtml") != null && !"".equals(newValues.get("bodyHtml"))){
			/*
			 * In questo caso è presente un corpo nel modello che si sta
			 * cercando di utilizzare.
			 * Deve essere concatenato PRIMA del body già presente nella mail,
			 * ovvero PRIMA del body standard impostato nella stessa
			 */
			
			if(((String)oldValues.get("bodyHtml")).contains("<!-- Inizio firmaInCalce -->")){
				/*
				 * Vuol dire che nel corpo della mail era presente una firma in calce
				 * e quindi sostituisco tale corpo con il body impostato nel modello
				 */
				
				GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
				// Pongo un extraparam per il valore della nuova firma
				corpoMailDataSource.performCustomOperation("findSignature", new Record(oldValues), new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record record = response.getData()[0];
							
							String firmaInCalce = record.getAttribute("bodyHtml");
							
							String oldBodyHtml = ((String)oldValues.get("bodyHtml"));
							String newBodyHtml = ((String)newValues.get("bodyHtml"));
							
							newBodyHtml = oldBodyHtml.replace(firmaInCalce, newBodyHtml);
							
							
							/*
							 * Setto come nuovo corpo quello in cui la firma in calce è stata 
							 * sostituita con il body del modello 
							 */
							values.setAttribute("bodyHtml", newBodyHtml);
							
							//Inserisco i nuovi valori all'interno dei rispettivi campi
							editNewRecord(values.toMap());
						}
					}
				}, new DSRequest());
			} else {
				/*
				 * Non è presente una firma in calce.
				 * Aggiungo quanto presente nel modello prima del body presente nella mail
				 */
				values.setAttribute("bodyHtml", newValues.get("bodyHtml") + "<br/><br/>" + oldValues.get("bodyHtml"));
				
				//Inserisco i nuovi valori all'interno dei rispettivi campi
				editNewRecord(values.toMap());
			}
		} else {
			values.setAttribute("bodyHtml", oldValues.get("bodyHtml"));
			editNewRecord(values.toMap());
		}
	}
	
	protected void createModelliDatasource(String nomeEntita) {
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", "risposta.modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0"); //TODO RIMUOVERE?
	}

	private void createSelectFirmaInCalce() {
		
		createFirmeDatasource();

		//Creo il pulsante per selezionare la firma predefinita
		firmaPredefinitaButton = new ToolStripButton("", "menu/firma_email.png");
		firmaPredefinitaButton.setPrompt(I18NUtil.getMessages().firme_in_calce_firmaPredefinita_title());
		/*
		 * L'immagine di background normalmente è già impostata.
		 * Ne setto una vuota così da non avere lo stile precedente ma per vedere in trasparenza 
		 * lo sfondo della finestra
		 */
//		firmaPredefinitaButton.setBackgroundImage("");
		firmaPredefinitaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				replaceSignature();
			}
			
			private void replaceSignature() {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
					}else{
						// Ottengo la firma impostata come predefinita
						final String nomeFirmaPredefinita = AurigaLayout.getFirmaEmailPredefinita();
						String firmaPredefinita = nomeFirmaPredefinita != null && !nomeFirmaPredefinita.equals("") ? AurigaLayout.getFirmaEmailHtml(nomeFirmaPredefinita) : null;
		
						if (firmaPredefinita != null && !firmaPredefinita.equals("")) {
							// Prelevo i valori del form
							Map formValues = getMapValues();
		
							GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
							// Pongo un extraparam per il valore della nuova firma
							corpoMailDataSource.extraparam.put("newSignature", firmaPredefinita);
							corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										editNewRecord(record.toMap());
										firmaInCalceSelectItem.setValue(nomeFirmaPredefinita);
									}
								}
							}, new DSRequest());
						}
						else{
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_firmaPredefinitaNonPresente_warningMessage(), "", MessageType.WARNING));
						}
					}
				}
			}
		});
		
		// Creo la select
		firmaInCalceSelectItem = new SelectItem("firmaInCalce");
		firmaInCalceSelectItem.setValueField("prefName");
		firmaInCalceSelectItem.setDisplayField("prefName");

		firmaInCalceSelectItem.setTitle("<b>" + I18NUtil.getMessages().firme_in_calce_modelliSelectItem_title() + "</b>");
		firmaInCalceSelectItem.setWrapTitle(false); // In questo modo il titolo non viene mandato a capo
		firmaInCalceSelectItem.setCachePickListResults(false);
		firmaInCalceSelectItem.setRedrawOnChange(true);
		firmaInCalceSelectItem.setShowOptionsFromDataSource(true);
		firmaInCalceSelectItem.setOptionDataSource(firmeDS);
		firmaInCalceSelectItem.setAllowEmptyValue(true);

		firmaInCalcePickListProperties = new ListGrid();
		firmaInCalcePickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		firmaInCalcePickListProperties.setShowHeader(false);
		// apply the selected preference from the SelectItem
		firmaInCalcePickListProperties.addCellClickHandler(new CellClickHandler() {

			@Override
			public void onCellClick(CellClickEvent event) {

				if(_layout != null && _layout.getForm() != null){
					if(_layout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
					}else{
						final String preferenceName = event.getRecord().getAttribute("prefName");
						if (preferenceName != null && !"".equals(preferenceName)) {
							AdvancedCriteria criteria = new AdvancedCriteria();
							criteria.addCriteria("prefName", OperatorId.EQUALS, preferenceName);
							firmeDS.fetchData(criteria, new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									Record[] data = response.getData();
									if (data.length != 0) {
										Record record = data[0];
										
										//Prelevo i valori del form
										Map formValues = getMapValues();
										
										GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
										//Pongo un extraparam per il valore della nuova firma
										corpoMailDataSource.extraparam.put("newSignature", record.getAttributeAsString("value"));
										corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Record record = response.getData()[0];
													
													editNewRecord(record.toMap());
												}
											}
										}, new DSRequest());									
									}
								}
							});
						}
					}
				}
			}
		});

		// Inserisco la picklist
		firmaInCalceSelectItem.setPickListProperties(firmaInCalcePickListProperties);
	}

	private void createFirmeDatasource() {
		firmeDS = UserInterfaceFactory.getPreferenceDataSource();
		firmeDS.addParam("prefKey", Layout.getConfiguredPrefKeyPrefix() + "signature.email");
	}
	
	private Map getMapValues() {
		return vm.getValues();
	}
	
	private void editNewRecord(Map map) {
		
		_layout.getForm().filtroPresenteFromModello(map);
		vm.editRecord(new Record(map));
		for(DynamicForm form : vm.getMembers()) {
			form.markForRedraw();
		}
	}
}
