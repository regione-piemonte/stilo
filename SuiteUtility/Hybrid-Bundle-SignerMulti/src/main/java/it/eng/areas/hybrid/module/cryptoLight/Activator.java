package it.eng.areas.hybrid.module.cryptoLight;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.cryptoLight.resources.Resources;
import it.eng.areas.hybrid.module.util.HybridActivator;

/**
 * ATTENZIONE OSGi non riesce ad accedere alla classe del provider!
 * Questa libreria va caricata come jar NON OSGi
 * @author GioBo
 *
 */
public class Activator extends HybridActivator {
	
	public final static Logger logger = Logger.getLogger(Activator.class);
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new SignerMultiClientModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
	
}
