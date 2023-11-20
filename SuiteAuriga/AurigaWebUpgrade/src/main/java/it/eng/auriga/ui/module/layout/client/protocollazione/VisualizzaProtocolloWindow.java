/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;

public class VisualizzaProtocolloWindow extends ModalWindow {

	protected VisualizzaProtocolloWindow _window;

	protected Canvas portletLayout;
	protected Record record;

	public VisualizzaProtocolloWindow(String pNomeEntita, Record pRecord, String pFlgTipoProv) {

		super(pNomeEntita, false);

		_window = this;

		this.record = pRecord;

		portletLayout = buildPortletLayout(record);

		manageOperationOnLayout(record);

		portletLayout.setHeight100();
		portletLayout.setWidth100();

		setBody(portletLayout);

		setIcon("menu/" + ((ProtocollazioneDetail) portletLayout).getNomeEntita() + ".png");

	}

	protected Canvas buildPortletLayout(Record record) {
		return ProtocollazioneDetail.getInstance(record);
	}

	protected void manageOperationOnLayout(Record record) {
		((ProtocollazioneDetail) portletLayout).caricaDettaglio(null, record);
	}

}
