/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspDeleteRicRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String token;	
	private String codiceFiscaleOp;
	private Integer progAtto;
	private Integer rigaAttim;
	
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

	public Integer getRigaAttim() {
		return rigaAttim;
	}

	public void setRigaAttim(Integer rigaAttim) {
		this.rigaAttim = rigaAttim;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspDeleteRicRequest [token=" + token + ", codiceFiscaleOp=" + codiceFiscaleOp
				+ ", progAtto=" + progAtto + ", rigaAttim=" + rigaAttim + "]";
	}
	
}
