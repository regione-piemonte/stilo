/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * Contiene l'uri dello zip con i documenti associati ad una unità documentale salvata in storage
 * @author massimo malvestio
 *
 */
public class DownloadDocsZipBean {

	
	private String storageZipRemoteUri;
	private String zipName;	
	private String message;
	private String warningTimbro;
	private String warningSbusta;	
	private MimeTypeFirmaBean infoFile;
	
	
	public String getStorageZipRemoteUri() {
		return storageZipRemoteUri;
	}

	public void setStorageZipRemoteUri(String storageZipRemoteUri) {
		this.storageZipRemoteUri = storageZipRemoteUri;
	}

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

	public String getWarningTimbro() {
		return warningTimbro;
	}

	public String getWarningSbusta() {
		return warningSbusta;
	}

	public void setWarningTimbro(String warningTimbro) {
		this.warningTimbro = warningTimbro;
	}

	public void setWarningSbusta(String warningSbusta) {
		this.warningSbusta = warningSbusta;
	}
	

}
