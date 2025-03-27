/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.servlet.fileExtractor;


import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;


public class EgrammataFileToExtractBean extends FileToExtractBean{
	
	
	private MimeTypeFirmaBean infoFile;

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}
	
	

}
