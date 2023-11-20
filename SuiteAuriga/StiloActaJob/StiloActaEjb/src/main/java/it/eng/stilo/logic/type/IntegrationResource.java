/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegrationResource {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public EIntegrationResource resolve(final String eCode, Map<String, String> dynamicAttributesMap) {
		ActaEnte actaEnte = new ActaEnte();
		String ente = actaEnte.getEnte();
		logger.debug("ente " + ente);
		if( ente!=null && ente.equalsIgnoreCase("RP")){
			IntegrationResourceRP intRP = new IntegrationResourceRP(dynamicAttributesMap);
			logger.debug("Restituisco rp " + intRP);
			return intRP;
		//	return EnumSet.allOf(EIntegrationResourceRP.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
		//				.orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
		} else if( ente!=null && ente.equalsIgnoreCase("CMTO")){
			IntegrationResourceCMTO intCMTO = new IntegrationResourceCMTO();
			logger.debug("Restituisco cmmto " + intCMTO);
			return intCMTO;
			//return EnumSet.allOf(EIntegrationResource.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
			//		.orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
		} else if( ente!=null && ente.equalsIgnoreCase("COTO")){
			IntegrationResourceCOTO intCOTO = new IntegrationResourceCOTO();
			logger.debug("Restituisco coto " + intCOTO);
			return intCOTO;
			//return EnumSet.allOf(EIntegrationResource.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
			//		.orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
		} else {
			IntegrationResourceRP intRP = new IntegrationResourceRP(dynamicAttributesMap);
			return intRP;
			//return EnumSet.allOf(EIntegrationResource.class).stream().filter(dt -> dt.code.equals(eCode)).findFirst()
			//		.orElseThrow(() -> new IllegalStateException(String.format("Unsupported code %s", eCode)));
		}
	}
}
