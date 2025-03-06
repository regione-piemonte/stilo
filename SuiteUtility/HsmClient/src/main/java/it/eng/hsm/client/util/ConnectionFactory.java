package it.eng.hsm.client.util;

import it.eng.hsm.client.config.ProxyConfig;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;

public class ConnectionFactory  implements HttpURLConnectionFactory {

	
    public ConnectionFactory(ProxyConfig proxyConfig) {
		super();
		this.proxyConfig = proxyConfig;
	}

	Proxy proxy;
	ProxyConfig proxyConfig;

    private void initializeProxy() {
        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyConfig.getProxyHost(), Integer.parseInt(proxyConfig.getProxyPort())));
        
        Authenticator.setDefault(new ProxyAuthenticator( proxyConfig.getProxyUser(), proxyConfig.getProxyPassword() ));
    }

    public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        initializeProxy();
        return (HttpURLConnection) url.openConnection(proxy);
    }
}
