package it.eng.core.service.client;

import it.eng.core.service.client.config.Configuration;



/**
 * Classe Factory
 * @author michele
 *
 */
public class FactoryBusiness {

	
	/**
	 * Ritorna una nuova istanza del tipo di business implementata
	 * @return
	 */
	public static IBusiness getBusiness() {
		
		BusinessType type = Configuration.getInstance().getBusinesstype();
		
		switch (type) {
			
			case API:
				return new ApiBusiness();
				
			case SOAP:
				return new SoapBusiness();	
									
			case REST:
				return new RestBusiness();
							
			default:
				return null;
		}
		
	}
	
	/**
	 * Ritorna una nuova istanza del tipo di business implementata
	 * @return
	 */
	public static IBusiness getBusiness(BusinessType pType) {
		BusinessType type = null;
		
		if (pType == null)
			type = Configuration.getInstance().getBusinesstype();
		else type = pType;
		
		switch (type) {
			
			case API:
				return new ApiBusiness();
				
			case SOAP:
				return new SoapBusiness();	
									
			case REST:
				return new RestBusiness();
							
			default:
				return null;
		}
		
	}
	
	
	public enum BusinessType {
		API,
		REST,
		SOAP
	}
}
