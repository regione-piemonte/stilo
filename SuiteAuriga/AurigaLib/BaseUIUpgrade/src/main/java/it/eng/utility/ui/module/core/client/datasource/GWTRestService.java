/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.Layout;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class GWTRestService<E,T> extends GWTRestDataSource {

	public GWTRestService(String serverid) {
		super(serverid, "key", FieldType.TEXT);		
	}

	public void call(Record record,final ServiceCallback<Record> callback){		
		this.addData(record,new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus()==DSResponse.STATUS_SUCCESS) {
					try {
						Record record = response.getData()[0];
						callback.execute(record);
					} catch(Exception e) {
						Layout.hideWaitPopup();				
					}
				} else {
					Layout.hideWaitPopup();
					callback.manageError();
				}
			}
		});		
	}
}