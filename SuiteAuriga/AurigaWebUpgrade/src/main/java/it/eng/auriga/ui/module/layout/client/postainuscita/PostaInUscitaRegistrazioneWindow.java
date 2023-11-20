/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioPostaElettronica;
import it.eng.auriga.ui.module.layout.client.postaElettronica.PostaElettronicaDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.Canvas;


public class PostaInUscitaRegistrazioneWindow extends ModalWindow {

	private PostaInUscitaRegistrazioneWindow _window;

	private Canvas portletLayout;

	public PostaInUscitaRegistrazioneWindow(Record record) {
		this(record, null);
	}

	public PostaInUscitaRegistrazioneWindow(String idUd) {
		this(null, idUd);
	}

	private PostaInUscitaRegistrazioneWindow(Record record, String idUd) {

		super("postainuscitaregistrazione", true);

		setTitle(getTitlePostaInUscitaRegistrazioneWindow());  	

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		

		if(record != null) {
			
			Record lRecord = new Record();
			final String idEmail = record.getAttribute("idEmail");
			lRecord.setAttribute("idEmail", idEmail);			

			if(!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {

				portletLayout = new PostaElettronicaDetail("postainuscitaregistrazione");

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaLoadDettaglioEmailDataSource", "idEmail", FieldType.TEXT);
				lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
							Record record = response.getData()[0];
							((PostaElettronicaDetail)portletLayout).editRecord(record);	
							portletLayout.getValuesManager().clearErrors(true);
							_window.show();
						} 				
					}
				}, new DSRequest());
			}
			else
			{
				portletLayout = new DettaglioPostaElettronica("postainuscitaregistrazione");

				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaGetDettaglioPostaElettronicaDataSource", "idEmail", FieldType.TEXT);
				lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
							Record record = response.getData()[0];
							((DettaglioPostaElettronica) portletLayout).editRecord(record);
							_window.show();
						} 				
					}
				}, new DSRequest());
			}

		} else {

			GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("PostaInUscitaRegistrazioneDataSource", "idEmail", FieldType.TEXT);
			lGwtRestDataSource.addParam("idUd", idUd);			

			portletLayout = new PostaInUscitaRegistrazioneLayout(lGwtRestDataSource) {				
				@Override
				public void showDetail() {
					
					super.showDetail();
					if(fullScreenDetail) {	
						String title = "";
						if(mode != null) {
							if(mode.equals("new")) {				
								title = getNewDetailTitle();
							} else if(mode.equals("edit")) {
								title = getEditDetailTitle();		
							} else if(mode.equals("view")) {
								title = getViewDetailTitle();
							}
						}
						_window.setTitle(title);											
					}
				}

				@Override
				public void hideDetail(boolean reloadList) {
					
					super.hideDetail(reloadList);
					if(fullScreenDetail) {			
						_window.setTitle(getTitlePostaInUscitaRegistrazioneWindow()); 
					} 	
				}				

				@Override
				public boolean isForcedToAutoSearch() {
					
					return true;
				}				
			};

			((PostaInUscitaRegistrazioneLayout)portletLayout).setLookup(false);

		}

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("mail/mail-reply2.png");              

		if(record == null) {
			_window.show();
		}        
	}
	
	public String getTitlePostaInUscitaRegistrazioneWindow() {
		return I18NUtil.getMessages().postainuscitaregistrazione_window_title();
	}

}
