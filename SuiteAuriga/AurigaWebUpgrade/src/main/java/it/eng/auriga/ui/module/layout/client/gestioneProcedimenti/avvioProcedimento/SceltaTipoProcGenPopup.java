/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

public class SceltaTipoProcGenPopup extends Window {

	private SceltaTipoProcGenPopup instance;
	private SceltaTipoProcGenForm form;

	public SceltaTipoProcGenPopup(ServiceCallback<Record> callback) {
		this(null, null, callback);
	}

	public SceltaTipoProcGenPopup(String flgDocFolder, String tipo, final ServiceCallback<Record> callback) {

		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(110);
		setKeepInParentRect(true);
		setTitle("Seleziona iter da avviare");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);

		form = new SceltaTipoProcGenForm(flgDocFolder, tipo, instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);

		addItem(form);

		setShowTitle(true);
		setHeaderIcon("blank.png");

	}

}
