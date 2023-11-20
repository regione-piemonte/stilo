/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocRectrBean implements Serializable {

	private String uriFileDoc;
	private String displayNameDoc;
	private BigDecimal idDocumento;
	private Integer nroVersione;
	private Integer nroAllegato;
	private Date dataRifValiditaFirma;
	
	public String getUriFileDoc() {
		return uriFileDoc;
	}
	public void setUriFileDoc(String uriFileDoc) {
		this.uriFileDoc = uriFileDoc;
	}
	public String getDisplayNameDoc() {
		return displayNameDoc;
	}
	public void setDisplayNameDoc(String displayNameDoc) {
		this.displayNameDoc = displayNameDoc;
	}
	public BigDecimal getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(BigDecimal idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Integer getNroVersione() {
		return nroVersione;
	}
	public void setNroVersione(Integer nroVersione) {
		this.nroVersione = nroVersione;
	}
	public Integer getNroAllegato() {
		return nroAllegato;
	}
	public void setNroAllegato(Integer nroAllegato) {
		this.nroAllegato = nroAllegato;
	}
	public Date getDataRifValiditaFirma() {
		return dataRifValiditaFirma;
	}
	public void setDataRifValiditaFirma(Date dataRifValiditaFirma) {
		this.dataRifValiditaFirma = dataRifValiditaFirma;
	}
	

}