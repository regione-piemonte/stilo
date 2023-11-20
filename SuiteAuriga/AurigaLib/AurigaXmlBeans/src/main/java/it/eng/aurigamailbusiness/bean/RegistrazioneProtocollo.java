/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe per la registrazione del protocollo da associarsi ad una email unici dati obbligatori saranno idProvReg e xmlDatiIn dall'xml si
 * ricaveranno estremi di protocollazione e eventuale uri degli allegati
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class RegistrazioneProtocollo implements Serializable {

	private static final long serialVersionUID = -3584785130304495317L;

	private Short annoReg;

	private String categoriaReg;

	private String idProvReg;

	private BigDecimal numReg;

	private String siglaRegistro;
	
	private Calendar dataRegistrazione;

	private List<String> uriAllegati;

	// xml di ingresso da dare in pasto
	// alla store contenente tutti gli estremi di registrazione
	// e in più anche gli uri degli allegati
	private String xmlDatiIn;

	public Short getAnnoReg() {
		return annoReg;
	}

	public String getCategoriaReg() {
		return categoriaReg;
	}

	public String getIdProvReg() {
		return idProvReg;
	}

	public void setIdProvReg(String idProvReg) {
		this.idProvReg = idProvReg;
	}

	public BigDecimal getNumReg() {
		return numReg;
	}

	public String getSiglaRegistro() {
		return siglaRegistro;
	}

	public String getXmlDatiIn() {
		return xmlDatiIn;
	}

	public void setXmlDatiIn(String xmlDatiIn) {
		this.xmlDatiIn = xmlDatiIn;
	}

	public List<String> getUriAllegati() {
		return uriAllegati;
	}

	public void setAnnoReg(Short annoReg) {
		this.annoReg = annoReg;
	}

	public void setCategoriaReg(String categoriaReg) {
		this.categoriaReg = categoriaReg;
	}

	public void setNumReg(BigDecimal numReg) {
		this.numReg = numReg;
	}

	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}

	public void setUriAllegati(List<String> uriAllegati) {
		this.uriAllegati = uriAllegati;
	}

	public Calendar getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Calendar dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

}
