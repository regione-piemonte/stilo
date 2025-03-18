/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.util.HybridActivator;
import it.eng.hybrid.module.jpedal.resources.Resources;

/**
 *
 */
public class Activator extends HybridActivator {
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new JPedalClientModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
