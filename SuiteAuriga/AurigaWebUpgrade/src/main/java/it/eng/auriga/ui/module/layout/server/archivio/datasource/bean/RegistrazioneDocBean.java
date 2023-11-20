/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RegistrazioneDocBean implements Serializable {
	
	private String codCategoria;
	private String sigla;
	private String anno;
	private String nro;
	private String motiviRichAnnullamento;
	private String errorMessage;
	
	public String getCodCategoria() {
		return codCategoria;
	}
	public void setCodCategoria(String codCategoria) {
		this.codCategoria = codCategoria;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getNro() {
		return nro;
	}
	public void setNro(String nro) {
		this.nro = nro;
	}
	public String getMotiviRichAnnullamento() {
		return motiviRichAnnullamento;
	}
	public void setMotiviRichAnnullamento(String motiviRichAnnullamento) {
		this.motiviRichAnnullamento = motiviRichAnnullamento;
	}	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}	
	
}
