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

public class InvioMailMultiXlsLayout extends CustomDetail {

	protected InvioMailMultiXlsForm form;
	protected Window window;

	protected String tipoRel;
	protected RecordList listaRecord;
	protected TipologiaMail tipologiaMailGestioneModelli;

	public InvioMailMultiXlsLayout(Window pWindow, String pTipoRel, ValuesManager vm, RecordList pListaRecord) {

		this(pWindow, pTipoRel, vm, pListaRecord, null);
	}

	public InvioMailMultiXlsLayout(Window pWindow, String pTipoRel, ValuesManager vm, RecordList pListaRecord, TipologiaMail tipologiaMailGestioneModelli) {

		super("InvioMailMultiDestinatariXlsLayoutNew", vm);

		this.window = pWindow;
		this.tipologiaMailGestioneModelli = tipologiaMailGestioneModelli;

		tipoRel = pTipoRel;
		listaRecord = pListaRecord;

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);

		form = new InvioMailMultiXlsForm(tipologiaMailGestioneModelli, pTipoRel, vm);
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
		// Procedo all'invio
		sendMail(callback);
	}

	private void sendMail(final DSCallback callback) {
		if (vm != null && validate()) {
			final Record lRecordMail = new Record(vm.getValues());
			if (lRecordMail != null) {
				if (validate()) {
					if (isMittenteValido(lRecordMail)) {
						SC.ask(I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_invioEmailAsk_message(),
								new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										if (value) {
											manageSaveEmail(lRecordMail);
											sendMail(callback, lRecordMail);
										}
									}
								});
					} else {
						Layout.addMessage(
								new MessageBean("Attenzione, mittente non valorizzato " + "", "", MessageType.WARNING));
					}
				}
			}
		}
	}

	private void manageSaveEmail(final Record lRecordMail) {
		if (AurigaLayout.getParametroDBAsBoolean("HIDE_SAVE_SENT_EMAIL")) {
			if (lRecordMail.getAttribute("casellaIsPec") != null && "true".equals(lRecordMail.getAttribute("casellaIsPec"))) {
				lRecordMail.setAttribute("salvaInviati", true);
			} else {
				lRecordMail.setAttribute("salvaInviati", getFormFlgSalvaInviatiDefaultValue());
			}
		} else {
			if (lRecordMail.getAttribute("casellaIsPec") != null && "true".equals(lRecordMail.getAttribute("casellaIsPec"))) {
				lRecordMail.setAttribute("salvaInviati", true);
			}
		}
	}

	protected void manageResponseInvio(DSResponse response) {
		Record recordResponse = response.getData()[0];
		String idJobOut = recordResponse.getAttribute("idJobOut");
		if (idJobOut != null) {
			Layout.addMessage(
					new MessageBean(I18NUtil.getMessages().invio_mail_form_multi_destinatari_xls_esitoInvio_OK_value(idJobOut), "", MessageType.INFO));
		}
		//window.markForDestroy();
	}

	public InvioMailMultiXlsForm getForm() {
		return form;
	}

	/**
	 * Viene verificato se è presente un mittente dentro il campo Mittente presente
	 * in maschera.
	 */
	public Boolean isMittenteValido(Record record) {
		return record != null && record.getAttributeAsString("mittente") != null
				&& !"".equalsIgnoreCase(record.getAttributeAsString("mittente"))
				&& !" ".equals(record.getAttributeAsString("mittente"));
	}

	/**
	 * Metodo relativo ad un invio di una nuova mail, nella window di Composizione
	 * nuovo messaggio.
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
		lGwtRestServiceInvioNotifica.executecustom("invioMailMultiDestinatariXls", lRecordMail, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					manageResponseInvio(response);
					if (callback != null) {
						callback.execute(response, null, request);
					}
				}
			}
		});
	}

	/**
	 * Se il campo “Da colonna” della riga IndirizziEmailCCN e' valorizzato e se il
	 * mittente scelto è una PEC diamo errore
	 */
	public Boolean isColonnaIndirizziEmailCCNValido(Record record) {

		boolean ret = true;
		if (record != null && record.getAttributeAsRecordList("dettagliXlsIndirizziEmail") != null) {
			RecordList listaDettagliXlsIndirizziEmail = record.getAttributeAsRecordList("dettagliXlsIndirizziEmail");
			for (int i = 0; i < listaDettagliXlsIndirizziEmail.getLength(); i++) {
				String valoreCampoXls = listaDettagliXlsIndirizziEmail.get(i).getAttribute("campoXls");

				// Se il campoXls contiene "IndirizziEmailCCN" controllo se il campo "Da colonna" e' valorizzato
				if (valoreCampoXls != null && "IndirizziEmailCCN".equals(valoreCampoXls)) {
					String valoreColonnaXls = listaDettagliXlsIndirizziEmail.get(i).getAttribute("colonnaXls");
					if (valoreColonnaXls != null && !valoreColonnaXls.equals("")) {
						// Se il mittente scelto è una PEC diamo errore
						if (record.getAttribute("casellaIsPec") != null && "true".equals(record.getAttribute("casellaIsPec"))) {
							ret = false;
						}
					}
				}
			}
		}
		return ret;
	}

	public Boolean isCasellaPec(Record record) {
		boolean isPec = false;
		if (record != null && record.getAttributeAsString("casellaIsPec") != null
				&& "true".equals(record.getAttributeAsString("casellaIsPec"))) {
			isPec = true;
		}
		return isPec;
	}
}