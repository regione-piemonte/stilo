/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.eng.utility.ui.module.core.server.service.GsonBuilderFactory;
import it.eng.utility.ui.servlet.fileExtractor.FileExtractor;

public abstract class RecordExtractorUtil implements FileExtractor {

	protected HttpServletRequest mHttpServletRequest;
	protected Object recordObject;
	public abstract Class getRecordClass();
	
	public RecordExtractorUtil(HttpServletRequest pHttpServletRequest){
		mHttpServletRequest = pHttpServletRequest;
		recordObject = getObjectRecord();
	}
	
	public Object getObjectRecord(){
		String recordString = mHttpServletRequest.getParameter("record");
		GsonBuilder builder = GsonBuilderFactory.getIstance();
		Gson gson = builder.create();
		return gson.fromJson(recordString, getRecordClass());
	}

	@Override
	public abstract String getFileName() throws Exception;

	@Override
	public abstract File getFile() throws Exception;
}
