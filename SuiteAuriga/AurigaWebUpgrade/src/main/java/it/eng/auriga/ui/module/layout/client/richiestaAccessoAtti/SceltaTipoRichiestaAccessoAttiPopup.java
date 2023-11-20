/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

public class SceltaTipoRichiestaAccessoAttiPopup extends Window {

	private SceltaTipoRichiestaAccessoAttiPopup instance;
	private SceltaTipoRichiestaAccessoAttiForm form; 
	
	public SceltaTipoRichiestaAccessoAttiPopup(String idTipoDocDefault, ServiceCallback<Record> callback){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);		
		setHeight(110);
		setKeepInParentRect(true);
		setTitle("Seleziona tipo di richiesta accesso atti");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		form = new SceltaTipoRichiestaAccessoAttiForm(idTipoDocDefault, instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);	
		
		addItem(form);	
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
	}
	
}
