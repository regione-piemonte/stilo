package it.eng.areas.hybrid.module.builtin.assets;

import java.io.InputStream;


/**
 * Classe da usare come riferimento per scaricare gli assests
 * @author GioBo
 *
 */
public final class Assets {
	
	private Assets() {
		//nop
	}
	
	public static InputStream getResource(String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return Assets.class.getResourceAsStream(path);
	}
	

}
