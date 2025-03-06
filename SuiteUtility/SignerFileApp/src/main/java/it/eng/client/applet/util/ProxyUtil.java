package it.eng.client.applet.util;

import it.eng.common.LogWriter;
import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyUtil {
	
	public static  void initProxyConnection(){
		ProxyConfiguration lProxyConfiguration = ProxyDefaultHttpClient.getConfiguration();
		
		String proxyHost = lProxyConfiguration.getProxy();
		LogWriter.writeLog("Proprietà proxyHost=" + proxyHost );
		String proxyPort = lProxyConfiguration.getPort()+"";
		LogWriter.writeLog("Proprietà proxyPort=" + proxyPort );
		String proxyUser=lProxyConfiguration.getUsername();
		LogWriter.writeLog("Proprietà proxyUser=" + proxyUser );
		String proxyPassword=lProxyConfiguration.getPassword()!=null?new String(lProxyConfiguration.getPassword()):"";
		LogWriter.writeLog("Proprietà proxyPassword=" + proxyPassword );
		
		 	// if( System.getProperty("http.proxyHost")==null || System.getProperty("http.proxyHost").trim().equals(""))
	    		 System.setProperty("http.proxyHost", proxyHost );
	    	// if( System.getProperty("http.proxyPort")==null || System.getProperty("http.proxyPort").trim().equals("") )
	    		 System.setProperty("http.proxyPort", proxyPort);
	    	// if( System.getProperty("http.proxyUser")==null || System.getProperty("http.proxyUser").trim().equals(""))
	    		 System.setProperty("http.proxyUser", proxyUser );
	    	// if( System.getProperty("http.proxyPassword")==null || System.getProperty("http.proxyPassword").trim().equals("") )
	    		 System.setProperty("http.proxyPassword",  proxyPassword );
	     if(proxyHost!=null && !proxyHost.equals("")) {
	    	 Proxy proxy = createProxy(Proxy.Type.HTTP, proxyHost, Integer.parseInt( proxyPort ));
	    	 if(proxyUser!=null && !proxyUser.equals(""))
	    		 Authenticator.setDefault(new ProxyAuthenticator( proxyUser, proxyPassword ));
	     } else {
	    	 LogWriter.writeLog("Authenticator eliminato");
	    	 Authenticator.setDefault(null);
	     }
	}
	
	 
	
	public static Proxy createProxy(Proxy.Type proxyType, String proxyHost, int proxyPort) {
		if( proxyType==null)
			return Proxy.NO_PROXY;
		else {
			return new Proxy(proxyType, new InetSocketAddress(proxyHost, proxyPort));
		}
	}
}
