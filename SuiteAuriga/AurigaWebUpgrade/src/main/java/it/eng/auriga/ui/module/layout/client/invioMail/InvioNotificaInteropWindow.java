/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class InvioNotificaInteropWindow extends ModalWindow {
	
	private GWTRestDataSource datasource;
	private InvioNotificaInteropWindow _window;
	private InvioNotificaInteropLayout portletLayout;
	
	public InvioNotificaInteropWindow(GWTRestDataSource pGWTRestDataSource, Record record, DSCallback callback){
		
		super("invio_notifica", true);
		
		_window = this;
		
		this.datasource = pGWTRestDataSource;
		
		setTitle(I18NUtil.getMessages().invionotificainteropwindow_title(record.getAttribute("tipoNotifica")));
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		portletLayout = new InvioNotificaInteropLayout(this, callback);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
										
		portletLayout.getForm().caricaMail(record);
		portletLayout.getForm().clearErrors(true);
		
		setIcon("postaElettronica/notifiche.png");	
		
	}
	
	@Override
	public void manageOnCloseClick() {
		Record lRecord = new Record();
		lRecord.setAttribute("idEmail", portletLayout.getForm().getValueAsString("idEmail"));
		datasource.executecustom("sbloccaMail", lRecord, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				_window.markForDestroy();
			}
		});
	}
	
	public void loadMail(Record lRecord){
		portletLayout.getForm().caricaMail(lRecord);
	}

	public GWTRestDataSource getDatasource() {
		return datasource;
	}

}
