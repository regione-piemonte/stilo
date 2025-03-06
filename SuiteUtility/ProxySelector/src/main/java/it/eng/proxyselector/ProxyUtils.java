package it.eng.proxyselector;

import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.log4j.Logger;

import com.btr.proxy.selector.pac.PacProxySelector;
import com.btr.proxy.selector.pac.UrlPacScriptSource;

import it.eng.resources.Assets;


public class ProxyUtils {
	private static final Logger log = Logger.getLogger(ProxyUtils.class);
	
	private static SSLConnectionSocketFactory getSslConnectionSocketFactory() throws Exception{
		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		log.debug ("Cerco truststore.jks tramite ProxyUtils.class.getClassLoader().getResourceAsStream");
		InputStream keystore = ProxyUtils.class.getClassLoader().getResourceAsStream("truststore.jks");
		if (keystore == null){
			log.debug ("truststore.jks non trovato, lo cerco tramite Assets.class.getClassLoader().getResourceAsStream");
			keystore = Assets.class.getResourceAsStream("truststore.jks");
			if (keystore == null){
				log.debug ("truststore.jks non trovato");
			}
		}
		//Verifico la presenza di un jks
		SSLContext sslcontext = null; 
		if (keystore!=null){
			log.debug("keystore truststore.jks trovato");
			try {
				trustStore.load(keystore, "changeit".toCharArray());
			} finally {
				keystore.close();
			}
			sslcontext = SSLContexts.custom()
					.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
					.build();
		} else {
			sslcontext = SSLContexts.createDefault();
		}
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[] { "TLSv1", "TLSv1.1" , "TLSv1.2"},
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		return sslsf;
	}

	public static CloseableHttpClient resetProxy() {
		CloseableHttpClient httpclient;
		try {
			httpclient = HttpClients.custom().setSSLSocketFactory(getSslConnectionSocketFactory()).build();
			ProxyDefaultHttpClient.setClientToUse(httpclient);
			return httpclient;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static CloseableHttpClient resetProxy(String jSessionId, String domain, String path ) {
		CloseableHttpClient httpclient;
		try {
			CookieStore cookieStore = new BasicCookieStore();
			
			BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jSessionId );
			cookie.setDomain(domain);
		    cookie.setPath(path);
		    cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
			cookieStore.addCookie(cookie);
			
			httpclient = HttpClients
					.custom()
					.setDefaultCookieStore(cookieStore)
					.setSSLSocketFactory(getSslConnectionSocketFactory())
					.build();
			ProxyDefaultHttpClient.setClientToUse(httpclient);
			return httpclient;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static CloseableHttpClient setProxyNTLM(String proxyHost, int proxyPort, String username, char[] password, String domain) {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				AuthScope.ANY,
				new NTCredentials(username, password != null ? new String(password) : "", "", domain));
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		.build();
		// Create an HttpClient with the given custom dependencies and configuration.
		CloseableHttpClient httpclient;
		try {
			httpclient = HttpClients.custom()
			.setProxy(new HttpHost(proxyHost, proxyPort))
			.setDefaultRequestConfig(defaultRequestConfig).setDefaultCredentialsProvider(credsProvider)
			.setSSLSocketFactory(getSslConnectionSocketFactory()).build();

			ProxyDefaultHttpClient.setClientToUse(httpclient);
			return httpclient;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static CloseableHttpClient setProxyNTLM(String proxyHost, int proxyPort, String username, char[] password, String domain, 
			String jSessionId, String dominio, String path ) {
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				AuthScope.ANY,
				new NTCredentials(username, password != null ? new String(password) : "", "", domain));
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		.setCookieSpec(CookieSpecs.STANDARD)		
		.build();
		// Create an HttpClient with the given custom dependencies and configuration.
		CloseableHttpClient httpclient;
		try {
			CookieStore cookieStore = new BasicCookieStore();
			
			BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jSessionId );
			cookie.setDomain(domain);
		    cookie.setPath(path);
		    cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
			cookieStore.addCookie(cookie);
			
			httpclient = HttpClients.custom()
			.setProxy(new HttpHost(proxyHost, proxyPort))
			.setDefaultRequestConfig(defaultRequestConfig).setDefaultCredentialsProvider(credsProvider)
			.setDefaultCookieStore(cookieStore)
			.setSSLSocketFactory(getSslConnectionSocketFactory()).build();

			ProxyDefaultHttpClient.setClientToUse(httpclient);
			return httpclient;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static CloseableHttpClient setProxy(String host, int port, String username, char[] password) {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		.build();

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				AuthScope.ANY,
				new UsernamePasswordCredentials(username, new String(password)));

		// Create an HttpClient with the given custom dependencies and configuration.
		CloseableHttpClient httpclient;
		try {
			httpclient = HttpClients.custom()
			.setProxy(new HttpHost(host, port))
			.setDefaultRequestConfig(defaultRequestConfig).setDefaultCredentialsProvider(credsProvider)
			.setSSLSocketFactory(getSslConnectionSocketFactory()).build();
			ProxyDefaultHttpClient.setClientToUse(httpclient);
			return httpclient;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static CloseableHttpClient setProxy(String host, int port, String username, char[] password, 
			String jSessionId, String domain, String path ) {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
		.setCookieSpec(CookieSpecs.STANDARD)
		.build();

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				AuthScope.ANY,
				new UsernamePasswordCredentials(username, new String(password)));

		// Create an HttpClient with the given custom dependencies and configuration.
		CloseableHttpClient httpclient;
		try {
			CookieStore cookieStore = new BasicCookieStore();
			
			BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", jSessionId );
			cookie.setDomain(domain);
		    cookie.setPath(path);
		    cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
			cookieStore.addCookie(cookie);
			
			httpclient = HttpClients.custom()
			.setProxy(new HttpHost(host, port))
			.setDefaultRequestConfig(defaultRequestConfig).setDefaultCredentialsProvider(credsProvider)
			.setDefaultCookieStore(cookieStore)
			.setSSLSocketFactory(getSslConnectionSocketFactory()).build();
			ProxyDefaultHttpClient.setClientToUse(httpclient);
			return httpclient;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}


	public static CloseableHttpClient setProxyScript(ProxySelector selector, String remoteUrl, String username, char[] password) {
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
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static CloseableHttpClient setProxyScript(ProxySelector selector, String remoteUrl, String username, char[] password, 
			String jSessionId, String domain, String path ) {
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
					return setProxy(host , addr.getPort(), username, password, jSessionId, domain, path);
				}
			}
			return resetProxy(jSessionId, domain, path);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static CloseableHttpClient setProxyScriptNTLM(ProxySelector selector, String remoteUrl, String username, char[] password, String dominio, Object object) {
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
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static CloseableHttpClient setProxyScriptNTLM(ProxySelector selector, String remoteUrl, String username, char[] password, String dominio, Object object, 
			String jSessionId, String domain, String path ) {
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
					setProxyNTLM(host , addr.getPort(), username, password, dominio, jSessionId, domain, path);
				}
			}
			return resetProxy( jSessionId, domain, path);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static CloseableHttpClient createHttpClientForRemoteInvocation(
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
	
	public static CloseableHttpClient createHttpClientForRemoteInvocation(
			ProxyConfiguration configuration, String jSessionId, String domain, String path) {
		int porta = configuration.getPort();
		if (!StringUtils.isEmpty(configuration.getProxy())){
			if (configuration.isUseNTLM()){
				PacProxySelector pacProxySelector = new PacProxySelector(
						new UrlPacScriptSource(configuration.getScript()));
				if (configuration.isUseNTLM()){
					return setProxyScriptNTLM(pacProxySelector, "", configuration.getUsername(), configuration.getPassword(), 
							configuration.getDominio(), null, jSessionId, domain, path);
				} else {
					return setProxyScript(pacProxySelector, "", configuration.getUsername(), configuration.getPassword(), jSessionId, domain, path);
				}
			} else {
				if (configuration.isUseNTLM()){
					return setProxyNTLM(configuration.getProxy(), 
							porta, configuration.getUsername(), 
							configuration.getPassword(), configuration.getDominio(), jSessionId, domain, path);
				} else {
					return setProxy(configuration.getProxy(), 
							porta, configuration.getUsername(), 
							configuration.getPassword(), jSessionId, domain, path);
				}
			}
		} else {
			return resetProxy(jSessionId, domain, path);
		}
	}
	
}


