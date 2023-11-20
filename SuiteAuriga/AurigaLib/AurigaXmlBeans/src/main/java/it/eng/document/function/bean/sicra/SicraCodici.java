/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraCodici implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String applicazione;
	private String codiceTrasco;
	
	public String getApplicazione() {
		return applicazione;
	}
	
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	
	public String getCodiceTrasco() {
		return codiceTrasco;
	}
	
	public void setCodiceTrasco(String codiceTrasco) {
		this.codiceTrasco = codiceTrasco;
	}
	
}
