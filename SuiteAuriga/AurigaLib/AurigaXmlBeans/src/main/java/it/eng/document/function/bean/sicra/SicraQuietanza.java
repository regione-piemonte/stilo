/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraQuietanza implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long numero;
	private BigInteger anno;
	private Calendar data;
	private Double importo;
	
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
	
	public Double getImporto() {
		return importo;
	}
	
	public void setImporto(Double importo) {
		this.importo = importo;
	}
	
}
