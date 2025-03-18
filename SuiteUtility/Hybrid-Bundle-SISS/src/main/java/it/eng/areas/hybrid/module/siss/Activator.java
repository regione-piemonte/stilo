/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.siss.resources.Resources;
import it.eng.areas.hybrid.module.util.HybridActivator;

public class Activator extends HybridActivator {
	
	protected void addModuleManager(IClientModuleManager moduleManager) {
		try {
			Resources.start(getBundleName());
			moduleManager.registerModule(new SissMultiClientModule(getBundleName(), getBundleVersion()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
