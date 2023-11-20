/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.document.NumeroColonna;

/**
 * @author Antonio Peluso
 */

public class FileToSendBean {
	
	private String nomeFile;	
	private String uri;	
	private String mimetype;	
	private BigDecimal dimensione;	
	private String idUD;
	private boolean firmato;
	private String impronta;
	private String algoritmo;
	private String encoding;
	private boolean firmaValida;

	public String getIdUD() {
		return idUD;
	}

	public void setIdUD(String idUD) {
		this.idUD = idUD;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public BigDecimal getDimensione() {
		return dimensione;
	}

	public void setDimensione(BigDecimal dimensione) {
		this.dimensione = dimensione;
	}

	public boolean isFirmato() {
		return firmato;
	}

	public void setFirmato(boolean firmato) {
		this.firmato = firmato;
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
