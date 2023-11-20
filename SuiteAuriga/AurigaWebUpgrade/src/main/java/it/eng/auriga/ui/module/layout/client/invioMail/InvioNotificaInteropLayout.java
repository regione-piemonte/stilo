/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class InvioNotificaInteropLayout extends VLayout{

	private InvioNotificaInteropForm form;
	private InvioNotificaInteropWindow window;

	public InvioNotificaInteropLayout(InvioNotificaInteropWindow pInvioNotificaInteropWindow, final DSCallback callback){
		
		window = pInvioNotificaInteropWindow;
		
		setOverflow(Overflow.VISIBLE);
		
		form = new InvioNotificaInteropForm();
		form.setMargin(10);
		form.setWidth100();
		form.setHeight100();
		
		//form.setCellBorder(1);
		 
		Button confermaButton = new Button(I18NUtil.getMessages().invionotificainterop_invioButton_title());
		confermaButton.setIcon("buttons/send.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.setAlign(Alignment.CENTER);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				inviaMail(callback);
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);		
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(form);
		
		addMember(layout);		
		addMember(_buttons);	
	}

	
	public void unlock(final RecordList listaMail, final boolean isMassiva, final ServiceCallback<Record> callback){
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				String errorMsg = null;	
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS){
					Record data = response.getData()[0];	
					Map errorMessages = data.getAttributeAsMap("errorMessages");
					
					String key = null;
					
					if(listaMail.get(0)!=null)
						key = listaMail.get(0).getAttribute("idEmail");
					
					if(errorMessages != null && errorMessages.size() > 0) { 
						errorMsg = "Fallito rilascio della mail al termine dell'operazione: " + (key !=null ? errorMessages.get(key) : "");
					}
				}
				Layout.hideWaitPopup();
				if(errorMsg != null) {
					Layout.addMessage(new MessageBean(errorMsg, "", MessageType.WARNING));				
				} else if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Rilascio effettuato con successo", "", MessageType.INFO));	
					
				}
				
				Record record = new Record();
				callback.execute(record);
			}
		});		
	}
	
	
	private void bloccaemail(final Record pRecordMail,  final ServiceCallback<Record> callback){
		ctrlLockEmail(pRecordMail, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				
				Record esitoCheck = object.getAttributeAsRecord("esito");
				
				boolean isLock = esitoCheck.getAttributeAsBoolean("flagPresenzaLock");
				boolean isForzaLock = esitoCheck.getAttributeAsBoolean("flagForzaLock");
				
				if (isLock){
					String messaggio = esitoCheck.getAttributeAsString("errorMessage");
					if (isForzaLock) {
						
						SC.ask(messaggio, new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {

									sbloccaEmail(pRecordMail, new ServiceCallback<Record>() {

										@Override
										public void execute(Record data) {
											final RecordList listaEmail = new RecordList();
											listaEmail.add(pRecordMail);
											
											lock(listaEmail, new ServiceCallback<Record>() {
												@Override
												public void execute(Record pRecord) {
													
													boolean isLock = pRecord.getAttributeAsBoolean("esitoLock");
														
													final RecordList listaEmail = new RecordList();
													listaEmail.add(pRecordMail);
													
													callback.execute(pRecordMail);
												}						
											});
										}
									});
								}
							}
						});
					} else {
						Layout.hideWaitPopup();
						Layout.addMessage(new MessageBean(esitoCheck.getAttributeAsString("errorMessage"), "", MessageType.ERROR));
					}
				} else {
					final RecordList listaEmail = new RecordList();
					listaEmail.add(pRecordMail);
					
					lock(listaEmail, new ServiceCallback<Record>() {
						@Override
						public void execute(Record pRecord) {
							
							boolean isLock = pRecord.getAttributeAsBoolean("esitoLock");
								
							final RecordList listaEmail = new RecordList();
							listaEmail.add(pRecordMail);
							
							callback.execute(pRecordMail);
						}						
					});
				}
			}
		});
	}
	
	private void verificaLock(final Record pRecordMail,  final DSCallback callback,final DSResponse response){
		
		final RecordList listaEmail = new RecordList();
		listaEmail.add(pRecordMail);
		
		boolean flg =pRecordMail.getAttributeAsBoolean("flgRilascia");
		
		String archEcc = AurigaLayout.getParametroDB("ARCHIVIAZIONE_AUTO_EMAIL_POST_ECCEZIONE");
		//se c'è archiviazione automatica, ossia lavorazione chiusa automatica,  (true) allora nn devo fare unlock perchè con lavorazione chiusa da errore
		if(flg && !"true".equals(archEcc)){
			unlock(listaEmail,false, new ServiceCallback<Record>() {
				
				@Override
				public void execute(Record pRecord) {

					callback.execute(response, null, new DSRequest());
				}
			});
		}
		else{	
			if(callback != null){
				callback.execute(response, null, new DSRequest());
			}
		}
	}
	
	private void lock(final RecordList listaMail, final ServiceCallback<Record> callback){
		
		Record record = new Record();
		record.setAttribute("listaRecord", listaMail);
						
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("LockEmailDataSource");
		
		lGwtRestDataSource.addData(record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request){
				String errorMsg = null;					
				Record rec = new Record();
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS){
					Record data = response.getData()[0];	
					Map errorMessages = data.getAttributeAsMap("errorMessages");
					
					if(errorMessages != null && errorMessages.size() > 0) { 
						errorMsg = "La messa in carico è andata in errore!";
						errorMsg += "<br/>" + data.getAttribute("id") + ": " + errorMessages.get(data.getAttribute("idEmail"));
						rec.setAttribute("esitoLock", false);
					}
					rec.setAttribute("esitoLock", true);
				}
				callback.execute(rec);
			}
		});		
	}
	
	private void ctrlLockEmail(Record record, final ServiceCallback<Record> callback){
		
		final GWTRestDataSource lockEmailDataSource = new GWTRestDataSource("LockEmailDataSource");
		lockEmailDataSource.executecustom("checkLockEmail", record, new DSCallback() {
			
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				Record rec = new Record();
				rec.setAttribute("esito", response.getData()[0]);
				callback.execute(rec);
			}
		});
	}

	protected void inviaMail(final DSCallback callback){
		
		Record lRecordMail = form.getRecord();
		if (lRecordMail!=null){
			Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_invio_mail());
			try {
				bloccaemail(lRecordMail, new ServiceCallback<Record>() {
					
					@Override
					public void execute(Record lRecordMail){
						
						window.getDatasource().executecustom("invioMail", lRecordMail, new DSCallback() {
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								Layout.hideWaitPopup();
								if (response.getStatus() == DSResponse.STATUS_SUCCESS){
									manageResponseInvio(response);
									
									Record lRecord = new Record();
									lRecord.setAttribute("idEmail",  form.getRecord().getAttribute("idEmail"));
									
									boolean flg = form.getRecord().getAttributeAsBoolean("flgRilascia");
									lRecord.setAttribute("flgRilascia",flg);
									
									verificaLock(lRecord,callback,response);								
								} 
								else {
									Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio dell'e-mail", "", MessageType.ERROR));
								}
							}
						});
					}
				});
			} catch (Exception e) {
				Layout.hideWaitPopup();
				Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio dell'e-mail: " + e.getMessage(), "", MessageType.ERROR));
			}
		}
	}
	
	// sblocco singola mail
	private void sbloccaEmail(Record record, final ServiceCallback<Record> callback) {
		final RecordList listaEmail = new RecordList();
		listaEmail.add(record);

		Record lRecord = new Record();
		lRecord.setAttribute("listaRecord", listaEmail);

		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("UnlockEmailDataSource");
		lGwtRestDataSource.addData(lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record data = response.getData()[0];
					callback.execute(data);
				}
			}
		});
	}

	protected void manageResponseInvio(DSResponse response) {
		Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(), "", MessageType.INFO));
		window.markForDestroy();
	}

	public InvioNotificaInteropForm getForm(){
		return form;
	}
}
