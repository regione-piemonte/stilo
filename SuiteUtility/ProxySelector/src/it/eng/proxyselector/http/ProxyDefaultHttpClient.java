package it.eng.proxyselector.http;

import it.eng.proxyselector.ProxyUtils;
import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.preferences.ProxyPreferences;

import java.util.prefs.Preferences;

import org.apache.http.impl.client.CloseableHttpClient;

public class ProxyDefaultHttpClient {

	public static Preferences preferences = ProxyPreferences.getPreferences();
	public static ProxyConfiguration configuration = ProxyPreferences.getSavedConfiguration();
	private static CloseableHttpClient clientToUse = null;// ProxyUtils.createHttpClientForRemoteInvocation(configuration);
	
	public static void setClientToUse(CloseableHttpClient clientToUse) {
		ProxyDefaultHttpClient.clientToUse = clientToUse;
	}

	public static CloseableHttpClient getClientToUse() {
		if (clientToUse == null) return ProxyUtils.createHttpClientForRemoteInvocation(configuration);
		return clientToUse;
	}

	public static void setConfiguration(ProxyConfiguration configuration) {
		ProxyDefaultHttpClient.configuration = configuration;
	}

	public static ProxyConfiguration getConfiguration() {
		return configuration;
	}
	
	
}
