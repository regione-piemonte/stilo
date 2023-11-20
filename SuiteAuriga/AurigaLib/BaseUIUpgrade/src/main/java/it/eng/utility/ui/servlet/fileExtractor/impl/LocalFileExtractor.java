/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.servlet.fileExtractor.FileExtractor;

import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class LocalFileExtractor implements FileExtractor {

	private static final String FILENAME = "filename";
	private static final String URL = "url";
	private static final String SBUSTATO = "sbustato";
	private static final String CORRECT_FILE_NAME = "correctFilename";
	private static Logger mLogger = Logger.getLogger(LocalFileExtractor.class);

	private HttpServletRequest mHttpServletRequest;

	public LocalFileExtractor(HttpServletRequest lHttpServletRequest){
		mHttpServletRequest = lHttpServletRequest;
	}
	@Override
	public String getFileName() {
		String correctFilename = mHttpServletRequest.getParameter(CORRECT_FILE_NAME);
		if (correctFilename == null) correctFilename = mHttpServletRequest.getParameter(FILENAME);
		while (correctFilename != null && (correctFilename.toUpperCase().endsWith(".P7M") || correctFilename.toUpperCase().endsWith(".TSD"))){
			correctFilename = correctFilename.substring(0, correctFilename.length()-4);
		}
		return correctFilename;	
	}
	
	public String getCorrectFileName() {
		String correctFilename = mHttpServletRequest.getParameter(CORRECT_FILE_NAME);
		if (correctFilename == null) {
			correctFilename = mHttpServletRequest.getParameter(FILENAME);
		}
		return correctFilename;	
	}

	@Override
	public File getFile() {
		String uri = mHttpServletRequest.getParameter(URL);
		Boolean sbustato = StringUtils.isNotBlank(mHttpServletRequest.getParameter(SBUSTATO))?Boolean.valueOf(mHttpServletRequest.getParameter(SBUSTATO)) : false;
		File lFile = new File(uri);
		if (!sbustato)
			return lFile;
		else {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			try {
				InputStream lInputStream = lInfoFileUtility.sbusta(lFile, getCorrectFileName());
				String localUriExtracted = StorageImplementation.getStorage().storeStream(lInputStream);
				File lFileExtracted = StorageImplementation.getStorage().extractFile(localUriExtracted);
				return lFileExtracted;
			} catch (Exception e) {
				mLogger.error("Errore recupero file: " + e.getMessage(), e);
				return null;
			}
		}
	}
	@Override
	public InputStream getStream() throws Exception {
		String uri = mHttpServletRequest.getParameter(URL);
		Boolean sbustato = StringUtils.isNotBlank(mHttpServletRequest.getParameter(SBUSTATO))?Boolean.valueOf(mHttpServletRequest.getParameter(SBUSTATO)) : false;
		if (!sbustato)return StorageImplementation.getStorage().extract(uri);
		else {
			InfoFileUtility lInfoFileUtility = new InfoFileUtility();
			InputStream lInputStream = lInfoFileUtility.sbusta(StorageImplementation.getStorage().extractFile(uri), getCorrectFileName());
			return lInputStream;
		}
	}

}
