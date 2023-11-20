/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.digest.DigestCtrl;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.layout.shared.bean.AppletMultiplaBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

@Datasource(id = "AppletMultiplaDatasource")
public class AppletMultiplaDatasource extends AbstractServiceDataSource<AppletMultiplaBean, AppletMultiplaBean>{

	@Override
	public AppletMultiplaBean call(AppletMultiplaBean bean) throws Exception {
		List<FileDaFirmareBean> files = null;
		if(bean.getFiles() != null) {
			files = new ArrayList<FileDaFirmareBean>();
			for(int i = 0; i < bean.getFiles().size(); i++) {
				FileDaFirmareBean lFileBean = bean.getFiles().get(i);
				if(lFileBean.getFirmaEseguita() != null && lFileBean.getFirmaEseguita()) {
					File lFile = StorageImplementation.getStorage().extractFile(lFileBean.getUri());
					MimeTypeFirmaBean lMimeTypeFirmaBean = retrieveInfo(lFile, lFileBean.getNomeFile(), bean.getProvenienza());
					lFileBean.setInfoFile(lMimeTypeFirmaBean);
				} 
				files.add(lFileBean);
			}
		}
		bean.setFiles(files);
		return bean;
	}

	private MimeTypeFirmaBean retrieveInfo(File pInputStream, String pStrDisplayFileName, String provenienza) throws FileNotFoundException{

		if (provenienza.equals("ScanApplet.jar")){
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
