/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraOrdinativo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private Long numero;
	private BigInteger anno;
	private Calendar data;
	private String causale;
	private BigInteger annullato;
	private List<SicraBeneficiario> beneficiari;
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Long getNumero() {
		return numero;
	}
	
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public BigInteger getAnno() {
		return anno;
	}
	
	public void setAnno(BigInteger anno) {
		this.anno = anno;
	}
	
	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public String getCausale() {
		return causale;
	}
	
	public void setCausale(String causale) {
		this.causale = causale;
	}
	
	public BigInteger getAnnullato() {
		return annullato;
	}
	
	public void setAnnullato(BigInteger annullato) {
		this.annullato = annullato;
	}
	
	public List<SicraBeneficiario> getBeneficiari() {
		return beneficiari;
	}
	
	public void setBeneficiari(List<SicraBeneficiario> beneficiari) {
		this.beneficiari = beneficiari;
	}
	
}
