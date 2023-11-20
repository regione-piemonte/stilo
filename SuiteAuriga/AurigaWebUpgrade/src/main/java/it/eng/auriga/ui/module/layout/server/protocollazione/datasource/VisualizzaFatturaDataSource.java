/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.VisualizzaFatturaBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.XmlToHTML;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;

@Datasource(id = "VisualizzaFatturaDataSource")
public class VisualizzaFatturaDataSource extends AbstractServiceDataSource<VisualizzaFatturaBean, VisualizzaFatturaBean> {	

	@Override
	public VisualizzaFatturaBean call(VisualizzaFatturaBean bean) throws Exception {	
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		boolean sbustato = getExtraparams().get("sbustato") != null ? new Boolean(getExtraparams().get("sbustato")) : false;
		
		InputStream lInputStream = null;
		try {
			File fileXml = null;
			if (bean.getRemoteUri()){
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getUriFile());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(getSession()), lAurigaLoginBean, lFileExtractedIn);
				fileXml = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
			} else {
				fileXml = StorageImplementation.getStorage().extractFile(bean.getUriFile());	
			}			
			if(sbustato) {
				String displayFilename = fileXml.getName();
				if (fileXml.getName().toUpperCase().endsWith("P7M") ||  fileXml.getName().toUpperCase().endsWith("TSD")){
					displayFilename = fileXml.getName().substring(0,   fileXml.getName().length()-4);
				} 
				InfoFileUtility lInfoFileUtility = new InfoFileUtility();
				lInputStream = lInfoFileUtility.sbusta(fileXml.toURI().toString(), fileXml.getName());
			} else {
				lInputStream = new FileInputStream(fileXml);
			}
			File fileXsl = new File(URLDecoder.decode(getClass().getClassLoader().getResource("fatturapa_v1.0.xsl").getFile(), "UTF-8"));
			
			bean.setHtml(XmlToHTML.convert(lInputStream, fileXsl));
			
			if(bean.getHtml() != null && bean.getHtml().contains("<div id=\"fattura-container\"/>")) {
				bean.setHtml(null);
			}
			
			return bean;
		} finally {
			if(lInputStream != null) {
				try {
					lInputStream.close(); 
				} catch (Exception e) {}
			}
		}
	}
}