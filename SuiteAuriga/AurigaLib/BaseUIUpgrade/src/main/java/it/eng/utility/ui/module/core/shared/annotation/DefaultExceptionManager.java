/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import it.eng.utility.MessageUtil;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.service.ResponseBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;

public class DefaultExceptionManager extends ExceptionManager {

	@Override
	public void manageException(Throwable e,
			AbstractDataSource<?, ?> datasource, ResponseBean response) {
				
		String message = MessageUtil.getValue(e.getMessage());
		if(StringUtils.isNotBlank(message)) {
			datasource.addMessage(message, "", MessageType.ERROR);				
		} else {
			datasource.addMessage(e.getMessage(), "", MessageType.ERROR);				
		}		
		response.setData(new ArrayList<String>());
	}

	

}
