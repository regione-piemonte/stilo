/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.LinkItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

public class CaselleEmailDetail extends CustomDetail {
	
	protected CaselleEmailDetail instance;
	
	protected DynamicForm hiddenForm;
	protected HiddenItem idCasellaItem;	
	protected HiddenItem indirizzoEmailItem;	
	protected DynamicForm parametriForm;
	protected DynamicForm buttonForm;
	protected ImgButtonItem testInvioButton;
	protected ImgButtonItem testScaricoButton;

	public CaselleEmailDetail(String nomeEntita) {
		this(nomeEntita, null);
	}
		
	public CaselleEmailDetail(String nomeEntita, Record record) {
		super(nomeEntita);
		
		instance = this;		
		
		hiddenForm = new DynamicForm();
		hiddenForm.setValuesManager(vm);
		hiddenForm.setWidth("100%");  
		hiddenForm.setHeight("5");  		
		
		idCasellaItem = new HiddenItem("idCasella");		
		indirizzoEmailItem = new HiddenItem("indirizzoEmail");		
		
		hiddenForm.setItems(
			idCasellaItem,
			indirizzoEmailItem
		);
		
		// FORM PARAMETRI  
		parametriForm = new DynamicForm();
		parametriForm.setValuesManager(vm);
		parametriForm.setWidth("100%");  
		parametriForm.setHeight("5");  
		parametriForm.setPadding(5);
		parametriForm.setNumCols(6);
		parametriForm.setColWidths(500, 1, 1, 1, "*", "*");
		parametriForm.setWrapItemTitles(true);
		
		if(record != null) {
			List<FormItem> parametriItems = new ArrayList<FormItem>();
		
			RecordList parametriCasella = record.getAttributeAsRecordList("parametriCasella");
			
			for(int i = 0; i < parametriCasella.getLength(); i++) {
				Record param = parametriCasella.get(i);							
				parametriItems.add(buildParametroCasellaFormItem(param));
			}
			parametriForm.setFields(parametriItems.toArray(new FormItem[0]));						
		}
		
		// FORM BOTTONI
		buttonForm = new DynamicForm();
		buttonForm.setValuesManager(vm);
		buttonForm.setWidth("100%");  
		buttonForm.setHeight("5");  
		buttonForm.setPadding(5);
		buttonForm.setNumCols(3);
		buttonForm.setColWidths(1, 1, 1);
		buttonForm.setWrapItemTitles(true);
		
		// Bottone test invio
		testInvioButton = new ImgButtonItem("testInvioButton", "postaElettronica/iconaMicroCategoria/inoltro_mail.png", I18NUtil.getMessages().caselleEmail_detail_testInvioButton_prompt());
		testInvioButton.setAlwaysEnabled(true);
		testInvioButton.setColSpan(1);
		testInvioButton.setIconWidth(32);
		testInvioButton.setIconHeight(32);
		testInvioButton.setIconVAlign(VerticalAlignment.BOTTOM);
		testInvioButton.setAlign(Alignment.LEFT);
		testInvioButton.setWidth(32);
		testInvioButton.setRedrawOnChange(true);
		testInvioButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (hiddenForm.getValue("idCasella")!=null && !hiddenForm.getValueAsString("idCasella").equalsIgnoreCase("") && hiddenForm.getValue("indirizzoEmail")!=null && !hiddenForm.getValueAsString("indirizzoEmail").equalsIgnoreCase(""));
			}
		});
		testInvioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				testInvioButtonClick();
			}
		});
		
		// Bottone test scarico
		testScaricoButton = new ImgButtonItem("testScaricoButton", "postaElettronica/iconaMicroCategoria/notifica_lettura.png", I18NUtil.getMessages().caselleEmail_detail_testScaricoButton_prompt());
		testScaricoButton.setAlwaysEnabled(true);
		testScaricoButton.setColSpan(1);
		testScaricoButton.setIconWidth(32);
		testScaricoButton.setIconHeight(32);
		testScaricoButton.setIconVAlign(VerticalAlignment.BOTTOM);
		testScaricoButton.setAlign(Alignment.LEFT);
		testScaricoButton.setWidth(32);
		testScaricoButton.setRedrawOnChange(true);
		testScaricoButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return (hiddenForm.getValue("idCasella")!=null && !hiddenForm.getValueAsString("idCasella").equalsIgnoreCase("") && hiddenForm.getValue("indirizzoEmail")!=null && !hiddenForm.getValueAsString("indirizzoEmail").equalsIgnoreCase(""));
			}
		});
		testScaricoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				testScaricoButtonClick();
			}
		});
		
		SpacerItem spacer = new SpacerItem();
		spacer.setWidth(400);
		spacer.setStartRow(true);
		
		buttonForm.setFields(spacer, testInvioButton, testScaricoButton);	
		
		setMembers(hiddenForm, parametriForm, buttonForm);	
	}

	public FormItem buildParametroCasellaFormItem(Record param) {
		FormItem item = null;

		// Se flgShow = 1 mostro l'item 
		if(param.getAttribute("flgShow") != null && param.getAttribute("flgShow").equalsIgnoreCase("1")) {
			if(param.getAttribute("tipo") != null) {
				if(param.getAttribute("tipo").equalsIgnoreCase("string")) {
					item = new TextItem(param.getAttribute("nome"), param.getAttribute("titolo"));
				} else if(param.getAttribute("tipo").equalsIgnoreCase("int")) {
					item = new NumericItem(param.getAttribute("nome"), param.getAttribute("titolo"), false);
				} else if(param.getAttribute("tipo").equalsIgnoreCase("enum")) {
					item = new SelectItem(param.getAttribute("nome"), param.getAttribute("titolo"));
					StringSplitterClient st = new StringSplitterClient(param.getAttribute("valueMap"), "|*|");
					item.setValueMap(st.getTokens());
				} else if(param.getAttribute("tipo").equalsIgnoreCase("password")) {
					item = new PasswordItem(param.getAttribute("nome"), param.getAttribute("titolo"));
					item.setWidth(250);							
				} else if(param.getAttribute("tipo").equalsIgnoreCase("path")) {
					item = new LinkItem(param.getAttribute("nome"));
					item.setTitle(param.getAttribute("titolo"));
					item.setWidth(250);							
				}
			}
		}
		// Se flgShow = 0 mostro un item HiddenItem
		else{
			item = new HiddenItem(param.getAttribute("nome"));
		}
		
		if(item == null) {
			item = new TextItem(param.getAttribute("nome"), param.getAttribute("titolo"));
		}
		
		item.setRequired(param.getAttribute("flgObblig") != null && param.getAttribute("flgObblig").equals("1"));
		if(item instanceof SelectItem) {
			((SelectItem)item).setAllowEmptyValue(!item.getRequired());				
		}
		
		item.setStartRow(true);
		return item;
	}
	
	@Override
	public void editRecord(Record record) {
		
		Record values = new Record();	
		values.setAttribute("idCasella", record.getAttribute("idCasella"));
		values.setAttribute("indirizzoEmail", record.getAttribute("indirizzoEmail"));
		
		RecordList parametriCasella = record.getAttributeAsRecordList("parametriCasella");
		
		if(parametriCasella != null) {
			for(int i = 0; i < parametriCasella.getLength(); i++) {
				Record param = parametriCasella.get(i);			
				values.setAttribute(param.getAttribute("nome"), param.getAttribute("valore"));
			}
		}
		// ATTENZIONE: alcuni dei nomi dei campi a maschera contengono il punto e l'editRecord non 
		// funziona correttamente, perciò devo settare manualmente i valori sugli item 
//		super.editRecord(values);
		for(DynamicForm form : vm.getMembers()) {
			for(FormItem item : form.getFields()) {
				item.setValue(values.getAttribute(item.getName()));
			}
		}		
	}
	
	public Record getRecordToSave() {		
		Record record = new Record();	
		record.setAttribute("idCasella", hiddenForm.getValue("idCasella"));
		record.setAttribute("indirizzoEmail", hiddenForm.getValue("indirizzoEmail"));
		RecordList parametriCasella = new RecordList();		
		for(FormItem item : parametriForm.getFields()) {
			Record param = new Record();
			param.setAttribute("nome", item.getName());
			param.setAttribute("valore", parametriForm.getValue(item.getName()));
			
			// Salvo solo quello visibili
			if ((item instanceof HiddenItem) == false) {
				parametriCasella.add(param);
			}
		}
		record.setAttribute("parametriCasella", parametriCasella);
		return record;
	}
	
	private void testInvioButtonClick() {
		
		if ( parametriForm.getFields()!=null && parametriForm.getFields().length>0 ) {
			Layout.showWaitPopup("Attendere...");		
			try {
					// Estraggo i valori dei parametri :
					// mail.username
		        	// mail.password
					// mail.smtp.host
			        // mail.smtp.port 
			        // mail.smtp.starttls.enable
			        // mail.smtp.auth
				    // mail.smtp.ssl.enable
				    // mail.smtp.socketFactory.fallback
				    // mail.smtp.socketFactory.class
				
			        
					String username = "";
					String password = "";
				    String host = "";
					String port = "";
					String starttls = "";
					String auth = "";
					String sslEnable = "";
					String smtpSocketFactoryFallback = "";
					String smtpSocketFactoryClass = "";
					
					for(FormItem item : parametriForm.getFields()) {	
						String nomeParametro = item.getName();
						String valParametro  = "";
						if (parametriForm.getValue(item.getName())!=null)
						    valParametro  = parametriForm.getValue(item.getName()).toString();
						
						switch (nomeParametro) {
						 	case "mail.username": {
					        	username = valParametro;
					            break;
					        }
					        case "mail.password": {
					        	password = valParametro;
					            break;
					        }
					        
					        case "mail.smtp.host": {
					        	host = valParametro;
					            break;
					        }		        
					        case "mail.smtp.port": {
					        	port = valParametro;
					            break;
					        }
					        case "mail.smtp.starttls.enable": {
					        	starttls = valParametro;
					            break;
					        }		       
					        case "mail.smtp.auth": {
					        	auth = valParametro;
					            break;
					        }
					       
					        case "mail.smtp.ssl.enable": {
					        	sslEnable = valParametro;
					            break;
					        }
					        
					        case "mail.smtp.socketFactory.fallback": {
					        	smtpSocketFactoryFallback = valParametro;
					            break;
					        }
					        
					        case "mail.smtp.socketFactory.class": {
					        	smtpSocketFactoryClass = valParametro;
					            break;
					        }
					        
						}
					}
								
					// Effettuo il test
					Record record = new Record();	
					record.setAttribute("username", username);
					record.setAttribute("password", password);
					record.setAttribute("host", host);
					record.setAttribute("port", port);
					record.setAttribute("starttls", starttls);
					record.setAttribute("auth", auth);
					record.setAttribute("sslEnable", sslEnable);
					record.setAttribute("smtpSocketFactoryFallback", smtpSocketFactoryFallback);
					record.setAttribute("smtpSocketFactoryClass", smtpSocketFactoryClass);
					 
					final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("CaselleEmailDatasource", "idCasella", FieldType.TEXT);
					lGWTRestDataSource.executecustom("testConnectSmtp", record, new DSCallback() {					
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {								
								Record recordResponse = response.getData()[0];
								boolean authenticationOK = recordResponse.getAttributeAsBoolean("authenticationOK");
								if (authenticationOK){
									Layout.addMessage(new MessageBean("Configurazione invio corretta: test di invio avvenuto con successo.", "", MessageType.INFO)  );	
								}
								else{
									String msgError = recordResponse.getAttributeAsString("msgError");
									Layout.addMessage(new MessageBean("Test fallito : " + msgError, "", MessageType.ERROR)  );
								}
								Layout.hideWaitPopup();
							} else {
								Layout.hideWaitPopup();
							}
						}
					});	
			} 
			catch(Exception e) {
				Layout.hideWaitPopup();
			}
		}
	}
	
	private void testScaricoButtonClick() {
		
		if ( parametriForm.getFields()!=null && parametriForm.getFields().length>0 ) {			
			Layout.showWaitPopup("Attendere...");		
			try {
					// Estraggo i valori dei parametri :  
			        // mail.imap.host
			        // mail.imap.port
			        // mail.username, 
			        // mail.password			        
			        String host = "";
					String port = "";
					String username = "";
					String password = "";
				
					for(FormItem item : parametriForm.getFields()) {	
						String nomeParametro = item.getName();
						String valParametro  = "";
						if (parametriForm.getValue(item.getName())!=null)
						    valParametro  = parametriForm.getValue(item.getName()).toString();
						
						switch (nomeParametro) {
					        case "mail.imap.host": {
					        	host = valParametro;
					            break;
					        }		        
					        case "mail.imap.port": {
					        	port = valParametro;
					            break;
					        }
					        case "mail.username": {
					        	username = valParametro;
					            break;
					        }
					        case "mail.password": {
					        	password = valParametro;
					            break;
					        }
						}
					}
								
					// Effettuo il test
					Record record = new Record();	
					record.setAttribute("host", host);
					record.setAttribute("port", port);
					record.setAttribute("username", username);
					record.setAttribute("password", password);
					
					final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("CaselleEmailDatasource", "idCasella", FieldType.TEXT);
					lGWTRestDataSource.executecustom("testConnectInBox", record, new DSCallback() {					
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Record recordResponse = response.getData()[0];
								boolean authenticationOK = recordResponse.getAttributeAsBoolean("authenticationOK");
								if (authenticationOK){
									Integer messageCountInBox = recordResponse.getAttributeAsInt("messageCountInBox");
									Layout.addMessage(new MessageBean("Configurazione scarico corretta: test di connessione per scarico avvenuto con successo.Nella INBOX ci sono " + String.valueOf(messageCountInBox) + " messaggi.", "", MessageType.INFO)  );
								}
								else{
									String msgError = recordResponse.getAttributeAsString("msgError");
									Layout.addMessage(new MessageBean("Test fallito : " + msgError, "", MessageType.ERROR)  );
								}
								Layout.hideWaitPopup();
							} else {
								Layout.hideWaitPopup();
							}
						}
					});	
			} 
			catch(Exception e) {
				Layout.hideWaitPopup();
			}
		}
	}
}