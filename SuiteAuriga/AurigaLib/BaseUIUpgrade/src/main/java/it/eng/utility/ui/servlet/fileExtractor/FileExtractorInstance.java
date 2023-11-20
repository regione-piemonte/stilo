/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class FileExtractorInstance {
	
	private Map<String, String> mapExtractors;
	private static FileExtractorInstance instance;
	
	public static FileExtractorInstance getInstance(){
		if (instance == null){
			instance = new FileExtractorInstance();
		}
		return instance;
	}

	public FileExtractor getRelatedFileExtractor(String lStringType, HttpServletRequest req) throws Exception {
		Class lString = Class.forName(mapExtractors.get(lStringType));
		return (FileExtractor)lString.getConstructor(new Class[]{HttpServletRequest.class}).newInstance(new Object[]{req});
	}

	public void setMapExtractors(Map<String, String> pMapExtractors) {
		mapExtractors = pMapExtractors;
	}

	
}
