/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
