/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ProfilaturaModelliDocWindow extends ModalWindow {
	
	private ProfilaturaModelliDocWindow instance;
	private ProfilaturaModelliDocLayout profilaturaModelliDocLayout;
	
	private String smartId;
	
	public ProfilaturaModelliDocWindow(String idModello, String nomeModello, String nomeTabella, String tipoEntitaAssociata, String idEntitaAssociata, String nomeEntitaAssociata) {
		
		super("profilaturaModelliDoc", true, false);
		
		instance = this;
		
		smartId = SC.generateID();
		
		setID(smartId);
		
		String idCreated = nomeEntita + smartId + new Date().getTime();
		final String profilaVariabileFunctionName = idCreated + "profilaVariabile";
		
		if(nomeModello != null && !"".equals(nomeModello)) {
			setTitle("Profilatura modello " + nomeModello);
		} else {
			setTitle("Profilatura modello per " + nomeEntitaAssociata);
		}
		setIcon("modelli/modelli.png");
		setMaximized(true);
		
		// visto che è massimizzata la rendo non modale per far funzionare il tabulatore (Mattia Zanin)
		setIsModal(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setHeight100();
		lVLayout.setWidth100();
		lVLayout.setTop(5);
		
		profilaturaModelliDocLayout = new ProfilaturaModelliDocLayout(idModello, nomeModello, nomeTabella, tipoEntitaAssociata, idEntitaAssociata, nomeEntitaAssociata, null, profilaVariabileFunctionName);
		lVLayout.setMembers(profilaturaModelliDocLayout);			
		
		setBody(lVLayout);
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				initProfilaVariabile(instance, profilaVariabileFunctionName);
			}
		});		
		
		if(idModello != null && !"".equals(idModello)) {
			Record record = new Record();
			record.setAttribute("idModello", idModello);
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ModelliDocDatasource", "idModello", FieldType.TEXT);
			Layout.showWaitPopup("Caricamento in corso...");
			lGwtRestDataSource.getData(record, new DSCallback() {

				@Override
				public void execute(final DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record detailRecord = response.getData()[0];						
						profilaturaModelliDocLayout.caricaDettaglio(detailRecord);
						instance.show();
					}
				}
			});
		} else {
			profilaturaModelliDocLayout.nuovoDettaglio();
			instance.show();				
		}
		
	}
	
	public void profilaVariabile(String nomeVariabile) {
		profilaturaModelliDocLayout.profilaVariabile(nomeVariabile);
	}	

	private native void initProfilaVariabile(ProfilaturaModelliDocWindow pProfilaturaModelliDocWindow, String functionName) /*-{
		$wnd[functionName] = function (value) {
			pProfilaturaModelliDocWindow.@it.eng.auriga.ui.module.layout.client.modelliDoc.ProfilaturaModelliDocWindow::profilaVariabile(Ljava/lang/String;)(value);
		};
	}-*/;	
	
	@Override
	public void manageOnCloseClick() {
		markForDestroy();
	}
	
}
