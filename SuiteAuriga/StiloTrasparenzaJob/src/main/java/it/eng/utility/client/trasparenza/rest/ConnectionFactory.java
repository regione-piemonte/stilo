/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;

public class ConnectionFactory implements HttpURLConnectionFactory {
	
	ProxyConfig proxyConfig;
	Proxy proxy;
	
	public ConnectionFactory(ProxyConfig proxyConfig) {
		 super();
		 this.proxyConfig = proxyConfig;
	}
	
	private void initializeProxy() {
		proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyConfig.getProxyUrl(), Integer.parseInt(proxyConfig.getProxyPort())));
	}
	
	@Override
	public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
		initializeProxy();
        return (HttpURLConnection) url.openConnection(proxy);
	}
	
}
