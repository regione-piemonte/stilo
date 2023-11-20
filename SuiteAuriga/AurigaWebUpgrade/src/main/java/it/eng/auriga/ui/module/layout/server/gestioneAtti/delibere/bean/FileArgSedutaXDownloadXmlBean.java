/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

/**
 * 
 * @author dbe4235
 *
 */

public class FileArgSedutaXDownloadXmlBean {
	
	@NumeroColonna(numero = "1")
	private String nomeFile;

	@NumeroColonna(numero = "2")
	private String uri;
	
	@NumeroColonna(numero = "3")
	private String idUd;
	
	@NumeroColonna(numero = "4")
	private String flgVersione;
	
	@NumeroColonna(numero = "5")
	private Integer flgFirmatoDaSbustare;
	
	@NumeroColonna(numero = "6")
	private Integer flgConvertibile;
	
	@NumeroColonna(numero = "7")
	private String mimetype;

	public String getNomeFile() {
		return nomeFile;
	}

	public String getUri() {
		return uri;
	}

	public String getIdUd() {
		return idUd;
	}

	public String getFlgVersione() {
		return flgVersione;
	}

	public Integer getFlgFirmatoDaSbustare() {
		return flgFirmatoDaSbustare;
	}

	public Integer getFlgConvertibile() {
		return flgConvertibile;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public void setFlgVersione(String flgVersione) {
		this.flgVersione = flgVersione;
	}

	public void setFlgFirmatoDaSbustare(Integer flgFirmatoDaSbustare) {
		this.flgFirmatoDaSbustare = flgFirmatoDaSbustare;
	}

	public void setFlgConvertibile(Integer flgConvertibile) {
		this.flgConvertibile = flgConvertibile;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

}