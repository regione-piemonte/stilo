package it.eng.areas.hybrid.module.siss.resources;

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
