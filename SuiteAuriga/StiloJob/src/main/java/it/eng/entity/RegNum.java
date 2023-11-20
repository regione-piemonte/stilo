/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;


public class RegNum implements java.io.Serializable {

	private String sigla;
	private BigDecimal numero;
	private BigDecimal anno;
	private Date ts;
	
	
	
	public RegNum(String sigla, BigDecimal numero, BigDecimal anno, Date ts) {
		super();
		this.sigla = sigla;
		this.numero = numero;
		this.anno = anno;
		this.ts = ts;
	}


	public RegNum() {
	}


	public String getSigla() {
		return sigla;
	}


	public void setSigla(String sigla) {
		this.sigla = sigla;
	}


	public BigDecimal getNumero() {
		return numero;
	}


	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}


	public BigDecimal getAnno() {
		return anno;
	}


	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}


	public Date getTs() {
		return ts;
	}


	public void setTs(Date ts) {
		this.ts = ts;
	}


	@Override
	public String toString() {
		return "RegNum [sigla=" + sigla + ", numero=" + numero + ", anno=" + anno + ", ts=" + ts + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anno == null) ? 0 : anno.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		result = prime * result + ((ts == null) ? 0 : ts.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegNum other = (RegNum) obj;
		if (anno == null) {
			if (other.anno != null)
				return false;
		} else if (!anno.equals(other.anno))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		if (ts == null) {
			if (other.ts != null)
				return false;
		} else if (!ts.equals(other.ts))
			return false;
		return true;
	}

    


}
