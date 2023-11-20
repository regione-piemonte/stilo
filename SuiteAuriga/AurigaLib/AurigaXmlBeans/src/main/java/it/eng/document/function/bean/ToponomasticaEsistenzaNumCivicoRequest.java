/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class ToponomasticaEsistenzaNumCivicoRequest extends ToponomasticaRicercaRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceStrada;
	private String numeroCivico;
	private String letteraCivico;
	private String coloreCivico;
	
	public String getCodiceStrada() {
		return codiceStrada;
	}
	
	public void setCodiceStrada(String codiceStrada) {
		this.codiceStrada = codiceStrada;
	}
	
	public String getNumeroCivico() {
		return numeroCivico;
	}
	
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	
	public String getLetteraCivico() {
		return letteraCivico;
	}
	
	public void setLetteraCivico(String letteraCivico) {
		this.letteraCivico = letteraCivico;
	}
	
	public String getColoreCivico() {
		return coloreCivico;
	}
	
	public void setColoreCivico(String coloreCivico) {
		this.coloreCivico = coloreCivico;
	}
	
}
