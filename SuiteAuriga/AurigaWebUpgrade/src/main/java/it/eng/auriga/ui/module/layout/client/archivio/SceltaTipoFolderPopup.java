/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Window;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

public class SceltaTipoFolderPopup extends Window {

	private SceltaTipoFolderPopup instance;
	private SceltaTipoFolderForm form; 
	
	public SceltaTipoFolderPopup(boolean required, String idFolderTypeDefault,String descType, Record record, ServiceCallback<Record> callback) {
		
		instance = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);		
		setHeight(110);
		setKeepInParentRect(true);
		setTitle("Seleziona tipologia");
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		form = new SceltaTipoFolderForm(instance, required, idFolderTypeDefault, descType, record, callback);
		form.setHeight100();
		form.setAlign(Alignment.CENTER);	
		
		addItem(form);	
		
		setShowTitle(true);
		setHeaderIcon("blank.png");
		
	}
	
}
