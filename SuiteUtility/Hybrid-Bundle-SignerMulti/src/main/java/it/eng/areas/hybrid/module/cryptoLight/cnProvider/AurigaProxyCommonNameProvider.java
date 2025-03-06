package it.eng.areas.hybrid.module.cryptoLight.cnProvider;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;





import it.eng.areas.hybrid.module.cryptoLight.ui.PanelSign;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;

public class AurigaProxyCommonNameProvider implements CommonNameProvider{

	public final static Logger logger = Logger.getLogger( AurigaProxyCommonNameProvider.class);
	
	private String commonNameCallback;
	
	@Override
	public List<String> sendCommonName(String commonName) {
		List<String> args = new ArrayList<String>();
		try {
			if (commonNameCallback!=null && !commonNameCallback.equals("")){
				args.add(commonNameCallback);
				args.add(commonName);
				return args;
			}
			return null;
		} catch (Exception e) {
			logger.info("Errore", e );
			return null;
		} 
	}

	@Override
	public void saveOutputParameter() throws Exception {
		setCommonNameCallback(PreferenceManager.getString(PreferenceKeys.PROPERTY_COMMON_NAME_CALLBACK));
		logger.info("Parametro " + PreferenceKeys.PROPERTY_COMMON_NAME_CALLBACK + ": " + commonNameCallback);

	}

	public String getCommonNameCallback() {
		return commonNameCallback;
	}

	public void setCommonNameCallback(String commonNameCallback) {
		this.commonNameCallback = commonNameCallback;
	}

}
