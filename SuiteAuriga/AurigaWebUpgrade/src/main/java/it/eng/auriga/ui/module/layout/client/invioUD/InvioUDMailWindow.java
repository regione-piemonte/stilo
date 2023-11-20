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
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.invioMail.GestioneModelliButton;
import it.eng.auriga.ui.module.layout.client.invioMail.TipologiaMail;
import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class InvioUDMailWindow extends ModalWindow {

	private InvioUDMailWindow _window;
	
	protected ValuesManager vm;
	
	private InvioUDMailLayout portletLayout;
	private String tipoMail;
	
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
	
	public InvioUDMailWindow(String pTipoMail){
		this(pTipoMail, null);
	}
	
	public InvioUDMailWindow(String pTipoMail, DSCallback callback){
		this(pTipoMail, false, callback);
	}
		
	public InvioUDMailWindow(String pTipoMail, Boolean invioMailFromAtti, DSCallback callback){
		
		super("invioudmail" + pTipoMail, true);
		
		this.tipoMail = pTipoMail;
		
		_window = this;
		
		String icona = pTipoMail; //Non ho voluto Modificare tipoMail in caso serve da altre parti come parametro 'PEC'
		setIcon("mail/" + icona + ".png");
		
		if (tipoMail.equals("PEC")){
			setTitle(I18NUtil.getMessages().invioudmail_detail_mailinterop_title());
			icona = "INTEROP";
		} else
			setTitle(I18NUtil.getMessages().invioudmail_detail_mailpeo_title());
		
		removeItem();
		createMainToolstrip();
		createMainLayout(invioMailFromAtti, callback);
		setBody(portletLayout);
		
		vm = new ValuesManager();
		setValuesManager(vm);
	}

	private void removeItem() {
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
	}

	public void loadMail(final Record lRecord){
		
		if(AurigaLayout.getParametroDB("SHOW_FIRME_IN_CALCE").equals("true")){
			//Se sono nella modalità per cui devo visualizzare le firme in calce
			
			Record buffRecord = new Record();
			buffRecord.setAttribute("bodyText", lRecord.getAttribute("bodyText"));
			buffRecord.setAttribute("bodyHtml", lRecord.getAttribute("bodyHtml"));
			
			//Chiamata al datasource per l'aggiunta della firma in calce
			GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
			//Pongo un extraparam per il valore della nuova firma
			corpoMailDataSource.extraparam.put("newSignature", AurigaLayout.getHtmlSignature("nuovo"));
			corpoMailDataSource.extraparam.put("modalita", "NUOVO_UD");
			corpoMailDataSource.performCustomOperation("addSignature", buffRecord, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record recordBody = response.getData()[0];
						lRecord.setAttribute("bodyHtml", recordBody.getAttribute("bodyHtml"));
						//Imposto il valore della select con quella che è stata pre-impostata come automatica
						if(AurigaLayout.getFirmaEmailAutoNuova() != null && !AurigaLayout.getFirmaEmailAutoNuova().equals("")){
							firmaInCalceSelectItem.setValue(AurigaLayout.getFirmaEmailAutoNuova());
						}

						portletLayout.getForm().caricaMail(lRecord);
					}
				}
			}, new DSRequest());
		} else {
			if(AurigaLayout.getFirmaEmailHtml() != null) {
				lRecord.setAttribute("body", lRecord.getAttribute("body") + "<br/><br/>" + AurigaLayout.getFirmaEmailHtml());
			} else {
				lRecord.setAttribute("body", lRecord.getAttribute("body") );
			}
			//Se sono in modalità per cui devo visualizzare la firma normale
			portletLayout.getForm().caricaMail(lRecord);
		}
	}

	public String getTipoMail() {
		return tipoMail;
	}
	
	protected void createMainLayout(Boolean invioMailFromAtti, DSCallback callback) {

		portletLayout = new InvioUDMailLayout(this, invioMailFromAtti, callback);
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.addMember(mainToolStrip, 0);
	}

	private void createMainToolstrip() {

		if(tipoMail.equals("PEO")){
			createModelliSelectItem();
		}
		
		if(AurigaLayout.getParametroDB("SHOW_FIRME_IN_CALCE").equals("true")) {
			//Creo la select relativa alla firma in calce
			createSelectFirmaInCalce();
		}

		// Creo la mainToolstrip e aggiungo le select impostate
		mainToolStrip = new ToolStrip();
		mainToolStrip.setBackgroundColor("transparent");
		mainToolStrip.setBackgroundImage("blank.png");
		mainToolStrip.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		mainToolStrip.setBorder("1px solid #878E96"); // La stessa colorazione dei bordi dei componenti
		mainToolStrip.setWidth100();
		mainToolStrip.setHeight(30);

//		mainToolStrip.addFill(); // Per spostare a destra la nuova select
			
		if(tipoMail.equals("PEO")){
			
			mainToolStrip.addFormItem(modelliSelectItem);
			mainToolStrip.addButton(new GestioneModelliButton(TipologiaMail.INVIO_UD, modelliSelectItem));
		}

		if(AurigaLayout.getParametroDB("SHOW_FIRME_IN_CALCE").equals("true")){
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

				if(isAbilToRemoveModello(record)) {
					return "<img src=\"images/buttons/remove.png\" height=\"16\" width=\"16\" align=MIDDLE/>";
				}
				return null;
				
			}
		});
		modelliRemoveField.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(final RecordClickEvent event) {
				
				if (isAbilToRemoveModello(event.getRecord())) {
					SC.ask(I18NUtil.getMessages().gestioneModelli_cancellazione_ask(), new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
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
		// apply the selected preference from the SelectItem
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
								final Record values = new Record(JSON.decode(record.getAttribute("value")));
								final String mittentePref = values.getAttributeAsString("mittente");
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
												values.setAttribute("mittente", "");
											}
											Map oldValues = getMapValues();
											// Alcune delle informazioni nascoste nella maschera di invio UD vanno mantenute quando carico i valori dal modello, altrimenti vengono sbiancate e poi va' in errore l'invio
											values.setAttribute("invioMailFromAtti", oldValues.get("invioMailFromAtti"));
											values.setAttribute("confermaLetturaShow", oldValues.get("confermaLetturaShow"));
											values.setAttribute("segnaturaPresente", oldValues.get("segnaturaPresente"));
											values.setAttribute("idUD", oldValues.get("idUD"));
											values.setAttribute("flgTipoProv", oldValues.get("flgTipoProv"));
											values.setAttribute("tipoMail", oldValues.get("tipoMail"));
											values.setAttribute("idMailPartenza", oldValues.get("idMailPartenza"));
											ArrayList<Map> lArrayListAttach = (ArrayList<Map>) oldValues.get("attach");
											if (lArrayListAttach != null) {
												RecordList lRecordListAttach = new RecordList();
												for (Map lMapAttach : lArrayListAttach) {
													Record lRecordAttach = new Record(lMapAttach);
													lRecordListAttach.add(lRecordAttach);
												}
												values.setAttribute("attach", lRecordListAttach);
											}
											editNewRecord(values.toMap());
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
		
		//Mittente
		if(newValues.get("mittente") != null && !"".equals(newValues.get("mittente"))){
			/*
			 * Se il mittente nel modello è impostato si deve utilizzare questo,
			 * altrimenti si deve usare quello presente nella mail di partenza
			 */
			values.setAttribute("mittente", newValues.get("mittente"));
		} else{
			values.setAttribute("mittente", oldValues.get("mittente"));
		}
		/*
		 * Conferma lettura
		 */
		if(newValues.get("confermaLettura") != null && !"".equals(newValues.get("confermaLettura"))){
			/*
			 * Se il flag di conferma lettura nel modello è impostato si deve utilizzare questo,
			 * altrimenti si deve usare quello presente nella mail di partenza
			 */
			values.setAttribute("confermaLettura", newValues.get("confermaLettura"));
		} else{
			values.setAttribute("confermaLettura", oldValues.get("confermaLettura"));
		}
		
		//Body della mail
		if(newValues.get("bodyHtml") != null && !"".equals(newValues.get("bodyHtml"))){
			/*
			 * In questo caso è presente un corpo nel modello che si sta
			 * cercando di utilizzare.
			 * Deve essere concatenato PRIMA del body già presente nella mail,
			 * ovvero PRIMA del body standard impostato nella stessa
			 */
			if(((String)oldValues.get("bodyHtml")).contains("<!-- Inizio firmaInCalce -->")) {
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
			}else{
				/*
				 * Non è presente una firma in calce.
				 * Aggiungo quanto presente nel modello prima del body presente nella mail
				 */
				values.setAttribute("bodyHtml", oldValues.get("bodyHtml") + "<br/><br/>" + newValues.get("bodyHtml"));
				
				//Inserisco i nuovi valori all'interno dei rispettivi campi
				editNewRecord(values.toMap());
			}
		}
	}
	
	protected void createModelliDatasource(String nomeEntita) {
		modelliDS = new GWTRestDataSource("ModelliDataSource", "key", FieldType.TEXT);
		modelliDS.addParam("prefKey", "invio_documento.modelli");
		modelliDS.addParam("isAbilToPublic", Layout.isPrivilegioAttivo("EML/MPB") ? "1" : "0"); 	
	}

	private void createSelectFirmaInCalce() {
		createFirmeDatasource();

		//Creo il pulsante per selezionare la firma predefinita
		firmaPredefinitaButton = new ToolStripButton("", "menu/firma_email.png");
		firmaPredefinitaButton.setPrompt(I18NUtil.getMessages().firme_in_calce_firmaPredefinita_title());		
		firmaPredefinitaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				replaceSignature();
			}
			
			private void replaceSignature() {

				if(portletLayout != null && portletLayout.getForm() != null) {
					if(portletLayout.getForm().getStyleText().equals("text")) {
						//Si sta considerando la modalità text
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
					} else {
						// Ottengo la firma impostata come predefinita
						final String nomeFirmaPredefinita = AurigaLayout.getFirmaEmailPredefinita();
						String firmaPredefinita = nomeFirmaPredefinita != null && !nomeFirmaPredefinita.equals("") ? AurigaLayout.getFirmaEmailHtml(nomeFirmaPredefinita) : null;
		
						if (firmaPredefinita != null && !firmaPredefinita.equals("")) {
							
							//Prelevo i valori delle variabili all'interno del form
							Map formValues = getMapValues();
							
							formValues.put("bodyHtml", formValues.get("bodyHtml"));
							formValues.put("bodyText", formValues.get("bodyText"));
							
							GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
							// Pongo un extraparam per il valore della nuova firma
							corpoMailDataSource.extraparam.put("newSignature", firmaPredefinita);
							corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
							corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
		
								@Override
								public void execute(DSResponse response, Object rawData, DSRequest request) {
									if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
										Record record = response.getData()[0];
										//Aggiorno il corpo html per cui è stata sostituita la firma
										refreshBody(record);
										firmaInCalceSelectItem.setValue(nomeFirmaPredefinita);
									}
								}
							}, new DSRequest());
						} else {
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_firmaPredefinitaNonPresente_warningMessage(), "", MessageType.WARNING));
						}
					}
				}
			}
		});
		
		//Creo la select
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
				
				if(portletLayout != null && portletLayout.getForm() != null){
					if(portletLayout.getForm().getStyleText().equals("text")){
						//Si sta considerando la modalità text
						
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().firme_in_calce_modificaInTestoSemplice_warningMessage(), "", MessageType.WARNING));
						
						}else{
							final String preferenceName = event.getRecord().getAttribute("prefName");
							if (preferenceName != null && !"".equals(preferenceName)) {
								
								//La firma che è stata selezionata e che deve essere inserita al posto di quella attualmente presente
								String firmaHtmlSelezionata = AurigaLayout.getFirmeEmailHtml().get(preferenceName);
							
									//Prelevo i valori delle variabili all'interno del form
									Map formValues = getMapValues();
									
									//ATTENZIONE: Perchè all'interno del Datasource viene prelevato il valore del bodyHtml
									formValues.put("bodyHtml", formValues.get("bodyHtml"));
									formValues.put("bodyText", formValues.get("bodyText"));
									
									GWTRestDataSource corpoMailDataSource = new GWTRestDataSource("CorpoMailDataSource");
									//Pongo un extraparam per il valore della nuova firma
									corpoMailDataSource.extraparam.put("newSignature", firmaHtmlSelezionata);
									corpoMailDataSource.extraparam.put("modalita", "nuovaMail");
									corpoMailDataSource.performCustomOperation("replaceSignature", new Record(formValues), new DSCallback() {
				
										@Override
										public void execute(DSResponse response, Object rawData, DSRequest request) {
											if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
												Record record = response.getData()[0];
												
												//Aggiorno il corpo html per cui è stata sostituita la firma
												refreshBody(record);
											}
										}
									}, new DSRequest());
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
		return portletLayout.getForm().getMapValues().getValues();
	}
	
	private void refreshBody(Record record) {
		portletLayout.getForm().refreshBody(record.getAttributeAsString("bodyHtml"));
	}
	
	private void editNewRecord(Map map) {
		portletLayout.getForm().refreshForm(map);
	}
	
	@Override
	protected void onDestroy() {
		if (vm != null) {
			try {
				vm.destroy();
			} catch (Exception e) {
			}
		}
		if(modelliDS != null) {
			modelliDS.destroy();
		}
		super.onDestroy();
	}
	
	public boolean isAbilToRemoveModello(Record record) {
		return (record.getAttribute("key") != null && !"".equals(record.getAttributeAsString("key"))) && 
				   (record.getAttributeAsBoolean("flgAbilDel") != null && record.getAttributeAsBoolean("flgAbilDel"));
	}
	
}