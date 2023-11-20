/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EstremiRegistrazioneRicSearchBean implements Serializable{

	private static final long serialVersionUID = -7070633635052688909L;
	private String siglaReg;
	private Integer annoReg;
	private Integer numReg;
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
