/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.i18n.client.Dictionary;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;

public class GestioneUtentiLayout extends CustomLayout{

	protected DetailToolStripButton nuovoUtenteButton;
	protected DetailToolStripButton nuovoUtenteCopiaButton;
	protected DetailToolStripButton sbloccaUtenteButton;
	
	public GestioneUtentiLayout() {
		this("gestioneutenti");
	}
	
	public GestioneUtentiLayout(String nomeEntita){
		
		super(nomeEntita,
			  new GWTRestDataSource("AurigaGestioneUtentiDataSource", "idUser", FieldType.TEXT),
			  createFilter(nomeEntita), 
			  new GestioneUtentiList(nomeEntita),
			  new GestioneUtentiDetail(nomeEntita) 
		    );
		
		multiselectButton.hide();	
		
		if (!isAbilToIns()) {
			newButton.hide();
		}
	}
	
	private static GestioneUtentiFilter createFilter(String nomeEntita){
		Map<String, String> extraParam = new HashMap<String, String>(); 
		boolean showFilterEscludiUtentiNoDominio = Layout.isPrivilegioAttivo("SIC/UTE");
		extraParam.put("showFilterEscludiUtentiNoDominio", Boolean.toString(showFilterEscludiUtentiNoDominio));		
		return new GestioneUtentiFilter(nomeEntita, extraParam);
	}
	
	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("SIC/UT;I");
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("SIC/UT;M");
	}
	
	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("SIC/UT;FC");
	}
	
	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		return !flgDiSistema && isAbilToMod();
	}
	
	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		return flgValido && !flgDiSistema && isAbilToDel();
	}
	
	@Override
	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("cognome") + " " + record.getAttribute("nome");	
	}
	
	@Override
	public String getNewDetailTitle() {
		return "Nuovo utente";
	}
	
	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().gestioneutenti_detail_view_title(getTipoEstremiRecord(record));
	}
	
	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().gestioneutenti_detail_edit_title(getTipoEstremiRecord(record));
	}
	
	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
		nuovoUtenteButton.hide();
		nuovoUtenteCopiaButton.hide();
		sbloccaUtenteButton.hide();
	}
	
	@Override
	public void viewMode() {
		super.viewMode();				
		Record record = new Record(detail.getValuesManager().getValues());
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");				
		if(isRecordAbilToDel(flgValido, flgDiSistema)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(flgDiSistema)) {
			editButton.show();
		} else{
			editButton.hide();
		}				
		if(isAbilToIns()) {
			nuovoUtenteButton.show();
			nuovoUtenteCopiaButton.show();
		}else {
			nuovoUtenteButton.hide();
			nuovoUtenteCopiaButton.hide();
		}		
		if(isAbilToSbloccaUtente()) {
			sbloccaUtenteButton.show();
		}else{
			sbloccaUtenteButton.hide();
		}
			
		altreOpButton.hide();		
	}
	
	
	@Override
	public void onSaveButtonClick() {
		((GWTRestDataSource)(getDetail().getDataSource())).extraparam.put("isGestioneUtenteInterno",   Boolean.toString(isGestioneUtenteInterno())); 
		((GWTRestDataSource)(getDetail().getDataSource())).extraparam.put("isGestioneLogo",   Boolean.toString(isGestioneLogo()));
		((GWTRestDataSource)(getDetail().getDataSource())).extraparam.put("isGestioneLingua", Boolean.toString(isGestioneLingua()));
		((GWTRestDataSource)(getDetail().getDataSource())).extraparam.put("isGestioneClienti", Boolean.toString(isGestioneClienti()));
		((GWTRestDataSource)(getDetail().getDataSource())).extraparam.put("isGestioneSocieta", Boolean.toString(isGestioneSocieta()));
		((GWTRestDataSource)(getDetail().getDataSource())).extraparam.put("isVisibilitaEmailCaselleAssociateUO", Boolean.toString(isVisibilitaEmailCaselleAssociateUO()));
		((GWTRestDataSource)(getDetail().getDataSource())).extraparam.put("isDisattivaNotifDocDaPrendereInCarico", Boolean.toString(isDisattivaNotifDocDaPrendereInCarico()));
		
		
		final String leditMode = this.mode;		
		final Record record = detail.getRecordToSave();
		if(detail.validate()) {
			DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();			
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			DSCallback callback = new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record savedRecord = response.getData()[0];
						if(savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
							try {
								list.manageLoadDetail(savedRecord, detail.getRecordNum(), new DSCallback() {							
									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										viewMode();		
										Layout.hideWaitPopup();
										Layout.addMessage(new MessageBean(I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "", MessageType.INFO));
										
										if  (isInviaEmailPwdCreaUtente()  )	 {
											if (leditMode.equals("new")) {
												String username =  savedRecord.getAttribute("username");
												String email    =  savedRecord.getAttribute("email");
												String message  = I18NUtil.getMessages().creaPasswordEsitoOK_message();
												// Effettuo la creazione della password 
												creaPassword(username,email, message);	
											}
										}																														
									}
								});	
							} catch(Exception e) {
								Layout.hideWaitPopup();
							}
						} else {
							detail.getValuesManager().setValue("flgIgnoreWarning", "1");
							Layout.hideWaitPopup();							
						}
						if(!fullScreenDetail) {
							reloadListAndSetCurrentRecord(savedRecord);
						}	
					} else {
						Layout.hideWaitPopup();
					}
				}
			};
			try {
				if((saveOperationType!=null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idUser")==null || record.getAttribute("idUser").equals("")) {
					detail.getDataSource().addData(record, callback);
				} else {
					detail.getDataSource().updateData(record, callback);
				}
			} catch(Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
		nuovoUtenteButton.hide();
		nuovoUtenteCopiaButton.hide();
		
		if(isAbilToSbloccaUtente()) {
			sbloccaUtenteButton.show();
		}else{
			sbloccaUtenteButton.hide();
		}
		
	}
	
	@Override
	public List<ToolStripButton> getDetailButtons() {
		List<ToolStripButton> detailButtons = new ArrayList<ToolStripButton>();
		for (ToolStripButton lToolStripButton : getCustomDetailButtons()) {
			detailButtons.add(lToolStripButton);
		}
		
		if(nuovoUtenteButton == null) {
			nuovoUtenteButton = new DetailToolStripButton("Nuovo utente","buttons/new.png");
			nuovoUtenteButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					manageNewClick();
				}
			});
		}
		

		if(nuovoUtenteCopiaButton == null) {
			nuovoUtenteCopiaButton = new DetailToolStripButton("Nuovo come copia","copia.png");
			nuovoUtenteCopiaButton.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					manageNewComeCopiaClick();
				}
			});
		}
		
		if(sbloccaUtenteButton == null) {
			sbloccaUtenteButton = new DetailToolStripButton("Sblocca","unlock.png");
			sbloccaUtenteButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					manageSbloccaUtenteClick();
				}
			});
		}
		
		detailButtons.add(nuovoUtenteButton);
		detailButtons.add(nuovoUtenteCopiaButton);
		detailButtons.add(editButton);
		detailButtons.add(saveButton);
		detailButtons.add(sbloccaUtenteButton);
		detailButtons.add(reloadDetailButton);
		detailButtons.add(undoButton);
		detailButtons.add(deleteButton);
		detailButtons.add(altreOpButton);
		detailButtons.add(lookupButton);
		return detailButtons;
	}
	
	public void manageNewComeCopiaClick(){
		
		Record record = new Record(detail.getValuesManager().getValues());
		Record recordNew = new Record();
		
		// profilo
		recordNew.setAttribute("idProfilo",  record.getAttribute("idProfilo"));
		
		//sub-profili	
		recordNew.setAttribute("idSubProfilo", new RecordList((JavaScriptObject) record.getAttributeAsObject("idSubProfilo")));
		
		// associazioni con le applicazioni esterne
		if (getDetail().getValuesManager().getValue("listaApplEstAccreditate") != null) {
			recordNew.setAttribute("listaApplEstAccreditate", new RecordList((JavaScriptObject) record.getAttributeAsObject("listaApplEstAccreditate")));
		}
		
		// collegamenti utente - UO comprese le abilitazioni fini per le UO che sono punti di protocollo
		if (getDetail().getValuesManager().getValue("listaUoAssociateUtente") != null) {
			recordNew.setAttribute("listaUoAssociateUtente", new RecordList((JavaScriptObject) record.getAttributeAsObject("listaUoAssociateUtente")));
		}
		
		// Visualizzazione di documenti e fascicoli assegnati/inviati in copia alla struttura
		if (getDetail().getValuesManager().getValue("listaVisualizzaDocumentiFascicoliStruttura") != null) {
			recordNew.setAttribute("listaVisualizzaDocumentiFascicoliStruttura", new RecordList((JavaScriptObject) record.getAttributeAsObject("listaVisualizzaDocumentiFascicoliStruttura")));
		}
		
		//Modifica di documenti e fascicoli assegnati alla struttura
	    if (getDetail().getValuesManager().getValue("listaModificaDocumentiFascicoliStruttura") != null) {
			recordNew.setAttribute("listaModificaDocumentiFascicoliStruttura", new RecordList((JavaScriptObject) record.getAttributeAsObject("listaModificaDocumentiFascicoliStruttura")));
		}
		// abilitazioni in visualizzazione alle mail delle caselle
		if (getDetail().getValuesManager().getValue("listaVisibEmailTransCaselleAssociateUO") != null) {
			recordNew.setAttribute("listaVisibEmailTransCaselleAssociateUO", new RecordList((JavaScriptObject) record.getAttributeAsObject("listaVisibEmailTransCaselleAssociateUO")));
		}
		
		list.deselectAllRecords();
		detail.editNewRecord(recordNew.toMap());
		detail.getValuesManager().clearErrors(true);
		detail.markForRedraw();
		newMode();
	}
	
	// Funzione per resettare la passowrd
	public void resetPassword(String usernameIn, String emailIn, String messageIn, Boolean flgAskMessage) {		 		
		final String username = usernameIn;	
		final String email    = emailIn;
		final String message  = messageIn;
		if(username!=null && !username.equalsIgnoreCase("") && email!=null && !email.equalsIgnoreCase("")){	
			if (flgAskMessage){				
				SC.ask(I18NUtil.getMessages().resetPasswordAsk_message(), new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if(value) {	
							executeResetPassword(username,email, message);			
						}
					}
				}); 
			}			
			else{
				executeResetPassword(username,email, message);
			}
		}
		else{
			 if (username == null || username.equalsIgnoreCase("") ){
				 SC.warn(I18NUtil.getMessages().usernameRequired_message()); 
			 }
			 else{
				 if (email == null || email.equalsIgnoreCase("") ){
					 SC.warn(I18NUtil.getMessages().emailRequired_message());
				 }	 
			 }
		}
	}
	
	// Funzione per creare la passowrd
	public void creaPassword(String usernameIn, String emailIn, String messageIn) {		 		
		final String username = usernameIn;	
		final String email    = emailIn;
		final String message  = messageIn;
		if(username!=null && !username.equalsIgnoreCase("") && email!=null && !email.equalsIgnoreCase("")){	
			executeCreaPassword(username,email, message);
		}
		else{
			if (username == null || username.equalsIgnoreCase("") ){
				 SC.warn(I18NUtil.getMessages().usernameRequired_message()); 
			 }
			 else{
				 if (email == null || email.equalsIgnoreCase("") ){
					 SC.warn(I18NUtil.getMessages().emailRequired_message());
				 }	 
			 }
		}
	}
		
	private void executeCreaPassword(String username, String email, String message) {		
		final String messageIn = message;		
		try {
			Layout.showWaitPopup(I18NUtil.getMessages().creaPasswordShowWaitPopup_message());			
			RPCRequest request = new RPCRequest();
			request.setContainsCredentials(true);
			request.setActionURL("springdispatcher/servletCreaPassword/");
			Map params = new HashMap();
			params.put("j_username", username);
			request.setParams(params);
			RPCManager.sendRequest(request,new RPCCallback(){
				public void execute(RPCResponse response, Object rawData, RPCRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {		
						Layout.hideWaitPopup();
						Record lRecord = new Record(JSON.decode((String)rawData));
						if (lRecord.getAttributeAsBoolean("changeOk")){
							SC.say(messageIn);
						}
						else{
							SC.say("Si e' verificato un errore nella procedura : " + lRecord.getAttributeAsString("errorMessages") + "\nContattare Help Desk.");
						}											
					} 
					else {
						Layout.addMessage(new MessageBean("Si e' verificato un errore", "", MessageType.ERROR));						
					}
				}
			});
		} 
		catch (Exception e) {
			Layout.hideWaitPopup();
			Layout.addMessage(new MessageBean("Si e' verificato un errore ", "", MessageType.ERROR));			
		}		
	}
	
	private void executeResetPassword(String username, String email, String message) {
		final String messageIn = message;
		try {
			Layout.showWaitPopup(I18NUtil.getMessages().resetPasswordShowWaitPopup_message());			
			RPCRequest request = new RPCRequest();
			request.setContainsCredentials(true);
			request.setActionURL("springdispatcher/servletResetPassword/");
			Map params = new HashMap();
			params.put("j_username", username);
			request.setParams(params);
			RPCManager.sendRequest(request,new RPCCallback(){
				public void execute(RPCResponse response, Object rawData, RPCRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {		
						Layout.hideWaitPopup();
						Record lRecord = new Record(JSON.decode((String)rawData));
						if (lRecord.getAttributeAsBoolean("changeOk")){
							if  (AurigaLayout.getParametroDBAsBoolean("INVIA_EMAIL_RESET_PWD") == true  )	 {
								SC.say(messageIn);	
							}
							else{
								String newPasswordOut = lRecord.getAttributeAsString("newPassword");								
								String messageOut = I18NUtil.getMessages().resetPasswordEsitoOK_WithPassword_message(newPasswordOut);
								SC.say(messageOut);	
							}
						}
						else{
							SC.say("Si e' verificato un errore nella procedura : " + lRecord.getAttributeAsString("errorMessages") + "\nContattare Help Desk.");
						}											
					} 
					else {
						Layout.addMessage(new MessageBean("Si e' verificato un errore", "", MessageType.ERROR));						
					}
				}
			});
		} 
		catch (Exception e) {
			Layout.hideWaitPopup();
			Layout.addMessage(new MessageBean("Si e' verificato un errore ", "", MessageType.ERROR));			
		}		
	}
	
	public boolean isGestioneClienti() {
		return AurigaLayout.getParametroDBAsBoolean("GEST_USER_CLIENTI");
	}

	public boolean isGestioneSocieta() {
		return AurigaLayout.getParametroDBAsBoolean("GEST_USER_SOCIETA");
	}

	public String getApplicationName() {
		
		String applicationName = null;
		try {
			Dictionary dictionary = Dictionary.getDictionary("params");   
			applicationName = dictionary != null && dictionary.get("applicationName") != null ? dictionary.get("applicationName") : "";
		} catch (Exception e) {
			applicationName = "";
		}
		
		return applicationName;
	}
	
	
	public static boolean isDisattivaNotifDocDaPrendereInCarico(){
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_NOTIF_X_DOC_DA_PRENDERE_IN_CARICO");
	}
	
	public boolean isVisibilitaEmailCaselleAssociateUO() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_VISIB_EMAIL_CASELLE_UO");
	}

	public static boolean isGestioneLogo() {
		return AurigaLayout.getParametroDBAsBoolean("GEST_USER_LOGO");
	}

	public static boolean isGestioneLingua() {
		return AurigaLayout.getParametroDBAsBoolean("GEST_USER_LINGUA");
	}
	
	public static boolean isGestioneUtenteInterno() {
		return isVersioneNESTLE();
	}

	public boolean isInviaEmailPwdCreaUtente() {
		return AurigaLayout.getParametroDBAsBoolean("INVIA_EMAIL_PWD_CREA_UTENTE");
	}
	
	public static  boolean isVersioneNESTLE() {					
		boolean ret = false;
		if (AurigaLayout.getParametroDB("SPEC_LABEL_GUI")!=null){
			String specLabelGUI = AurigaLayout.getParametroDB("SPEC_LABEL_GUI").toString();
			if (specLabelGUI.equalsIgnoreCase("NESTLE")){
				ret = true;
			}
		}
		return ret;
	}
	
	
	public boolean isAbilToSbloccaUtente() {
		Record record = new Record(detail.getValuesManager().getValues());		
		final boolean accountDefLocked = record.getAttribute("accountDefLocked") != null && record.getAttributeAsString("accountDefLocked").equals("1");	
		if ( AurigaLayout.getParametroDBAsBoolean("AUTH_EXT") && accountDefLocked) {
				return true;
			}
			return false;			
	}
	
	public void manageSbloccaUtenteClick(){		
		SC.ask("Vuoi sbloccare l'utente ? ", new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value != null && value) {
					Record record = new Record(detail.getValuesManager().getValues());
					GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGestioneUtentiDataSource");
					lGwtRestDataSource.executecustom("sbloccaUtente", record, new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if (response.getStatus() == DSResponse.STATUS_SUCCESS){
								detail.getValuesManager().setValue("accountDefLocked", "0");
								sbloccaUtenteButton.hide();								
							}
						}						
					});
				}
			}
		});
	}
	

	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		return new GWTRestDataSource("AurigaGestioneUtentiDataSource", "idUser", FieldType.TEXT);
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
	

	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
}