/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;


public class AttributiCustomWindow extends ModalWindow {

	private AttributiCustomWindow _window;
	
	private AttributiCustomDetail portletLayout;
	
		
	public AttributiCustomWindow(String nome) {
	
		super("attributi_custom", true);
		
		setTitle("Dettaglio attributo custom");  	
		
		_window = this;
							
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		   
		portletLayout = new AttributiCustomDetail("attributi_custom");
		portletLayout.setCanEdit(false);
		Record lRecord = new Record();
		lRecord.setAttribute("nome", nome);
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttributiCustomDataSource", "nome", FieldType.TEXT);
		lGwtRestDataSource.performCustomOperation("get", lRecord, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record record = response.getData()[0];
					portletLayout.editRecord(record);	
					portletLayout.getValuesManager().clearErrors(true);
					_window.setTitle("Dettaglio attributo custom " + record.getAttribute("nome"));  	
					_window.show();
				} 				
			}
		}, new DSRequest());
			
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
    
       	setIcon("menu/attibuti_custom.png");
               
	}
}