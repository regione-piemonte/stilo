/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class StatisticheMailWindow extends ModalWindow {

	private StatisticheMailDetail portletLayout;
	
	public StatisticheMailWindow() {
		
		super("statisticheMail", false);
		
		setTitle(I18NUtil.getMessages().statisticheMail_window_title());

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
						
		portletLayout = new StatisticheMailDetail();
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
		setWidth(880);
		setHeight(640);
		
		setIcon("menu/statisticheMail.png");
				
		setShowItem();
		
		portletLayout.markForRedraw();
	}	

	public void setShowItem() {
		
		// Mostra/nasconde la combo ENTE/AOO
		final GWTRestDataSource ds =  new GWTRestDataSource("ReportDocAvanzatiDatasource");
		Record record = new Record();
		ds.executecustom("isAbilEnteAooFiltro", record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {				
				if(response.getStatus()==DSResponse.STATUS_SUCCESS)
				{					
					Record record = response.getData()[0];
					if (record.getAttributeAsBoolean("result")){						
						portletLayout.setVisibleEnteAooItem(record.getAttributeAsBoolean("esito"));	
						portletLayout.setVisibleRaggruppaEnteAooItem(record.getAttributeAsBoolean("esito"));
						// il form uoReportForm è visibile solo se la select Ente/AOO non si vede.
						portletLayout.setVisibleAssegnazioneUoForm(record.getAttributeAsBoolean("esito") == false);
						
					}
					else {						
						Layout.addMessage(new MessageBean(record.getAttribute("error"), record.getAttribute("error"), MessageType.ERROR));
					}					
				}				
			}
		});						
	}
}