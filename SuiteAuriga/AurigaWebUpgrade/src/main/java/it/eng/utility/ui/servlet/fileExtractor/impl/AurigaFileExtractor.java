/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.servlet.fileExtractor.impl;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.servlet.fileExtractor.FileToExtractBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

public class AurigaFileExtractor extends RecordExtractorUtil {
	
	private long fileLength = 0;

	public AurigaFileExtractor(HttpServletRequest pHttpServletRequest) {
		super(pHttpServletRequest);
	}

	public static String recordType = "FileToExtractBean";
	private FileToExtractBean mFileToExtractBean;

	@Override
	public String getFileName() {
		mFileToExtractBean = mFileToExtractBean == null ? (FileToExtractBean) recordObject : mFileToExtractBean; 
		String correctFilename = mFileToExtractBean.getCorrectFilename();
		if (!mFileToExtractBean.isSbustato()) return mFileToExtractBean.getDisplayFilename();
		if (correctFilename == null) {
			correctFilename = mFileToExtractBean.getDisplayFilename();			
		}
		while (correctFilename != null && (correctFilename.toLowerCase().endsWith(".p7m") || correctFilename.toLowerCase().endsWith(".tsd"))){
			correctFilename = correctFilename.substring(0, correctFilename.length()-4);
		}
		return correctFilename;		
	}
	
	public String getCorrectFileName() {
		mFileToExtractBean = mFileToExtractBean == null ? (FileToExtractBean) recordObject : mFileToExtractBean; 
		String correctFilename = mFileToExtractBean.getCorrectFilename();
		if (!mFileToExtractBean.isSbustato()) return mFileToExtractBean.getDisplayFilename();
		if (correctFilename == null) {
			correctFilename = mFileToExtractBean.getDisplayFilename();			
		}
		return correctFilename;		
	}
	
	@Override
	public File getFile() throws Exception {
		return null;
	}

	@Override
	public Class getRecordClass() {
		return FileToExtractBean.class;
	}

	@Override
	public InputStream getStream() throws Exception {
		mFileToExtractBean = mFileToExtractBean == null ? (FileToExtractBean) recordObject : mFileToExtractBean; 
		File file = null;
		if (mFileToExtractBean.getRemoteUri()==null || !mFileToExtractBean.getRemoteUri()){
			file = StorageImplementation.getStorage().extractFile(mFileToExtractBean.getUri());
		} else {
			//Remoto
			RecuperoFile lRecuperoFile = new RecuperoFile();
			AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(mHttpServletRequest.getSession());
			FileExtractedIn lFileExtractedIn = new FileExtractedIn();
			lFileExtractedIn.setUri(mFileToExtractBean.getUri());
			FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
			file = StorageImplementation.getStorage().getRealFile(StorageImplementation.getStorage().store(out.getExtracted()));
		}
		fileLength = file.length();
		if (!mFileToExtractBean.isSbustato())
			return new FileInputStream(file);
		else {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			InputStream lInputStreamExtracted = lInfoFileUtility.sbusta(file.toURI().toString(), getCorrectFileName());
			
			return getInputStreamFile(lInputStreamExtracted);
		}
	}

	@Override
	public long getFileLength() throws Exception {
		return fileLength;
	}
	
	private InputStream getInputStreamFile(InputStream inputStream) throws IOException {
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            
            byte[] data = byteArrayOutputStream.toByteArray();

            // Crea due ByteArrayInputStream distinti basati sullo stesso array di byte
            ByteArrayInputStream inputStream1 = new ByteArrayInputStream(data);

            fileLength = byteArrayOutputStream.size();
            
            return inputStream1;
        }
	}

}
