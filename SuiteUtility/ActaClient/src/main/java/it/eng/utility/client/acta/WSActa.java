package it.eng.utility.client.acta;

import java.util.Map;

import javax.xml.ws.BindingProvider;

public class WSActa {
	

	protected String appKey;
	protected String fiscalCodeUtente;
	protected Integer timeout;
	protected Integer connectTimeout = 60000;
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getFiscalCodeUtente() {
		return fiscalCodeUtente;
	}
	public void setFiscalCodeUtente(String fiscalCodeUtente) {
		this.fiscalCodeUtente = fiscalCodeUtente;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	protected void setTimeout(Object bindingProvider) {
		if (!(bindingProvider instanceof BindingProvider)) {
			return;
		}
        final Map<String, Object> requestCtx = ((BindingProvider) bindingProvider).getRequestContext();
        if (timeout != null) {
           requestCtx.put("com.sun.xml.internal.ws.request.timeout", timeout);
        }
        if (connectTimeout != null) {
           requestCtx.put("com.sun.xml.internal.ws.connect.timeout", connectTimeout);
        }
    }
	
}
