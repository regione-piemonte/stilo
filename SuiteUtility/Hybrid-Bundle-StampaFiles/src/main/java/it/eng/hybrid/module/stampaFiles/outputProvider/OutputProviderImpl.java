package it.eng.hybrid.module.stampaFiles.outputProvider;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.stampaFiles.preferences.PreferenceKeys;
import it.eng.hybrid.module.stampaFiles.preferences.PreferenceManager;

public class OutputProviderImpl implements OutputProvider {

	public final static Logger logger = Logger.getLogger(OutputProviderImpl.class);

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
	public void saveOutputParameter() throws Exception {
		String autoClosePostPrintString = PreferenceManager.getString(PreferenceKeys.PROPERTY_AUTOCLOSEPOSTPRINT);
		logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTPRINT + ": " + autoClosePostPrintString);
		if (autoClosePostPrintString != null)
			autoClosePostPrint = Boolean.valueOf(autoClosePostPrintString);

		callBackAskForClose = PreferenceManager.getString(PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE);
		logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
	}
}
