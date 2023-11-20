/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.core.service.client.FactoryBusiness.BusinessType;
import it.eng.utility.storageutil.GenericStorageInfo;
import it.eng.utility.storageutil.StorageConfig;
import it.eng.utility.storageutil.StorageService;

/**
 * Classe che implementa tutti i servizi dello storage da utilizzare all'esterno per il salvataggio, la cancellazione e il recupero del file
 * <br />
 * <b>Nel caso la si utilizzi tramite i servizi di business, assicurarsi di aver correttamente inizializzato 
 * il BusinessClient</b> (come normalmente viene fatto quando si utilizzano i servizi di chiamata alle stored proc.) 
 * 
 * @author D.Bragato
 * 
 */
public class StorageServiceImpl {

	private static Logger mLogger = Logger.getLogger(StorageService.class);

	private StorageServiceImpl() {
		//CLASSE STATICA
	}

	/**
	 * Metodo factory che ritorna una nuova istanza della classe 
	 * che implementa i servizi in base al tipo di servizio configurato in
	 * {@link StorageConfig} 
	 * 
	 * @param info
	 */
	public static StorageService newInstance(GenericStorageInfo info) {
		if (BusinessType.API == StorageConfig.getTipoServizio()) {
			mLogger.debug("StorageUtil, chiamate dirette alle API");
			return new StorageServiceAPIImpl(info);
		} else if (BusinessType.REST == StorageConfig.getTipoServizio()) {
			mLogger.debug("StorageUtil, chiamate ai servizi esposti tramite Business via REST");
			return new StorageServiceRESTImpl(info);
		} else {
			mLogger.error("StorageUtil, tipo di servizio non configurato!");
			return null;
		}
	}

}
