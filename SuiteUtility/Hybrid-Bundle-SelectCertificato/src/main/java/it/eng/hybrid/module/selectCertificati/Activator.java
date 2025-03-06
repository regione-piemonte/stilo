package it.eng.hybrid.module.selectCertificati;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.util.HybridActivator;
import it.eng.hybrid.module.selectCertificati.resources.Resources;


/**
 *
 */
public class Activator extends HybridActivator {
	
	public final static Logger logger = Logger.getLogger(Activator.class);
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new SelectCertificatiModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
	
}
