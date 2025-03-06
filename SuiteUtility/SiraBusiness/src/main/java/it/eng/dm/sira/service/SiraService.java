package it.eng.dm.sira.service;

import it.eng.dm.sira.service.util.ExternalApp;
import it.eng.dm.sira.service.util.ProxyManager;
import it.eng.dm.sira.service.util.ProxySetter;
import it.eng.sira.mgu.ws.MguServiceEndPointProxy;

import com.hyperborea.sira.ws.SearchIntertematiciWSProxy;

public class SiraService {
	
	private SearchIntertematiciWSProxy catastiProxy;
	
	private MguServiceEndPointProxy mguProxy;
	
	private ProxyManager proxyManager;
	
	private ProxySetter proxySetter;
	
	private ExternalApp externalApp;

	public SearchIntertematiciWSProxy getCatastiProxy() {
		return catastiProxy;
	}

	public void setCatastiProxy(SearchIntertematiciWSProxy proxy) {
		this.catastiProxy = proxy;
	}

	public MguServiceEndPointProxy getMguProxy() {
		return mguProxy;
	}

	public void setMguProxy(MguServiceEndPointProxy mguProxy) {
		this.mguProxy = mguProxy;
	}

	public ProxyManager getProxyManager() {
		return proxyManager;
	}

	public void setProxyManager(ProxyManager proxyManager) {
		this.proxyManager = proxyManager;
	}

	public ProxySetter getProxySetter() {
		return proxySetter;
	}

	public void setProxySetter(ProxySetter proxySetter) {
		this.proxySetter = proxySetter;
	}

	public ExternalApp getExternalApp() {
		return externalApp;
	}

	public void setExternalApp(ExternalApp externalApp) {
		this.externalApp = externalApp;
	}

}
