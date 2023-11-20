/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

public class InvioMailLayoutNew extends CustomDetail {

	protected InvioMailFormNew form;
	protected Window window;

	protected String tipoRel;
	protected RecordList listaRecord;
	protected TipologiaMail tipologiaMailGestioneModelli;
	
	public InvioMailLayoutNew(Window pWindow, String pTipoRel, ValuesManager vm, RecordList pListaRecord) {

		this(pWindow, pTipoRel, vm, pListaRecord, null);
	}

	public InvioMailLayoutNew(Window pWindow, String pTipoRel, ValuesManager vm, RecordList pListaRecord, TipologiaMail tipologiaMailGestioneModelli) {
		
		super("invioMailLayoutNew", vm);

		this.window = pWindow;
		this.tipologiaMailGestioneModelli = tipologiaMailGestioneModelli;

		tipoRel = pTipoRel;
		listaRecord = pListaRecord;

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		form = new InvioMailFormNew(tipologiaMailGestioneModelli, pTipoRel, vm) {
			
			@Override
			public boolean getFlgSalvaInviatiDefaultValue() {
				return getFormFlgSalvaInviatiDefaultValue();
			}
		};
		form.setMargin(10);

		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
	
		layout.addMember(form);

		addMember(layout);

	}
	
	public boolean getFormFlgSalvaInviatiDefaultValue() {
		return AurigaLayout.getParametroDBAsBoolean("DEFAULT_SAVE_SENT_EMAIL");
	}

	/**
	 * Metodo relativo alla verifica di invio di una nuova mail.
	 * 
	 * @param callback
	 */
	public void inviaMail(final DSCallback callback) {
		//Procedo all'invio 
		sendMail(callback);		
	}
	
	private void sendMail(final DSCallback callback) {
		if ((vm != null) && validate()) {
			final Record lRecordMail = new Record(vm.getValues());
			if (lRecordMail != null) {
				if (isMittenteValido(lRecordMail)) {
					if(verificaInvioSeparato(lRecordMail)){
						if (isNotSaveMail(lRecordMail)) {
							SC.ask(I18NUtil.getMessages().posta_elettronica_salvamail_item_warning_message(), new BooleanCallback() {
	
								@Override
								public void execute(Boolean value) {
									if (value) {
										manageSaveEmail(lRecordMail);
										sendMail(callback, lRecordMail);
									}
								}
							});
						} else {
							manageSaveEmail(lRecordMail);
							sendMail(callback, lRecordMail);
						}
					}else{
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().inviomailform_flgInvioSeparato_errormessages() + "", "", MessageType.WARNING));
					}
				} else {
					Layout.addMessage(new MessageBean("Attenzione, mittente non valorizzato " + "", "", MessageType.WARNING));
				}
			}
		}
	}
	
	private void manageSaveEmail(final Record lRecordMail) {
		if(AurigaLayout.getParametroDBAsBoolean("HIDE_SAVE_SENT_EMAIL")){
			if(lRecordMail.getAttribute("casellaIsPec") != null &&
					"true".equals(lRecordMail.getAttribute("casellaIsPec"))){
				lRecordMail.setAttribute("salvaInviati", true);
			}else{
				lRecordMail.setAttribute("salvaInviati", getFormFlgSalvaInviatiDefaultValue());
			}
		}else{
			if(lRecordMail.getAttribute("casellaIsPec") != null &&
					"true".equals(lRecordMail.getAttribute("casellaIsPec"))){
				lRecordMail.setAttribute("salvaInviati", true);
			}
		}
	}

	protected void manageResponseInvio(DSResponse response) {
		Layout.addMessage(new MessageBean(I18NUtil.getMessages().invionotificainterop_esitoInvio_OK_value(), "", MessageType.INFO));
		window.markForDestroy();
	}

	/**
	 * Metodo relativo al slavataggio di una bozza da maschera di Composizione nuovo messaggio. Vengono salvati i dati relativi al bean SalvaInBozzaBean ed al
	 * bean InvioMailBean di cui è figlio.
	 * 
	 * @param callback
	 * @param operazione
	 */
	public void salvaBozza(final DSCallback callback, final String operazione) {
		if (vm != null) {
			//Apro un popup per avvertire l'utente del salvataggio della bozza
			Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_salvataggio_mail());
			
			vm.clearErrors(true);
			if ((vm.getItem("listaItemInLavorazione") == null)
					|| (vm.getItem("listaItemInLavorazione") != null && vm.getItem("listaItemInLavorazione").validate())) {
				final Record record = new Record(vm.getValues());
				if (isMittenteValido(record)) {
										
					salvaMail(callback, operazione, record);
					
					
				} else {
					//Nascondo il popup aperto in precedenza
					Layout.hideWaitPopup();
					
					Layout.addMessage(new MessageBean("Attenzione, mittente non valorizzato " + "", "", MessageType.WARNING));
				}
			} else{
				//Nascondo il popup aperto in precedenza
				Layout.hideWaitPopup();
			}
		}
	}

	private void salvaMail(final DSCallback callback, final String operazione, Record record) {
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AurigaSalvaIterBozzaMailDataSource");
		lGwtRestService.extraparam.put("tipoRel", tipoRel);
		lGwtRestService.extraparam.put("operazione", operazione);
		lGwtRestService.performCustomOperation("saveBozza", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				//Nascondo il popup aperto in precedenza
				Layout.hideWaitPopup();
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean("Bozza salvata con successo", "", MessageType.INFO));
					if (callback != null) {
						callback.execute(response, rawData, request);
					}
				}
			}
		}, new DSRequest());
	}

	public InvioMailFormNew getForm() {
		return form;
	}

	/**
	 * Viene verificato se è presente un mittente dentro il campo Mittente presente in maschera.
	 */
	public Boolean isMittenteValido(Record record) {
		return record != null && record.getAttributeAsString("mittente") != null && !"".equalsIgnoreCase(record.getAttributeAsString("mittente"))
				&& !" ".equals(record.getAttributeAsString("mittente"));
	}
	
	/**
	 * Viene verificato l'invio separato della mail, se il chek flgInvioSeparato è spuntato e sono presenti
	 * destinatari in CC, l'invio della stessa non è consentito.
	 */
	public Boolean verificaInvioSeparato(Record record) {
		
		boolean verify = true;
		if(record != null){
			if(record.getAttributeAsBoolean("flgInvioSeparato") != null && record.getAttributeAsBoolean("flgInvioSeparato")){
				if(record.getAttributeAsString("destinatariCC") != null && !"".equalsIgnoreCase(record.getAttributeAsString("destinatariCC"))){
					verify = false;
				}
			}
		}
		return verify;
	}

	/**
	 * Metodo di verifica nel caso in cui: 1) Se si è selezionato il check Salva mail, viene spedita la mail direttamente. 2) Se Non si è selezionato il check
	 * Salva mail, allora si procede alla verifica della presenza di Item in Lavorazione,Tab: Appunti&Note. Se questi ultimi sono presenti allora viene mostrato
	 * a video l'avviso di conferma invio.
	 * 
	 * @param record
	 * @return
	 */
	public Boolean isNotSaveMail(Record record) {
		Boolean verify = false;
		if (record != null && record.getAttributeAsBoolean("salvaInviati") != null && !record.getAttributeAsBoolean("salvaInviati")) {
			if (record.getAttributeAsRecordList("listaItemInLavorazione") != null && record.getAttributeAsRecordList("listaItemInLavorazione").getLength() > 0
					&& !record.getAttributeAsRecordList("listaItemInLavorazione").isEmpty()) {
				RecordList recordListItemInLavorazione = record.getAttributeAsRecordList("listaItemInLavorazione");
				for (int i = 0; i < recordListItemInLavorazione.getLength(); i++) {
					Record item = recordListItemInLavorazione.get(i);
					if (item != null) {
						if (item.getAttributeAsString("itemLavUriFile") != null && !"".equals(item.getAttributeAsString("itemLavUriFile"))) {
							verify = true;
							break;
						}
						if ((item.getAttributeAsString("itemLavTag") != null && !"".equals(item.getAttributeAsString("itemLavTag")))
								|| (item.getAttributeAsString("itemLavCommento") != null && !"".equals(item.getAttributeAsString("itemLavCommento")))) {
							verify = true;
							break;
						}
					}
				}
			}
		}

		return verify;
	}

	/**
	 * Metodo relativo ad un invio di una nuova mail, nella window di Composizione nuovo messaggio.
	 * 
	 * @param callback
	 * @param lRecordMail
	 */
	private void sendMail(final DSCallback callback, final Record lRecordMail) {
		Layout.showWaitPopup(I18NUtil.getMessages().posta_elettronica_detail_invio_mail());
		final GWTRestService<Record, Record> lGwtRestServiceInvioNotifica = new GWTRestService<Record, Record>("AurigaInvioMailDatasource");
		lGwtRestServiceInvioNotifica.extraparam.put("tipoRel", tipoRel);
		lGwtRestServiceInvioNotifica.extraparam.put("idUD", lRecordMail.getAttribute("idUD"));
		lGwtRestServiceInvioNotifica.extraparam.put("idEmailUD", lRecordMail.getAttribute("idEmailUD"));
		lGwtRestServiceInvioNotifica.executecustom("invioMail", lRecordMail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					manageResponseInvio(response);
					// Ho mandato la mail, ora devo salvare gli appunti e note
					if (callback != null) {
						callback.execute(response, null, request);
					}
				}
			}
		});
	}

}