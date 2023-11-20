/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.log4j.Logger;

import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FileUtilityBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

@Datasource(id = "FileUtilityDatasource")
public class FileUtilityDatasource extends AbstractServiceDataSource<FileUtilityBean, FileUtilityBean> {

	private static Logger mLogger = Logger.getLogger(FileUtilityDatasource.class);
	
	@Override
	public FileUtilityBean call(FileUtilityBean pInBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FileUtilityBean getDimensioneFile(FileUtilityBean fileBean) throws Exception {
		try {
			// Bean da restituire contenente la dimensione nell'infoFile
			File file = null;
			if (!fileBean.isRemote()) {
				file = StorageImplementation.getStorage().getRealFile(fileBean.getUri());
			} else {
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(fileBean.getUri());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), AurigaUserUtil.getLoginInfo(getSession()),
						lFileExtractedIn);
				file = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
			}
			long dimensioneFile = file.length();
			MimeTypeFirmaBean infoFile = fileBean.getInfoFile() != null ? fileBean.getInfoFile() : new MimeTypeFirmaBean();
			infoFile.setBytes(dimensioneFile);
			fileBean.setInfoFile(infoFile);
			return fileBean;
		} catch (Exception e) {
			mLogger.error(e.getMessage(), e);
			return fileBean;
		}
		
	}

}

