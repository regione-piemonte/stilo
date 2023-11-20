/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.service.client.config.Configuration;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientInit {

	private static Logger mLogger = Logger.getLogger(ClientInit.class);
	
	@Autowired
	public void init() throws ServletException{
		try {
			Configuration.getInstance().initClient();
		} catch (Exception e) {
			mLogger.error("Errore " + e.getMessage(), e);
			throw new ServletException(e.getMessage(), e);
		}
		mLogger.debug("Init ok");
	}
}
