/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import it.eng.auriga.function.bean.FattureServiceInputBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.storage.DocumentStorage;
import it.eng.util.FattureUtil;

@Service(name = "FattureService")
public class FattureService {

	@Operation(name = "generaPdf")
	public FileExtractedOut generaPdf(AurigaLoginBean pAurigaLoginBean, FattureServiceInputBean inputBean){
		FileExtractedOut lFileExtractedOut = new FileExtractedOut();
		try {
			File fattura = DocumentStorage.extract(inputBean.getUriFileFattura(), pAurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			
			FattureUtil fattureUtil = new FattureUtil();
			File fatturaInPdf = fattureUtil.generaPdfFattura(pAurigaLoginBean, fattura, inputBean.isFirmato(), inputBean.getFileName(),
					inputBean.getIdDoc(), inputBean.getIdUd(), inputBean.getMimeType());
			
			if(fatturaInPdf==null) {
				throw new Exception("Il File non è una fattura");
			}
			
			lFileExtractedOut.setExtracted(fatturaInPdf);
		}
		catch (Exception e) {
			String errMsg = "Errore nella creazione della fattura pdf";
			if(e.getMessage()!=null)
				errMsg =  errMsg + " - Errore : " + e.getMessage();
			
			lFileExtractedOut.setErrorInExtract(errMsg);
		}
		return lFileExtractedOut;
	}
	
	
}
