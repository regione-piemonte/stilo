/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;


public class PostaInArrivoRegistrazioneWindow extends ModalWindow {

	private PostaInArrivoRegistrazioneWindow _window;

	private String idUd;

	private CustomDetail portletLayout;

	public PostaInArrivoRegistrazioneWindow(Record record) {

		super("postainarrivoregistrazione", true);

		setTitle(getTitlePostaInArrivoRegistrazioneWindow());  	

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		Record lRecord = new Record();
		final String idEmail = record.getAttribute("idEmail");
		lRecord.setAttribute("idEmail", idEmail);		

		if(!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {
			
			portletLayout = new PostaElettronicaDetail("postainarrivoregistrazione");

			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						Record record = response.getData()[0];
						portletLayout.editRecord(record);	
						portletLayout.getValuesManager().clearErrors(true);
						_window.show();
					} 				
				}
			}, new DSRequest());
			
		}else{
			
			portletLayout = new DettaglioPostaElettronica("postainarrivoregistrazione");
		
			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						Record record = response.getData()[0];						
						portletLayout.editRecord(record);													
						portletLayout.getValuesManager().clearErrors(true);
						_window.show();
					} 				
				}
			}, new DSRequest());
		}

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);                

		setIcon("mail/mail-reply2.png");

	}
	
	public String getTitlePostaInArrivoRegistrazioneWindow() {
		return I18NUtil.getMessages().postainarrivoregistrazione_window_title();
	}

}
