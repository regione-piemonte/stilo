/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class AlberoProcessoXmlBean {

	@NumeroColonna(numero = "1")
	private String id;
	@NumeroColonna(numero = "2")
	private Integer livello;
	@NumeroColonna(numero = "3")
	private String tipo;
	@NumeroColonna(numero = "4")
	private String title;
	@NumeroColonna(numero = "5")
	private String altToShow;
	@NumeroColonna(numero = "6")
	private String colonnaSix;
	@NumeroColonna(numero = "7")
	private String colonnaSeven;
	@NumeroColonna(numero = "9")
	private String colonnaNine;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getLivello() {
		return livello;
	}
	public void setLivello(Integer livello) {
		this.livello = livello;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAltToShow() {
		return altToShow;
	}
	public void setAltToShow(String altToShow) {
		this.altToShow = altToShow;
	}
	public String getColonnaSix() {
		return colonnaSix;
	}
	public void setColonnaSix(String colonnaSix) {
		this.colonnaSix = colonnaSix;
	}
	public String getColonnaSeven() {
		return colonnaSeven;
	}
	public void setColonnaSeven(String colonnaSeven) {
		this.colonnaSeven = colonnaSeven;
	}
	public String getColonnaNine() {
		return colonnaNine;
	}
	public void setColonnaNine(String colonnaNine) {
		this.colonnaNine = colonnaNine;
	}
   
}
