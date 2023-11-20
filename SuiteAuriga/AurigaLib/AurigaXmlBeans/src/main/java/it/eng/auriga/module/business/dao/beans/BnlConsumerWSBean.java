/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class BnlConsumerWSBean extends AbstractBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -19041102070629777L;
	
	private BigDecimal idSpAoo;
	private String userid;
	private String password;
	private String descrizione;
	private String ciApplicazione;
	private String ciIstanzaApplicazione;
	private String cifratura;
	private String chiaveCifratura;
	private String connToken;
	private String nomeSistemaCsEsterno;
	
	public BigDecimal getIdSpAoo() {
		return idSpAoo;
	}
	
	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getCiApplicazione() {
		return ciApplicazione;
	}
	
	public void setCiApplicazione(String ciApplicazione) {
		this.ciApplicazione = ciApplicazione;
	}
	
	public String getCiIstanzaApplicazione() {
		return ciIstanzaApplicazione;
	}
	
	public void setCiIstanzaApplicazione(String ciIstanzaApplicazione) {
		this.ciIstanzaApplicazione = ciIstanzaApplicazione;
	}
	
	public String getCifratura() {
		return cifratura;
	}
	
	public void setCifratura(String cifratura) {
		this.cifratura = cifratura;
	}
	
	public String getChiaveCifratura() {
		return chiaveCifratura;
	}
	
	public void setChiaveCifratura(String chiaveCifratura) {
		this.chiaveCifratura = chiaveCifratura;
	}
	
	public String getConnToken() {
		return connToken;
	}
	
	public void setConnToken(String connToken) {
		this.connToken = connToken;
	}
	
	public String getNomeSistemaCsEsterno() {
		return nomeSistemaCsEsterno;
	}
	
	public void setNomeSistemaCsEsterno(String nomeSistemaCsEsterno) {
		this.nomeSistemaCsEsterno = nomeSistemaCsEsterno;
	}


}
