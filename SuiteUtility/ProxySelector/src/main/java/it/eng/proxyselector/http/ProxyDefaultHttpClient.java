package it.eng.proxyselector.http;

import it.eng.proxyselector.ProxyBuilderUtils;
import it.eng.proxyselector.ProxyUtils;
import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.preferences.ProxyPreferences;

import java.io.InputStream;
import java.util.prefs.Preferences;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ProxyDefaultHttpClient {

	public static Preferences preferences = ProxyPreferences.getPreferences();
	public static ProxyConfiguration configuration = ProxyPreferences.getSavedConfiguration();
	private static CloseableHttpClient clientToUse = null;// ProxyUtils.createHttpClientForRemoteInvocation(configuration);
	private static HttpClientBuilder clientBuilderToUse = null;
	
	public static void setClientToUse(CloseableHttpClient clientToUse) {
		ProxyDefaultHttpClient.clientToUse = clientToUse;
	}
	
	public static void setClientBuilderToUse(HttpClientBuilder clientBuilderToUse) {
		ProxyDefaultHttpClient.clientBuilderToUse = clientBuilderToUse;
	}

	public static CloseableHttpClient getClientToUse() {
		if (clientToUse == null) return ProxyUtils.createHttpClientForRemoteInvocation(configuration);
		return clientToUse;
	}
	
	public static CloseableHttpClient getClientToUse(String jSessionId, String domain, String path) {
		clientToUse = ProxyUtils.createHttpClientForRemoteInvocation(configuration, jSessionId, domain, path);
		return clientToUse;
	}
	
	public static HttpClientBuilder getClientBuilderToUse() {
		if (clientBuilderToUse == null) return ProxyBuilderUtils.createHttpClientForRemoteInvocation(configuration);
		return clientBuilderToUse;
	}

	public static void setConfiguration(ProxyConfiguration configuration) {
		ProxyDefaultHttpClient.configuration = configuration;
	}

	public static ProxyConfiguration getConfiguration() {
		return configuration;
	}
	
	
}
