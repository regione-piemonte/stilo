/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.digest.DigestCtrl;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.AppletBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Datasource(id = "AppletDatasource")
public class AppletDatasource extends AbstractServiceDataSource<AppletBean, AppletBean>{

	@Override
	public AppletBean call(AppletBean bean) throws Exception {
		File lFile = StorageImplementation.getStorage().extractFile(bean.getUri());
		MimeTypeFirmaBean lMimeTypeFirmaBean = retrieveInfo(lFile, bean.getFileName(), bean.getProvenienza());
		bean.setMimeTypeFirmaBean(lMimeTypeFirmaBean);

		return bean;
	}

	private MimeTypeFirmaBean retrieveInfo(File pInputStream, String pStrDisplayFileName, String provenienza) throws FileNotFoundException{

		if (provenienza.startsWith("ScanApplet")){
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setCorrectFileName(pStrDisplayFileName);
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			lMimeTypeFirmaBean.setDaScansione(true);
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration)SpringAppContext.getContext().getBean("DocumentConfiguration");
			DigestCtrl lDigestCtrl = new DigestCtrl();
			lDigestCtrl.setDigestAlgId(lDocumentConfiguration.getAlgoritmo());
			lDigestCtrl.setEncoding(lDocumentConfiguration.getEncoding());
			lMimeTypeFirmaBean.setImpronta(lDigestCtrl.execute(new FileInputStream(pInputStream)));
			lMimeTypeFirmaBean.setConvertibile(true);
			lMimeTypeFirmaBean.setCorrectFileName(pStrDisplayFileName);
			lMimeTypeFirmaBean.setFirmato(false);
			lMimeTypeFirmaBean.setMimetype("application/pdf");
			lMimeTypeFirmaBean.setDaScansione(true);
			lMimeTypeFirmaBean.setBytes(pInputStream.length());
			return lMimeTypeFirmaBean;
		} else {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean.setDaScansione(false);
			try {
				return lInfoFileUtility.getInfoFromFile(pInputStream.toURI().toString(), pStrDisplayFileName, false, null);
			} catch (Exception e) {
				return lMimeTypeFirmaBean;
			}
		}
	}

}
