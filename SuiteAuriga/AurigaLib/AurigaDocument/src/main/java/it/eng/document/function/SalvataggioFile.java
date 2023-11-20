/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.document.storage.DocumentStorage;

@Service(name = "SalvataggioFile")
public class SalvataggioFile {

	@Operation(name = "saveFile")
	public FileSavedOut saveFile(AurigaLoginBean pAurigaLoginBean, FileSavedIn pFileSavedIn){
		FileSavedOut lFileSavedOut = new FileSavedOut();
		try {
			lFileSavedOut.setUri(DocumentStorage.store(pFileSavedIn.getSaved(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio()));
		}
		catch (Exception e) {
			lFileSavedOut.setErrorInSaved(e.getMessage());
		}
		return lFileSavedOut;
	}
	
}
