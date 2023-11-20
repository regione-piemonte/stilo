/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspUpdateAttoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;	
	private String codiceFiscaleOp;
	private Integer progAtto;
	private String oggettoAt;
	private String codCup;
	private String codFiscaleRup;
	private Integer numAtto;
	private Date dataAtto;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCodiceFiscaleOp() {
		return codiceFiscaleOp;
	}
	
	public void setCodiceFiscaleOp(String codiceFiscaleOp) {
		this.codiceFiscaleOp = codiceFiscaleOp;
	}
	
	public Integer getProgAtto() {
		return progAtto;
	}
	
	public void setProgAtto(Integer progAtto) {
		this.progAtto = progAtto;
	}

	public String getOggettoAt() {
		return oggettoAt;
	}

	public void setOggettoAt(String oggettoAt) {
		this.oggettoAt = oggettoAt;
	}

	public String getCodCup() {
		return codCup;
	}

	public void setCodCup(String codCup) {
		this.codCup = codCup;
	}

	public String getCodFiscaleRup() {
		return codFiscaleRup;
	}

	public void setCodFiscaleRup(String codFiscaleRup) {
		this.codFiscaleRup = codFiscaleRup;
	}

	public Integer getNumAtto() {
		return numAtto;
	}

	public void setNumAtto(Integer numAtto) {
		this.numAtto = numAtto;
	}

	public Date getDataAtto() {
		return dataAtto;
	}

	public void setDataAtto(Date dataAtto) {
		this.dataAtto = dataAtto;
	}
	
}
