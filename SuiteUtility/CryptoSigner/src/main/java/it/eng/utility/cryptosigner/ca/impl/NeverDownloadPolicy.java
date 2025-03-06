package it.eng.utility.cryptosigner.ca.impl;

import be.fedict.eid.tsl.TrustServiceList;

/**
 * @author Russo
 *
 */
public class NeverDownloadPolicy implements TSLDownloadPolicy{
	//file che contiene il timestamp dell'ultimo download del file 
	 
	public boolean needDownload() {
		  
		return false;
	}

	public void downloadComplete(TrustServiceList tsl) {

	}

}
