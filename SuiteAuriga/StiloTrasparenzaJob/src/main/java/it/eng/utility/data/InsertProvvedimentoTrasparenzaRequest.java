/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class InsertProvvedimentoTrasparenzaRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String annoProvvedimento;
	private String oggettoProvvedimeno;
	private String semestreProvvedimento;
	private String numeroProvvedimento;
	private String dataProvvedimento;
	private String tipoProvvedimento;
	
	public String getAnnoProvvedimento() {
		return annoProvvedimento;
	}
	
	public void setAnnoProvvedimento(String annoProvvedimento) {
		this.annoProvvedimento = annoProvvedimento;
	}
	
	public String getOggettoProvvedimeno() {
		return oggettoProvvedimeno;
	}
	
	public void setOggettoProvvedimeno(String oggettoProvvedimeno) {
		this.oggettoProvvedimeno = oggettoProvvedimeno;
	}
	
	public String getSemestreProvvedimento() {
		return semestreProvvedimento;
	}
	
	public void setSemestreProvvedimento(String semestreProvvedimento) {
		this.semestreProvvedimento = semestreProvvedimento;
	}
	
	public String getNumeroProvvedimento() {
		return numeroProvvedimento;
	}
	
	public void setNumeroProvvedimento(String numeroProvvedimento) {
		this.numeroProvvedimento = numeroProvvedimento;
	}
	
	public String getDataProvvedimento() {
		return dataProvvedimento;
	}
	
	public void setDataProvvedimento(String dataProvvedimento) {
		this.dataProvvedimento = dataProvvedimento;
	}
	
	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}
	
	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}
	
	@Override
	public String toString() {
		return "InsertAttoTrasparenzaRequest [annoProvvedimento=" + annoProvvedimento + ", oggettoProvvedimeno="
				+ oggettoProvvedimeno + ", semestreProvvedimento=" + semestreProvvedimento + ", numeroProvvedimento="
				+ numeroProvvedimento + ", dataProvvedimento=" + dataProvvedimento + ", tipoProvvedimento="
				+ tipoProvvedimento + "]";
	}
	
}
