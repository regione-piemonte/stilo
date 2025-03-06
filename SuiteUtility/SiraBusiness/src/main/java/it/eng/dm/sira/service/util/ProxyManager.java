package it.eng.dm.sira.service.util;

import org.apache.log4j.Logger;

public class ProxyManager {

	private static Logger log = Logger.getLogger(ProxyManager.class);
	
	private boolean needProxy;

	public boolean needProxy() {
		return needProxy;
	}

	public void setNeedProxy(boolean needProxy) {
		this.needProxy = needProxy;
	}
	

}
