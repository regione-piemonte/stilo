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

public class SceltaCommissionePopup extends Window {

	private SceltaCommissionePopup instance;
	private SceltaCommissioneForm form;
	
	public SceltaCommissionePopup(Record[] listaCommissioni, final ServiceCallback<Record> callback) {
		this(listaCommissioni, null, callback);
	}

	public SceltaCommissionePopup(Record[] listaCommissioni, String listaIdCommissioniConvocateSalvate, final ServiceCallback<Record> callback) {

		instance = this;

		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(100);
		setKeepInParentRect(true);
		setTitle("Seleziona commissione/i da convocare");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);

		form = new SceltaCommissioneForm(listaCommissioni, listaIdCommissioniConvocateSalvate, instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);

		addItem(form);

		setShowTitle(true);
		setHeaderIcon("menu/commissione.png");
		
		show();
	}
}