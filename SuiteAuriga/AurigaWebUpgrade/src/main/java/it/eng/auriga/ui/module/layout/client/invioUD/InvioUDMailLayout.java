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
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class InvioUDMailLayout extends VLayout {

	private InvioUDMailForm form;
	private InvioUDMailWindow window;

	private DSCallback callback;

	public InvioUDMailLayout(InvioUDMailWindow pWindow, Boolean invioMailFromAtti, final DSCallback pCallback) {

		window = pWindow;

		callback = pCallback;

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		form = new InvioUDMailForm(window.getTipoMail(), invioMailFromAtti);
		form.setMargin(10);

		Button confermaButton = new Button("Invia");
		confermaButton.setIcon("buttons/send.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.setAlign(Alignment.CENTER);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				inviaMail();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.setBorder("1px solid #878E96");
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

	protected void inviaMail() {
		final Record lRecordMail = form.getRecord();
		if (lRecordMail != null) {
			if("text".equals(lRecordMail.getAttribute("textHtml"))) {
				lRecordMail.setAttribute("body", lRecordMail.getAttribute("bodyText"));
			} else {
				lRecordMail.setAttribute("body", lRecordMail.getAttribute("bodyHtml"));
			}
			sendMail(lRecordMail);
		}
	}

	private void sendMail(final Record lRecordMail) {
		final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("AurigaInvioUDMailDatasource");
		
		// Se è una pec
		if (window.getTipoMail().equals("PEC")) {
			// il window.tipoMail è PEC (invio mail interoperabile). Setto l'extraparam da passare al DataSource.
			lGWTRestDataSource.extraparam.put("modalitaForm","invio_mail_interop");
			
			// Recupero la lista dei destinatari
			RecordList destinatariPec = lRecordMail.getAttributeAsRecordList("destinatariPec");
			if (hasTuttiIDestinatariNonSelezionati(destinatariPec)) {
				Layout.addMessage(new MessageBean("Nessun destinatario selezionato per l'invio", "", MessageType.ERROR));
			} else if (hasDestinatariNonSelezionatiConMailNonInviata(destinatariPec)) {
				SC.ask("Alcuni dei destinatari in lista non sono selezionati come destinatari dell'e-mail. " + "Vuoi comunque procedere all'invio?",
						new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									inviaMail(lRecordMail, lGWTRestDataSource);
								}
							}
						});
			} else
				inviaMail(lRecordMail, lGWTRestDataSource);
		} else {
			// il window.tipoMail è PEO (invio mail normale). Setto l'extraparam da passare al DataSource.
			lGWTRestDataSource.extraparam.put("modalitaForm","invio_mail");
			inviaMail(lRecordMail, lGWTRestDataSource);
		}
	}

	protected void inviaMail(Record lRecordMail, GWTRestDataSource lGWTRestDataSource) {
		Layout.showWaitPopup("Invio e-mail in corso: potrebbe richiedere qualche secondo. Attendere…");
		try {
			lGWTRestDataSource.executecustom("invioMail", lRecordMail, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						manageResponseInvio(response);
					} else {
						// Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio dell'e-mail", "", MessageType.ERROR));
					}
				}
			});
		} catch (Exception e) {
			Layout.hideWaitPopup();
			Layout.addMessage(new MessageBean("Si è verificato un errore durante l'invio dell'e-mail", "", MessageType.ERROR));
		}
	}

	protected Boolean hasDestinatariNonSelezionatiConMailNonInviata(RecordList destinatariPec) {
		if (destinatariPec != null) {
			for (int i = 0; i < destinatariPec.getLength(); i++) {
				Record lRecordDestinatario = destinatariPec.get(i);
				Boolean destPrimario = lRecordDestinatario.getAttributeAsBoolean("destPrimario");
				Boolean destCC = lRecordDestinatario.getAttributeAsBoolean("destCC");
				if ((destPrimario == null || !destPrimario) && (destCC == null || !destCC)) {
					// Il destinatario non è selezionato
					if (lRecordDestinatario.getAttribute("idMailInviata") == null || lRecordDestinatario.getAttribute("idMailInviata").trim().equals("")) {
						return true;
					}
				}
			}
			return false;
		}
		return null;
	}

	protected Boolean hasTuttiIDestinatariNonSelezionati(RecordList destinatariPec) {
		if (destinatariPec != null && !destinatariPec.isEmpty()) {
			int nro = 0;
			for (int i = 0; i < destinatariPec.getLength(); i++) {
				Record lRecordDestinatario = destinatariPec.get(i);
				Boolean destPrimario = lRecordDestinatario.getAttributeAsBoolean("destPrimario");
				Boolean destCC = lRecordDestinatario.getAttributeAsBoolean("destCC");
				if ((destPrimario == null || !destPrimario) && (destCC == null || !destCC)) {
					// Il destinatario non è selezionato
					nro++;
				}
			}
			return nro == destinatariPec.getLength();
		}
		return null;
	}

	protected void manageResponseInvio(DSResponse response) {
		Layout.addMessage(new MessageBean("Mail inviata correttamente", "", MessageType.INFO));
		window.markForDestroy();
		if (callback != null) {
			callback.execute(response, null, new DSRequest());
		}
	}

	public InvioUDMailForm getForm() {
		return form;
	}
}