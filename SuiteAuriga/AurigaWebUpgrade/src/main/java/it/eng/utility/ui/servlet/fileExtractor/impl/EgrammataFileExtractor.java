/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.servlet.fileExtractor.EgrammataFileToExtractBean;
import it.eng.utility.ui.servlet.fileExtractor.config.EgrammataConfig;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

public class EgrammataFileExtractor extends RecordExtractorUtil {

	public EgrammataFileExtractor(HttpServletRequest pHttpServletRequest) {
		super(pHttpServletRequest);
	}
	
	public static String recordType = "EgrammataFileToExtractBean";
	private EgrammataFileToExtractBean mFileToExtractBean;

	@Override
	public String getFileName() {
		EgrammataFileToExtractBean lFileToExtractBean = (EgrammataFileToExtractBean) recordObject; 
		mFileToExtractBean = lFileToExtractBean;
		String correctFilename = mFileToExtractBean.getCorrectFilename();
		if (!mFileToExtractBean.isSbustato()) return mFileToExtractBean.getDisplayFilename();
		if (correctFilename == null) {
			if ( mFileToExtractBean.getDisplayFilename().toUpperCase().endsWith("P7M") ||  mFileToExtractBean.getDisplayFilename().toUpperCase().endsWith("TSD")){
				return  mFileToExtractBean.getDisplayFilename().substring(0,  mFileToExtractBean.getDisplayFilename().length()-4);
			} else {
				return  mFileToExtractBean.getDisplayFilename();
			}
			
		} else {
			if (correctFilename.toUpperCase().endsWith("P7M") || correctFilename.toUpperCase().endsWith("TSD")){
				return correctFilename.substring(0, correctFilename.length()-4);
			} else {
				return correctFilename;
			}

		}
	}
	
	public String getCorrectFileName() {
		EgrammataFileToExtractBean lFileToExtractBean = (EgrammataFileToExtractBean) recordObject; 
		mFileToExtractBean = lFileToExtractBean;
		String correctFilename = mFileToExtractBean.getCorrectFilename();
		if (!mFileToExtractBean.isSbustato()) {
			return mFileToExtractBean.getDisplayFilename();
		}
		if (correctFilename == null) {
			return  mFileToExtractBean.getDisplayFilename();
		} else {
			return correctFilename;
		}
	}

	@Override
	public File getFile() throws Exception {
		return null;
	}

	@Override
	public Class getRecordClass() {
		return EgrammataFileToExtractBean.class;
	}

	@Override
	public InputStream getStream() throws Exception {
		File file = null;
		if (mFileToExtractBean.getRemoteUri()==null || !mFileToExtractBean.getRemoteUri()){
			file = StorageImplementation.getStorage().extractFile(mFileToExtractBean.getUri());
		} else {
			//Remoto
			RecuperoFile lRecuperoFile = new RecuperoFile();
			AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(mHttpServletRequest.getSession());
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			EgrammataConfig lEgrammataConfig = (EgrammataConfig) SpringAppContext.getContext().getBean("EgrammataConfig");
			if (mFileToExtractBean.getUri().startsWith("[" + lEgrammataConfig.getDescriptorName() + "]" + lEgrammataConfig.getPrefix())){
				lFileExtractedIn.setUri(mFileToExtractBean.getUri().substring(("[" + lEgrammataConfig.getDescriptorName() + "]" + 
						lEgrammataConfig.getPrefix()).length()));
			} else lFileExtractedIn.setUri(mFileToExtractBean.getUri());
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(mHttpServletRequest.getSession()), lAurigaLoginBean, lFileExtractedIn);
			file = out.getExtracted();
		}
		if (!mFileToExtractBean.isSbustato())
			return new FileInputStream(file);
		else {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			InputStream lInputStreamExtracted = lInfoFileUtility.sbusta(file, getCorrectFileName());
			return lInputStreamExtracted;
		}


	}
	
	public static InputStream getStream(AurigaLoginBean pAurigaLoginBean, EgrammataFileToExtractBean bean) throws Exception {
		File file = null;
		if (bean.getRemoteUri()==null || !bean.getRemoteUri()){
			file = StorageImplementation.getStorage().extractFile(bean.getUri());
		} else {
			//Remoto
			RecuperoFile lRecuperoFile = new RecuperoFile();
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			EgrammataConfig lEgrammataConfig = (EgrammataConfig) SpringAppContext.getContext().getBean("EgrammataConfig");
			if (bean.getUri().startsWith("[" + lEgrammataConfig.getDescriptorName() + "]" + lEgrammataConfig.getPrefix())){
				lFileExtractedIn.setUri(bean.getUri().substring(("[" + lEgrammataConfig.getDescriptorName() + "]" + 
						lEgrammataConfig.getPrefix()).length()));
			} else lFileExtractedIn.setUri(bean.getUri());
			Locale userLocal = new Locale(pAurigaLoginBean.getLinguaApplicazione());
			FileExtractedOut out = lRecuperoFile.extractfile(userLocal, pAurigaLoginBean, lFileExtractedIn);
			file = out.getExtracted();
		}
		if (!bean.isSbustato())
			return new FileInputStream(file);
		else {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			InputStream lInputStreamExtracted = lInfoFileUtility.sbusta(file, getCorrectFileName(bean));
			return lInputStreamExtracted;
		}
	}

	private static String getFileName(EgrammataFileToExtractBean bean) {
		String correctFilename = bean.getCorrectFilename();
		if (!bean.isSbustato()) return bean.getDisplayFilename();
		if (correctFilename == null) {
			if ( bean.getDisplayFilename().toUpperCase().endsWith("P7M") ||  bean.getDisplayFilename().toUpperCase().endsWith("TSD")){
				return  bean.getDisplayFilename().substring(0,  bean.getDisplayFilename().length()-4);
			} else {
				return  bean.getDisplayFilename();
			}
		} else {
			if (correctFilename.toUpperCase().endsWith("P7M") || correctFilename.toUpperCase().endsWith("TSD")){
				return correctFilename.substring(0, correctFilename.length()-4);
			} else {
				return correctFilename;
			}

		}
	}
	
	public static String getCorrectFileName(EgrammataFileToExtractBean bean) {
		String correctFilename = bean.getCorrectFilename();
		if (!bean.isSbustato()) return bean.getDisplayFilename();
		if (correctFilename == null) {
			return  bean.getDisplayFilename();
		} else {
			return correctFilename;
		}		
	}
	
}
