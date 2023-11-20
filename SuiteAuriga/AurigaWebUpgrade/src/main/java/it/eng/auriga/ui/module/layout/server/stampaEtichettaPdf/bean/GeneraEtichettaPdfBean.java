/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Map;

import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

public class GeneraEtichettaPdfBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, String> lMapParams;
	private String impostazioniPdf;
	private String uriPdfResult;
	private MimeTypeFirmaBean infoFile;

	public Map<String, String> getlMapParams() {
		return lMapParams;
	}

	public void setlMapParams(Map<String, String> lMapParams) {
		this.lMapParams = lMapParams;
	}

	public String getImpostazioniPdf() {
		return impostazioniPdf;
	}

	public void setImpostazioniPdf(String impostazioniPdf) {
		this.impostazioniPdf = impostazioniPdf;
	}

	public String getUriPdfResult() {
		return uriPdfResult;
	}

	public void setUriPdfResult(String uriPdfResult) {
		this.uriPdfResult = uriPdfResult;
	}

	public MimeTypeFirmaBean getInfoFile() {
		return infoFile;
	}

	public void setInfoFile(MimeTypeFirmaBean infoFile) {
		this.infoFile = infoFile;
	}

}
