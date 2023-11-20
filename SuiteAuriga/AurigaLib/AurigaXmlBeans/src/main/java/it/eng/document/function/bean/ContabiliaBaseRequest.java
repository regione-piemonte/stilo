/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabiliaBaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer annoBilancio;
	private String codiceEnte;
	private String codiceFruitore;
	
	public Integer getAnnoBilancio() {
		return annoBilancio;
	}
	
	public void setAnnoBilancio(Integer annoBilancio) {
		this.annoBilancio = annoBilancio;
	}
	
	public String getCodiceEnte() {
		return codiceEnte;
	}
	
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	
	public String getCodiceFruitore() {
		return codiceFruitore;
	}
	
	public void setCodiceFruitore(String codiceFruitore) {
		this.codiceFruitore = codiceFruitore;
	}
	
	@Override
	public String toString() {
		return "ContabiliaBaseRequest [annoBilancio=" + annoBilancio + ", codiceEnte=" + codiceEnte
				+ ", codiceFruitore=" + codiceFruitore + "]";
	}
	
}
