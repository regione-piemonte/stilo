/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.TipoAssegnatario;
import java.io.Serializable;

public class AssegnatariEmailBean implements Serializable{

	@NumeroColonna(numero = "1")
	private TipoAssegnatario tipo;
	
	@NumeroColonna(numero = "2")
	private String idSettato;
	
	@NumeroColonna(numero = "3")
	private String messaggio;	
	
	public TipoAssegnatario getTipo() {
		return tipo;
	}
	public void setTipo(TipoAssegnatario tipo) {
		this.tipo = tipo;
	}
	public String getIdSettato() {
		return idSettato;
	}
	public void setIdSettato(String idSettato) {
		this.idSettato = idSettato;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}	
	
}
