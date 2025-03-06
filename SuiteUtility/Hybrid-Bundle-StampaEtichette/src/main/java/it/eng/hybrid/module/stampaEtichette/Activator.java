package it.eng.hybrid.module.stampaEtichette;

import it.eng.areas.hybrid.module.IClientModuleManager;

import it.eng.hybrid.module.stampaEtichette.resources.Resources;
import it.eng.areas.hybrid.module.util.HybridActivator;

/**
 * ATTENZIONE OSGi non riesce ad accedere alla classe del provider!
 * Questa libreria va caricata come jar NON OSGi
 *
 */
public class Activator extends HybridActivator {
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new StampaEtichetteClientModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
