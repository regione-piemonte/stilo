package it.eng.utility.cryptosigner.ca.impl;

import be.fedict.eid.tsl.TrustServiceList;

/**
 * indica una politica di download della TSL
 * Si possono implementare varie politiche per il download della TSL
 * ad esempio: per intervallo di giorni configurato
 * per data pianificata, schedulata etc.. se occorre si implementa questa interfaccia e si configura 
 * nel bean TSLCertificationAuthority la prop tslDownloadPolicy 
 * @author Russo
 *
 */
public interface TSLDownloadPolicy {
	/**
	 * se tru indica che occorre scaricare la TSL
	 * @return
	 */
	public boolean needDownload();
	
	/**
	 * indica alla policy che il download è stato completato per cui può memorizzare
	 * eventuali dati 
	 */
	public void downloadComplete(TrustServiceList tsl);
}
