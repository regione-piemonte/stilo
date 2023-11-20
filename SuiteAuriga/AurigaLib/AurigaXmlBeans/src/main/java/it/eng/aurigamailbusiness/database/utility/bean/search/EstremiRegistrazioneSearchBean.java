/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EstremiRegistrazioneSearchBean implements Serializable{

	private static final long serialVersionUID = -916915087472815935L;
	private String categoriaReg;
	private String siglaReg;
	private Integer annoReg;
	private Integer numReg;
	public String getCategoriaReg() {
		return categoriaReg;
	}
	public void setCategoriaReg(String categoriaReg) {
		this.categoriaReg = categoriaReg;
	}
	public String getSiglaReg() {
		return siglaReg;
	}
	public void setSiglaReg(String siglaReg) {
		this.siglaReg = siglaReg;
	}
	public Integer getAnnoReg() {
		return annoReg;
	}
	public void setAnnoReg(Integer annoReg) {
		this.annoReg = annoReg;
	}
	public Integer getNumReg() {
		return numReg;
	}
	public void setNumReg(Integer numReg) {
		this.numReg = numReg;
	}
	
}
