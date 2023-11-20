/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.document.bean.UniqueIdentificationFileBean;
import it.eng.auriga.ui.module.layout.server.document.bean.UniqueIdentificatorDocument;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Transform {

	public static UniqueIdentificatorDocument fromRichiediProtocollo(RichiediProtocolloBean pRichiediProtocolloBean,
			AurigaLoginBean pAurigaLoginBean) throws StorageException, Exception{
		UniqueIdentificatorDocument lUniqueIdentificatorDocument = new UniqueIdentificatorDocument();
		File lFile = retrieveFile(pRichiediProtocolloBean.getUriFilePrimario(), pRichiediProtocolloBean.getRemoteUriFilePrimario(), pAurigaLoginBean);
		UniqueIdentificationFileBean primario = retrieveUniqueIdentificationFileBean(null, pRichiediProtocolloBean.getNomeFilePrimario(), lFile);
		lUniqueIdentificatorDocument.setPrimario(primario);
		Map<Integer, UniqueIdentificationFileBean> allegati = new HashMap<Integer, UniqueIdentificationFileBean>();
		int i = 1;
		for (RichiestaProtocolloAllegatoBean lRichiestaProtocolloAllegatoBean : pRichiediProtocolloBean.getListaAllegati()){
			File lFileAllegato = retrieveFile(lRichiestaProtocolloAllegatoBean.getUriFileAllegato(), 
					lRichiestaProtocolloAllegatoBean.getRemoteUri(), pAurigaLoginBean);
			UniqueIdentificationFileBean allegato = retrieveUniqueIdentificationFileBean(lRichiestaProtocolloAllegatoBean.getDescrizioneFileAllegato(), 
					lRichiestaProtocolloAllegatoBean.getNomeFileAllegato(), lFileAllegato);
			allegati.put(i, allegato);
			i++;
		}
		lUniqueIdentificatorDocument.setAllegati(allegati);
		return lUniqueIdentificatorDocument;
	}

	private static UniqueIdentificationFileBean retrieveUniqueIdentificationFileBean(
			String descrizione, String nomeFilePrimario, File lFile) throws NoSuchAlgorithmException, IOException {
		UniqueIdentificationFileBean lUniqueIdentificationFileBean = new UniqueIdentificationFileBean();
		lUniqueIdentificationFileBean.setDescrizione(descrizione);
		lUniqueIdentificationFileBean.setNome(nomeFilePrimario);
		lUniqueIdentificationFileBean.setHash(calculateHash(lFile));
		return lUniqueIdentificationFileBean;
	}

	private static String calculateHash(File lFile) throws IOException, NoSuchAlgorithmException {
		
		FileInputStream lFileInputStream = null;
		try {
			lFileInputStream = new FileInputStream(lFile);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
	        			 
	        byte[] dataBytes = new byte[1024];
	 
	        int nread = 0; 
	        while ((nread = lFileInputStream.read(dataBytes)) != -1) {
	          md.update(dataBytes, 0, nread);
	        };
	        byte[] mdbytes = md.digest();
	 
	       //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<mdbytes.length;i++) {
	    	  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
	    	}
	    	return hexString.toString();
		} finally {
			if(lFileInputStream != null) {
				try {
					lFileInputStream.close(); 
				} catch (Exception e) {}
			}
		}
	}

	protected static File retrieveFile(String uri, boolean remote,
			AurigaLoginBean pAurigaLoginBean) throws Exception,
			StorageException {
		//Il file è esterno
		if (remote){
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(uri);
			Locale local = new Locale(pAurigaLoginBean.getLinguaApplicazione());
			FileExtractedOut out = lRecuperoFile.extractfile(local, pAurigaLoginBean, lFileExtractedIn);
			return  out.getExtracted();
		} else {
			//File locale
			return StorageImplementation.getStorage().extractFile(uri);
		}
	}
}
