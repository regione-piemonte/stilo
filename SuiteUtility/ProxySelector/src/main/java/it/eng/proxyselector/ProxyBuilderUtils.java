package it.eng.proxyselector;

import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.Proxy.Type;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.btr.proxy.selector.pac.PacProxySelector;
import com.btr.proxy.selector.pac.UrlPacScriptSource;

public class ProxyBuilderUtils {
	private static final Log log = LogFactory.getLog(ProxyBuilderUtils.class);

	public static HttpClientBuilder resetProxy(){
		HttpClientBuilder httpclient = HttpClients.custom();
		ProxyDefaultHttpClient.setClientBuilderToUse(httpclient);
		return httpclient;
	}

	public static HttpClientBuilder setProxyNTLM(String proxyHost, int proxyPort, String username, char[] password, String domain) {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				AuthScope.ANY,
				new NTCredentials(username, password != null ? new String(password) : "", "", domain));
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		.build();
		// Create an HttpClient with the given custom dependencies and configuration.
		HttpClientBuilder httpclient = HttpClients.custom()
		.setProxy(new HttpHost(proxyHost, proxyPort))
		.setDefaultRequestConfig(defaultRequestConfig).setDefaultCredentialsProvider(credsProvider);
		ProxyDefaultHttpClient.setClientBuilderToUse(httpclient);
		return httpclient;
	}

	public static HttpClientBuilder setProxy(String host, int port, String username, char[] password) {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		.build();

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				AuthScope.ANY,
				new UsernamePasswordCredentials(username, new String(password)));

		// Create an HttpClient with the given custom dependencies and configuration.
		HttpClientBuilder httpclient = HttpClients.custom()
		.setProxy(new HttpHost(host, port))
		.setDefaultRequestConfig(defaultRequestConfig).setDefaultCredentialsProvider(credsProvider);
		ProxyDefaultHttpClient.setClientBuilderToUse(httpclient);
		return httpclient;
	}


	public static HttpClientBuilder setProxyScript(ProxySelector selector, String remoteUrl, String username, char[] password) {
		if (selector == null) {
			throw new RuntimeException("  La modalita' selezionata non puo' essere applicata.\n");
		}
		try {
			List<Proxy> proxies = selector.select(new URI(remoteUrl));
			for (Proxy p : proxies) {
				System.out.println(p.type());
				if (p.type() == Type.HTTP
						&& p.address() instanceof InetSocketAddress) {
					InetSocketAddress addr = (InetSocketAddress) p.address();
					String host=addr.isUnresolved() ? addr.getHostName() : addr
							.getAddress().getHostAddress();
					log.debug ("  Verra' usato il proxy "   + host + ":" + addr .getPort());
					return setProxy(host , addr.getPort(), username, password);
				}
			}
			return resetProxy();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static HttpClientBuilder setProxyScriptNTLM(ProxySelector selector, String remoteUrl, String username, char[] password, String dominio, Object object) {
		if (selector == null) {
			throw new RuntimeException("  La modalita' selezionata non puo' essere applicata.\n");
		}
		try {
			List<Proxy> proxies = selector.select(new URI(remoteUrl));
			for (Proxy p : proxies) {
				System.out.println(p.type());
				if (p.type() == Type.HTTP
						&& p.address() instanceof InetSocketAddress) {
					InetSocketAddress addr = (InetSocketAddress) p.address();
					String host=addr.isUnresolved() ? addr.getHostName() : addr
							.getAddress().getHostAddress();
					log.debug ("  Verra' usato il proxy "   + host + ":" + addr .getPort());
					setProxyNTLM(host , addr.getPort(), username, password, dominio);
				}
			}
			return resetProxy();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static HttpClientBuilder createHttpClientForRemoteInvocation(
			ProxyConfiguration configuration) {
		int porta = configuration.getPort();
		if (!StringUtils.isEmpty(configuration.getProxy())){
			if (configuration.isUseNTLM()){
				PacProxySelector pacProxySelector = new PacProxySelector(
						new UrlPacScriptSource(configuration.getScript()));
				if (configuration.isUseNTLM()){
					return setProxyScriptNTLM(pacProxySelector, "", configuration.getUsername(), configuration.getPassword(), 
							configuration.getDominio(), null);
				} else {
					return setProxyScript(pacProxySelector, "", configuration.getUsername(), configuration.getPassword());
				}
			} else {
				if (configuration.isUseNTLM()){
					return setProxyNTLM(configuration.getProxy(), 
							porta, configuration.getUsername(), 
							configuration.getPassword(), configuration.getDominio());
				} else {
					return setProxy(configuration.getProxy(), 
							porta, configuration.getUsername(), 
							configuration.getPassword());
				}
			}
		} else {
			return resetProxy();
		}
	}
}


