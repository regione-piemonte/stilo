package it.eng.areas.hybrid.module.cryptoLight.util;

import it.eng.proxyselector.configuration.ProxyConfiguration;
import it.eng.proxyselector.http.ProxyDefaultHttpClient;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.Proxy;

import org.apache.log4j.Logger;

public class ProxyUtil {
	
	public final static Logger logger = Logger.getLogger( ProxyUtil.class );
	
	public static  void initProxyConnection(){
		ProxyConfiguration lProxyConfiguration = ProxyDefaultHttpClient.getConfiguration();
		
		String proxyHost = lProxyConfiguration.getProxy();
		logger.info("Proprietà proxyHost=" + proxyHost );
		String proxyPort = lProxyConfiguration.getPort()+"";
		logger.info("Proprietà proxyPort=" + proxyPort );
		String proxyUser=lProxyConfiguration.getUsername();
		logger.info("Proprietà proxyUser=" + proxyUser );
		String proxyPassword=lProxyConfiguration.getPassword()!=null?new String(lProxyConfiguration.getPassword()):"";
		logger.info("Proprietà proxyPassword=" + proxyPassword );
		
		 	// if( System.getProperty("http.proxyHost")==null || System.getProperty("http.proxyHost").trim().equals(""))
	    		 System.setProperty("http.proxyHost", proxyHost );
	    		 System.setProperty("https.proxyHost", proxyHost );
	    	// if( System.getProperty("http.proxyPort")==null || System.getProperty("http.proxyPort").trim().equals("") )
	    		 System.setProperty("http.proxyPort", proxyPort);
	    		 System.setProperty("https.proxyPort", proxyPort);
	    	// if( System.getProperty("http.proxyUser")==null || System.getProperty("http.proxyUser").trim().equals(""))
	    		 System.setProperty("http.proxyUser", proxyUser );
	    		 System.setProperty("https.proxyUser", proxyUser );
	    	// if( System.getProperty("http.proxyPassword")==null || System.getProperty("http.proxyPassword").trim().equals("") )
	    		 System.setProperty("http.proxyPassword",  proxyPassword );
	    		 System.setProperty("https.proxyPassword",  proxyPassword );
	     if(proxyHost!=null && !proxyHost.equals("")) {
	    	 Proxy proxy = createProxy(Proxy.Type.HTTP, proxyHost, Integer.parseInt( proxyPort ));
	    	 
	    	 if(proxyUser!=null && !proxyUser.equals(""))
	    		 Authenticator.setDefault(new ProxyAuthenticator( proxyUser, proxyPassword ));
	     } else {
	    	 logger.info("Authenticator eliminato");
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
