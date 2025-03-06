package it.eng.areas.hybrid.deploy;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;

public class HybridConfig {
	
	public final static Logger logger = Logger.getLogger(HybridConfig.class); 
	
	public static int versionCompare(String str1, String str2) {
	    String[] vals1 = str1.split("\\.");
	    String[] vals2 = str2.split("\\.");
	    int i = 0;
	    // set index to first non-equal ordinal or length of shortest version string
	    while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
	      i++;
	    }
	    // compare first non-equal ordinal number
	    if (i < vals1.length && i < vals2.length) {
	        int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
	        return Integer.signum(diff);
	    }
	    // the strings are equal or one string is a substring of the other
	    // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
	    return Integer.signum(vals1.length - vals2.length);
	}	
	
	public interface ModuleFactoryInterface {
		
		public InputStream getModuleBundleInputStream() throws Exception;
		
	}
	
	public static abstract class ModuleFactory implements ModuleFactoryInterface {
	    String bundleName;
	    String bundleVersion;
	    
	    
	  }
		
		
		
	public static class UrlModuleFactory extends ModuleFactory {
		
		
		protected URL url;

		public UrlModuleFactory(URL url) {
			this.url = url;
		}
		
		public URL getUrl() {
			return url;
		}

		@Override
		public InputStream getModuleBundleInputStream() throws Exception {
			logger.debug("Richiesto bundle da url "+url);
			return url.openStream();
		}
		
	}
	
	
	private static final String BUNDLE_NAME="Bundle-Name"; 
	private static final String BUNDLE_VERSION="Bundle-Version"; 
	
	private static HybridConfig instance;
	
	
	public static HybridConfig getInstance() {
		if (instance == null) {
			instance = new HybridConfig(); 
		}
		return instance;
	}
	
	
	Map<String,ModuleFactory> modules = new HashMap<String, ModuleFactory>();
	
	protected HybridConfig() {
		
	}
	
	public void registerModule(URL url) throws Exception {
		registerModule(new HybridConfig.UrlModuleFactory(url));
	}
	
	public void registerModule(ModuleFactory factory) throws Exception {
		logger.info("METODO registerModule");
		//Analalizzo il modulo per estrarne il nome e la versione
		JarInputStream jarInputStream = null;
		try {
			jarInputStream = new JarInputStream(factory.getModuleBundleInputStream());
			Manifest manifest = jarInputStream.getManifest();
			Attributes mainAttributes = manifest.getMainAttributes();
			String bundleName = (String) mainAttributes.get(new Attributes.Name(BUNDLE_NAME));
			String bundleVersion = (String) mainAttributes.get(new Attributes.Name(BUNDLE_VERSION));
			factory.bundleName = bundleName;
			factory.bundleVersion = bundleVersion;
			
			//Il bundle di default è sempre quello con versione maggiore 
			ModuleFactory oldFactory = modules.get(bundleName);
			if (oldFactory != null) {
				String oldBundleVersion = oldFactory.bundleVersion;
				if (versionCompare(bundleVersion, oldBundleVersion) >= 0) {
					modules.put(bundleName, factory);
				}
			} else {
				modules.put(bundleName, factory);
			}
			modules.put(bundleName+"["+bundleVersion+"]", factory);
			logger.info("Registrato Hybrid bundle "+bundleName+"["+bundleVersion+"]");
		}catch (Exception e) {
			e.printStackTrace(); 
		} finally {
			if( jarInputStream!=null )
				jarInputStream.close();
		}
	}
	
	
	public InputStream getModuleData(String moduleName, String version) throws Exception {
		String key = (version != null) ? moduleName+"["+version+"]" : moduleName;
		ModuleFactory factory =  modules.get(key);
		if (factory != null) {
			return factory.getModuleBundleInputStream();
		}
		throw new IllegalArgumentException(key + " not found");
	}
	
	public InputStream getModuleData(String moduleName) throws Exception {
		return getModuleData(moduleName, null);
	}

}
