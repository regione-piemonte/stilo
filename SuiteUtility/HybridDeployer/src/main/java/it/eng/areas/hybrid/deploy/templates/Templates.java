package it.eng.areas.hybrid.deploy.templates;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import it.eng.areas.hybrid.deploy.util.DeployerUtils;

/**
 * Classe da usare come riferimento per scaricare i template
 * @author GioBo
 *
 */
public final class Templates {
	
	private Templates() {
		//nop
	}
	
	public static InputStream getTemplate(String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		if (System.getProperty("debug.hybrid.template.path") != null) {
			try {
				return new FileInputStream(System.getProperty("debug.hybrid.template.path") + "/" + path);
			} catch (IOException e) {
				return null;
			}
		} else {
			return Templates.class.getResourceAsStream(path);
		}
	}
	
	public static InputStream getTemplate(String path, Map<String,String> values) throws IOException {
		String template = DeployerUtils.inputStreamToString(getTemplate(path));
		
		return DeployerUtils.stringToInputStream(DeployerUtils.replaceValues(template, values));
	}
	

}
