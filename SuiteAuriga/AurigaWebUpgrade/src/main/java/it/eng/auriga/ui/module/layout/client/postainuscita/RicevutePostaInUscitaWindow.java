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


public class RicevutePostaInUscitaWindow extends ModalWindow {

	private RicevutePostaInUscitaWindow _window;

	private Canvas portletLayout;

	public RicevutePostaInUscitaWindow(Record record) {
		this(record, null, null);
	}

	public RicevutePostaInUscitaWindow(String idEmailRif) {
		this(null, idEmailRif, null);
	}

	public RicevutePostaInUscitaWindow(String idEmailRif, String idDestinatario) {
		this(null, idEmailRif, idDestinatario);
	}

	private RicevutePostaInUscitaWindow(Record record, String idEmailRif, String idDestinatario) {

		super("ricevutepostainuscita", true);

		setTitle(I18NUtil.getMessages().ricevutePostaInUscitaWindow_title());  	

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		if(record != null) {

			Record lRecord = new Record();
			final String idEmail = record.getAttribute("idEmail");
			lRecord.setAttribute("idEmail", idEmail);			
			
			if(!AurigaLayout.getParametroDBAsBoolean("DETT_EMAIL_UNICO")) {

				portletLayout = new PostaElettronicaDetail("ricevutepostainuscita");

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

			}else{

				portletLayout = new DettaglioPostaElettronica("ricevutepostainuscita");

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

			GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RicevutePostaInUscitaDataSource", "idEmail", FieldType.TEXT);
			lGWTRestDataSource.addParam("idEmailRif", idEmailRif);
			lGWTRestDataSource.addParam("idDestinatario", idDestinatario);

			portletLayout = new RicevutePostaInUscitaLayout(lGWTRestDataSource) {
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
						_window.setTitle(I18NUtil.getMessages().ricevutePostaInUscitaWindow_title()); 
					} 	
				}				

				@Override
				public boolean isForcedToAutoSearch() {
					
					return true;
				}						
			};

			((RicevutePostaInUscitaLayout)portletLayout).setLookup(false);

		}

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("mail/mail-reply2.png");

		if(record == null) {
			_window.show();
		}
	}	

}
