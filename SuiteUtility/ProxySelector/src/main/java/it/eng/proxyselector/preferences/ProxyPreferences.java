package it.eng.proxyselector.preferences;

import it.eng.proxyselector.ProxySelector;
import it.eng.proxyselector.ProxyUtils;
import it.eng.proxyselector.configuration.ProxyConfiguration;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;

import com.btr.proxy.selector.pac.PacProxySelector;
import com.btr.proxy.selector.pac.UrlPacScriptSource;

public class ProxyPreferences {

	private static final String PROXY_HOST = "proxy";
	private static final String PROXY_USERNAME = "username";
	private static final String PROXY_PORT = "porty";
	private static final String PROXY_PASSWORD = "password";
	private static final String PROXY_DOMINIO = "dominio";
	private static final String PROXY_NTLM = "ntlm";
	private static final String PROXY_USE_SCRIPT = "use_script";
	private static final String PROXY_SCRIPT = "script";
	
	public static Preferences getPreferences() {
		System.setProperty("java.util.prefs.PreferencesFactory", FilePreferencesFactory.class.getName());
		return Preferences.userNodeForPackage(ProxySelector.class);
	}

	public static ProxyConfiguration getSavedConfiguration() {
		System.setProperty("java.util.prefs.PreferencesFactory", FilePreferencesFactory.class.getName());
		Preferences lPreferences = Preferences.userNodeForPackage(ProxySelector.class);
		return new ProxyConfiguration(lPreferences.get(PROXY_HOST, ""),
				lPreferences.getInt(PROXY_PORT, 0),lPreferences.get(PROXY_DOMINIO, ""),
				lPreferences.get(PROXY_USERNAME, ""), lPreferences.get(PROXY_PASSWORD, "").toCharArray(),
				lPreferences.getBoolean(PROXY_NTLM, false), lPreferences.getBoolean(PROXY_USE_SCRIPT, false),
				lPreferences.get(PROXY_SCRIPT, ""));
	}
	
	public static void saveConfiguration(ProxyConfiguration pProxyConfiguration) {
		Preferences lPreferences = Preferences.userNodeForPackage(ProxySelector.class);
		lPreferences.put(PROXY_HOST, pProxyConfiguration.getProxy());
		lPreferences.put(PROXY_USERNAME, pProxyConfiguration.getUsername());
		lPreferences.putInt(PROXY_PORT, pProxyConfiguration.getPort());
		lPreferences.put(PROXY_PASSWORD, new String(pProxyConfiguration.getPassword()));
		lPreferences.put(PROXY_DOMINIO, pProxyConfiguration.getDominio());
		lPreferences.putBoolean(PROXY_NTLM, pProxyConfiguration.isUseNTLM());
		lPreferences.putBoolean(PROXY_USE_SCRIPT, pProxyConfiguration.isUseScript());
		lPreferences.put(PROXY_SCRIPT, pProxyConfiguration.getScript());
		try {
			lPreferences.flush();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
