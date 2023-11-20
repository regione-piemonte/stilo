/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.protocollazione.ProtocollazioneDetail;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ModificaDatiExtraIterRegProtAssociatoWindow extends ModalWindow {

	private ModificaDatiExtraIterRegProtAssociatoWindow _window;

	private ProtocollazioneDetail portletLayout;

	public ModificaDatiExtraIterRegProtAssociatoWindow(Record record, String title) {
		this(record, null, title);
	}
	
	public ModificaDatiExtraIterRegProtAssociatoWindow(Record record, String taskName, String title) {
		this(record, taskName, title, null);
	}
		
	public ModificaDatiExtraIterRegProtAssociatoWindow(Record record, String taskName, String title, ServiceCallback<Record> afterUpdateCallback) {
	
		super("modificadatiextraiterregprotassociato", true);

		setTitle(title);

		_window = this;

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
				
		loadDettUD(record, taskName, afterUpdateCallback);
		
		setIcon("blank.png");
	}

	private void loadDettUD(Record record, String taskName, final ServiceCallback<Record> afterUpdateCallback) {
	
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("ProtocolloDataSource");
		if(taskName != null && !"".equals(taskName)) {
			lGwtRestDataSource.addParam("taskName", taskName);
		}
		Record lRecordToLoad = new Record();
		lRecordToLoad.setAttribute("idUd", record.getAttribute("idUd"));
		lGwtRestDataSource.getData(lRecordToLoad, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record lRecord = response.getData()[0];		
					if(lRecord.getAttributeAsBoolean("abilModificaDatiExtraIter") != null &&
					   lRecord.getAttributeAsBoolean("abilModificaDatiExtraIter")) {
						portletLayout = ProtocollazioneDetail.getInstance(lRecord, afterUpdateCallback);
						portletLayout.caricaDettaglio(null, lRecord);					
						portletLayout.getValuesManager().clearErrors(true);
						portletLayout.setHeight100();
						portletLayout.setWidth100();
						portletLayout.modificaDatiExtraIterMode();					
						setBody(portletLayout);
						_window.show();
					} else {
						Layout.addMessage(new MessageBean("Non è configurato alcun dato modificabile in questa sede","",MessageType.WARNING));
					}
				}
			}
		});
	}
	
}