/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class EditaProtocolloWindowFromRegEmergenza extends ModalWindow {

	protected EditaProtocolloWindowFromRegEmergenza _window;

	protected ProtocollazioneDetail portletLayout;
	protected Record record;
	
	private String idRegEmergenza;
	private DSCallback callbackOnClose;

	public EditaProtocolloWindowFromRegEmergenza(String pNomeEntita, Record pRecord, String pFlgTipoProv, String pIdRegEmergenza, DSCallback pCallbackOnClose) {

		super(pNomeEntita, false);

		_window = this;

		setTitle("Protocollazione registrazione di emergenza");
		
		this.record = pRecord;
		this.idRegEmergenza = pIdRegEmergenza;
		this.callbackOnClose = pCallbackOnClose;

		portletLayout = buildPortletLayout(pFlgTipoProv);
		portletLayout.nuovoDettaglioRegEmergenza(record);
		portletLayout.protocollaRegEmergenzaMode();
		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);
		setIcon("menu/" + portletLayout.getNomeEntita() + ".png");
	}

	protected ProtocollazioneDetail buildPortletLayout(String flgTipoProv) {

		if (flgTipoProv != null) {
			if ("E".equals(flgTipoProv)) {
				return ProtocollazioneUtil.buildProtocollazioneDetailEntrata(null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						afterRegistraRegEmergenza(object);
					}
				});
			} else if ("U".equals(flgTipoProv)) {
				return ProtocollazioneUtil.buildProtocollazioneDetailUscita(null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						afterRegistraRegEmergenza(object);
					}
				});
			} else if ("I".equals(flgTipoProv)) {
				return ProtocollazioneUtil.buildProtocollazioneDetailInterna(null, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						afterRegistraRegEmergenza(object);
					}
				});
			}
		}
		return ProtocollazioneUtil.buildProtocollazioneDetailBozze(null, null, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				afterRegistraRegEmergenza(object);
			}
		});
	}

	public void afterRegistraRegEmergenza(Record record) {

		record.setAttribute("idRegEmergenza", idRegEmergenza);
		final GWTRestDataSource lGwtRestDataSourceProtocollo = new GWTRestDataSource("ProtocollazioneRegEmergenzaDatasource");
		lGwtRestDataSourceProtocollo.addData(record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					portletLayout.viewMode();
				}
			}
		});
	}

	@Override
	public void manageOnCloseClick() {

		_window.markForDestroy();
		callbackOnClose.execute(new DSResponse(), null, new DSRequest());
	}

}
