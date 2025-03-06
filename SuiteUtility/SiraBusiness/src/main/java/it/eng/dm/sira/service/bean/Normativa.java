package it.eng.dm.sira.service.bean;

import java.math.BigDecimal;

public class Normativa {

	public Normativa() {
	}

	private BigDecimal codice;

	private String descrizione;

	public BigDecimal getCodice() {
		return codice;
	}

	public void setCodice(BigDecimal codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
