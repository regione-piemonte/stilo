/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class RichiestaAccessoDocBean {

	private String tipo;
	
	private String siglaRegistro;
	
	private String anno;
	
	private String numero;
	
	private String sub;
	
	private String tipoAccesso;
	
	private String motivazioni;
	
	private String idUd;
	
	private String storeErrorMessage;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSiglaRegistro() {
		return siglaRegistro;
	}
	public void setSiglaRegistro(String siglaRegistro) {
		this.siglaRegistro = siglaRegistro;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getTipoAccesso() {
		return tipoAccesso;
	}
	public void setTipoAccesso(String tipoAccesso) {
		this.tipoAccesso = tipoAccesso;
	}
	public String getMotivazioni() {
		return motivazioni;
	}
	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}
	public String getIdUd() {
		return idUd;
	}
	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}
	public String getStoreErrorMessage() {
		return storeErrorMessage;
	}
	public void setStoreErrorMessage(String storeErrorMessage) {
		this.storeErrorMessage = storeErrorMessage;
	}
}
