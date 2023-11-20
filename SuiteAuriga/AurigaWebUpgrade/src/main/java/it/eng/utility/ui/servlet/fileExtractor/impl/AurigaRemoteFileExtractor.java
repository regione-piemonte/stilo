/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.UserUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

public class AurigaRemoteFileExtractor extends RecordExtractorUtil {

	public AurigaRemoteFileExtractor(HttpServletRequest pHttpServletRequest) {
		super(pHttpServletRequest);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InputStream getStream() throws Exception {
		InputStream lInputStream = null;
		String uri = mHttpServletRequest.getParameter("url");
		RecuperoFile lRecuperoFile = new RecuperoFile();
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(mHttpServletRequest.getSession());
		FileExtractedIn lFileExtractedIn = new FileExtractedIn();
		lFileExtractedIn.setUri(uri);
		FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(mHttpServletRequest.getSession()), lAurigaLoginBean, lFileExtractedIn);
		lInputStream = new FileInputStream(out.getExtracted());
		return lInputStream;
	}
	
	@Override
	public Object getObjectRecord(){
		return null;
	}

	@Override
	public Class getRecordClass() {
		
		return null;
	}

	@Override
	public String getFileName() throws Exception {
		
		return "";
	}

	@Override
	public File getFile() throws Exception {
		return null;
	}

}
