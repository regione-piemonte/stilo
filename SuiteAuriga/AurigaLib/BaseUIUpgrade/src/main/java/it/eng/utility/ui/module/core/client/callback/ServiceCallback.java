/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.error.ErrorWindow;

import com.smartgwt.client.data.DSResponse;

/**
 * Interfaccia di callback
 * @author michele
 *
 * @param <E>
 */
public abstract class ServiceCallback<Record> {

	public abstract void execute(Record object); 
	
	public void manageError(DSResponse response, GWTRestDataSource lGWTRestDataSource){
//		Layout.addMessage(new MessageBean(
//				"Errore nella chiamata al metodo call per la datasource " + lGWTRestDataSource.extraparam.get("sourceidobject"),
//				"Errore nella chiamata al metodo call per la datasource " + lGWTRestDataSource.extraparam.get("sourceidobject"),
//				MessageType.ERROR));
	};
	
	public void manageError(Exception lException){
		ErrorWindow lErrorWindow = new ErrorWindow(lException);
		lErrorWindow.show();
	}

	public void manageError() {
		
		
	};
	
}
