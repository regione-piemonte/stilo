/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;
import it.eng.document.NumeroColonna;

@XmlRootElement
public class InvioMultiDestinatariXlsAttachmentsXmlBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1283125721249592189L;

	@NumeroColonna(numero = "1")
	private String urifile;

	@NumeroColonna(numero = "2")
	private String filename;

	@NumeroColonna(numero = "3")
	private BigDecimal dimensione;

	@NumeroColonna(numero = "4")
	private String mimetype;

	@NumeroColonna(numero = "5")
	private boolean firmato;

	@NumeroColonna(numero = "6")
	private boolean firmaValida;

	@NumeroColonna(numero = "7")
	private String impronta;

	@NumeroColonna(numero = "8")
	private String algoritmo;

	@NumeroColonna(numero = "9")
	private String encoding;

	public String getUrifile() {
		return urifile;
	}

	public void setUrifile(String urifile) {
		this.urifile = urifile;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public boolean isFirmato() {
		return firmato;
	}

	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isFirmaValida() {
		return firmaValida;
	}

	public void setFirmaValida(boolean firmaValida) {
		this.firmaValida = firmaValida;
	}
}