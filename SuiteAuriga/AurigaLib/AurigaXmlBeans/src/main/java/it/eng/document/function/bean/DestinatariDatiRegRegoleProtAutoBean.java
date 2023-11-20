/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import it.eng.document.NumeroColonna;

public class DestinatariDatiRegRegoleProtAutoBean implements Serializable {

	private static final long serialVersionUID = 8688713266565631368L;

	@NumeroColonna(numero = "1")
	private String typeNodo;
	
	@NumeroColonna(numero = "2")
	private String idUoSv;
	
	@NumeroColonna(numero = "3")
	private String tipoAssegnazione;
	
	@NumeroColonna(numero = "4")
	private String codRapido;
	
	@NumeroColonna(numero = "5")
	private String denDescUoSv;

	public String getTypeNodo() {
		return typeNodo;
	}

	public void setTypeNodo(String typeNodo) {
		this.typeNodo = typeNodo;
	}

	public String getIdUoSv() {
		return idUoSv;
	}

	public void setIdUoSv(String idUoSv) {
		this.idUoSv = idUoSv;
	}

	public String getTipoAssegnazione() {
		return tipoAssegnazione;
	}

	public void setTipoAssegnazione(String tipoAssegnazione) {
		this.tipoAssegnazione = tipoAssegnazione;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getDenDescUoSv() {
		return denDescUoSv;
	}

	public void setDenDescUoSv(String denDescUoSv) {
		this.denDescUoSv = denDescUoSv;
	}

}
