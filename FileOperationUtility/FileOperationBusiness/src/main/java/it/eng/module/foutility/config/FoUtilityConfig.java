/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Module;
import it.eng.core.config.IModuleConfigurator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Module(name=FoUtilityConfig.modulename, version="0.0.1-SNAPSHOT")
public class FoUtilityConfig implements IModuleConfigurator {
	
	public static final String modulename="FoUtility";
	private static final Logger log = LogManager.getLogger(FoUtilityConfig.class);
	
	@Override
	public void init() throws Exception {
		log.debug("Questo modulo esponse servizi di utilità per i files, quali:\n" +
				" conversione in pdf, verifica della firma, merge etc.");
		log.debug("FoUtilityConfig.modulename:"+FoUtilityConfig.modulename+" OK");
	}

	@Override
	public void destroy() throws Exception {
		 
		
	}
}
