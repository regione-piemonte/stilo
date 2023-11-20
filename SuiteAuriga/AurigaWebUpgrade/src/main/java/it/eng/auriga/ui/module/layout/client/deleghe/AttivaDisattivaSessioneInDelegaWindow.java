/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

public class AttivaDisattivaSessioneInDelegaWindow extends Window {

	private AttivaDisattivaSessioneInDelegaForm form;
	private Button buttonAttiva;
	private Button buttonTermina;
	private VLayout lVLayout;
	private boolean conferma = false;
	private AttivaDisattivaSessioneInDelegaWindow instance;

	public AttivaDisattivaSessioneInDelegaWindow() {

		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(450);
		setHeight(125);
		setKeepInParentRect(true);
		setTitle("Attiva/termina sessione di lavoro in delega");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);

		form = new AttivaDisattivaSessioneInDelegaForm(this);

		HStack lHStack = new HStack();
		lHStack.setWidth100();
		lHStack.setMembersMargin(10);
		lHStack.setAlign(Alignment.CENTER);

		buttonAttiva = new Button("Attiva");
		buttonAttiva.setWidth(150);
		buttonAttiva.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageAttivaClick();
			}
		});
		buttonTermina = new Button("Termina");
		buttonTermina.setWidth(150);
		buttonTermina.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageTerminaClick();
			}
		});

		lHStack.addMember(buttonAttiva);
		lHStack.addMember(buttonTermina);

		lVLayout = new VLayout();
		form.setHeight("75%");
		lVLayout.addMember(form);
		lVLayout.addMember(lHStack);
		lVLayout.setBottom(20);

		addItem(lVLayout);

		setShowTitle(true);
		setHeaderIcon("menu/deleghe_attivadisattiva.png");

		String delegaIn = Layout.getUtenteLoggato().getIdUserLavoro();
		String delegaDenominazioneIn = Layout.getUtenteLoggato().getDelegaDenominazione();
		
		if( delegaIn!=null &&  !delegaIn.equalsIgnoreCase("")){
			form.initCombo(delegaIn, delegaDenominazioneIn);
		}
		
		manageChangeDelega();
	}

	protected void manageTerminaClick() {
		GWTRestService<Record, Record> lGwtRestServiceTermina = new GWTRestService<Record, Record>("AurigaTerminaDelegaDataSource");
		Record lRecord = new Record();
		Layout.showWaitPopup("Terminazione sessione in delega in corso: potrebbe richiedere qualche secondo. Attendere…");
		try {
			lGwtRestServiceTermina.call(lRecord, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {
					if (object.getAttributeAsBoolean("error")) {
						Layout.hideWaitPopup();
					} else {
						AurigaLayout.getInstance().terminaDelega();
						instance.markForDestroy();
					}
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			Layout.hideWaitPopup();
		}
	}

	protected void manageAttivaClick() {
		String utenteSelezionato = form.getUtenteSelezionato();
		// Boolean mantieniPreference = form.getValue("mantieniPreference") != null ? (Boolean)form.getValue("mantieniPreference") : false;
		GWTRestService<Record, Record> lGwtRestServiceAttiva = new GWTRestService<Record, Record>("AurigaAttivaDelegaDataSource");
		Record lRecord = new Record();
		lRecord.setAttribute("idUser", utenteSelezionato);
		// lRecord.setAttribute("mantieniPreference", mantieniPreference);
		Layout.showWaitPopup("Attivazione sessione in delega in corso: potrebbe richiedere qualche secondo. Attendere…");
		try {
			if (!conferma) {
				lGwtRestServiceAttiva.call(lRecord, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						if (object.getAttributeAsBoolean("error")) {
							Layout.hideWaitPopup();
							if (object.getAttributeAsString("avvertimenti") != null) {
								// Mostro gli avvertimenti
								buttonAttiva.setTitle("Conferma Attivazione");
								instance.setHeight(instance.getHeight() + 120);
								form.settaAvvertimenti(object.getAttributeAsString("avvertimenti"));
								conferma = true;
							}
						} else {
							AurigaLayout.getInstance().lavoraInDelega();
							instance.markForDestroy();
						}
					}
				});
			} else {
				lRecord.setAttribute("ignora", true);
				lGwtRestServiceAttiva.call(lRecord, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						Layout.getInstance().lavoraInDelega();
						instance.markForDestroy();
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
			Layout.hideWaitPopup();
		}
	}

	public void manageChangeDelega() {
		String utenteSelezionato = form.getUtenteSelezionato();
		if (utenteSelezionato == null || utenteSelezionato.trim().equals("")) {
			buttonAttiva.setVisible(false);
			buttonTermina.setVisible(false);
			// form.getField("mantieniPreference").hide();
		} else if (Layout.getUtenteLoggato().getIdUserLavoro() != null && Layout.getUtenteLoggato().getIdUserLavoro().equals(utenteSelezionato)) {
			buttonAttiva.setVisible(false);
			// form.getField("mantieniPreference").hide();
			buttonTermina.setVisible(true);
		} else {
			buttonAttiva.setVisible(true);
			// form.getField("mantieniPreference").show();
			buttonTermina.setVisible(false);
		}
	}
}
