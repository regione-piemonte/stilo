/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.log4j.Logger;

import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.FileUtilityBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

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
			long dimensioneFile = 0;
			if (!fileBean.isRemote()) {
				file = StorageImplementation.getStorage().getRealFile(fileBean.getUri());
				dimensioneFile = file.length();
			} 			
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
