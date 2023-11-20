/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

public class ProxyManager {

	private static Logger log = Logger.getLogger(ProxyManager.class);
	
	private ProxySetter proxySetter;
	
	public ProxySetter getProxySetter() {
		return proxySetter;
	}

	public void setProxySetter(ProxySetter proxySetter) {
		this.proxySetter = proxySetter;
	}

	private boolean needProxy;

	public boolean needProxy() {
		return needProxy;
	}

	public void setNeedProxy(boolean needProxy) {
		this.needProxy = needProxy;
	}

}
