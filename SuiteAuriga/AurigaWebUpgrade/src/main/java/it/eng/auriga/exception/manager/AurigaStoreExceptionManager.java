/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.service.ResponseBean;
import it.eng.utility.ui.module.core.shared.annotation.ExceptionManager;
import it.eng.utility.ui.module.core.shared.message.MessageType;

import java.util.ArrayList;

public class AurigaStoreExceptionManager extends ExceptionManager {
	
	@Override
	public void manageException(Throwable e,
			AbstractDataSource<?, ?> datasource, ResponseBean response) {
		String errorMessage = e.getMessage() != null ? e.getMessage() : "Errore generico";
		if(!errorMessage.equals("CONNECTION_CLOSED")) {
			datasource.addMessage(e.getMessage(), "", MessageType.ERROR);
		}
		response.setData(new ArrayList<String>());
	}

}