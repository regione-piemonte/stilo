/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.areas.hybrid.module.util.ResourceHelper;

public class Resources extends ResourceHelper {
	
	private static Resources instance;
	
	public static void start(String moduleName) {
		instance = new Resources(moduleName);
	}
	
	public static Resources getInstance() {
		return instance;
	}

	protected Resources(String moduleName) {
		super(moduleName);
	}

}
