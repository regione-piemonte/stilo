/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

public class SceltaTipoFlussoIstanzaPopup extends Window {

	private SceltaTipoFlussoIstanzaPopup instance;
	private SceltaTipoFlussoIstanzaForm form; 
	
	public SceltaTipoFlussoIstanzaPopup(String tipoIstanza, ServiceCallback<Record> callback){
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);		
		setHeight(110);
		setKeepInParentRect(true);
		setTitle("Seleziona tipo flusso per l'istanza " + tipoIstanza);
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		form = new SceltaTipoFlussoIstanzaForm(tipoIstanza, instance, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);	
		
		addItem(form);	
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
	}
	
}