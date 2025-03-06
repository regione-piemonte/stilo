package it.eng.client.applet.cnProvider;

import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

public class AurigaProxyCommonNameProvider implements CommonNameProvider{

	private String commonNameCallback;
	private JApplet applet;

	@Override
	public boolean sendCommonName(String commonName) {
		try {
			if (commonNameCallback!=null && !commonNameCallback.equals("")){
				String lStrToInvoke = "javascript:" + commonNameCallback + "('" + commonName +"');";
				JSObject.getWindow(applet).eval( lStrToInvoke );
			}
			return true;
		} catch (Exception e) {
			LogWriter.writeLog("Errore", e );
			return false;
		} 
	}

	@Override
	public void saveOutputParameter(JApplet applet) throws Exception {
		setCommonNameCallback(PreferenceManager.getString(PreferenceKeys.PROPERTY_COMMON_NAME_CALLBACK));
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_COMMON_NAME_CALLBACK + ": " + commonNameCallback);
		if( applet!=null ){			
			setApplet(applet);
		}
	}

	public JApplet getApplet() {
		return applet;
	}

	public void setApplet(JApplet applet) {
		this.applet = applet;
	}

	public String getCommonNameCallback() {
		return commonNameCallback;
	}

	public void setCommonNameCallback(String commonNameCallback) {
		this.commonNameCallback = commonNameCallback;
	}

}
