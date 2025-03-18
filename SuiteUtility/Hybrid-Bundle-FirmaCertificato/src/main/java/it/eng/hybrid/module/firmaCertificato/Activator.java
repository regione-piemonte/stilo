/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.util.HybridActivator;
import it.eng.hybrid.module.firmaCertificato.resources.Resources;


/**
 *
 */
public class Activator extends HybridActivator {
	
	public final static Logger logger = Logger.getLogger(Activator.class);
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new FirmaCertificatoModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
	
}
