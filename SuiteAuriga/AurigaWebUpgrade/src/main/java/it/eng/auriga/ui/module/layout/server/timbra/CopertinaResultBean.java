/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * 
 * @author DANCRIST
 *
 */

public class CopertinaResultBean {
	
	private boolean result;
	private String error;
	private String uri;
	private MimeTypeFirmaBean infoFile;

	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * @return the infoFile
	 */
	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}
	/**
	 * @param infoFile the infoFile to set
	 */
	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

}
