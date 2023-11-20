/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.servlet.fileExtractor.FileToExtractBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

import java.io.File;
import java.io.InputStream;

@Datasource(id="SbustaFileService")
public class SbustaFileService extends AbstractServiceDataSource<FileToExtractBean, FileToExtractBean>{
		
	@Override
	public FileToExtractBean call(FileToExtractBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		File file = null;
		if (bean.getRemoteUri()){
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(bean.getUri());
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
			file = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
		} else {
			file = StorageImplementation.getStorage().extractFile(bean.getUri());	
		}
		InputStream lInputStream = null;
		String displayFilename = bean.getDisplayFilename();
		if (displayFilename.toUpperCase().endsWith("P7M") ||  displayFilename.toUpperCase().endsWith("TSD")){
			displayFilename = displayFilename.substring(0, displayFilename.length()-4);
		} 
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		lInputStream= lInfoFileUtility.sbusta(file.toURI().toString(), bean.getDisplayFilename());		
		
		bean.setUri(StorageImplementation.getStorage().storeStream(lInputStream));
		bean.setDisplayFileName(displayFilename);		
		File realFile = StorageImplementation.getStorage().getRealFile(bean.getUri());
		bean.setImpronta(lInfoFileUtility.calcolaImpronta(realFile.toURI().toString(), bean.getDisplayFilename()));
		
		return bean;		
		
	}

}
