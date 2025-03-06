package it.eng.utility.cryptosigner.ca.impl;

import it.eng.utility.cryptosigner.CryptoSingleton;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import be.fedict.eid.tsl.TrustServiceList;

/**
 * implementa una politica di download basata su un intervallo di giorni preconfigurato
 * se lil numero di giorni trascorso dall'ultimo download è > del numero di giorni configurato 
 * occorre scaricare la TSL
 * @author Russo
 *
 */
public class IntervalDownloadPolicy implements TSLDownloadPolicy{
	//file che contiene il timestamp dell'ultimo download del file 
	//TODO store on file !? private static final String LAST_CA_DOWNLOAD_TIME_FILE="lastCaDownloadTime.txt";
	
	public boolean needDownload() {
		boolean ret=false;

		long lastDownloadTime=CryptoSingleton.getInstance().getConfiguration().getLastTimeDownload();
		int interval=CryptoSingleton.getInstance().getConfiguration().getDayDownloadCAList();
		//se imposti l'intervallo zero o negativo scarica sempre
		// se lastDownloadTime ==-1 è la prima volta e quindi devi scaricare
		if(lastDownloadTime==-1 || interval<=0){
			ret=true; 
		}else{
			Date last = new Date(lastDownloadTime); // last download date
			Date today = new Date(); //  
			int days = Days.daysBetween(new DateTime(last), new DateTime(today)).getDays();
			if(days>interval){
				ret=true;
			}
		}
		return ret;
	}
	
 

	public void downloadComplete(TrustServiceList tsl) {
		CryptoSingleton.getInstance().getConfiguration().setLastTimeDownload(System.currentTimeMillis());
		
	}
}
