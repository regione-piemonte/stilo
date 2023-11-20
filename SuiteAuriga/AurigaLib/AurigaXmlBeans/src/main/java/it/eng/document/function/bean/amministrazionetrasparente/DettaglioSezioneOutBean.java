/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dettaglioSezione")
public class DettaglioSezioneOutBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4467790093019448924L;

	@XmlElement(name = "listaContenuti")
	List<XmlContenutiOutBean> listaContenuti;
	
	@XmlElement(name = "idsezione")
	BigDecimal idsezione;
	
	@XmlElement(name = "flgpresentirifnorm")
	Integer flgpresentirifnorm;
	
	@XmlElement(name = "rifnormativi")
	String rifnormativi;
	
	@XmlElement(name = "flgpresenteheader")
	Integer flgpresenteheader;
	
	@XmlElement(name = "header")
	String header;
	
	@XmlElement(name = "datiagg")
	String datiagg;
	
	@XmlElement(name = "nrocontenuti")
	BigDecimal nrocontenuti;

	Esito esito;

	public DettaglioSezioneOutBean() {
	}

	public List<XmlContenutiOutBean> getListaContenuti() {
		return listaContenuti;
	}

	public void setListaContenuti(List<XmlContenutiOutBean> listaContenuti) {
		this.listaContenuti = listaContenuti;
	}

	public BigDecimal getIdsezione() {
		return idsezione;
	}

	public void setIdsezione(BigDecimal idsezione) {
		this.idsezione = idsezione;
	}

	public Integer getFlgpresentirifnorm() {
		return flgpresentirifnorm;
	}

	public void setFlgpresentirifnorm(Integer flgpresentirifnorm) {
		this.flgpresentirifnorm = flgpresentirifnorm;
	}

	public String getRifnormativi() {
		return rifnormativi;
	}

	public void setRifnormativi(String rifnormativi) {
		this.rifnormativi = rifnormativi;
	}

	public Integer getFlgpresenteheader() {
		return flgpresenteheader;
	}

	public void setFlgpresenteheader(Integer flgpresenteheader) {
		this.flgpresenteheader = flgpresenteheader;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDatiagg() {
		return datiagg;
	}

	public void setDatiagg(String datiagg) {
		this.datiagg = datiagg;
	}

	public BigDecimal getNrocontenuti() {
		return nrocontenuti;
	}

	public void setNrocontenuti(BigDecimal nrocontenuti) {
		this.nrocontenuti = nrocontenuti;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

}
