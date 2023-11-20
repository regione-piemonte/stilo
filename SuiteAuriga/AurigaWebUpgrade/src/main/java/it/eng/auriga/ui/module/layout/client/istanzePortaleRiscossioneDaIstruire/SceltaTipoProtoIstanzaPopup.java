/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

/**
 * 
 * @author DANCRIST
 *
 */

public class SceltaTipoProtoIstanzaPopup extends Window {

	private SceltaTipoProtoIstanzaPopup instance;
	private SceltaTipoProtoIstanzaForm form;

	public SceltaTipoProtoIstanzaPopup(final ServiceCallback<Record> callback) {
		
		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(350);
		setHeight(150);
		setKeepInParentRect(true);
		setTitle("Seleziona stato protocollazione istanza");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);

		form = new SceltaTipoProtoIstanzaForm(instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);

		addItem(form);

		setShowTitle(true);
		setHeaderIcon("menu/istanze_portale_riscossione_da_istruire.png");

	}
		
}