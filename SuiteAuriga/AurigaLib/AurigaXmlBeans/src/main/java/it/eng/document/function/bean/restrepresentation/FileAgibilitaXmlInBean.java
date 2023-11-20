/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import it.eng.document.NumeroColonna;

/**
 * @author Antonio Peluso
 */

public class FileAgibilitaXmlInBean {
	
	@NumeroColonna(numero = "1")
	private String uri;
	
	@NumeroColonna(numero = "2")
	private String nome;
	
	@NumeroColonna(numero = "3")
	private BigDecimal dimensione;
	
	@NumeroColonna(numero = "4")
	private String impronta;
	
	@NumeroColonna(numero = "5")
	private String algoritmo;
	
	@NumeroColonna(numero = "6")
	private String encoding;
	
	@NumeroColonna(numero = "7")
	private String mimetype;
	
	@NumeroColonna(numero = "8")
	private Integer flgFirmato;
	
	//Firmatari del file (se più di uno separati da ;)
	@NumeroColonna(numero = "9")
	private String firmatari;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Integer getFlgFirmato() {
		return flgFirmato;
	}

	public void setFlgFirmato(Integer flgFirmato) {
		this.flgFirmato = flgFirmato;
	}

	public String getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}

}
