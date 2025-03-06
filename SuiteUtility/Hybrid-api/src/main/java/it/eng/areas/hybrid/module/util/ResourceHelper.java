package it.eng.areas.hybrid.module.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Classe da usare come riferimento per scaricare le risorse
 * @author GioBo
 *
 */
public class ResourceHelper {
	protected final String moduleName;
	
	protected ResourceHelper(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public InputStream getResource(String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		if (moduleName != null && System.getProperty("debug.hybrid."+moduleName+".path") != null) {
			try {
				return new FileInputStream(System.getProperty("debug.hybrid."+moduleName+".path") + "/" + path);
			} catch (IOException e) {
				e.printStackTrace(); //Siamo sicuramente in un ambiente di sviluppo
				return null;
			}
		} else {
			return this.getClass().getResourceAsStream(path);
		}
	}
	
	

}
