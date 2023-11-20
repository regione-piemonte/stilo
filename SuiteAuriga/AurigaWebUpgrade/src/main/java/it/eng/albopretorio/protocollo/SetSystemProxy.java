/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.albopretorio.bean.ProxyBean;

import org.apache.log4j.Logger;

public class SetSystemProxy {
	
	Logger logger = Logger.getLogger(SetSystemProxy.class);
	
	public boolean impostaProxy(ProxyBean impostazioniProxy) {
		
		String proxySet = "";
		String proxyHost = "";
		String proxyPort = "";
		String proxyUser = "";
		String proxyPassword = "";
		String nonProxyHosts = "";
		
		if (impostazioniProxy != null) {
			proxySet = impostazioniProxy.getProxySet();
			proxyHost = impostazioniProxy.getProxyHost();
			proxyPort = impostazioniProxy.getProxyPort();
			proxyUser = impostazioniProxy.getProxyUser();
			proxyPassword = impostazioniProxy.getProxyPassword();
			nonProxyHosts = impostazioniProxy.getNonProxyHosts();
		}
		try {
			if (proxySet == null || "".equalsIgnoreCase(proxySet))  {
				ReadProperties rp=new ReadProperties();
				proxySet = rp.getProperty("PROXY_SET");
				logger.debug("ProxySet(da file properties): " +proxySet);
			}
			if (proxyHost == null || "".equalsIgnoreCase(proxyHost))  {
				ReadProperties rp=new ReadProperties();
				proxyHost = rp.getProperty("PROXY_HOST");
				logger.debug("ProxyHost(da file properties): " +proxyHost);
			}
			if (proxyPort == null || "".equalsIgnoreCase(proxyPort))  {
				ReadProperties rp=new ReadProperties();
				proxyPort = rp.getProperty("PROXY_PORT");
				logger.debug("ProxyPort(da file properties): " +proxyPort);
			}
			if (proxyUser == null || "".equalsIgnoreCase(proxyUser))  {
				ReadProperties rp=new ReadProperties();
				proxyUser = rp.getProperty("PROXY_USER");
				logger.debug("ProxyUser(da file properties): " +proxyUser);
			}
			if (proxyPassword == null || "".equalsIgnoreCase(proxyPassword))  {
				ReadProperties rp=new ReadProperties();
				proxyPassword = rp.getProperty("PROXY_PASSWORD");
				logger.debug("ProxyPassword(da file properties): " +proxyPassword);
			}
			if (nonProxyHosts == null || "".equalsIgnoreCase(nonProxyHosts))  {
				ReadProperties rp=new ReadProperties();
				nonProxyHosts = rp.getProperty("NON_PROXY_HOST");
				logger.debug("NonProxyHost(da file properties): " +nonProxyHosts);
			}
		} catch (Exception e) {
			logger.error("Errore nel reperimento delle variabili da file di properties: " + e.getMessage());
		}
		
		boolean proxy = false;
		
		if (proxySet.equalsIgnoreCase("true")) {
			System.getProperties().put("http.proxyHost",proxyHost);
			System.getProperties().put("http.proxyPort", proxyPort);
			System.getProperties().put("https.proxyHost", proxyHost);
			System.getProperties().put("https.proxyPort", proxyPort);
			System.getProperties().put("http.proxyUser", proxyUser);
			System.getProperties().put("http.proxyPassword", proxyPassword);
			System.getProperties().put("https.proxyUser", proxyUser);
			System.getProperties().put("https.proxyPassword", proxyPassword);
			System.getProperties().put("http.nonProxyHosts",nonProxyHosts);
			
			proxy = true;
		} else {
			
			System.clearProperty("http.proxyHost");
			System.clearProperty("http.proxyPort");
			System.clearProperty("https.proxyHost");
			System.clearProperty("https.proxyPort");
			System.clearProperty("http.proxyUser");
			System.clearProperty("http.proxyPassword");
			System.clearProperty("https.proxyUser");
			System.clearProperty("https.proxyPassword");
			System.clearProperty("http.nonProxyHosts");
		}
		
		return proxy;
	}
}
