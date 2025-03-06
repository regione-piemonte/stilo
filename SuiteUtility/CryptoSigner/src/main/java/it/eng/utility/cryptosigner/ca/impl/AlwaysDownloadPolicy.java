package it.eng.utility.cryptosigner.ca.impl;

import be.fedict.eid.tsl.TrustServiceList;

/**
 * @author Russo
 *
 */
public class AlwaysDownloadPolicy implements TSLDownloadPolicy{
	//file che contiene il timestamp dell'ultimo download del file 
	 
	public boolean needDownload() {
		  
		return true;
	}

	public void downloadComplete(TrustServiceList tsl) {

	}

}
