/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraFormaPagamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger codice;
	private String descrizione;
	private String codiceTipoPagamento;
	private String iban;
	private String bicSwift;
	
	public BigInteger getCodice() {
		return codice;
	}
	
	public void setCodice(BigInteger codice) {
		this.codice = codice;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getCodiceTipoPagamento() {
		return codiceTipoPagamento;
	}
	
	public void setCodiceTipoPagamento(String codiceTipoPagamento) {
		this.codiceTipoPagamento = codiceTipoPagamento;
	}
	
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	public String getBicSwift() {
		return bicSwift;
	}
	
	public void setBicSwift(String bicSwift) {
		this.bicSwift = bicSwift;
	}
	
}
