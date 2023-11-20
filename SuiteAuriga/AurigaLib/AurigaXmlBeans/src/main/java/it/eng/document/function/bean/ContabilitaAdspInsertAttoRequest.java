/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspInsertAttoRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String codiceFiscaleOp;
	private Integer progAtto;
	private Integer tiAttoam;
	private Integer kStato;
	private Date datainser;
	private String oggettoAt;
	private String codFiscaleRup;
	private List<ContabilitaAdspRichiesteContabiliRequest> richiesteContabili;
	
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
	
	public Integer getTiAttoam() {
		return tiAttoam;
	}
	
	public void setTiAttoam(Integer tiAttoam) {
		this.tiAttoam = tiAttoam;
	}
	
	public Integer getkStato() {
		return kStato;
	}
	
	public void setkStato(Integer kStato) {
		this.kStato = kStato;
	}
	
	public Date getDatainser() {
		return datainser;
	}

	public void setDatainser(Date datainser) {
		this.datainser = datainser;
	}

	public String getOggettoAt() {
		return oggettoAt;
	}
	
	public void setOggettoAt(String oggettoAt) {
		this.oggettoAt = oggettoAt;
	}
	
	public String getCodFiscaleRup() {
		return codFiscaleRup;
	}
	
	public void setCodFiscaleRup(String codFiscaleRup) {
		this.codFiscaleRup = codFiscaleRup;
	}
	
	public List<ContabilitaAdspRichiesteContabiliRequest> getRichiesteContabili() {
		return richiesteContabili;
	}
	
	public void setRichiesteContabili(List<ContabilitaAdspRichiesteContabiliRequest> richiesteContabili) {
		this.richiesteContabili = richiesteContabili;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspInsertAttoRequest [token=" + token + ", codiceFiscaleOp=" + codiceFiscaleOp
				+ ", progAtto=" + progAtto + ", tiAttoam=" + tiAttoam + ", kStato=" + kStato + ", datainser="
				+ datainser + ", oggettoAt=" + oggettoAt + ", codFiscaleRup=" + codFiscaleRup + ", richiesteContabili=" + richiesteContabili + "]";
	}
	
}
