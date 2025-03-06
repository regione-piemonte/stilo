package it.eng.applet.stampaFile.outputProvider;

import it.eng.applet.stampaFile.preferences.PreferenceKeys;
import it.eng.applet.stampaFile.preferences.PreferenceManager;
import it.eng.applet.stampaFile.util.LogWriter;

import javax.swing.JApplet;

public class OutputProviderImpl implements OutputProvider {

	private boolean autoClosePostPrint = false;
	private String callBackAskForClose = null;
	
	
	@Override
	public boolean getAutoClosePostPrint() {
		return autoClosePostPrint;
	}
	
	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}

	@Override
	public void saveOutputParameter(JApplet applet) throws Exception {
		String autoClosePostPrintString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTPRINT);
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTPRINT + ": " + autoClosePostPrintString);
		if( autoClosePostPrintString!=null )
			autoClosePostPrint  = Boolean.valueOf( autoClosePostPrintString ); 

		callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
	}
}
